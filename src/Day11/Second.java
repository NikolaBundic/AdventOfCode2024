package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Second {
    public static void main(String[] args) throws IOException {

        ArrayList<Long> list = new ArrayList<>();

        Path filePath = Paths.get("src/Day11/inputFile.txt");
        String line = new String(Files.readAllBytes(filePath));

        String[] tokens = line.split("\\s+");
        for (String token : tokens) {
            list.add(Long.parseLong(token));
        }

        Map<Long, Map<Long, Long>> map = new HashMap<>();
        long solution = 0;

        for (Long aLong : list) {
            solution += 1 + solution(aLong, 1, map, 75);
        }

        System.out.println(solution);
    }

    public static long solution(long num, long step, Map<Long, Map<Long, Long>> map, int stepLimit){
        if(step > stepLimit){
            return 0;
        }

        map.putIfAbsent(num, new HashMap<>());

        if(map.get(num).containsKey(step)){
            return map.get(num).get(step);
        }
        if(num == 0){
            long x = solution(1, step+1, map, stepLimit);
            map.get(num).put(step, x);
            return x;
        }
        if(num == 1){
            long x = solution(2024, step+1, map, stepLimit);
            map.get(num).put(step, x);
            return x;
        }

        String x = Long.toString(num);
        long len = x.length();

        if(len%2 == 0){
            long first = Long.parseLong(x.substring(0, (int) (len/2)));
            long second = Long.parseLong(x.substring((int)len/2));
            long solution = 1 + solution(first, step+1, map, stepLimit) + solution(second, step+1, map, stepLimit);
            map.get(num).put(step, solution);
            return solution;
        }else{
            long solution = solution(num*2024, step+1, map, stepLimit);
            map.get(num).put(step, solution);
            return solution;
        }
    }
}
