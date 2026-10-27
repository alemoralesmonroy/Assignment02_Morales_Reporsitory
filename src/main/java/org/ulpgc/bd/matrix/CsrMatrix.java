package org.ulpgc.bd.matrix;

public class CsrMatrix {
    private final int n;
    private final double[] values;
    private final int[] colIndices;
    private final int[] rowPointers;
    private final int nonZeros;

    public CsrMatrix(DenseMatrix dense) {
        this.n = dense.size();
        int nzCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dense.get(i, j) != 0.0) {
                    nzCount++;
                }
            }
        }
        this.nonZeros = nzCount;
        this.values = new double[nonZeros];
        this.colIndices = new int[nonZeros];
        this.rowPointers = new int[n + 1];
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
        rowPointers[n] = currentNz;
    }
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