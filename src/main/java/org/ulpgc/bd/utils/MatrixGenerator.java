package org.ulpgc.bd.utils;

import org.ulpgc.bd.matrix.DenseMatrix;
import java.util.Random;

public class MatrixGenerator {

    public static DenseMatrix generateDense(int n, long seed) {
        Random random = new Random(seed);
        DenseMatrix matrix = new DenseMatrix(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix.set(i, j, random.nextDouble() * 2.0 - 1.0);
            }
        }
        return matrix;
    }
    public static DenseMatrix generateSparse(int n, long seed, double sparsity) {
        Random random = new Random(seed);
        DenseMatrix matrix = new DenseMatrix(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (random.nextDouble() > sparsity) {
                    matrix.set(i, j, random.nextDouble() * 2.0 - 1.0);
                } else {
                    matrix.set(i, j, 0.0);
                }
            }
        }
        return matrix;
    }
}