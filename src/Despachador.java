import java.util.ArrayList;

public class Despachador extends Thread {
    // ================== ATRIBUTOS ==================
    private Bodega bodega; // La bodega se comparte con los productores, no estática (hay un despachador)
    private Producto productoActual; // El despachador tiene sólo un producto en su poder en un momento dado
    private ArrayList<Repartidor> repartidores; // El despachador conoce a los repartidores
    private int totalACompletar;
    private int completados; // El despachador es el que conoce cuántos productos hay que entregar en total

    // ================== CONSTRUCTOR ==================
    public Despachador(Bodega pBodega, int pTotal) {
        bodega = pBodega;
        this.productoActual = null; // En principio, no tiene ningún producto a la mano
        this.repartidores = new ArrayList<>();
        this.totalACompletar = pTotal;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
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
                if (repartidor.darProductoActual() == null) {
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

    public int getTotalACompletar() {
        // Retorna el total de productos que deben ser procesados en la ejecución
        return totalACompletar;
    }

    public int darCompletados() {
        // Retorna la cantidad de productos que han sido completamente procesados hasta el momento
        return completados;
    }

    public void agregarCompletado() {
        // Incrementa el contador con la cantidad de productos completados hasta el momento
        completados ++;
    }

    public Producto retirarDeBodega() {
        // Retira un producto de la bodega y lo retorna
        return bodega.quitarDeBodega();
    }

    public void esperarActivamente() {
        // Se realiza una acción cualquiera no tan larga
        System.out.println("Despachador: Esperando a que haya productos en bodega...\n");
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

    public synchronized void despacharProducto() {
        // Sincronizar sobre el producto del despachador en un momento dado
        while (productoActual != null) {
            try {
                productoActual.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}