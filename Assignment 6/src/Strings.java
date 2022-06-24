
import tester.Tester;

// to represent a predict that compare the two strings 
interface IStringsCompare {
  // to test whether string 1 comes before string 2 
  boolean comesBefore(String s1, String s2);
}

//to represent a predict that compare the two strings 
class StringLexComp implements IStringsCompare {
  //test whether string 2 comes before string 1 lexicographically
  public boolean comesBefore(String s1, String s2) {
    return s2.compareTo(s1) < 0;
  }
}

// to represent a predict that compare the two strings length 
class StringLengthComp implements IStringsCompare {
  // test whether string 1 comes before string2, from shortest to longest 
  public boolean comesBefore(String s1, String s2) {
    return s1.length() <= s2.length();
  }
}

// to represent a list of strings 
interface ILoString { 
  // is this list of strings sorted according to the IStringCompare?
  boolean isSorted(IStringsCompare c);

  // is this list sorted according to the IStringCompare?
  boolean isSortedHelper(IStringsCompare c, String other);

  // sort this list of Strings according to IStringCompare
  ILoString sort(IStringsCompare c);

  // insert the string in this sorted list 
  ILoString insert(IStringsCompare c, String other);

  // merge the this sorted list and the given sorted list 
  // and sort them according to IStringCompare
  ILoString merge(ILoString other, IStringsCompare c);

  // is this list the same as the given list? 
  boolean sameList(ILoString other); 

  // is this list the same as the given ConsLoString 
  boolean sameConsList(ConsLoString other);

  // is this list the same as the given empty list of strings
  boolean sameMtList(MtLoString other);
}

// to represent an empty list of strings 
class MtLoString implements ILoString {

  //is this list of strings sorted according to the IStringCompare?
  public boolean isSorted(IStringsCompare c) {
    return true;
  }

  //is this list of strings sorted according to the IStringCompare?
  public boolean isSortedHelper(IStringsCompare c, String other) {
    return true;
  }

  // sort this list of Strings according to IStringCompare
  public ILoString sort(IStringsCompare c) {
    return this;
  }

  // insert the string in this sorted list 
  public ILoString insert(IStringsCompare c, String other) {
    return new ConsLoString(other, this);
  }

  // merge the this sorted list and the given sorted list 
  // and sort them according to IStringCompare
  public ILoString merge(ILoString other, IStringsCompare c) {
    return other;
  }

  // is this list the same as the given one 
  public boolean sameList(ILoString other) {
    return other.sameMtList(this);
  }

  // is this list the same as the given ConsLoString
  public boolean sameConsList(ConsLoString other) {
    return false;
  }

  // is this list the same as the given MtLoString 
  public boolean sameMtList(MtLoString other) {
    return true;
  }
}

// to represent a list of strings 
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  // constructor 
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  //is this list of strings sorted according to the IStringCompare?
  public boolean isSorted(IStringsCompare c) {
    return this.rest.isSortedHelper(c, this.first);
  }

  //is this list of strings sorted according to the IStringCompare?
  public boolean isSortedHelper(IStringsCompare c, String other) {
    return c.comesBefore(other, this.first)
        && this.rest.isSortedHelper(c, this.first);
  }

  // sort this list of Strings according to IStringCompare
  public ILoString sort(IStringsCompare c) {
    return this.rest.sort(c).insert(c, this.first);
  }

  // insert the string in this sorted list 
  public ILoString insert(IStringsCompare c, String other) {
    if (c.comesBefore(other, this.first)) {
      return new ConsLoString(other, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.insert(c, other));
    }
  } 

  //merge the this sorted list and the given sorted list 
  // and sort them according to IStringCompare
  public ILoString merge(ILoString other, IStringsCompare c) {
    return other.merge(this.rest, c).insert(c, this.first);
  }

  // is this list the same as the given one 
  public boolean sameList(ILoString other) {
    return other.sameConsList(this);
  }

  // is this list the same as the given ConsLoString 
  public boolean sameConsList(ConsLoString other) {
    return this.first.equals(other.first)
        && this.rest.sameList(other.rest);
  }

  // is this list the same as the given MtLoString 
  public boolean sameMtList(MtLoString other) {
    return false;
  }
}

