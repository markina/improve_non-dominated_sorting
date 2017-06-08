package main.java.units;

import java.util.Arrays;

// List<List<Set<Integer>>>
public class SmartL {
    private int capacity_dim;
    private int capacity_size;
    private RefStruct [] L;
    private int size;
    private int dim;

    public SmartL(int capacity_dim, int capacity_size) {
        this.capacity_dim = capacity_dim;
        this.capacity_size = capacity_size;
        L = new RefStruct[capacity_dim];

        for (int x = 0; x < capacity_dim; x++) {
            L[x] = new RefStruct(capacity_size);
        }
    }

    public void init(int sz, int d) {
        size = sz;
        dim = d;
        if(d > capacity_dim || sz > capacity_size) {
            System.out.println("capacity_dim > capacity_dim || size > capacity_size");
            System.out.println("size = " + size);
            System.out.println("dim = " + dim);
            System.out.println("c_size = " + capacity_size);
            System.out.println("c_d = " + capacity_dim);
        }
        for (int x = 0; x < d; x++) {
            L[x].init(capacity_size);
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
        private int[] refToLast; // null == -1
        private int[] pref;      // null == -1
        private int[] elems;
        private int last_id;
        private int capacity_size;

        RefStruct(int sz) {
            capacity_size = sz;
            refToLast = new int[sz];
            pref = new int[sz];
            elems = new int[sz]; // empty == -1
            init(sz);
        }

        void init(int sz) {
            if(sz > capacity_size) {
                System.out.println("sz > capacity_size");
            }
            Arrays.fill(refToLast, -1);
            Arrays.fill(pref, -1);
            Arrays.fill(elems, -1);

//            for (int i = 0; i < sz; i++) {
//                refToLast[i] = -1;
//                pref[i] = -1;
//                elems[i] = -1;
//            }
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
