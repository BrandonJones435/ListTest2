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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("My guy your index is out of bounds");
        }
        
        // If adding at the end
        if (index == size) {
            add(element);
            return;
        }
        
        Node<T> newNode = new Node<>(element);
        
        // If adding at the front
        if (index == 0) {
            newNode.setNextNode(head);
            head = newNode;
            // If list was empty update tail as well
            if (size == 0) {
                tail = newNode;
            }
        } else {
            // Traverse to the node right before the insertion point
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNextNode();
            }
            newNode.setNextNode(current.getNextNode());
            current.setNextNode(newNode);
        }
        
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
        Node<T> current = head;
        while (current != null) {
            if (current.getElement().equals(target)) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
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
        return new SLLIterator();
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T lastElement = tail.getElement();
        return lastElement;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        if (startingIndex < 0 || startingIndex > size) {
            throw new IndexOutOfBoundsException();
        }
        return null;
    }

    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty; nothing to remove.");
        }
        T removedElement;
        // Special case: element is at the head
        if (head.getElement().equals(element)) {
            removedElement = head.getElement();
            head = head.getNextNode();
            if (head == null) {
                tail = null;
            }
            size--;
            modCount++;
            return removedElement;
        }
        
        // Traverse until we find the target element
        Node<T> previous = head;
        Node<T> current = head.getNextNode();
        while (current != null && !current.getElement().equals(element)) {
            previous = current;
            current = current.getNextNode();
        }
        
        if (current == null) {
            throw new NoSuchElementException("There is no element like that in here!");
        }
        
        // Remove the target node
        removedElement = current.getElement();
        previous.setNextNode(current.getNextNode());
        if (current == tail) {
            tail = previous;
        }
        size--;
        modCount++;
        return removedElement;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index way to big or small my guy!");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        current.setElement(element);
        modCount++;
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
        private Node<T> lastReturned;
        private Node<T> previous;
        private int iterModCount;

        /**
         * Initialize Iterator before first element.
         */
        public SLLIterator() {
            nextNode = head;
            lastReturned = null;
            previous = null;
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
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Save reference to allow remove()
            lastReturned = nextNode;
            T retVal = nextNode.getElement();
            // Update previous pointer appropriately.
            if (previous == null && head == lastReturned) {
                // first element was returned
            } else {
                previous = lastReturned; 
            }
            nextNode = nextNode.getNextNode();
            return retVal;
        }

        @Override
        public void remove() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            if (head == lastReturned) {
                head = nextNode;
            } else {
                Node<T> predecessor = head;
                while (predecessor.getNextNode() != lastReturned) {
                    predecessor = predecessor.getNextNode();
                }
                predecessor.setNextNode(nextNode);
                if (lastReturned == tail) {
                    tail = predecessor;
                }
            }
            lastReturned = null;
            size--;
            modCount++;
            iterModCount = modCount;
        }
    }
}
