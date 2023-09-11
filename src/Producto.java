public class Producto {
    // ================== ATRIBUTOS ==================
    private static int classId = 0; // Para ir asignando un identificador secuencial con cada creación
    private int id; // Para salvaguardar el id como identificador único del objeto
    private String estado; // Para conservar el estado de un producto dentro del proceso de la planta
    private Productor productor; // Un producto tiene uno y sólo un productor

    // ================== CONSTRUCTOR ==================
    public Producto(Productor pProductor) {
        classId ++; // Para que los productos arranquen siempre desde 1 y no desde 0
        this.id = classId;
        this.estado = "Producido"; // Un producto siempre arranca en estado Producido
        this.productor = pProductor;
        // Se imprime el estado inicial y el productor para conservar el registro en consola
        System.out.println("Producto " + id + ": Producido por el Productor " + productor.darId());
    }

    // ================== MÉTODOS ==================
    public int getId() {
        // Retorna el indicador único del producto
        return id;
    }

    public String getEstado() {
        // Retorna el estado actual del producto dentro del proceso de la planta
        return estado;
    }

    public void cambiarEstado(String pEstadoNuevo) {
        // Permite alterar el estado del producto
        estado = pEstadoNuevo;
    }
}
