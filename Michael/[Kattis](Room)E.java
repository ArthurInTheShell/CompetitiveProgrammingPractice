import java.util.Arrays;
import java.util.Scanner;

public class E {
    public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();
    Room[] rooms = new Room[n];
    for(int i=0;i<n;i++){
        int size = scan.nextInt();
        Room r = new Room(i+1,size);
        rooms[i]=r;
    }
        Arrays.sort(rooms);
        int size1 = rooms[0].size;
        int sumSizeOther = 0;
        boolean possible = false;
        for(int i=1;i<rooms.length;i++){
            sumSizeOther+=rooms[i].size;
            if(sumSizeOther>=size1){
                possible=true;
                break;
            }
        }
        if(!possible){
            System.out.println("impossible");
        }else{
            StringBuilder build = new StringBuilder();
            for(Room r:rooms){
                build.append(r.index).append(' ');
            }
            build.setLength(build.length()-1);
            System.out.println(build.toString());
        }
    }
    static class Room implements Comparable<Room>{
        int index;
        int size;
        Room(int i,int s){
            index = i;
            size = s;
        }
        public int compareTo(Room o){
            return o.size-size;
        }
    }
}
