package com.example.esurat.utils;

import java.util.Random;

public class DataUtils {
    public static String generateRandomWords(int numberOfWords) {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for(int j = 0; j < word.length; j++) {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String string: randomStrings) {
            stringBuilder.append(" ");
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String toSentenceCase(String text) {
        if (text == null) return "";

        int pos = 0;
        boolean capitalize = true;
        StringBuilder sb = new StringBuilder(text);

        while (pos < sb.length()) {
            if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
            }
            else if (!capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                sb.setCharAt(pos, Character.toLowerCase(sb.charAt(pos)));
            }
            capitalize = sb.charAt(pos) == '.' || (capitalize && Character.isWhitespace(sb.charAt(pos)));
            pos++;
        }
        return sb.toString();
    }
}
