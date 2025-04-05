import java.util.ConcurrentModificationException;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.getElement());
            if (current.getNextNode() != null) {
                sb.append(", "); 
            }
            current = current.getNextNode();
        }
        sb.append("]");
        return sb.toString();
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
        while (currentNode != null && !currentNode.getElement().equals(target)) {
            currentNode = currentNode.getNextNode();
        }
        if (currentNode == null) {
            throw new NoSuchElementException();
        } 
        else  if (currentNode == tail) {
            currentNode.setNextNode(newNode);
            newNode.setPreviousNode(currentNode); 
            newNode.setNextNode(null);
            tail = newNode;
        } else {
            newNode.setNextNode(currentNode.getNextNode());
            newNode.setPreviousNode(currentNode);
            currentNode.setNextNode(newNode);
            newNode.getNextNode().setPreviousNode(newNode);
        }
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
        if (index < 0 || index >= size) {
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
        private Node<T> currentNode = head;
        private boolean canRemove = false;
        private Node<T> nextNode = head;
        private Node<T> lastReturned;
        private int nextIndex;
        private int expectedModCount = modCount;
        private int iterModCount;

        // public IUDoubleLinkedIterator() {
        //     lastReturend = null; 
        // }

        private void checkForConcurrentModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    
        /**
         * Intialize iterator before startingIndex
         * @param startingIndex index of element that would be next
         */
        public IUDoubleLinkedListIterator(int startingIndex) {
            expectedModCount = modCount;
            nextIndex = startingIndex;
            if (startingIndex < 0 || startingIndex > size) {
                throw new IndexOutOfBoundsException();
            }
            // Traverse to the startingIndex
            nextNode = head;
            for (int i = 0; i < startingIndex; i++) { // TODO have the starting index loop decrement or increment based on where it is in the list 
                nextNode = nextNode.getNextNode();
            }
            nextIndex = startingIndex;
            iterModCount = modCount;
            lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            checkForConcurrentModification();
            return nextNode != null;
        }

        @Override
        public T next() {
            checkForConcurrentModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = nextNode;
            T retVal = nextNode.getElement();
            nextNode = nextNode.getNextNode();
            nextIndex++;
            return retVal;
        }

        @Override
        public boolean hasPrevious() {
            checkForConcurrentModification();
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            checkForConcurrentModification();
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
            checkForConcurrentModification();
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            checkForConcurrentModification();
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkForConcurrentModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            // Remove lastReturned from the list 
            if (lastReturned == head) {
                if (size == 1) {
                    head = null; 
                    tail = null; 
                } else {
                    head = lastReturned.getNextNode();
                    head.setPreviousNode(null);
                }
            } else if (lastReturned == tail) {
                tail = lastReturned.getPreviousNode();
                tail.setNextNode(null);
            } else {
                lastReturned.getPreviousNode().setNextNode(lastReturned.getNextNode());
                lastReturned.getNextNode().setPreviousNode(lastReturned.getPreviousNode());
            }

            if (nextNode == lastReturned) {
                nextNode = lastReturned.getNextNode();
            } else {
                nextIndex--;
            }
            // Clean up 
            size--;
            modCount++;
            iterModCount++;
            expectedModCount = modCount;
            lastReturned = null;
        }

        @Override
        public void set(T e) {
            checkForConcurrentModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.setElement(e);
            lastReturned = null; 
        }

        @Override
        public void add(T e) {
            checkForConcurrentModification();
            Node<T> newNode = new Node<>(e);
            
            // If adding into an empty list
            if (head == null) {
                head = newNode;
                tail = newNode;
            } 
            // Inserting before head (i.e. when nextNode is head)
            else if (nextNode == head) {
                newNode.setNextNode(head);
                head.setPreviousNode(newNode);
                head = newNode;
            }
            // Inserting at the end (i.e. when nextNode is null)
            else if (nextNode == null) {
                tail.setNextNode(newNode);
                newNode.setPreviousNode(tail);
                tail = newNode;
            }
            // Inserting in the middle
            else {
                Node<T> prev = nextNode.getPreviousNode();
                newNode.setPreviousNode(prev);
                newNode.setNextNode(nextNode);
                prev.setNextNode(newNode);
                nextNode.setPreviousNode(newNode);
            }
            
            size++;
            modCount++;
            expectedModCount = modCount;
            nextIndex++;
            lastReturned = null;
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
            if (size == 1) {
                head = null; 
                tail = null;
            } else {
                head = currentNode.getNextNode();
                head.setPreviousNode(null);
            }
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
        T retVal; // Return the element that we removed
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        if (index == (size - 1)) { // If last element of the list 
            return removeLast();
        }
        else if (currentNode == head) {
            if (size == 1) {
                head = null; 
                tail = null;
            } else {
                head = currentNode.getNextNode();
                head.setPreviousNode(null);
            }
        } else {
            currentNode.getPreviousNode().setNextNode(currentNode.getNextNode());
            currentNode.getNextNode().setPreviousNode(currentNode.getPreviousNode());
        }
        retVal = currentNode.getElement();
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
    
    /**
     * ListIterator (and basic Iterator) for IUDLL.
     */
    // private class IUDLLIterator implements Listiterator<T> {
    //     private Node<T> nextNode;
    //     private int iterModCount;

    //     /**
    //      * Initilize iterator before first element
    //      */
    //     public IUDLLIterator() {
    //         nextNode = head;
    //         iterModCount = modCount;
    //     }

    //     @Override
    //     public boolean hasNext() {
    //         if (iterModCount != modCount) {
    //             throw new ConcurrentModificationException();
    //         }
    //         return (nextNode != null);
    //     }

    //     @Override 
    //     public T next() {
    //         if (!hasNext()) {
    //             throw new NoSuchElementException();
    //         }
    //         T retVal
    //         nextNode = nextNode.getPreviousNode();
    //         return nextNode.getPreviousNode().getElement();
    //     }

    //     @Override 
    //     public boolean hasPrevious() {
    //         throw new UnsupportedOperationException();
    //     }

    //     @Override 
    //     public T previous() {
    //         throw new UnsupportedOperationException();
    //     }
    // }

}
