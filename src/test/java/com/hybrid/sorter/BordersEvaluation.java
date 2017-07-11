package com.hybrid.sorter;

import com.hybrid.sorter.old.AnalysisTests;
import com.hybrid.sorter.old.GenerateTestFiles;

import java.util.Random;

import static java.lang.Math.*;


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

    private GenerateTestFiles.TestGenerator generator;

    BordersEvaluation(int N, int M, GenerateTestFiles.TestGenerator generator) throws Exception {
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
        double r = log(N);
        while(r - l > eps) {
            double a = (l * 2 + r) / 3;
            double b = (l + r * 2) / 3;
            double fa = f((int) pow(E, a));
            double fb = f((int) pow(E, b));
            if(max(fa, fb) - min(fa, fb) < 0.05) {
                double fr = f((int) pow(E, r));
                double fl = f((int) pow(E, l));
                if(abs(fr - fb) > abs(fl - fa)) {
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

        return (int) pow(E, (r + l) / 2);
    }

    private int binary_down() {
        double l = 1;
        double r = log(Xmin);

        while(r - l > eps) {
            double m = (l+r)/2;
            double fm = f((int) pow(E, m));

            if(-delta < fm && fm < delta) {
                return (int) pow(E, m);
            } if (fm < 0) {
                r = m;
            } else {
                l = m;
            }
        }
        return (int) pow(E, (l+r)/2);
    }

    private int binary_up() {
        double l = log(Xmin);
        double r = log(N);

        while(r - l > eps) {
            double m = (l+r)/2;
            double fm = f((int) pow(E, m));

            if(-delta < fm && fm < delta) {
                return (int) pow(E, m);
            } if (fm > 0) {
                r = m;
            } else {
                l = m;
            }
        }
        return (int) pow(E, (l+r)/2);
    }

    double f(int n) {
        double sum = 0.0;
        int times = 5;
        for (int i = 0; i < times; ++i) {
            double[][] test = generator.generate(n, M);
            double fastTiming = AnalysisTests.timing(new FasterNonDominatedSorting(), test);
            double bosTiming = AnalysisTests.timing(new BOSNonDominatedSorting(), test);
            sum += (bosTiming - fastTiming) / max(bosTiming, fastTiming);
        }
        // return 0.5 - 1/(1 + (log(n)/3 - 2) * (log(n)/3 - 2)) + nnew Random().nextGaussian() * 0.02 - 0.01;
        System.err.println("[" + n + " -> " + (sum / times) + "]");
        return sum / times;
    }
}

