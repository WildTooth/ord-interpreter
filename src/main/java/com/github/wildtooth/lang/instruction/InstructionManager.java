package com.github.wildtooth.lang.instruction;

import java.util.ArrayList;

public final class InstructionManager {
  private final ArrayList<ArrayList<String>> instructions;

  public InstructionManager() {
    this.instructions = new ArrayList<>();
  }

  public void addInstruction(ArrayList<String> instruction) {
    if (instruction != null && !instruction.isEmpty()) {
      instructions.add(instruction);
    }
  }

  public ArrayList<ArrayList<String>> getInstructions() {
    return new ArrayList<>(instructions);
  }
}
