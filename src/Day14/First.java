package Day14;

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
        List<Robot> robots = new ArrayList<>();
        int matX = 101;
        int matY = 103;
        int[][] matrix = new int[matY][matX];

        Reader in = new FileReader("src/Day14/inputFile.txt");
        BufferedReader br = new BufferedReader(in);
        String line;

        Pattern pattern = Pattern.compile("-?\\d+");
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
            robots.add(new Robot(nums.get(i), nums.get(i + 1), nums.get(i+2), nums.get(i+3)));
            i+=4;
        }

        for(Robot robot : robots) {
            int x = robot.vx * 100 % matX + robot.px;
            int y = robot.vy * 100 % matY + robot.py;
            if(x>=matX){
                x-=matX;
            }else if(x<0){
                x+=matX;
            }
            if(y>=matY){
                y-=matY;
            }else if(y<0){
                y+=matY;
            }

            matrix[y][x]++;
        }

        int count1 = 0;
        for(int j = 0; j < matY/2; j++){
            for(int m = 0; m < matX/2; m++){
                count1 += matrix[j][m];
            }
        }

        int count2 = 0;
        for(int j = matY - 1; j > matY/2; j--){
            for(int m = 0; m < matX/2; m++){
                count2 += matrix[j][m];
            }
        }

        int count3 = 0;
        for(int j = 0; j < matY/2; j++){
            for(int m = matX - 1; m > matX/2; m--){
                count3 += matrix[j][m];
            }
        }

        int count4 = 0;
        for(int j = matY - 1; j > matY/2; j--){
            for(int m = matX - 1; m > matX/2; m--){
                count4 += matrix[j][m];
            }
        }

        System.out.println(count1 * count2 * count3 * count4);
        in.close();
    }

    record Robot(int px, int py, int vx, int vy) {}
}
