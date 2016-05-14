import units.Reader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static units.Assert.check;

public class AnalysisTests {

    private static Random rnd = new Random(366239);

    private static double[][] genHypercube(int dim, int size) {
        double[][] rv = new double[size][dim];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < dim; j++) {
                rv[i][j] = rnd.nextDouble();
            }
        }
        return rv;
    }

    static double[][] getOneRank(int N, int M) {
        double[][] res = new double[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M-1; j++) {
                res[i][j] = j + i;
            }
            res[i][M-1] = N - i;
        }
        return res;
    }

    private static void timing(Sorter sorter,
                               double[][] input,
                               PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        bean.setThreadCpuTimeEnabled(true);
        boolean good_time = false;
        int cur_n = 1;
        while (!good_time) {
            long start = bean.getCurrentThreadUserTime();
            for (int i = 0; i < cur_n; i++) {
                int[] rv = new int[input.length];
                sorter.sort(input, rv);
            }
            long end = bean.getCurrentThreadUserTime();
            if (end - start > 100000000) {
                out.print(end - start + " ");
                out.println(cur_n);
                good_time = true;
            } else {
                cur_n *= 2;
            }
        }
    }

    private static void logging(String prefix, double[][] input) throws IllegalAccessException, FileNotFoundException {
        PrintWriter out = new PrintWriter(prefix + "_data.txt");
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        int[] rv = new int[size];
        FactoryNonDominatedSorting sorterFactory = new FasterNonDominatedSorting();
        Sorter sorter = sorterFactory.getSorter(size, dim);
        sorter.setParamAnalysis(true, out);
        sorter.sort(input, rv);
        out.close();
    }

    private static void init_timing(FactoryNonDominatedSorting sorterFactory, String prefix, String suffix) throws Exception {
        Reader in = new Reader(prefix + "_data.txt");
        PrintWriter out = new PrintWriter(prefix + "_time_" + suffix + ".txt");
        while (in.hasMore()) {
            int id = in.nextInt();
            double[][] input = in.getNextData();
            out.print(id + " " +
                    input.length + " " +
                    (input.length != 0 ? input[0].length : 0) + " ");
            Sorter sorter = sorterFactory.getSorter(input.length, input.length != 0 ? input[0].length : 0);
            timing(sorter, input, out);
        }
        out.close();
        in.close();
    }

    private static void timing_fast(String prefix) throws Exception {
        FasterNonDominatedSorting sorterFactory = new FasterNonDominatedSorting();
        String suffix = "fast";
        init_timing(sorterFactory, prefix, suffix);
    }

    private static void timing_bos(String prefix) throws Exception {
        BOSNonDominatedSorting sorterFactory = new BOSNonDominatedSorting();
        String suffix = "bos";
        init_timing(sorterFactory, prefix, suffix);
    }

    private static void test_one_rank(int N, int M) throws Exception {
        String name = "one_rank" + "_" + N + "_" + M;
        logging(name, getOneRank(N, M));
        timing_fast(name);
        timing_bos(name);
        aggregation_result(name);
    }

    private static class AggregationStruct {
        int N, M;
        long t_fast, t_bos;
        int n_fast, n_bos;
        long speed_fast, speed_bos;

        AggregationStruct(int size, int dim) {
            N = size;
            M = dim;
        }

        void setFastInfo(long time, int cnt) {
            t_fast = time;
            n_fast = cnt;
            speed_fast = t_fast / n_fast;
            check(speed_fast > 0);
        }

        void setBOSInfo(long time, int cnt) {
            t_bos = time;
            n_bos = cnt;
            speed_bos = t_bos / n_bos;
            check(speed_bos > 0);
        }

        @Override
        public String toString() {
            return "" + N + " " + M + "\n"
                    + speed_fast + "\n"
                    + speed_bos + "\n";
        }
    }

    private static List<AggregationStruct> get_aggregation_info(String prefix) throws Exception {
        Reader in_fast = new Reader(prefix + "_time_fast.txt");
        Reader in_bos = new Reader(prefix + "_time_bos.txt");
        List<AggregationStruct> res = new ArrayList<>();
        while (in_fast.hasMore()) {
            if(!in_bos.hasMore()) {
                break;
            }
            int id = in_fast.nextInt();
            int id_bos = in_bos.nextInt();
            check(id == id_bos, "Bad id");
            check(res.size() == id, "Bad id");
            int N = in_fast.nextInt(), M = in_fast.nextInt();
            int N_bos = in_bos.nextInt(), M_bos = in_bos.nextInt();
            check(N == N_bos, "N from fast != N from bos for id = " + id);
            check(M == M_bos, "N from fast != N from bos for id = " + id);

            AggregationStruct elem = new AggregationStruct(N, M);
            elem.setFastInfo(in_fast.nextLong(), in_fast.nextInt());
            elem.setBOSInfo(in_bos.nextLong(), in_bos.nextInt());
            res.add(elem);
        }
        in_fast.close();
        in_bos.close();
        return res;
    }



    private static void print_result(String prefix, List<AggregationStruct> res) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(prefix + "_result.txt");

        for(AggregationStruct elem : res) {
            out.print(elem.toString());
            if(elem.speed_bos < elem.speed_fast) {
                out.println("-");
            } else {
                out.println("*");
            }
        }
        out.close();
    }

    private static void aggregation_result(String prefix) throws Exception {
        List<AggregationStruct> res = get_aggregation_info(prefix);
        print_result(prefix, res);
    }

    private static int get_max(int[] rv) {
        int m = -1;
        for(int i = 0; i < rv.length; i++) {
            m = Math.max(m, rv[i]);
        }
        return m;
    }

