# 2D Matrix Slicing 

An image is a 2D matrix: `M[row][column]` is the color of one pixel.
Both programs slice the same image (`images-4.jpeg`, 381 rows x 806 columns) in the same 8 ways and save the results as PNG images.

## How to run

Put the image in the same folder as the code, then:

```bash
javac *.java
java MatrixSlicing images-4.jpeg          # Java slices  -> java_output/
python3 matrix_slicing.py images-4.jpeg   # NumPy slices -> numpy_output/
```

The file name can be left out (`images-4.jpeg` is the default) or replaced with another image.
Needs a JDK, and Python 3 with `numpy` and `pillow`.

## Slices

Positions are given in percent of the image size (for example `R*5/100` in Java, `R*5//100` in Python), so the code works for any image size.
R = number of rows, C = number of columns.

| # | What | NumPy | Java | Result size |
|---|---|---|---|---|
| 1 | Crop the letters "ADA" | `M[5%:63%, 15%:85%]` | `slice(M, 5%, 63%, 1, 15%, 85%, 1)` | 221 x 565 |
| 2 | Crop the letter "D" | `M[5%:63%, 39%:61%]` | `slice(M, 5%, 63%, 1, 39%, 61%, 1)` | 221 x 177 |
| 3 | Crop the stripes | `M[65%:92%, :]` | `slice(M, 65%, 92%, 1, 0, C, 1)` | 103 x 806 |
| 4 | Every 2nd pixel | `M[::2, ::2]` | `slice(M, 0, R, 2, 0, C, 2)` | 191 x 403 |
| 5 | Vertical flip | `M[::-1, :]` | `slice(M, R-1, -1, -1, 0, C, 1)` | 381 x 806 |
| 6 | Horizontal flip | `M[:, ::-1]` | `slice(M, 0, R, 1, C-1, -1, -1)` | 381 x 806 |
| 7 | Rotate 180° | `M[::-1, ::-1]` | `slice(M, R-1, -1, -1, C-1, -1, -1)` | 381 x 806 |
| 8 | Letters with steps | `M[5%:63%:2, 15%:85%:4]` | `slice(M, 5%, 63%, 2, 15%, 85%, 4)` | 111 x 142 |

In Java, `stop = -1` with a negative step means "go all the way to index 0".

## Comparison

Slicing is much easier to write in Python. NumPy has slicing built into the language: one short expression like `M[::-1, :]` or `M[10:50:2, 20:80]` does the whole job. It also supports negative indices (`M[-100:, :]`) and leaving out the start or stop.

Java has no slicing syntax. We had to write our own `slice` method that:
- counts how many rows and columns the result will have,
- checks that the indices are inside the matrix,
- creates a new matrix and copies every element with two nested loops,
- handles negative steps (for flips) by hand.

Every slice that takes one line in Python needs a call to this method in Java, with the start, stop and step written out for both rows and columns.

## Chatgpt usage

For slicing and working with images, I have asked chatgpt to implement some parts and also I have given chatgpt to solve whole solution again. I have chosen chatgpt suggestions + my own approaches since it did not consider image sizes and etc.

Advantages of partial code:
1) Helped to implement slicing and image processing in Java in efficient way
2) I have taken that parts and added to my code

Disadvantages of full code:
1) Does not consider image sizes that i have creates from own 
2) Uses advanced expression which makes code harder to understand
3) I have seen C++ solution and seen that it will be not optimal for Java also

Link for partial code suggestion:

https://chatgpt.com/share/6ac52c13-3b78-83ed-8c98-856434243e71

Link for asking doing whole task:

https://chatgpt.com/share/6ac53252-da1c-83ed-a083-70b881bb83ac




