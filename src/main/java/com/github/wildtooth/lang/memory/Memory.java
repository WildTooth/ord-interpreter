package com.github.wildtooth.lang.memory;

import java.util.Arrays;

/**
 * Memory management class for the interpreter.
 * Provides a byte array with a movable pointer for storing and retrieving values.
 */
public final class Memory {
  private short pointer;
  private final byte[] memory;
  public static final int DEFAULT_SIZE = 1024;

  /**
   * Creates a memory instance with default size
   */
  public Memory() {
    this(DEFAULT_SIZE);
  }

  /**
   * Creates a memory instance with specified size
   *
   * @param size Size of the memory in bytes
   */
  public Memory(int size) {
    this.pointer = 0;
    this.memory = new byte[size];
    Arrays.fill(memory, (byte) 0);
  }

  /**
   * Increments the memory pointer
   * If pointer exceeds memory size, it wraps around to the beginning
   */
  public void incrementPointer() {
    pointer++;
    if (pointer >= memory.length) {
      pointer = 0;
    }
  }

  /**
   * Decrements the memory pointer
   * If pointer goes below 0, it wraps around to the end
   */
  public void decrementPointer() {
    pointer--;
    if (pointer < 0) {
      pointer = (short) (memory.length - 1);
    }
  }

  /**
   * Gets the current pointer position
   *
   * @return Current pointer position
   */
  public short getPointer() {
    return pointer;
  }

  /**
   * Sets the pointer to a specific address
   * Handles out-of-bounds addresses by wrapping around
   *
   * @param address Memory address to point to
   */
  public void setPointer(short address) {
    if (address < 0 || address >= memory.length) {
      address = (short) (address % memory.length);
      if (address < 0) {
        address += (short) memory.length;
      }
    }
    this.pointer = address;
  }

  /**
   * Gets the unsigned byte value at the current pointer position
   *
   * @return Unsigned byte value (0-255)
   */
  public int getCurrentUnsignedByte() {
    return Byte.toUnsignedInt(memory[pointer]);
  }

  /**
   * Gets the unsigned byte value at a specific address
   *
   * @param address Memory address to read from
   * @return Unsigned byte value (0-255)
   * @throws IndexOutOfBoundsException if address is invalid
   */
  public int getUnsignedByte(short address) {
    validateAddress(address);
    return Byte.toUnsignedInt(memory[address]);
  }

  /**
   * Sets the byte at the current pointer position
   *
   * @param value Value to set (will be truncated to byte range)
   */
  public void setCurrentByte(int value) {
    memory[pointer] = (byte) (value & 0xFF);
  }

  /**
   * Validates that an address is within the memory bounds
   *
   * @param address Address to validate
   * @throws IndexOutOfBoundsException if address is outside memory bounds
   */
  private void validateAddress(short address) {
    if (address < 0 || address >= memory.length) {
      throw new IndexOutOfBoundsException("Memory address out of bounds: " + address);
    }
  }

  /**
   * Resets all memory to zero
   */
  public void clear() {
    Arrays.fill(memory, (byte) 0);
  }

  /**
   * Gets the size of the memory
   *
   * @return Memory size in bytes
   */
  public int size() {
    return memory.length;
  }
}
