

import java.util.Random;
import java.util.Scanner;

public class MatrixMultiplication {

    static Scanner scanner = new Scanner(System.in);


    // We are checking we can multiply two matrices
    public static boolean canMultiply(int columnsA, int rowsB) {
        return columnsA == rowsB;
    }

    // Returns true if the matrix is not empty and all rows have the same length
    public static boolean isValid(double[][] M) {
        if (M == null || M.length == 0) {
            return false;
        }
        for (int i = 0; i < M.length; i++) {
            if (M[i] == null || M[i].length == 0 || M[i].length != M[0].length) {
                return false;
            }
        }
        return true;
    }

    
    // Returns null if the matrices cannot be multiplied.
    public static double[][] multiply(double[][] A, double[][] B) {
        if (!isValid(A) || !isValid(B) || !canMultiply(A[0].length, B.length)) {
            return null;
        }
        int n = A.length;      // rows of A
        int m = B.length;      // columns of A = rows of B
        int p = B[0].length;   // columns of B
        double[][] C = new double[n][p];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                double sum = 0;
                for (int k = 0; k < m; k++) {
                    sum = sum + A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    // I also implemented a faster version of the multiplication, which is more efficient for large matrices.
    public static double[][] multiplyFast(double[][] A, double[][] B) {
        if (!isValid(A) || !isValid(B) || !canMultiply(A[0].length, B.length)) {
            return null;
        }
        int n = A.length;
        int m = B.length;
        int p = B[0].length;
        double[][] C = new double[n][p];   // starts filled with zeros

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                double a = A[i][k];   
                for (int j = 0; j < p; j++) {
                    C[i][j] = C[i][j] + a * B[k][j];
                }
            }
        }
        return C;
    }

    

    // we ask until the user types a whole number greater than 0
    public static int readPositiveInt(String message) {
        int number = 0;
        while (number <= 0) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number <= 0) {
                    System.out.println("Please enter a number greater than 0.");
                }
            } else {
                System.out.println("That is not a whole number. Try again.");
                scanner.next();   // throw away the wrong input
            }
        }
        return number;
    }

    // Reads one number
    public static double readNumber() {
        while (!scanner.hasNextDouble()) {
            System.out.println("'" + scanner.next() + "' is not a number. Type it again:");
        }
        return scanner.nextDouble();
    }

    // Reads a whole matrix. The user type each row on one line
    public static double[][] readMatrix(String name, int rows, int columns) {
        System.out.println();
        System.out.println("Enter matrix " + name + " (" + rows + "x" + columns + ")");
        double[][] M = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.out.print("Row " + (i + 1) + " (" + columns + " numbers): ");
            for (int j = 0; j < columns; j++) {
                M[i][j] = readNumber();
            }
        }
        return M;
    }

    // Fills a matrix with random whole numbers from 0 to 9
    public static double[][] randomMatrix(int rows, int columns) {
        Random random = new Random();
        double[][] M = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                M[i][j] = random.nextInt(10);
            }
        }
        return M;
    }

    // Prints a matrix only if our matrix is small)
    public static void printMatrix(String name, double[][] M) {
        System.out.println();
        System.out.println("Matrix " + name + " (" + M.length + "x" + M[0].length + "):");
        if (M.length > 10 || M[0].length > 10) {
            System.out.println("(too large to print)");
            return;
        }
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                double value = M[i][j];
                if (value == (long) value) {
                    System.out.print((long) value + "\t");   // print 6 instead of 6.0
                } else {
                    System.out.print(value + "\t");
                }
            }
            System.out.println();
        }
    }

   

    public static void main(String[] args) {
        System.out.println("=== Matrix Multiplication ===");
        System.out.println("1) Multiply two matrices");
        System.out.println("2) Speed test (benchmark)");
        int option = readPositiveInt("Choose 1 or 2: ");

        if (option == 2) {
            runBenchmark();
        } else {
            runCalculator();
        }
    }

    public static void runCalculator() {
        int rowsA = 0;
        int columnsA = 0;
        int rowsB = 0;
        int columnsB = 0;
        boolean sizesOk = false;

        // Ask for the sizes until A and B can be multiplied
        while (!sizesOk) {
            System.out.println();
            rowsA = readPositiveInt("Rows of matrix A: ");
            columnsA = readPositiveInt("Columns of matrix A: ");
            rowsB = readPositiveInt("Rows of matrix B: ");
            columnsB = readPositiveInt("Columns of matrix B: ");

            if (canMultiply(columnsA, rowsB)) {
                sizesOk = true;
            } else {
                System.out.println("Cannot multiply: A has " + columnsA + " columns but B has "
                        + rowsB + " rows. They must be equal. Try again.");
            }
        }

        // Ask from user how to fill the matrices
        System.out.println();
        System.out.println("1) Type the numbers myself");
        System.out.println("2) Random numbers from 0 to 9");
        int choice = readPositiveInt("Choose 1 or 2: ");

        double[][] A;
        double[][] B;
        if (choice == 2) {
            A = randomMatrix(rowsA, columnsA);
            B = randomMatrix(rowsB, columnsB);
        } else {
            A = readMatrix("A", rowsA, columnsA);
            B = readMatrix("B", rowsB, columnsB);
        }

        // Multiply and measure the time
        long start = System.nanoTime();
        double[][] C = multiply(A, B);
        long end = System.nanoTime();

        printMatrix("A", A);
        printMatrix("B", B);
        printMatrix("C = A x B", C);
        System.out.println();
        System.out.println("Time: " + (end - start) / 1000 + " microseconds");
    }

  // it is for running sample code
    public static void runBenchmark() {
        int[] sizes = {64, 128, 256, 512, 1024};

       
        // so we run some small multiplications first to make the test fair.
        double[][] W = randomMatrix(100, 100);
        for (int i = 0; i < 20; i++) {
            multiply(W, W);
            multiplyFast(W, W);
        }

        System.out.println();
        System.out.println("N\tmultiply (ms)\tmultiplyFast (ms)");
        for (int s = 0; s < sizes.length; s++) {
            int n = sizes[s];
            double[][] A = randomMatrix(n, n);
            double[][] B = randomMatrix(n, n);

            long start = System.nanoTime();
            multiply(A, B);
            long timeSlow = (System.nanoTime() - start) / 1000000;

            start = System.nanoTime();
            multiplyFast(A, B);
            long timeFast = (System.nanoTime() - start) / 1000000;

            System.out.println(n + "\t" + timeSlow + "\t\t" + timeFast);
        }
    }
}
