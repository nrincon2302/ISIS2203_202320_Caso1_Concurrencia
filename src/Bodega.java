public class Bodega {
    // ================== ATRIBUTOS ==================
    private int tamanio;
    private ArrayList<Producto> contenido;

    // ================== CONSTRUCTOR ==================
    public Bodega(int TAM) {
        this.tamanio = TAM;
        this.contenido = new ArrayList<>();
    }

    // ================== MÉTODOS ==================
    public void agregarABodega(Producto p) {
        if (contenido.size() < tamanio) {
            contenido.add(p);
        }
    }

    public void quitarDeBodega() {
        if (contenido.size() != 0) {
            contenido.remove(0);
        }
        else {
            Thread.wait();
        }
    }
}