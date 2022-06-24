
import tester.Tester;

//to represent a predict that compare the two same types data  
interface IComparator<T> {
  // produce a int that represent the result of comparing two same data type
  int compare(T t1, T t2);
}

// to represent the comparison between two strings in reverse
// lexicographical order
class StringLexCompGen implements IComparator<String> {
  //produce a number that represent the result of comparing two 
  //strings in  reverse lexicographical order 
  // <= 0 means s2 comes before s1 or equal to s1
  // >0 means s1 comes before s2
  public int compare(String s1, String s2) {
    return s2.compareTo(s1);
  }
}

// to represent the comparison between two strings
// according to their length, from shortest to longest
class StringLengthCompGen implements IComparator<String> {
  //produce a number that represent the result of comparing two 
  //strings according to their length, from shortest to longest
  public int compare(String s1, String s2) {
    return s1.length() - s2.length();
  }
}

// to represent the comparison between two strings 
// whether they are the same 
class SameString implements IComparator<String> { 

  // produce the number that represent the result of the comparison 
  // 1 means that the two input strings are equal
  // - 1 means that they are different 
  public int compare(String s1, String s2) {
    if (s1.equals(s2)) {
      return 1;
    }
    else {
      return -1;
    }
  }
}

// to represent a list of any data types 
interface IList<T> {
  // is this list sorted 
  boolean isSorted(IComparator<T> c);

  // is this list sorted with the given date types
  boolean isSortedHelper(T other, IComparator<T> c);

  // insert the given T to this sorted list 
  IList<T> insert(T other, IComparator<T> c);

  // merge this list with the given list, according to the comparator 
  IList<T> merge(IList<T> other, IComparator<T> c);

  // is this list the same as the given list 
  boolean sameList(IList<T> other, IComparator<T> c); 

  // is this list the same as the given ConsList
  boolean sameConsList(ConsList<T> other, IComparator<T> c);

  // is this list the same as the given MtList
  boolean sameMtList(MtList<T> other);
}

// to represent an empty list of t 
class MtList<T> implements IList<T> {

  // is this list sorted 
  public boolean isSorted(IComparator<T> c) {
    return true;
  }

  //is this list sorted with the given date types
  public boolean isSortedHelper(T other, IComparator<T> c) {
    return true;
  }

  // insert the given T to this sorted list 
  public IList<T> insert(T other, IComparator<T> c) {
    return new ConsList<T>(other, this);
  }

  // merge this list with the given list, according to the comparator 
  public IList<T> merge(IList<T> other, IComparator<T> c) {
    return other;
  }

  // is this list the same as the given list 
  public boolean sameList(IList<T> other, IComparator<T> c) {
    return other.sameMtList(this);
  }

  // is this list the same as the given ConsList
  public boolean sameConsList(ConsList<T> other, IComparator<T> c) {
    return false;
  }

  // is this list the same as the given MtList
  public boolean sameMtList(MtList<T> other) {
    return true;
  }
}

// to represent an ConsLoList of T
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // constructor
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // is this list sorted 
  public boolean isSorted(IComparator<T> c) {
    return this.rest.isSortedHelper(this.first, c);
  }

  // is this listed sorted with the given T, according to the comparator
  public boolean isSortedHelper(T other, IComparator<T> c) {
    return (c.compare(other, this.first) <= 0)
        && this.rest.isSortedHelper(this.first, c);
  }

  // insert the given T to this sorted list 
  public IList<T> insert(T other, IComparator<T> c) {
    if (c.compare(other, this.first) <= 0) {
      return new ConsList<T>(other, this);
    }
    else {
      return new ConsList<T>(this.first, this.rest.insert(other, c));
    }
  }

  // merge this sorted list with the given sorted list 
  public IList<T> merge(IList<T> other, IComparator<T> c) {
    return other.merge(this.rest, c).insert(this.first, c);
  }

  // is this list the same as the given list
  public boolean sameList(IList<T> other, IComparator<T> c) {
    return other.sameConsList(this, c);
  }

  // is this list the same as the given ConsList
  public boolean sameConsList(ConsList<T> other, IComparator<T> c) {
    return c.compare(this.first, other.first) == 1
        && this.rest.sameList(other.rest, c);
  }

  // is this list the same as the given MtList
  public boolean sameMtList(MtList<T> other) {
    return false;
  }
}

