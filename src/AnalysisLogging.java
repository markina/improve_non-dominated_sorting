import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class AnalysisLogging {
    public static void main(String[] args) throws IllegalAccessException, FileNotFoundException {
        double [][] cube = AnalysisTests.genHypercube(10, 1000);
        PrintWriter out = new PrintWriter("cube_data.txt");
        AnalysisTests.findFrontIndicesFastAnalysis(cube, false, true, out);
        out.close();
    }
}
