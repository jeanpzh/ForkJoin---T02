package problem;

import java.util.concurrent.RecursiveAction;
import java.util.Arrays;

/**
 * Implementa Merge Sort en paralelo usando Fork/Join.
 * Extiende RecursiveAction porque no devuelve valor (trabaja "in place").
 */
public class ParallelMergeSort extends RecursiveAction {

    private static final int MIN_TASK_SIZE = 4; // Umbral mínimo para parar de dividir tareas
    private final int[] array;
    private final int start; // índice inclusivo
    private final int end; // índice exclusivo

    public ParallelMergeSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int length = end - start;

        // Caso base: si el segmento es pequeño, ordenamos directamente
        if (length <= MIN_TASK_SIZE) {
            Arrays.sort(array, start, end);
            return;
        }

        // División: partimos en dos mitades
        int mid = start + length / 2;
        ParallelMergeSort leftTask = new ParallelMergeSort(array, start, mid);
        ParallelMergeSort rightTask = new ParallelMergeSort(array, mid, end);

        // Fork: lanzamos ambas tareas en paralelo
        invokeAll(leftTask, rightTask);

        // Join sobre invokeAll: tras finalizar las dos mitades, las fusionamos
        merge(start, mid, end);
    }

    /**
     * Fusiona dos subarreglos ordenados: [start, mid) y [mid, end)
     */
    private void merge(int start, int mid, int end) {
        int[] temp = new int[end - start];
        int i = start, j = mid, k = 0;

        // Recorremos ambas mitades y vamos copiando el menor elemento
        while (i < mid && j < end) {
            if (array[i] <= array[j])
                temp[k++] = array[i++];
            else
                temp[k++] = array[j++];

        }
        // Si quedan elementos en la izquierda
        while (i < mid)
            temp[k++] = array[i++];

        // Si quedan elementos en la derecha
        while (j < end)
            temp[k++] = array[j++];

        // Copiamos de vuelta al array original
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
