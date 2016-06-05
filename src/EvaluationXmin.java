


public class EvaluationXmin {
    double l, r;
    private int Xmin;
    private double eps = 0.01;
    int step;

    EvaluationXmin(int N) {
        l = 0;
        r = Math.log(N);
        step = 0;
    }

    int find() {
        while(r - l > eps) {
            step++;
            double a = (l * 2 + r) / 3;
            double b = (l + r * 2) / 3;
            int ia = (int) (Math.pow(Math.E, a));
            int ib = (int) (Math.pow(Math.E, b));
            if(f(ia) < f(ib)) {
                r = b;
            } else {
                l = a;
            }
        }
        Xmin = (int) ((Math.pow(Math.E, l) + Math.pow(Math.E, r)) / 2);
        return Xmin;
    }

    double f(int n) {
//        System.out.println(n);
        return (n-33)*(n-33) + 27.54234534; // TODO add real func
    }

}
