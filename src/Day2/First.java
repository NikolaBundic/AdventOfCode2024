package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> list1 = new ArrayList<>();
        int solution = 0;
        boolean growing = false;
        boolean safe;
        int value = 0;

        Reader in = new FileReader("src/Day2/inputList.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            list1.clear();
            String[] tokens = line.split(" ");
            for (String token : tokens) {
                list1.add(Integer.parseInt(token));
            }
            safe = true;

            for (int i = 0; i < list1.size(); i++) {
                if(i==0){
                    value = list1.get(i);
                    growing = list1.get(i + 1) > value;
                }else if(i < list1.size()-1){
                    if(growing && list1.get(i) > value && list1.get(i) - value < 4){
                        value = list1.get(i);
                    }else if(!growing && list1.get(i) < value && value - list1.get(i) < 4){
                        value = list1.get(i);
                    }else{
                        safe = false;
                    }
                }else{
                    if(growing && list1.get(i) > value && list1.get(i) - value < 4){
                        if(safe){
                            solution++;
                        }
                    }else if(!growing && list1.get(i) < value && value - list1.get(i) < 4){
                        if(safe){
                            solution++;
                        }
                    }
                }
            }
        }

        System.out.println(solution);
    }
}
