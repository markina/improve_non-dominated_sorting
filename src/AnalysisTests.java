import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

public class AnalysisTests {

    static Random rng = new Random(366239);

    static double[][] genHypercube(int dim, int size) {
        double[][] rv = new double[size][dim];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < dim; j++) {
                rv[i][j] = rng.nextDouble();
            }
        }
        return rv;
    }

    static void timing(Sorter sorter,
                        double[][] input,
                        PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        bean.setThreadCpuTimeEnabled(true);
        boolean good_time = false;
        int cur_n = 1;
        while(!good_time) {
            long start = bean.getCurrentThreadUserTime();
            for(int i = 0; i < cur_n; i++) {
                int [] rv = new int[input.length];
                sorter.sort(input, rv);
            }
            long end = bean.getCurrentThreadUserTime();
            if(end - start > 0) {
                out.print(end - start + " ");
                out.println(cur_n);
                good_time = true;
            } else {
                cur_n *= 10;
            }
        }
    }

    public static void logging(String prefix, double[][] input) throws IllegalAccessException, FileNotFoundException {
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
        while(in.hasMore()) {
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

    public static void timing_fast(int N, int M, String prefix) throws Exception {
        FasterNonDominatedSorting sorterFactory = new FasterNonDominatedSorting();
        String suffix = "fast";
        init_timing(sorterFactory, prefix, suffix);
    }

    public static void timing_bos(int N, int M, String prefix) throws Exception {
        BOSNonDominatedSorting sorterFactory = new BOSNonDominatedSorting();
        String suffix = "bos";
        init_timing(sorterFactory, prefix, suffix);
    }


    public static void test_cube(int N, int M) throws Exception {
        String name = "cube" + "_" + N + "_" + M;
        logging(name, AnalysisTests.genHypercube(M, N));
        timing_fast(N, M, name);
        timing_bos(N, M, name);
    }

    public static void main(String[] args) throws Exception {
        test_cube(1000, 4);
    }

}
