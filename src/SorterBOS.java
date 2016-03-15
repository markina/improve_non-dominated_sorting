import java.util.*;

// Best Order Sort sorter
final class SorterBOS extends Sorter {
    List<List<Set<Integer>>> L = null;
    List<Set<Integer>> C = null;
    boolean [] isRanked = null;
    int SC;
    int RC;
    int [][] Q = null;
    int[] scratchByKthObj;
    int time;

    private double[][] input;
    private int[] output;
    MergeSorter sorter;

    public SorterBOS(int size, int dim) {
        super(size, dim);
        initL();
        C = new ArrayList<>(size);
        isRanked = new boolean[size];
        Arrays.fill(isRanked, false);
        output = new int[size];
        Arrays.fill(output, -1);
        Q = new int [dim][size];
        SC = 0;
        RC = 1;
        sorter = new MergeSorter(size);
        time = 0;
    }

    protected void sortImpl(double[][] input, int[] output) {
        System.out.println("BOS");
        this.input = input;
        this.output = output;

        fillQ_second_edition();
        fillC();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < dim; j++) {
                int s = Q[j][i];
//                C.get(s).remove(j); // in original algorithm here
                if(isRanked[s]) {
                    L.get(j).get(this.output[s]).add(s);
                } else {
                    findRank(s, j);
                    isRanked[s] = true;
                    SC++;
                }
                C.get(s).remove(j); //
            }
            if (SC == size) {
                break;
            }
        }
        System.out.print("output = ");
        for(int i = 0; i < size; i++) {
            System.out.print(this.output[i] + " ");
        }
        System.out.println();
        System.out.println("size = " + size + "; dim = " + dim);
        System.out.println ("Time for lex sorting: " + sorter.time);
        double log = Math.log(64) / Math.log(2);
        double estimated = size * log * dim;
        System.out.println("O(M N (log N))      = " + estimated);
        System.out.println("Result time         = " + time);
        System.out.println("Const               = " + (time / estimated));
        System.out.println();

    }

    private void findRank(int s, int j) {
        boolean done = false;
        for(int k = 0; k < RC; k++) {
            boolean check = false;
            for (Integer t : L.get(j).get(k)) {
                check = dominationCheck(s, t);
                if(check) {
                    break;
                }
            }
            if(!check) {
                output[s] = k;
                done = true;
                L.get(j).get(output[s]).add(s);
                break;
            }
        }
        if(!done) {
            RC++;
            output[s] = RC-1;
            L.get(j).get(output[s]).add(s);
        }
    }

    private boolean dominationCheck(int s, int t) {
        boolean isEq = true;
        for (int j : C.get(s)){
            time += 2;
            int compRes = Double.compare(input[s][j], input[t][j]);
            if(compRes < 0) {
                return false;
            }
            if(compRes > 0) {
                isEq = false;
            }
        }
        return !isEq;
    }

    private void initL() {
        L = new ArrayList<>(dim);
        for(int x = 0; x < dim; x++) {
            L.add(new ArrayList<>(size));
            for(int y = 0; y < size; y++){
                L.get(x).add(new TreeSet<>());
            }
        }
    }

    private void fillC() {
        for(int i = 0; i < size; i++) {
            C.add(new TreeSet<>());
            for(int j = 0; j < dim; j++) {
                C.get(i).add(j);
            }

        }
        System.out.print("C = ");
        for(int x = 0; x < size; x++) {
            System.out.print(C.get(x).toString() + " ");
        }
        System.out.println();
    }

    private void fillQ_second_edition(){
        int [] indices = new int[size];
        for (int i = 0; i < size; ++i) {
            indices[i] = i;
        }
        if(dim > 0) {
            sorter.lexSort(indices, 0, size, input, new int[size]);
        }
        for(int i = 0; i < dim; i++) {
            System.arraycopy(sorter.indices, 0, Q[i], 0, size);
            scratchByKthObj = new int[size];
            sortIntByTthObj(0, size, i);
            scratchByKthObj = null;

            System.out.print("Q[" + i + "] = ");
            for(int x = 0; x < size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();
        }

        time = sorter.time;
    }

    private void sortIntByTthObj(int from, int until, int t) {
        if (from + 1 < until) {
            int mid = (from + until) >>> 1;
            sortIntByTthObj(from, mid, t);
            sortIntByTthObj(mid, until, t);
            int i = from, j = mid, k = 0, kMax = until - from;
            while (k < kMax) {
                sorter.time += 2;
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


}