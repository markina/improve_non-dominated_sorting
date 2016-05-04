package units;

//List<Set<Integer>>
public class SmartC {
    private SetOnArray [] C = null;
    private int size;
//    private int dim;

    public SmartC(int size, int dim) {
        this.size = size;
//        this.dim = dim;
        C = new SetOnArray[size];
        for(int i = 0; i < size; i++) {
            C[i] = new SetOnArray(dim);
        }
    }

    public void init() {
        for(int i = 0; i < size; i++) {
            C[i].init();
        }
    }

    public void removeFrom(int s, int j) {
        C[s].remove(j);
    }

    public int [] get(int s) {
        return C[s].toArray();
    }

    private class SetOnArray{
        int cnt;
        int [] elems;
        boolean [] flags;
        int dim;

        SetOnArray(int dim) {
            this.dim = dim;
            elems = new int[dim];
            flags = new boolean[dim];
            init();
        }

        void init() {
            cnt = dim;
            for(int i = 0; i < dim; i++) {
                elems[i] = i;
                flags[i] = true;
            }
        }

        int [] toArray() {
            int to = 0;
            for(int from = 0; to < cnt && from < cnt; ) {
                if(flags[from] && flags[to]) {
                    to++;
                    from++;
                } else if (!flags[from] && !flags[to]){
                    from++;
                } else if (flags[from] && !flags[to]) {
                    elems[to] = elems[from];
                    flags[to] = flags[from];
                    flags[from] = false;
                }
            }
            int [] res = new int[to];
            System.arraycopy(elems, 0, res, 0, to);
            return res;
        }

        void remove(int j) {
            flags[j] = false;
        }


    }
}