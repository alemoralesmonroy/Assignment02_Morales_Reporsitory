package org.ulpgc.bd.multiplier.dense;

import org.ulpgc.bd.matrix.DenseMatrix;
import org.ulpgc.bd.multiplier.MatrixMultiplier;

public class BaselineMultiplier implements MatrixMultiplier {

    @Override
    public DenseMatrix multiply(DenseMatrix a, DenseMatrix b) {
        int n = a.size();
        DenseMatrix c = new DenseMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < n; k++) {

                    sum += a.get(i, k) * b.get(k, j);
                }
                c.set(i, j, sum);
            }
        }
        return c;
    }

    @Override
    public String getName() {
        return "Baseline (I-J-K)";
    }
}