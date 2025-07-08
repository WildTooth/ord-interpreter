package com.github.wildtooth.lang;

import com.github.wildtooth.lang.instruction.Instruction;
import com.github.wildtooth.lang.instruction.InstructionManager;
import com.github.wildtooth.lang.lex.LexCalculator;
import com.github.wildtooth.lang.memory.Memory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

  private static boolean breakOut = false;
  private static int lineNum = 0;

  public static void main(String[] args) {
    File file = new File("C:\\Users\\vildt\\IdeaProjects\\ord-interpreter\\src\\main\\resources\\HelloWorld.lex");

    // File contents reading
    StringBuilder fileContents = new StringBuilder();

    try (java.util.Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        fileContents.append(scanner.nextLine()).append(System.lineSeparator());
      }
    } catch (java.io.FileNotFoundException e) {
      System.err.println("File not found: " + file.getAbsolutePath());
      return;
    }

    InstructionManager instructionManager = new InstructionManager();

    {
      ArrayList<String[]> instructions = new ArrayList<>();
      for (String line : fileContents.toString().split(System.lineSeparator())) {
        instructions.add(line.split(" "));
      }

      for (String[] instruction : instructions) {
        if (instruction.length > 0) {
          instructionManager.addInstruction(new ArrayList<>(Arrays.asList(instruction)));
        }
      }
    }

    ArrayList<ArrayList<String>> instructions = instructionManager.getInstructions();

    LexCalculator lex = new LexCalculator();
    Memory memory = new Memory();

    for (lineNum = 0; lineNum < instructions.size(); lineNum++) {
      ArrayList<String> line = instructions.get(lineNum);
      Instruction instruction = Instruction.UNSPECIFIED;
      int lexVal = 0;

      for (int i = 0; i < line.size(); i++) {
        if (i == 0) { // First word
          instruction = parseInstruction(line.get(i));
          lexVal += lex.calculateLexValue(line.get(i));
          if (line.size() == 1) { // If it's the only word
            breakOut = false;
            handleInstruction(memory, instructions.size(), instruction, lexVal);
            if (breakOut) {
              break;
            }
          }
          continue;
        }
        if (i == line.size() - 1) { // Last word
          if (line.get(i).length() % 2 == 0) {
            lexVal += lex.calculateLexValue(line.get(i));
          } else {
            lexVal -= lex.calculateLexValue(line.get(i));
          }
          breakOut = false;
          handleInstruction(memory, instructions.size(), instruction, lexVal);
          if (breakOut) {
            break;
          }
        } else { // All the other words
          if (line.get(i).length() % 2 == 0) {
            lexVal += lex.calculateLexValue(line.get(i));
          } else {
            lexVal -= lex.calculateLexValue(line.get(i));
          }
        }
      }
    }

  }

  private static void handleInstruction(Memory memory, int instructionsLength,
                                        Instruction instruction, int lexVal) {
    switch (instruction) {
      case ADD:
        memory.setCurrentByte(memory.getCurrentUnsignedByte() + lexVal);
        break;
      case SUBTRACT:
        memory.setCurrentByte(memory.getCurrentUnsignedByte() - lexVal);
        break;
      case SET:
        memory.setCurrentByte(lexVal);
        break;
      case MOVE_POINTER_TO:
        memory.setPointer((short) lexVal);
        break;
      case MOVE_POINTER_FORWARD:
        memory.incrementPointer();
        break;
      case MOVE_POINTER_BACK:
        memory.decrementPointer();
        break;
      case JUMP_TO_INSTRUCTION:
        int result = (lexVal % instructionsLength);
        if (result < 0) result += instructionsLength;
        lineNum = result - 1;
        breakOut = true; // Break out of the loop to jump
        break;
      case PRINT_ASCII_AT:
        if (lexVal >= 0 && lexVal < Memory.DEFAULT_SIZE) {
          System.out.println((char) memory.getUnsignedByte((short) lexVal));
        }
        break;
      case PRINT_ASCII:
        System.out.println((char) memory.getCurrentUnsignedByte());
        break;
      case PRINT_VALUE_AT:
        if (lexVal >= 0 && lexVal < Memory.DEFAULT_SIZE) {
          System.out.println(memory.getUnsignedByte((short) lexVal));
        }
        break;
      case PRINT_VALUE:
        System.out.println(memory.getCurrentUnsignedByte());
        break;
      case PRINT_ASCII_MEM_TABLE:
        for (int i = 0; i < Memory.DEFAULT_SIZE; i++) {
          System.out.print((char) memory.getUnsignedByte((short) i));
        }
        System.out.println();
        break;
      case PRINT_VALUE_MEM_TABLE:
        for (int i = 0; i < Memory.DEFAULT_SIZE; i++) {
          System.out.print(memory.getUnsignedByte((short) i) + " ");
        }
        System.out.println();
        break;
      default:
        // Do nothing for unspecified instructions
    }
  }

  private static Instruction parseInstruction(String instruction) {
    char firstChar = Character.toLowerCase(instruction.charAt(0));
    switch (firstChar) {
      // a-h are reserved for memory operations
      case 'a':
        return Instruction.ADD;
      case 'b':
        return Instruction.SUBTRACT;
      case 'c':
        return Instruction.SET;
      case 'e':
        return Instruction.MOVE_POINTER_TO;
      case 'f':
        return Instruction.MOVE_POINTER_FORWARD;
      case 'g':
        return Instruction.MOVE_POINTER_BACK;
      // i-n are reserved for control flow operations
      case 'j':
        return Instruction.JUMP_TO_INSTRUCTION;
      // o-z are reserved for output operations
      case 'p':
        return Instruction.PRINT_ASCII_AT;
      case 'q':
        return Instruction.PRINT_VALUE_AT;
      case 'r':
        return Instruction.PRINT_ASCII;
      case 's':
        return Instruction.PRINT_VALUE;
      case 'v':
        return Instruction.PRINT_ASCII_MEM_TABLE;
      case 'w':
        return Instruction.PRINT_VALUE_MEM_TABLE;
      default:
        return Instruction.UNSPECIFIED;
    }
  }
}
