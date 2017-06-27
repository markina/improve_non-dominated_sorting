package com.hybrid.sorter;

import com.hybrid.sorter.units.LogD;

import java.util.*;
import java.util.function.Consumer;

// Hybrid sorter
final class SorterHybrid extends Sorter {
    private final int[] indices;
    private final int[] swap;
    private final int[] eqComp;
    private final MergeSorter sorter;

    private double[][] input;
    private int[] output;

    private final Random random = new Random();

    private boolean logging = false;
    private Consumer<double[][]> out = null;

    private Sorter sorterBOS;

    private final TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
        public int compare(Integer lhs, Integer rhs) {
            int ilhs = lhs, irhs = rhs;
            int cmp1 = Double.compare(input[ilhs][1], input[irhs][1]);
            if(cmp1 != 0) {
                return cmp1;
            } else {
                return Double.compare(input[ilhs][0], input[irhs][0]);
            }
        }
    });

    SorterHybrid(int size, int dim) {
        super(size, dim);
        indices = new int[size];
        eqComp = new int[size];
        swap = new int[size];
        sorter = new MergeSorter(size);
        sorterBOS = new BOSNonDominatedSorting().getSorter(size, dim);
    }

    public void sortImpl(double[][] input, int[] output) {
        for(int i = 0; i < size; ++i) {
            indices[i] = i;
        }
        Arrays.fill(output, 0);
        sorter.lexSort(indices, 0, size, input, eqComp);
        this.input = input;
        this.output = output;
        sort(0, size, dim - 1);
        this.input = null;
        this.output = null;
    }


    private void updateFront(int target, int source) {
        if(eqComp[target] == eqComp[source]) {
            output[target] = output[source];
        } else {
            output[target] = Math.max(output[target], output[source] + 1);
        }
    }

    private void cleanup(int curr) {
        Iterator<Integer> greaterIterator = set.tailSet(curr, true).iterator();
        while(greaterIterator.hasNext()) {
            if(output[greaterIterator.next()] <= output[curr]) {
                greaterIterator.remove();
            } else {
                break;
            }
        }
    }

    private void sort2D(int from, int until) {
        for(int i = from; i < until; ++i) {
            int curr = indices[i];
            Iterator<Integer> lessIterator = set.headSet(curr, true).descendingIterator();
            if(lessIterator.hasNext()) {
                updateFront(curr, lessIterator.next());
            }
            cleanup(curr);
            set.add(curr);
        }
        set.clear();
    }

    private void sortHighByLow2D(int lFrom, int lUntil, int hFrom, int hUntil) {
        int li = lFrom;
        for(int hi = hFrom; hi < hUntil; ++hi) {
            int currH = indices[hi];
            int eCurrH = eqComp[currH];
            while(li < lUntil && eqComp[indices[li]] < eCurrH) {
                int curr = indices[li++];
                Iterator<Integer> lessIterator = set.headSet(curr, true).descendingIterator();
                if(!lessIterator.hasNext() || output[lessIterator.next()] < output[curr]) {
                    cleanup(curr);
                    set.add(curr);
                }
            }
            Iterator<Integer> lessIterator = set.headSet(currH, true).descendingIterator();
            if(lessIterator.hasNext()) {
                updateFront(currH, lessIterator.next());
            }
        }
        set.clear();
    }

    private double medianInSwap(int from, int until, int dimension) {
        int to = until - 1;
        int med = (from + until) >>> 1;
        while(from <= to) {
            double pivot = input[swap[from + random.nextInt(to - from + 1)]][dimension];
            int ff = from, tt = to;
            while(ff <= tt) {
                while(input[swap[ff]][dimension] < pivot) {
                    ++ff;
                }
                while(input[swap[tt]][dimension] > pivot) {
                    --tt;
                }
                if(ff <= tt) {
                    int tmp = swap[ff];
                    swap[ff] = swap[tt];
                    swap[tt] = tmp;
                    ++ff;
                    --tt;
                }
            }
            if(med <= tt) {
                to = tt;
            } else if(med >= ff) {
                from = ff;
            } else {
                return input[swap[med]][dimension];
            }
        }
        return input[swap[from]][dimension];
    }

    private int lessThan, equalTo, greaterThan;

    private void split3(int from, int until, int dimension, double median) {
        lessThan = equalTo = greaterThan = 0;
        for(int i = from; i < until; ++i) {
            int cmp = Double.compare(input[indices[i]][dimension], median);
            if(cmp < 0) {
                ++lessThan;
            } else if(cmp == 0) {
                ++equalTo;
            } else {
                ++greaterThan;
            }
        }
        int lessThanPtr = 0, equalToPtr = lessThan, greaterThanPtr = lessThan + equalTo;
        for(int i = from; i < until; ++i) {
            int cmp = Double.compare(input[indices[i]][dimension], median);
            if(cmp < 0) {
                swap[lessThanPtr++] = indices[i];
            } else if(cmp == 0) {
                swap[equalToPtr++] = indices[i];
            } else {
                swap[greaterThanPtr++] = indices[i];
            }
        }
        System.arraycopy(swap, 0, indices, from, until - from);
    }

    private void merge(int from, int mid, int until) {
        int p0 = from, p1 = mid;
        for(int i = from; i < until; ++i) {
            if(p0 == mid || p1 < until && eqComp[indices[p1]] < eqComp[indices[p0]]) {
                swap[i] = indices[p1++];
            } else {
                swap[i] = indices[p0++];
            }
        }
        System.arraycopy(swap, from, indices, from, until - from);
    }

    private void sortHighByLow(int lFrom, int lUntil, int hFrom, int hUntil, int dimension) {
        int lSize = lUntil - lFrom, hSize = hUntil - hFrom;
        if(lSize == 0 || hSize == 0) {
            return;
        }
        if(lSize == 1) {
            for(int hi = hFrom; hi < hUntil; ++hi) {
                if(dominatesEq(lFrom, hi, dimension)) {
                    updateFront(indices[hi], indices[lFrom]);
                }
            }
        } else if(hSize == 1) {
            for(int li = lFrom; li < lUntil; ++li) {
                if(dominatesEq(li, hFrom, dimension)) {
                    updateFront(indices[hFrom], indices[li]);
                }
            }
        } else if(dimension == 1) {
            sortHighByLow2D(lFrom, lUntil, hFrom, hUntil);
        } else {
            if(maxValue(lFrom, lUntil, dimension) <= minValue(hFrom, hUntil, dimension)) {
                sortHighByLow(lFrom, lUntil, hFrom, hUntil, dimension - 1);
            } else {
                System.arraycopy(indices, lFrom, swap, 0, lSize);
                System.arraycopy(indices, hFrom, swap, lSize, hSize);
                double median = medianInSwap(0, lSize + hSize, dimension);

                split3(lFrom, lUntil, dimension, median);
                int lMidL = lFrom + lessThan, lMidR = lMidL + equalTo;

                split3(hFrom, hUntil, dimension, median);
                int hMidL = hFrom + lessThan, hMidR = hMidL + equalTo;

                sortHighByLow(lFrom, lMidL, hFrom, hMidL, dimension);
                sortHighByLow(lFrom, lMidL, hMidL, hMidR, dimension - 1);
                sortHighByLow(lMidL, lMidR, hMidL, hMidR, dimension - 1);
                merge(lFrom, lMidL, lMidR);
                merge(hFrom, hMidL, hMidR);
                sortHighByLow(lFrom, lMidR, hMidR, hUntil, dimension - 1);
                sortHighByLow(lMidR, lUntil, hMidR, hUntil, dimension);
                merge(lFrom, lMidR, lUntil);
                merge(hFrom, hMidR, hUntil);
            }
        }
    }

    private void sort(int from, int until, int dimension) {
        int size = until - from;

        if(needChooseBOS(size, dimension+1)) {
            sorterBOS.sortImplSpecial(input, output, indices, from, until, dimension+1);
            return;
        }

        if(logging && size >= 2) {
            double[][] test = new double[size][];
            for (int i = 0; i < size; ++i) {
                test[i] = Arrays.copyOf(input[indices[from + i]], dimension + 1);
            }
            out.accept(test);
        }
        if(size == 2) {
            if(dominatesEq(from, from + 1, dimension)) {
                updateFront(indices[from + 1], indices[from]);
            }
        } else if(size > 2) {
            if(dimension == 1) {
                sort2D(from, until);
            } else {
                if(allValuesEqual(from, until, dimension)) {
                    sort(from, until, dimension - 1);
                } else {
                    System.arraycopy(indices, from, swap, from, size);
                    double median = medianInSwap(from, until, dimension);

                    split3(from, until, dimension, median);
                    int midL = from + lessThan, midH = midL + equalTo;

                    sort(from, midL, dimension);
                    sortHighByLow(from, midL, midL, midH, dimension - 1);
                    sort(midL, midH, dimension-1);
                    merge(from, midL, midH);
                    sortHighByLow(from, midH, midH, until, dimension - 1);
                    sort(midH, until, dimension);
                    merge(from, midH, until);
                }
            }
        }
    }

    private boolean needChooseBOS(int sz, int d) {
//        if(1.5 * d * LogD.log[d + 1] < sz && sz < 600 * 1.5 * d * (LogD.log[d + 1] - 1.37)) {
//            return true;
//        }
//        return false;

//        if(1.5 * d * LogD.log[d + 1] < sz && sz < ChangePointEvaluation.changePoints[d]) {
        if(d * LogD.log[d + 1] < sz && sz < 150 * d * (Math.pow(LogD.log[d + 1], 0.9) - 1.5)) {
            return true;
        } 
        return false;
    }

    private boolean allValuesEqual(int from, int until, int k) {
        double value = input[indices[from]][k];
        for(int i = from + 1; i < until; ++i) {
            if(input[indices[i]][k] != value) {
                return false;
            }
        }
        return true;
    }

    private double minValue(int from, int until, int k) {
        double rv = Double.MAX_VALUE;
        for(int i = from; i < until; ++i) {
            rv = Math.min(rv, input[indices[i]][k]);
        }
        return rv;
    }

    private double maxValue(int from, int until, int k) {
        double rv = Double.MIN_VALUE;
        for(int i = from; i < until; ++i) {
            rv = Math.max(rv, input[indices[i]][k]);
        }
        return rv;
    }

    private boolean dominatesEq(int l, int r, int k) {
        int il = indices[l];
        int ir = indices[r];
        for(int i = 0; i <= k; ++i) {
            if(input[il][i] > input[ir][i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setParamAnalysis(boolean withLogging, Consumer<double[][]> out) {
        logging = withLogging;
        this.out = out;
    }

    @Override
    protected void print_info() {
        System.out.println("--------------");
        System.out.println("SorterHybrid");
        System.out.println("N = " + size + "; M = " + dim);
    }

}
