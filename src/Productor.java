public class Productor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private static Bodega bodega;
    private int cantidadAProducir;

    // ================== CONSTRUCTOR ==================
    public Productor(int pId, Bodega pBodega, int pCantidad) {
        this.id = pId;
        bodega = pBodega;
        this.cantidadAProducir = pCantidad;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        for (int i=0; i<cantidadAProducir; i++) {
            // Producir la cantidad de productos que tiene asignado este Productor
            Producto producto = producir();

            // Sincronizar con respecto a la bodega
            synchronized (bodega) {
                // Si la bodega está llena, espera pasivamente sobre la bodega
                while (bodega.darDisponibilidad() == 0) {
                    try {
                        System.out.println("Productor " + id + ": La bodega está llena. No se agregó el producto " + producto.getId());
                        bodega.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // De lo contrario, agrega el producto al almacén de la bodega
                bodega.agregarABodega(producto);
                System.out.println("Productor " + id + ": Hay espacio en bodega. Se agregó el producto " + producto.getId());
            }
            // Sincronizar con respecto al producto
            synchronized (producto) {
                // Si el producto no ha sido entregado, espera pasivamente sobre el producto
                while (producto.getEstado() != "Entregado") {
                    try {
                        producto.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public Producto producir() {
        // Produce un nuevo producto
        Producto p = new Producto();
        return p;
    }

}