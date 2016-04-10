import java.util.Random;

public class StressTest {

    static void stressTest(int N, int M, boolean doRundomNM) {
        Random rnd = new Random();

        if(doRundomNM) {
            N = rnd.nextInt(N);
            M = rnd.nextInt(M);
        }

        double[][] input = new double[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                input[i][j] = rnd.nextInt(1000000);
            }
        }

        try {
            Tests.checkEqual(Tests.findFrontIndicesFastAlgo(input), Tests.findFrontIndicesBOS(input));
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
        stressTest(100000, 5, false);

        for(int i = 0; i < 10; i++) {
            stressTest(10000, 20, true);
        }

        System.out.println();
        System.out.println("Stress tests passed");
    }
}
