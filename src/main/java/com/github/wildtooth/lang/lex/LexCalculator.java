package com.github.wildtooth.lang.lex;

public final class LexCalculator {
  public int calculateLexValue(final String word) {
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
