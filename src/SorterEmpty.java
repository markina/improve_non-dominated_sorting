import java.io.PrintWriter;

// Empty sorter: to rule out the case of empty input array.
final class SorterEmpty extends SorterFast {
    SorterEmpty(int dim) {
        super(0, dim);
    }
    protected void sortImpl(double[][] input, int[] output) {
        // do nothing
    }
}
