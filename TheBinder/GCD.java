import java.util.Scanner;

public class GCD {
    public static void main(String...Args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.close();
        int c = gcd(n,m);
        int d = n*(m/c);
        System.out.println("gcd: "+c);
        System.out.println("lcm: "+d);
    }

    private static int gcd(int u, int v) {
        return u%v==0?v:gcd(v,u%v);
    }

}
