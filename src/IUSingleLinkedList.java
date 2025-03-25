import java.security.NoSuchAlgorithmException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked-Node-based implementaiton of IndexedUnsortedList
 * 
 * @author Brandon Jones CS221-3 Sp2025
 */

public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
    Node<T> head; 
    Node<T> tail;
    int size = 0;
    int rear = 0;
    int modCount = 0;
    private boolean canRemove = true; 
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

    public void addToFront(T element) {
        Node<T> newNode = new Node<T>(element);
        newNode.setNextNode(head); 
        if (isEmpty()) {
            tail = newNode;
        } else {
            newNode.setNextNode(head);
        }
        head = newNode; 
        size++;
        modCount++;
        
    }

    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<T>(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNextNode(newNode);
        }
        tail = newNode;
        size++;
        modCount++;
    }

    @Override
    public boolean contains(T target) {
        return indexOf(target) > -1;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.getElement();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head; 
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode.getElement();
    }

    @Override
    public int indexOf(T element) {
        Node<T> currentNode = head; 
        int currentIndex = 0;
        boolean foundIt = false; 
        while(currentNode != null && !foundIt) {
            if (currentNode.getElement().equals(element);) {
                foundIt = true;
            }  else {
                currentNode = currentNode.getNextNode();
                currentIndex++;
            }
        }
        if (!foundIt) {
            currentIndex = -1; 
        } else {
            return currentIndex; 
        }
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
        if (isEmpty()) {

        }
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
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T element = head.getElement();
        head = head.getNextNode();
        if (head == null || size == 1) {
            tail = null;
        }

        size--;
        modCount++;
        return element;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();
        if (size == 1) {
            head = tail = null; 
        } else {
            Node<T> newTail = head; 
            while (newTail.getNextNode() != tail) {
                newTail = newTail.getNextNode();
            }        
            tail = newTail; 
            newTail.setNextNode(null);
        }
        size--;
        modCount++;
        return retVal;
    }

    @Override
    public void set(int index, T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int size() {
        return size == 0;
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
    
    /**
     * Basic Iterator for IUSingleLinkedList.
     */
    private class SLLIterator implements Iterator<T> {
        private T element = element;
        private Node<T> nextNode;
        private Node<T> previousNode;
        private int iterModCount;

        /**
         * Initialize Iterator before first element.
         */
        public SLLIterator() {
            nextNode = head;
            iterModCount = modCount;
            canRemove = false;
        }
        @Override
        public boolean hasNext() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextNode != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                throw new NoSuchElementException();
            }
            T retVal = nextNode.getElement();
            nextNode = nextNode.getNextNode();
            return retVal;
        }

        @Override
        public void remove() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!canRemove) {
                throw new IllegalStateException();
            }
            canRemove = false;
            if (head.getNextNode() == nextNode) { // removing the head
                head = nextNode; 
                if (nextNode == null) { // removing ONLY node
                    tail = null; 
                }
            } else {
                Node<T> prevPrevNode = head; 
                while (prePrevNode.getNextNode().getNextNode() != nextNode) {
                    prevPrevNode = prevPrevNode.getNextNode();
                }
                prevPrevNode.setNextNode(nextNode);
                if (nextNode == null) {
                    tail = prePrevNode;
                }
            }
            size--;
            modCount++;
            iterModCount++;
        }
    }
}
