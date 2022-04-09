package com.ds.search;

public class TFCalculator {
    public static double tf(String phrase, String word) {
        double count = 0;
        String[] phrase_words = phrase.split("\\W+");
        for (String sentence_word : phrase_words) {
            if (word.equals(sentence_word.toLowerCase()))
                count++;
        }
        return count / phrase_words.length;
    }
}
