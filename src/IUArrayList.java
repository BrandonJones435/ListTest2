import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-List-based implementaiton of IndexedUnsortedList
 * 
 * @author Brandon Jones CS221-3 Sp2025
 */

public class IUArrayList<T> implements IndexedUnsortedList<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private T[] data;
    private int size;
    private int modCount;
    
    public IUArrayList() {
        data = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
        modCount = 0;
    }
    
    @Override
    public void add(T element) {
        data[size] = element;
        size++;
        modCount++;
    }
    
    @Override
    public void add(int index, T element) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        // Shift elements to the right
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = element;
        size++;
        modCount++;
    }
    
    @Override
    public void addAfter(T element, T target) {
        int index = indexOf(target);
        if(index == -1) {
            throw new NoSuchElementException();
        }
        add(index + 1, element);
    }
    
    public void addToFront(T element) {
        add(0, element);
    }
    
    @Override
    public void addToRear(T element) {
        add(element);
    }
    
    @Override
    public boolean contains(T target) {
        return indexOf(target) != -1;
    }
    
    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }
    
    @Override
    public T get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }
    
    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if(data[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
    
    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[size - 1];
    }
    
    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public T remove(T element) {
        int index = indexOf(element);
        if(index == -1) {
            throw new NoSuchElementException("Element not found");
        }
        return remove(index);
    }
    
    @Override
    public T remove(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null; 
        modCount++;
        return removed;
    }
    
    @Override
    public T removeFirst() { 
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(0);
    }
    
    @Override
    public T removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(size - 1);
    }
    
    @Override
    public void set(int index, T element) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = element;
        modCount++;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < size; i++){
            sb.append(data[i]);
            if(i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    private class ArrayIterator implements Iterator<T> {
        private int currentIndex;
        private int expectedModCount;
        private boolean canRemove;
        
        public ArrayIterator() {
            currentIndex = 0;
            expectedModCount = modCount;
            canRemove = false;
        }
        
        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return currentIndex < size;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            canRemove = true;
            return data[currentIndex++];
        }
        
        @Override
        public void remove() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!canRemove) {
                throw new IllegalStateException();
            }
            IUArrayList.this.remove(--currentIndex);
            expectedModCount = modCount;
            canRemove = false;
        }
    }
}
