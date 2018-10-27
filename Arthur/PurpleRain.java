import java.util.Scanner;

public class PurpleRain {
  public static void main(String... Args) {
    Scanner sc = new Scanner(System.in);
    int globalmax = Integer.MIN_VALUE;
    int globalmin = Integer.MAX_VALUE;
    int startmin = 0;
    int startmax = 0;
    int endmin = 0;
    int endmax = 0;
    int localmin = 0;
    int localmax = 0;
    int smax = 0;
    int smin = 0;
    String str = sc.next();
    sc.close();
    char[] carr = str.toCharArray();
    int temp = 0;
    for (int i = 0; i < carr.length; i++) {
      temp = 1;
      if (carr[i] == 'B') {
        temp = -1;
      }
      localmax += temp;
      localmin += temp;
      if (globalmin > localmin) {
        globalmin = localmin;
        startmin = smin;
        endmin = i;
      }
      if (globalmax < localmax) {
        globalmax = localmax;
        startmax = smax;
        endmax = i;
      }
      if (localmax < 0) {
        localmax = 0;
        smax = i + 1;
      }
      if (localmin > 0) {
        localmin = 0;
        smin = i + 1;
      }
    }
    // System.out.println("globalmax: " + globalmax);
    // System.out.println("globalmin: " + globalmin);
    if ((0 - globalmin) > globalmax) {
      System.out.println((startmin + 1) + " " + (endmin + 1));
    } else if ((0 - globalmin) == globalmax) {
      if (startmax < startmin) {
        System.out.println((startmax + 1) + " " + (endmax + 1));
      } else {
        System.out.println((startmin + 1) + " " + (endmin + 1));
      }
    } else {
      System.out.println((startmax + 1) + " " + (endmax + 1));

    }
  }
}
