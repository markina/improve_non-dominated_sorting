import units.Reader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import static units.Assert.check;

public class AnalysisTests {
    private static final Random rnd = new Random(366239);
    private static final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    static {
        bean.setThreadCpuTimeEnabled(true);
    }

    private static FactoryNonDominatedSorting fast, bos, hybrid;

    static abstract class TestGenerator {
        public abstract double[][] generate(int n, int m);
        public abstract String getName();
    }

    static class CubeGenerator extends TestGenerator {
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

    static class NFrontGenerator extends TestGenerator {
        private int nFronts;
        public NFrontGenerator(int nFronts) {
            this.nFronts = nFronts;
        }
        public double[][] generate(int n, int m) {
            double[][] rv = new double[n][m];
            for (int i = 0; i < n; ++i) {
                double sum = 0;
                for (int j = 1; j < m; ++j) {
                    rv[i][j] = rnd.nextDouble();
                    sum += rv[i][j];
                }
                rv[i][0] = (double) rnd.nextInt(nFronts) / nFronts - sum;
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

    private static double timing(FactoryNonDominatedSorting sorterFactory, double[][] input) {
        int[] rv = new int[input.length];
        Sorter sorter = sorterFactory.getSorter(input.length, input[0].length);
        int iterations = 1;
        while (true) {
            long start = bean.getCurrentThreadUserTime();
            for (int i = 0; i < iterations; i++) {
                sorter.sort(input, rv);
            }
            long end = bean.getCurrentThreadUserTime();
            if (end - start > 100000000) {
                return (double) (end - start) / iterations;
            } else {
                iterations *= 2;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length < 4) {
            throw new Exception("Args: (cube|Xfronts) N M threads");
        }

        String name = args[0];
        TestGenerator generator;
        if (name.equals("cube")) {
            generator = new CubeGenerator();
        } else if (name.endsWith("fronts")) {
            generator = new NFrontGenerator(Integer.parseInt(name.substring(0, name.length() - "fronts".length())));
        } else {
            throw new Exception("Unknown generator " + name);
        }

        int N = Integer.parseInt(args[1]);
        int M = Integer.parseInt(args[2]);
        int threads = Integer.parseInt(args[3]);

        fast = new FasterNonDominatedSorting();
        bos  = new BOSNonDominatedSorting();
        hybrid  = new BOSNonDominatedSorting();

        List<double[][]> tests = collectTests(generator, N, M);

        System.out.println(tests.size() + " tests generated.");
        System.out.print("Warming up at first 10 tests [");

        for (int j = 0; j < tests.size() && j < 10; ++j) {
            double[][] test = tests.get(j);
            double fastTime = timing(fast, test);
            double bosTime = timing(bos, test);
            double hybridTime = timing(hybrid, test);
            if(fastTime > hybridTime && bosTime > hybridTime) {
                System.out.print('h');
            } else if(hybridTime > fastTime && bosTime > fastTime) {
                System.out.print('f');
            } else {
                System.out.print('b');
            }
//            System.out.print(fastTime > bosTime ? '+' : '-');
        }
        System.out.println("]");

        System.out.println("Now measuring");

        try (PrintWriter out = new PrintWriter(generator.getName() + "_" + N + "_" + M + "_result.txt")) {
            ExecutorService service = Executors.newFixedThreadPool(threads);
            List<Callable<Void>> tasks = new ArrayList<>();
            AtomicInteger countDone = new AtomicInteger(0);
            for (double[][] test : tests) {
                tasks.add(() -> {
                    double fastTime = timing(fast, test);
                    double bosTime = timing(bos, test);
                    double hybridTime = timing(hybrid, test);
                    int localN = test.length;
                    int localM = test[0].length;
                    int[] output = new int[localN];
                    fast.getSorter(localN, localM).sort(test, output);
                    int maxK = -1;
                    for (int i = 0; i < test.length; ++i) {
                        maxK = Math.max(maxK, output[i]);
                    }
                    ++maxK;
                    out.println(localN + " " + localM + " " + maxK + "\n" + fastTime + "\n" + bosTime + "\n" + hybridTime);
                    System.out.println(countDone.incrementAndGet() + "/" + tests.size());
                    return null;
                });
            }
            service.invokeAll(tasks);
            service.shutdown();
        }
    }
}
