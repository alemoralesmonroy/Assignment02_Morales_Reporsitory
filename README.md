# Big Data - Individual Assignment 2
**Empirical Analysis of Hardware Cache Optimisations and Sparse Storage Formats**

**Author:** Alejandro Morales Monroy  
**Course:** Big Data (Data Science and Engineering)  
**University:** Universidad de Las Palmas de Gran Canaria (ULPGC)

## Overview
This repository contains the source code, benchmarks, and data analysis scripts for Individual Assignment 2. The project empirically evaluates the performance of $O(n^3)$ matrix multiplication under two paradigms:
1. **Part A (Dense Matrices):** Hardware-aware cache optimisations (Loop Reordering and Cache Blocking) to exploit L1/L2 cache locality.
2. **Part B (Sparse Matrices):** Compressed Sparse Row (CSR) storage format implementation, evaluating both execution time acceleration and the memory allocation overhead caused by the dense fill-in effect.

## Prerequisites
To compile the project and reproduce the benchmarks, ensure the following are installed:
* **Java:** JDK 17 (or newer)
* **Build Tool:** Apache Maven 3.8+
* **Python:** Python 3.10+ (for data visualization)
* **Python Packages:** `pandas`, `matplotlib`, `seaborn`

## Project Structure
* `src/main/java/`: Contains the Java source code for all matrix multiplication algorithms (Baseline, Reordered, Blocked, CSR) and JMH benchmark configurations.
* `src/test/java/`: Contains the validation tests comparing custom algorithms against a strict reference implementation.
* `plot_results.py`: Python script to parse JMH output and generate performance charts.
* `benchmark_results.csv`: Raw benchmark data exported by JMH.
* `research_report.pdf`: The final academic report detailing the findings.

## Build Instructions
To compile the Java source code, resolve JMH dependencies, and build the executable benchmark JAR, run the following Maven command from the project root:
```bash
mvn clean install
```

## Running the validation tests
Before running the performance benchmarks, verify the mathematical correctness of all implementations. The tests use small matrices ($N=100$) and assert floating-point equality with an absolute tolerance of 1e-9.
```bash
mvn test
```

## Running the benchmarks (JMH)
The benchmarks are managed by the Java Microbenchmark Harness (JMH) and include the GCProfiler to measure memory allocation (B/op).

To execute the benchmark suite and generate the CSV results file:
```bash
java -jar target/benchmarks.jar
```

## Reproducing the plots
Once the benchmark finishes and updates the benchmark_results.csv file, generate the graphical charts by executing the Python script:
```bash
python plot_results.py
```
This script will ouput three PNG files in the root directory:
* `dense_performance.png`
* `sparse_performance.png`
* `memory_allocation_1024.png`

## Generative AI Disclosure
Generative AI tools (Gemini) were used in this project exclusively to assist with regex debugging for code cleanup, structuring the Python pandas/seaborn plotting scripts, and formatting the LaTeX academic report, in compliance with the course policies.