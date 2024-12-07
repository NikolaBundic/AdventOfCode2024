package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class First {
    public static void main(String[] args) throws IOException {
        ArrayList<Long> testValue = new ArrayList<>();
        ArrayList<ArrayList<Long>> values = new ArrayList<>();

        Reader in = new FileReader("src/Day7/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(":");
            testValue.add(Long.parseLong(tokens[0].trim()));

            String[] tokens2 = tokens[1].trim().split("\\s+");
            ArrayList<Long> tempValues = new ArrayList<>();

            for (String token : tokens2) {
                tempValues.add(Long.parseLong(token.trim()));
            }
            values.add(tempValues);
        }

        long result = 0;
        for (int i = 0; i < testValue.size(); i++) {
            long check = checker(testValue.get(i), values.get(i));
            if (check != 0) {
                result += testValue.get(i);
            }
        }

        System.out.println(result);
        br.close();
    }

    private static long checker(long testValue, ArrayList<Long> values) {
        long result = 0;

        if (values.size() == 2) {
            if (values.get(0) + values.get(1) == testValue || values.get(0) * values.get(1) == testValue) {
                result = 1;
            }
        } else {
            ArrayList<Long> valuesPlus = new ArrayList<>(values);
            ArrayList<Long> valuesMult = new ArrayList<>(values);

            valuesPlus.set(0, valuesPlus.get(0) + valuesPlus.get(1));
            valuesMult.set(0, valuesMult.get(0) * valuesMult.get(1));
            valuesPlus.remove(1);
            valuesMult.remove(1);

            result += checker(testValue, valuesPlus);
            result += checker(testValue, valuesMult);
        }

        return result;
    }
}
