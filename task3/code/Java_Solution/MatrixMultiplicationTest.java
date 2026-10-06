

public class MatrixMultiplicationTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        // It is all tests that we will have
        test2x2KnownResult();
        testNonSquare();
        testResultSize();
        testDotProduct();
        testIdentity();
        testZeroMatrix();
        testNegativeAndDecimal();
        testIncompatibleSizes();
        testInvalidMatrices();
        testCanMultiply();
        testBothVersionsAgree();

        System.out.println();
        System.out.println(passed + " / " + (passed + failed) + " tests passed");
    }

  
    static void check(String testName, boolean ok) {
        if (ok) {
            System.out.println("PASS  " + testName);
            passed++;
        } else {
            System.out.println("FAIL  " + testName);
            failed++;
        }
    }

    // Returns true if bour matrices have the same size and the same values
    static boolean equal(double[][] X, double[][] Y) {
        if (X == null || Y == null || X.length != Y.length) {
            return false;
        }
        for (int i = 0; i < X.length; i++) {
            if (X[i].length != Y[i].length) {
                return false;
            }
            for (int j = 0; j < X[i].length; j++) {
                // Here we use a small tolerance because of rounding errors with doubles in any case
                if (Math.abs(X[i][j] - Y[i][j]) > 0.000000001) {
                    return false;
                }
            }
        }
        return true;
    }

    // Runs both versions and checks that both give the expected answer
    static void checkBoth(String testName, double[][] A, double[][] B, double[][] expected) {
        check(testName + " (multiply)", equal(MatrixMultiplication.multiply(A, B), expected));
        check(testName + " (multiplyFast)", equal(MatrixMultiplication.multiplyFast(A, B), expected));
    }

   

    // [1 2] x [5 6] = [1*5+2*7  1*6+2*8] = [19 22]
    // [3 4]   [7 8]   [3*5+4*7  3*6+4*8]   [43 50]
    static void test2x2KnownResult() {
        double[][] A = {{1, 2}, {3, 4}};
        double[][] B = {{5, 6}, {7, 8}};
        double[][] expected = {{19, 22}, {43, 50}};
        checkBoth("2x2 known result", A, B, expected);
    }

    // (2x3) x (3x2) = (2x2)
    static void testNonSquare() {
        double[][] A = {{1, 2, 3}, {4, 5, 6}};
        double[][] B = {{7, 8}, {9, 10}, {11, 12}};
        double[][] expected = {{58, 64}, {139, 154}};
        checkBoth("non-square 2x3 * 3x2", A, B, expected);
    }

    // (3x1) x (1x4) must give a 3x4 matrix
    static void testResultSize() {
        double[][] A = {{1}, {2}, {3}};
        double[][] B = {{1, 2, 3, 4}};
        double[][] C = MatrixMultiplication.multiply(A, B);
        check("result size is 3x4", C.length == 3 && C[0].length == 4);
    }

    // (1x3) x (3x1) = 1*4 + 2*5 + 3*6 = 32
    static void testDotProduct() {
        double[][] A = {{1, 2, 3}};
        double[][] B = {{4}, {5}, {6}};
        double[][] expected = {{32}};
        checkBoth("dot product 1x3 * 3x1", A, B, expected);
    }

    // A x I = A
    static void testIdentity() {
        double[][] A = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double[][] I = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        checkBoth("A * identity = A", A, I, A);
    }

    // A x 0 = 0
    static void testZeroMatrix() {
        double[][] A = {{1, 2}, {3, 4}};
        double[][] Z = {{0, 0}, {0, 0}};
        checkBoth("A * zero = zero", A, Z, Z);
    }

    static void testNegativeAndDecimal() {
        double[][] A = {{-1.5, 2}, {0.5, -3}};
        double[][] B = {{2, -1}, {4, 0.5}};
        double[][] expected = {{5, 2.5}, {-11, -2}};
        checkBoth("negative and decimal numbers", A, B, expected);
    }

    // (2x3) x (2x3) is not possible, so the our result must be null
    static void testIncompatibleSizes() {
        double[][] A = {{1, 2, 3}, {4, 5, 6}};
        double[][] B = {{1, 2, 3}, {4, 5, 6}};
        check("incompatible sizes return null (multiply)", MatrixMultiplication.multiply(A, B) == null);
        check("incompatible sizes return null (multiplyFast)", MatrixMultiplication.multiplyFast(A, B) == null);
    }

    static void testInvalidMatrices() {
        double[][] A = {{1, 2}, {3, 4}};
        double[][] empty = {};
        double[][] uneven = {{1, 2}, {3}};   // second row is too short
        check("null matrix returns null", MatrixMultiplication.multiply(null, A) == null);
        check("empty matrix returns null", MatrixMultiplication.multiply(empty, A) == null);
        check("uneven rows return null", MatrixMultiplication.multiply(uneven, A) == null);
    }

    static void testCanMultiply() {
        check("canMultiply(3, 3) is true", MatrixMultiplication.canMultiply(3, 3));
        check("canMultiply(3, 2) is false", !MatrixMultiplication.canMultiply(3, 2));
    }

    
    static void testBothVersionsAgree() {
        double[][] A = MatrixMultiplication.randomMatrix(50, 37);
        double[][] B = MatrixMultiplication.randomMatrix(37, 61);
        double[][] C1 = MatrixMultiplication.multiply(A, B);
        double[][] C2 = MatrixMultiplication.multiplyFast(A, B);
        check("random 50x37 * 37x61: both versions agree", equal(C1, C2));
    }
}
