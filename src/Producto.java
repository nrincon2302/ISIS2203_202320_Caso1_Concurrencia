public class Producto {
    // ================== ATRIBUTOS ==================
    private static int classId = 0;
    private int id;
    private String estado;

    // ================== CONSTRUCTOR ==================
    public Producto() {
        classId ++;
        this.id = classId;
        estado = "Producido";
    }

    // ================== MÉTODOS ==================
    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void cambiarEstado(String pEstadoNuevo) {
        estado = pEstadoNuevo;
    }
}