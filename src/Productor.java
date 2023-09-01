public class Productor extends Thread {
    // ================== ATRIBUTOS ==================
    private static int totalProductos;
    private int id;
    private int yoProduzco;


    // ================== CONSTRUCTOR ==================
    public Productor(int pId, int pTotal) {
        this.id = pId;
        totalProductos = pTotal;
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {

    }

}