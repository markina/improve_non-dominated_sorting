import java.util.Random;

public class StressTest {

    private static void stressTest(int N, int M, boolean doRundomNM) {
        Random rnd = new Random();

        if(doRundomNM) {
            N = rnd.nextInt(N);
            M = Math.max(rnd.nextInt(M), 1);
        }

        double[][] input = new double[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                input[i][j] = rnd.nextInt(1000000);
            }
        }

        try {
            Tests.checkEqual(
                    Tests.findFrontIndices(input, new FasterNonDominatedSorting()),
                    Tests.findFrontIndices(input, new BOSNonDominatedSorting()));
            System.out.println("Stress test passed");
        } catch(AssertionError er) {
            throw new AssertionError("Error in stress test : " + er.getMessage());
        }
    }

    private static void stressTest(double[][] input) {
        try {
            Tests.checkEqual(
                    Tests.findFrontIndices(input, new FasterNonDominatedSorting()),
                    Tests.findFrontIndices(input, new BOSNonDominatedSorting()));
            System.out.println("Stress test passed");
        } catch(AssertionError er) {
            throw new AssertionError("Error in stress test : " + er.getMessage());
        }
    }

    public static void main(String[] args) {
        stressTest(6, 2, false);

        stressTest(6, 3, false);

        stressTest(600, 3, false);

        stressTest(600, 5, false);
        stressTest(600, 50, false);

        stressTest(10000, 10, false);
        stressTest(10000, 5, false);

        for(int i = 0; i < 10; i++) {
            stressTest(100000, 20, true);
        }

        stressTest(6, 3, false);

        for(int i = 0; i < 10; i++) {
            stressTest(100000, 1, true);
        }

        for(int i = 0; i < 10; i++) {
            stressTest(100000, 3, true);
        }

        for(int i = 0; i < 10; i++) {
            stressTest(100000, 3, false);
        }


        System.out.println();
        System.out.println("All stress tests passed");
    }
}
