package Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

public class First {
    static final int EDGES = 71;
    static final int COUNT = 1024;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        char[][] matrix = new char[EDGES][EDGES];

        for (int i = 0; i < EDGES; i++) {
            for (int j = 0; j < EDGES; j++) {
                matrix[i][j] = '.';
            }
        }

        Reader in = new FileReader("src/Day18/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            int value1 = Integer.parseInt(tokens[0]);
            int value2 = Integer.parseInt(tokens[1]);

            if(count < COUNT) {
                matrix[value2][value1] = '#';
                count++;
            }
        }

        int startX = 0;
        int startY = 0;
        int endX = EDGES - 1;
        int endY = EDGES - 1;

        for (int i = 0; i < EDGES; i++) {
            System.out.println(matrix[i]);
        }

        int shortestPath = findShortestPath(matrix, startX, startY, endX, endY);

        if (shortestPath == -1) {
            System.out.println("No path found!");
        } else {
            System.out.println("Shortest path length: " + shortestPath);
        }

        in.close();

        in.close();
    }

    static int findShortestPath(char[][] matrix, int startX, int startY, int endX, int endY) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[EDGES][EDGES];
        int[][] distance = new int[EDGES][EDGES];

        queue.offer(new int[]{startX, startY});
        visited[startX][startY] = true;
        distance[startX][startY] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                return distance[x][y];
            }

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (isValidMove(matrix, newX, newY, visited)) {
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    distance[newX][newY] = distance[x][y] + 1;
                }
            }
        }

        return -1;
    }

    static boolean isValidMove(char[][] matrix, int x, int y, boolean[][] visited) {
        return x >= 0 && x < EDGES && y >= 0 && y < EDGES && matrix[x][y] != '#' && !visited[x][y];
    }
}
