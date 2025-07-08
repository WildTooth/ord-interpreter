package com.github.wildtooth.lang.memory;

import java.util.Arrays;

public final class Memory {
  private short pointer;
  private final byte[] memory;
  public static final int DEFAULT_SIZE = 1024;

  public Memory() {
    this(DEFAULT_SIZE);
  }

  public Memory(int size) {
    this.pointer = 0;
    this.memory = new byte[size];
    Arrays.fill(memory, (byte) 0);
  }

  public void incrementPointer() {
    pointer++;
  }

  public void decrementPointer() {
    pointer--;
  }

  public short getPointer() {
    return pointer;
  }

  public void setPointer(short address) {
    if (address < 0 || address >= memory.length) {
      address = (short) (address % memory.length);
    }
    this.pointer = address;
  }

  public int getCurrentUnsignedByte() {
    return Byte.toUnsignedInt(memory[pointer]);
  }

  public int getUnsignedByte(short address) {
    return Byte.toUnsignedInt(memory[address]);
  }

  public void setCurrentByte(int value) {
    memory[pointer] = (byte) (value & 0xFF);
  }

  public void setByte(short address, int value) {
    memory[address] = (byte) (value & 0xFF);
  }
}
