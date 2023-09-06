public class Productor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private static Bodega bodega;

    // ================== CONSTRUCTOR ==================
    public Productor(int pId, Bodega pBodega) {
        this.id = pId;
        bodega = pBodega;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {

    }

    public void producirProducto() {

    }

}