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
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNextNode(newNode);
            tail = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void add(int index, T element) {
        Node<T> newNode = new Node<>(element);
        Node<T> current = head;
        // Moving to the node right before the spot we insert the new node
        for (int i = 0; i < index - 1; i++) {
            current = current.getNextNode();
        }
        newNode.setNextNode(current.getNextNode());
        current.setNextNode(newNode);
        size++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<T> newNode = new Node<>(element);
        Node<T> current = head; 
        // Traversing to the node right before our target 
        while (current != null && !current.getElement().equals(target)) {
            current = current.getNextNode();
        }
        if (current == null) {
            throw new NoSuchElementException();
        }
        newNode.setNextNode(current.getNextNode());
        current.setNextNode(newNode);
        if (current == tail) {
            tail = newNode;
        }
        size++;
        modCount++;
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
        while(currentNode != null) {
            if (currentNode.getElement().equals(element)) {
                return currentIndex;
            }  else {
                currentNode = currentNode.getNextNode();
                currentIndex++;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
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
        if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in this list no remove! You've already taken Everything!");
        }
        T removedElement; // Initialize the element we are removing
        // If you element is at the head 
        if (head.getElement().equals(element)) {
            removedElement = head.getElement();
            head = head.getNextNode();
            // If you remove the head then set your tail also to null
            if (head == null) {
                tail = null;
            }
            size--;
            modCount++;
            return removedElement;
        }
        // If the element is not the head then traverse
        Node<T> previous = head;
        Node<T> current = head.getNextNode();

        while (current != null && current.getElement().equals(element)) {
            removedElement = current.getElement();
            previous.setNextNode(current.getNextNode()); // Essentially setting the node with the element to death
            if (current == tail) {
                tail = previous;
            }
            size--;
            modCount++;
            return removedElement;
        }

        // If after all of our efforts we did not find the element
        throw new NoSuchElementException("There is no element like that in here!");
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Your index is way too big or small! Not sure which one");
        }
        T removedElement;
        // If we are removing the first element in the list 
        if (index == 0) {
            removedElement = head.getElement();
            head = head.getNextNode();
            // If the list becomes empty
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> current = head;
            // Traverse to the node before the node you want removed
            for (int i = 0; i < index - 1; i++) {
            current = current.getNextNode();
            }
            // Remove the element after the current node 
            removedElement = current.getNextNode().getElement();
            current.setNextNode(current.getNextNode().getNextNode());
            // If the node we removed then update the tail reference
            if (current.getNextNode() == null) {
                tail = current;
            }
        }
        size--;
        modCount++;
        return removedElement;
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
        return size;
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
        return str.toString(); 
    }
    
    /**
     * Basic Iterator for IUSingleLinkedList.
     */
    private class SLLIterator implements Iterator<T> {
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
                while (prevPrevNode.getNextNode().getNextNode() != nextNode) {
                    prevPrevNode = prevPrevNode.getNextNode();
                }
                prevPrevNode.setNextNode(nextNode);
                if (nextNode == null) {
                    tail = prevPrevNode;
                }
            }
            size--;
            modCount++;
            iterModCount++;
        }
    }
}
