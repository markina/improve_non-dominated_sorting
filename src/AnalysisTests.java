import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by rita on 20.04.16.
 */
public class AnalysisTests {

    static Random rng = new Random(366239);
    private static double[][] genHypercube(int dim, int size) {
        double [][] rv = new double[size][dim];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < dim ; j++) {
                rv[i][j] = rng.nextDouble();
            }
        }
        return rv;
    }

    static int[] findFrontIndicesFastTiming(double[][] input) throws IllegalAccessException {
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        int[] rv = new int[size];
        boolean withTiming = true;
        boolean withLogging = false;
        FasterNonDominatedSorting.getSorterFastWithAnalysis(size, dim, withTiming, withLogging).sort(input, rv);
        return rv;
    }

//    static int[] findFrontIndicesBOSTiming(double[][] input) {
//        int size = input.length;
//        int dim = size == 0 ? 0 : input[0].length;
//        int[] rv = new int[size];
//        Arrays.fill(rv, 0);
//        FasterNonDominatedSorting.getSorterBOS(size, dim).sort(input, rv);
//        return rv;
//    }


    public static void main(String[] args) throws IllegalAccessException {
        findFrontIndicesFastTiming(genHypercube(10, 100000));

    }
}
