import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Productor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private static Bodega bodega; // La bodega es compartida por todos los Productores
    private int cantidadAProducir;
    private CyclicBarrier barrera;

    // ================== CONSTRUCTOR ==================
    public Productor(int pId, Bodega pBodega, int pCantidad, CyclicBarrier pBarrera) {
        this.id = pId;
        bodega = pBodega;
        this.cantidadAProducir = pCantidad;
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

        for (int i=0; i<cantidadAProducir; i++) {
            // Producir la cantidad de productos que tiene asignado este Productor
            Producto producto = producir();

            // Sincronizar con respecto a la bodega
            synchronized (bodega) {
                // Si la bodega está llena, espera pasivamente sobre la bodega
                while (bodega.darDisponibilidad() == 0) {
                    try {
                        // El producto debería seguir en estado Producido pues no fue agregado
                        System.out.println("Productor " + id + ": La bodega está llena. No se agregó el producto " + producto.getId() +
                                            "\nProducto " + producto.getId() + ": " + producto.getEstado() + "\n");
                        bodega.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // De lo contrario, agrega el producto al almacén de la bodega
                bodega.agregarABodega(producto);
                producto.cambiarEstado("En bodega");
                System.out.println("Productor " + id + ": Hay espacio en bodega. Se agregó el producto " + producto.getId()
                                    + "\nProducto " + producto.getId() + ": " + producto.getEstado() + "\n");
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

    public int darId() {
        // Retorna su identificador único como instancia de Productor
        return id;
    }

    public Producto producir() {
        // Produce un nuevo producto, no tiene que ser sincronizado
        Producto p = new Producto(this);
        return p;
    }
}
