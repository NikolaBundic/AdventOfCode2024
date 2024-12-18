package Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

public class Second {
    static final int EDGES = 71;
    static final int MAX_COUNT = 3450;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        int result = findFirstUnsolvableConfiguration();

        Reader in = new FileReader("src/Day18/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        int counter = 1;
        while ((line = br.readLine()) != null) {
            if(counter == result){
                System.out.println(line);
                break;
            }
            counter++;
        }

        in.close();
    }

    static int findFirstUnsolvableConfiguration() throws IOException {
        int left = 0;
        int right = MAX_COUNT;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (isMazeSolvable(mid)) {
                left = mid + 1;
            } else {
                result = mid;
                right = mid - 1;
            }
        }

        return result;
    }

    static boolean isMazeSolvable(int count) throws IOException {
        char[][] matrix = new char[EDGES][EDGES];

        for (int i = 0; i < EDGES; i++) {
            for (int j = 0; j < EDGES; j++) {
                matrix[i][j] = '.';
            }
        }

        BufferedReader br = new BufferedReader(new FileReader("src/Day18/inputFile.txt"));
        String line;
        int currentCount = 0;
        while ((line = br.readLine()) != null && currentCount < count) {
            String[] tokens = line.split(",");
            int value1 = Integer.parseInt(tokens[0]);
            int value2 = Integer.parseInt(tokens[1]);

            matrix[value2][value1] = '#';
            currentCount++;
        }

        br.close();

        return findShortestPath(matrix, 0, 0, EDGES - 1, EDGES - 1) != -1;
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