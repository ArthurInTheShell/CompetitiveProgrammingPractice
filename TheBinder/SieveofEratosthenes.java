import java.util.ArrayList;

public class SieveofEratosthenes {
    private static int sieve_size = 1000000;
    private static boolean[] primeArr = new boolean[sieve_size];
    private static ArrayList<Integer> prime = new ArrayList<Integer>();
    public static void main(String...Args){

        for(int i =0; i < sieve_size;i++){
            primeArr[i]=true;
        }
        //StringBuilder sb = new StringBuilder();
        //sb.append("private static int [] prime = {");
        for(int i =2; i < sieve_size;i++){
            if(primeArr[i]==true){
                for(int j = 2; j*i < sieve_size; j++){
                    primeArr[i*j] = false;
                }
                prime.add(i);
                //sb.append(i);
                //sb.append(",");
            }
        }
        //sb.deleteCharAt(sb.length()-1);
        //sb.append("};");
        //System.out.println(sb.toString());
        System.out.println(isPrime(2147483647));
        System.out.println(isPrime(136117223861L));
        System.out.println(isPrime(999999999989L));
    }

    public static boolean isPrime(long n){
        if(n<sieve_size) {
            return primeArr[(int)n];
        }
        for(int i=0;i<prime.size();i++){
            if(n % prime.get(i) ==0) {
                return false;
            }
        }
        return true;
    }


}
