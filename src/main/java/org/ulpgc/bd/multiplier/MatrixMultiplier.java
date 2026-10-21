package org.ulpgc.bd.multiplier;

import org.ulpgc.bd.matrix.DenseMatrix;

public interface MatrixMultiplier {
    // Todos nuestros algoritmos recibirán dos matrices y devolverán la resultante
    DenseMatrix multiply(DenseMatrix a, DenseMatrix b);

    // Un métod para obtener el nombre del algoritmo y usarlo en los CSV de resultados
    String getName();
}