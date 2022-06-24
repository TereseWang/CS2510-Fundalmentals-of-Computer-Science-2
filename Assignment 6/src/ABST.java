import tester.Tester;

//represent a book
class Book {
  String title;
  String author;
  int price;

  // constructor 
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

//to represent a predict that compare the two same types data  
interface IComparator<T> {
  // Returns a negative number if t1 comes before t2 in this ordering
  // Returns zero              if t1 is the same as t2 in this ordering
  // Returns a positive number if t1 comes after t2 in this ordering
  int compare(T t1, T t2);
}


//compare the books by their title alphabetically
class BooksByTitle implements IComparator<Book> {
  //Returns a negative number if book1 comes before book2 in this ordering
  // Returns zero              if book1 is the same as book2 in this ordering
  // Returns a positive number if book1 comes after book2 in this ordering
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

//compare the books by their author alphabetically
class BooksByAuthor implements IComparator<Book> {
  //Returns a negative number if book1 comes before book2 in this ordering
  // Returns zero              if book1 is the same as book2 in this ordering
  // Returns a positive number if book1 comes after book2 in this ordering
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

//compare the books by their price increasingly
class BooksByPrice implements IComparator<Book> {
  //Returns a negative number if book1 comes before book2 in this ordering
  // Returns zero              if book1 is the same as book2 in this ordering
  // Returns a positive number if book1 comes after book2 in this ordering
  public int compare(Book b1, Book b2) {
    return b1.price - b2.price;
  }
}

//represent a binary search tree
abstract class ABST<T> {
  IComparator<T> order;

  //constructor
  ABST(IComparator<T> order) {
    this.order = order;
  }

  //takes an item and produces a new binary search tree with
  //the given item inserted in the correct place.
  abstract ABST<T> insert(T item);

  //returns the leftmost item contained in this tree.
  abstract T getLeftmost();

  //returns the leftmost item contained in this tree.
  abstract T getLeftmostHelper(T data);

  //returns the tree containing all but the leftmost item of this tree.
  abstract ABST<T> getRight();

  //determines whether this binary search tree is the same as the given one
  abstract boolean sameTree(ABST<T> that);

  //determines whether that node is the same as this ABST
  abstract boolean sameNode(Node<T> that);

  //determines whether that leaf is the same as this ABST
  abstract boolean sameLeaf(Leaf<T> leaf);

  //is this tree contain the same data and same order of the given tree
  public boolean sameData(ABST<T> that) {
    return this.sameAsList(that.buildList(new MtList<T>()).reverse());
  }

  //is this tree contain the same date in the same order of the given list of t 
  public boolean sameAsList(IList<T> list) {
    return this.buildList(new MtList<T>()).reverse().sameList(list, this.order);
  }

  //represent the binary search tree of type T that consumes a list of type T 
  //and adds to it one at a time all items from this tree in the sorted order.
  abstract IList<T> buildList(IList<T> list);
}

// to represent a leaf
class Leaf<T> extends ABST<T> {

  //constructor 
  Leaf(IComparator<T> order) {
    super(order);
  }

  //takes an item and produces a new binary search tree with
  //the given item inserted in the correct place.
  ABST<T> insert(T item) {
    return new Node<T>(order, item, new Leaf<T>(order), new Leaf<T>(order));
  }

  //returns the leftmost item contained in this tree.
  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  //returns the leftmost item contained in this tree.
  T getLeftmostHelper(T data) {
    return data;
  }

  //returns the tree containing all but the leftmost item of this tree.
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  //determines whether this binary search tree is the same as the given one
  boolean sameTree(ABST<T> that) {
    return that.sameLeaf(this);
  }

  //determines whether that node is the same as this leaf
  boolean sameNode(Node<T> that) {
    return false;
  }

  //determines whether that leaf is the same as this leaf
  boolean sameLeaf(Leaf<T> leaf) {
    return true;
  }

