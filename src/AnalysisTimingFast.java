import java.io.PrintWriter;

public class AnalysisTimingFast {
    public static void main(String[] args) throws Exception {
        Reader in = new Reader("cube_data.txt");
        PrintWriter out = new PrintWriter("cube_time_fast.txt");
        while(in.hasMore()) {
            int id = in.nextInt();
            double[][] input = in.getNextData();
            out.print(id + " : " +
                    "N = " + input.length + " " +
                    "M = " + (input.length != 0 ? input[0].length : 0) + " " +
                    "t = ");
            AnalysisTests.findFrontIndicesFastAnalysis(input, true, false, out);
        }
        out.close();
        in.close();
    }
}
