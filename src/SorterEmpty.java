// Empty sorter: to rule out the case of empty input array.
final class SorterEmpty extends Sorter {
    public SorterEmpty(int dim) {
        super(0, dim);
    }
    protected void sortImpl(double[][] input, int[] output) {
        // do nothing
    }
}