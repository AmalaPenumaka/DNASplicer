import org.junit.*;
import static org.junit.Assert.*;
import java.util.Iterator;

/**
 * A class that tests the methods of the DNA class.
 * @author Amala Penumaka
 */
public class DNATester {
  
  /**
   * Tests the toString method of DNA
   */
  @Test
  public void testToString() {
    
    DNA test = new DNA();
    
    // test 0
    assertEquals("Testing a DNA with 0 bases", "", test.toString());
    
    // test 1
    test.addToBack(DNA.Base.G);
    assertEquals("Testing a DNA with 1 base", "G", test.toString());
    
    // test many
    test.addToBack(DNA.Base.C);
    test.addToBack(DNA.Base.T);
    assertEquals("Testing a DNA with many bases", "GCT", test.toString());
    
    DNA test2 = new DNA();
    test2.addToBack(DNA.Base.A);
    test2.addToBack(DNA.Base.C);
    test2.addToBack(DNA.Base.G);
    test2.addToBack(DNA.Base.G);
    test2.addToBack(DNA.Base.C);
    test2.addToBack(DNA.Base.G);
    test2.addToBack(DNA.Base.T);
    assertEquals("Testing a DNA with many bases", "ACGGCGT", test2.toString());
  }
  
  /**
   * Tests the splice method of DNA
   */
  @Test
  public void testSplice() {
    
    DNA dna1 = new DNA();
    DNA dna2 = new DNA();
    
    // test 0
    dna1.splice(dna2, 0);
    assertNull("Splicing 0 bases of an empty DNA onto an empty DNA", dna1.getFront());
    
    // test 0
    dna2.addToBack(DNA.Base.T);
    dna1.splice(dna2, 0);
    assertEquals("Splicing 0 numbases of a non-empty DNA onto an empty DNA", DNA.Base.T, dna1.getFront().getElement());
    
    // resetting DNAs
    dna1.removeFromFront();
    dna2.removeFromFront();
    
    // test 0 (dna1 remains unchanged)
    dna1.addToBack(DNA.Base.A);
    dna1.addToBack(DNA.Base.G);
    dna2.addToBack(DNA.Base.G);
    dna2.addToBack(DNA.Base.C);
    dna1.splice(dna2, 3);
    assertEquals("Splicing greater number of bases than present in a DNA", DNA.Base.A, dna1.getFront().getElement());
    assertEquals("Splicing greater number of bases than present in a DNA", DNA.Base.G, dna1.getFront().getNext().getElement());  
    
    dna1.addToBack(DNA.Base.T);
    dna1.addToBack(DNA.Base.A);
    dna2.addToBack(DNA.Base.T);
    // dna1: AGTA
    // dna2: GCT
    
    // test 1
    dna1.splice(dna2, 1);
    assertEquals("Splicing with one numbase", DNA.Base.A, dna1.getFront().getElement());
    assertEquals("Splicing with one numbase", DNA.Base.G, dna1.getFront().getNext().getElement());
    assertEquals("Splicing with one numbase", DNA.Base.T, dna1.getFront().getNext().getNext().getElement());
    assertEquals("Splicing with one numbase", DNA.Base.A, dna1.getBack().getPrevious().getPrevious().getElement());
    assertEquals("Splicing with one numbase", DNA.Base.C, dna1.getBack().getPrevious().getElement());
    assertEquals("Splicing with one numbase", DNA.Base.T, dna1.getBack().getElement());
    
    // dna1: AGTACT
    // dna2: CT
    
    // test many
    dna2.splice(dna1, 2);
    assertEquals("Splicing with two numbases", DNA.Base.C, dna2.getFront().getElement());
    assertEquals("Splicing with two numbases", DNA.Base.T, dna2.getFront().getNext().getElement());
    assertEquals("Splicing with two numbases", DNA.Base.T, dna2.getFront().getNext().getNext().getElement());
    assertEquals("Splicing with two numbases", DNA.Base.A, dna2.getFront().getNext().getNext().getNext().getElement());
    assertEquals("Splicing with two numbases", DNA.Base.C, dna2.getBack().getPrevious().getElement());
    assertEquals("Splicing with two numbases", DNA.Base.T, dna2.getBack().getElement());
    
    // dna1: TACT
    // dna2: CTTACT
    
    // test many
    dna1.splice(dna2, 3);
    assertEquals("Splicing with many numbases", DNA.Base.T, dna1.getFront().getElement());
    assertEquals("Splicing with many numbases", DNA.Base.A, dna1.getFront().getNext().getElement());
    assertEquals("Splicing with many numbases", DNA.Base.C, dna1.getFront().getNext().getNext().getElement());
    assertEquals("Splicing with many numbases", DNA.Base.T, dna1.getBack().getPrevious().getPrevious().getPrevious().getElement());
    assertEquals("Splicing with many numbases", DNA.Base.A, dna1.getBack().getPrevious().getPrevious().getElement());
    assertEquals("Splicing with many numbases", DNA.Base.C, dna1.getBack().getPrevious().getElement());
    assertEquals("Splicing with many numbases", DNA.Base.T, dna1.getBack().getElement());
  }
  
