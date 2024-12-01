package Day1;

import java.io.*;
import java.util.ArrayList;

public class First {
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

        for (int i = 0; i < list1.size(); i++) {
            if(list1.get(i) < list2.get(i)){
                solution += list2.get(i) - list1.get(i);
            }else {
                solution += list1.get(i) - list2.get(i);
            }
        }

        System.out.println(solution);

        in.close();
    }
}
