import java.util.*;

// Best Order Sort sorter
final class SorterBOS extends Sorter {
    List<List<Set<Integer>>> L = null;
    List<Set<Integer>> C = null;
//    int [][] C = null;
    boolean [] isRanked = null;
    int SC;
    int RC;
    int [][] Q = null;

    private double[][] input;
    private int[] output;
    int[] scratch;

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
    }

    protected void sortImpl(double[][] input, int[] output) {
        System.out.println("BOS");
        this.input = input;
        this.output = output;

        fillQ();
        fillC();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < dim; j++) {
                int s = Q[j][i];
//                C.get(s).remove(j); TODO think was
                if(isRanked[s]) {
                    L.get(j).get(this.output[s]).add(s);
                } else {
                    findRank(s, j);
                    isRanked[s] = true;
                    SC++;
                }
                C.get(s).remove(j);
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
//        if(C.get(t).size() == 0) {
//            return false;
//        }
        for (int j : C.get(s)){
            if(Double.compare(input[s][j], input[t][j]) < 0) {
                return false;
            }
        }
        return true;
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
            C.add(new TreeSet<Integer>());
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

    private void fillQ() {
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < size; j++) {
                Q[i][j] = j;
            }
            scratch = new int[size];
            sortIntBykthObj(0, size, i);
            scratch = null;

            System.out.print("Q[" + i + "] = ");
            for(int x = 0; x < size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();

        }
    }


    private void sortIntBykthObj(int from, int until, int t) {
        if (from + 1 < until) {
            int mid = (from + until) >>> 1;
            sortIntBykthObj(from, mid, t);
            sortIntBykthObj(mid, until, t);
            int i = from, j = mid, k = 0, kMax = until - from;
            while (k < kMax) {
                if (i == mid || j < until && Double.compare(input[Q[t][j]][t], input[Q[t][i]][t]) < 0) {
                    scratch[k] = Q[t][j];
                    ++j;
                } else {
                    scratch[k] = Q[t][i];
                    ++i;
                }
                ++k;
            }
            System.arraycopy(scratch, 0, Q[t], from, kMax);
        }
    }

}