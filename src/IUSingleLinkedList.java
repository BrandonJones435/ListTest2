import java.util.Iterator;
import java.util.ListIterator;

/**
 * Single-linked-Node-based implementaiton of IndexedUnsortedList
 * 
 * @author Brandon Jones CS221-3 Sp2025
 */

public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {

    @Override
    public void add(T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void add(int index, T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAfter(T element, T target) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addToFront(T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addToRear(T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean contains(T target) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public T first() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(T element) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T last() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T remove(T element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T remove(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T removeFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T removeLast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set(int index, T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public String toString() {
        //return ""; //TODO format: "[A, B, C]"
        StringBuilder str = new StringBuilder();
        str.append("["); 
        for(T element : this) {
            str.append(element.toString());
            str.append(", ");
        }
        if (rear > 0) {
            str.delete(str.length()-2, str.length());
        }
        str.append("]");
        return str; 
    }

}
