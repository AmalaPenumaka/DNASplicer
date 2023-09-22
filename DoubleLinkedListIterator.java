import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * An iterator for a DoubleLinkedList.
 * @author Amala Penumaka
 */
public class DoubleLinkedListIterator<T> implements ListIterator<T> {

  // temporary node pointer to iterate through the list
  private DLNode<T> nodeptr;
  
  // stores the list for which the iterator is to be created for
  private DoubleLinkedList<T> list = null;
  
  // stores the node at the endpoint to be saved while iterating back and forth through lists
  private DLNode<T> endpoint;
  
  // stores whether the end of a list was reached
  private boolean endReached = false;

  /*
   * Constructor for this class that creates an iterator for the input list
   * @param list  the list for which an iterator is to be created
   */
  public DoubleLinkedListIterator(DoubleLinkedList<T> inputlist) {
    list = inputlist;
    nodeptr = list.getFront();
  }
  
  /**
   * Inserts the specified element into the list
   * @param element the element to be inserted into the list
   */
  @Override
  public void add(T element) {
    
    // node that contains the element that is to be added into the list
    DLNode<T> insert = new DLNode<T>(element, null, null);
    DoubleLinkedListIterator<T> listit = list.iterator();
    
    if (!listit.hasPrevious())
      list.addToFront(element);
    if (!listit.hasNext())
      list.addToBack(element);
    else {
      insert.setPrevious(nodeptr.getPrevious());
      nodeptr.getPrevious().setNext(insert);
      
      insert.setNext(nodeptr);
      nodeptr.setPrevious(insert);
    }
  } 
  
  /**
   * Checks if the list has a next element while traversing in the forward direction
   * @return true if the list has a next element
   */
  @Override
  public boolean hasNext() {
    
    return !endReached && !list.isEmpty();
  }
  
  /**
   * Checks if the list has a previous element while traversing in the backwards direction
   * @return true if the list has a previous element
   */
  @Override
  public boolean hasPrevious() {
    
    // if the nodeptr is at the end of a list with only 1 element, the list will have a previous element
    if (list.length() == 1)
      return endReached;
    else
      return !list.isEmpty() && nodeptr != null && nodeptr.getPrevious() != null;

  }
  
  /** 
   * Returns the next element in the list and increments forwards
   * @return the next element of the iteration
   * @throws NoSuchElementException if the list does not have a next element
   */
  @Override
  public T next() {
    if (!hasNext())
      throw new NoSuchElementException();
    
    // updates the endpoint if the last element of the list is reached
    if (nodeptr.equals(list.getBack())) {
      endpoint = nodeptr;
    }
    else;
    
    // saving the current element to be returned in a temp variable
    T save = nodeptr.getElement(); 
    nodeptr = nodeptr.getNext();
    
    // resets the nodepointer to point to the last element if the end if reached
    // so that the nodepointer can still be accessed (not null) and we can iterate backwards
    if (nodeptr == null) {
      nodeptr = endpoint;
      endReached = true;
    }
    return save;
  }
  
  /**
   * Returns the previous element in the list and increments backwards
   * @return the previous element of the iteration
   * @throws NoSuchElementException if the list does not have a previous element
   */
  @Override
  public T previous() {
    if (!hasPrevious())
      throw new NoSuchElementException();
    
    // sets the endReached boolean to false if moving backwards
    endReached = false;
    
    if (list.length() == 1) {
      nodeptr = list.getFront();
      return nodeptr.getElement();
    }
    
    nodeptr = nodeptr.getPrevious();
    // saving in temp variable
    T save = nodeptr.getElement();

    return save;
  }
  
  /**
   * Method not implemented 
   * @throws UnsupportedOperationException since operation is not supported
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Method not implemented 
   * @throws UnsupportedOperationException since operation is not supported 
   */
  public int nextIndex() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Method not implemented 
   * @throws UnsupportedOperationException since operation is not supported
   */
  public int previousIndex() {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Replaces the last element returned by next() or previous() with the specified element
   * @param element the element to insert
   */
  @Override
  public void set(T element) {
  
  }
  
}