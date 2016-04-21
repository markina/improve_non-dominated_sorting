import java.util.Arrays;

// 0D sorter: zero out the answer.
final class Sorter0D extends SorterFast {
    public Sorter0D(int size) {
        super(size, 0);
    }

    protected void sortImpl(double[][] input, int[] output) {
        Arrays.fill(output, 0);
    }

    @Override
    protected void setParamAnalysis(boolean withTiming, boolean withLogging) {}
}