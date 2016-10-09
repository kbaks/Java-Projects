import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Sentinel sentNode;
    
    private class Sentinel
    {
        int size = 0;
        Node front;
        Node end;
    }
    private class Node
    {
        Item item = null;
        Node next = null;
        Node prev = null;
    }
    
    public Deque()                           // construct an empty deque
    {    
        sentNode = new Sentinel();
    }
    
    public boolean isEmpty()                 // is the deque empty?
    {    return sentNode.size == 0;    }
    
    public int size()                        // return the number of items on the deque
    {    return sentNode.size;    }
    
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) 
        {throw new NullPointerException("can't add null element");}
        Node first = new Node();
        first.item = item;
        first.prev = null;
        if (sentNode.size == 0) {
            first.next = null;
            sentNode.front = first;
            sentNode.end = first;
        }
        else {
            first.next = sentNode.front;
            sentNode.front.prev = first;
            sentNode.front = first;
        }
        sentNode.size++;
    }
    
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) 
        {throw new NullPointerException("can't add null element");}
        Node last = new Node();
        last.item = item;
        last.next = null;
        if (sentNode.size == 0) {
            last.prev = null;
            sentNode.front = last;
            sentNode.end = last;
        }
        else {
            last.prev = sentNode.end;
            sentNode.end.next = last;
            sentNode.end = last;
        }
        sentNode.size++;
    }
    
    public Item removeFirst()                // remove and return the item from the front
    {
        if (sentNode.size > 1) {
            Item item = sentNode.front.item;
            Node next = sentNode.front.next; 
            sentNode.front.item = null;
            sentNode.front.next = null;
            sentNode.front = next; 
            next.prev = null;
            sentNode.size--;
            return item;
        }
        else if (sentNode.size == 1) {
            Item item = sentNode.front.item;
            sentNode.front.item = null;
            sentNode.front.next = null;
            sentNode.front = null;
            sentNode.end = null;
            sentNode.size--;
            return item;
        }
        else { throw new NoSuchElementException("deque already empty!"); }
    }
    
    public Item removeLast()                 // remove and return the item from the end
    {
        if (sentNode.size > 1) {
            //StdOut.print("Remove when size = " + sentNode.size + "\n");
            Item item = sentNode.end.item;
            Node prev = sentNode.end.prev;
            sentNode.end.item = null;
            sentNode.end.prev = null;
            sentNode.end = prev; 
            prev.next = null;
            sentNode.size--;
            return item;
        }
        else if (sentNode.size == 1) {
            //StdOut.print("Last one \n");
            Item item = sentNode.end.item;
            sentNode.end.item = null;
            sentNode.end.next = null;
            sentNode.front = null;
            sentNode.end = null;
            sentNode.size--;
            return item;
        }
        else { throw new NoSuchElementException("deque already empty!"); }
    }
    
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {    return new ListIterator();    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = sentNode.front;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); } 
        public Item next()
        {
            if (!hasNext()) 
            { throw new NoSuchElementException("deque already empty!"); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args)   // unit testing
    {
        Deque<String> deque = new Deque<String>();
        StdOut.print("Play with Deque... ");
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            if (s.equals("-F")) StdOut.print(deque.removeFirst() + "\n");
            else if (s.equals("-E")) StdOut.print(deque.removeLast() + "\n");
            else deque.addLast(s);
        }
    }
    
}