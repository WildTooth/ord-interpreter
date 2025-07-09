# Documentation

# ord Language Interpreter

## Overview
The ord-interpreter is a Java application that interprets programs written in the ord programming language.
The ord language uses valid English words to encode instructions, with each word mapping to specific operations
based on certain characters they contain.

## Language Specification

### Program Structure
An ord program consists of a sequence of valid English words (using only chars of a through z). The first letter of the
first word, on a line, is used to determine the instruction to execute. Each word can contain multiple characters,
and the sum of the positional values of the characters in the word determines the value to be used in the instruction.
Multiple words per line are allowed, the first word always results in a positive value, but the following words can 
result in negative values if their length is uneven.

### Instruction Set

The Ord language maps to the following operations based on specific characters:

#### Memory Operations
- `a`: Add value to current memory cell
- `b`: Subtract value from current memory cell
- `c`: Set current memory cell to value
- `e`: Move memory pointer to specified position
- `f`: Move memory pointer forward
- `g`: Move memory pointer backward

#### Control Flow Operations
- `j`: Jump to specified instruction
- `k`: Jump to specified instruction if current memory cell is zero
- `l`: Jump to specified instruction if current memory cell is not zero
- `m`: Jump back to a previous instruction
- `n`: Jump to next instruction if current memory cell is non-zero

#### Output Operations
- `p`: Print ASCII character at specified memory address
- `q`: Print numeric value at specified memory address
- `r`: Print ASCII character at current memory address
- `s`: Print numeric value at current memory address
- `v`: Print all memory as ASCII characters
- `w`: Print all memory as numeric values

### Word-to-Instruction Mapping
The interpreter uses the first letter of the first word on each line to determine the instruction to execute.
The value of the instruction is determined by the sum of the positional values of the characters in the word,
where `a=1`, `b=2`, ..., `z=26`. If a word has an odd length, it is treated as a negative value.

### Memory Model
The interpreter maintains a memory array with a pointer that can be moved. Operations can read from and write to the
current memory cell or specified cells.

## Example Program

Here's a breakdown of the HelloWorld.ord program, which prints "Hello, World!" to the console:

```
# This is essentially a comment in ord. The thing about ord is that comments are not really supported, but can be
# simulated by making a line contain an unrecognized word. The easiest way to do this is by using a non a-z character
# at the start of the line, e.g. a #.

# The following program prints "Hello, World!" to the console. This is not necessarily the most efficient way
# nor the easiest. This is just an example of how the ord language can be used to achieve this, while showing off
# some of the features of the language.

# Increases memory cell value (by 72)
amowt

# Moves pointer forward by 1
forward

# Increases memory cell value (by 101)
axwort

# Moves pointer forward by 1
forward

# Decreases memory cell value (by 148)
brownwort

# Moves pointer forward by 1
forward

# Sets current memory cell to a value (of 108)
churrus

# Moves pointer forward by 1
forward

# Increases memory cell value (by 111)
armoury

# Moves pointer (to the fifth cell)
e

# Sets character value (to 32)
cit

# Moves pointer (to the sixth cell e=5 a=1 e+a=6)
ea

# Sets character value (to 87)
cestus

# Moves pointer forward by 1
forward

# Increases memory cell value (by 111)
armoury

# Moves pointer forward by 1
forward

# Increases memory cell value (by 114)
apyrexy

# Moves pointer (to the ninth cell)
ed

# Sets character value (to 108)
churrus

# Moves pointer forward by 1
forward

# Decreases memory cell value (by 156)
blutwurst

# Moves pointer forward by 1
forward

# Sets character value (to 33)
clr

# Outputs all the stored values as ASCII characters
valid
```
