package Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class Second {
    public static void main(String[] args) throws IOException {

        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        int solution = 0;

        Reader in = new FileReader("src/Day1/inputList.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            int value1 = Integer.parseInt(tokens[0]);
            int value2 = Integer.parseInt(tokens[1]);

            list1.add(value1);
            list2.add(value2);
        }

        list1.sort(null);
        list2.sort(null);

        //Making a map of all the values of list2 with amount of occurances
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 1;

        for (int i = 0; i < list2.size(); i++) {
            int temp = list2.get(i);

            if(i < list2.size() - 1 && list2.get(i + 1) == temp) {
                count++;
            }else if (i < list2.size() - 1 && list2.get(i + 1) != temp){
                map.put(temp, count);
                count = 1;
            }else {
                map.put(temp, count);
            }
        }

        for(int i = 0; i < list1.size(); i++){
            if(map.containsKey(list1.get(i))) {
                solution += list1.get(i) * map.get(list1.get(i));
            }
        }

        System.out.println(solution);

        in.close();
    }
}
