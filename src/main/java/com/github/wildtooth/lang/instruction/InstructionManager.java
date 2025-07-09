package com.github.wildtooth.lang.instruction;

import java.util.ArrayList;

/**
 * Manages the program's instructions, providing storage and retrieval capabilities
 */
public final class InstructionManager {
  private final ArrayList<ArrayList<String>> instructions;

  public InstructionManager() {
    this.instructions = new ArrayList<>();
  }

  /**
   * Adds an instruction to the program
   *
   * @param instruction List of strings representing an instruction and its parameters
   */
  public void addInstruction(ArrayList<String> instruction) {
    if (instruction != null && !instruction.isEmpty()) {
      instructions.add(instruction);
    }
  }

  /**
   * Get the number of instructions in the program
   *
   * @return The number of instructions
   */
  public int size() {
    return instructions.size();
  }

  /**
   * Get all instructions
   *
   * @return A defensive copy of all instructions
   */
  public ArrayList<ArrayList<String>> getInstructions() {
    return new ArrayList<>(instructions);
  }
}
