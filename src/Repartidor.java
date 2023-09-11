import java.util.Random;

public class Repartidor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private static Bodega bodega;
    private boolean ocupado;
    private Producto producto;

    // ================== CONSTRUCTOR ==================
    public Repartidor(int pId, Bodega pBodega) {
        this.id = pId;
        bodega = pBodega;
        ocupado = false;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        while(true){
            if(producto != null){
            // Tiempo de entrega de producto (3-10s)
            int tiempoEntrega = 3000 + new Random().nextInt(7000);
            try{
                Thread.sleep(tiempoEntrega);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            producto.cambiarEstado("Entregado");
            System.out.println("Repartidor " + id + " ha entregado el producto " + producto.getId());

            synchronized (producto){
                producto.notify();
            }

            ocupado = false;
        }
    }
    }

    public boolean estaOcupado(){
        return ocupado;
    }

    public void entregarProducto(Producto pProducto){
        producto = pProducto;
        ocupado = true;
        System.out.println("Repartidor " + id + " está entregando el producto " + producto.getId() + " al cliente");
    }
}