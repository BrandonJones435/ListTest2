import java.util.Comparator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Mergesort algorithm.
 *
 * @author CS221
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new IUDoubleLinkedList<T>();
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		mergesort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		mergesort(list, c);
	}
	/** 
	 * Merge method to be used in Mergesort algorithm. It compares the left and
	 * right side of list to be used when they are down to one or less elements. 
	 * Then based on which size has the the generics size adds the smaller one to
	 * the left and the larger to the right. 
	 * 
	 * @param <T> The type of element in my list 
	 * 
	 * @param left a sorted IndexedUnsortedList<T> whose contents will be merged;
	 * 
	 * @param left a sorted IndexedUnsortedList<T> whose contents will be merged;
	 * 
	 * @param list a sorted IndexedUnsortedList<T> where left and right will be merged into;
	 */

	 private static <T extends Comparable<? super T>> void merge (
		IndexedUnsortedList<T> left, 
		IndexedUnsortedList<T> right, 
		IndexedUnsortedList<T> list) 
		{
    	// as long as both sides have elements
		while (!left.isEmpty() && !right.isEmpty()) {
			// compare the first elements
			if (left.get(0).compareTo(right.get(0)) <= 0) {
				list.add(left.removeFirst());
			} else {
				list.add(right.removeFirst());
			}
		}
		// drain whichever remains
		while (!left.isEmpty())  list.add(left.removeFirst());
		while (!right.isEmpty()) list.add(right.removeFirst());
	}
	    /**
     * Merge method using a Comparator.
     */
	private static <T> void merge(
        IndexedUnsortedList<T> left,
        IndexedUnsortedList<T> right,
        IndexedUnsortedList<T> list,
        Comparator<? super T> c)
     {
         while (!left.isEmpty() && !right.isEmpty()) {
             if (c.compare(left.get(0), right.get(0)) <= 0) {
                 list.add(left.removeFirst());
             } else {
                 list.add(right.removeFirst());
             }
         }
         while (!left.isEmpty()) {
             list.add(left.removeFirst());
         }
         while (!right.isEmpty()) {
             list.add(right.removeFirst());
         }
     }
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list) {
        int n = list.size();
        if (n <= 1) return;

        IndexedUnsortedList<T> left = newList();
        IndexedUnsortedList<T> right = newList();
        int mid = n / 2;
        for (int i = 0; i < mid; i++) {
            left.add(list.removeFirst());
        }
        while (!list.isEmpty()) {
            right.add(list.removeFirst());
        }

        mergesort(left);
        mergesort(right);

        merge(left, right, list);
    }
		
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void mergesort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		int n = list.size();
		if (n <= 1) return;
	
		IndexedUnsortedList<T> left  = newList();
		IndexedUnsortedList<T> right = newList();
		int mid = n / 2;
		for (int i = 0; i < mid; i++)
			left.add(list.remove(0));
		while (list.size() > 0)
			right.add(list.remove(0));
	
		mergesort(left, c);
		mergesort(right, c);
	
		merge(left, right, list, c);
	}
}