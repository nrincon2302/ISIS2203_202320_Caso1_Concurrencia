import java.util.Random;

public class Repartidor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private static Bodega bodega;
    private static Despachador despachador;
    private Producto productoActual;

    // ================== CONSTRUCTOR ==================
    public Repartidor(int pId, Bodega pBodega, Despachador pDespachador) {
        this.id = pId;
        bodega = pBodega;
        productoActual = null;
        despachador = pDespachador;
        despachador.agregarRepartidor(this);
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        while (despachador.getTotalACompletar() - despachador.darCompletados() != 0) {
            while ((productoActual.equals(null))) {
                try {
                    despachador.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            recogerProducto();
            System.out.println("Repartidor " + id + ": He recogido el producto " + productoActual.getId() +
                                "\nProducto " + productoActual.getId() + ": " + productoActual.getEstado());
            repartirProducto();
            System.out.println("Repartidor " + id + ": He entregado el producto " + productoActual.getId() +
                    "\nProducto " + productoActual.getId() + ": " + productoActual.getEstado());
        }
    }

    public synchronized void recogerProducto() {
        // Recoge el producto del despachador si tiene alguno disponible para reparto
        while (despachador.darProductoActual().equals(null)) {
            // Espera pasivamente en caso de que no haya producto en el despachador
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        productoActual = despachador.darProductoActual();
        despachador.despacharProducto();
        productoActual.cambiarEstado("En reparto");
    }

    public void repartirProducto() {
        // Cuando un producto se encuentra en reparto, esto dura aleatorio entre 3 y 10 segundos
        Random r = new Random();
        int tiempoEspera = r.nextInt(10000-3000) + 3000;
        try {
            // Para simular el reparto, se pone a dormir la cantidad de tiempo dada
            sleep(tiempoEspera);
            productoActual.cambiarEstado("Entregado");
            despachador.agregarCompletado();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Producto darProductoActual() {
        return productoActual;
    }
}