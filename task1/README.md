# Introduction and Pre Knowledge

In fact, Endianness (big/little endian) is one of the essential topics in practical and theoretical CS. Recently, it is also discussed in course Computer Systems and Architecture taught by Dr. John Thomas Burns. Here is the quick overview what is mentioned:

<img src="endian_image_from_class.png" alt="Endian" width="400">

Source: <https://github.com/jzburns/csci-comp-arch/blob/master/latex/csci-6461/csci-6461-1.pdf>

## What is Endianness?

Endianness is the order in which a computer stores the bytes of a number in memory. A number bigger than one byte, such as a 4-byte integer, must be split into pieces, and the computer has to decide which piece goes first.

There are two main orders:

- **Big-endian:** the most significant byte (the "big end") is stored first, at the lowest memory address. This is the same way we write numbers on paper, left to right.
- **Little-endian:** the least significant byte (the "little end") is stored first. The number looks reversed in memory.

Example: the hexadecimal number `0x12345678` stored at memory addresses 0 to 3.

| Address | 0 | 1 | 2 | 3 |
|---|---|---|---|---|
| Big-endian | `12` | `34` | `56` | `78` |
| Little-endian | `78` | `56` | `34` | `12` |

The value is the same in both cases, and only the order of the bytes changes. The names come from *Gulliver's Travels*, where two groups fight over which end of an egg should be broken first.

Source: <https://www.ling.upenn.edu/courses/Spring_2003/ling538/Lecnotes/ADfn1.htm>

## Where each order is used?

Today most computers are little-endian, but the internet sends data in big-endian. Some processors, such as ARM, are bi-endian or they can work in either order, but almost all systems run them as little-endian. Here is very detailed conversation about ARM being bi-endian:

<https://softwareengineering.stackexchange.com/questions/165899/has-little-endian-won>

## Experiment and overall critique

On the test machine (x86-64, Python 3.11 that I have in my machine), the system is little-endian, and reading bytes in the wrong order gives a completely different number.

```python
import sys, struct

n = 0x12345678
print(sys.byteorder)                       # machine's byte order
print(n.to_bytes(4, 'big').hex(' '))       # big-endian bytes
print(n.to_bytes(4, 'little').hex(' '))    # little-endian bytes
print(struct.pack('=I', n).hex(' '))       # how our machine stores it

b = n.to_bytes(4, 'big')
print(hex(int.from_bytes(b, 'little')))    # read with the wrong order
```

Output is:

```text
little
12 34 56 78
78 56 34 12
78 56 34 12
0x78563412
```

I believe that there is problem here with endianness since the bytes `12 34 56 78` were written as big-endian but read as little-endian. For syntax, I implemented this logic:

<https://www.geeksforgeeks.org/python/to-bytes-in-python/>

### Critique

In my view, endianness still causes real problems, even though neither order is truly better. From technical side, I believe that there was never a strong technical winner. For example, little-endian creates some hardware tricks, reading a 4-byte value as 2 or 1 bytes from the same address. Moreover, two standards create hidden bugs. Code that works on one machine can give wrong numbers on another, with no error message. As the experiment above shows that the program does not crash, and it just silently reads a different number. These small bugs are the hardest to find. Nevertheless, I wondered why then little-endian is widely used nowadays and read this interesting post:

<https://www.zhihu.com/en/answer/3349097829>

The main reasons are from the post: simplicity of hardware implementation, efficiency optimization, historical continuity and compatibility. While I can understand some reasons as valid ones, I believe that choice of little-endian mainly comes from the preferences of companies rather than on pure technical numbers and reports.

## Conclusion

Endianness only decides the order of bytes, not the value, but using the wrong order silently produces wrong data. Little-endian has won inside most computers, while big-endian remains the rule on networks and in some file formats.