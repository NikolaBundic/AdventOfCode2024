package Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

//Works on examples but not on inputFile
//Will try to find edge case later
//There is like 10 boxes that get messed up and I don't know what to do anymore
public class Second {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        ArrayList<Character> move = new ArrayList<>();
        boolean area = true;

        Reader in = new FileReader("src/Day15/inputFileSmall.txt");
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
                    if(c == '@'){
                        row.add(c);
                        row.add('.');
                    }else if(c == 'O'){
                        row.add('[');
                        row.add(']');
                    }else{
                        row.add(c);
                        row.add(c);
                    }
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
            ArrayList<Integer> check = new ArrayList<>();
            ArrayList<Integer> checked = new ArrayList<>();
            ArrayList<ArrayList<Integer>> forMove = new ArrayList<>();

            while (matrix.get(pos[1]).get(pos[0]) != '#') {
                if(c == '<' || c == '>') {
                    if (matrix.get(pos[1]).get(pos[0]) != '.') {
                        pos[0] += dir[0];
                    } else {
                        while (matrix.get(pos[1] - dir[1]).get(pos[0] - dir[0]) != '@') {
                            swapElements(matrix, pos[1], pos[0], pos[1] - dir[1], pos[0] - dir[0]);

                            pos[0] -= dir[0];
                        }
                        swapElements(matrix, pos[1], pos[0], pos[1] - dir[1], pos[0] - dir[0]);
                        break;
                    }
                }else if(c == '^' || c == 'v') {
                    boolean canMove = false;
                    boolean shouldBreak = false;
                    if(!check.contains(pos[0])) {
                        check.add(pos[0]);
                    }

                    ArrayList<Integer> temp = new ArrayList<>();
                    for(Integer i : check) {
                        if(!checked.contains(i)) {
                            if (matrix.get(pos[1]).get(i) == '.') {
                                checked.add(i);
                            } else if (matrix.get(pos[1]).get(i) == '[') {
                                temp.add(i + 1);

                                ArrayList<Integer> row = new ArrayList<>();
                                row.add(i + 1);
                                row.add(pos[1]);
                                ArrayList<Integer> row2 = new ArrayList<>();
                                row2.add(i);
                                row2.add(pos[1]);
                                forMove.add(row);
                                forMove.add(row2);
                            } else if (matrix.get(pos[1]).get(i) == ']') {
                                temp.add(i - 1);

                                ArrayList<Integer> row = new ArrayList<>();
                                row.add(i - 1);
                                row.add(pos[1]);
                                ArrayList<Integer> row2 = new ArrayList<>();
                                row2.add(i);
                                row2.add(pos[1]);
                                forMove.add(row);
                                forMove.add(row2);
                            }else if (matrix.get(pos[1]).get(i) == '#') {
                                shouldBreak = true;
                            }
                        }
                    }

                    if(shouldBreak) {
                        break;
                    }

                    for(Integer i : temp) {
                        if(!check.contains(i)) {
                            check.add(i);
                        }
                    }

                    if(checked.size() == check.size()) {
                        canMove = true;
                        pos[1] += dir[1];
                    }

                    if(!canMove){
                        pos[1] += dir[1];
                    }else{
                        while (matrix.get(pos[1] - dir[1]).get(pos[0]) != '@') {
                            for(Integer i : check) {
                                ArrayList<Integer> row = new ArrayList<>();
                                row.add(i);
                                row.add(pos[1] - dir[1]);

                                if(forMove.contains(row)) {
                                    swapElements(matrix, pos[1], i, pos[1] - dir[1], i);
                                }
                            }
                            pos[1] -= dir[1];
                        }

                        swapElements(matrix, pos[1], pos[0], pos[1] - dir[1], pos[0]);
                        break;
                    }
                }
            }
        }

        long solution = 0;
        for(long i = 0; i < matrix.size(); i++) {
            for(long j = 0; j < matrix.get((int) i).size(); j++) {
                if(matrix.get((int) i).get((int) j) == '[') {
                    solution += 100 * i + j;
                }
            }
        }

        for(ArrayList<Character> row : matrix) {
            System.out.println(row);
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