  /**
   * Tests the compareTo method of the DNA class
   */
  @Test
  public void testCompareTo() {
    
    DNA test1 = DNA.string2DNA("AGCT");
    DNA test2 = DNA.string2DNA("CGCT");
    DNA test3 = DNA.string2DNA("GCTAT");
    DNA test4 = DNA.string2DNA("GCTAT");
    DNA test5 = DNA.string2DNA("TGCTAGCATCAGCT");
      
    // test 0
    assertEquals("Comparing 2 DNAs of same length and same elements", 0, test3.compareTo(test4));
    
    // test 0
    assertEquals("Comparing 2 DNAs of same length but different elements (ordering alphabetically)", -2, test1.compareTo(test2));
    assertEquals("Comparing 2 DNAs of same length but different elements (ordering alphabetically)", 2, test2.compareTo(test1));
    
    // test 1
    assertEquals("Comparing 2 DNAs with a difference in length of 1", -1, test1.compareTo(test3));
    assertEquals("Comparing 2 DNAs with a difference in length of 1", 1, test3.compareTo(test1));
    
    // test many
    assertEquals("Comparing 2 DNAs with a difference in length of more than 1", -9, test4.compareTo(test5));
    assertEquals("Comparing 2 DNAs with a difference in length of more than 1", 9, test5.compareTo(test4));
  }
  
  /**
   * Tests the string2DNA method of DNA
   */
  @Test
  public void testString2DNA() {
    
    // Testing an empty string - test 0
    try {
      DNA test0 = DNA.string2DNA("");
      fail("Trying to convert an empty string to a DNA did not throw the right exception.");
    }
    catch (IllegalArgumentException e) {
      // success
    }
    catch (Exception e) {
      fail("Converting an empty string to a DNA threw the wrong type of exception.");
    }
    
    // Testing a string with invalid characters in the beginning (characters other than A, G, T, C) - test first
    try {
      DNA test1 = DNA.string2DNA("ZGCT");
      fail("Trying to convert a string with invalid characters to a DNA did not throw the right exception.");
    }
    catch (IllegalArgumentException e) {
      // success
    }
    catch (Exception e) {
      fail("Converting a string with invalid characters to a DNA threw the wrong type of exception.");
    }
    
    // Testing a string with invalid characters in the middle - test middle
    try {
      DNA test2 = DNA.string2DNA("AGZCT");
      fail("Trying to convert a string with invalid characters to a DNA did not throw the right exception.");
    }
    catch (IllegalArgumentException e) {
      //success
    }
    catch (Exception e) {
      fail("Converting a string with invalid characters to a DNA threw the wrong type of exception.");
    }
    
    // Testing a string with invalid characters at the end - test last
    try {
      DNA test3 = DNA.string2DNA("AGTGO");
      fail("Trying to convert a string with invalid characters to a DNA did not throw the right exception.");
    }
    catch (IllegalArgumentException e) {
      //success
    }
    catch (Exception e) {
      fail("Converting a string with invalid characters to a DNA threw the wrong type of exception.");
    }
    
    // test 1
    DNA test4 = DNA.string2DNA("A");
    assertEquals("Testing a string with one valid character", DNA.Base.A, test4.getFront().getElement());
    assertNull("Testing a string with one valid character", test4.getFront().getNext());
    
    // test many
    DNA test5 = DNA.string2DNA("CTGACA");
    assertEquals("Testing a string with many characters", DNA.Base.C, test5.getFront().getElement());
    assertEquals("Testing a string with many characters", DNA.Base.T, test5.getFront().getNext().getElement());
    assertEquals("Testing a string with many characters", DNA.Base.G, test5.getFront().getNext().getNext().getElement());
    assertEquals("Testing a string with many characters", DNA.Base.A, test5.getBack().getPrevious().getPrevious().getElement());
    assertEquals("Testing a string with many characters", DNA.Base.C, test5.getBack().getPrevious().getElement());
    assertEquals("Testing a string with many characters", DNA.Base.A, test5.getBack().getElement());
  }
  
  
  /**
   * Tests the overlaps method of DNA
   */
  @Test
  public void testOverlaps() {

    DNA test1 = DNA.string2DNA("AGCT");
    DNA test2 = DNA.string2DNA("CTATA");
    DNA test3 = DNA.string2DNA("AGCT");
    
    // test 0
    assertTrue("Testing if 0 bases overlap for two DNAs", DNA.overlaps(test1, test2, 0));
    
    // test 1
    assertTrue("Testing if 1 base overlaps for two DNAs with overlap of 1", DNA.overlaps(test2, test1, 1));
    assertFalse("Testing if 1 base overlaps for two DNAs without overlap of 1", DNA.overlaps(test1, test2, 1));
    
    // test many
    assertTrue("Testing if 2 bases overlap for two DNAs with overlap of 2", DNA.overlaps(test1, test2, 2));
    assertFalse("Testing if 3 bases overlap for two DNAs with overlap of only 2", DNA.overlaps(test1, test2, 3));
    assertTrue("Testing 2 DNAs that completely overlap (are equal)", DNA.overlaps(test1, test3, 4));
    assertTrue("Testing 2 DNAs that completely overlap (are equal)", DNA.overlaps(test3, test1, 4));
    
    // testing DNAs of different lengths
    assertFalse("Testing overlap with a number greater than the length of one DNA", DNA.overlaps(test1, test2, 5));
    assertFalse("Testing overlap with a number greater than the length of both DNAs", DNA.overlaps(test1, test2, 8));
  }
  
}