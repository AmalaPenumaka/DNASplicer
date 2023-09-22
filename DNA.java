/**
 * A class that represents a DNA sequence as a DoubleLinkedList
 * @author Amala Penumaka
 */
public class DNA extends DoubleLinkedList<DNA.Base> implements Comparable<DNA> {

  /*
   * Possible bases that a DNA could consist of
   */
  enum Base {
    A, C, G, T;
  }
  
  /**
   * Constructor that initializes the new DNA to be empty.
   * Calls the super constructor.
   */
  public DNA() {
    super();
  }

  /**
   * Returns a String representation of a DNA sequence.
   * @return the String representation of the given DNA.
   */
  @Override
  public String toString() {
    
    // String Builder to represent the DNA sequence as a String
    StringBuilder b = new StringBuilder();
    
    // iterates through the DNA and appends each base to the String Builder
    for (DNA.Base base : this) {
      switch (base) {
        case A:
          b.append("A");
          break;
        case G:
          b.append("G");
          break;
        case C:
          b.append("C");
          break;
        case T:
          b.append("T");
          break;
      }
    }
    
    // returns the String Builder as a string 
    return b.toString();
  }
  
  /**
   * Removes the specified number of bases from the start of the given DNA and appends the remaining bases to the end of this DNA.
   * @param dna      the DNA's whose bases need to be spliced.
   * @param numbases the number of bases that need to be removed from the given DNA.
   */
  public void splice(DNA dna, int numbases) {
    
    // checks if the length of the input is greater than numbases
    if (dna.length() >= numbases) 
      {
      // iterates through the loop and removes the first numbases elements from the beginning
      for (int i = 0; i < numbases; i++)
        dna.removeFromFront();
      
      // appends the rest of the input dna onto this dna
      this.append(dna);
    }
  }
  
  /**
   * Compares two DNAs by length or if the same length, by alphabetical order.
   * @param dna the DNA this DNA is to be compared with.
   * @return a positive or negative integer if the input DNA is longer or shorter than this DNA,
   * or if the same length, 0, a positive, or negative integer if the input DNA is equal to, comes after, or comes before this DNA alphabetically.
   */
  @Override
  public int compareTo(DNA dna) {
    
    // stores the 2 DNAs to be compared to as Strings so that they can be compared alphabetically if required
    String thisDNA = this.toString();
    String inputDNA = dna.toString();
    
    // if the DNAs are the same length, they are ordered alphabetically
    if (this.length() == dna.length())
      return thisDNA.compareTo(inputDNA);

    // if different lengths, the DNAs are compared by length
    return this.length() - dna.length();
  }
  
  /**
   * Returns the input String as a DNA sequence.
   * @param  s the String to be converted to a DNA sequence.
   * @return the DNA sequence represented by the String.
   * @throws IllegalArgumentException  if the input String is empty or contains characters other than A, C, G, T.
   */
  public static DNA string2DNA(String s) {
  
    // new DNA sequence that the method will convert the String to
    DNA dnalist = new DNA();
    
    // checks if the input String is empty. If it is, throws an exception
    if (s.length() == 0)
      throw new IllegalArgumentException();

    // loops through the input string and adds the corresponding Base to the DNA for each character
    // if the character is not 'A', 'G', 'C', or 'T', throws an exception
    // precondition: the string is not empty
    for (int i = 0; i < s.length(); i++) {
      switch (s.charAt(i)) {
        case 'A':
          dnalist.addToBack(DNA.Base.A);
          break;
        case 'G':
          dnalist.addToBack(DNA.Base.G);
          break;
        case 'C':
          dnalist.addToBack(DNA.Base.C);
          break;
        case 'T':
          dnalist.addToBack(DNA.Base.T);
          break;
        default:
          throw new IllegalArgumentException();
      }   
    }

    return dnalist;
  }
  
  /**
   * Checks if the last specified number of bases of a given DNA exactly match the first same number of bases of a second DNA.
   * @param dna1 the first DNA sequence.
   * @param dna2 the second DNA sequence.
   * @param n    the number of bases being checked for any overlap.
   * @return true if the last n bases of the first given DNA exactly match the first n bases of the second DNA.
   */
  public static boolean overlaps(DNA dna1, DNA dna2, int n) {
    
    // checks if the DNAs are longer than the overlap number to be checked. if not, returns false since they cannot be compared
    if (dna1.length() < n || dna2.length() < n)
      return false;
    
    // temporary node pointer to iterate through dna1, initialized to the last base of dna1
    DLNode<DNA.Base> nodeptr = dna1.getBack();
    
    // temporary DNA that stores the last n bases of dna1
    DNA temp1 = new DNA();
    
    // temporary DNA that stores the first n bases of dna2
    DNA temp2 = new DNA();
        
    // iteratres through dna1 and adds each of the last n bases to a temporary DNA
    for (int i = 0; i < n; i++) {
      temp1.addToFront(nodeptr.getElement());
        nodeptr = nodeptr.getPrevious();
    }
    
    // resetting nodeptr to point to iterate through dna2, initialized to the first base of dna2
    nodeptr = dna2.getFront();

    // iteratres through dna2 and adds each of the first n bases to the second temporary DNA
    for (int i = 0; i < n; i++) {
      temp2.addToBack(nodeptr.getElement());
        nodeptr = nodeptr.getNext();
    }
    
    // compares the two temporary DNAs to check if they are equal
    return temp1.equals(temp2);
  }
  
  
  /**
   * Performs the DNA sequencing.
   * @param args array of command-line arguments: the first and second DNAs, respectively.
   */
  public static void main(String[] args) {
    // stores the command-line arguments as corresponding DNAs
    DNA dna1 = new DNA();
    DNA dna2 = new DNA();
    
    // checking for correct input of arguments
    if (args.length != 2) {
      System.out.println("Usage: java DNA dna1 dna2\nPlease enter the right number of arguments.");
    }
    
    else {
      try {
        dna1 = DNA.string2DNA(args[0]);
        dna2 = DNA.string2DNA(args[1]);
      }
      catch (IllegalArgumentException e) {
        System.out.print("Usage: java DNA dna1 dna2\nPlease enter 2 valid DNAs.");
      }
      
      // the final DNA that will be returned
      DNA finalDNA = new DNA();
      
      // indicates which order to splice the DNAs in, each one initialized to false
      boolean splice2on1 = false;
      boolean splice1on2 = false;
      
      // stores the shorter DNA out of dna1 and dna2
      DNA shorter = null; 
      
      // stores the value of the greatest overlap between the two DNAs, initialized to 0
      int overlapNum = 0;
      
      // determins the shorter DNA
      if (dna1.compareTo(dna2) < 0)
        shorter = dna1;
      else
        shorter = dna2;
      
      // finds the largest overlap of dna1 and dna2
      for (int i = 0; i <= shorter.length(); i++) {
        
        // checks if an overlap exists of dna2 on dna 1 and updates overlapNum if it does
        if (overlaps(dna1, dna2, i)) {
          overlapNum = i;
          splice2on1 = true;
        }
        
        // updates overlapNum if there is a larger overlap of dna1 on dna2 than an overlap of dna1 on dna2
        if (overlaps(dna2, dna1, i) && i > overlapNum) {
          overlapNum = i;
          splice1on2 = true;
          splice2on1 = false;
        }
      }
      
      // splicing the DNAs accordingly
      if (splice2on1) {
        dna1.splice(dna2, overlapNum);
        finalDNA = dna1;
      }
      else {
        dna2.splice(dna1, overlapNum);
        finalDNA = dna2;
      }
      
      // printing the final result
      System.out.println(finalDNA.toString());
    }
  }
 
}