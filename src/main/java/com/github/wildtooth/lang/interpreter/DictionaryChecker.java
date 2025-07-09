package com.github.wildtooth.lang.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles dictionary operations for validating words
 */
public class DictionaryChecker {
    private static final String DICTIONARY_FILE = "/dictionary.txt";
    private final Set<String> dictionary = new HashSet<>();
    private boolean isLoaded = false;

    /**
     * Loads the dictionary from the resources file
     */
    public void loadDictionary() {
        if (isLoaded) {
            return;
        }

        try (InputStream inputStream = DictionaryChecker.class.getResourceAsStream(DICTIONARY_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toLowerCase());
            }
            isLoaded = true;
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    /**
     * Checks if a word exists in the dictionary
     *
     * @param word The word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public Boolean isValidWord(String word) {
        if (!isLoaded) {
            loadDictionary();
        }
        return dictionary.contains(word.trim().toLowerCase());
    }
}
