import javafx.util.Pair;
import units.Reader;

import java.util.*;

/**
 * Created by mmarkina on 04/06/16.
 */
public class BordersEvaluation {
    private int Xmin;
    private double eps = 0.01;
    private double delta = 0.01;

    private int N, M;
    int leftBorder, rightBorder;

    Random rnd = new Random(23);

    private AnalysisTests.TestGenerator generator;

    BordersEvaluation(int N, int M, AnalysisTests.TestGenerator generator) throws Exception {
        this.N = N;
        this.M = M;
        this.generator = generator;
    }

    void evalBorders() {
        Xmin = ternary();
        leftBorder = binary_down();
        rightBorder = binary_up();
    }

    int getLeftBorder(){
        return leftBorder;
    }

    int getRightBorder() {
        return rightBorder;
    }

    int getXmin() {
        return Xmin;
    }

    int ternary() {
        double l = 0;
        double r = Math.log(N);
        while(r - l > eps) {
            double a = (l * 2 + r) / 3;
            double b = (l + r * 2) / 3;
            double fa = f((int) Math.pow(Math.E, a));
            double fb = f((int) Math.pow(Math.E, b));
            if(Math.max(fa, fb) - Math.min(fa, fb) < 0.05) {
                double fr = f((int) Math.pow(Math.E, r));
                double fl = f((int) Math.pow(Math.E, l));
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

    private int binary_down() {
        double l = 1;
        double r = Math.log(Xmin);

        while(r - l > eps) {
            double m = (l+r)/2;
            double fm = f((int) Math.pow(Math.E, m));

            if(-delta < fm && fm < delta) {
                return (int) Math.pow(Math.E, m);
            } if (fm < 0) {
                r = m;
            } else {
                l = m;
            }
        }
        return (int) Math.pow(Math.E, (l+r)/2);
    }

    private int binary_up() {
        double l = Math.log(Xmin);
        double r = Math.log(N);

        while(r - l > eps) {
            double m = (l+r)/2;
            double fm = f((int) Math.pow(Math.E, m));

            if(-delta < fm && fm < delta) {
                return (int) Math.pow(Math.E, m);
            } if (fm > 0) {
                r = m;
            } else {
                l = m;
            }
        }
        return (int) Math.pow(Math.E, (l+r)/2);
    }

    double f(int n) {
        double sum = 0.0;
        int times = 5;
        for (int i = 0; i < times; ++i) {
            double[][] test = generator.generate(n, M);
            double fastTiming = AnalysisTests.timing(new FasterNonDominatedSorting(), test);
            double bosTiming = AnalysisTests.timing(new BOSNonDominatedSorting(), test);
            sum += (bosTiming - fastTiming) / Math.max(bosTiming, fastTiming);
        }
        // return 0.5 - 1/(1 + (Math.log(n)/3 - 2) * (Math.log(n)/3 - 2)) + new Random().nextGaussian() * 0.02 - 0.01;
        System.err.println("[" + n + " -> " + (sum / times) + "]");
        return sum / times;
    }
}

