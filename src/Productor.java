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

    }

}