import java.io.PrintWriter;

// 1D sorter: do the sorting and uniquification.
final class Sorter1D extends SorterFast {
    private final int[] indices;
    private final MergeSorter sorter;

    Sorter1D(int size) {
        super(size, 1);
        indices = new int[size];
        sorter = new MergeSorter(size);
    }

    protected void sortImpl(double[][] input, int[] output) {
        for (int i = 0; i < size; ++i) {
            indices[i] = i;
        }
        sorter.sort(indices, 0, size, input, 0);
        output[indices[0]] = 0;
        for (int i = 1; i < size; ++i) {
            int prev = indices[i - 1], curr = indices[i];
            if (input[prev][0] == input[curr][0]) {
                output[curr] = output[prev];
            } else {
                output[curr] = output[prev] + 1;
            }
        }
    }

    @Override
    protected void setParamAnalysis(boolean withLogging, PrintWriter out) {}
}