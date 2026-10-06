import sys
import time
import numpy as np

max_print_size = 10  # I have put limit for matrix size


def can_multiply(cols_a, rows_b):
    # tp multiply matrices, the number of columns in A must equal the number of rows in B from Linear Algebra we know it
    return cols_a == rows_b


def matmul_numpy(A, B):
    
    if not can_multiply(A.shape[1], B.shape[0]):
        raise ValueError(f"Cannot multiply: A has {A.shape[1]} columns " # If we can not multiply we should check it
                         f"but B has {B.shape[0]} rows")
    return A @ B



def read_line(prompt):
    # Using try catch. I want to handle the case where user wants to end program
    try:
        return input(prompt).strip()
    except EOFError:
        print("\nNo more input. Exiting.")
        sys.exit(1)


def read_positive_int(prompt):
    # It will be used for asking user to enter the size of matrix. I want to make sure that user enters a positive integer
    while True:
        text = read_line(prompt)
        try:
            value = int(text)
            if value > 0:
                return value
            print("  Please enter a number greater than 0.")
        except ValueError:
            print(f"  '{text}' is not a whole number. Try again.")


def read_choice(prompt):
    
    while True:
        text = read_line(prompt)
        if text in ("1", "2"):
            return int(text)
        print("  Please type 1 or 2.")


def read_row(prompt, count):
    # We will use this function to read a row of matrix. It will check if user entered the correct number of values and if they are numbers
    while True:
        parts = read_line(prompt).split()
        if len(parts) != count:
            print(f"  Please enter exactly {count} numbers.")
            continue
        try:
            return [float(x) for x in parts]
        except ValueError:
            print("  Only numbers are allowed. Try again.")


def read_matrix(name, rows, cols):
    # It will be used to read the matrix from user. It will call read_row for each row of the matrix
    print(f"\nEnter matrix {name} ({rows}x{cols}), one row per line, "
          f"{cols} numbers are separeted by spaces:")
    rows_list = [read_row(f"  Row {i + 1}: ", cols) for i in range(rows)]
    return np.array(rows_list)


def print_matrix(name, M):
    rows, cols = M.shape
    print(f"\nMatrix {name} ({rows}x{cols}):")
    if rows <= max_print_size and cols <= max_print_size:
        
        if np.all(M == np.round(M)):
            print(M.astype(np.int64))
        else:
            print(np.round(M, 2))
    else:
        print("  (too large to print)")




def main():
    print("Matrix Multiplication")
    print("Rule: columns of A must equal rows of B.\n")

    # 1. Ask for the sizes of the matrices
    while True:
        rows_a = read_positive_int("Rows of matrix A:    ")
        cols_a = read_positive_int("Columns of matrix A: ")
        rows_b = read_positive_int("Rows of matrix B:    ")
        cols_b = read_positive_int("Columns of matrix B: ")

        if can_multiply(cols_a, rows_b):
            break
        print(f"\nCannot multiply: A is {rows_a}x{cols_a} and B is {rows_b}x{cols_b}.")
        print(f"A has {cols_a} columns but B has {rows_b} rows; they must be equal.")
        print("Please enter the sizes again.\n")

    # We allow users to type values from themselves or fill the matrices with random whole numbers from 0 to 9. I will use read_choice function to get the choice from user
    print("\nHow do you want to fill the matrices?")
    print("  1) Type the values myself")
    print("  2) Random whole numbers from 0 to 9")
    choice = read_choice("Your choice (1 or 2): ")

    if choice == 1:
        A = read_matrix("A", rows_a, cols_a)
        B = read_matrix("B", rows_b, cols_b)
    else:
        rng = np.random.default_rng()
        A = rng.integers(0, 10, size=(rows_a, cols_a)).astype(float)
        B = rng.integers(0, 10, size=(rows_b, cols_b)).astype(float)

   
    start = time.perf_counter()
    C = matmul_numpy(A, B)
    elapsed_ms = (time.perf_counter() - start) * 1000

    # Print the matrices and the time taken for multiplication, I have used documentation to do that to make sure how much it takes for comparison with Java
    print_matrix("A", A)
    print_matrix("B", B)
    print_matrix("C = A x B", C)
    print(f"Multiplication took {elapsed_ms:.3f} ms")


if __name__ == "__main__":
    main()