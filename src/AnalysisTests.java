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

    private static final String NOISE = "noise";
    private static final String CUBE = "cube";
    private static Random rnd = new Random(366239);

    private static void timing(Sorter sorter,
                               double[][] input,
                               PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        bean.setThreadCpuTimeEnabled(true);
        boolean good_time = false;
        int cur_n = 1;
        int[] rv = new int[input.length];
        while (!good_time) {
            long start = bean.getCurrentThreadUserTime();
            for (int i = 0; i < cur_n; i++) {
                sorter.sort(input, rv);
            }
            long end = bean.getCurrentThreadUserTime();
            if (end - start > 100000000) {
                out.print(end - start + " ");
                out.print(cur_n + " ");
                good_time = true;
            } else {
                cur_n *= 2;
            }
        }
        out.println(get_max(rv)+1);
    }

    private static int get_max(int[] rv) {
        int m = -1;
        for (int e : rv) {
            m = Math.max(m, e);
        }
        return m;
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

    public static void timing_bos(String prefix) throws Exception {
        BOSNonDominatedSorting sorterFactory = new BOSNonDominatedSorting();
        String suffix = "bos";
        init_timing(sorterFactory, prefix, suffix);
    }

    private static class AggregationStruct {
        int N, M, k;
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
            return "" + N + " " + M + " " + k + "\n"
                    + speed_fast + "\n"
                    + speed_bos + "\n";
        }
    }

    private static List<AggregationStruct> get_aggregation_info(String prefix) throws Exception {
        Reader in_fast = new Reader(prefix + "_time_fast.txt");
        Reader in_bos = new Reader(prefix + "_time_bos.txt");
        List<AggregationStruct> res = new ArrayList<>();
        while (in_fast.hasMore()) {
            check(in_bos.hasMore(), prefix + "_time_bos.txt ended");
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
            int k1 = in_fast.nextInt(), k2 = in_bos.nextInt();
            check(k1 == k2);
            elem.k = k1;
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
        }
        out.close();
    }

    public static void aggregation_result(String prefix) throws Exception {
        List<AggregationStruct> res = get_aggregation_info(prefix);
        print_result(prefix, res);
    }

    private static double[][] genHypercube(int dim, int size) {
        double[][] rv = new double[size][dim];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < dim; j++) {
                rv[i][j] = rnd.nextDouble();
            }
        }
        return rv;
    }

    private static void getArrayWithSum(double C, double [] ans, int l, int r) throws Exception {
        if(r - l == 0) {
            throw new Exception("r == l");
        }
        if(r - l == 1) {
            ans[l] = C;
            return;
        }
        int cnt = r - l;
        int sep = rnd.nextInt(cnt-1) + 1;
        if(cnt == sep) {
            ans[r-1] = C/2;
            getArrayWithSum(C/2, ans, l, r-1);
            return;
        }

        getArrayWithSum(C/2, ans, l, l+sep);
        getArrayWithSum(C/2, ans, l+sep, r);
    }

    private static double[][] getOneRankRandom(int N, int M) throws Exception {
        double [][] res = new double[N][M];
        double C = 1;
        for(int i = 0; i < N; i++) {
            double[] temp = new double[M];
            getArrayWithSum(C, temp, 0, M);
            for(int j = 0; j < M; j++) {
                res[i][j] = temp[j];
            }
        }
        return res;
    }

    private static double[][] getOneRankWithNoise(int N, int M, double A) throws Exception {
        double[][] res = getOneRankRandom(N, M);
        Tests.checkEqual(new int[N], Tests.findFrontIndices(res, new FasterNonDominatedSorting()));

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                res[i][j] += (2 * rnd.nextDouble() - 1) * A;
            }
        }
        return res;
    }

    static void run_experiment(String name, int N, int M) throws Exception {
        run_experiment(name, N, M, 0);
    }

    private static double[][] getTestIn(int N, int M) throws Exception {
        double[][] res = new double[N][M];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                res[i][j] = i;
            }
        }
        return res;
    }

    private static void run_experiment(String name, int N, int M, double A) throws Exception {
        double[][] in;
        String prefix;
        if(name.equals(CUBE)) {
            prefix = name + "_" + N + "_" + M;
            in = genHypercube(M, N);
        } else if (name.equals(NOISE)) {
            prefix = name + "_" + N + "_" + M + "_" + A;
            in = getOneRankWithNoise(N, M, A);
        } else if (name.equals("test")) {
            prefix = name + "_" + N + "_" + M;
            in = getTestIn(N, M);
        } else {
            throw new Exception("Bad name");
        }


        int[] rv_fast = Tests.findFrontIndices(in, new FasterNonDominatedSorting());
        int[] rv_bos = Tests.findFrontIndices(in, new BOSNonDominatedSorting());
        Tests.checkEqual(rv_fast, rv_bos);

        logging(prefix, in);
        timing_fast(prefix);
        timing_bos(prefix);
        aggregation_result(prefix);
    }


    public static void main(String[] args) throws Exception {
        if(args.length < 3) {
            throw new Exception("Args: (cube|noise) N M [A]");
        }

        String name = args[0];
        int N = Integer.parseInt(args[1]);
        int M = Integer.parseInt(args[2]);
        double A = 0;
        if(args.length == 4) {
            A = Double.parseDouble(args[3]);
        }

        run_experiment(name, N, M, A);
    }


}
