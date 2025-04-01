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
        // Traverse to find the element attached to the node current
        
        // TO-DO 

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
            if (target == null) {
                if (isEmpty()) {
                    return true;
                }
            }
            else if (currentNode.getElement().equals(target)) {
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

        // Throw exception if the index is out of bounds
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        // Traverse the list to find the element 
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode.getElement();
    }

    @Override
    public int indexOf(T element) {
        
        // Traverse throught the list to find the first node that references this element we are searched for 
        Node<T> currentNode = head; 
        int currentIndex = 0;
        while (currentNode != null) {
            if (element == null) {
                if (currentNode.getElement() == null) {
                    return currentIndex;
                }
            } else if (element.equals(currentNode.getElement())) {
                return currentIndex;
            }
            currentNode = currentNode.getNextNode(); 
            currentIndex++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
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
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        if (startingIndex < 0 || startingIndex > size) {
            throw new IndexOutOfBoundsException();   
        }
        return new IUDoubleLinkedListIterator(startingIndex);
    }

    private class IUDoubleLinkedListIterator implements ListIterator<T> {
        private Node<T> nextNode;
        private Node<T> lastReturned;
        private int nextIndex;
        private int expectedModCount;

        public IUDoubleLinkedListIterator(int startingIndex) {
            expectedModCount = modCount;
            nextIndex = startingIndex;
            nextNode = head;
            // Traverse to the starting node
            for (int i = 0; i < startingIndex; i++) {
                nextNode = nextNode.getNextNode();
            }
            lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = nextNode;
            nextNode = nextNode.getNextNode();
            nextIndex++;
            return lastReturned.getElement();
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            if (modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextNode == null) { // We're at the end
                nextNode = tail;
            } else {
                nextNode = nextNode.getPreviousNode();
            }
            lastReturned = nextNode;
            nextIndex--;
            return lastReturned.getElement();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T e) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // Find the node containing the element and checks for null value and handles it 
        Node<T> currentNode = head;
        while (!currentNode.getElement().equals(element) && currentNode.getNextNode() != null) {
                currentNode = currentNode.getNextNode();      
        }
        // Feed the removed node and element to the hungry trash collector
        if  (!currentNode.getElement().equals(element)) {
            throw new NoSuchElementException();
        }
        else if (currentNode == head) {
            head = currentNode.getNextNode();
            head.setPreviousNode(null);
        }
        else if (currentNode == tail) {
            tail = currentNode.getPreviousNode();
            tail.setNextNode(null);
        } else {
            currentNode.getPreviousNode().setNextNode(currentNode.getNextNode());
            currentNode.getNextNode().setPreviousNode(currentNode.getPreviousNode());
        }
        size--;
        modCount++;
        return element;
    }

    @Override
    public T remove(int index) {

        // Check the index is in bounds 
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        else if (index == 0) {
            return removeFirst();
        }
        else if (index == (size - 1)) { // If last element of the list 
            return removeLast();
        }
        T retVal; // Return the element that we removed
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        retVal = currentNode.getElement();
        currentNode.getPreviousNode().setNextNode(currentNode.getNextNode());
        currentNode.getNextNode().setPreviousNode(currentNode.getPreviousNode());
        size--;
        modCount++;
        return retVal;
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
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        currentNode.setElement(element);
        modCount++;        
    }

    @Override
    public int size() {
        return size;
    }
    
}
