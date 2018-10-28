import java.util.Scanner;

public class HyperCube {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int dim = scan.nextInt();
        String code1 = scan.next();
        String code2 = scan.next();
        long lo = 0;
        long hi = 1;
        for(int i=0;i<dim;i++){
            hi*=2;
        }
        hi=hi-1;
        long pos1 = binarySearch(code1,0,false,lo,hi);
        long pos2 = binarySearch(code2,0,false,lo,hi);
        long result = Math.abs(pos1-pos2)-1;
        System.out.println(result);
    }

    public static long binarySearch(String code,int pos,boolean left1,long lo,long hi){
        if(hi==lo) return hi;
        int c = code.charAt(pos)-'0';
        boolean left = false;
        if(c==0){
            left = !left1;
            left1=!left;
        }else
        if(c==1){
            left = left1;
            left1=!left;
        }
        if(left){
            hi = (hi+lo)/2;
        }else{
            lo = (hi+lo)/2+1;
        }
        return binarySearch(code,pos+1,left1,lo,hi);
    }
}
