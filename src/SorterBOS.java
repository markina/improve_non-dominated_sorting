import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

// Best Order Sort sorter
final class SorterBOS extends Sorter {
    private List<List<Set<Integer>>> L = null;
    private List<Set<Integer>> C = null;
    private boolean [] isRanked = null;
    private int SC;
    private int RC;
    private int [][] Q = null;
    private int[] scratchByKthObj;

    private double[][] input;
    private int[] output;
    MergeSorter sorter;

    SorterBOS(int size, int dim) {
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
    }

    protected void sortImpl(double[][] input, int[] output) {
        this.input = input;
        this.output = output;

        fillQ();
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
        // printOutput();
    }

    private void printOutput() {
        System.out.print("output = ");
        for(int i = 0; i < size; i++) {
            System.out.print(output[i] + " ");
        }
    }

    private void findRank(int s, int j) {
        int l = -1;
        int r = RC - 1;
        boolean check;
        while(r - l > 1) {
            int m = (l + r) / 2;
            check = false;
            for (Integer t : L.get(j).get(m)) {
                check = dominationCheck(s, t);
                if(check) {
                    break;
                }
            }
            if(!check) {
                r = m;
            } else {
                l = m;
            }
        }
        check = false;
        for (Integer t : L.get(j).get(r)) {
            check = dominationCheck(s, t);
            if(check) {
                break;
            }
        }
        if(!check) {
            output[s] = r;
            L.get(j).get(output[s]).add(s);
        } else {
            RC++;
            output[s] = RC-1;
            L.get(j).get(output[s]).add(s);
        }

    }

    private boolean dominationCheck(int s, int t) {
        boolean isEq = true;
        for (int j : C.get(s)){
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
        printC();
    }

    private void printC() {
        System.out.print("C = ");
        for(int x = 0; x < size; x++) {
            System.out.print(C.get(x).toString() + " ");
        }
        System.out.println();
    }

    private void printL() {
        System.out.println("L = ");
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < size; j++) {
                L.get(i).get(j).forEach(System.out::print);
            }
            System.out.println();
        }
    }

    private void fillQ(){
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

        }
        printQ();
    }

    private void printQ() {
        for(int i = 0; i < dim; i++) {
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

    @Override
    protected void setParamAnalysis(boolean withLogging, PrintWriter out) throws FileNotFoundException {}
}