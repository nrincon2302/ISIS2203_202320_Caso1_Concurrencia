import java.util.ArrayList;

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
    public int darTamanio() {
        return tamanio;
    }

    public int darDisponibilidad() {
        return tamanio - contenido.size();
    }

    public synchronized void agregarABodega(Producto p) {
        // Sincronizado para evitar que muchos agreguen al tiempo
        contenido.add(p);
    }

    public Producto quitarDeBodega() {
        // No tiene que ser sincronizado porque sólo el despachador quita
        Producto p = contenido.remove(0);
        return p;
    }
}