import tester.Tester;

class PairOfLists {
  ILoString first;
  ILoString second;
  PairOfLists(ILoString first, ILoString second) {
    this.first = first;
    this.second = second;
  }
  PairOfLists addToFirst(String first) {
    return new PairOfLists(new ConsLoString(first, this.first), this.second);
  }
  PairOfLists addToSecond(String second) {
    return new PairOfLists(this.first, new ConsLoString(second, this.second));
  }
}
// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // produces a new list, sorted in alphabetical order 
  // treating all Strings as if they were given in all lowercase.
  ILoString sort();

  // produce a new list, insert the given String into this ILoString 
  // already sorted alphabetically 
  ILoString insert(String s);

  // is this list sorted in alphabetic order, in a case-insensitive way?
  boolean isSorted();

  // is the given String alphabetically before all element in this sorted list?
  boolean isSortedHelper(String s);

  // produce a list where the odd number of elements are from this list 
  // the even number of elements are from the given list 
  // any "leftover" elements should just be left at the end.
  ILoString interleave(ILoString other);

  // produces a sorted list that merge this sorted list with the given sorted list 
  ILoString merge(ILoString other);

  // produce this list of strings, in a reversed order
  ILoString reverse();

  // produce this list of strings in a reversed order, 
  //with the given string at the end of this list  
  ILoString reverseHelper(ILoString other);
  
  // determines if this list contains pairs of identical strings
  boolean isDoubledList();
  
  // determines if the first of this list matches the given string
  // and the rest of this list contains pairs of identical strings
  boolean isDoubledListHelper(String s);
  
  // determines whether this list contains 
  // the same words reading the list in either order
  boolean isPalindromeList();
  PairOfLists helper();
  PairOfLists helper2();
  PairOfLists unize();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString(){}

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }  

  // return itself 
  public ILoString sort() {
    return this;
  }

  //produce a new list, insert the given String into this ILoString 
  // already sorted alphabetically 
  public ILoString insert(String s) {
    return new ConsLoString(s, this);
  }

  //is this list sorted in alphabetic order, in a case-insensitive way?
  public boolean isSorted() {
    return true;
  }

  //is the given String alphabetically before all element in this sorted list?
  public boolean isSortedHelper(String s) {
    return true;
  }

  //produce a list where the odd number of elements are from this list 
  // the even number of elements are from the given list 
  // any "leftover" elements should just be left at the end.
  public ILoString interleave(ILoString other) {
    return other;
  }

  //produces a sorted list that merge this sorted list with the given sorted list 
  public ILoString merge(ILoString other) {
    return other;
  }

  // return itself 
  public ILoString reverse() {
    return this;
  }

  // produce this list of strings in a reversed order, 
  // with the given string at the end of this list  
  public ILoString reverseHelper(ILoString other) {
    return other;
  }

  // determines if this list contains pairs of identical strings
  public boolean isDoubledList() {
    return true;
  }

  // determines if the first of this list matches the given string 
  // and the rest of this list contains pairs of identical strings
  public boolean isDoubledListHelper(String s) {
    return false;
  }

  // determines whether this list contains 
  // the same words reading the list in either order
  public boolean isPalindromeList() {
    return true;
  }
  
  public PairOfLists helper() {
    return new PairOfLists(this, this);
  }
  public PairOfLists helper2() {
    return new PairOfLists(this, this);
  }
  public PairOfLists unize() {
    return new PairOfLists(new MtLoString(), new MtLoString());
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  // constructor 
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;  
  }

  //combine all Strings in this list into one
  public String combine() {
    /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString

     METHODS
     ... this.combine() ...     -- String
     ... this.sort() ...        -- ILoString 
     ... this.insert(String s) ...      -- ILoString
     ... this.isSorted() ...    -- boolean 
     ... this.isSortedHelper(String)... -- boolean 
     ... this.interleave(ILoString) ...  -- ILoString 
     ... this.merge(ILoString) ... -- ILoString 
     ... this.reverse()        ... -- ILoString 
     ... this.reverseHelper(ILoString)  ... -- ILoString
     ... this.isDoubledList() ... -- boolean 
     ... this.isDoubledListHelper(String) ... -- boolean 
     ... this.isPalindromeList()    ... -- boolean 

     METHODS FOR FIELDS
     ... this.first.concat(String) ...        -- String
     ... this.first.compareToIgnoreCase(String) ...     -- int
     ... this.rest.combine() ...              -- String
     ... this.rest.sort() ...                 -- ILoString
     ... this.rest.insert(String)           -- ILoString
     ... this.rest.isSorted() ...             -- boolean 
     ... this.rest.isSortedHelper(String) ...       -- boolean 
     ... this.rest.reverse()        ... -- ILoString 
     ... this.rest.reverseHelper(ILoString)  ... -- ILoString
     ... this.rest.isDoubledList() ...   -- boolean 
     ... this.rest.isDoubledListHelper(String) ... -- boolean 

     Parameters:
     ... other ...  -- ILoString 

     Methods on parameters 
     ... other.interleave(ILoString) ...      --ILoString
     ... other.merge(ILoString) ...  ILoString 
     */
    return this.first.concat(this.rest.combine());
  }  

  //produces a new list, sorted in alphabetical order 
  // treating all Strings as if they were given in all lowercase.
  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  //produce a new list, insert the given String into this ILoString 
  // already sorted alphabetically 
  public ILoString insert(String s) {
    if (this.first.compareToIgnoreCase(s) > 0) {
      return new ConsLoString(s, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.insert(s));
    }
  }

  //is this list sorted in alphabetic order, in a case-insensitive way?
  public boolean isSorted() {
    return this.rest.isSortedHelper(this.first);
  }

  //is the given String alphabetically before all element in this sorted list?
  public boolean isSortedHelper(String s) {
    return s.compareToIgnoreCase(this.first) <= 0
        && this.rest.isSortedHelper(this.first);
  }

  //produce a list where the odd number of elements are from this list 
  // the even number of elements are from the given list 
  // any "leftover" elements should just be left at the end.
  public ILoString interleave(ILoString other) {
    return new ConsLoString(this.first, other.interleave(this.rest));
  }

  //produces a sorted list that merge this sorted list with the given sorted list 
  public ILoString merge(ILoString other) {
    return other.merge(this.rest).insert(this.first);
  }

  // produce this list of strings, in a reversed order
  public ILoString reverse() {
    return reverseHelper(new MtLoString());
  }

  // produce this list of strings in a reversed order,
  // with the given string at the end of this list  
  public ILoString reverseHelper(ILoString other) {
    return this.rest.reverseHelper(new ConsLoString(this.first, other));
  }

  //determines if this list contains pairs of identical strings
  public boolean isDoubledList() {
    return this.rest.isDoubledListHelper(this.first);
  }

  //determines if the first of this list matches the given string 
  // and the rest of this list contains pairs of identical strings
  public boolean isDoubledListHelper(String s) {
    return s == this.first 
        && this.rest.isDoubledList();
  }

  //determines whether this list contains 
  // the same words reading the list in either order
  public boolean isPalindromeList() {
    return interleave(reverse()).isDoubledList();
  }
  
  public PairOfLists helper() { 
    return this.rest.helper2().addToFirst(this.first);
  }
  public PairOfLists helper2() {
    return this.rest.helper().addToSecond(this.first);
  }  
  public PairOfLists unize() {
    return this.helper();
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ",
      new ConsLoString("had ",
          new ConsLoString("a ",
              new ConsLoString("little ",
                  new ConsLoString("lamb.", new MtLoString())))));

  ILoString marySorted = new ConsLoString("ab ",
      new ConsLoString("had ",
          new ConsLoString("lamb.",
              new ConsLoString("little ",
                  new ConsLoString("Mary ", new MtLoString())))));

  ILoString los1 = new ConsLoString("a", 
      new ConsLoString("b", 
          new ConsLoString("c", new MtLoString())));
  ILoString los2 = new ConsLoString("d", 
      new ConsLoString("e", 
          new ConsLoString("f", new MtLoString())));
  ILoString los3 = new ConsLoString("g", 
      new ConsLoString("h", 
          new ConsLoString("i", 
              new ConsLoString("j", 
                  new ConsLoString("k", new MtLoString())))));
  ILoString los4 = new ConsLoString("a", new ConsLoString("a", 
      new ConsLoString("b", new ConsLoString("b", 
          new ConsLoString("c", new ConsLoString("c", new MtLoString()))))));
  ILoString los5 = new ConsLoString("a", new ConsLoString("a", 
      new ConsLoString("b", new ConsLoString("b", 
          new ConsLoString("c", new MtLoString())))));
  ILoString los6 = new ConsLoString("a", 
      new ConsLoString("b", 
          new ConsLoString("b", 
              new ConsLoString("c", 
                  new ConsLoString("c", new MtLoString())))));
  ILoString los7 = new ConsLoString("a", 
      new ConsLoString("b", new ConsLoString("a", new MtLoString())));


  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test the method sort for the lists of Strings 
  boolean testSort(Tester t) {
    return t.checkExpect((new MtLoString()).sort(), new MtLoString())
        && t.checkExpect(this.mary.sort(), 
            new ConsLoString("a ",
                new ConsLoString("had ",
                    new ConsLoString("lamb.",
                        new ConsLoString("little ",
                            new ConsLoString("Mary ", new MtLoString()))))));
  }

  // test the method insert for the lists of Strings 
  boolean testInsert(Tester t) {
    return t.checkExpect(new MtLoString().insert("a"), new ConsLoString("a", new MtLoString()))
        && t.checkExpect(this.marySorted.insert("b"), 
            new ConsLoString("ab ",
                new ConsLoString("b", 
                    new ConsLoString("had ",
                        new ConsLoString("lamb.",
                            new ConsLoString("little ",
                                new ConsLoString("Mary ", new MtLoString())))))));
  }

  // test the method isSorted for the lists of Strings 
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.mary.isSorted(), false)
        && t.checkExpect(this.marySorted.isSorted(), true);
  }

  // test the method isSortedHelper for the lists of Strings 
  boolean testIsSortedHelper(Tester t) {
    return t.checkExpect(this.mary.isSortedHelper("b"), false)
        && t.checkExpect(this.marySorted.isSortedHelper("a"), true);
  }

  // test the method interleave for the lists of Strings 
  boolean testInterleave(Tester t) {
    return t.checkExpect(new MtLoString().interleave(los1), los1)
        && t.checkExpect(this.los1.interleave(los2), new ConsLoString("a", 
            new ConsLoString("d", 
                new ConsLoString("b", 
                    new ConsLoString("e", 
                        new ConsLoString("c", 
                            new ConsLoString("f", new MtLoString())))))))
        && t.checkExpect(this.los1.interleave(los3), new ConsLoString("a", 
            new ConsLoString("g", new ConsLoString("b",
                new ConsLoString("h", new ConsLoString("c", 
                    new ConsLoString("i", new ConsLoString("j",
                        new ConsLoString("k",new MtLoString())))))))))
        && t.checkExpect(this.los3.interleave(los1), new ConsLoString("g", 
            new ConsLoString("a", new ConsLoString("h",
                new ConsLoString("b", new ConsLoString("i", 
                    new ConsLoString("c", new ConsLoString("j",
                        new ConsLoString("k",new MtLoString())))))))))
        && t.checkExpect(this.los1.interleave(los1), new ConsLoString("a", 
            new ConsLoString("a", 
                new ConsLoString("b", 
                    new ConsLoString("b", 
                        new ConsLoString("c", 
                            new ConsLoString("c", new MtLoString())))))));
  }

  // test the method merge for the lists of Strings 
  boolean testMerge(Tester t) {
    return 
        // an example that produce the result different from method interleave
        t.checkExpect(this.los1.merge(los2), new ConsLoString("a", 
            new ConsLoString("b", 
                new ConsLoString("c", 
                    new ConsLoString("d", 
                        new ConsLoString("e", 
                            new ConsLoString("f", new MtLoString())))))))
        // an example that produce the result similar with method interleave
        && t.checkExpect(this.los1.merge(los1), new ConsLoString("a", 
            new ConsLoString("a", 
                new ConsLoString("b", 
                    new ConsLoString("b", 
                        new ConsLoString("c", 
                            new ConsLoString("c", new MtLoString())))))));
  }

  // test the method reverse for the lists of Strings 
  boolean testReverse(Tester t) {
    return t.checkExpect(this.los1.reverse(), new ConsLoString("c", 
        new ConsLoString("b", 
            new ConsLoString("a", new MtLoString()))))
        && t.checkExpect(this.mary.reverse(), new ConsLoString("lamb.",
            new ConsLoString("little ",
                new ConsLoString("a ",
                    new ConsLoString("had ",
                        new ConsLoString("Mary ", new MtLoString()))))));
  }

  // test the method reverseHelper for the lists of Strings 
  boolean testReverseHelper(Tester t) {
    return t.checkExpect(this.los1.reverseHelper(new MtLoString()), new ConsLoString("c", 
        new ConsLoString("b", 
            new ConsLoString("a", new MtLoString())))) 
        && t.checkExpect(this.los1.reverseHelper(los1), new ConsLoString("c", 
            new ConsLoString("b", 
                new ConsLoString("a", los1))));
  }
  
  // test the method isDoubledList for the lists of Strings
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.los4.isDoubledList(), true)
        && t.checkExpect(this.los5.isDoubledList(), false);
  }
  
  // test the method isDoubledListHelper for the lists of Strings 
  boolean testIsDoubledListHelper(Tester t) {
    return t.checkExpect(this.mary.isDoubledListHelper("a"), false)
        && t.checkExpect(this.los6.isDoubledListHelper("a"), true);
  }
  
  // test the method isPalindromeList for the lists of Strings 
  boolean testIsPalidrome(Tester t) {
    return t.checkExpect(this.mary.isPalindromeList(), false)
        && t.checkExpect(this.los7.isPalindromeList(), true);
  }
  ILoString list = new ConsLoString("1 ",
      new ConsLoString("2 ",
          new ConsLoString("3 ",
              new ConsLoString("4 ",
                  new ConsLoString("5 ", new MtLoString())))));
  
  ILoString list1 = new ConsLoString("1 ",
      new ConsLoString("3 ",
          new ConsLoString("5 ", new MtLoString())));
  
  ILoString list2 = new ConsLoString("2 ",
      new ConsLoString("4 ", new MtLoString()));
  
  PairOfLists ans = new PairOfLists(list1, list2);
  boolean testPair(Tester t) {
    return t.checkExpect(this.list.unize(), ans);
  }
}