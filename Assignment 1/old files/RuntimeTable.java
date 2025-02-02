import java.io.*;
import java.util.*;

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

        for (i = 0; i < numTestCaseSizes; i++) {
            for (j = 0; j < numRepeats; j++) {
                // Generate test data for the function sortingAlgorithm
                testData = generateTestInput(0, testCaseSizesArray[i],
                        testCaseSizesArray[i], order);
                // Run sortingAlgorithm on the generated test data
                start = System.currentTimeMillis();
                sortingAlgorithm.executeAlg(testData);
                end = System.currentTimeMillis();

                tableDS.runtimeTable[i][j] = (double) (end - start) / 1000;
            }
        }
        // TODO: Calculate the average runtimes (create space for tableDS.averageArray
        // and call computeAverages here)
        tableDS.averageArray = new double[numTestCaseSizes];
        computeAverages(tableDS);
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
        int i, j;
        for (i = 0; i < tableDS.numTestCaseSizes; i++) {
            double total = 0;
            for (j = 0; j < tableDS.numRepeats; j++) {
                total += tableDS.runtimeTable[i][j];
            }
            tableDS.averageArray[i] = total / tableDS.numRepeats;
        }
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
        System.out.printf("%-12s %-10s", "Size", "Average");
        for (int j = 0; j < tableDS.numRepeats; j++) {
            System.out.printf(" Run%-2d ", j + 1);
        }
        System.out.println(" Increase");

        for (int i = 0; i < tableDS.numTestCaseSizes; i++) {
            System.out.printf("%-12d %-10.3f", tableDS.testCaseSizesArray[i], tableDS.averageArray[i]);

            for (int j = 0; j < tableDS.numRepeats; j++) {
                System.out.printf(" %-6.3f", tableDS.runtimeTable[i][j]);
            }

            if (i == 0) {
                System.out.println(" N/A");
            } else {
                double increase = tableDS.averageArray[i] / tableDS.averageArray[i - 1];
                System.out.printf(" %-6.3f%n", increase);
            }
        }
    }
}