package Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        ArrayList<Character> move = new ArrayList<>();
        boolean area = true;

        Reader in = new FileReader("src/Day15/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                area = false;
                continue;
            }

            if (area) {
                ArrayList<Character> row = new ArrayList<>();
                for(char c : line.toCharArray()) {
                    row.add(c);
                }
                matrix.add(row);
            } else {
                for(char c : line.toCharArray()) {
                    move.add(c);
                }
            }
        }

        for(char c : move) {
            int[] dir = parseDir(c);
            int[] pos = getStart(matrix);

            while (matrix.get(pos[1]).get(pos[0]) != '#') {
                if(matrix.get(pos[1]).get(pos[0]) != '.'){
                    pos[0] += dir[0];
                    pos[1] += dir[1];
                }else{
                    while(matrix.get(pos[1] - dir[1]).get(pos[0] - dir[0]) != '@'){
                        swapElements(matrix, pos[1], pos[0], pos[1] - dir[1], pos[0] - dir[0]);

                        pos[0] -= dir[0];
                        pos[1] -= dir[1];
                    }
                    swapElements(matrix, pos[1], pos[0], pos[1] - dir[1], pos[0] - dir[0]);
                    break;
                }
            }
        }

        long solution = 0;
        for(long i = 0; i < matrix.size(); i++) {
            for(long j = 0; j < matrix.get((int) i).size(); j++) {
                if(matrix.get((int) i).get((int) j) == 'O') {
                    solution += 100 * i + j;
                }
            }
        }

        System.out.println(solution);

        in.close();
    }

    private static int[] getStart(ArrayList<ArrayList<Character>> matrix) {
        int[] start = new int[2];
        for(int i = 0; i < matrix.size(); i++) {
            for(int j = 0; j < matrix.get(i).size(); j++) {
                if(matrix.get(i).get(j) == '@') {
                    start[0] = j;
                    start[1] = i;
                }
            }
        }
        return start;
    }

    private static int[] parseDir(char c){
        int[] value = new int[2];
        switch(c){
            case '>':
                value[0] = 1;
                break;
            case '<':
                value[0] = -1;
                break;
            case '^':
                value[1] = -1;
                break;
            case 'v':
                value[1] = 1;
                break;
        }
        return value;
    }

    private static void swapElements(ArrayList<ArrayList<Character>> matrix, int row1, int col1, int row2, int col2) {
        Character temp = matrix.get(row1).get(col1);
        matrix.get(row1).set(col1, matrix.get(row2).get(col2));
        matrix.get(row2).set(col2, temp);
    }
}
