import units.Reader;

import static units.Assert.check;

public class Temp {
    public static void main(String[] args) throws Exception {
        Reader reader = new Reader("cube_100000_20_result.txt");
        Reader reader_opt = new Reader("cube_opt_100000_20_result.txt");

        int cnt_f = 0;
        int cnt_b = 0;
        int N, M, N_opt, M_opt;
        long t_f, t_b, t_f_opt, t_b_opt;
        String w;
        while(reader.hasMore()) {
            N =  reader.nextInt();
            M =  reader.nextInt();
            N_opt =  reader_opt.nextInt();
            M_opt =  reader_opt.nextInt();

            check(N == N_opt);
            check(M == M_opt);

            t_f = reader.nextLong();
            t_b = reader.nextLong();

            t_f_opt = reader_opt.nextLong();
            t_b_opt = reader_opt.nextLong();

            w = reader.nextWord();
            w = reader_opt.nextWord();

            if(t_b > t_b_opt) {
                cnt_b++;
            }
        }
        System.out.println(cnt_f);
        System.out.println(cnt_b);
    }

}
