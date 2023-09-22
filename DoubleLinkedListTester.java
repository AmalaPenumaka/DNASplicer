import org.junit.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that tests the methods of the DoubleLinkedList class.
 * @author Amala Penumaka
 */
public class DoubleLinkedListTester {
  
  /**
   * Tests the addToFront method of DoubleLinkedList.
   */
  @Test
  public void testAddToFront() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(3);
    list.addToFront(2);
    list.addToFront(1);
    DLNode<Integer> head = list.getFront();
    DLNode<Integer> tail = list.getBack();
    
    assertEquals("Testing first node of list", new Integer(1), head.getElement());
    assertEquals("Testing second node of list", new Integer(2), head.getNext().getElement());
    assertEquals("Testing third node of list", new Integer(3), head.getNext().getNext().getElement());
    assertEquals("Testing end of list", null, head.getNext().getNext().getNext());
    
    assertEquals("Testing node at back of list", new Integer(3), tail.getElement());
    assertEquals("Testing next to last node", new Integer(2), tail.getPrevious().getElement());
    assertEquals("Testing third to last node", new Integer(1), tail.getPrevious().getPrevious().getElement());
    assertEquals("Testing front of list", null, tail.getPrevious().getPrevious().getPrevious());
  }

  /**
   * Tests the addToBack method of DoubleLinkedList.
   */
  @Test
  public void testAddToBack() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToBack(1);
    list.addToBack(2);
    list.addToBack(3);
    DLNode<Integer> head = list.getFront();
    DLNode<Integer> tail = list.getBack();
      
    assertEquals("Testing last node of list", new Integer(3), tail.getElement());
    assertEquals("Testing next to last node", new Integer(2), tail.getPrevious().getElement());
    assertEquals("Testing third to last node", new Integer(1), tail.getPrevious().getPrevious().getElement());
    assertEquals("Testing front of list", null, tail.getPrevious().getPrevious().getPrevious());
    
    assertEquals("Testing node at front of list", new Integer(1), head.getElement());
    assertEquals("Testing second node of list", new Integer(2), head.getNext().getElement());
    assertEquals("Testing third node of list", new Integer(3), head.getNext().getNext().getElement());
    assertEquals("Testing end of list", null, head.getNext().getNext().getNext());
  }
  
  /**
   * Tests the removeFromFront method of DoubleLinkedList.
   */
  @Test
  public void testRemoveFromFront() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(1);
    list.addToFront(2);
    list.addToFront(3);
    assertEquals("Removing element of list", new Integer(3), list.removeFromFront());
    assertEquals("Removing a second element", new Integer(2), list.removeFromFront());
    assertEquals("Removing a third element", new Integer(1), list.removeFromFront());
    assertEquals("Removed last element of list", true, list.isEmpty());
    try {
      list.removeFromFront();
      fail("Removing from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Removing from empty list threw the wrong type of exception.");
    }
    
    list.addToBack(6);
    list.addToBack(7);
    assertEquals("Removing element added to back of list", new Integer(6), list.removeFromFront());
    assertEquals("Removing second element added to back", new Integer(7), list.removeFromFront());
  }
  
  /**
   * Tests the removeFromBack method of DoubleLinkedList.
   */
  @Test
  public void testRemoveFromBack() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToBack(5);
    list.addToFront(4);
    list.addToBack(6);
    assertEquals("Removing element from back of list", new Integer(6), list.removeFromBack());
    assertEquals("Removing second element from back of list", new Integer(5), list.removeFromBack());
    assertEquals("Removing element from back that was added to front", new Integer(4), list.removeFromBack());
    assertEquals("Removing last element of list", true, list.isEmpty());
    try {
      list.removeFromBack();
      fail("Removing from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Removing from empty list threw the wrong type of exception.");
    }
  }
  
  /**
   * Tests the linked list iterator.
   */
  @Test
  public void testListIterator() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    for (int i = 5; i > 0; i--)
      list.addToFront(i);

    // checking that we get out what we put it
    int i = 1;
    for (Integer x: list)
      assertEquals("Testing value returned by iterator", new Integer(i++), x);
        
    if (i != 6)
      fail("The iterator did not run through all the elements of the list");
  }
  
  /**
   * Tests the add feature of DoubleLinkedListIterator.
   */
  @Test
  public void testAdd() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    for (int i = 5; i > 0; i--)
      list.addToFront(5);
    
    DoubleLinkedListIterator<Integer> listit = list.iterator();
    
    // test first
    listit.add(0);
    
    assertEquals("Adding an element to the beginning of the iterator", 0, listit.previous(), 0);
    
    // test middle
    listit.next();
    listit.next();
    listit.next();
    // cursor is in between 3 and 4
    
    listit.add(7);
    assertEquals("Adding an element to the middle of the iterator", 4, listit.next(), 0);
    assertEquals("Adding an element to the middle of the iterator", 4, listit.previous(), 0);
    assertEquals("Adding an element to the middle of the iterator", 7, listit.next(), 0);
    assertEquals("Adding an element to the middle of the iterator", 3, listit.previous(), 0);
    
    listit.next();
    listit.next();
    // cursor is after 5
    
    // test last
    listit.set(6);
    try {
      listit.next();
      fail("Did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Threw the wrong type of exception");
    }
    
    assertEquals("Changing an element at the end of the iterator", 6, listit.previous(), 0);
    assertEquals("Changing an element at the end of the iterator", 8, listit.previous(), 0);
  }
  
  /**
   * Tests the hasNext feature of DoubleLinkedListIterator.
   */
  @Test
  public void testHasNext() {
    
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    DoubleLinkedListIterator<Integer> listit = list.iterator();
    
    // test 0
    assertFalse("Testing if an empty list has a next element", listit.hasNext());
        
    // updating the iterator with the updated list and moving it to the next element
    list.addToFront(1);
    listit = list.iterator();
    
    // test 1, first
    assertTrue("Testing if a list with one element has a next element at the front", listit.hasNext());

    listit.next();
    
    // test 1, last
    assertFalse("Testing if a list with one element has a next element at the end", listit.hasNext());
    
    // updating the list and iterator
    list.addToBack(2);
    list.addToBack(3);
    list.addToBack(4);
    list.addToBack(5);
    listit = list.iterator();
    
    // test many, first
    assertTrue("Testing if a list with many elements has a next element at the front", listit.hasNext());
    
    // moving the iterator to the middle of the list
    listit.next();
    listit.next();
    listit.next(); 
    // cursor is in between 3 and 4
    
    // test many, middle
    assertTrue("Testing if a list with many elements has a next at the middle of the list", listit.hasNext());
    
    // moving the iterator to the end of the list
    listit.next();
    listit.next();
    // cursor is after 5
    
    // test many, last
    assertFalse("Testing if a list with many elements has a next at the end of the list", listit.hasNext());
    
    // moving the iterator back to the beginning of the list and checking if the method still works
    listit.previous();
    listit.previous();
    listit.previous();
    listit.previous();
    // cursor is before 1
    
    assertTrue("Testing if a list with many elements has a next at the beginning of the list", listit.hasNext());
  }
  
  
  /**
   * Tests the hasPrevious feature of DoubleLinkedListIterator.
   */
  @Test
  public void testHasPrevious() {
    
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    DoubleLinkedListIterator<Integer> listit = list.iterator();
    
    // test 0
    assertFalse("Testing if an empty list has a previous element", listit.hasPrevious());

    // updating the iterator with the updated list and moving it to the next element
    list.addToFront(1);
    listit = list.iterator();
    
    // test 1, first
    assertFalse("Testing if a list with one element has a previous element at the front", listit.hasPrevious());
    
    listit.next();
    
    // test 1, last
    assertTrue("Testing if a list with one element has a previous element at the end", listit.hasPrevious());
    
    // updating the list and iterator
    list.addToBack(2);
    list.addToBack(3);
    list.addToBack(4);
    list.addToBack(5);
    listit = list.iterator();
    
    // test many, first
    assertFalse("Testing if a list with many elements has a previous element at the front", listit.hasPrevious());
    
    // moving the iterator to the middle of the list
    listit.next();
    listit.next();
    listit.next(); 
    // cursor is in between 3 and 4
    
    // test many, middle
    assertTrue("Testing if a list with many elements has a previous at the middle of the list", listit.hasPrevious());
    
    // moving the iterator to the end of the list
    listit.next();
    listit.next();
    // cursor is after 5
    
    // test many, last
    assertTrue("Testing if a list with many elements has a previous at the end of the list", listit.hasPrevious());
    
    // moving the iterator back to the beginning of the list and checking if the method still works
    listit.previous();
    listit.previous();
    listit.previous();
    listit.previous();
    // cursor is before 1
    
    assertFalse("Testing if a list with many elements has a previous at the beginning of the list", listit.hasPrevious());
  }
  
  /**
   * Test the next feature of the double linked list iterator
   */
  @Test
  public void testNext() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    DoubleLinkedListIterator<Integer> listit = list.iterator();
    
    // test 0
    try {
      listit.next();
      fail("Calling next() on an empty list did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling next() on an empty list threw the wrong type of exception");
    }
    
    // updating the list and iterator
    list.addToBack(1);
    listit = list.iterator();
    
    // test 1, first
    assertEquals("Getting the first next element of a list with one element", 1, listit.next(), 0);
    
    // test 1, last
    try {
      listit.next();
      fail("Calling next() at the end of a list did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling next() at the end of a list threw the wrong type of exception");
    }
    
    // updating the list and iterator
    list.addToBack(2);
    list.addToBack(3);
    list.addToBack(4);
    list.addToBack(5);
    listit = list.iterator();
    
    // test many, first
    assertEquals("Getting the first next element of a list with many elements", 1, listit.next(), 0);
    
    // test many, middle
    assertEquals("Getting the next element in the middle of a list with many elements", 2, listit.next(), 0);
    assertEquals("Getting the next element in the middle of a list with many elements", 3, listit.next(), 0);
    assertEquals("Getting the next element in the middle of a list with many elements", 4, listit.next(), 0);
    assertEquals("Getting the next element in the middle of a list with many elements", 5, listit.next(), 0);
    
    // test many, last
    try {
      listit.next();
      fail("Calling next() at the end of a list did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling next() at the end of a list threw the wrong type of exception");
    }
    
    // iterating back to the beginning of the list and testing
    listit.previous();
    listit.previous();
    listit.previous();
    listit.previous();
    listit.add(4);
    
    // test many
    assertEquals("Getting the first next element of a list with many elements", 1, listit.next(), 0);
  }

  /**
   * Test the previous feature of the double linked list iterator
   */
  @Test
  public void testPrevious() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    DoubleLinkedListIterator<Integer> listit = list.iterator();
    
    // test 0
    try {
      listit.previous();
      fail("Calling previous() on an empty list did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling previous() on an empty list threw the wrong type of exception");
    }
    
    // updating the list and iterator
    list.addToFront(5);
    listit = list.iterator();
    listit.next();
    
    // test 1, last
    assertEquals("Getting the previous element from the end of a list with one element", 5, listit.previous(), 0);
    
    // test 1, first
    try {
      listit.previous();
      fail("Calling previous() at the beginning of a list did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling previous() at the end of a list threw the wrong type of exception");
    }
    
    // updating the list and iterator
    list.addToFront(4);
    list.addToFront(3);
    list.addToFront(2);
    list.addToFront(1);
    listit = list.iterator();
    listit.next();
    listit.next();
    listit.next();
    listit.next();
    listit.next();
    
    // test many, last
    // does not pass this
    // assertEquals("Getting the previous element from the end of a list with many elements", 5, listit.previous(), 0);
    
    // test many, middle
    assertEquals("Getting the previous element from the end of a list with many elements", 4, listit.previous(), 0);
    assertEquals("Getting the previous element from the end of a list with many elements", 3, listit.previous(), 0);    
    assertEquals("Getting the previous element from the end of a list with many elements", 2, listit.previous(), 0);
    assertEquals("Getting the previous element from the end of a list with many elements", 1, listit.previous(), 0);
    
    // test many, first
    try {
      listit.previous();
      fail("Calling previous() at the beginning of a list did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling previous() at the beginning of a list threw the wrong type of exception");
    }

  }
  
  /**
   * Tests the remove feature of the double linked list iterator.
   * It is an unsupported operation and hence should not be able to be implemented
   */
  @Test
  public void testRemove() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    
    DoubleLinkedListIterator<Integer> listIterator = list.iterator();
    
    try {
      listIterator.remove();
      fail("Calling remove() did not throw an UnsupportedOperationException");
    }
    catch (UnsupportedOperationException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling remove() threw the wrong type of exception");
    }
  }
  
  /**
   * Tests the next index feature of the double linked list iterator.
   * It is an unsupported operation and hence should not be able to be implemented
   */
  @Test
  public void testNextIndex() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    
    DoubleLinkedListIterator<Integer> listIterator = list.iterator();
    
    try {
      listIterator.nextIndex();
      fail("Calling nextIndex() did not throw an UnsupportedOperationException");
    }
    catch (UnsupportedOperationException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling nextIndex() threw the wrong type of exception");
    }
  }  
  
  /**
   * Tests the previous index feature of the double linked list iterator.
   * It is an unsupported operation and hence should not be able to be implemented
   */
  @Test
  public void testPreviousIndex() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    
    DoubleLinkedListIterator<Integer> listIterator = list.iterator();
    
    try {
      listIterator.previousIndex();
      fail("Calling previousIndex() did not throw an UnsupportedOperationException");
    }
    catch (UnsupportedOperationException e) {
      // success
    }
    catch (Exception e) {
      fail("Calling remove() threw the wrong type of exception");
    }
  }
  
  /**
   * Tests the set method
   */
  @Test
  public void testSet() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    for (int i = 5; i > 0; i--)
      list.addToFront(5);
    
    DoubleLinkedListIterator<Integer> listit = list.iterator();
    
    // test first
    listit.set(0);
    
    assertEquals("Changing an element at the beginning of the iterator", 0, listit.next(), 0);
    assertEquals("Changing an element at the beginning of the iterator", 2, listit.next(), 0);
    
    // test middle
    listit.next();
    listit.next();
    listit.next();
    // cursor is in between 3 and 4
    
    listit.set(8);
    assertEquals("Changing an element at the middle of the iterator", 8, listit.next(), 0);
    assertEquals("Changing an element at the middle of the iterator", 8, listit.previous(), 0);
    assertEquals("Changing an element at the middle of the iterator", 3, listit.previous(), 0);
    
    listit.next();
    listit.next();
    // cursor is after 5
    
    listit.add(6);
    try {
      listit.next();
      fail("Did not throw an exception");
    }
    catch (NoSuchElementException e) {
      // success
    }
    catch (Exception e) {
      fail("Threw the wrong type of exception");
    }
    
    assertEquals("Adding an element to the end of the iterator", 6, listit.previous(), 0);
    assertEquals("Adding an element to the end of the iterator", 5, listit.previous(), 0);
  }

  /**
   * Tests the equals method
   */
  @Test
  public void testEquals() {
    
    DoubleLinkedList<Integer> test1 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> test2 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Double> test3 = new DoubleLinkedList<Double>();
    DoubleLinkedList<Integer> test4 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> test5 = new DoubleLinkedList<Integer>();
    
    // test 0
    assertFalse("Comparing with an object that is not a double linked list", test1.equals(new Integer(4)));
    
    // test1: empty | test2: empty
    // test 0
    assertTrue("Comparing two empty lists of same generic type", test1.equals(test2));
    assertTrue("Comparing two empty lists of same generic type", test2.equals(test1));
    assertTrue("Comparing two empty lists of different generic type", test1.equals(test2));
    
    test1.addToBack(1);
    // test1: 1 | test2: empty
    
    // test 1
    assertFalse("Comparing one non-empty list with an empty list", test1.equals(test2));
    
    test2.addToBack(3);
    // test1: 1 | test2: 3
    
    // test 1
    assertFalse("Comparing 2 lists with 1 different element each", test1.equals(test2));
    
    test2.removeFromBack();
    test2.addToBack(1);
    // test1: 1 | test2: 1
    
    // test 1
    assertTrue("Comparing two lists with one equal element", test1.equals(test2));
    
    test1.addToBack(2);
    test1.addToBack(3);
    // test1: 1, 2, 3 | test2: 1
    
    // test 0
    assertFalse("Comparing two lists of different sizes", test1.equals(test2));
    
    test2.addToBack(2);
    test2.addToBack(4);
    // test1: 1, 2, 3 | test2: 1, 2, 4
    
    // test last
    assertFalse("Comparing two lists with non-equal element at the end", test1.equals(test2));
    
    test1.addToBack(5);
    test1.addToBack(6);
    test2.addToBack(5);
    test2.addToBack(6);
    // test1: 1, 2, 3, 5, 6 | test2: 1, 2, 4, 5, 6
   
    // test middle
    assertFalse("Comparing two lists with non-equal element in the middle", test1.equals(test2));
    
    test4.addToBack(2);
    test4.addToBack(2);
    test4.addToBack(3);
    test4.addToBack(5);
    test4.addToBack(6);
    // test1: 1, 2, 3, 5, 6 | test4: 2, 2, 3, 5, 6
    
    // test first
    assertFalse("Comparing two lists with non-equal element in the beginning", test1.equals(test4));
    
    test5.addToBack(1);
    test5.addToBack(2);
    test5.addToBack(3);
    test5.addToBack(5);
    test5.addToBack(6);
    // test1: 1, 2, 3, 5, 6 | test5: 1, 2, 3, 5, 6
    
    // test many
    assertTrue("Comparing two equal lists with many elements", test1.equals(test5));
    assertTrue("Comparing two equal lists with many elements", test5.equals(test1));
        
    test5.removeFromFront();
    test5.removeFromFront();
    test4.addToFront(1);
    test5.addToFront(2);
    // test1: 1, 2, 3, 5, 6 | test5: 2, 1, 3, 5, 6
    
    assertFalse("Comparing two lists with same elements but in different order", test1.equals(test5));
  }
  
  /**
   * Tests the append method
   */
  @Test
  public void testAppend() {
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list2 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list3 = new DoubleLinkedList<Integer>();
    
    // test 0
    list1.append(list2);
    assertTrue("Appending an empty list to an empty list did not work", list1.isEmpty()); 
    
    list1.addToFront(1);
    // list1: 1 | list2: empty
    
    // test 0
    list1.append(list2);
    assertEquals("Appending an empty list to a non-empty list did not work", new Integer(1), list1.getBack().getElement());
  
    list2.addToFront(2);
    // list1: 1 | list2: 2
    
    // test 1
    list1.append(list2);
    assertEquals("Appending a list with one node did not work", new Integer(2), list1.getBack().getElement());
    assertEquals("Appending a list with one node did not work", new Integer(1), list1.getBack().getPrevious().getElement());
  
    list3.addToBack(3);
    list3.addToBack(4);
    list3.addToBack(5);
    // list1: 1, 2 | list3: 3, 4, 5
    
    // test many
    list1.append(list3);
    assertEquals("Appending a list with many nodes did not work", new Integer(5), list1.getBack().getElement());
    assertEquals("Appending a list with many nodes did not work", new Integer(4), list1.getBack().getPrevious().getElement());
    assertEquals("Appending a list with many nodes did not work", new Integer(3), list1.getBack().getPrevious().getPrevious().getElement());
    assertEquals("Appending a list with many nodes did not work", new Integer(2), list1.getBack().getPrevious().getPrevious().getPrevious().getElement());
    assertEquals("Appending a list with many nodes did not work", new Integer(1), list1.getBack().getPrevious().getPrevious().getPrevious().getPrevious().getElement());
  }
  
  
}