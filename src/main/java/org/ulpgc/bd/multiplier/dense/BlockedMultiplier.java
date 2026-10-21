package org.ulpgc.bd.multiplier.dense;

import org.ulpgc.bd.matrix.DenseMatrix;
import org.ulpgc.bd.multiplier.MatrixMultiplier;

public class BlockedMultiplier implements MatrixMultiplier {

    // Tamaño del bloque. Suele ajustarse empíricamente en el benchmarking.
    private final int blockSize;

    public BlockedMultiplier(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public DenseMatrix multiply(DenseMatrix a, DenseMatrix b) {
        int n = a.size();
        DenseMatrix c = new DenseMatrix(n);

        // Bucles externos: iteran sobre los bloques
        for (int i0 = 0; i0 < n; i0 += blockSize) {
            for (int k0 = 0; k0 < n; k0 += blockSize) {
                for (int j0 = 0; j0 < n; j0 += blockSize) {

                    // Aseguramos no salirnos del tamaño de la matriz en los bordes
                    int iMax = Math.min(i0 + blockSize, n);
                    int kMax = Math.min(k0 + blockSize, n);
                    int jMax = Math.min(j0 + blockSize, n);

                    // Bucles internos: multiplicación clásica (optimizada I-K-J) dentro del bloque
                    for (int i = i0; i < iMax; i++) {
                        for (int k = k0; k < kMax; k++) {
                            double a_ik = a.get(i, k);
                            for (int j = j0; j < jMax; j++) {
                                c.add(i, j, a_ik * b.get(k, j));
                            }
                        }
                    }

                }
            }
        }
        return c;
    }

    @Override
    public String getName() {
        return "Blocked (Size: " + blockSize + ")";
    }
}