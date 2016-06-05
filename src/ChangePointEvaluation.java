import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Created by mmarkina on 05/06/16.
 */

public class ChangePointEvaluation {
    public static int [] changePoints;
    private final int MAX_N_CHANGE_POINT = 2 * 10000;
    private double EPS_TERNATY = 0.01;
    private double DELTA = 0.01;
    double [][] input;

    private FactoryNonDominatedSorting hybrid;
    private static final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    private AnalysisTests.TestGenerator gen = new AnalysisTests.CubeGenerator();

    ChangePointEvaluation(int M){
        hybrid = new HybridNonDominatedSorting();
    }

    public void adjustChangePoints(int M) {
        changePoints = new int[M+1];
        changePoints[0] = 0;
        changePoints[1] = 0;
        changePoints[2] = 0;
        for(int m = 3; m <= M; m++) {
            int sum_x = 0;
            for(int j = 0; j < 15; j++) {
                input = gen.generate(MAX_N_CHANGE_POINT, m);
                sum_x += startTernary(m);
            }
            changePoints[m] = sum_x / 15;
        }
    }

    private int startTernary(int dim) {
        double l = 0;
        double r = Math.log(MAX_N_CHANGE_POINT);
        while(r - l > EPS_TERNATY) {
            double a = (l * 2 + r) / 3;
            double b = (l + r * 2) / 3;

            changePoints[dim] = (int) Math.pow(Math.E, a);
            double fa = f(dim);
            System.out.println("a: (" + changePoints[dim] + ", " + fa + ")");
            changePoints[dim] = (int) Math.pow(Math.E, b);
//            System.out.println("b = " + changePoints[dim]);
            double fb = f(dim);
            System.out.println("b: (" + changePoints[dim] + ", " + fb + ")");

//            System.out.println("fb = " + fb);
            if((Math.max(fa, fb) - Math.min(fa, fb)) < DELTA * Math.max(fa, fb)) {
                changePoints[dim] = (int) Math.pow(Math.E, r);
                double fr = f(dim);
                System.out.println("r: (" + changePoints[dim] + ", " + fr + ")");
                changePoints[dim] = (int) Math.pow(Math.E, l);
                double fl = f(dim);
                System.out.println("l: (" + changePoints[dim] + ", " + fl + ")");
                if(Math.abs(fr - fb) > Math.abs(fl - fa)) {
                    r = b;
                } else {
                    l = a;
                }
            } else if(fa < fb) {
                r = b;
            } else {
                l = a;
            }
        }
        return (int) Math.pow(Math.E, (r + l) / 2);
    }

    private double f(int dim) {
        return AnalysisTests.timing(hybrid, input);
    }

}
