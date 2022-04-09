package com.ds.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Index {
    Map<Integer, String> sources;
    HashMap<String, HashSet<Integer>> index;

    List<Double[]> TFIDFPhraseVector;
    Map<String, Double> wordsIDF;

    Index() {
        sources = new HashMap<Integer, String>();
        index = new HashMap<String, HashSet<Integer>>();

        TFIDFPhraseVector = new ArrayList<Double[]>();
        wordsIDF = new HashMap<String, Double>();
    }

    public void buildSourceIndex(String filename) {
        int line_number = 0;
        try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
            String ln;
            while ((ln = file.readLine()) != null) {
                sources.put(line_number, ln);
                String[] words = ln.split("\\W+");

                for (String word : words) {
                    word = word.toLowerCase();
                    if (!index.containsKey(word))
                        index.put(word, new HashSet<Integer>());
                    index.get(word).add(line_number);
                }
                line_number++;
            }
        } catch (IOException ex) {
            System.out.println("File " + filename + " not found.");
        }
    }

    public void buildTFIDFPhraseVector() {
        double tf, idf;
        int n_documents = sources.size();

        for (String phrase : sources.values()) {
            Double[] TFIDFVector = new Double[index.keySet().size()];
            int idx = 0;
            for (String word : index.keySet()) {
                tf = TFCalculator.tf(phrase, word);
                idf = Math.log((1 + n_documents) / (1 + index.get(word).size()));

                if (!wordsIDF.containsKey(word))
                    wordsIDF.put(word, idf);
                TFIDFVector[idx] = tf * idf;
                idx++;
            }
            TFIDFPhraseVector.add(TFIDFVector);
        }
    }

    public void find_re_rank(String phrase) throws NullPointerException {
        String[] words = phrase.split("\\W+");
        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        for (String word : words) {
            res.retainAll(index.get(word.toLowerCase()));
        }

        if (res.size() == 0) {
            System.out.println("Not found");
            return;
        }

        double tf, idf;
        Double[] phraseTFIDFVector = new Double[index.keySet().size()];
        int idx = 0;
        for (String word : index.keySet()) {
            tf = TFCalculator.tf(phrase, word);
            idf = wordsIDF.get(word);
            phraseTFIDFVector[idx] = tf * idf;
            idx++;
        }

        List<Integer> res_reranked = new ArrayList<Integer>();
        for (int num : res) {
            if (res_reranked.size() == 0) {
                res_reranked.add(num);
            } else {
                int n = res_reranked.size();
                for (int i = 0; i < n; i++) {
                    double score1 = CosineSimilarity.cosinesimilarity(phraseTFIDFVector, TFIDFPhraseVector.get(res_reranked.get(i)));
                    double score2 = CosineSimilarity.cosinesimilarity(phraseTFIDFVector, TFIDFPhraseVector.get(num));

                    if (score2 >= score1) {
                        res_reranked.add(i, num);
                        break;
                    }
                }

                if (!res_reranked.contains(num)) {
                    res_reranked.add(num);
                }
            }
        }

        System.out.println("(Re-ranked) Found in: ");
        for (int num : res_reranked) {
            System.out.println("Line number: " + num + " => " + sources.get(num));
            System.out.println(CosineSimilarity.cosinesimilarity(phraseTFIDFVector, TFIDFPhraseVector.get(num)));
        }
    }

    public void find(String phrase) throws NullPointerException {
        String[] words = phrase.split("\\W+");
        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        for (String word : words) {
            res.retainAll(index.get(word.toLowerCase()));
        }

        if (res.size() == 0) {
            System.out.println("Not found");
            return;
        }

        System.out.println("Found in: ");
        for (int num : res) {
            System.out.println("Line number: " + num + " => " + sources.get(num));
        }
    }
}
