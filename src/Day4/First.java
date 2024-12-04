package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class First {
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
                            // E       SE       S       SW       W         NW        N       NE
        int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0 ; j < matrix.get(i).size() ; j++) {
                if(matrix.get(i).get(j).equals('X')) {
                    solution += countUp(matrix, 0, directions, j, i);
                }
            }
        }

        System.out.println(solution);
    }

    private static int countUp(ArrayList<ArrayList<Character>> matrix, int i, int[][] directions, int x, int y) {
        int solution = 0;

        if(y + directions[i][0] * 3 >= 0 && x + directions[i][1] * 3 >= 0 && y + directions[i][0] * 3 < matrix.size() && x + directions[i][1] * 3 < matrix.getFirst().size()) {
            StringBuilder s = new StringBuilder();
            int x2 = x;
            int y2 = y;

            for(int j = 0; j < 3; j++){
                x2 += directions[i][1];
                y2 += directions[i][0];
                s.append(matrix.get(y2).get(x2));
            }

            if(s.toString().equals("MAS")){
                solution += 1;
            }
        }

        if(i < 7){
            solution += countUp(matrix, i + 1, directions, x, y);
        }

        return solution;
    }
}
