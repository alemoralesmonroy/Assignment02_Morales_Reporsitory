package org.ulpgc.bd.multiplier.sparse;

import org.ulpgc.bd.matrix.CsrMatrix;
import org.ulpgc.bd.matrix.DenseMatrix;

public class CsrMultiplier {

    public CsrMatrix multiply(CsrMatrix a, CsrMatrix b) {
        int n = a.size();

        DenseMatrix c = new DenseMatrix(n);

        int[] aRowPtr = a.getRowPointers();
        int[] aColInd = a.getColIndices();
        double[] aValues = a.getValues();

        int[] bRowPtr = b.getRowPointers();
        int[] bColInd = b.getColIndices();
        double[] bValues = b.getValues();
        for (int i = 0; i < n; i++) {
            int aStart = aRowPtr[i];
            int aEnd = aRowPtr[i + 1];
            for (int kIdx = aStart; kIdx < aEnd; kIdx++) {
                int k = aColInd[kIdx];
                double a_ik = aValues[kIdx];
                int bStart = bRowPtr[k];
                int bEnd = bRowPtr[k + 1];
                for (int jIdx = bStart; jIdx < bEnd; jIdx++) {
                    int j = bColInd[jIdx];
                    double b_kj = bValues[jIdx];
                    c.add(i, j, a_ik * b_kj);
                }
            }
        }
        return new CsrMatrix(c);
    }

    public String getName() {
        return "CSR Multiplier";
    }
}

