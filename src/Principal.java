import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Principal {

    public static String input(String mensaje) {
    /// Método input que puede usarse análogo al de Python
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        }
        catch (IOException e) {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("¡Bienvenid@ a la planta de producción!");
        System.out.println();

        int N = Integer.parseInt(input("Ingrese un número de productores"));
        int M = Integer.parseInt(input("Ingrese un número de repartidores"));
        int TAM = Integer.parseInt(input("Ingrese un tamaño de almacenamiento para la bodega"));
        int totalProductos = Integer.parseInt(input("Ingrese el total de productos a producir"));

        System.out.println();
        System.out.println("Usted ha ingresado los siguientes parámetros: \n" +
                            "NÚMERO DE PRODUCTORES: " + N + "\n" +
                            "NÚMERO DE REPARTIDORES: " + M + "\n" +
                            "TAMAÑO DE LA BODEGA: " + TAM + "\n" +
                            "TOTAL A PRODUCIR: " + totalProductos + "\n");
        System.out.println("Inicia el proceso... \n");

        int numThreads = N + M + 1 + 1; // Productores + Despachador + Repartidores + Principal
        CyclicBarrier barrera = new CyclicBarrier(numThreads);

        // Creación de la Bodega del programa
        Bodega bodega = new Bodega(TAM);

        // Asignación de la cantidad a producir de forma aleatoria


        // Creación e inicialización de los Threads Productores
        for (int p=1; p<=N; p++) {
            new Productor(p, bodega, 4, barrera).start();
        }

        // Creación e inicialización del Thread Despachador
        Despachador despachador = new Despachador(bodega, totalProductos, barrera);
        despachador.start();

        // Creación e inicialización de los Threads Repartidores
        for (int r=0; r<M; r++) {
            new Repartidor(r, despachador, barrera).start();
        }

        try {
            barrera.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}