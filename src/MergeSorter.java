class MergeSorter {
    final int[] scratch;
    int[] indices = null;
    int secondIndex = -1;
    double[][] reference = null;
    int[] eqComp = null;
    int time = 0;

    public MergeSorter(int size) {
        this.scratch = new int[size];
        this.indices = new int[size];
    }


    public void lexSort(int[] indices, int from, int until, double[][] reference, int[] eqComp) {
        this.indices = indices;
        this.reference = reference;
        this.eqComp = eqComp;
        this.time = 0;
        lexSortImpl(from, until, 0, 0);
        this.eqComp = null;
        this.reference = null;

//        this.indices = null;
    }

    private int lexSortImpl(int from, int until, int currIndex, int compSoFar) {
        if (from + 1 < until) {
            secondIndex = currIndex;
            sortImpl(from, until);
            secondIndex = -1;
            if (currIndex + 1 == reference[0].length) {
                eqComp[indices[from]] = compSoFar;
                for (int i = from + 1; i < until; ++i) {
                    int prev = indices[i - 1], curr = indices[i];
                    time += 2;
                    if (reference[prev][currIndex] != reference[curr][currIndex]) {
                        ++compSoFar;
                    }
                    eqComp[curr] = compSoFar;
                }
                return compSoFar + 1;
            } else {
                int lastIndex = from;
                for (int i = from + 1; i < until; ++i) {
                    time += 2;
                    if (reference[indices[lastIndex]][currIndex] != reference[indices[i]][currIndex]) {
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

    public void sort(int[] indices, int from, int until, double[][] reference, int secondIndex) {
        this.indices = indices;
        this.reference = reference;
        this.secondIndex = secondIndex;
        sortImpl(from, until);
        this.indices = null;
        this.reference = null;
        this.secondIndex = -1;
    }

    private void sortImpl(int from, int until) {
        if (from + 1 < until) {

            int mid = (from + until) >>> 1;
            sortImpl(from, mid);
            sortImpl(mid, until);
            int i = from, j = mid, k = 0, kMax = until - from;
            while (k < kMax) {
                time += 2;
                if (i == mid || j < until && reference[indices[j]][secondIndex] < reference[indices[i]][secondIndex]) {
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