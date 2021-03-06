package com.hybrid.sorter.old;


import com.hybrid.sorter.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AnalysisTests {

    private static final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    static {
        bean.setThreadCpuTimeEnabled(true);
    }

    private static FactoryNonDominatedSorting fast, bos, hybrid;

    /**
     * Measure time for one soring
     *
     * @param sorterFactory sorter factory
     * @param input input data
     * @return time per one sorting
     */
    public static double timing(FactoryNonDominatedSorting sorterFactory, double[][] input) {
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

    static class Test {
        double[][] data;
        int nGeneration;
    }

    /**
     * Scroll all file in directory recursively and save as Test in tests
     *
     * @param file is directory or file with data for test
     * @param tests OUT parameters for saving tests
     * @throws IOException file has problems opening
     */
    private static void collectTests(File file, List<Test> tests) throws IOException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                collectTests(f, tests);
            }
        } else {
            try (Scanner in = new Scanner(file)) {
                int nPoints = in.nextInt();
                int nObjectives = in.nextInt();
                int nGeneration = Integer.parseInt(file.getParentFile().getName());
                double[][] rv = new double[nPoints][nObjectives];
                for (int i = 0; i < nPoints; ++i) {
                    for (int j = 0; j < nObjectives; ++j) {
                        rv[i][j] = in.nextDouble();
                    }
                }
                Test test = new Test();
                test.data = rv;
                test.nGeneration = nGeneration;
                tests.add(test);
            } catch (Exception ex) {
                System.out.println("File " + file.getName() + " has problems opening");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length < 2) {
            throw new Exception("Args: testRoot threads");
        }

        Locale.setDefault(Locale.US);

        int threads = Integer.parseInt(args[1]);

        fast = new FasterNonDominatedSorting();
        bos  = new BOSNonDominatedSorting();
        hybrid  = new HybridNonDominatedSorting();

        List<Test> tests = new ArrayList<>();
        File testRoot = new File(args[0]);
        collectTests(testRoot, tests);

        System.out.println(tests.size() + " tests generated.");
        System.out.print("Warming up at first 10 tests [");

        for (int j = 0; j < tests.size() && j < 10; ++j) {
            double[][] test = tests.get(j).data;
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

        try (PrintWriter out = new PrintWriter(new File(testRoot, "result.txt"))) {
            ExecutorService service = Executors.newFixedThreadPool(threads);
            List<Callable<Void>> tasks = new ArrayList<>();
            AtomicInteger countDone = new AtomicInteger(0);
            for (Test test0 : tests) {
                tasks.add(() -> {
                    double[][] test = test0.data;
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
                    out.println(localN + " " + localM + " " + test0.nGeneration + " " + maxK + "\n" + fastTime + "\n" + bosTime + "\n" + hybridTime);
                    System.out.println(countDone.incrementAndGet() + "/" + tests.size());
                    return null;
                });
            }
            service.invokeAll(tasks);
            service.shutdown();
        }
    }
}
