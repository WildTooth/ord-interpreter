package com.github.wildtooth.lang.lex;

/**
 * Calculates lexical values for words according to language rules.
 * The value is determined by summing the position values of each letter in the alphabet (a=1, b=2, etc.)
 */
public final class LexCalculator {

  private LexCalculator() {
  }

  /**
   * Calculates the lexical value of a word.
   *
   * @param word The word to calculate the value for
   * @return The lexical value
   */
  public static int calculateLexValue(final String word) {
    int value = 0;
    for (char c : word.toCharArray()) {
      char lowerCaseChar = Character.toLowerCase(c);
      if (lowerCaseChar >= 'a' && lowerCaseChar <= 'z') {
        value += (lowerCaseChar - 'a' + 1);
      }
    }
    return value;
  }

  /**
   * Debug version of calculateLexValue that prints calculation steps.
   *
   * @param word The word to calculate the value for
   * @return The lexical value
   */
  public static int calculateLexValueWithDebug(final String word) {
    int value = 0;
    for (char c : word.toCharArray()) {
      char lowerCaseChar = Character.toLowerCase(c);
      System.out.print(lowerCaseChar + " ");
      if (lowerCaseChar >= 'a' && lowerCaseChar <= 'z') {
        value += (lowerCaseChar - 'a' + 1);
      }
    }
    System.out.println(System.lineSeparator());
    System.out.println("Lex value for word '" + word + "' is: " + value);
    return value;
  }
}
