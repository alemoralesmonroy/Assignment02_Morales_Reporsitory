package org.ulpgc.bd.matrix;

import java.util.Arrays;

public class DenseMatrix {
    private final int n;
    private final double[] data;

    public DenseMatrix(int n) {
        this.n = n;
        this.data = new double[n * n];
    }

    public DenseMatrix(int n, double[] data) {
        this.n = n;
        this.data = Arrays.copyOf(data, data.length);
    }

    public int size() { return n; }

    // Mapeo 1D a 2D: índice = fila * N + columna
    public double get(int i, int j) { return data[i * n + j]; }
    public void set(int i, int j, double value) { data[i * n + j] = value; }
    public void add(int i, int j, double value) { data[i * n + j] += value; }
}