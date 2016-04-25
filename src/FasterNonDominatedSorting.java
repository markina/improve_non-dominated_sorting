public class FasterNonDominatedSorting extends FactoryNonDominatedSorting {

    @Override
    public Sorter getSorter(int size, int dim) {
        if(dim < 0 || size < 0) {
            throw new IllegalArgumentException("Size or dimension is negative");
        }

        if(size == 0) {
            return new SorterEmpty(dim);
        }
        switch(dim) {
            case 0:
                return new Sorter0D(size);
            case 1:
                return new Sorter1D(size);
            case 2:
                return new Sorter2D(size);
            default:
                return new SorterXD(size, dim);
        }
    }
}
