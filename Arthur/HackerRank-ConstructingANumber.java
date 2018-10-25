import javafx.util.Pair;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class ConstructingANumber{

    // Complete the solve function below.
    static long solve(int n, int k) {
        long sum = 0;
        int different = 0;
        int ave = 0;
        int limit = 2*n-1;
        for(int i=k; i<=limit;i+=k){
            if(i-n>0){
                sum += i%2 == 0 ? i/2-(i-n) : i/2 + 1 -(i-n);
            }else{
                sum += i%2 == 0 ? i/2-1 : i/2;
            }
        }
        return sum;

    }

    private static final Scanner scanner = new Scanner(System.in);



    public static void main(String[] args) throws IOException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        long a = System.currentTimeMillis();
        HashMap<Pair<Integer,Integer>,Long> reduceCost = new HashMap<Pair<Integer, Integer>, Long>();
        for (int tItr = 0; tItr < t; tItr++) {
            String[] nk = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nk[0]);

            int k = Integer.parseInt(nk[1]);

            Pair<Integer,Integer> myKey = new Pair<Integer,Integer>(n,k);

            long result =0;

            if(reduceCost.keySet().contains(myKey)){
                result = reduceCost.get(myKey);
            }else{
                result = solve(n, k);
                reduceCost.put(myKey,result);
            }


            System.out.println(result);
            System.out.println(System.currentTimeMillis()-a);
//            bufferedWriter.write(String.valueOf(result));
//            bufferedWriter.newLine();
        }

//        bufferedWriter.close();

        scanner.close();
    }
}
