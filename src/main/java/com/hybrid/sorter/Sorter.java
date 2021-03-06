package com.hybrid.sorter;

import java.util.function.Consumer;

/**
 * A base class for all sorters.
 * A sorter supports two getter methods (for size and dimension)
 * and the method for actual sorting.
 */
public abstract class Sorter {
    protected int size;
    protected int dim;
    protected final int capacity_size;
    protected final int capacity_dim;
    protected Sorter(int size, int dim) {
        this.size = size;
        this.dim = dim;
        this.capacity_size = size;
        this.capacity_dim = dim;
    }

     /**
     * Returns the size of the problem this sorter can handle.
     * @return the size of the problem.
     */
    public int size() {
        return size;
    }
    /**
     * Returns the dimension of the problem this sorter can handle.
     * @return the dimension of the problem.
     */
    public int dimension() {
        return dim;
    }
    /**
     * Performs the non-dominated sorting of the given input array
     * and stores the results in the given output array.
     *
     * The input array should have the dimensions of exactly {#size()} * {#dimension()},
     * otherwise an IllegalArgumentException is thrown.
     *
     * The output array should have the dimension of exactly {#size()},
     * otherwise an IllegalArgumentException is thrown.
     *
     * The method does not change the {#input} array and fills the {#output} array by layer indices:
     * <code>i</code>th element of {#output} will be the layer index of the <code>i</code>th point from {#input}.
     * The layer 0 corresponds to the non-dominated layer of solutions, the layer 1 corresponds to solutions which
     * are dominated by solutions from layer 0 only, and so far.
     *
     * @param input is array which is to be sorted.
     * @param output is array which is filled with the front indices of the corresponding input elements.
     */
    public void sort(double[][] input, int[] output) {
        if(this instanceof SorterBOS) {
            sortImpl(input, output);
            return;
        }

        if (input.length != size) {
            throw new IllegalArgumentException(
                    "Input size (" + input.length + ") does not match the sorter's size (" + size + ")"
            );
        }
        if (output.length != size) {
            throw new IllegalArgumentException(
                    "Output size (" + output.length + ") does not match the sorter's size (" + size + ")"
            );
        }
        for (int i = 0; i < size; ++i) {
            if (input[i].length != dim) {
                throw new IllegalArgumentException(
                        "Input dimension at index " + i + " (" + input[i].length +
                                ") does not match the sorter's dimension (" + dim + ")"
                );
            }
        }
        sortImpl(input, output);
    }
    public abstract void sortImpl(double[][] input, int[] output);
    protected abstract void print_info();
    public void setParamAnalysis(boolean withLogging, Consumer<double[][]> out) {
        throw new UnsupportedOperationException("setParamAnalysis is not implemented");
    }

    protected void sortImplSpecial(double[][] input, int[] output, int[] indices, int from, int until, int d) {
        throw new UnsupportedOperationException("sortImplSpecial is not implemented");
    }
}
