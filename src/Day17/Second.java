package Day17;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Second {
    static long A = 0;
    static long B = 0;
    static long C = 0;

    public static void main(String[] args) throws IOException {
        List<Long> nums = new ArrayList<>();

        Reader in = new FileReader("src/Day17/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;

        Pattern pattern = Pattern.compile("\\d+");
        while ((line = br.readLine()) != null) {
            if(!line.isEmpty()) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    nums.add(Long.parseLong(matcher.group()));
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
                program[i - 3] = Math.toIntExact(nums.get(i));
            }
        }

        long initialA = findInitialA(program);
        System.out.println(initialA);

        in.close();
    }

    private static long findInitialA(int[] program) {
        long a = 0;
        long b = 0;
        long c = 0;

        for (int pos = program.length - 1; pos >= 0; pos--) {
            a <<= 3;

            while (true) {
                List<Integer> output = runProgram(a, b, c, program);

                if (isSubsequenceMatch(output, Arrays.copyOfRange(program, pos, program.length))) {
                    break;
                }

                a++;
            }
        }

        return a;
    }

    private static boolean isSubsequenceMatch(List<Integer> output, int[] expectedSubsequence) {
        if (output.size() < expectedSubsequence.length) {
            return false;
        }

        for (int i = 0; i < expectedSubsequence.length; i++) {
            if (!output.get(output.size() - expectedSubsequence.length + i).equals(expectedSubsequence[i])) {
                return false;
            }
        }

        return true;
    }

    private static List<Integer> runProgram(long a, long b, long c, int[] program) {
        List<Integer> out = new ArrayList<>();
        long currentA = a;
        long currentB = b;
        long currentC = c;

        for (int i = 0; i < program.length; i += 2) {
            int instruction = program[i];
            int operand = program[i + 1];

            switch (instruction) {
                case 0:
                    currentA = (long) (currentA / Math.pow(2, getComboOperandValue(operand, currentA, currentB, currentC)));
                    break;
                case 1:
                    currentB = currentB ^ operand;
                    break;
                case 2:
                    currentB = getComboOperandValue(operand, currentA, currentB, currentC) % 8;
                    break;
                case 3:
                    if (currentA != 0) {
                        i = operand - 2;
                    }
                    break;
                case 4:
                    currentB ^= currentC;
                    break;
                case 5:
                    out.add((int) (getComboOperandValue(operand, currentA, currentB, currentC) % 8));
                    break;
                case 6:
                    currentB = (long) (currentA / Math.pow(2, getComboOperandValue(operand, currentA, currentB, currentC)));
                    break;
                case 7:
                    currentC = (long) (currentA / Math.pow(2, getComboOperandValue(operand, currentA, currentB, currentC)));
                    break;
            }
        }

        return out;
    }

    private static long getComboOperandValue(int comboOperand, long a, long b, long c) {
        return switch (comboOperand) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            case 7 -> throw new IllegalArgumentException("Combo operand 7 is invalid.");
            default -> throw new IllegalArgumentException("Invalid combo operand: " + comboOperand);
        };
    }
}