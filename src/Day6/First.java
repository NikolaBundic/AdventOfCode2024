package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        int y = -1, x;
        int[] start = new int[2];
        int solution;

        Reader in = new FileReader("src/Day6/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            y++;
            x = 0;
            ArrayList<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                if(c == '^'){
                    start[0] = y;
                    start[1] = x;
                }
                row.add(c);
                x++;
            }
            matrix.add(row);
        }

                            //   N       E       S       W
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        solution = solution(matrix, directions, start, 0);

        System.out.println(solution);

        in.close();
    }

    private static int solution(ArrayList<ArrayList<Character>> matrix, int[][] directions, int[] position, int dir){
        int solution = 0;

        while(position[0] + directions[dir][0] >= 0 && position[1] + directions[dir][1] >= 0 &&
                position[0] + directions[dir][0] < matrix.size() &&
                position[1] + directions[dir][1] < matrix.getFirst().size() &&
                matrix.get(position[0] + directions[dir][0]).get(position[1]) != '#' &&
                matrix.get(position[0]).get(position[1] + directions[dir][1]) != '#'){

            if(matrix.get(position[0]).get(position[1]) != 'X'){
                matrix.get(position[0]).set(position[1], 'X');
                solution++;
            }
            position[0] += directions[dir][0];
            position[1] += directions[dir][1];
        }

        if(matrix.get(position[0]).get(position[1]) != 'X'){
            matrix.get(position[0]).set(position[1], 'X');
            solution++;
        }

        if (position[0] + directions[dir][0] >= 0 && position[1] + directions[dir][1] >= 0 &&
                position[0] + directions[dir][0] < matrix.size() && position[1] + directions[dir][1] < matrix.getFirst().size()) {
            if (dir < 3) {
                dir++;
            } else {
                dir = 0;
            }

            solution += solution(matrix, directions, position, dir);
        }
        return solution;
    }
}
