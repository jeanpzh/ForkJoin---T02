import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

import problem.SequentialSorter;
import problem.ParallelMergeSort;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el número de elementos del arreglo (0 para salir): ");
        int n = scanner.nextInt(); // lee solo el número de elementos
        if (n <= 0) {
            System.out.println("Saliendo del programa.");
            scanner.close();
            return;
        }
        scanner.close();

        // genera arreglo aleatorio de tamaño n
        int[] original = new int[n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            original[i] = rnd.nextInt(100); // valores entre 0 y 99
        }

        // ejecuta Merge Sort secuencial
        int[] seqArray = Arrays.copyOf(original, n);
        long t0 = System.currentTimeMillis();
        SequentialSorter.sort(seqArray);
        long t1 = System.currentTimeMillis();

        // ejecuta Merge Sort paralelo
        int[] parArray = Arrays.copyOf(original, n);
        long t2 = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(new ParallelMergeSort(parArray, 0, n));
        long t3 = System.currentTimeMillis();

        // decide si mostrar arrays según tamaño
        boolean showArrays = (n <= 40);

        // imprime solo resultados
        if (showArrays) {
            System.out.println("Secuencial -> "
                    + Arrays.toString(seqArray)
                    + " | tiempo: " + (t1 - t0) + " ms");
            System.out.println("Paralelo   -> "
                    + Arrays.toString(parArray)
                    + " | tiempo: " + (t3 - t2) + " ms");
            return;
        }
        System.out.println("Secuencial -> tiempo: " + (t1 - t0) + " ms");
        System.out.println("Paralelo   -> tiempo: " + (t3 - t2) + " ms");

    }
}
