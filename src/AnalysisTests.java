import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

public class AnalysisTests {

    static Random rng = new Random(366239);
    static double[][] genHypercube(int dim, int size) {
        double [][] rv = new double[size][dim];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < dim ; j++) {
                rv[i][j] = rng.nextDouble();
            }
        }
        return rv;
    }

    static int[] findFrontIndicesFastAnalysis(double[][] input,
                                              boolean withTiming,
                                              boolean withLogging,
                                              PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        int[] rv = new int[size];
        Sorter sorter = FasterNonDominatedSorting.getSorterFastWithAnalysis(
                size,
                dim,
                withTiming,
                withLogging,
                out);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        bean.setThreadCpuTimeEnabled(true);
        long start = 0;
        if(withTiming) {
            start = bean.getCurrentThreadUserTime();
        }
            sorter.sort(input, rv);
        if(withTiming) {
            long end = bean.getCurrentThreadUserTime();
            out.println(end - start);
        }
        return rv;
    }

    static int[] findFrontIndicesBOSAnalysis(double[][] input,
                                              boolean withTiming,
                                              boolean withLogging,
                                              PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        int[] rv = new int[size];
        Sorter sorter = FasterNonDominatedSorting.getSorterBOSWithAnalysis(
                size,
                dim,
                withTiming,
                withLogging,
                out);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        bean.setThreadCpuTimeEnabled(true);
        long start = 0;
        if(withTiming) {
            start = bean.getCurrentThreadUserTime();
        }
        sorter.sort(input, rv);
        if(withTiming) {
            long end = bean.getCurrentThreadUserTime();
            out.println(end - start);
        }
        return rv;
    }

}
