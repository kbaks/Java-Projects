import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;			// array of items
    private int n;            	// number of elements on RandomizedQueue
	
    
    //  initializes an empty RandomizedQueue.
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    // is this stack empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items in the stack.
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;
        // StdOut.print("resize to capacity = " + capacity + "\n");
		// create new array of items with desired capacity and copy the elements
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;		// copy array back to the original array
    }

    // add the item to this RandomizedQueue.
    public void enqueue(Item item) {             // add the item
        if (item == null) { throw new NullPointerException("null element"); }
        //StdOut.print("enqueue item = " + item + "\n");
        if (n == a.length) resize(2*a.length);   // double size of array if necessary
        a[n++] = item;                             	 // add item and increase index
    }

    // removes and returns a random item form the RandomizedQueue.
    public Item dequeue() {                        // remove and return a random item
        if (isEmpty()) { throw new NoSuchElementException("Stack underflow"); }
        StdRandom.shuffle(a, 0, n-1);			// shuffle items and return the last
        Item item = a[n-1];
        a[n-1] = null;                             	// avoid loitering
        n--;												// decrease index
        //StdOut.print("dequeue item = " + item + "\n");
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);	// halve size of array if necessary
        return item;
    }

    // Returns (but does not remove) a random item.
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        StdRandom.shuffle(a, 0, n-1);
        return a[n-1];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomArrayIterator implements Iterator<Item> {
        private int i = n-1;				// initialize index to size-1
        private Item[] copy;

        public RandomArrayIterator() {
            copy = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                copy[j] = a[j];
            }
			// shuffle the array so that the iterator is independently random
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return i >= 0;					// if index is negative the RandomizedQueue is empty
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy[i--];			// return element and decrease index
        }
    }

    public static void main(String[] args) {
        /* RandomizedQueue<String> randQ = new RandomizedQueue<String>();
        //StdOut.print("Play with RandomizedQueue... ");
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            //StdOut.print("s = " + s + "\n");
            if (s.equals("-")) StdOut.print(randQ.dequeue() + "\n");
            else randQ.enqueue(s);
        }
        //StdOut.println("Using Iterator!");
        randQ.enqueue("1");
        randQ.enqueue("2");
        randQ.enqueue("3");
        randQ.enqueue("4");
        randQ.enqueue("5");
        randQ.enqueue("6");
        Iterator<String> iter = randQ.iterator();
        for (String s : randQ)
            StdOut.println(s);*/
    }
}