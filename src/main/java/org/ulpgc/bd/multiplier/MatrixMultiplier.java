package org.ulpgc.bd.multiplier;

import org.ulpgc.bd.matrix.DenseMatrix;

public interface MatrixMultiplier {
    DenseMatrix multiply(DenseMatrix a, DenseMatrix b);
    String getName();
}