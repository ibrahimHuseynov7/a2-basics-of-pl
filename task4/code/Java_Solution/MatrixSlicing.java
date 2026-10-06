

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
// For slicing i have consulted Chatgpt and it has given me a road for implementing
// The link for conversation with Chatgpt:https://chatgpt.com/share/6ac52c13-3b78-83ed-8c98-856434243e71
public class MatrixSlicing {

   // It looks like slicing in Python with a step, but we have to implement it ourselves in Java.
    public static int countIndices(int start, int stop, int step) {
        if (step > 0 && start < stop) {
            return (stop - start + step - 1) / step;
        }
        if (step < 0 && start > stop) {
            return (start - stop - step - 1) / (-step);
        }
        return 0;
    }

  
    public static int[][] slice(int[][] M,
                                int rowStart, int rowStop, int rowStep,
                                int colStart, int colStop, int colStep) {
        if (M == null || M.length == 0 || rowStep == 0 || colStep == 0) {
            return null;
        }
        int newRows = countIndices(rowStart, rowStop, rowStep);
        int newCols = countIndices(colStart, colStop, colStep);
        if (newRows == 0 || newCols == 0) {
            return new int[0][0];   // empty slice
        }

        
        int lastRow = rowStart + (newRows - 1) * rowStep;
        int lastCol = colStart + (newCols - 1) * colStep;
        if (!inside(rowStart, M.length) || !inside(lastRow, M.length)
                || !inside(colStart, M[0].length) || !inside(lastCol, M[0].length)) {
            return null;
        }

        // Copy the chosen elements into a new matrix
        int[][] result = new int[newRows][newCols];
        for (int i = 0; i < newRows; i++) {
            int row = rowStart + i * rowStep;
            for (int j = 0; j < newCols; j++) {
                int col = colStart + j * colStep;
                result[i][j] = M[row][col];
            }
        }
        return result;
    }

    // Returns true if 0 <= index < size
    public static boolean inside(int index, int size) {
        return index >= 0 && index < size;
    }

    

    // Reads an image into a matrix: M[row][column] = color of the pixel as one RGB number
    public static int[][] readImage(String fileName) throws IOException {
        BufferedImage image = ImageIO.read(new File(fileName));
        int rows = image.getHeight();
        int cols = image.getWidth();
        int[][] M = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                M[r][c] = image.getRGB(c, r);  
            }
        }
        return M;
    }
     // Buffered image was done from the Chatgpt conversation
    // Saves a matrix as a PNG image
    public static void saveImage(int[][] M, String fileName) throws IOException {
        int rows = M.length;
        int cols = M[0].length;
        BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                image.setRGB(c, r, M[r][c]);
            }
        }
        ImageIO.write(image, "png", new File(fileName));
    }

  
    public static void main(String[] args) throws IOException {
        String fileName = "images-4.jpeg";
        if (args.length > 0) {
            fileName = args[0];   
        }

        int[][] M = readImage(fileName);
        int R = M.length;      // number of rows
        int C = M[0].length;   // number of columns
        System.out.println("Input image " + fileName + ": " + R + " rows x " + C + " columns");

        new File("java_output").mkdir();

         // Same numbers that I have in python code. I have choosen them randomly with trying too many numbers
        save("1_crop_letters",    slice(M, R*5/100, R*63/100, 1,  C*15/100, C*85/100, 1));  
        save("2_crop_D",          slice(M, R*5/100, R*63/100, 1,  C*39/100, C*61/100, 1));  
        save("3_crop_stripes",    slice(M, R*65/100, R*92/100, 1, 0, C, 1));               
        save("4_every_2nd",       slice(M, 0, R, 2,               0, C, 2));                
        save("5_flip_vertical",   slice(M, R-1, -1, -1,           0, C, 1));               
        save("6_flip_horizontal", slice(M, 0, R, 1,               C-1, -1, -1));          
        save("7_rotate_180",      slice(M, R-1, -1, -1,           C-1, -1, -1));           
        save("8_crop_with_step",  slice(M, R*5/100, R*63/100, 2,  C*15/100, C*85/100, 4));  

        System.out.println("Results are in the folder java_output/");
    }

    public static void save(String name, int[][] result) throws IOException {
        saveImage(result, "java_output/" + name + ".png");
        System.out.println("  " + name + ": " + result.length + " x " + result[0].length);
    }
}
