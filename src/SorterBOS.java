import units.SmartC;
import units.SmartL;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;

// Best Order Sort sorter
final class SorterBOS extends Sorter {
    private SmartL L = null;
    private SmartC C = null;
    private boolean[] isRanked = null;
    private int SC;
    private int RC;
    private int[][] Q = null;
    private int[] scratchByKthObj;

    private double[][] input;
    private int[] output;
    MergeSorter sorter;

    SorterBOS(int size, int dim) {
        super(size, dim);
        L = new SmartL(dim, size);
        C = new SmartC(size, dim);
        isRanked = new boolean[size];
        output = new int[size];
        Q = new int[dim][size];
        sorter = new MergeSorter(size);
        initAll();
    }

    private void initAll() {
        L.init();
        C.init();
        Arrays.fill(isRanked, false);
        Arrays.fill(output, 0);
        SC = 0;
        RC = 1;

    }

    protected void sortImpl(double[][] input, int[] output) {
        this.input = input;
        this.output = output;

        initAll();
        fillQ();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < dim; j++) {
                int s = Q[j][i];
//                C.get(s).remove(j); // in original algorithm here
                if (isRanked[s]) {
                    L.addTo(j, this.output[s], s);
                } else {
                    findRank(s, j);
                    isRanked[s] = true;
                    SC++;
                }
                C.removeFrom(s, j);
            }
            if (SC == size) {
                break;
            }
        }
//         printOutput();
    }



    private void printOutput() {
        System.out.print("output = ");
        for (int i = 0; i < size; i++) {
            System.out.print(output[i] + " ");
        }
        System.out.println();
    }

    private void findRank(int s, int j) {
        int l = -1;
        int r = RC - 1;
        boolean check;
        while (r - l > 1) {
            int m = (l + r) / 2;
            check = false;
            for (Integer t : L.get(j, m)) {
//                System.out.print(t + " ");
                check = dominationCheck(s, t);
                if (check) {
                    break;
                }
            }
//            System.out.println();
            if (!check) {
                r = m;
            } else {
                l = m;
            }
        }
        check = false;
//        L.printSout();
        for (Integer t : L.get(j, r)) {
            check = dominationCheck(s, t);
            if (check) {
                break;
            }
        }
        if (!check) {
            output[s] = r;
            L.addTo(j, output[s], s);
        } else {
            RC++;
            output[s] = RC - 1;
            L.addTo(j, output[s], s);
        }
//        L.printSout();
    }

    private boolean dominationCheck(int s, int t) {
        boolean isEq = true;
        for (int j : C.get(s)) {
            int compRes = Double.compare(input[s][j], input[t][j]);
            if (compRes < 0) {
                return false;
            }
            if (compRes > 0) {
                isEq = false;
            }
        }
        return !isEq;
    }

//    private void fillC() {
//        for (int i = 0; i < size; i++) {
//            C.add(new TreeSet<>());
//            for (int j = 0; j < dim; j++) {
//                C.get(i).add(j);
//            }
//
//        }
////        printC();
//    }

    private void printC() {
        System.out.print("C = ");
        for (int x = 0; x < size; x++) {
            System.out.print("{ ");
            for(int y: C.get(x)) {
                System.out.print(y + " ");
            }
            System.out.print("}  ");
        }
        System.out.println();
    }

    private void fillQ() {
        int[] indices = new int[size];
        for (int i = 0; i < size; ++i) {
            indices[i] = i;
        }
        if (dim > 0) {
            sorter.lexSort(indices, 0, size, input, new int[size]);
        }
        for (int i = 0; i < dim; i++) {
            System.arraycopy(sorter.indices, 0, Q[i], 0, size);
            scratchByKthObj = new int[size];
            sortIntByTthObj(0, size, i);
            scratchByKthObj = null;

        }
//        printQ();
    }

    private void printQ() {
        for (int i = 0; i < dim; i++) {
            System.out.print("Q[" + i + "] = ");
            for (int x = 0; x < size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();
        }
    }

    private void sortIntByTthObj(int from, int until, int t) {
        if (from + 1 < until) {
            int mid = (from + until) >>> 1;
            sortIntByTthObj(from, mid, t);
            sortIntByTthObj(mid, until, t);
            int i = from, j = mid, k = 0, kMax = until - from;
            while (k < kMax) {
                if (i == mid || j < until && Double.compare(input[Q[t][j]][t], input[Q[t][i]][t]) < 0) {
                    scratchByKthObj[k] = Q[t][j];
                    ++j;
                } else {
                    scratchByKthObj[k] = Q[t][i];
                    ++i;
                }
                ++k;
            }
            System.arraycopy(scratchByKthObj, 0, Q[t], from, kMax);
        }
    }

    @Override
    protected void print_info() {
        System.out.println("--------------");
        System.out.println("SorterBOS");
        System.out.println("N = " + size + "; M = " + dim);
    }
}
