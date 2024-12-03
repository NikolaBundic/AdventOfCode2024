package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class First {
    public static void main(String[] args) throws IOException {
        String regex = "mul\\((\\d+),(\\d+)\\)";
        int solution = 0;

        Reader in = new FileReader("src/Day3/inputList.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        Pattern pattern = Pattern.compile(regex);
        while ((line = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                System.out.println("Found: " + matcher.group());
                int firstNumber = Integer.parseInt(matcher.group(1));
                int secondNumber = Integer.parseInt(matcher.group(2));

                solution += firstNumber * secondNumber;
            }
        }

        System.out.println("Solution: " + solution);
    }
}