  //represent the binary search tree of type T that consumes a list of type T 
  //and adds to it one at a time all items from this tree in the sorted order.
  IList<T> buildList(IList<T> list) {
    return list;
  }
}

class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  //takes an item and produces a new binary search tree with
  //the given item inserted in the correct place.
  ABST<T> insert(T item) {
    if (order.compare(item, this.data) < 0) {
      return new Node<T>(this.order,this.data, this.left.insert(item), this.right);
    }
    else {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }
  }

  //returns the leftmost item contained in this tree.
  T getLeftmost() {
    return this.left.getLeftmostHelper(this.data);
  }

  //returns the leftmost item contained in this tree.
  T getLeftmostHelper(T data) {
    return this.left.getLeftmostHelper(this.data);
  }

  //returns the tree containing all but the leftmost item of this tree.
  ABST<T> getRight() {
    if (this.order.compare(this.data, this.getLeftmost()) == 0) {
      return this.right;
    }
    else {
      return new Node<T>(order, data, left.getRight(), right);
    }
  }

  //determines whether this binary search tree is the same as the given one
  boolean sameTree(ABST<T> that) {
    return that.sameNode(this);
  }

  //determines whether that node is the same as this node
  boolean sameNode(Node<T> that) {
    return this.order.compare(this.data, that.data) == 0
        && this.left.sameTree(that.left)
        && this.right.sameTree(that.right);
  }

  //determines whether that leaf is the same as this node
  boolean sameLeaf(Leaf<T> leaf) {
    return false;
  }

  //represent the binary search tree of type T that consumes a list of type T 
  //and adds to it one at a time all items from this tree in the sorted order.
  IList<T> buildList(IList<T> list) {
    return this.getRight().buildList(new ConsList<T>(this.getLeftmost(), list));
  }
}

// to represent a list of t 
interface IList<T> {
  //insert the given tree at the end a binary search tree that contains all 
  //items in this list as well as the items already in the tree
  ABST<T> buildTree(ABST<T> t);

  //is this list the same as the given list 
  boolean sameList(IList<T> other, IComparator<T> c); 

  // is this list the same as the given ConsList
  boolean sameConsList(ConsList<T> other, IComparator<T> c);

  // is this list the same as the given MtList
  boolean sameMtList(MtList<T> other);

  // produce this list of strings, in a reversed order
  IList<T> reverse();

  // produce this list of strings in a reversed order, 
  //with the given string at the end of this list  
  IList<T> reverseHelper(IList<T> other);
}

// to represent an empty list of t 
class MtList<T> implements IList<T> {

  //insert the given tree at the end a binary search tree that contains all 
  //items in this list as well as the items already in the tree
  public ABST<T> buildTree(ABST<T> t) {
    return t;
  }

  //is this list the same as the given list 
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

  // produce this list of strings, in a reversed order
  public IList<T> reverse() {
    return this;
  }

  //produce this list of strings in a reversed order, 
  //with the given string at the end of this list  
  public IList<T> reverseHelper(IList<T> other) {
    return other;
  }
}

// to represent a cons list of t 
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  //insert the given tree at the end a binary search tree that contains all 
  //items in this list as well as the items already in the tree
  public ABST<T> buildTree(ABST<T> t) {
    return this.rest.buildTree(t.insert(this.first));
  }

  //is this list the same as the given list
  public boolean sameList(IList<T> other, IComparator<T> c) {
    return other.sameConsList(this, c);
  }

  // is this list the same as the given ConsList
  public boolean sameConsList(ConsList<T> other, IComparator<T> c) {
    return c.compare(this.first, other.first) == 0
        && this.rest.sameList(other.rest, c);
  }

  // is this list the same as the given MtList
  public boolean sameMtList(MtList<T> other) {
    return false;
  }

  //produce this list of strings, in a reversed order
  public IList<T> reverse() {
    return reverseHelper(new MtList<T>());
  }

