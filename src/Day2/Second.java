package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Second {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> list1 = new ArrayList<>();
        int solution = 0;

        Reader in = new FileReader("src/Day2/inputList.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            list1.clear();

            String[] tokens = line.split(" ");
            for (String token : tokens) {
                list1.add(Integer.parseInt(token));
            }

            boolean growing = false;
            int growth = 0;
            for (int i = 1; i < list1.size(); i++) {
                if (list1.get(i - 1) < list1.get(i)) {
                    growth++;
                }
            }
            if (growth > 1) {
                growing = true;
            }

            boolean safe = true;
            boolean tolerate = true;

            for (int i = 0; i < list1.size() - 1; i++) {
                int diff = list1.get(i) - list1.get(i + 1);
                if (growing) {
                    if (list1.get(i) >= list1.get(i + 1) || Math.abs(diff) > 3 || Math.abs(diff) == 0) {
                        if (tolerate) {
                            tolerate = false;
                            if(i != 0) {
                                if(list1.get(i-1) < list1.get(i+1) && list1.get(i+1) - list1.get(i-1) < 4){
                                    list1.remove(i);
                                }else{
                                    list1.remove(i+1);
                                }
                                i--;
                            }else if(list1.get(i) < list1.get(i+2) && list1.get(i+2) - list1.get(i) < 4){
                                list1.remove(i+1);
                            }
                        } else {
                            safe = false;
                        }
                    }
                } else {
                    if (list1.get(i) <= list1.get(i + 1) || Math.abs(diff) > 3 || Math.abs(diff) == 0) {
                        if (tolerate) {
                            tolerate = false;
                            if(i != 0) {
                                if(list1.get(i-1) > list1.get(i+1) && list1.get(i-1) - list1.get(i+1) < 4){
                                    list1.remove(i);
                                }else{
                                    list1.remove(i+1);
                                }
                                i--;
                            }else if(list1.get(i) > list1.get(i+2) && list1.get(i) - list1.get(i+2) < 4){
                                list1.remove(i+1);
                            }
                        } else {
                            safe = false;
                        }
                    }
                }
            }
            if (safe) {
                solution++;
            }
        }
        System.out.println(solution);
    }
}