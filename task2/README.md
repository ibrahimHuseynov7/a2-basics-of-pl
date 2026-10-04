# Introduction and Initial Knowledge

Before running the code, I analyzed its structure and behavior. 

### List and Tuples

We begin with a tuple and a list, both containing the same three integer elements:

* **Tuple:** `tpl = (1, 2, 3)`
* **List:** `lst = [1, 2, 3]`



In Python, **mutability** is a key foundational concept:
* **Tuples** are **immutable** .
* **Lists** are **mutable** and act as **dynamic arrays**, allowing for runtime modifications such as insertions and deletions.

I always prefer this image while comparing them:


![Python Tuples vs Lists](python-tuples-vs-lists.jpg)

Source Link: https://techvidvan.com/tutorials/python-tuples-vs-lists/


### Sizeof operator
Regarding __sizeof__(), it is used to measure the memory size of an object. For our example, we can test whether a list or tuple is more memory efficient for a string values, and __sizeof__() reveals their baseline sizes,