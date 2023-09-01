import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        // Creación e inicialización de los Threads Productores
        for (int p=0; p<N; p++) {
            new Productor(p,N).start();
        }

        // Creación e inicialización de los Threads Repartidores
        for (int r=0; r<M; r++) {
            new Repartidor(r).start();
        }

        // Creación e inicialización del Thread Despachador
        new Despachador().start();


    }
}