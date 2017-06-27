package com.hybrid.sorter;

// Empty sorter: to rule out the case of empty input array.
final class SorterEmpty extends SorterFast {
    SorterEmpty(int dim) {
        super(0, dim);
    }
    public void sortImpl(double[][] input, int[] output) {
        // do nothing
    }
}
