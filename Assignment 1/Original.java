import java.io.*;
import java.util.*;

/************************** Driver to test your code **************************/
/*
 * Sortness
 * Controls what numbers are generated for the arrays
 */
enum OrderOfData {
    RANDOM,
    INCREASING,
    DECREASING,
    DUPLICATE
}

/*
 * Provided code - DO NOT CHANGE THIS CODE
 */
public class Original {
    /* set enableTest[i] to false to disable the ith test */
    public static final boolean enableTest[] = {
            true, // Insertion (sorted order)
            true, // Insertion (reverse sorted order)
            true, // quicksort (Random numbers)
            true, // quicksort (duplicate numbers)
            true, // Mystery 1
            true, // Mystery 2
            true, // Mystery 3
    };
    public static final int[] sizes1 = { 4000, 8000, 16000, 32000, 64000, 128000,
            256000, 512000, 1024000, 2048000, 4096000 };

    public static void main(String[] args) throws IOException {
        RuntimeTable tableDS;
        System.out.println();
        RuntimeTable.printName();
        System.out.println();
        tableDS = RuntimeTable.timeAlgorithm("quicksort (random)", 1, 9, sizes1, new ArrayAlgs("quickSortOptInitial"),
                OrderOfData.RANDOM); // unprinted run to allocate memory ahead of time
        for (int i = 0; i <= 6; i++) {
            if (enableTest[i] && i == 0)
                tableDS = RuntimeTable.timeAlgorithm("Insertion Sort (sorted order)", 4, 5,
                        sizes1, new ArrayAlgs("insertionSortInitial"), OrderOfData.INCREASING);
            else if (enableTest[i] && i == 1)
                tableDS = RuntimeTable.timeAlgorithm("Insertion Sort (reverse sorted order)", 4, 4, sizes1,
                        new ArrayAlgs("insertionSortInitial"),
                        OrderOfData.DECREASING);
            else if (enableTest[i] && i == 2)
                tableDS = RuntimeTable.timeAlgorithm("quicksort (random)", 12, 8, sizes1,
                        new ArrayAlgs("quickSortOptInitial"), OrderOfData.RANDOM);
            else if (enableTest[i] && i == 3)
                tableDS = RuntimeTable.timeAlgorithm("quicksort (duplicates)", 12, 5,
                        sizes1, new ArrayAlgs("quickSortOptInitial"), OrderOfData.DUPLICATE);
            else if (enableTest[i] && i == 4)
                tableDS = RuntimeTable.timeAlgorithm("Mystery 1", 6, 8, sizes1, new ArrayAlgs("mysteryRuntime1"),
                        OrderOfData.RANDOM);
            else if (enableTest[i] && i == 5)
                tableDS = RuntimeTable.timeAlgorithm("Mystery 2", 3, 4, sizes1, new ArrayAlgs("mysteryRuntime2"),
                        OrderOfData.RANDOM);
            else if (enableTest[i] && i == 6)
                tableDS = RuntimeTable.timeAlgorithm("Mystery 3", 4, 5, sizes1, new ArrayAlgs("mysteryRuntime3"),
                        OrderOfData.RANDOM);
            if (enableTest[i])
                RuntimeTable.printRuntimeTable(tableDS);
        }
        RuntimeTable.printName();
        System.out.println();
    }
}

/**************************
 * Methods for timing functions and printing tables of the
 * runtimes
 **************************/
/*
 * TODO:
 * Give your asymptotic estimates for the runtimes of the following 3 functions:
 * mysteryRuntime1: O( )
 * mysteryRuntime2: O( )
 * mysteryRuntime3: O( )
 *
 */
class RuntimeTable {
    public static final String DATA_FILE_NAME = "TestData.txt";
    public String name; // name of the function being tested
    public double[][] runtimeTable; // table of runtimes (array #rows = numTestCaseSizes,#columns=numRepeats)
    public double[] averageArray; // average runtimes (array length = numTestCaseSizes)
    public int[] testCaseSizesArray; // array containing the test case sizes (array length=numTestCaseSizes)
    public int numRepeats; // number of times to repeat each test size
    public int numTestCaseSizes; // number of test case sizes
    /*
     * TO BE COMPLETED BY YOU
     * Fill in your name in the function below
     */

