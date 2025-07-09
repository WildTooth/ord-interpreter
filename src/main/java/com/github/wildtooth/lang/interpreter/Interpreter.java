package com.github.wildtooth.lang.interpreter;

import com.github.wildtooth.lang.instruction.Instruction;
import com.github.wildtooth.lang.instruction.InstructionManager;
import com.github.wildtooth.lang.io.OutputHandler;
import com.github.wildtooth.lang.lex.LexCalculator;
import com.github.wildtooth.lang.memory.Memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Core interpreter class that processes instructions
 */
public class Interpreter {
    private final Memory memory;
    private final InstructionManager instructionManager;
    private final OutputHandler outputHandler;
    private final DictionaryChecker dictionaryChecker;

    private boolean breakOut = false;
    private int lineNum = 0;
    private boolean debugMode = false;
    public Interpreter() {
        this.memory = new Memory();
        this.instructionManager = new InstructionManager();
        this.outputHandler = new OutputHandler(memory);
        this.dictionaryChecker = new DictionaryChecker();
        dictionaryChecker.loadDictionary();
    }

    /**
     * Set debug mode
     *
     * @param debugMode true to enable debug mode, false otherwise
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        this.outputHandler.setDebugMode(debugMode);
    }

    /**
     * Executes the loaded program
     */
    public void execute() {
        ArrayList<ArrayList<String>> instructions = instructionManager.getInstructions();

        for (lineNum = 0; lineNum < instructions.size(); lineNum++) {
            ArrayList<String> line = instructions.get(lineNum);
            Instruction instruction = Instruction.UNSPECIFIED;
            int lexVal = 0;

            for (int i = 0; i < line.size(); i++) {
                if (i == 0) { // First word
                    instruction = parseInstruction(line.get(i));
                    lexVal += calculateLexValue(line.get(i));
                    if (line.size() == 1) { // If it's the only word
                        breakOut = false;
                        handleInstruction(instructions.size(), instruction, lexVal);
                        if (breakOut) {
                            break;
                        }
                    }
                    continue;
                }
                if (i == line.size() - 1) { // Last word
                    if (line.get(i).length() % 2 == 0) {
                        lexVal += calculateLexValue(line.get(i));
                    } else {
                        lexVal -= calculateLexValue(line.get(i));
                    }
                    breakOut = false;
                    handleInstruction(instructions.size(), instruction, lexVal);
                    if (breakOut) {
                        break;
                    }
                } else { // All the other words
                    if (line.get(i).length() % 2 == 0) {
                        lexVal += calculateLexValue(line.get(i));
                    } else {
                        lexVal -= calculateLexValue(line.get(i));
                    }
                }
            }

            if (debugMode) {
                System.out.println("Line " + lineNum + ": " + line + " -> Instruction: " + instruction.name() +
                        " (" + instruction.getDescription() + "), Value: " + lexVal);
            }
        }
    }

    /**
     * Calculate lex value using the appropriate method based on debug mode
     */
    private int calculateLexValue(String word) {
        return debugMode ? LexCalculator.calculateLexValueWithDebug(word) : LexCalculator.calculateLexValue(word);
    }

    /**
     * Load instructions from a string into the instruction manager
     */
    public void loadInstructions(String programText) {
        AtomicBoolean isInvalidInstruction = new AtomicBoolean(false);
        for (String line : programText.split(System.lineSeparator())) {
            String[] instructionParts = line.split(" ");
            if (instructionParts.length > 0) {
                ArrayList<String> instructionList = new ArrayList<>(Arrays.asList(instructionParts));
                instructionList.forEach(instruction -> {
                    if (!dictionaryChecker.isValidWord(instruction)) {
                        isInvalidInstruction.set(true);
                    }
                });
                if (isInvalidInstruction.get()) {
                    isInvalidInstruction.set(false);
                    continue;
                }
                instructionManager.addInstruction(instructionList);
            }
        }
    }

    /**
     * Parse the first word of an instruction to determine the operation
     */
    private Instruction parseInstruction(String instruction) {
        if (instruction.isEmpty()) {
            return Instruction.UNSPECIFIED;
        }
        return Instruction.fromLetter(instruction.charAt(0));
    }

    private void handleInstruction(int instructionsLength, Instruction instruction, int lexVal) {
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
            case JUMP_IF_ZERO:
                if (memory.getCurrentUnsignedByte() == 0) {
                    int jumpTo = (lexVal % instructionsLength);
                    if (jumpTo < 0) jumpTo += instructionsLength;
                    lineNum = jumpTo - 1; // -1 because we will increment lineNum at the end of the loop
                    breakOut = true; // Break out of the loop to jump
                }
                break;
            case JUMP_IF_NOT_ZERO:
                if (memory.getCurrentUnsignedByte() != 0) {
                    int jumpTo = (lexVal % instructionsLength);
                    if (jumpTo < 0) jumpTo += instructionsLength;
                    lineNum = jumpTo - 1; // -1 because we will increment lineNum at the end of the loop
                    breakOut = true; // Break out of the loop to jump
                }
                break;
            case JUMP_BACK:
                int jumpBack = (lineNum - lexVal);
                if (jumpBack < 0) jumpBack += instructionsLength;
                lineNum = jumpBack - 1; // -1 because we will increment lineNum at the end of the loop
                breakOut = true; // Break out of the loop to jump
                break;
            case JUMP_FORWARD:
                int jumpForward = (lineNum + lexVal);
                if (jumpForward >= instructionsLength) jumpForward -= instructionsLength;
                lineNum = jumpForward - 1; // -1 because we will increment lineNum at the end of the loop
                breakOut = true; // Break out of the loop to jump
                break;
            case PRINT_ASCII_AT:
                outputHandler.printAsciiAt(lexVal);
                break;
            case PRINT_ASCII:
                outputHandler.printAscii();
                break;
            case PRINT_VALUE_AT:
                outputHandler.printValueAt(lexVal);
                break;
            case PRINT_VALUE:
                outputHandler.printValue();
                break;
            case PRINT_ASCII_MEM_TABLE:
                outputHandler.printAsciiMemTable();
                break;
            case PRINT_VALUE_MEM_TABLE:
                outputHandler.printValueMemTable();
                break;
            default:
                if (debugMode) {
                    System.out.println("Unknown instruction, ignoring: " + instruction);
                }
        }
    }
}