//    private static void print_statistic(String prefix, List<AggregationStruct> res) throws FileNotFoundException {
//        int N = res.get(0).N;
//        int[] cnt_success_fast = new int[N + 1];
//        int[] cnt_success_bos = new int[N + 1];
//
//        for (AggregationStruct elem : res) {
//            if (elem.speed_bos / elem.speed_fast >= 10 || elem.speed_fast / elem.speed_bos >= 10) {
//                if (elem.speed_bos < elem.speed_fast) {
//                    cnt_success_fast[elem.N]++;
//                } else {
//                    cnt_success_bos[elem.N]++;
//                }
//            }
//        }
//
//        PrintWriter out_fast = new PrintWriter(prefix + "_statistic_fast" + ".txt");
//        PrintWriter out_bos = new PrintWriter(prefix + "_statistic_bos" + ".txt");
//
//        for (int i = 0; i < N + 1; i++) {
//            if (cnt_success_fast[i] != 0 || cnt_success_bos[i] != 0) {
//                out_fast.println(i + " " + cnt_success_fast[i]);
//                out_bos.println(i + " " + cnt_success_bos[i]);
//            }
//        }
//        out_fast.close();
//        out_bos.close();
//    }

    private static double[][] getOneRankWithNoise(int N, int M, int K) {
        double[][] res = new double[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M-1; j++) {
                res[i][j] = j + i;
            }
            res[i][M-1] = N - i;
        }

        while(K > 0) {
            int a = rnd.nextInt(N);
            for(int j = 0; j < M-1; j++) {
                res[a][j] = rnd.nextDouble();
            }
            K--;
        }
        return res;
    }

    private static void test_cube(int N, int M) throws Exception {
        String name = "cube" + "_" + N + "_" + M;
        logging(name, genHypercube(M, N));
        timing_fast(name);
        timing_bos(name);
        aggregation_result(name);
    }

    private static void test_one_with_noise(int N, int M, int K) throws Exception {
        String name = "noise" + "_" + N + "_" + M + "_" + K;
        double[][] in = getOneRankWithNoise(N, M, K);

        int[] rv_fast = Tests.findFrontIndices(in, new FasterNonDominatedSorting());
        int[] rv_bos = Tests.findFrontIndices(in, new BOSNonDominatedSorting());
        Tests.checkEqual(rv_fast, rv_bos);

        logging(name, in);
        timing_fast(name);
        timing_bos(name);
        aggregation_result(name);
    }

    public static void main(String[] args) {
        int k = 10;
        try {
            test_one_with_noise(100000, 17, k);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }



}
