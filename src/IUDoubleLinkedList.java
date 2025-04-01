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

        // If the index is out of bounds throw an excpetion 
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        // check if you are inserting at the front or the back 
        if (index == 0) {
            addToFront(element);
            return;
        } else if (index == size) {
            addToRear(element);
            return;
        }

        // Create a new Node 
        Node<T> newNode = new Node<T>(element);

        // Traverses the list until currentNode references the node that we want to add in the element for
        Node<T> currentNode = head; 
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }

        // Set prevNode to be before currentNode and point to current Node 
        Node<T> prevNode = currentNode.getPreviousNode();

        // Change all of the points to reflect the new node that was put in 
        newNode.setNextNode(currentNode); 
        newNode.setPreviousNode(prevNode);
        prevNode.setNextNode(newNode);
        currentNode.setPreviousNode(newNode);

        size++;
        modCount++;
        
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
        Node<T> newNode = new Node<T>(element);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNextNode(head);
            head.setPreviousNode(newNode);
            head = newNode;
        }
        size++;
        modCount++;
        
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

        // Traverse the loop looking for the target 
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.getElement().equals(target)) {
                return true;
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        return false;
    }

    @Override
    public T first() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return head.getElement();
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
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T last() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return tail.getElement();
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
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (head.getElement().equals(element)) {
            head = null;
            tail = null;
        }
        Node<T> current = head;
        while(current != null && !current.getElement().equals(element)) {
            current = current.getNextNode();
        }
        if (current == null) {
            throw new NoSuchElementException();
        }
        if (current == head) {
            head = head.getNextNode();
            head.setPreviousNode(null);
        } else if (current == tail) {
            tail = tail.getPreviousNode();
            tail.setNextNode(null);
        } else {
            current.getPreviousNode().setNextNode(current.getNextNode());
            current.getNextNode().setPreviousNode(current.getPreviousNode());
        }
        T retVal = current.getNextNode().getElement();
        size--;
        modCount++;
        return retVal;
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
        T retVal = head.getElement();
        if (size == 1) {
            head = null; 
            tail = null;
        } else {
            head = head.getNextNode();
            head.setPreviousNode(null);
        }
        size--;
        modCount++;
        return retVal;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head.setElement(element);
        } else {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNextNode();
            }
            currentNode.setElement(element);
        }
        modCount++;        
    }

    @Override
    public int size() {
        return size;
    }
    
}
