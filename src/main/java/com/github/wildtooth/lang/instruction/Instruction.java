package com.github.wildtooth.lang.instruction;

/**
 * Enumeration of all supported language instructions
 */
public enum Instruction {
  // Memory operations (a-h)
  ADD('a', "Add value to current memory cell"),
  SUBTRACT('b', "Subtract value from current memory cell"),
  SET('c', "Set current memory cell to value"),
  MOVE_POINTER_TO('e', "Move memory pointer to specified position"),
  MOVE_POINTER_FORWARD('f', "Move memory pointer forward"),
  MOVE_POINTER_BACK('g', "Move memory pointer backward"),

  // Control flow operations (i-n)
  JUMP_TO_INSTRUCTION('j', "Jump to specified instruction"),
  JUMP_IF_ZERO('k', "Jump to specified instruction if current memory cell is zero"),
  JUMP_IF_NOT_ZERO('l', "Jump to specified instruction if current memory cell is not zero"),
  JUMP_BACK('m', "Jump back to a previous instruction"),
  JUMP_FORWARD('n', "Jump to next instruction if current memory cell is non-zero"),

  // Output operations (o-z)
  PRINT_ASCII_AT('p', "Print ASCII character at specified memory address"),
  PRINT_VALUE_AT('q', "Print numeric value at specified memory address"),
  PRINT_ASCII('r', "Print ASCII character at current memory address"),
  PRINT_VALUE('s', "Print numeric value at current memory address"),
  PRINT_ASCII_MEM_TABLE('v', "Print all memory as ASCII characters"),
  PRINT_VALUE_MEM_TABLE('w', "Print all memory as numeric values"),

  // Special - No Operation - Might as well be a comment
  UNSPECIFIED('\0', "Unspecified instruction");

  private final char letter;
  private final String description;

  Instruction(char letter, String description) {
    this.letter = letter;
    this.description = description;
  }

  /**
   * Get the description of this instruction
   *
   * @return The instruction's description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Find an instruction by its letter identifier
   *
   * @param letter The letter to search for
   * @return The matching instruction, or UNSPECIFIED if not found
   */
  public static Instruction fromLetter(char letter) {
    char lowerCase = Character.toLowerCase(letter);
    for (Instruction instruction : values()) {
      if (instruction.letter == lowerCase) {
        return instruction;
      }
    }
    return UNSPECIFIED;
  }
}
