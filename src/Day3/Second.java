package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Second {
    public static void main(String[] args) throws IOException {
        String regex = "mul\\((\\d+),(\\d+)\\)";
        String regexDo = "do\\(\\)";
        String regexDont = "don't\\(\\)";

        int solution = 0;
        boolean enabled = true;

        Pattern pattern = Pattern.compile(regex);
        Pattern patternDo = Pattern.compile(regexDo);
        Pattern patternDont = Pattern.compile(regexDont);

        TreeMap<Integer, String> matches = new TreeMap<>();
        Path filePath = Paths.get("src/Day3/inputList.txt");
        String line = new String(Files.readAllBytes(filePath));

        Matcher matcher = pattern.matcher(line);
        Matcher matcherDo = patternDo.matcher(line);
        Matcher matcherDont = patternDont.matcher(line);

        while (matcher.find()) {
            matches.put(matcher.start(), matcher.group());
        }

        while (matcherDo.find()) {
            matches.put(matcherDo.start(), "Do()");
        }

        while (matcherDont.find()) {
            matches.put(matcherDont.start(), "Dont()");
        }

        for(Map.Entry<Integer, String> entry : matches.entrySet()){
            if(entry.getValue().equals("Do()")){
                enabled = true;
            }else if (entry.getValue().equals("Dont()")){
                enabled = false;
            }else{
                Matcher matcher2 = pattern.matcher(entry.getValue());
                if(matcher2.matches()){
                    int firstNumber = Integer.parseInt(matcher2.group(1));
                    int secondNumber = Integer.parseInt(matcher2.group(2));

                    if(enabled){
                        solution += firstNumber * secondNumber;
                    }
                }
            }
        }

        System.out.println(solution);
    }
}
