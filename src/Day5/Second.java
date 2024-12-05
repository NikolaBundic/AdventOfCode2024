package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;

public class Second {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        Boolean[][] rules = new Boolean[100][100];
        int solution = 0;
        boolean order = true;

        Reader in = new FileReader("src/Day5/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            if(line.isEmpty()){
                order = false;
                continue;
            }

            if(order){
                String[] tokens = line.split("\\|");
                int value1 = Integer.parseInt(tokens[0]);
                int value2 = Integer.parseInt(tokens[1]);

                rules[value1][value2] = true;

            }else{
                ArrayList<Integer> row = new ArrayList<>();
                String[] tokens = line.split(",");
                for(String token : tokens){
                    row.add(Integer.parseInt(token));
                }
                matrix.add(row);
            }
        }

        for(ArrayList<Integer> row : matrix){
            boolean swapped = true;
            int swapCount = 0;

            while(swapped) {
                swapped = false;
                for (int i = 0; i < row.size() - 1; i++) {
                    for (int j = i + 1; j < row.size(); j++) {
                        if (rules[row.get(j)][row.get(i)] != null) {
                            Collections.swap(row, i, j);
                            swapped = true;
                            swapCount++;
                        }
                    }
                }
            }

            if (swapCount > 0) {
                solution += row.get(row.size()/2);
            }
        }

        System.out.println(solution);

        in.close();
    }
}
