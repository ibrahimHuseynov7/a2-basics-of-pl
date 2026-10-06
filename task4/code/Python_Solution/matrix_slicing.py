
import os
import sys
import numpy as np
from PIL import Image


def load_image(file_name):
    # I have converted it to RGB as we have done in computer graphics course in bachelor
    return np.array(Image.open(file_name).convert("RGB")).astype(int)


def save_image(M, file_name):
    Image.fromarray(M.astype(np.uint8)).save(file_name)



file_name = "images-4.jpeg"
if len(sys.argv) > 1:
    file_name = sys.argv[1]   

M = load_image(file_name)
R = M.shape[0]   # number of rows
C = M.shape[1]   # number of columns
print("Input image", file_name + ":", R, "rows x", C, "columns")




# Positions are given in percent of the image size . I have choosen them randomly with trying too many numbers
top = R * 5 // 100
bottom = R * 63 // 100
left = C * 15 // 100
right = C * 85 // 100

# Name and the NumPy result of each slice. The result is a new matrix, which is a view of the original matrix M. For this I have used documentation and advice from Chatgpt:
# The link for conversation with Chatgpt: https://chatgpt.com/share/6ac52abc-bb00-83ed-9e4a-8d7b736b8ffa
slices = [
    ["1_crop_letters",    M[top:bottom, left:right]],              # letters "ADA"
    ["2_crop_D",          M[top:bottom, C*39//100:C*61//100]],     # letter "D"
    ["3_crop_stripes",    M[R*65//100:R*92//100, :]],              # blue stripes
    ["4_every_2nd",       M[::2, ::2]],                            # every 2nd row and column
    ["5_flip_vertical",   M[::-1, :]],                             # rows backwards
    ["6_flip_horizontal", M[:, ::-1]],                             # columns backwards
    ["7_rotate_180",      M[::-1, ::-1]],                          # both backwards
    ["8_crop_with_step",  M[top:bottom:2, left:right:4]],          # letters, every 2nd row, every 4th column
]

os.makedirs("numpy_output", exist_ok=True)
for name, result in slices:
    save_image(result, "numpy_output/" + name + ".png")
    print("  " + name + ":", result.shape[0], "x", result.shape[1])

print("Results are in the folder numpy_output/")