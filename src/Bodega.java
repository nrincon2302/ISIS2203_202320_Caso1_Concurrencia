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
        // Retorna el tamaño de la bodega - cuántos productos puede almacenar
        return tamanio;
    }

    public int darDisponibilidad() {
        // Retorna el espacio disponible en la bodega - cuántos productos le caben en este momento
        return tamanio - contenido.size();
    }

    public synchronized void agregarABodega(Producto p) {
        // Sincronizado para evitar que muchos productores agreguen productos al tiempo
        contenido.add(p);
    }

    public Producto quitarDeBodega() {
        // No tiene que ser sincronizado porque sólo el despachador quita productos uno a la vez
        Producto p = contenido.remove(0);
        return p;
    }
}