


public class EvalXminInt {
    private int M, l, r, Xmin;
    private int eps = 2;
    int step;

    EvalXminInt(int N) {
        l = 0;
        r = N;
        step = 0;
    }

    int find() {
        while(r - l > eps) {
            step++;
            int a = (l * 2 + r) / 3;
            int b = (l + r * 2) / 3;
            double fa = f(a);
            double fb = f(b);
            if(f(a) < f(b)) {
                r = b;
            } else {
                l = a;
            }
        }
        Xmin = (r + l) / 2;
        return Xmin;
    }

    double f(int x) {
//        System.out.println(x);
        return (x-33)*(x-33) + 27.54234534; // TODO add real func
    }

}
