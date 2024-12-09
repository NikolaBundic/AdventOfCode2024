package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Second {
    public static void main (String[] args) throws IOException {
        Path filePath = Paths.get("src/Day9/inputFile.txt");
        String line = new String(Files.readAllBytes(filePath));

        ArrayList<Integer> num = new ArrayList<>();
        for(char c : line.toCharArray()){
            num.add(c - '0');
        }

        TreeMap<Integer, Integer> holes = new TreeMap<>();
        ArrayList<String> blocks = new ArrayList<>();
        int id = 0;
        for(int i = 0; i < num.size(); i++){
            int j = 0;
            if(i % 2 == 0){
                while(j < num.get(i)){
                    blocks.add(String.valueOf(id));
                    j++;
                }
            }else{
                while(j < num.get(i)){
                    if(j == 0){
                        holes.put(blocks.size(), num.get(i));
                    }
                    blocks.add(".");
                    j++;
                }
                id++;
            }
        }

        int max = blocks.size() - 1;

        while(Objects.equals(blocks.get(max), ".")){
            max--;
        }

        while(holes.firstKey() < max){
            int space = 1;
            while(Objects.equals(blocks.get(max), blocks.get(max - 1))){
                max--;
                space++;
            }

            int hole = 0;
            for (Map.Entry<Integer, Integer> entry : holes.entrySet()) {
                if (entry.getValue() >= space && entry.getKey() < max) {
                    hole = entry.getKey();
                    break;
                }
            }

            if(hole != 0) {
                if (holes.get(hole) == space) {
                    holes.remove(hole);
                    for (int i = 0; i < space; i++) {
                        Collections.swap(blocks, hole + i, max + i);
                    }
                }else{
                    holes.put(hole + space, holes.get(hole) - space);
                    holes.remove(hole);

                    for (int i = 0; i < space; i++) {
                        Collections.swap(blocks, hole + i, max + i);
                    }
                }
            }

            max--;
            while(Objects.equals(blocks.get(max), ".")){
                max--;
            }
        }

        long solution = 0;
        for(long i = 0; i < (long) blocks.size(); i++){
            if(!Objects.equals(blocks.get((int) i), ".")) {
                solution += Long.parseLong(blocks.get((int) i)) * i;
            }
        }

        System.out.println(solution);
    }
}
