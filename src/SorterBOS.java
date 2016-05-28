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
    int _size;
    int _dim;
    private int[] temp4CompEq;

    SorterBOS(int capacity_size, int capacity_dim) {
        super(capacity_size, capacity_dim);
        scratchByKthObj = new int[capacity_size];
        L = new SmartL(capacity_dim, capacity_size);
        C = new SmartC(capacity_size, capacity_dim);
        isRanked = new boolean[capacity_size];
        output = new int[capacity_size];
        Q = new int[capacity_dim][capacity_size];
        sorter = new MergeSorter(capacity_size);
        temp4CompEq = new int[capacity_size];
    }

    private void initAll(int sz, int d) {
        _size = sz;
        _dim = d;
        L.init(sz, d);
        C.init(sz, d);
        Arrays.fill(isRanked, false);
        Arrays.fill(output, 0);
        SC = 0;
        RC = 1;
    }

    protected void sortImpl(double[][] input, int[] output) {
        this.input = input;
        this.output = output;

        initAll(input.length, input.length > 0 ? input[0].length : 0);
        fillQ();

        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _dim; j++) {
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
            if (SC == _size) {
                break;
            }
        }
//         printOutput();
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

    private void fillQ() {
        for (int i = 0; i < _size; ++i) {
            scratchByKthObj[i] = i;
        }
        if (_dim > 0) {
            sorter.lexSort(scratchByKthObj, 0, _size, input, temp4CompEq);
        }
        for (int i = 0; i < _dim; i++) {
            System.arraycopy(sorter.indices, 0, Q[i], 0, _size);
            Arrays.fill(scratchByKthObj, 0);
            sortIntByTthObj(0, _size, i);
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
        System.out.println("N = " + _size + "; M = " + _dim);
    }

    private void printQ() {
        for (int i = 0; i < _dim; i++) {
            System.out.print("Q[" + i + "] = ");
            for (int x = 0; x < _size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();
        }
    }

    private void printC() {
        System.out.print("C = ");
        for (int x = 0; x < _size; x++) {
            System.out.print("{ ");
            for(int y: C.get(x)) {
                System.out.print(y + " ");
            }
            System.out.print("}  ");
        }
        System.out.println();
    }

    private void printOutput() {
        System.out.print("output = ");
        for (int i = 0; i < _size; i++) {
            System.out.print(output[i] + " ");
        }
        System.out.println();
    }
}
