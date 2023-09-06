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
        return contenido.size() - tamanio;
    }

    public synchronized void agregarABodega(Object p) {
        if (darDisponibilidad() > 0) {
            contenido.add(p);
            System.out.println("Se ha agregado un producto a la bodega");
        }
        else {
            System.out.println("No se ha agregado el producto. La bodega está llena");
        }
    }

    public synchronized void quitarDeBodega() {
        if (!contenido.isEmpty()) {
            contenido.remove(0);
        }
        else {
            System.out.println("La bodega se encuentra vacía, no se puede retirar ningún producto");
        }
    }
}