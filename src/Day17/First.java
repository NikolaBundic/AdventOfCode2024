package Day17;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class First {
    static int A = 0;
    static int B = 0;
    static int C = 0;

    public static void main(String[] args) throws IOException {
        List<Integer> nums = new ArrayList<>();

        Reader in = new FileReader("src/Day17/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;

        Pattern pattern = Pattern.compile("\\d+");
        while ((line = br.readLine()) != null) {
            if(!line.isEmpty()) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    nums.add(Integer.parseInt(matcher.group()));
                }
            }
        }

        int[] program = new int[nums.size() - 3];
        for (int i = 0; i < nums.size(); i++) {
            if(i == 0){
                A = nums.get(i);
            }else if(i == 1){
                B = nums.get(i);
            }else if(i == 2){
                C = nums.get(i);
            }else{
                program[i - 3] = nums.get(i);
            }
        }

        int i = 0;
        boolean change;
        StringBuilder output = new StringBuilder();

        while(i < program.length){
            change = true;
            int instruction = program[i];
            int operand = program[i + 1];

            switch (instruction){
                case 0:
                    A = (int) (A / Math.pow(2, getComboOperandValue(operand)));
                    break;
                case 1:
                    B = B ^ operand;
                    break;
                case 2:
                    B = getComboOperandValue(operand) % 8;
                    break;
                case 3:
                    if(A != 0){
                        i = operand;
                        change = false;
                    }
                    break;
                case 4:
                    B ^= C;
                    break;
                case 5:
                    if (!output.isEmpty()) {
                        output.append(",");
                    }
                    output.append(getComboOperandValue(operand) % 8);
                    break;
                case 6:
                    B = (int) (A / Math.pow(2, getComboOperandValue(operand)));
                    break;
                case 7:
                    C = (int) (A / Math.pow(2, getComboOperandValue(operand)));
                    break;
            }
            if(change){
                i += 2;
            }
        }

        System.out.println(output);

        in.close();
    }

    private static int getComboOperandValue(int comboOperand) {
        return switch (comboOperand) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> A;
            case 5 -> B;
            case 6 -> C;
            case 7 -> throw new IllegalArgumentException("Combo operand 7 is invalid.");
            default -> throw new IllegalArgumentException("Invalid combo operand: " + comboOperand);
        };
    }
}
