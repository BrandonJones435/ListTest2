/**
 * Reusable node for creating linked linear data structures.
 * @author Brandon Jones CS221-3 Sp2025
 */
public class Node<T> {
    private T element;
    private Node<T> nextNode;
    private Node<T> previousNode;

    public Node(T element) {
        this.element = element;
        nextNode = null;
        previousNode = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node<T> getNextNode() {
        return nextNode;
    }

    public Node<T> getPreviousNode() {
        return previousNode;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

    public void setPreviousNode(Node<T> previousNode) {
        this.previousNode = previousNode;
    }

    public Node(T element, Node<T> nextNode) {
        this.element = element; 
        this.nextNode = nextNode;
    }
}
