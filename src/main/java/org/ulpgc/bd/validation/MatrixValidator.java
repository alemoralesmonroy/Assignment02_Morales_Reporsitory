package org.ulpgc.bd.validation;

import org.ulpgc.bd.matrix.DenseMatrix;
import org.ulpgc.bd.multiplier.MatrixMultiplier;
import org.ulpgc.bd.multiplier.dense.BaselineMultiplier;
import org.ulpgc.bd.multiplier.dense.BlockedMultiplier;
import org.ulpgc.bd.multiplier.dense.ReorderedMultiplier;
import org.ulpgc.bd.utils.MatrixGenerator;

public class MatrixValidator {

    // Tolerancia absoluta. 1e-9 es un margen estricto pero seguro para
    // operaciones de suma/multiplicación acumulada con valores entre -1 y 1.
    private static final double EPSILON = 1e-9;

    public static void main(String[] args) {
        System.out.println("--- Starting Correctness Validation ---");
        int n = 100; // "Test small matrices"
        long seed = 42L;

        System.out.println("Generating dense matrices (N=" + n + ")...");
        DenseMatrix a = MatrixGenerator.generateDense(n, seed);
        DenseMatrix b = MatrixGenerator.generateDense(n, seed + 1);

        // 1. Obtener matriz de referencia con el algoritmo base
        MatrixMultiplier baseline = new BaselineMultiplier();
        DenseMatrix expected = baseline.multiply(a, b);
        System.out.println("Reference calculated using: " + baseline.getName());

        // 2. Validar Reordered (I-K-J)
        MatrixMultiplier reordered = new ReorderedMultiplier();
        DenseMatrix actualReordered = reordered.multiply(a, b);
        boolean reorderedPass = compareMatrices(expected, actualReordered);
        System.out.println(reordered.getName() + " Validation: " + (reorderedPass ? "PASS" : "FAIL"));

        // 3. Validar Blocked (Tamaño de bloque: 16)
        MatrixMultiplier blocked = new BlockedMultiplier(16);
        DenseMatrix actualBlocked = blocked.multiply(a, b);
        boolean blockedPass = compareMatrices(expected, actualBlocked);
        System.out.println(blocked.getName() + " Validation: " + (blockedPass ? "PASS" : "FAIL"));

        System.out.println("--- Validation Complete ---");
    }

    private static boolean compareMatrices(DenseMatrix expected, DenseMatrix actual) {
        if (expected.size() != actual.size()) return false;

        int n = expected.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double diff = Math.abs(expected.get(i, j) - actual.get(i, j));
                if (diff > EPSILON) {
                    System.err.printf("Mismatch at [%d,%d]: Expected %f but got %f (diff: %e)%n",
                            i, j, expected.get(i, j), actual.get(i, j), diff);
                    return false;
                }
            }
        }
        return true;
    }
}