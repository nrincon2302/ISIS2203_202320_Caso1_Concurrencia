import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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

    public static int[] generarArregloDeProductos(int totalProductos, int numProductores) {
        int[] arregloProductos = new int[numProductores]; // Arreglo de N posiciones
        int productosPorProductor = totalProductos / numProductores; // Dividir equitativamente parte entera
        int productosRestantes = totalProductos % numProductores; // Manejar residuos que haya

        for (int i = 0; i < numProductores; i++) {
            arregloProductos[i] = productosPorProductor; // Distribuir los productos por productor
            if (productosRestantes > 0) { // Si hay residuo, distribuir hasta que se complete todo
                arregloProductos[i] ++;
                productosRestantes --;
            }
        }
        return arregloProductos;
    }

    public static void main(String[] args) {
        System.out.println("¡Bienvenid@ a la planta de producción!");
        System.out.println();

        int N = Integer.parseInt(input("Ingrese un número de productores"));
        int M = Integer.parseInt(input("Ingrese un número de repartidores"));
        int TAM = Integer.parseInt(input("Ingrese un tamaño de almacenamiento para la bodega"));
        int totalProductos = Integer.parseInt(input("Ingrese el total de productos a producir"));

        // Asignación de la cantidad a producir de forma aleatoria
        int[] productosPorProductor = generarArregloDeProductos(totalProductos, N);

        System.out.println();
        System.out.println("Usted ha ingresado los siguientes parámetros: \n" +
                            "NÚMERO DE PRODUCTORES: " + N + "\n" +
                            "NÚMERO DE REPARTIDORES: " + M + "\n" +
                            "TAMAÑO DE LA BODEGA: " + TAM + "\n" +
                            "TOTAL A PRODUCIR: " + totalProductos + "\n" +
                            "PRODUCTOS POR PRODUCTOR: " + Arrays.toString(productosPorProductor) + "\n");
        System.out.println("Inicia el proceso... \n");

        // Creación de la Bodega del programa
        Bodega bodega = new Bodega(TAM);

        // Creación e inicialización de los Threads Productores
        for (int p=1; p<=N; p++) {
            new Productor(p, bodega, productosPorProductor[p-1]).start();
        }

        // Creación e inicialización del Thread Despachador
        Despachador despachador = new Despachador(bodega, totalProductos);
        despachador.start();

        // Creación e inicialización de los Threads Repartidores
        for (int r=0; r<M; r++) {
            new Repartidor(r, despachador).start();
        }
    }
}