import java.util.ArrayList;

public class Despachador extends Thread {
    // ================== ATRIBUTOS ==================
    private static Bodega bodega;
    private Producto productoActual;
    private ArrayList<Repartidor> repartidores;

    // ================== CONSTRUCTOR ==================
    public Despachador(Bodega pBodega) {
        bodega = pBodega;
        productoActual = null;
        repartidores = new ArrayList<>();
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        // Mientras que la bodega esté vacía, espera activamente realizando otro método
        while (bodega.darDisponibilidad() == bodega.darTamanio()) {
            System.out.println("Despachador: No hay productos en bodega");
            esperarActivamente();
        }
        // Si hay un producto en bodega, puede retirarlo
        Producto p = retirarDeBodega();
        productoActual = p;
        // Sincronizar sobre el producto retirado
        synchronized (productoActual) {
            System.out.println("Despachador: El producto " + p.getId() + " ha sido retirado de bodega");
            p.cambiarEstado("Despachado");
            try {
                productoActual.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Producto retirarDeBodega() {
        return bodega.quitarDeBodega();
    }

    public void esperarActivamente() {
        // Se realiza una acción cualquiera no tan larga
        int contador = 0;
        for (int j=0; j<100; j++) {
            contador += j;
        }
    }
}