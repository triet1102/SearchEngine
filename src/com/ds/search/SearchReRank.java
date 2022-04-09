package com.ds.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchReRank {
    public static void main(String[] args) throws IOException {
        Index index = new Index();
        String file_name = "paris_test.txt";
//        String file_name = "paris.txt";

        index.buildSourceIndex(file_name);
        index.buildTFIDFPhraseVector();

        System.out.println("Input Search query: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String phrase = in.readLine();

        while (!phrase.strip().isEmpty()) {
            try {
                index.find(phrase);
            } catch (NullPointerException e) {
                System.out.println("Not found");
            }

            try {
                System.out.println("\n");
                index.find_re_rank(phrase);
            } catch (NullPointerException e) {
                System.out.println("Not found");
            }
            System.out.println("\nInput Search query: ");
            phrase = in.readLine();
        }
        System.out.println("Bye");

    }
}
