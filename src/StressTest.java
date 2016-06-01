import units.LogD;

import java.util.Random;

public class StressTest {
    private static Sorter sorterBOS = new BOSNonDominatedSorting().getSorter(100000, 50);
    private static FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
    private static FactoryNonDominatedSorting BOSFactory = new BOSNonDominatedSorting();
    private static FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();

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

//            Tests.checkEqual(
//                    Tests.findFrontIndices(input, fastFactory),
//                    Tests.findFrontIndices(input, sorterBOS));

            Tests.checkEqual(
                    Tests.findFrontIndices(input, hybridFactory),
                    Tests.findFrontIndices(input, fastFactory));
            

            System.out.println("Stress test passed");
        } catch(AssertionError er) {
            throw new AssertionError("Error in stress test : " + er.getMessage());
        }
    }

    public static void stressTest(double[][] input) {
        try {
            Tests.checkEqual(
                    Tests.findFrontIndices(input, fastFactory),
                    Tests.findFrontIndices(input, sorterBOS));
            System.out.println("Stress test passed");
        } catch(AssertionError er) {
            throw new AssertionError("Error in stress test : " + er.getMessage());
        }
    }

    public static void main(String[] args) {

        long begin = System.nanoTime();

        stressTest(6, 2, false);

        stressTest(6, 3, false);

        stressTest(600, 3, false);

        stressTest(600, 5, false);
        stressTest(600, 50, false);

        stressTest(10000, 10, false);
        stressTest(10000, 5, false);

        for(int i = 0; i < 5; i++) {
            stressTest(100000, 20, true);
        }

        stressTest(6, 3, false);

//        for(int i = 0; i < 5; i++) {
//            stressTest(100000, 1, true);
//        }
//
//        for(int i = 0; i < 5; i++) {
//            stressTest(100000, 3, true);
//        }
//
//        for(int i = 0; i < 5; i++) {
//            stressTest(100000, 3, false);
//        }


        System.out.println();

        long end = System.nanoTime();

        System.out.println("All stress tests passed");
        System.out.print(end - begin);
    }
}
