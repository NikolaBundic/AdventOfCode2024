package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class First {
    public static void main(String[] args) throws IOException {

        ArrayList<Long> list = new ArrayList<>();

        Path filePath = Paths.get("src/Day11/inputFile.txt");
        String line = new String(Files.readAllBytes(filePath));

        String[] tokens = line.split("\\s+");
        for(String token : tokens) {
            list.add(Long.parseLong(token));
        }

        for(int i = 0; i < 25; i++) {
            for(int j = list.size() - 1; j >= 0; j--){
                long value = list.get(j);
                if(value == 0){
                    list.set(j, (long) 1);
                }else if(hasEvenNumberOfDigits(value)){
                    String numberStr = Long.toString(value);
                    int mid = numberStr.length() / 2;

                    String firstPart = numberStr.substring(0, mid);
                    String secondPart = numberStr.substring(mid);

                    list.set(j, Long.parseLong(firstPart));
                    list.add(j + 1, Long.parseLong(secondPart));
                }else{
                    list.set(j, value*2024);
                }
            }
        }

        System.out.println(list.size());
    }

    public static boolean hasEvenNumberOfDigits(long number) {
        String numberStr = Long.toString(number);
        return numberStr.length() % 2 == 0;
    }
}