  //produce this list of strings in a reversed order, 
  //with the given string at the end of this list 
  public IList<T> reverseHelper(IList<T> other) {
    return this.rest.reverseHelper(new ConsList<T>(this.first, other));
  }
}

class ExamplesABST {
  Book b1 = new Book("a", "d", 3);
  Book b2 = new Book("b", "c", 4);
  Book b3 = new Book("c", "b", 1);
  Book b4 = new Book("d", "a", 2);

  //bytitle (b1, b2, b3, b4)
  //byAuthor (b4, b3, b2, b1)
  //byPrice (b3, b4, b1, b2)

  ABST<Book> leaf = new Leaf<Book>(new BooksByTitle());

  ABST<Book> tree1left = new Node<Book>(new BooksByTitle(), b2, 
      new Node<Book>(new BooksByTitle(), b1, leaf, leaf),
      leaf);
  ABST<Book> tree1right = new Node<Book>(new BooksByTitle(), b4, leaf, leaf);
  ABST<Book> tree1 = new Node<Book>(new BooksByTitle(), b3, tree1left, tree1right);
  //   b3
  //  b2 b4
  // b1
  IList<Book> tree1List = new ConsList<Book>(b1, new ConsList<Book>(b2, 
      new ConsList<Book>(b3, new ConsList<Book>(b4, new MtList<Book>()))));
  IList<Book> tree1ListInverse = new ConsList<Book>(b4, new ConsList<Book>(b3, 
      new ConsList<Book>(b2, new ConsList<Book>(b1, new MtList<Book>()))));

  ABST<Book> tree1leftB = new Node<Book>(new BooksByTitle(), b2, 
      new Node<Book>(new BooksByTitle(), b1, leaf, leaf),
      leaf);
  ABST<Book> tree1rightB = new Node<Book>(new BooksByTitle(), b4, leaf, leaf);
  ABST<Book> treeB = new Node<Book>(new BooksByTitle(), b3, tree1leftB, tree1rightB);
  //   b3
  //  b2 b4
  // b1

  ABST<Book> tree1leftC = new Node<Book>(new BooksByTitle(), b1, leaf, leaf);
  ABST<Book> tree1rightC = new Node<Book>(new BooksByTitle(), b4, 
      new Node<Book>(new BooksByTitle(), b3, leaf, leaf), leaf);
  ABST<Book> treeC = new Node<Book>(new BooksByTitle(), b2, tree1leftC, tree1rightC);
  //   b2
  //  b1 b4
  //    b3

  ABST<Book> leafA = new Leaf<Book>(new BooksByAuthor());

  ABST<Book> tree1leftA = new Node<Book>(new BooksByAuthor(), b3, 
      new Node<Book>(new BooksByAuthor(), b4, leafA, leafA),
      leafA);
  ABST<Book> tree1rightA = new Node<Book>(new BooksByAuthor(), b1, leafA, leafA);
  ABST<Book> treeA = new Node<Book>(new BooksByAuthor(), b2, tree1leftA, tree1rightA);
  //   b2
  //  b3 b1
  // b4
  IList<Book> treeAList = new ConsList<Book>(b4, new ConsList<Book>(b3, 
      new ConsList<Book>(b2, new ConsList<Book>(b1, new MtList<Book>()))));

  ConsList<Book> tree2List = new ConsList<Book>(b1, new ConsList<Book>(b2, 
      new ConsList<Book>(b3, new ConsList<Book>(b4, new MtList<Book>()))));
  ConsList<Book> tree2ListInverse = new ConsList<Book>(b4, new ConsList<Book>(b3, 
      new ConsList<Book>(b2, new ConsList<Book>(b1, new MtList<Book>()))));

  // test the method compare 
  boolean testCompare(Tester t) {
    return t.checkExpect(new BooksByTitle().compare(b2, b1), 1)
        && t.checkExpect(new BooksByTitle().compare(b1, b1), 0)
        && t.checkExpect(new BooksByTitle().compare(b1, b2), -1)

        && t.checkExpect(new BooksByAuthor().compare(b2, b1), -1)
        && t.checkExpect(new BooksByAuthor().compare(b1, b1), 0)
        && t.checkExpect(new BooksByAuthor().compare(b1, b2), 1)

        && t.checkExpect(new BooksByPrice().compare(b2, b1), 1)
        && t.checkExpect(new BooksByPrice().compare(b1, b1), 0)
        && t.checkExpect(new BooksByPrice().compare(b1, b2), -1);
  }

