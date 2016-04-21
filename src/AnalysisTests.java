import java.io.FileNotFoundException;
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
                                              String nameFile) throws IllegalAccessException, FileNotFoundException {
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        int[] rv = new int[size];
        FasterNonDominatedSorting.getSorterFastWithAnalysis(
                size,
                dim,
                withTiming,
                withLogging,
                nameFile)
                .sort(input, rv);
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


}
