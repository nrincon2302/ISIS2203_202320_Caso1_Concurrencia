import java.util.ArrayList;

public class Despachador extends Thread {
    // ================== ATRIBUTOS ==================
    private static Bodega bodega;
    private Producto productoActual;
    private ArrayList<Repartidor> repartidores;

    // ================== CONSTRUCTOR ==================
    public Despachador(Bodega pBodega) {
        bodega = pBodega;
        productoActual = null;
        repartidores = new ArrayList<>();
    }

    // ================== MÉTODOS ==================
    @Override
    public void run() {
        // Mientras que la bodega esté vacía, espera activamente realizando otro método
        while (bodega.darDisponibilidad() == bodega.darTamanio()) {
            esperarActivamente();
        }
        // Si hay un producto en bodega, puede retirarlo
        Producto p = retirarDeBodega();
        p.cambiarEstado("En despacho");
        productoActual = p;

        // Espera pasivamente sobre el producto hasta que sea recogido por un repartidor
        try {
            productoActual.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Repartidor repartidorDisponible = null;
        synchronized (repartidores){
            for(Repartidor repartidor : repartidores){
                if(!repartidor.estaOcupado()){
                    repartidorDisponible = repartidor;
                    break;
                }
            }
        }
        if(repartidorDisponible != null){
            //Asignar producto al repartidor
            repartidorDisponible.entregarProducto(p);
            
            // Sincronizar sobre el producto retirado
            synchronized (productoActual) {
                System.out.println("Despachador: El producto " + productoActual.getId() + " ha sido retirado de bodega"
                                + "\nProducto " + productoActual.getId() + ": " + productoActual.getEstado() + "\n");
                
            }
        }
    }

    public Producto retirarDeBodega() {
        return bodega.quitarDeBodega();
    }

    public void esperarActivamente() {
        // Se realiza una acción cualquiera no tan larga
        System.out.println("Despachador: Esperando a que haya productos en bodega...");
        int contador = 0;
        for (int j=0; j<100; j++) {
            contador += j;
        }
    }
}