package com.hybrid.sorter.old;

import com.hybrid.sorter.FasterNonDominatedSorting;
import com.hybrid.sorter.HybridNonDominatedSorting;
import com.hybrid.sorter.Sorter;
import com.hybrid.sorter.BOSNonDominatedSorting;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by mmarkina on 06/06/16.
 * TODO delete
 */
public class MuPlusLambda {
    int M = 10;
    int mu = 1000;
    int lambda = 100000;
    double [][] population_parent;
    double [][] population_children;
    Random rnd;
    double old_result = 20*M;
    int interation = 1000;
    Sorter sorter;
    PrintWriter out;

    private static final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    static {
        bean.setThreadCpuTimeEnabled(true);
    }

    MuPlusLambda(Sorter sorter, PrintWriter out) {
        this.out = out;
        rnd = new Random(64);
        this.sorter = sorter;
        population_parent = new double[mu][M];
        for(int i = 0; i < mu; i++) {
            for(int j = 0; j < M; j++) {
                population_parent[i][j] = rnd.nextDouble() * 20;
            }
        }
        population_children = new double[lambda][M];
    }

    void run() {
        while (bestOfPopulation()) {
            generateNewPopulation();
            chalange();
        }
    }

    double getSum(double [] ar) {
        double sum = 0;
        for(int i = 0; i < ar.length; i++) {
            sum += ar[i];
        }
        return sum;
    }


    boolean bestOfPopulation() {
        double mi = 10*M;
        int i_mi = -1;
        for(int i = 0; i < mu; i++) {
            double sum = getSum(population_parent[i]);
            if(sum < mi) {
                mi = sum;
                i_mi = i;
            }
        }

        if(old_result == mi) {
            interation--;
            if(interation == 0) {
                out.println("END!");
                return false;
            }
        } else {
            interation = 30;
            out.println(Arrays.toString(population_parent[i_mi]));
        }
        old_result = mi;
        return true;
    }


    void crossover(int i_parent1, int i_parent2, int i_child) {
        for(int i = 0; i < M; i++) {
            double r = rnd.nextDouble();
            if(r < 0.495) {
                population_children[i_child][i] = population_parent[i_parent1][i];
            } else if (r < 0.99) {
                population_children[i_child][i] = population_parent[i_parent2][i];
            } else {
                population_children[i_child][i] = rnd.nextDouble() * 20;
            }
        }
    }

    void generateNewPopulation() {
        for (int i = 0; i < mu; i++) {
            population_children[i] = population_parent[i];
        }
        for(int i = mu; i < lambda; i++) {
            crossover(rnd.nextInt(mu), rnd.nextInt(mu), i);
        }
    }

    void chalange() {
        int [] rv = new int[lambda];
        sorter.sortImpl(population_children, rv);
        int cur_rang = 0;
        int cnt = 0;
        while(cnt < mu) {
            for(int i = 0; i < lambda; i++) {
                if(rv[i] == cur_rang) {
                    population_parent[cnt] = population_children[i];
                    cnt++;
                    if(cnt >= mu) {
                        break;
                    }
                }
            }
            cur_rang++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("log.txt");
        long start = bean.getCurrentThreadUserTime();
        MuPlusLambda algoFast = new MuPlusLambda(new FasterNonDominatedSorting().getSorter(100000, 10), out);
        algoFast.run();
        long end = bean.getCurrentThreadUserTime();
        out.println(end - start);
        out.close();

        out = new PrintWriter("log2.txt");
        start = bean.getCurrentThreadUserTime();
        MuPlusLambda algoHybrid = new MuPlusLambda(new HybridNonDominatedSorting().getSorter(100000, 10), out);
        algoHybrid.run();
        end = bean.getCurrentThreadUserTime();
        out.println(end - start);
        out.close();

        out = new PrintWriter("log3.txt");
        start = bean.getCurrentThreadUserTime();
        MuPlusLambda algoBOS = new MuPlusLambda(new BOSNonDominatedSorting().getSorter(100000, 10), out);
        algoBOS.run();
        end = bean.getCurrentThreadUserTime();
        out.println(end - start);
        out.close();
    }

}


