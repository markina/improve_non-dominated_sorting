package units;

// List<List<Set<Integer>>>
public class SmartL {
    private int dim;
    private int size;
    private RefStruct [] L;


    public SmartL(int dim, int size) {
        this.dim = dim;
        this.size = size;
        L = new RefStruct[dim];

        for (int x = 0; x < dim; x++) {
            L[x] = new RefStruct(size);
        }
    }

    public void init() {
        for (int x = 0; x < dim; x++) {
            L[x].init();
        }
    }

    public int[] get(int dim_i, int rank_i) {
        return L[dim_i].get(rank_i);
    }

    public void printSout() {
        System.out.println("L = ");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("{ ");
                for (int x : L[i].get(j)) {
                    System.out.print(x + " ");
                }
                System.out.print("}  ");
                System.out.println();
            }
            System.out.println();
        }
    }

    public void addTo(int dim_i, int rank_i, int s) {
        L[dim_i].add(rank_i, s);
    }

    private class RefStruct {
        int[] refToLast; // null == -1
        int[] pref;      // null == -1
        int[] elems;
        int last_id;

        RefStruct(int size) {
            refToLast = new int[size];
            pref = new int[size];
            elems = new int[size]; // empty == -1
            init();
        }

        void init() {
            for (int i = 0; i < size; i++) {
                refToLast[i] = -1;
                pref[i] = -1;
                elems[i] = -1;
            }
            last_id = 0;
        }

        void add(int rank, int s) {
            if (refToLast[rank] == -1) {
                refToLast[rank] = last_id;
                elems[last_id] = s;
                last_id++;
            } else {
                int to = refToLast[rank];
                refToLast[rank] = last_id;
                elems[last_id] = s;
                pref[last_id] = to;
                last_id++;
            }
        }

        int [] get(int rank) {
            if(refToLast[rank] == -1) {
                return new int[0];
            }
            int cnt = 0;
            int p = pref[refToLast[rank]];
            cnt++;
            while (p != -1) {
                p = pref[p];
                cnt++;
            }
            int [] res = new int[cnt];

            p = pref[refToLast[rank]];
            cnt--;
            res[cnt] = elems[refToLast[rank]];
            cnt--;
            while (p != -1) {
                res[cnt] = elems[p];
                p = pref[p];
                cnt--;
            }
            return res;
        }


    }


}
