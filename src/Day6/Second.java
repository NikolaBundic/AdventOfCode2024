package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Second {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        int y = -1, x;
        int[] start = new int[2];

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

        int[] pos = new int[2];
        pos[0] = start[0];
        pos[1] = start[1];

        solution(matrix, directions, pos, 0);
        int solution2 = 0;

        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < matrix.get(i).size(); j++){
                if(matrix.get(i).get(j) == 'X'){
                    matrix.get(i).set(j, '#');
                    Set<DirectedPosition> seen = new HashSet<>();

                    int[] pos2 = new int[2];
                    pos2[0] = start[0];
                    pos2[1] = start[1];

                    matrix.get(start[0]).set(start[1], '.');

                    solution2 += infiniteLoop(matrix, directions, pos2, 0, seen);
                    matrix.get(i).set(j, '.');
                }
            }
        }

        System.out.println(solution2);

        in.close();
    }

    private static int infiniteLoop(ArrayList<ArrayList<Character>> matrix, int[][] directions, int[] position, int dir, Set<DirectedPosition> seen){
        int solution = 0;

        while(position[0] + directions[dir][0] >= 0 && position[1] + directions[dir][1] >= 0 &&
                position[0] + directions[dir][0] < matrix.size() &&
                position[1] + directions[dir][1] < matrix.getFirst().size() &&
                matrix.get(position[0] + directions[dir][0]).get(position[1]) != '#' &&
                matrix.get(position[0]).get(position[1] + directions[dir][1]) != '#'){

            DirectedPosition posSeen = new DirectedPosition(position[0], position[1], dir);
            seen.add(posSeen);
            position[0] += directions[dir][0];
            position[1] += directions[dir][1];
        }

        if (position[0] + directions[dir][0] >= 0 && position[1] + directions[dir][1] >= 0 &&
                position[0] + directions[dir][0] < matrix.size() && position[1] + directions[dir][1] < matrix.getFirst().size()) {
            if (dir < 3) {
                dir++;
            } else {
                dir = 0;
            }

            DirectedPosition posSeen = new DirectedPosition(position[0], position[1], dir);
            if(seen.contains(posSeen)){
                return 1;
            }
            solution += infiniteLoop(matrix, directions, position, dir, seen);
        }
        return solution;
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

    record DirectedPosition(int y, int x, int dir) {}
}
