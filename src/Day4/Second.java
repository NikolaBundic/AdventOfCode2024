package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Second {
    public static void main(String[] args) throws IOException {
        int solution = 0;
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();

        Reader in = new FileReader("src/Day4/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            matrix.add(row);
        }

        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0 ; j < matrix.get(i).size() ; j++) {
                if(matrix.get(i).get(j).equals('A')) {
                    solution += countUp(matrix, j, i);
                }
            }
        }

        System.out.println(solution);
    }

    private static int countUp(ArrayList<ArrayList<Character>> matrix, int x, int y) {
        int solution = 0;

        if(y - 1 >= 0 && x - 1 >= 0 && y + 1 < matrix.size() && x + 1 < matrix.getFirst().size()) {
            StringBuilder s = new StringBuilder();
            StringBuilder s2 = new StringBuilder();

            s.append(matrix.get(y-1).get(x-1));
            s.append(matrix.get(y).get(x));
            s.append(matrix.get(y+1).get(x+1));

            s2.append(matrix.get(y-1).get(x+1));
            s2.append(matrix.get(y).get(x));
            s2.append(matrix.get(y+1).get(x-1));

            List<String> valid = Arrays.asList("MAS", "SAM");
            if(valid.contains(s.toString()) && valid.contains(s2.toString())) {
                solution += 1;
            }
        }

        return solution;
    }
}
