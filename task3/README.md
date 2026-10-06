# Matrix Multiplication

## Files

| File | What it does |
|------|--------------|
| `task3/code/Java_Solution/MatrixMultiplication.java` | Java implementation: multiplication, 
| `task3/code/Java_Solution/MatrixMultiplicationTest.java` | Unit tests for the Java implementation 
| `task3/code/Python_Solution/matmul_numpy.py` | Python implementation with NumPy

## How to run

**Java** (needs a JDK):
```bash

javac *.java
java MatrixMultiplication       
java MatrixMultiplicationTest   
```

**Python** (needs `pip install numpy`):
```bash

python3 matmul_numpy.py        
```

## How the program works (same in both languages)

1. The user enters the rows and columns of matrix A and matrix B.
2. If **columns of A ≠ rows of B**, the program prints that the matrices cannot be multiplied and asks for the sizes again.
3. The user chooses to **type the numbers** (one row per line, separated by spaces) or use **random numbers 0–9**.
4. The program prints A, B and the result C = A × B, plus the time taken. Matrices larger than 10×10 are not printed.

Wrong input (letters, zero or negative sizes) is caught, and the user is asked again.

Example:
```
Rows of matrix A: 2
Columns of matrix A: 3
Rows of matrix B: 2
Columns of matrix B: 2
Cannot multiply: A has 3 columns but B has 2 rows. They must be equal. Try again.

Rows of matrix A: 2
Columns of matrix A: 3
Rows of matrix B: 3
Columns of matrix B: 2

1) Type the numbers myself
2) Random numbers from 0 to 9
Choose 1 or 2: 1

Enter matrix A (2x3)
Row 1 (3 numbers): 1 2 3
Row 2 (3 numbers): 4 5 6

Enter matrix B (3x2)
Row 1 (2 numbers): 7 8
Row 2 (2 numbers): 9 10
Row 3 (2 numbers): 11 12

Matrix C = A x B (2x2):
58	64
139	154
```

## Java implementation

- `multiply` uses the textbook formula `C[i][j] = A[i][0]*B[0][j] + A[i][1]*B[1][j] + ...` with loops in the order i, j, k.
- `multiplyFast` computes the same result with the loops in the order i, k, j. This reads memory row by row, which is faster.
- Both return `null` if the matrices cannot be multiplied, are empty, or have rows of different lengths.

## Unit tests (Java)

Most tests check both `multiply` and `multiplyFast`:

- 2×2 result calculated by hand
- Non-square matrices (2×3 · 3×2)
- Result has the correct size (3×1 · 1×4 → 3×4)
- Dot product (1×3 · 3×1 → 1×1)
- A × identity = A
- A × zero = zero
- Negative and decimal numbers
- Incompatible sizes return `null`
- Null, empty and uneven matrices return `null`
- `canMultiply` gives true and false correctly
- Random 50×37 · 37×61 matrices: both versions give the same answer

Result: **21 / 21 tests passed.**

## Analysis



### Execution time

Random N×N matrices in my MacBook 13 M1 CPU

| N | Java `multiply` | Java `multiplyFast` | NumPy |
|---|---|---|---|
| 64   | < 1 ms   | < 1 ms | 0.06 ms |
| 128  | 2 ms     | < 1 ms | 1.0 ms |
| 256  | 23 ms    | 4 ms   | 2.4 ms |
| 512  | 213 ms   | 39 ms  | 6.0 ms |
| 1024 | 2212 ms  | 348 ms | 36.9 ms |

As we expect, numpy has better results

### Code size
 
| Part | Python| Java |
|---|---|---|
| Multiplication itself | **1 line** (`A @ B`), 3 with the size check | ~15 lines per version (3 nested loops) + 12 lines `isValid` |
| Whole program (multiply + input + output + benchmark) | 178 lines | 262 lines |

 
With NumPy, the multiplication is one operator. Most of the Python code is for reading user input. In Java, every loop, index and check must be written by hand.


## Chatgpt usage

For this task, I have used Chatgpt for only Java part since for Python part it is not important to overthink about matrix multiplication. For Java, it was good for giving advices about unit test design and suggesting faster ways of writing code.

1) It was good for giving suggestion for implementing Unit tests design
2) It was good for suggesting optimal and faster ways of writing

Link for full conversation:

https://chatgpt.com/share/6ac53316-7a98-83eb-b8e8-cd0633dfc58a

