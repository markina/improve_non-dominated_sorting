public abstract class SorterFast  extends Sorter {
    public SorterFast(int size, int dim) {
        super(size, dim);
    }

    @Override
    protected double estimated() {
        double log = Math.log(size) / Math.log(2);
        return size * Math.pow(log, dim - 1);
    }

    @Override
    protected void print_info() {
        System.out.println("--------------");
        System.out.println("SorterFast");
        System.out.println("N = " + size + "; M = " + dim);
        System.out.println("O(N (log N) ^ (M-1))= " + estimated());
        print_time();

    }


}
