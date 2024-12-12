package Day12;

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

        Reader in = new FileReader("src/Day12/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            matrix.add(row);
        }

        Set<int[]> visited = new HashSet<>();
        int solution = 0;

        for(int i = 0; i < matrix.size(); i++) {
            for(int j = 0; j < matrix.get(i).size(); j++) {
                if(!containsCoordinate(visited, i, j)) {
                    char ch = matrix.get(i).get(j);
                    int[] sol = areaPlusSides(matrix, visited, i, j, ch);
                    solution += sol[0] * sol[1];
                }
            }
        }

        System.out.println(solution);
    }

    private static int[] areaPlusSides(ArrayList<ArrayList<Character>> matrix, Set<int[]> visited, int row, int col, char ch) {

        if(row < 0 || col < 0 || row >= matrix.size() || col >= matrix.get(row).size()) {
            return new int[]{0, 0};
        }

        if(matrix.get(row).get(col) != ch) {
            return new int[]{0, 0};
        }

        if (containsCoordinate(visited, row, col)) {
            return new int[]{0, 0};
        }

        visited.add(new int[]{row, col});
        int[] solution = new int[]{1,0};
        ArrayList<ArrayList<Character>> paddedMatrix = addPadding(matrix);

        int row2 = row + 1;
        int col2 = col + 1;

        if(row2 + 1 < paddedMatrix.size() && col2 + 1 < paddedMatrix.get(row2).size()) {
            if (paddedMatrix.get(row2).get(col2 - 1) != ch && paddedMatrix.get(row2 - 1).get(col2) != ch ||
                    paddedMatrix.get(row2 - 1).get(col2 - 1) != ch && paddedMatrix.get(row2).get(col2 - 1) == ch && paddedMatrix.get(row2 - 1).get(col2) == ch) {
                solution[1]++;
            }
            if (paddedMatrix.get(row2).get(col2 - 1) != ch && paddedMatrix.get(row2 + 1).get(col2) != ch ||
                    paddedMatrix.get(row2 + 1).get(col2 - 1) != ch && paddedMatrix.get(row2).get(col2 - 1) == ch && paddedMatrix.get(row2 + 1).get(col2) == ch) {
                solution[1]++;
            }
            if (paddedMatrix.get(row2).get(col2 + 1) != ch && paddedMatrix.get(row2 - 1).get(col2) != ch ||
                    paddedMatrix.get(row2 - 1).get(col2 + 1) != ch && paddedMatrix.get(row2).get(col2 + 1) == ch && paddedMatrix.get(row2 - 1).get(col2) == ch) {
                solution[1]++;
            }
            if (paddedMatrix.get(row2).get(col2 + 1) != ch && paddedMatrix.get(row2 + 1).get(col2) != ch ||
                    paddedMatrix.get(row2 + 1).get(col2 + 1) != ch && paddedMatrix.get(row2).get(col2 + 1) == ch && paddedMatrix.get(row2 + 1).get(col2) == ch) {
                solution[1]++;
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            int[] temp = areaPlusSides(matrix, visited, newRow, newCol, ch);
            solution[0] += temp[0];
            solution[1] += temp[1];
        }

        return solution;
    }

    private static boolean containsCoordinate(Set<int[]> visited, int row, int col) {
        for (int[] coordinate : visited) {
            if (coordinate[0] == row && coordinate[1] == col) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<ArrayList<Character>> addPadding(ArrayList<ArrayList<Character>> matrix) {
        if (matrix == null || matrix.isEmpty()) {
            return matrix;
        }

        int cols = matrix.getFirst().size();

        ArrayList<ArrayList<Character>> paddedMatrix = new ArrayList<>();

        ArrayList<Character> topPaddingRow = new ArrayList<>();
        for (int i = 0; i < cols + 2; i++) {
            topPaddingRow.add('.');
        }
        paddedMatrix.add(topPaddingRow);

        for (ArrayList<Character> row : matrix) {
            ArrayList<Character> paddedRow = new ArrayList<>();
            paddedRow.add('.');
            paddedRow.addAll(row);
            paddedRow.add('.');
            paddedMatrix.add(paddedRow);
        }

        ArrayList<Character> bottomPaddingRow = new ArrayList<>();
        for (int i = 0; i < cols + 2; i++) {
            bottomPaddingRow.add('.');
        }
        paddedMatrix.add(bottomPaddingRow);

        return paddedMatrix;
    }
}
