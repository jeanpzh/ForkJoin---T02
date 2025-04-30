package problem;

/**
 * Merge Sort secuencial: misma lógica de fusión que en ParallelMergeSort,
 * pero sin Fork/Join. Ordena el array "in place".
 */
public class SequentialSorter {

    /** Punto de entrada: ordena todo el arreglo. */
    public static void sort(int[] array) {
        mergeSort(array, 0, array.length);
    }

    /**
     * Divide y conquista: parte el rango [start, end) hasta tamaño 1,
     * luego fusiona.
     */
    private static void mergeSort(int[] array, int start, int end) {
        if (end - start <= 1) {
            // caso base: 0 o 1 elemento ya está ordenado
            return;
        }
        int mid = start + (end - start) / 2;
        // ordeno mitad izquierda
        mergeSort(array, start, mid);
        // ordeno mitad derecha
        mergeSort(array, mid, end);
        // fusiono ambas mitades
        merge(array, start, mid, end);
    }

    /**
     * Fusiona dos subarreglos ordenados: [start, mid) y [mid, end).
     */
    private static void merge(int[] array, int start, int mid, int end) {
        int[] temp = new int[end - start];
        int i = start, j = mid, k = 0;

        // mientras queden elementos en ambas mitades
        while (i < mid && j < end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        // si quedan en la izquierda
        while (i < mid) {
            temp[k++] = array[i++];
        }
        // si quedan en la derecha
        while (j < end) {
            temp[k++] = array[j++];
        }
        // copio de vuelta al arreglo original
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
