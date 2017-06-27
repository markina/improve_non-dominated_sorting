package com.hybrid.sorter;

import com.hybrid.sorter.units.SmartC;
import com.hybrid.sorter.units.SmartL;

import java.util.Arrays;

// Best Order Sort sorter
final public class SorterBOS extends Sorter {
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
    private int[] temp4CompEq;

    public SorterBOS(int capacity_size, int capacity_dim) {
        super(capacity_size, capacity_dim);
        scratchByKthObj = new int[capacity_size];
        L = new SmartL(capacity_dim, capacity_size);
        C = new SmartC(capacity_size, capacity_dim);
        isRanked = new boolean[capacity_size];
        Q = new int[capacity_dim][capacity_size];
        sorter = new MergeSorter(capacity_size);
        temp4CompEq = new int[capacity_size];
        input = new double[capacity_size][capacity_dim];
        output = new int[capacity_size];
    }

    private void initAll(int sz, int d) {
        size = sz;
        dim = d;

        L.init(size, dim);
        C.init(size, dim);
        Arrays.fill(isRanked, 0, size, false);
        SC = 0;
        RC = 1;
    }

    public void sortImplSpecial(double[][] input, int[] output, int[] indices, int from, int until, int d) {
//        this.input = input;
//        this.output = output;

        for(int i = from; i < until; i++) {
            this.input[i-from] = input[indices[i]];
            this.output[i-from] = output[indices[i]];
        }

        initAll(until - from, d);
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

        for(int i = from; i < until; i++) {
            output[indices[i]] = this.output[i-from];
        }
    }

    public void sortImpl(double[][] input, int[] output) {
        this.input = input;
        this.output = output;

        initAll(input.length, input.length > 0 ? input[0].length : 0);
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
    }


    private void findRank(int s, int j) {
//        int l = -1;
        int l = output[s]-1;
//        int r = RC - 1;
        int r = Math.max(RC, output[s]+1);
        boolean check;
        boolean done = false;
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
                r = Math.max(m, output[s]);
                done = true;
            } else {
                l = m;
            }
        }
        if (done) {
            RC = Math.max(RC, r + 1);
            output[s] = r;
//            output[s] = Math.max(r, output[s]);
            L.addTo(j, output[s], s);
        } else {
            RC++;
            output[s] = RC - 1;
//            output[s] = Math.max(RC - 1, output[s]);
            L.addTo(j, output[s], s);
        }
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
        for (int i = 0; i < size; ++i) {
            scratchByKthObj[i] = i;
        }
        if (dim > 0) {
            sorter.lexSort(scratchByKthObj, 0, size, input, temp4CompEq);
        }
        for (int i = 0; i < dim; i++) {
            System.arraycopy(sorter.indices, 0, Q[i], 0, size);
            sortIntByTthObj(0, size, i);
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

    private void printQ() {
        for (int i = 0; i < dim; i++) {
            System.out.print("Q[" + i + "] = ");
            for (int x = 0; x < size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();
        }
    }

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

    private void printOutput() {
        System.out.print("output = ");
        for (int i = 0; i < size; i++) {
            System.out.print(output[i] + " ");
        }
        System.out.println();
    }
}
