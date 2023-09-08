import java.util.ArrayList;

public class Bodega {
    // ================== ATRIBUTOS ==================
    private int tamanio;
    private ArrayList<Object> contenido;

    // ================== CONSTRUCTOR ==================
    public Bodega(int TAM) {
        this.tamanio = TAM;
        this.contenido = new ArrayList<>();
    }

    // ================== MÉTODOS ==================
    public int darDisponibilidad() {
        return tamanio - contenido.size();
    }

    public void agregarABodega(Object p) {
        contenido.add(p);
    }

    public void quitarDeBodega() {
        contenido.remove(0);
    }
}