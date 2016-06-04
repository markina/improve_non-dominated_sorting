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

    BordersEvaluation(int N, int M) throws Exception {
        this.N = N;
        this.M = M;
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
        double l = 1;
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
        return 0.5 - 1/(1 + (Math.log(n)/3 - 2) * (Math.log(n)/3 - 2)) + new Random().nextGaussian() * 0.02 - 0.01;
        // TODO return the average time((tb - tf)/max) of 15 runs with n random points
    }
}

