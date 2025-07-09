package com.github.wildtooth.lang.io;

import com.github.wildtooth.lang.memory.Memory;

/**
 * Handles all output operations for the interpreter
 */
public class OutputHandler {
    private final Memory memory;
    private boolean debugMode = false;

    /**
     * Creates a new OutputHandler
     *
     * @param memory Memory instance to read from
     */
    public OutputHandler(Memory memory) {
        this.memory = memory;
    }

    /**
     * Set debug mode
     *
     * @param debugMode true to enable debug mode, false otherwise
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Print ASCII character at specified memory address
     *
     * @param address Memory address to read from
     */
    public void printAsciiAt(int address) {
        if (address >= 0 && address < memory.size()) {
            if (debugMode) {
                System.out.print("ASCII at address " + address + ": ");
            }
            System.out.println((char) memory.getUnsignedByte((short) address));
        } else if (debugMode) {
            System.out.println("Invalid memory address for printAsciiAt: " + address);
        }
    }

    /**
     * Print ASCII character at current memory pointer
     */
    public void printAscii() {
        if (debugMode) {
            System.out.print("ASCII at current address: ");
        }
        System.out.println((char) memory.getCurrentUnsignedByte());
    }

    /**
     * Print numeric value at specified memory address
     *
     * @param address Memory address to read from
     */
    public void printValueAt(int address) {
        if (address >= 0 && address < memory.size()) {
            if (debugMode) {
                System.out.print("Value at address " + address + ": ");
            }
            System.out.println(memory.getUnsignedByte((short) address));
        } else if (debugMode) {
            System.out.println("Invalid memory address for printValueAt: " + address);
        }
    }

    /**
     * Print numeric value at current memory pointer
     */
    public void printValue() {
        if (debugMode) {
            System.out.print("Value at current address: ");
        }
        System.out.println(memory.getCurrentUnsignedByte());
    }

    /**
     * Print all memory as ASCII characters
     */
    public void printAsciiMemTable() {
        if (debugMode) {
            System.out.println("Memory as ASCII:");
        }
        for (int i = 0; i < memory.size(); i++) {
            System.out.print((char) memory.getUnsignedByte((short) i));
        }
        System.out.println();
    }

    /**
     * Print all memory as numeric values
     */
    public void printValueMemTable() {
        if (debugMode) {
            System.out.println("Memory as values:");
        }
        for (int i = 0; i < memory.size(); i++) {
            System.out.print(memory.getUnsignedByte((short) i) + " ");
        }
        System.out.println();
    }
}
