import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        //Deque<String> deque = new Deque<String>();
        //StdOut.print("k = " + k);
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) 
        { randQ.enqueue(StdIn.readString()); }
        for( int i = 0; i < k ; i++) { 
            System.out.println(randQ.dequeue()); 
        }
    }
}