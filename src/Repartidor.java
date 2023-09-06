public class Repartidor extends Thread {
    // ================== ATRIBUTOS ==================
    private int id;
    private static Bodega bodega;

    // ================== CONSTRUCTOR ==================
    public Repartidor(int pId, Bodega pBodega) {
        this.id = pId;
        bodega = pBodega;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {

    }
}