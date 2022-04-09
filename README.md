# Search Engine

## Introduction

The goal of this project is to perform a binary search (with re-rank option) in Java. Given a file which contains lines
of text and an input query, retrieve all lines in text file which contain all words in the query (AND operation).
Optional re-rank function is provided to re-rank the result based on cosine-similarity between TF-IDF vectors.

## Implementation

Classes:

* CosineSimilarity: Calculate cosine similarity.
* TFCalculator: Calculate TF
* Index:
    * Build Inverted Index.
    * Create and store TF-IDF vector of lines in memory (may consume much memory if the text file is large).
    * Perform binary search without re-rank functionality.
    * Perform binary search with re-rank functionality.
* SearchReRank: Main class to run application.

## Limitations

* split("\\\W+") works well for English but not for French (e.g. remove "é" character) => Need to find better word
  tokenizer.

## Todo

* Implement text preprocessing (e.g. lemmatization, stemming, remove stop words, etc.).
* Try bm25 algo.

