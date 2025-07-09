package com.github.wildtooth.lang.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * File utility class for handling program file operations
 */
public class FileHandler {

    /**
     * Reads the contents of a file
     *
     * @param file The file to read
     * @return The file contents as a string, or null if reading failed
     */
    public static String readFileContents(File file) {
        StringBuilder fileContents = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine()).append(System.lineSeparator());
            }
            return fileContents.toString();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return null;
        }
    }

    /**
     * Reads the contents of a file from a path string
     *
     * @param path Path to the file
     * @return The file contents as a string, or null if reading failed
     */
    public static String readFileContents(String path) {
        return readFileContents(new File(path));
    }
}
