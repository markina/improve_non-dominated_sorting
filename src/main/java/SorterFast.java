package main.java;

abstract class SorterFast  extends Sorter {
    SorterFast(int size, int dim) {
        super(size, dim);
    }

    @Override
    protected void print_info() {
        System.out.println("--------------");
        System.out.println("main.java.SorterFast");
        System.out.println("N = " + size + "; M = " + dim);
    }


}
