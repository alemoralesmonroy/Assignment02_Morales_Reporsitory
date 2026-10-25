package org.ulpgc.bd.utils;

import org.ulpgc.bd.matrix.DenseMatrix;
import java.util.Random;

public class MatrixGenerator {

    // Usamos una "seed" para asegurar que la misma semilla siempre da la misma matriz.
    // Esto es obligatorio en la sección "Control the inputs" del protocolo.
    public static DenseMatrix generateDense(int n, long seed) {
        Random random = new Random(seed);
        DenseMatrix matrix = new DenseMatrix(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Generamos valores entre -1.0 y 1.0 para evitar desbordamientos rápidos
                matrix.set(i, j, random.nextDouble() * 2.0 - 1.0);
            }
        }
        return matrix;
    }

    // sparsity: porcentaje de ceros (ej. 0.90 significa 90% ceros)
    public static DenseMatrix generateSparse(int n, long seed, double sparsity) {
        Random random = new Random(seed);
        DenseMatrix matrix = new DenseMatrix(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Si el número aleatorio es mayor que la sparsity, ponemos un valor real
                if (random.nextDouble() > sparsity) {
                    matrix.set(i, j, random.nextDouble() * 2.0 - 1.0);
                } else {
                    matrix.set(i, j, 0.0);
                }
            }
        }
        return matrix;
    }
}