package org.ulpgc.bd.multiplier.sparse;

import org.ulpgc.bd.matrix.CsrMatrix;
import org.ulpgc.bd.matrix.DenseMatrix;

public class CrsMultiplier {
    // Nótese que este multiplicador no implementa MatrixMultiplier porque
    // las firmas son diferentes (recibe y devuelve CsrMatrix)
    public CsrMatrix multiply(CsrMatrix a, CsrMatrix b) {
        int n = a.size();
        // Para simplificar la validación y el benchmark, devolvemos una matriz densa.
        // Convertir de CsrMatrix * CsrMatrix a DenseMatrix final es muy común en pipelines mixtos.
        DenseMatrix c = new DenseMatrix(n);

        int[] aRowPtr = a.getRowPointers();
        int[] aColInd = a.getColIndices();
        double[] aValues = a.getValues();

        int[] bRowPtr = b.getRowPointers();
        int[] bColInd = b.getColIndices();
        double[] bValues = b.getValues();

        // Iterar sobre las filas de A
        for (int i = 0; i < n; i++) {
            // Obtener los índices de inicio y fin para la fila 'i' en A
            int aStart = aRowPtr[i];
            int aEnd = aRowPtr[i + 1];

            // Iterar sobre los elementos no nulos de la fila 'i' de A
            for (int kIdx = aStart; kIdx < aEnd; kIdx++) {
                int k = aColInd[kIdx]; // La columna 'k' en A corresponde a la fila 'k' en B
                double a_ik = aValues[kIdx];

                // Obtener los índices de inicio y fin para la fila 'k' en B
                int bStart = bRowPtr[k];
                int bEnd = bRowPtr[k + 1];

                // Multiplicar y acumular en C
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

