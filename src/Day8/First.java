package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();

        Reader in = new FileReader("src/Day8/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            matrix.add(row);
        }

        TreeMap<Character, ArrayList<ArrayList<Integer>>> map = new TreeMap<>();

        for(int i = 0; i < matrix.size(); i++) {
            for(int j = 0; j < matrix.get(i).size(); j++) {
                if(matrix.get(i).get(j) != '.'){
                    char key = matrix.get(i).get(j);
                    ArrayList<Integer> position = new ArrayList<>();
                    position.add(i);
                    position.add(j);

                    if (map.containsKey(key)) {
                        map.get(key).add(position);
                    } else {
                        ArrayList<ArrayList<Integer>> positionsList = new ArrayList<>();
                        positionsList.add(position);
                        map.put(key, positionsList);
                    }
                }
            }
        }

        for (Map.Entry<Character, ArrayList<ArrayList<Integer>>> entry : map.entrySet()) {
            ArrayList<ArrayList<Integer>> positions = entry.getValue();
            for (int i = 0; i < positions.size(); i++) {
                ArrayList<Integer> pos1 = positions.get(i);
                for (int j = i + 1; j < positions.size(); j++) {
                    ArrayList<Integer> pos2 = positions.get(j);
                    int posX = Math.abs(pos1.get(1) - pos2.get(1));
                    int posY = Math.abs(pos1.get(0) - pos2.get(0));

                    if(pos1.get(0) - posY >= 0){
                        if (pos1.get(1) > pos2.get(1)) {
                            if(pos1.get(1) + posX < matrix.getFirst().size()){
                                matrix.get(pos1.get(0) - posY).set(pos1.get(1) + posX, '#');
                            }
                        }else if(pos1.get(1) < pos2.get(1)){
                            if(pos1.get(1) - posX >= 0){
                                matrix.get(pos1.get(0) - posY).set(pos1.get(1) - posX, '#');
                            }
                        }else{
                            matrix.get(pos1.get(0) - posY).set(pos1.get(1), '#');
                        }
                    }

                    if(pos2.get(0) + posY < matrix.size()){
                        if (pos1.get(1) > pos2.get(1)) {
                            if(pos2.get(1) - posX >= 0){
                                matrix.get(pos2.get(0) + posY).set(pos2.get(1) - posX, '#');
                            }
                        }else if (pos1.get(1) < pos2.get(1)) {
                            if(pos2.get(1) + posX < matrix.getFirst().size()){
                                matrix.get(pos2.get(0) + posY).set(pos2.get(1) + posX, '#');
                            }
                        }else{
                            matrix.get(pos2.get(0) + posY).set(pos2.get(1), '#');
                        }
                    }
                }
            }
        }

        int solution = 0;
        for (ArrayList<Character> characters : matrix) {
            for (Character character : characters) {
                if (character == '#') {
                    solution++;
                }
            }
        }
        System.out.println(solution);
        in.close();
    }
}