class ExampleStrings {
  ILoString mtLoString = new MtLoString();

  MtLoString mtLoString2 = new MtLoString();

  ILoString iLoString1 = new ConsLoString("a", 
      new ConsLoString("b", 
          new ConsLoString("c", mtLoString)));

  ILoString iLoString2 = new ConsLoString("c", 
      new ConsLoString("b", 
          new ConsLoString("a", mtLoString)));

  ILoString iLoString3 = new ConsLoString("c", 
      new ConsLoString("b", 
          new ConsLoString("c", mtLoString)));

  ILoString iLoString4 = new ConsLoString("aa", 
      new ConsLoString("bbb", 
          new ConsLoString("ccc", mtLoString)));

  ILoString iLoString5 = new ConsLoString("cc", 
      new ConsLoString("bbb", 
          new ConsLoString("a", mtLoString)));

  ILoString iLoString6 = new ConsLoString("aaa", 
      new ConsLoString("bb", 
          new ConsLoString("c", mtLoString)));

  ILoString iLoString7 = new ConsLoString("a", 
      new ConsLoString("a", mtLoString));

  ConsLoString cLoString8 = new ConsLoString("a", 
      new ConsLoString("b", 
          new ConsLoString("c", mtLoString)));

  ConsLoString cLoString9 = new ConsLoString("c", 
      new ConsLoString("b", 
          new ConsLoString("a", mtLoString)));

  ConsLoString cLoString10 = new ConsLoString("c", 
      new ConsLoString("b", 
          new ConsLoString("c", mtLoString)));

  ConsLoString cLoString11 = new ConsLoString("aa", 
      new ConsLoString("bbb", 
          new ConsLoString("ccc", mtLoString)));

  // test the method comsBefore 
  boolean testComesBefore(Tester t) {
    return t.checkExpect(new StringLexComp().comesBefore("a", "b"), false)
        && t.checkExpect(new StringLexComp().comesBefore("bb", "a"), true)
        && t.checkExpect(new StringLengthComp().comesBefore("bb", "a"), false)
        && t.checkExpect(new StringLexComp().comesBefore("b", "aaa"), true);
  }

