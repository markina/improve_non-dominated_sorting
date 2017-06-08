package main.java.units;

//List<Set<Integer>>
public class SmartC {
    private SetOnArray [] C = null;
    private int capacity_size;
    private int capacity_dim;

    public SmartC(int capacity_size, int capacity_dim) {
        this.capacity_size = capacity_size;
        this.capacity_dim = capacity_dim;
        C = new SetOnArray[capacity_size];
        for(int i = 0; i < capacity_size; i++) {
            C[i] = new SetOnArray(capacity_dim);
        }
    }

    public void init(int sz, int d) {
        if(sz > capacity_size || d > capacity_dim) {
            System.out.print("sz > capacity_size || d > capacity_dim");
        }

        for(int i = 0; i < sz; i++) {
            C[i].init(d);
        }
    }

    public void removeFrom(int s, int j) {
        C[s].remove(j);
    }

    public int [] get(int s) {
        return C[s].toArray();
    }

    private class SetOnArray{
        private int cnt;
        private int [] elems;
        private boolean [] flags;
        private int capacity_dim;

        SetOnArray(int capacity_dim) {
            this.capacity_dim = capacity_dim;
            elems = new int[capacity_dim];
            flags = new boolean[capacity_dim];
            init(capacity_dim);
        }

        void init(int d) {
            if(d > capacity_dim) {
                System.out.print("d > capacity_dim");
            }
            cnt = d;
            for(int i = 0; i < capacity_dim; i++) {
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