// test and examples 
class ExampleStringsGen {
  IList<String> mtLoString = new MtList<String>();

  IList<String> iLoString1 = new ConsList<String>("a", 
      new ConsList<String>("b", 
          new ConsList<String>("c", mtLoString)));

  IList<String> iLoString2 = new ConsList<String>("c", 
      new ConsList<String>("b", 
          new ConsList<String>("a", mtLoString)));

  IList<String> iLoString3 = new ConsList<String>("c", 
      new ConsList<String>("b", 
          new ConsList<String>("c", mtLoString)));

  IList<String> iLoString4 = new ConsList<String>("aa", 
      new ConsList<String>("bbb", 
          new ConsList<String>("ccc", mtLoString)));

  IList<String> iLoString5 = new ConsList<String>("cc", 
      new ConsList<String>("bbb", 
          new ConsList<String>("a", mtLoString)));

  IList<String> iLoString6 = new ConsList<String>("aaa", 
      new ConsList<String>("bb", 
          new ConsList<String>("c", mtLoString)));

  IList<String> iLoString7 = new ConsList<String>("a", 
      new ConsList<String>("a", mtLoString));

  MtList<String> mtLoString2 = new MtList<String>();

  ConsList<String> cLoString8 = new ConsList<String>("a", 
      new ConsList<String>("b", 
          new ConsList<String>("c", mtLoString)));

  ConsList<String> cLoString9 = new ConsList<String>("c", 
      new ConsList<String>("b", 
          new ConsList<String>("a", mtLoString)));

  ConsList<String> cLoString10 = new ConsList<String>("c", 
      new ConsList<String>("b",
          new ConsList<String>("c", mtLoString)));

  ConsList<String> cLoString11 = new ConsList<String>("aa", 
      new ConsList<String>("bbb", 
          new ConsList<String>("ccc", mtLoString)));

  // test the method compare 
  boolean testCompare(Tester t) {
    return t.checkExpect(new StringLexCompGen().compare("a", "b"), 1)
        && t.checkExpect(new StringLexCompGen().compare("bb", "a"), -1)
        && t.checkExpect(new StringLengthCompGen().compare("bb", "a"), 1)
        && t.checkExpect(new StringLengthCompGen().compare("b", "aaa"), -2)
        && t.checkExpect(new SameString().compare("a", "b"), -1)
        && t.checkExpect(new SameString().compare("a", "a"), 1);
  }

