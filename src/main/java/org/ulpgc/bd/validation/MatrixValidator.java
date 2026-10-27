package org.ulpgc.bd.validation;

import org.ulpgc.bd.matrix.CsrMatrix;
import org.ulpgc.bd.matrix.DenseMatrix;
import org.ulpgc.bd.multiplier.MatrixMultiplier;
import org.ulpgc.bd.multiplier.dense.BaselineMultiplier;
import org.ulpgc.bd.multiplier.dense.BlockedMultiplier;
import org.ulpgc.bd.multiplier.dense.ReorderedMultiplier;
import org.ulpgc.bd.multiplier.sparse.CsrMultiplier;
import org.ulpgc.bd.utils.MatrixGenerator;

public class MatrixValidator {

    private static final double EPSILON = 1e-9;

    public static void main(String[] args) {
        System.out.println("--- Starting Correctness Validation ---");
        int n = 100;
        long seed = 42L;
        System.out.println("\n[Part A] Generating dense matrices (N=" + n + ")...");
        DenseMatrix a = MatrixGenerator.generateDense(n, seed);
        DenseMatrix b = MatrixGenerator.generateDense(n, seed + 1);

        MatrixMultiplier baseline = new BaselineMultiplier();
        DenseMatrix expected = baseline.multiply(a, b);
        System.out.println("Reference calculated using: " + baseline.getName());

        MatrixMultiplier reordered = new ReorderedMultiplier();
        System.out.println(reordered.getName() + " Validation: " +
                (compareMatrices(expected, reordered.multiply(a, b)) ? "PASS" : "FAIL"));

        MatrixMultiplier blocked = new BlockedMultiplier(16);
        System.out.println(blocked.getName() + " Validation: " +
                (compareMatrices(expected, blocked.multiply(a, b)) ? "PASS" : "FAIL"));
        double sparsity = 0.90;
        System.out.println("\n[Part B] Generating sparse matrices (N=" + n + ", Sparsity=" + (sparsity*100) + "%)...");
        DenseMatrix aSparseDense = MatrixGenerator.generateSparse(n, seed, sparsity);
        DenseMatrix bSparseDense = MatrixGenerator.generateSparse(n, seed + 1, sparsity);
        DenseMatrix expectedSparse = baseline.multiply(aSparseDense, bSparseDense);
        CsrMatrix aCsr = new CsrMatrix(aSparseDense);
        CsrMatrix bCsr = new CsrMatrix(bSparseDense);
        CsrMultiplier csrMultiplier = new CsrMultiplier();
        CsrMatrix actualCsr = csrMultiplier.multiply(aCsr, bCsr);
        DenseMatrix actualCsrDense = actualCsr.toDense();
        System.out.println(csrMultiplier.getName() + " Validation: " +
                (compareMatrices(expectedSparse, actualCsrDense) ? "PASS" : "FAIL"));

        System.out.println("\n--- Validation Complete ---");
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