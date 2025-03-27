import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Double-linked-node-based implementation of IndexedUnsortedlist.
 * @author brandonjones and CS221-3 Sp2025
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int modCount;

    /**
     * Double Linked list
     */
    public IUDoubleLinkedList() {
        head = tail = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void add(int index, T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> newNode = new Node<T>(element);
        if (size == 0) {
            throw new NoSuchElementException("Target is not in the list");
        }
        Node<T> currentNode = head;
        boolean targetFound = false;
        // Traverse to find the element attached to the node current
        while (targetFound == false && currentNode.getNextNode() != null) {
            if (currentNode.getElement().equals(null)) {
                targetFound = true;
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        if (targetFound) {
            newNode.setNextNode(currentNode.getNextNode());
            newNode.setPreviousNode(currentNode);
            if (currentNode == tail) {
                tail = newNode;
            } else {
                newNode.getNextNode().setPreviousNode(newNode);
                currentNode.setNextNode(newNode); 
            }
        } else {
            throw new NoSuchElementException();
        }
        newNode = currentNode.getNextNode();
        size++; 
        modCount++;
    }

    @Override
    public void addToFront(T element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<T>(element); 
        newNode.setPreviousNode(tail);
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
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();
        if (size == 1) {
            head = null;
        } else {
            tail.getPreviousNode().setNextNode(null);
            tail = tail.getPreviousNode();
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
        // TODO Auto-generated method stub
        return 0;
    }
    
}
