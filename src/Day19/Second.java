package Day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Second {
    public static void main(String[] args) throws IOException {
        ArrayList<String> towels = new ArrayList<>();
        ArrayList<String> designs = new ArrayList<>();
        boolean order = true;

        Reader in = new FileReader("src/Day19/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                order = false;
                continue;
            }
            if (order) {
                String[] tokens = line.trim().split(",");
                for (String token : tokens) {
                    towels.add(token.trim());
                }
            } else {
                designs.add(line.trim());
            }
        }

        long totalDesigns = 0;
        for (String design : designs) {
            long[] dp = new long[design.length() + 1];
            dp[0] = 1;

            for (int i = 0; i < design.length(); i++) {
                if (dp[i] == 0) continue;

                for (String towel : towels) {
                    int end = i + towel.length();
                    if (end <= design.length()) {
                        String substring = design.substring(i, end);
                        if (substring.equals(towel)) {
                            dp[end] += dp[i];
                        }
                    }
                }
            }

            totalDesigns += dp[design.length()];
        }

        System.out.println(totalDesigns);
        in.close();
    }
}