  // test the method isSorted
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.iLoString1.isSorted(new StringLexComp()), false)
        && t.checkExpect(this.iLoString2.isSorted(new StringLexComp()), true)
        && t.checkExpect(this.iLoString3.isSorted(new StringLexComp()), false)
        && t.checkExpect(this.iLoString4.isSorted(new StringLengthComp()), true)
        && t.checkExpect(this.iLoString5.isSorted(new StringLengthComp()), false)
        && t.checkExpect(this.iLoString6.isSorted(new StringLengthComp()), false)
        && t.checkExpect(this.mtLoString.isSorted(new StringLexComp()), true)
        && t.checkExpect(this.mtLoString.isSorted(new StringLengthComp()), true);
  }

  // test the method isSortedHelper
  boolean testIsSortedHelper(Tester t) {
    return t.checkExpect(this.iLoString1.isSortedHelper(new StringLexComp(), "a"), false)
        && t.checkExpect(this.iLoString2.isSortedHelper(new StringLexComp(), "d"), true)
        && t.checkExpect(this.iLoString3.isSortedHelper(new StringLexComp(), "a"), false)
        && t.checkExpect(this.iLoString4.isSortedHelper(new StringLengthComp(), "a"), true)
        && t.checkExpect(this.iLoString5.isSortedHelper(new StringLengthComp(), "aaa"), false)
        && t.checkExpect(this.iLoString6.isSortedHelper(new StringLengthComp(), "a"), false)
        && t.checkExpect(this.mtLoString.isSortedHelper(new StringLexComp(), "a"), true)
        && t.checkExpect(this.mtLoString.isSortedHelper(new StringLengthComp(), "a"), true);
  }

  // test the method sort
  boolean testSort(Tester t) {
    return t.checkExpect(this.iLoString1.sort(new StringLexComp()), this.iLoString2)
        && t.checkExpect(this.iLoString3.sort(new StringLexComp()), new ConsLoString("c", 
            new ConsLoString("c", new ConsLoString("b", this.mtLoString)))); 
  }

  // test the method insert 
  boolean testInsert(Tester t) {
    return t.checkExpect(this.iLoString2.insert(new StringLexComp(), "a"), 
        new ConsLoString("c", 
            new ConsLoString("b", 
                new ConsLoString("a", new ConsLoString("a", mtLoString)))))
        && t.checkExpect(this.iLoString2.insert(new StringLexComp(), "d"), 
            new ConsLoString("d", 
                new ConsLoString("c", 
                    new ConsLoString("b", new ConsLoString("a", mtLoString)))))
        && t.checkExpect(this.mtLoString.insert(new StringLexComp(), "a"), 
            new ConsLoString("a", this.mtLoString))
        && t.checkExpect(this.mtLoString.insert(new StringLengthComp(), "a"), 
            new ConsLoString("a", this.mtLoString))
        && t.checkExpect(this.iLoString4.insert(new StringLengthComp(), "dddd"), 
            new ConsLoString("aa", 
                new ConsLoString("bbb", 
                    new ConsLoString("ccc", new ConsLoString("dddd", mtLoString)))));
  }

  // test the method merge 
  boolean testMerge(Tester t) {
    return t.checkExpect(this.iLoString2.merge(mtLoString, new StringLexComp()), this.iLoString2)
        && t.checkExpect(this.mtLoString.merge(mtLoString, new StringLexComp()), this.mtLoString)
        && t.checkExpect(this.iLoString2.merge(this.iLoString2, new StringLexComp()), 
            new ConsLoString("c", 
                new ConsLoString("c", 
                    new ConsLoString("b", 
                        new ConsLoString("b",
                            new ConsLoString("a",new ConsLoString("a", this.mtLoString)))))))
        && t.checkExpect(this.iLoString7.merge(this.iLoString2, new StringLexComp()), 
            new ConsLoString("c", 
                new ConsLoString("b", 
                    new ConsLoString("a", 
                        new ConsLoString("a",
                            new ConsLoString("a", this.mtLoString))))))
        && t.checkExpect(this.iLoString4.merge(mtLoString, new StringLengthComp()), this.iLoString4)
        && t.checkExpect(this.mtLoString.merge(mtLoString, new StringLengthComp()), this.mtLoString)
        && t.checkExpect(this.iLoString4.merge(this.iLoString4, new StringLengthComp()), 
            new ConsLoString("aa", 
                new ConsLoString("aa", 
                    new ConsLoString("bbb", 
                        new ConsLoString("bbb",
                            new ConsLoString("ccc",new ConsLoString("ccc", this.mtLoString)))))))
        && t.checkExpect(this.iLoString7.merge(this.iLoString4, new StringLengthComp()), 
            new ConsLoString("a", 
                new ConsLoString("a", 
                    new ConsLoString("aa", 
                        new ConsLoString("bbb",
                            new ConsLoString("ccc", this.mtLoString))))));
  }

  // test the method sameConsList
  boolean testSameConsList(Tester t) {
    return t.checkExpect(this.cLoString8.sameConsList(this.cLoString9), false)
        && t.checkExpect(this.cLoString8.sameConsList(this.cLoString8), true)
        && t.checkExpect(this.cLoString8.sameConsList(this.cLoString10), false)
        && t.checkExpect(this.mtLoString.sameConsList(this.cLoString9), false);
  }

  // test the method sameMtList
  boolean testSameMtList(Tester t) {
    return t.checkExpect(this.cLoString8.sameMtList(this.mtLoString2), false)
        && t.checkExpect(this.cLoString9.sameMtList(this.mtLoString2), false)
        && t.checkExpect(this.cLoString10.sameMtList(this.mtLoString2), false)
        && t.checkExpect(this.mtLoString.sameMtList(this.mtLoString2), true);
  }

  // test the method sameList
  boolean testSameList(Tester t) {
    return t.checkExpect(this.mtLoString.sameList(this.mtLoString), true)
        && t.checkExpect(this.iLoString1.sameList(this.iLoString2), false)
        && t.checkExpect(this.iLoString1.sameList(this.iLoString1), true)
        && t.checkExpect(this.mtLoString.sameList(this.iLoString1),false)
        && t.checkExpect(this.mtLoString.sameList(this.iLoString7),false);
  }
}
