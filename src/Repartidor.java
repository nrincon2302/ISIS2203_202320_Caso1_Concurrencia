import java.util.Random;

public class Repartidor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private Despachador despachador;
    private Producto productoActual; // El repartidor tiene sólo un producto en su poder en un momento dado
    private boolean finalizado; // Indica si el repartidor debe continuar o ya finaliza su ejecución

    // ================== CONSTRUCTOR ==================
    public Repartidor(int pId, Despachador pDespachador) {
        this.id = pId;
        this.despachador = pDespachador;
        this.productoActual = null; // En principio, no tiene ningún producto a la mano
        this.finalizado = false;
        despachador.agregarRepartidor(this); // Vincula la asociación con el despachador
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        // Siempre que no deba haber finalizado el thread, while(true)
        while (!finalizado) {
            // Sincronizar sobre el despachador porque en Despachador se sincronizó sobre this
            synchronized (despachador) {
                while (productoActual == null) {
                    try {
                        // Espera pasivamente sobre el despachador hasta que le pase un producto
                        despachador.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            // Cuando tenga un producto para repartir
            productoActual.cambiarEstado("En reparto");
            repartirProducto();
            if (despachador.getProductosEntregados() == despachador.getTotalProductos() && !getFinalizado()) {
                finalizarEjecucion();
            }
        }
    }

    public void recogerProducto(Producto producto) {
        // Asigna el producto actual al que se pasa por parámetro
        this.productoActual = producto;
    }

    public void repartirProducto() {
        // Cuando un producto se encuentra en reparto, esto dura aleatorio entre 3 y 10 segundos
        int tiempoEspera = new Random().nextInt(7000) + 3000;
        try {
            // Para simular el reparto, se pone a dormir la cantidad de tiempo dada
            System.out.println("Repartidor " + darId() + ": Entregando el producto " + productoActual.getId() + "..." +
                                "\nProducto " + productoActual.getId() + ": " + productoActual.getEstado());
            Thread.sleep(tiempoEspera);
            // Se actualiza el estado del producto y los cambios para terminar el sistema
            productoActual.cambiarEstado("Entregado");
            despachador.agregarEntregado();
            System.out.println("\nProducto" + productoActual.getId() + ": " + productoActual.getEstado());
            productoActual = null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void finalizarEjecucion() {
        // Sincronizado para forzar la terminación uno por uno de los Repartidores
        this.finalizado = true;
    }

    public int darId() {
        // Retorna el indicador único de cada repartidor
        return id;
    }

    public Producto getProductoActual() {
        // Retorna el producto que el repartidor tiene en este momento
        return productoActual;
    }

    public boolean getFinalizado() {
        // Retorna si el thread de repartidor ha finalizado o no
        return finalizado;
    }
}