package com.hybrid.sorter;

public class BOSNonDominatedSorting extends FactoryNonDominatedSorting {

    @Override
    public Sorter getSorter(int size, int dim) {
        if (dim < 0 || size < 0) {
            throw new IllegalArgumentException("Size or dimension is negative");
        }

        if (size == 0) {
            return new SorterEmpty(dim);
        }

        return new SorterBOS(size, dim);
    }
}
