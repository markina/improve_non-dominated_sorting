public abstract class SorterFast  extends Sorter {
    public SorterFast(int size, int dim) {
        super(size, dim);
    }

    @Override
    protected void print_info() {
        System.out.println("--------------");
        System.out.println("SorterFast");
        System.out.println("N = " + size + "; M = " + dim);
        print_time();

    }


}
