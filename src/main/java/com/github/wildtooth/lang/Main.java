package com.github.wildtooth.lang;

import com.github.wildtooth.lang.interpreter.Interpreter;
import com.github.wildtooth.lang.io.FileHandler;

import java.util.Objects;

/**
 * Main entry point for the ord-interpreter
 */
public class Main {

  public static void main(String[] args) {
    String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("HelloWorld.ord")).getPath();;

    if (args.length == 0) {
      System.out.println("Usage: java -jar ord-interpreter.jar [--debug|-d] [--file|-f <file_path>] [file_path]");
      System.out.println("Using default example program...");
    }

    boolean debugMode = false;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--debug") || args[i].equals("-d")) {
        debugMode = true;
      } else if (args[i].equals("--file") || args[i].equals("-f")) {
        if (i + 1 < args.length) {
          filePath = args[++i];
        }
      } else if (i == 0 && !args[i].startsWith("-")) {
        filePath = args[i];
      }
    }

    if (debugMode) {
      System.out.println("Debug mode: ON");
      System.out.println("Loading file: " + filePath);
    }

    String fileContents = FileHandler.readFileContents(filePath);
    if (fileContents == null) {
      System.err.println("Failed to read program file.");
      return;
    }

    Interpreter interpreter = new Interpreter();
    interpreter.setDebugMode(debugMode);

    if (debugMode) {
      System.out.println("Loading program...");
    }

    interpreter.loadInstructions(fileContents);

    if (debugMode) {
      System.out.println("Executing program...");
    }

    interpreter.execute();

    if (debugMode) {
      System.out.println("Program execution completed.");
    }
  }
}
