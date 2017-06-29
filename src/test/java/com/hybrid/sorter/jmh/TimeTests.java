package com.hybrid.sorter.jmh;

import com.hybrid.sorter.BOSNonDominatedSorting;
import com.hybrid.sorter.FactoryNonDominatedSorting;
import com.hybrid.sorter.FasterNonDominatedSorting;
import com.hybrid.sorter.HybridNonDominatedSorting;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static com.hybrid.sorter.jmh.DataTests.*;
import static com.hybrid.sorter.jmh.UtilsTests.findFrontIndices;
import static org.junit.Assert.assertTrue;


/**
 * @author mmarkina
 */
public class TimeTests {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void simpleTestBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputSimpleTest, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void simpleTestFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputSimpleTest, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void simpleTestHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputSimpleTest, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void otherSimpleTestBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputOtherSimpleTest, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void otherSimpleTestFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputOtherSimpleTest, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void otherSimpleTestHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputOtherSimpleTest, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoUnequal1DPointsOrderedDecreasingBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoUnequal1DPointsOrderedDecreasing, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoUnequal1DPointsOrderedDecreasingFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoUnequal1DPointsOrderedDecreasing, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoUnequal1DPointsOrderedDecreasingHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoUnequal1DPointsOrderedDecreasing, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoUnequal1DPointsOrderedIncreasingBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoUnequal1DPointsOrderedIncreasing, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoUnequal1DPointsOrderedIncreasingFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoUnequal1DPointsOrderedIncreasing, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoUnequal1DPointsOrderedIncreasingHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoUnequal1DPointsOrderedIncreasing, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoEqual1DPointsBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoEqual1DPoints, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoEqual1DPointsFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoEqual1DPoints, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoEqual1DPointsHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoEqual1DPoints, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoIncomparable2DPointsBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoIncomparable2DPoints, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoIncomparable2DPointsFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoIncomparable2DPoints, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void twoIncomparable2DPointsHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputTwoIncomparable2DPoints, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void two2DPointsDominatedIncreasinglyBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputTwo2DPointsDominatedIncreasingly, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void two2DPointsDominatedIncreasinglyFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputTwo2DPointsDominatedIncreasingly, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void two2DPointsDominatedIncreasinglyHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputTwo2DPointsDominatedIncreasingly, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void five2DPointsDominatedDecreasinglyBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputFive2DPointsDominatedDecreasingly, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void five2DPointsDominatedDecreasinglyFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputFive2DPointsDominatedDecreasingly, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void five2DPointsDominatedDecreasinglyHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputFive2DPointsDominatedDecreasingly, hybridFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void two2DPointsDominatedDecreasinglyBOS() throws InterruptedException {
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        int[] output = findFrontIndices(inputTwo2DPointsDominatedDecreasingly, bosFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void two2DPointsDominatedDecreasinglyFast() throws InterruptedException {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        int[] output = findFrontIndices(inputTwo2DPointsDominatedDecreasingly, fastFactory);
        assertTrue(output.length > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void two2DPointsDominatedDecreasinglyHybrid() throws InterruptedException {
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();
        int[] output = findFrontIndices(inputTwo2DPointsDominatedDecreasingly, hybridFactory);
        assertTrue(output.length > 0);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TimeTests.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
