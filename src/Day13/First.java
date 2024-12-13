package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class First {
    public static void main(String[] args) throws IOException {
        List<Integer> nums = new ArrayList<>();
        List<Machine> machines = new ArrayList<>();

        Reader in = new FileReader("src/Day13/inputFile.txt");
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

        int i = 0;
        while (i < nums.size()) {
            machines.add(new Machine(nums.get(i), nums.get(i + 1), nums.get(i+2), nums.get(i+3), nums.get(i+4), nums.get(i+5)));
            i+=6;
        }

        var counters = new long[2];
        for (Machine machine : machines) {
            var left = machine.ay * machine.bx - machine.ax * machine.by;
            var right = machine.prizeY * machine.bx - machine.prizeX * machine.by;

            if (right % left == 0) {
                var aCount = right / left;

                var prizeXRemaining = machine.prizeX - machine.ax * aCount;
                if (prizeXRemaining % machine.bx == 0) {
                    var bCount = prizeXRemaining / machine.bx;

                    counters[0] += aCount;
                    counters[1] += bCount;
                }
            }
        }

        System.out.println(counters[0] * 3 + counters[1]);
    }

    record Machine(int ax, int ay, int bx, int by, long prizeX, long prizeY) {}
}