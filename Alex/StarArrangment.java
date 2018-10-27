import java.util.ArrayList;
import java.util.Scanner;

public class StarArrangment {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();
    ArrayList<int[]> arr = new ArrayList<>();
    int bound = (int) Math.ceil(n / 2);
    for (int i = 2; i <= bound + 1; i++) {
      for (int j = 0; j < 2; j++) {
        int val = i - 1 + j;
        if (n % (val + i) == i || n % (val + i) == 0) {
          int[] cons = { i, val };
          arr.add(cons);
        }
      }
    }
    System.out.println(n + ":");
    for (int i = 0; i < arr.size(); i++) {
      int[] cons = arr.get(i);
      System.out.println(cons[0] + "," + cons[1]);
    }
    scan.close();
  }

}
