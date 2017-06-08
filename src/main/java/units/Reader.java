package main.java.units;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Reader {
    BufferedReader in;
    String curLine;
    StringTokenizer tok;
    final String delimeter = " ";
    final String endOfFile = "";

    public Reader(String filename) throws Exception {
        in = new BufferedReader(new FileReader(filename));
        curLine = in.readLine();
        if (curLine == null || curLine == endOfFile) {
            tok = null;
        } else {
            tok = new StringTokenizer(curLine, delimeter);
        }
    }

    public boolean hasMore() throws Exception {
        if (tok == null || curLine == null) {
            return false;
        } else {
            while (!tok.hasMoreTokens()) {
                curLine = in.readLine();
                if (curLine == null || curLine.equalsIgnoreCase(endOfFile)) {
                    tok = null;
                    return false;
                } else {
                    tok = new StringTokenizer(curLine);
                }
            }
            return true;
        }
    }

    public String nextWord() throws Exception {
        if (!hasMore()) {
            return null;
        } else {
            return tok.nextToken();
        }
    }

    public int nextInt() throws Exception {
        return Integer.parseInt(nextWord());
    }

    public double nextDouble() throws Exception {
        return Double.parseDouble(nextWord());
    }

    public long nextLong() throws Exception {
        return Long.parseLong(nextWord());
    }

    public void close() throws Exception {
        in.close();
    }

    public double[][] getNextData() throws Exception {

        int N = nextInt();
        int M = nextInt();
        double[][] input = new double[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                input[i][j] = nextDouble();
            }
        }
        return input;
    }
}
