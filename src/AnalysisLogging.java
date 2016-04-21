import java.io.FileNotFoundException;

public class AnalysisLogging {
    public static void main(String[] args) throws IllegalAccessException, FileNotFoundException {
        double [][] cube = AnalysisTests.genHypercube(10, 100);
        AnalysisTests.findFrontIndicesFastAnalysis(cube, false, true, "cube_data.txt");
    }
}
