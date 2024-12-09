package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class First {
    public static void main (String[] args) throws IOException {
        Path filePath = Paths.get("src/Day9/inputFile.txt");
        String line = new String(Files.readAllBytes(filePath));

        ArrayList<Integer> num = new ArrayList<>();
        for(char c : line.toCharArray()){
            num.add(c - '0');
        }

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
                    blocks.add(".");
                    j++;
                }
                id++;
            }
        }

        int min = 0;
        int max = blocks.size() - 1;

        while(!Objects.equals(blocks.get(min), ".")){
            min++;
        }
        while(Objects.equals(blocks.get(max), ".")){
            max--;
        }

        while(min < max){
            Collections.swap(blocks, min, max);

            do {
                min++;
            } while (!Objects.equals(blocks.get(min), "."));

            do {
                max--;
            } while (Objects.equals(blocks.get(max), "."));
        }

        long solution = 0;

        long i = 0;
        while(!Objects.equals(blocks.get((int) i), ".")){
            solution += Long.parseLong(blocks.get((int) i)) * i;
            i++;
        }

        System.out.println(solution);
    }
}
