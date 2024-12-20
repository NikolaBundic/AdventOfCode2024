package Day20;

import java.io.*;
import java.util.*;

//Takes a really long time but works
public class Second {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();

        Reader in = new FileReader("src/Day20/inputFileSmall.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            matrix.add(row);
        }

        Point start = null, end = null;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 'S') start = new Point(j, i);
                if (matrix.get(i).get(j) == 'E') end = new Point(j, i);
            }
        }

        int normalLength = findNormalPath(matrix, start, end);
        Map<Integer, Integer> savings = new TreeMap<>();

        for (int y1 = 0; y1 < matrix.size(); y1++) {
            for (int x1 = 0; x1 < matrix.getFirst().size(); x1++) {

                int pathToCheat = findNormalPath(matrix, start, new Point(x1, y1));
                if (pathToCheat == -1) continue;

                for (int y2 = 0; y2 < matrix.size(); y2++) {
                    for (int x2 = 0; x2 < matrix.getFirst().size(); x2++) {
                        if (matrix.get(y2).get(x2) != '#') {
                            int manhattanDist = Math.abs(x2 - x1) + Math.abs(y2 - y1);
                            if (manhattanDist <= 20) {
                                int pathFromCheat = findNormalPath(matrix, new Point(x2, y2), end);
                                if (pathFromCheat != -1) {
                                    int totalPath = pathToCheat + manhattanDist + pathFromCheat;
                                    if (totalPath < normalLength) {
                                        int saved = normalLength - totalPath;
                                        savings.put(saved, savings.getOrDefault(saved, 0) + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int count = savings.entrySet().stream()
                .filter(e -> e.getKey() >= 100)
                .mapToInt(Map.Entry::getValue)
                .sum();
        System.out.println(count);

        in.close();
    }

    private static int findNormalPath(ArrayList<ArrayList<Character>> matrix, Point start, Point end) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        Queue<Point> queue = new LinkedList<>();
        Map<Point, Integer> distances = new HashMap<>();

        queue.offer(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(end)) {
                return distances.get(current);
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];
                Point next = new Point(newX, newY);

                if (newX >= 0 && newX < matrix.getFirst().size() &&
                        newY >= 0 && newY < matrix.size() &&
                        matrix.get(newY).get(newX) != '#' &&
                        !distances.containsKey(next)) {

                    queue.offer(next);
                    distances.put(next, distances.get(current) + 1);
                }
            }
        }
        return -1;
    }

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}