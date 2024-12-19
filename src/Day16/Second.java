package Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

class Second {
    private static final int[] dRow = {-1, 1, 0, 0}; // Up, Down, Left, Right
    private static final int[] dCol = {0, 0, -1, 1};
    private static final int STEP_COST = 1;
    private static final int TURN_COST = 1000;

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();

        Reader in = new FileReader("src/Day16/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            input.add(line);
        }

        char[][] maze = new char[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            maze[i] = input.get(i).toCharArray();
        }

        int startRow = maze.length - 2, startCol = 1;
        int endRow = 1, endCol = maze[0].length - 2;

        PathResult result = shortestPath(maze, startRow, startCol, endRow, endCol);
        System.out.println(result.visitedSpaces.size());
    }

    private static class PathResult {
        int shortestCost;
        Set<String> visitedSpaces;

        PathResult(int cost, Set<String> spaces) {
            this.shortestCost = cost;
            this.visitedSpaces = spaces;
        }
    }

    private static PathResult shortestPath(char[][] maze, int startRow, int startCol, int endRow, int endCol) {
        int rows = maze.length;
        int cols = maze[0].length;
        int[][][] costs = new int[rows][cols][4];
        Set<String> visitedInShortestPaths = new HashSet<>();
        int shortestPathCost = Integer.MAX_VALUE;

        for (int[][] cost : costs) {
            for (int[] dirCost : cost) {
                Arrays.fill(dirCost, Integer.MAX_VALUE);
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        for (int i = 0; i < 4; i++) {
            int initialCost = (i == 3) ? 0 : TURN_COST;
            pq.add(new Node(startRow, startCol, initialCost, i, new ArrayList<>(List.of(startRow + "," + startCol))));
            costs[startRow][startCol][i] = initialCost;
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.row == endRow && current.col == endCol) {
                if (current.cost <= shortestPathCost) {
                    if (current.cost < shortestPathCost) {
                        visitedInShortestPaths.clear();
                        shortestPathCost = current.cost;
                    }
                    visitedInShortestPaths.addAll(current.path);
                }
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.row + dRow[i];
                int newCol = current.col + dCol[i];
                if (isValidMove(maze, newRow, newCol)) {
                    int newCost = current.cost + STEP_COST + (current.direction == i ? 0 : TURN_COST);
                    if (newCost <= costs[newRow][newCol][i]) {
                        costs[newRow][newCol][i] = newCost;
                        ArrayList<String> newPath = new ArrayList<>(current.path);
                        newPath.add(newRow + "," + newCol);
                        pq.add(new Node(newRow, newCol, newCost, i, newPath));
                    }
                }
            }
        }

        return new PathResult(shortestPathCost, visitedInShortestPaths);
    }

    private static class Node {
        int row, col, cost, direction;
        ArrayList<String> path;

        Node(int row, int col, int cost, int direction, ArrayList<String> path) {
            this.row = row;
            this.col = col;
            this.cost = cost;
            this.direction = direction;
            this.path = path;
        }
    }

    private static boolean isValidMove(char[][] maze, int row, int col) {
        return row >= 0 && col >= 0 && row < maze.length && col < maze[0].length && maze[row][col] != '#';
    }
}