package org.ulpgc.bd.multiplier.dense;

import org.ulpgc.bd.matrix.DenseMatrix;
import org.ulpgc.bd.multiplier.MatrixMultiplier;

public class ReorderedMultiplier implements MatrixMultiplier {

    @Override
    public DenseMatrix multiply(DenseMatrix a, DenseMatrix b) {
        int n = a.size();
        DenseMatrix c = new DenseMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                double a_ik = a.get(i, k);
                for (int j = 0; j < n; j++) {
                    c.add(i, j, a_ik * b.get(k, j));
                }
            }
        }
        return c;
    }

    @Override
    public String getName() {
        return "Reordered (I-K-J)";
    }
}