  // test the method insert 
  void testInsert(Tester t) {
    ABST<Book> tree1left2 = new Node<Book>(new BooksByTitle(), b2, 
        new Node<Book>(new BooksByTitle(), b1, leaf, leaf),
        leaf);
    ABST<Book> tree1right2 = new Node<Book>(new BooksByTitle(), b4, 
        new Node<Book>(new BooksByTitle(), b3, leaf, leaf), leaf);
    ABST<Book> tree2 = new Node<Book>(new BooksByTitle(), b3, tree1left2, tree1right2);

    t.checkExpect(tree1.insert(b3), tree2);
    t.checkExpect(leaf.insert(b1), new Node<Book>(new BooksByTitle(), 
        b1, new Leaf<Book>(new BooksByTitle()), 
        new Leaf<Book>(new BooksByTitle())));


    ABST<Book> tree1leftA2 = new Node<Book>(new BooksByAuthor(), b3, 
        new Node<Book>(new BooksByAuthor(), b4, leafA, leafA),
        new Node<Book>(new BooksByAuthor(), b3, leafA, leafA));
    ABST<Book> tree1rightA2 = new Node<Book>(new BooksByAuthor(), b1, leafA, leafA);
    ABST<Book> treeA2 = new Node<Book>(new BooksByAuthor(), b2, tree1leftA2, tree1rightA2);

    t.checkExpect(treeA.insert(b3), treeA2);
    t.checkExpect(leafA.insert(b1), new Node<Book>(new BooksByAuthor(), 
        b1, new Leaf<Book>(new BooksByAuthor()), 
        new Leaf<Book>(new BooksByAuthor())));
  }

  // test the method getLeftMost
  void testGetLeftMost(Tester t) {
    t.checkExpect(tree1.getLeftmost(), b1);
    t.checkException(
        new RuntimeException("No leftmost item of an empty tree"),
        this.leaf, "getLeftmost");
    t.checkExpect(treeA.getLeftmost(), b4);
    t.checkException(
        new RuntimeException("No leftmost item of an empty tree"),
        this.leafA, "getLeftmost");
  }

  // test the method getLeftMostHelper
  void testGetLeftMostHelper(Tester t) {
    t.checkExpect(tree1.getLeftmostHelper(b1), b1);
    t.checkException(
        new RuntimeException("No leftmost item of an empty tree"),
        this.leaf, "getLeftmost");
    t.checkExpect(treeA.getLeftmostHelper(b2), b4);
    t.checkExpect(leaf.getLeftmostHelper(b2), b2);
  }

  // test the method getRight
  void testGetRight(Tester t) {
    ABST<Book> tree1left3 = new Node<Book>(new BooksByTitle(), b2, 
        leaf,
        leaf);
    ABST<Book> tree1right3 = new Node<Book>(new BooksByTitle(), b4, leaf, leaf);
    ABST<Book> tree3 = new Node<Book>(new BooksByTitle(), b3, tree1left3, tree1right3);
    t.checkExpect(tree1.getRight(), tree3 );
    t.checkException(
        new RuntimeException("No right of an empty tree"),
        this.leaf, "getRight");

    ABST<Book> tree1left3A = new Node<Book>(new BooksByAuthor(), b3, 
        leafA,
        leafA);
    ABST<Book> tree1right3A = new Node<Book>(new BooksByAuthor(), b1, leafA, leafA);
    ABST<Book> tree3A = new Node<Book>(new BooksByAuthor(), b2, tree1left3A, tree1right3A);
    t.checkExpect(treeA.getRight(), tree3A);
    t.checkException(
        new RuntimeException("No right of an empty tree"),
        this.leafA, "getRight");
  }