    public static void printName() {
        System.out.println("This solution was completed by:");
        System.out.println("Sean West");
    }

    public static RuntimeTable timeAlgorithm(String name, int numRepeats, int numTestCaseSizes,
            int[] testCaseSizesArray, ArrayAlgs sortingAlgorithm, OrderOfData order) throws IOException {
        /* Call and calculate the runtime of the provided function */
        long start = 0, end = 0;
        int i, j;
        File testData;
        // create RuntimeTable variable to return
        RuntimeTable tableDS = new RuntimeTable();

        tableDS.name = name;
        tableDS.numRepeats = numRepeats;
        tableDS.numTestCaseSizes = numTestCaseSizes;
        tableDS.testCaseSizesArray = new int[numTestCaseSizes];

        for (i = 0; i < numTestCaseSizes; i++) {
            tableDS.testCaseSizesArray[i] = testCaseSizesArray[i];
        }
        tableDS.runtimeTable = new double[numTestCaseSizes][numRepeats];

        for (j = 0; j < numRepeats; j++) {
            {
                // Generate test data for the function sortingAlgorithm
                testData = generateTestInput(0, testCaseSizesArray[i],
                        testCaseSizesArray[i], order);
                // Run sortingAlgorithm on the generated test data
                start = System.currentTimeMillis();
                sortingAlgorithm.executeAlg(testData);
                end = System.currentTimeMillis();
                // Enter the elapsed number of seconds into the arrRuntimes array fortableDS
                // TODO: uncomment the next line line after you've set a value for
                // tableDS.runtimeTable
                // tableDS.runtimeTable[i][j] = (double)(end - start) / 1000;
            }
        }
        // TODO: Calculate the average runtimes (create space for tableDS.averageArray
        // and call computeAverages here)
        return tableDS;

    }

    /*
     * Provided code - DO NOT CHANGE THIS METHOD
     */
    public static File generateTestInput(int min, int max, int size, OrderOfData order) throws IOException {
        int i;
        File data = new File(DATA_FILE_NAME);
        data.createNewFile();
        FileWriter writer = new FileWriter(data);
        writer.write(String.valueOf(size));
        writer.write(" ");
        for (i = 0; i < size; i++) {
            Random rand = new Random();
            if (order == OrderOfData.RANDOM)
                writer.write(String.valueOf(rand.nextInt(max - min + 1) + min));
            else if (order == OrderOfData.INCREASING)
                writer.write(String.valueOf(i));
            else if (order == OrderOfData.DECREASING)
                writer.write(String.valueOf(size - i));
            else if (order == OrderOfData.DUPLICATE)
                writer.write(String.valueOf(1337));
            else
                writer.write("ERROR");
            writer.write(" ");
        }
        writer.flush();
        writer.close();
        return data;
    }

    /*
     * TODO: TO BE COMPLETED BY YOU
     * Calculate and insert the average runtime for each set of test data into
     * tableDS
     */
    public static void computeAverages(RuntimeTable tableDS) {
    }

    /*
     * TODO: TO BE COMPLETED BY YOU
     * Print the information in tableDS as a 2d table. Display 3 digits after the
     * decimal point.
     * You can assume all of the runtimes are <= 99.999 seconds. The number of
     * repeats will be <= 14.
     *
     * The columns should each line up. Using printf to create minimum width sizes
     * for your printed variables should make this easier.
     *
     * The ith value in the increase column is calculated by dividing the ith
     * average
     * by the i-1th average. If i=0, print "N/A" instead.
     * E.g. if i=3, increase = tableDS.averageArray[3]/tableDS.averageArray[2]
     */
    public static void printRuntimeTable(RuntimeTable tableDS) {
    }
}

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
     * TODO: find the runtime of this code and put it in comment above RuntimeTable
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
     * TODO: find the runtime of this code and put it in comment above RuntimeTable
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
     * TODO: find the runtime of this code and put it in comment above RuntimeTable
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

class Pair {
    public int low;
    public int high;

    /* creates a Pair from the given x, y values */
    public Pair(int low, int high) {
        this.low = low;
        this.high = high;
    }
}