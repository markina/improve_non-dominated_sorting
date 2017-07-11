package com.hybrid.sorter.old;

import com.hybrid.sorter.FactoryNonDominatedSorting;
import com.hybrid.sorter.FasterNonDominatedSorting;
import com.hybrid.sorter.Sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author mmarkina
 */
public class GenerateTestFiles {
    private static final Random rnd = new Random(366239);

    private static FactoryNonDominatedSorting fast = new FasterNonDominatedSorting();

    public static abstract class TestGenerator {
        public abstract double[][] generate(int n, int m);
        public abstract String getName();
    }

    public static class CubeGenerator extends TestGenerator {
        public double[][] generate(int n, int m) {
            double[][] rv = new double[n][m];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    rv[i][j] = rnd.nextDouble();
                }
            }
            return rv;
        }

        public String getName() {
            return "cube";
        }
    }

    public static class NFrontGenerator extends TestGenerator {
        private int nFronts;

        public NFrontGenerator(int nFronts) {
            this.nFronts = nFronts;
        }

        public double[][] generate(int n, int m) {
            double[][] rv = new double[n][m];
            for (int i = 0; i < n; ++i) {
                double sum = (double) (i % nFronts) / nFronts;
                for (int j = 1; j < m; ++j) {
                    rv[i][j] = (sum / m) + (i / nFronts == 0 ? 0 : rnd.nextGaussian());
                    sum -= rv[i][j];
                }
                rv[i][0] = sum;
            }
            return rv;
        }

        public String getName() {
            return nFronts + "fronts";
        }
    }

    private static List<double[][]> collectTests(TestGenerator generator, int n, int k) {
        double[][] input = generator.generate(n, k);
        int[] output = new int[n];
        List<double[][]> rv = new ArrayList<>();
        Sorter sorter = fast.getSorter(n, k);
        sorter.setParamAnalysis(true, (test) -> rv.add(test));
        sorter.sort(input, output);
        return rv;
    }
}
