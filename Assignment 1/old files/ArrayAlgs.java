import java.io.*;
import java.util.*;

/**************************
 * Sorting and Mystery Algorithms
 **************************/

class ArrayAlgs {
    private String algName;

    public ArrayAlgs(String algName) {
        this.algName = algName;
    }

    public void executeAlg(File testData) throws FileNotFoundException {
        if (algName.equals("insertionSortInitial"))
            insertionSortInitial(testData);
        else if (algName.equals("quickSortOptInitial"))
            quickSortOptInitial(testData);
        else if (algName.equals("mysteryRuntime1"))
            mysteryRuntime1(testData);
        else if (algName.equals("mysteryRuntime2"))
            mysteryRuntime2(testData);
        else if (algName.equals("mysteryRuntime3"))
            mysteryRuntime3(testData);
        else
            System.out.println("ERROR - Unknown Algorithm");
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     * TODO: find the runtime of this code and put it in comment at top file
     */
    static void mysteryRuntime1(File input) throws FileNotFoundException {
        int temp;
        int n;
        int i = 0;
        int[] array;
        Scanner sc = new Scanner(input);
        n = sc.nextInt();
        array = new int[n];
        while (sc.hasNextInt() && i < n) {
            temp = sc.nextInt();
            array[i] = temp;
            i++;
        }
        sc.close();
        while (n > 1) {
            n = n / 2;
            array[n / 2] = array[n];
        }
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     * TODO: find the runtime of this code and put it in comment at top file
     */
    static void mysteryRuntime2(File input) throws FileNotFoundException {
        int temp;
        int n;
        int i = 0, j = 0;
        int[] array;
        Scanner sc = new Scanner(input);
        n = sc.nextInt();
        array = new int[n];
        while (sc.hasNextInt() && i < n) {
            temp = sc.nextInt();
            array[i] = temp;
            i++;
        }
        sc.close();
        i = 0;
        while (j < n) {
            array[j] = array[i];
            i++;
            if (i >= n) {
                j++;
                i = 0;
            }
        }
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     * TODO: find the runtime of this code and put it in comment at top file
     */
    static void mysteryRuntime3(File input) throws FileNotFoundException {
        int temp;
        int n;
        int j;
        int i = 0;
        int[] array;
        Scanner sc = new Scanner(input);
        n = sc.nextInt();
        array = new int[n];
        while (sc.hasNextInt() && i < n) {
            temp = sc.nextInt();
            array[i] = temp;
            i++;
        }
        sc.close();
        for (i = 0; i < n; i++) {
            for (j = n - 1; j > 1; j /= 1.01) {
                array[j - 1] = array[j];
            }
        }
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     */
    static void insertionSortInitial(File input) throws FileNotFoundException {
        int i;
        int size;
        int[] array;
        Scanner sc = new Scanner(input);
        size = sc.nextInt();
        array = new int[size];
        for (i = 0; i < size; i++) {
            array[i] = sc.nextInt();
        }
        insertionSort(array, 0, size - 1);
        sc.close();
        // Error check to verify the array is sorted
        /*
         * for(i = 1; i < size; i++)
         * {
         * if(array[i-1] > array[i])
         * {
         * System.out.println("Not sorted!");
         * System.exit(-1);
         * }
         * }
         */
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     */
    static void insertionSort(int[] points, int low, int high) {
        int i, j;
        int temp; // fixed from double temp;
        for (i = low + 1; i <= high; i++) {
            for (j = i; j > low && points[j] < points[j - 1]; j--) {
                temp = points[j];
                points[j] = points[j - 1];
                points[j - 1] = temp;
            }
        }
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     */
    static void quickSortOptInitial(File input) throws FileNotFoundException {
        int i;
        int size;
        int[] array;
        Scanner sc = new Scanner(input);
        size = sc.nextInt();
        array = new int[size];
        for (i = 0; i < size; i++) {
            array[i] = sc.nextInt();
        }
        quickSortOpt(array, 0, size - 1);
        sc.close();
        // Error check to verify the array is sorted
        /*
         * for(i = 0; i < size; i++)
         * {
         * System.out.print(array[i] + " ");
         * if(array[i-1] > array[i])
         * {
         * System.out.println("Not sorted!");
         * System.exit(-1);
         * }
         * }
         * System.out.println();
         */
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     */
    static void quickSortOpt(int[] points, int low, int high) {
        Stack<Pair> recCalls = new Stack<Pair>();
        recCalls.push(new Pair(low, high));
        while (!recCalls.isEmpty()) {
            Pair p = recCalls.pop();
            if (p.high < p.low + 30)// Uses insertion sort when sorting <30 numbers
            {
                insertionSort(points, p.low, p.high);
            } else {
                int pivot = partition(points, p.low, p.high);
                recCalls.push(new Pair(p.low, pivot - 1)); // same as:
                quickSortOpt(points, p.low, pivot - 1);
                recCalls.push(new Pair(pivot + 1, p.high)); // same as:
                quickSortOpt(points, pivot + 1, p.high);
            }
        }
    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     */
    static int partition(int[] points, int low, int high) {
        Random rand = new Random();
        int pivot = rand.nextInt(high - low + 1) + low; // Pick a random pivot
        int pivotValue = points[pivot];
        int i = low + 1;
        int j = high;
        int temp;
        points[pivot] = points[low];
        points[low] = pivotValue;
        while (i < j) {
            while (i <= high && points[i] <= pivotValue) {
                i++;
            }
            while (j >= low && points[j] > pivotValue) {
                j--;
            }
            if (i < j) // swap out of order elements
            {
                temp = points[i];
                points[i] = points[j];
                points[j] = temp;
            }
        }
        if (i <= high && points[i] <= pivotValue) {
            i++;
        }
        points[low] = points[i - 1];
        points[i - 1] = pivotValue;
        return i - 1;
    }
}