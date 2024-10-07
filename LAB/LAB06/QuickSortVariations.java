import java.util.Random;

public class QuickSortVariations {

    // Função QuickSort com pivô sendo o primeiro elemento
    public static void quickSortFirstPivot(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = left; // Primeiro elemento como pivô
            int pivot = array[pivotIndex];
            int i = left + 1;
            int j = right;

            while (i <= j) {
                if (array[i] <= pivot) {
                    i++;
                } else if (array[j] > pivot) {
                    j--;
                } else {
                    swap(array, i, j);
                }
            }
            swap(array, left, j);
            quickSortFirstPivot(array, left, j - 1);
            quickSortFirstPivot(array, j + 1, right);
        }
    }

    // Função QuickSort com pivô sendo o último elemento
    public static void quickSortLastPivot(int[] array, int left, int right) {
        if (left < right) {
            swap(array, left, right); // Coloca o último como primeiro
            quickSortFirstPivot(array, left, right);
        }
    }

    // Função QuickSort com pivô aleatório
    public static void quickSortRandomPivot(int[] array, int left, int right) {
        if (left < right) {
            Random rand = new Random();
            int randomPivotIndex = left + rand.nextInt(right - left + 1);
            swap(array, left, randomPivotIndex); // Coloca pivô aleatório no início
            quickSortFirstPivot(array, left, right);
        }
    }

    // Função QuickSort com mediana de três elementos (início, meio e fim)
    public static void quickSortMedianOfThree(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            int a = array[left], b = array[mid], c = array[right];

            // Determina a mediana de três
            int medianIndex = (a < b) ?
                    (b < c ? mid : (a < c ? right : left)) :
                    (a < c ? left : (b < c ? right : mid));

            swap(array, left, medianIndex); // Coloca a mediana como pivô
            quickSortFirstPivot(array, left, right);
        }
    }

    // Função auxiliar para trocar elementos no array
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Função para medir o tempo de execução de cada algoritmo
    public static void measureTime(Runnable quickSortFunction, String name) {
        long startTime = System.nanoTime();
        quickSortFunction.run();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Tempo em milissegundos

        System.out.println("Tempo de execução (" + name + "): " + duration + " ms");
    }

    // Gera um array ordenado
    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    // Gera um array quase ordenado
    public static int[] generateNearlySortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        // Pequena desordem
        swap(array, size / 2, size / 2 + 1);
        return array;
    }

    // Gera um array aleatório
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(10000);
        }
        return array;
    }

    // Função principal para testar as variações do QuickSort
    public static void main(String[] args) {
        // Tamanhos dos arrays
        int[] sizes = {100, 1000, 10000};

        for (int size : sizes) {
            System.out.println("\n--- Testando com array de tamanho: " + size + " ---");

            int[] sortedArray = generateSortedArray(size);
            int[] nearlySortedArray = generateNearlySortedArray(size);
            int[] randomArray = generateRandomArray(size);

            System.out.println("\nArray ordenado:");
            measureTime(() -> quickSortFirstPivot(sortedArray.clone(), 0, sortedArray.length - 1), "Primeiro pivô");
            measureTime(() -> quickSortLastPivot(sortedArray.clone(), 0, sortedArray.length - 1), "Último pivô");
            measureTime(() -> quickSortRandomPivot(sortedArray.clone(), 0, sortedArray.length - 1), "Pivô aleatório");
            measureTime(() -> quickSortMedianOfThree(sortedArray.clone(), 0, sortedArray.length - 1), "Mediana de três");

            System.out.println("\nArray quase ordenado:");
            measureTime(() -> quickSortFirstPivot(nearlySortedArray.clone(), 0, nearlySortedArray.length - 1), "Primeiro pivô");
            measureTime(() -> quickSortLastPivot(nearlySortedArray.clone(), 0, nearlySortedArray.length - 1), "Último pivô");
            measureTime(() -> quickSortRandomPivot(nearlySortedArray.clone(), 0, nearlySortedArray.length - 1), "Pivô aleatório");
            measureTime(() -> quickSortMedianOfThree(nearlySortedArray.clone(), 0, nearlySortedArray.length - 1), "Mediana de três");

            System.out.println("\nArray aleatório:");
            measureTime(() -> quickSortFirstPivot(randomArray.clone(), 0, randomArray.length - 1), "Primeiro pivô");
            measureTime(() -> quickSortLastPivot(randomArray.clone(), 0, randomArray.length - 1), "Último pivô");
            measureTime(() -> quickSortRandomPivot(randomArray.clone(), 0, randomArray.length - 1), "Pivô aleatório");
            measureTime(() -> quickSortMedianOfThree(randomArray.clone(), 0, randomArray.length - 1), "Mediana de três");
        }
    }
}

