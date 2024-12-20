package Day20;

import java.io.*;
import java.util.*;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();

        Reader in = new FileReader("src/Day20/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            matrix.add(row);
        }

        int startX = 0, startY = 0, endX = 0, endY = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 'S') {
                    startX = j;
                    startY = i;
                } else if (matrix.get(i).get(j) == 'E') {
                    endX = j;
                    endY = i;
                }
            }
        }

        int cheatsOver99 = 0;

        for (int y = 0; y < matrix.size(); y++) {
            for (int x = 0; x < matrix.getFirst().size(); x++) {
                if (matrix.get(y).get(x) == '#') {
                    int savedTime = calculateTimeSaved(matrix, startX, startY, endX, endY, x, y);
                    if (savedTime > 99) {
                        cheatsOver99++;
                    }
                }
            }
        }

        System.out.println(cheatsOver99);

        in.close();
    }

    private static int calculateTimeSaved(ArrayList<ArrayList<Character>> matrix,
                                          int startX, int startY, int endX, int endY,
                                          int cheatX, int cheatY) {

        int normalLength = findShortestPath(matrix, startX, startY, endX, endY, -1, -1);
        int cheatLength = findShortestPath(matrix, startX, startY, endX, endY, cheatX, cheatY);

        return (normalLength != -1 && cheatLength != -1) ? normalLength - cheatLength : 0;
    }

    private static int findShortestPath(ArrayList<ArrayList<Character>> matrix,
                                        int startX, int startY, int endX, int endY,
                                        int cheatX, int cheatY) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        Queue<Position> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new Position(startX, startY, 0));

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.x == endX && current.y == endY) {
                return current.steps;
            }

            String pos = current.x + "," + current.y;
            if (visited.contains(pos)) continue;
            visited.add(pos);

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (newX >= 0 && newX < matrix.getFirst().size() &&
                        newY >= 0 && newY < matrix.size()) {

                    char cell = matrix.get(newY).get(newX);
                    if (cell != '#' || (newX == cheatX && newY == cheatY)) {
                        queue.offer(new Position(newX, newY, current.steps + 1));
                    }
                }
            }
        }
        return -1;
    }

    static class Position {
        int x, y;
        int steps;

        Position(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }
    }
}