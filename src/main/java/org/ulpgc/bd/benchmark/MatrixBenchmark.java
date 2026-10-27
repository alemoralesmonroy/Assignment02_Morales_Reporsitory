package org.ulpgc.bd.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.ulpgc.bd.matrix.CsrMatrix;
import org.ulpgc.bd.matrix.DenseMatrix;
import org.ulpgc.bd.multiplier.dense.BaselineMultiplier;
import org.ulpgc.bd.multiplier.dense.BlockedMultiplier;
import org.ulpgc.bd.multiplier.dense.ReorderedMultiplier;
import org.ulpgc.bd.multiplier.sparse.CsrMultiplier;
import org.ulpgc.bd.utils.MatrixGenerator;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class MatrixBenchmark {


    @State(Scope.Thread)
    public static class DenseState {
        @Param({"256", "512", "1024"})
        public int n;

        public DenseMatrix a;
        public DenseMatrix b;

        public BaselineMultiplier baseline = new BaselineMultiplier();
        public ReorderedMultiplier reordered = new ReorderedMultiplier();
        public BlockedMultiplier blocked = new BlockedMultiplier(16);
        @Setup(Level.Trial)
        public void setup() {
            a = MatrixGenerator.generateDense(n, 42L);
            b = MatrixGenerator.generateDense(n, 43L);
        }
    }


    @State(Scope.Thread)
    public static class SparseState {
        @Param({"512", "1024"})
        public int n;

        @Param({"0.80", "0.90", "0.99"})
        public double sparsity;

        public CsrMatrix aCsr;
        public CsrMatrix bCsr;
        public DenseMatrix aDense;
        public DenseMatrix bDense;

        public BaselineMultiplier baseline = new BaselineMultiplier();
        public CsrMultiplier csr = new CsrMultiplier();

        @Setup(Level.Trial)
        public void setup() {
            aDense = MatrixGenerator.generateSparse(n, 42L, sparsity);
            bDense = MatrixGenerator.generateSparse(n, 43L, sparsity);
            aCsr = new CsrMatrix(aDense);
            bCsr = new CsrMatrix(bDense);
        }
    }
    @Benchmark
    public DenseMatrix denseBaseline(DenseState state) {
        return state.baseline.multiply(state.a, state.b);
    }

    @Benchmark
    public DenseMatrix denseReordered(DenseState state) {
        return state.reordered.multiply(state.a, state.b);
    }

    @Benchmark
    public DenseMatrix denseBlocked(DenseState state) {
        return state.blocked.multiply(state.a, state.b);
    }
    @Benchmark
    public DenseMatrix sparseBaseline(SparseState state) {
        return state.baseline.multiply(state.aDense, state.bDense);
    }

    @Benchmark
    public CsrMatrix sparseCsr(SparseState state) {
        return state.csr.multiply(state.aCsr, state.bCsr);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MatrixBenchmark.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .resultFormat(ResultFormatType.CSV)
                .result("benchmark_results.csv")
                .build();

        new Runner(opt).run();
    }
}