  //test the method sameTree
  void testSameTree(Tester t) {
    t.checkExpect(tree1.sameTree(treeB), true);
    t.checkExpect(tree1.sameTree(treeC), false);
    t.checkExpect(leaf.sameTree(tree1), false);
    t.checkExpect(treeA.sameTree(treeA), true);
    t.checkExpect(treeA.sameTree(tree1), false);
    t.checkExpect(leaf.sameTree(treeA), false);
  }

  //test the method sameData
  void testSameData(Tester t) {
    t.checkExpect(treeC.sameData(treeB), true);
    t.checkExpect(treeC.sameData(tree1), true);
    t.checkExpect(treeA.sameData(treeC), false);
    t.checkExpect(treeA.sameData(treeA), true);
    t.checkExpect(leaf.sameData(leaf), true);
    t.checkExpect(leaf.sameData(tree1), false);
  }

  //test the method sameAsList
  void testSameAsList(Tester t) {
    t.checkExpect(tree1.sameAsList(tree1List), true);
    t.checkExpect(tree1.sameAsList(tree1ListInverse), false);
    t.checkExpect(treeC.sameAsList(tree1List), true);
    t.checkExpect(leaf.sameAsList(tree1List), false);
    t.checkExpect(leaf.sameAsList(new MtList<Book>()), true);
  }

  //test the method buildList
  void testBuildList(Tester t) {
    t.checkExpect(tree1.buildList(new MtList<Book>()), tree1ListInverse);
    t.checkExpect(treeC.buildList(new MtList<Book>()), tree1ListInverse);
  }

  //test the method buildTree 
  void testBuildTree(Tester t) {
    t.checkExpect(tree1List.buildTree(leaf).buildList(new MtList<Book>()), 
        tree1ListInverse);

    IList<Book> List = new ConsList<Book>(b3, new ConsList<Book>(b2, 
        new ConsList<Book>(b1, new ConsList<Book>(b4, new MtList<Book>()))));
    t.checkExpect(List.buildTree(leaf), tree1);

  }

  //test the method sameConsList
  boolean testSameConsList(Tester t) {
    MtList<Book> mtLoBook = new MtList<Book>();
    return t.checkExpect(this.tree2List.sameConsList(this.tree2ListInverse, 
        new BooksByTitle()), false)
        && t.checkExpect(this.tree2List.sameConsList(this.tree2List, 
            new BooksByTitle()), true)
        && t.checkExpect(mtLoBook.sameConsList(this.tree2List, 
            new BooksByTitle()), false);
  }

  // test the method sameMtList
  boolean testSameMtList(Tester t) {
    MtList<Book> mtLoBook = new MtList<Book>();
    return t.checkExpect(this.tree1List.sameMtList(mtLoBook), false)
        && t.checkExpect(mtLoBook.sameMtList(mtLoBook), true);
  }

  // test the method sameList
  boolean testSameList(Tester t) {
    MtList<Book> mtLoBook = new MtList<Book>();
    return t.checkExpect(mtLoBook.sameList(mtLoBook, new BooksByTitle()), true)
        && t.checkExpect(this.tree1List.sameList(this.tree1List, new BooksByTitle()), true)
        && t.checkExpect(this.tree1List.sameList(this.tree1ListInverse, new BooksByTitle()), false)
        && t.checkExpect(mtLoBook.sameList(this.tree1List, new BooksByTitle()),false);
  }

  //test the method reverse
  void testReverse(Tester t) {
    IList<Book> mtLoBook = new MtList<Book>();
    t.checkExpect(this.tree1List.reverse(), this.tree1ListInverse);
    t.checkExpect(mtLoBook.reverse(), mtLoBook);
  }

  //test the method reverseHelper
  void testReverseHelper(Tester t) {
    IList<Book> mtLoBook = new MtList<Book>();
    t.checkExpect(this.tree1List.reverseHelper(mtLoBook), this.tree1ListInverse);
    t.checkExpect(mtLoBook.reverseHelper(this.tree1List), this.tree1List);
  }
}


