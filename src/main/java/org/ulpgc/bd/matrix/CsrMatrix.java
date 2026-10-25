package org.ulpgc.bd.matrix;

public class CsrMatrix {
    private final int n;
    private final double[] values;
    private final int[] colIndices;
    private final int[] rowPointers;
    private final int nonZeros;

    // Constructor que convierte una DenseMatrix a formato CSR
    public CsrMatrix(DenseMatrix dense) {
        this.n = dense.size();

        // 1. Contar valores no nulos para dimensionar los arrays
        int nzCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dense.get(i, j) != 0.0) {
                    nzCount++;
                }
            }
        }
        this.nonZeros = nzCount;

        // 2. Inicializar los arrays CSR
        this.values = new double[nonZeros];
        this.colIndices = new int[nonZeros];
        this.rowPointers = new int[n + 1];

        // 3. Llenar los arrays
        int currentNz = 0;
        for (int i = 0; i < n; i++) {
            rowPointers[i] = currentNz;
            for (int j = 0; j < n; j++) {
                double val = dense.get(i, j);
                if (val != 0.0) {
                    values[currentNz] = val;
                    colIndices[currentNz] = j;
                    currentNz++;
                }
            }
        }
        // El último puntero marca el final
        rowPointers[n] = currentNz;
    }

    // Reconstruye la matriz densa original (muy útil para validar y testear)
    public DenseMatrix toDense() {
        DenseMatrix dense = new DenseMatrix(n);
        for (int i = 0; i < n; i++) {
            int start = rowPointers[i];
            int end = rowPointers[i + 1];
            for (int k = start; k < end; k++) {
                dense.set(i, colIndices[k], values[k]);
            }
        }
        return dense;
    }

    public int size() { return n; }
    public double[] getValues() { return values; }
    public int[] getColIndices() { return colIndices; }
    public int[] getRowPointers() { return rowPointers; }
    public int getNonZeros() { return nonZeros; }
}