  // test the method isSorted
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.iLoString1.isSorted(new StringLexCompGen()), false)
        && t.checkExpect(this.iLoString2.isSorted(new StringLexCompGen()), true)
        && t.checkExpect(this.iLoString3.isSorted(new StringLexCompGen()), false)
        && t.checkExpect(this.iLoString4.isSorted(new StringLengthCompGen()), true)
        && t.checkExpect(this.iLoString5.isSorted(new StringLengthCompGen()), false)
        && t.checkExpect(this.iLoString6.isSorted(new StringLengthCompGen()), false)
        && t.checkExpect(this.mtLoString.isSorted(new StringLexCompGen()), true)
        && t.checkExpect(this.mtLoString.isSorted(new StringLengthCompGen()), true);
  }

  //test the method isSortedHelper
  boolean testIsSortedHelper(Tester t) {
    return t.checkExpect(this.iLoString1.isSortedHelper("a", new StringLexCompGen()), false)
        && t.checkExpect(this.iLoString2.isSortedHelper("d", new StringLexCompGen()), true)
        && t.checkExpect(this.iLoString3.isSortedHelper("a", new StringLexCompGen()), false)
        && t.checkExpect(this.iLoString4.isSortedHelper("a", new StringLengthCompGen()), true)
        && t.checkExpect(this.iLoString5.isSortedHelper("aaa", new StringLengthCompGen()), false)
        && t.checkExpect(this.iLoString6.isSortedHelper("a", new StringLengthCompGen()), false)
        && t.checkExpect(this.mtLoString.isSortedHelper("a", new StringLexCompGen()), true)
        && t.checkExpect(this.mtLoString.isSortedHelper("a", new StringLengthCompGen()), true);
  }

  // test the method insert 
  boolean testInsert(Tester t) {
    return t.checkExpect(this.iLoString2.insert("a", new StringLexCompGen()), 
        new ConsList<String>("c", 
            new ConsList<String>("b", 
                new ConsList<String>("a", new ConsList<String>("a", mtLoString)))))
        && t.checkExpect(this.iLoString2.insert("d", new StringLexCompGen()), 
            new ConsList<String>("d", 
                new ConsList<String>("c", 
                    new ConsList<String>("b", new ConsList<String>("a", mtLoString)))))
        && t.checkExpect(this.mtLoString.insert("a", new StringLexCompGen()), 
            new ConsList<String>("a", this.mtLoString))
        && t.checkExpect(this.mtLoString.insert("a", new StringLengthCompGen()), 
            new ConsList<String>("a", this.mtLoString))
        && t.checkExpect(this.iLoString4.insert("dddd", new StringLengthCompGen()), 
            new ConsList<String>("aa", 
                new ConsList<String>("bbb", 
                    new ConsList<String>("ccc", new ConsList<String>("dddd", mtLoString)))));
  }

  // test the method merge 
  boolean testMerge(Tester t) {
    return t.checkExpect(this.iLoString2.merge(mtLoString, new StringLexCompGen()), 
        this.iLoString2)
        && t.checkExpect(this.mtLoString.merge(mtLoString, new StringLexCompGen()), 
            this.mtLoString)
        && t.checkExpect(this.iLoString2.merge(this.iLoString2, new StringLexCompGen()), 
            new ConsList<String>("c", 
                new ConsList<String>("c", 
                    new ConsList<String>("b", 
                        new ConsList<String>("b",
                            new ConsList<String>("a",
                                new ConsList<String>("a", this.mtLoString)))))))
        && t.checkExpect(this.iLoString7.merge(this.iLoString2, new StringLexCompGen()), 
            new ConsList<String>("c", 
                new ConsList<String>("b", 
                    new ConsList<String>("a", 
                        new ConsList<String>("a",
                            new ConsList<String>("a", this.mtLoString))))))
        && t.checkExpect(this.iLoString4.merge(mtLoString, new StringLengthCompGen()), 
            this.iLoString4)
        && t.checkExpect(this.mtLoString.merge(mtLoString, new StringLengthCompGen()), 
            this.mtLoString)
        && t.checkExpect(this.iLoString4.merge(this.iLoString4, new StringLengthCompGen()), 
            new ConsList<String>("aa", 
                new ConsList<String>("aa", 
                    new ConsList<String>("bbb", 
                        new ConsList<String>("bbb",
                            new ConsList<String>("ccc",new ConsList<String>("ccc", 
                                this.mtLoString)))))))
        && t.checkExpect(this.iLoString7.merge(this.iLoString4, new StringLengthCompGen()), 
            new ConsList<String>("a", 
                new ConsList<String>("a", 
                    new ConsList<String>("aa", 
                        new ConsList<String>("bbb",
                            new ConsList<String>("ccc", this.mtLoString))))));
  }

  // test the method sameConsList
  boolean testSameConsList(Tester t) {
    return t.checkExpect(this.cLoString8.sameConsList(this.cLoString9, new SameString()), false)
        && t.checkExpect(this.cLoString8.sameConsList(this.cLoString8, new SameString()), true)
        && t.checkExpect(this.cLoString8.sameConsList(this.cLoString10, new SameString()), false)
        && t.checkExpect(this.mtLoString.sameConsList(this.cLoString9, new SameString()), false);
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
    return t.checkExpect(this.mtLoString.sameList(this.mtLoString, new SameString()), true)
        && t.checkExpect(this.iLoString1.sameList(this.iLoString2, new SameString()), false)
        && t.checkExpect(this.iLoString1.sameList(this.iLoString1, new SameString()), true)
        && t.checkExpect(this.mtLoString.sameList(this.iLoString1, new SameString()),false)
        && t.checkExpect(this.mtLoString.sameList(this.iLoString7, new SameString()),false);
  }
}