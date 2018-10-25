import java.util.ArrayList;
import java.util.Scanner;

public class RoundTableKnight {
    public static void main(String...Args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Boolean flag = true;
        ArrayList<Boolean> table = new ArrayList<Boolean>();
        for (int i =0;i<n;i++){
            table.add(sc.nextInt()==1 ? true:false);
        }
        sc.close();
        for(int i=3; i<= n; i++){
            if(n%i == 0){
                int k = n/i;
                for(int z=0; z<k;z++){
                    flag = true;
                    for (int j =0; j<i; j++){
                        if(table.get(z+j*k) == false){
                            flag = false;
                        }
                    }
                    if(flag){
                        System.out.println("YES");
                        return;
                    }
                }
            }

        }
        System.out.println("NO");
        return;
    }
}
