import javafx.util.Pair;
import units.Reader;

import java.util.*;
import java.util.jar.Pack200;

/**
 * Created by mmarkina on 03/06/16.
 */
public class Imitation {
    int l, r;
    private int Xmin;
    private int eps = 2;
    private int N;
    List<Pair<Integer, Double>> med_points = new ArrayList<>();

    Random rnd = new Random(23);

    Imitation(int N, int M, String test_name, String dir) throws Exception {
        this.N = N;

        Map<Integer, List<Double>> mp = new HashMap<>();
        Reader in = new Reader(dir + test_name + "_" + N + "_" + M + "_result.txt");

        while (in.hasMore()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();
            double tf = in.nextDouble();
            double tb = in.nextDouble();
            double th = in.nextDouble();
            if(!mp.containsKey(n)) {
                mp.put(n, new ArrayList<>());
            }
            mp.get(n).add((tb - tf) / Math.max(tb, tf));
        }

        for (int k: mp.keySet()) {
            int sz = mp.get(k).size();
            double sum = 0;
            if(sz < 15) {
                for(int i = 0; i < sz; i++) {
                    sum += mp.get(k).get(i);
                }
            } else {
                for(int i = 0; i < 15; i++) {
                    sum += mp.get(k).get(rnd.nextInt(sz));
                }
            }
            med_points.add(new Pair<>(k, sum/Math.min(15, sz)));
        }

        Collections.sort(med_points, new Comparator<Pair<Integer, Double>>() {
            @Override
            public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
                return o1.getKey() - o2.getKey();
            }
        });

    }

    int find() {
        l = 0;
        r = med_points.size()-1;
        while(r - l > eps) {
            int a = (l * 2 + r) / 3;
            int b = (l + r * 2) / 3;
            double fa = f(a);
            double fb = f(b);
            if(Math.max(fa, fb) - Math.min(fa, fb) < 0.05) {
                double fr = f(r);
                double fl = f(l);
                if(Math.abs(fr - fb) > Math.abs(fl - fa)) {
                    r = b;
                } else {
                    l = a;
                }
            } else if(fa < fb) {
                r = b;
            } else {
                l = a;
            }

        }
        Xmin = med_points.get(r).getKey();
        return Xmin;
    }

    double f(int n) {
        return med_points.get(n).getValue(); // TODO return the average time((tb - tf)/max) of 15 runs with n random points
    }


}
