import java.util.*;

public class FreeWeights {
    public static void main(String... Args){


        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> r1 = new ArrayList<Integer>();
        ArrayList<Integer> r2 = new ArrayList<Integer>();
        HashSet<Integer> order1 = new HashSet<Integer>();
        HashSet<Integer> order2 = new HashSet<Integer>();
        ArrayList<Integer> r3 = new ArrayList<Integer>();
        ArrayList<Integer> r4 = new ArrayList<Integer>();
        HashSet<Integer> hs1 = new HashSet<Integer>();
        int x =0 ;
        // Read in
        for(int i = 0; i<n;i++){
            x = sc.nextInt();
            if (hs1.contains(x)){
                hs1.remove((Integer) x);
            }else{
                hs1.add((Integer) x);
            }
            r1.add((Integer) x);
        }

        // Read in
        for(int i = 0; i<n;i++){
            x = sc.nextInt();
            r2.add((Integer)x);

        }

        // Largest Standalone
        int standMax = 0;
        if(hs1.size()!=0){
        standMax = Collections.max(hs1);
        }

        // Find the binary search beginning point
        int m1 = Collections.max(r1);
        int m2 = Collections.max(r2);
        int max = m2;
        if(m1>m2){
            max = m1;
        }
        int min = 0;
        int searchPoint = (max-min)/2;
        //ArrayList<Integer> temp = new ArrayList<Integer>();
        //ArrayList<Integer> temp2 = new ArrayList<Integer>();
        int temp3 = -1;
        r3 = (ArrayList)r1.clone();
        r4 = (ArrayList)r2.clone();
        ArrayList<Integer> a = new ArrayList<Integer>();
        while (temp3 != searchPoint){
            temp3 = searchPoint;
            //temp = rCD(r3,searchPoint);
            //temp2 = rCD(r2,searchPoint);
            if((rCD(r3,searchPoint).size() == 0) && (rCD(r4,searchPoint).size() == 0)){
                max = searchPoint;
                searchPoint = (max-min)/2 + min;
               // System.out.println("low");
            }else {
                min = searchPoint+1;
                searchPoint = max -(max-min)/2;
                //System.out.println("high");
            }
            r3 = (ArrayList)r1.clone();
            r4 = (ArrayList)r2.clone();
            a = new ArrayList<Integer>();
            //System.out.println(searchPoint);
        }
        System.out.println(temp3);

        sc.close();
    }

    static ArrayList<Integer> rCD (ArrayList<Integer> a, Integer sp) {
        ArrayList<Integer> a2b = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();
        String[] p = new String[0];
        List<String> l = new ArrayList<String>();

        for (Integer j:
             a) {
            if(j>sp){
                a2b.add(j);
            }
        }
        int i = 0;
        int n = a2b.size();
        while(i< n-1){

                if(a2b.get(i).equals(a2b.get(i+1))){
                    i ++;
                }else {
                    b.add(a2b.get(i));
                }

            i++;
        }
        if(i == n-1){
            b.add(a2b.get(i));
        }


        return b;
    }
}
