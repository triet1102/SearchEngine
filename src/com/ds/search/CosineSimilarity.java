package com.ds.search;

public class CosineSimilarity {
    public static double cosinesimilarity(Double[] vector1, Double[] vector2) {
        double dotProduct = 0.0;
        double mag1 = 0.0;
        double mag2 = 0.0;
        double cosim = 0.0;

        for (int i = 1; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            mag1 += Math.pow(vector1[i], 2);
            mag2 += Math.pow(vector2[i], 2);
        }
        mag1 = Math.sqrt(mag1);
        mag2 = Math.sqrt(mag2);

        if (mag1 != 0.0 && mag2 != 0.0) {
            cosim = dotProduct / (mag1 * mag2);
        } else {
            return 0.0;
        }
        return cosim;
    }
}
