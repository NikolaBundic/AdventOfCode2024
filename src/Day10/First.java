package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        int solution = 0;

        Reader in = new FileReader("src/Day10/inputFile.txt");
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
                if(matrix.get(i).get(j).equals('0')) {
                    ArrayList<Integer> visited = new ArrayList<>();
                    solution += countUp(matrix, 0, j, i, visited);
                }
            }
        }

        System.out.println(solution);
    }

    private static int countUp(ArrayList<ArrayList<Character>> matrix, int i, int x, int y, ArrayList<Integer> visited) {
        int solution = 0;
        int positionHash = x * matrix.size() + y;
        if (i == 9 && matrix.get(y).get(x) == (char) ('0' + i) && !visited.contains(positionHash)) {
            solution++;
            visited.add(positionHash);
        } else if (i < 9 && matrix.get(y).get(x) == (char) ('0' + i) && !visited.contains(positionHash)) {
            visited.add(positionHash);
            if (y + 1 < matrix.size()) {
                solution += countUp(matrix, i + 1, x, y + 1, visited);
            }
            if (y - 1 >= 0) {
                solution += countUp(matrix, i + 1, x, y - 1, visited);
            }
            if (x + 1 < matrix.getFirst().size()) {
                solution += countUp(matrix, i + 1, x + 1, y, visited);
            }
            if (x - 1 >= 0) {
                solution += countUp(matrix, i + 1, x - 1, y, visited);
            }
        }
        return solution;
    }
}
