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
    int[] indices;
    int[] eqComp;
    int secondIndex = -1;
//    MergeSorter sorter;

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
        scratch = new int[size];
//        sorter = new MergeSorter(size);
    }

    protected void sortImpl(double[][] input, int[] output) {
        System.out.println("BOS");
        this.input = input;
        this.output = output;

        fillQ_second_edition();
//        fillQ();
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
        if(isEq) {
            return false;
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
        //TODO add lex sorted

        for(int i = 0; i < dim; i++) {

            for(int j = 0; j < size; j++) {
                Q[i][j] = j;
            }


            scratch = new int[size];
            sortIntByKthObj(0, size, i);
            scratch = null;

            System.out.print("Q[" + i + "] = ");
            for(int x = 0; x < size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();

        }
    }

    private void fillQ_second_edition(){
        indices = new int[size];
        eqComp = new int[size];
        for (int i = 0; i < size; ++i) {
            indices[i] = i;
        }
        if(dim > 0) {
            lexSortImpl(0, size, 0, 0);
        }
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < size; j++) {
                Q[i][j] = indices[j];
            }
            scratch = new int[size];
            sortIntByKthObj(0, size, i);
            scratch = null;

            System.out.print("Q[" + i + "] = ");
            for(int x = 0; x < size; x++) {
                System.out.print(Q[i][x] + " ");
            }
            System.out.println();

        }

    }

    private void sortIntByKthObj(int from, int until, int t) {
        if (from + 1 < until) {
            int mid = (from + until) >>> 1;
            sortIntByKthObj(from, mid, t);
            sortIntByKthObj(mid, until, t);
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

    private int lexSortImpl(int from, int until, int currIndex, int compSoFar) {
        if (from + 1 < until) {
            secondIndex = currIndex;
            sortImpl(from, until);
            secondIndex = -1;
            if (currIndex + 1 == input[0].length) {
                eqComp[indices[from]] = compSoFar;
                for (int i = from + 1; i < until; ++i) {
                    int prev = indices[i - 1], curr = indices[i];
                    if (input[prev][currIndex] != input[curr][currIndex]) {
                        ++compSoFar;
                    }
                    eqComp[curr] = compSoFar;
                }
                return compSoFar + 1;
            } else {
                int lastIndex = from;
                for (int i = from + 1; i < until; ++i) {
                    if (input[indices[lastIndex]][currIndex] != input[indices[i]][currIndex]) {
                        compSoFar = lexSortImpl(lastIndex, i, currIndex + 1, compSoFar);
                        lastIndex = i;
                    }
                }
                return lexSortImpl(lastIndex, until, currIndex + 1, compSoFar);
            }
        } else {
            eqComp[indices[from]] = compSoFar;
            return compSoFar + 1;
        }
    }

    private void sortImpl(int from, int until) {
        if (from + 1 < until) {

            int mid = (from + until) >>> 1;
            sortImpl(from, mid);
            sortImpl(mid, until);
            int i = from, j = mid, k = 0, kMax = until - from;
            while (k < kMax) {
                if (i == mid || j < until && input[indices[j]][secondIndex] < input[indices[i]][secondIndex]) {
                    scratch[k] = indices[j];
                    ++j;
                } else {
                    scratch[k] = indices[i];
                    ++i;
                }
                ++k;
            }
            System.arraycopy(scratch, 0, indices, from, kMax);
        }
    }
}