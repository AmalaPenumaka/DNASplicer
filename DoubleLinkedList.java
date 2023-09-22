import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * A doubly linked linked list.
 */
public class DoubleLinkedList<T> implements Iterable<T> {
  /** a reference to the first node of the double linked list */
  private DLNode<T> front;
  
  /** a reference to the last node of a double linked list */
  private DLNode<T> back;
  
  /** Create an empty double linked list. */
  public DoubleLinkedList() {
    front = back = null;
  }
  
  /** 
   * Returns true if the list is empty.
   * @return  true if the list has no nodes
   */
  public boolean isEmpty() {
    return (getFront() == null);
  }
  
  /**
   * Returns the reference to the first node of the linked list.
   * @return the first node of the linked list
   */
  protected DLNode<T> getFront() {
    return front;
  }
  
  /**
   * Sets the first node of the linked list.
   * @param node  the node that will be the head of the linked list.
   */
  protected void setFront(DLNode<T> node) {
    front = node;
  }
  
  /**
   * Returns the reference to the last node of the linked list.
   * @return the last node of the linked list
   */
  protected DLNode<T> getBack() {
    return back;
  }
  
  /**
   * Sets the last node of the linked list.
   * @param node the node that will be the last node of the linked list
   */
  protected void setBack(DLNode<T> node) {
    back = node;
  }
  
  /**
   * Calculates the length of the linked list.
   * @return the length of the linked list
   */
  public int length() {
    
    // Stores the length of the list, initialized to 0
    int length = 0;
    
    // Temporary node pointer to iterate through the loop to calculate length
    DLNode<T> sizeCounter = this.getFront();
    
    // Iterate through the loop and increment length for every node
    while (sizeCounter != null) {
      length++;
      sizeCounter = sizeCounter.getNext();
    }
    
    return length;
  }
  
  /**
   * Add an element to the head of the linked list.
   * @param element  the element to add to the front of the linked list
   */
  public void addToFront(T element) {
    DLNode<T> list = new DLNode<T>(element, null, null);
    
    if (front == null) {
      setFront(list);
      setBack(list);
    }
    else {
      list.setNext(getFront());
      getFront().setPrevious(list);
      this.setFront(list);
    }
  }
  
  /**
   * Add an element to the tail of the linked list.
   * @param element  the element to add to the tail of the linked list
   */
  public void addToBack(T element) {
    DLNode<T> list = new DLNode<T>(element, null, null);
    
    if (back == null) {
      setFront(list);
      setBack(list);
    }
    else {
      list.setPrevious(getBack());
      getBack().setNext(list);
      this.setBack(list);
    }
  }

  /**
   * Remove and return the element at the front of the linked list.
   * @return the element that was at the front of the linked list
   * @throws NoSuchElementException if attempting to remove from an empty list
   */
  public T removeFromFront() throws NoSuchElementException {
    
    // temporary node that stores the first element that is to be removed
    DLNode<T> temp = front;
    
    if (this.isEmpty() == true) { // list is empty
      throw new NoSuchElementException();
    }
    
    else if (front.getNext() != null || back.getPrevious() != null) { // list has multiple elements
        front.getNext().setPrevious(null);
        setFront(front.getNext());
    }
    
    else { // list has only one element
        setFront(null);
        setBack(null);
      }

    return temp.getElement();
  }
  
  /**
   * Remove and return the element at the back of the linked list.
   * @return the element that was at the back of the linked list
   * @throws NoSuchElementException if attempting to remove from an empty list
   */
  public T removeFromBack() {
    
    // temporary node that stores the last element that is to be removed
    DLNode<T> temp = back;
    
    if (this.isEmpty() == true) { // list is empty 
      throw new NoSuchElementException();
    }
    
    else if (back.getPrevious() != null || front.getNext() != null) { // list has multiple elements
        back.getPrevious().setNext(null);
        setBack(back.getPrevious());
    }
    
    else { // list has one element
        setFront(null);
        setBack(null);
    }
    return temp.getElement();
  }
     
  /**
   * Returns an iterator for the linked list that starts at the head of the list and runs to the tail.
   * @return  the iterator that runs through the list from the head to the tail
   */
  @Override
  public DoubleLinkedListIterator<T> iterator() {
    return new DoubleLinkedListIterator<T>(this);
  }
  
  /**
   * Checks if two linked lists are equal.
   * @return true if the input list has the same elements in the same order as this list
   * @param o the object (list) to be compared with this list
   */
  @Override
  public boolean equals(Object o) {
    
    // two temporary nodepointers to iterate through each of the lists
    DLNode<?> nodeptr1;
    DLNode<?> nodeptr2;

    // checks if the input is a double linked list and can hence be compared with this list
    if(!(o instanceof DoubleLinkedList<?>))
      return false;
    else {
      DoubleLinkedList<?> list = (DoubleLinkedList<?>)o;
      nodeptr1 = this.getFront();
      nodeptr2 = list.getFront();
      
      // checks if lengths of lists
      if (this.length() != list.length())
        return false;
    }
    
    // iterates through the two lists at the same time 
    // checks if each of the corresponding elements from the 2 lists are equal
    while(nodeptr1 != null || nodeptr2 != null) {
      if (!nodeptr1.getElement().equals(nodeptr2.getElement())) {
        return false;
      }
      nodeptr1 = nodeptr1.getNext();
      nodeptr2 = nodeptr2.getNext();
    }
    return true;
  }
  
  /**
   * Appends the nodes of a list to the end of this list.
   * @param list the list whose nodes need to be appended to this list
   */
  public void append(DoubleLinkedList<T> list) {
    
    // iterates through the list
    // precondition: list is iterable
    for (T element : list) {
      // adds each of the elements of the input list to the end of this list
      this.addToBack(element);
    }
  }
}
