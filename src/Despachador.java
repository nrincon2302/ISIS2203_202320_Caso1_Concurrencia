import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Despachador extends Thread {
    // ================== ATRIBUTOS ==================
    private Bodega bodega; // La bodega se comparte con los productores, no estática (hay un despachador)
    private Producto productoActual; // El despachador tiene sólo un producto en su poder en un momento dado
    private ArrayList<Repartidor> repartidores; // El despachador conoce a los repartidores
    private int totalProductos; // El despachador es el que conoce cuántos productos hay que entregar en total
    private int productosEntregados; // Contador para saber cuántos han sido entregados en un momento dado
    private CyclicBarrier barrera;

    // ================== CONSTRUCTOR ==================
    public Despachador(Bodega pBodega, int pTotal, CyclicBarrier pBarrera) {
        bodega = pBodega;
        this.productoActual = null; // En principio, no tiene ningún producto a la mano
        this.repartidores = new ArrayList<>();
        this.totalProductos = pTotal;
        this.productosEntregados = 0;
        this.barrera = pBarrera;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        try {
            barrera.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        // Mientras que la bodega esté vacía, espera activamente realizando otro método no muy largo
        while (bodega.darDisponibilidad() == bodega.darTamanio()) {
            esperarActivamente();
        }
        // Si hay un producto en bodega, puede retirarlo
        Producto p = retirarDeBodega();
        // Se cambia el estado del producto a En Despacho y se asigna el producto actual del despachador
        p.cambiarEstado("En despacho");
        productoActual = p;

        // Intenta entregarle un producto a un repartidor que esté disponible
        while (productoActual != null) {
            for (Repartidor repartidor : repartidores) {
                // Si el repartidor está disponible, su producto actual es null
                if (repartidor.getProductoActual() == null) {
                    productoActual.cambiarEstado("Despachado");
                    repartidor.recogerProducto(productoActual);
                    productoActual = null;
                }
            }
        }

        // Si no se entrega el producto, espera pasivamente sobre sí mismo
        synchronized (this) {
            try {
                System.out.println("Despachador: No hay repartidores disponibles. No se despachó el producto." +
                                    "\nProducto " + productoActual.getId() + ": " + productoActual.getEstado());
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public int getTotalProductos() {
        // Retorna el total de productos que deben ser procesados en la ejecución
        return totalProductos;
    }

    public int getProductosEntregados() {
        // Retorna la cantidad de productos que han sido completamente procesados hasta el momento
        return productosEntregados;
    }

    public void agregarEntregado() {
        // Incrementa el contador con la cantidad de productos completados hasta el momento
        productosEntregados ++;
    }

    public Producto retirarDeBodega() {
        // Retira un producto de la bodega y lo retorna
        return bodega.quitarDeBodega();
    }

    public void esperarActivamente() {
        // Se realiza una acción cualquiera no tan larga
        int contador = 0;
        for (int j=0; j<1000; j++) {
            contador += j;
        }
    }

    public Producto darProductoActual() {
        // Retorna el producto que el despachador tiene en este momento
        return productoActual;
    }

    public void agregarRepartidor(Repartidor repartidor) {
        // Agrega un repartidor al listado de repartidores de la planta
        repartidores.add(repartidor);
    }
}