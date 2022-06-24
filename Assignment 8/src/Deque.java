import tester.Tester;

// Represents either a sentinel or a actual node containing data 
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // constructor 
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  // an empty constructor that take no arguments and no fields 
  ANode() {}

  // counts the number of nodes in a list Deque, not including 
  // header node and sentinel 
  abstract int size();

  // EFFECT: inserts the given value t into the list 
  public void add(T t, ANode<T> a1, ANode<T> a2) {
    new Node<T>(t, a1, a2);
  }

  // EFFECT: inserts it at the front of the list
  public void addAtHead(T t) {
    add(t, this, this.prev);
  }

  // EFFECT: consumes a value of type T and inserts it at the tail of this list
  public void addAtTail(T t) {
    add(t, this.next, this);
  }

  // EFFECT: remove a node from this list 
  abstract T removeHelper();

  // EFFECT: removes the first node from this list
  // return the data of the node that is removed, throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  abstract T removeFromHead();

  // EFFECT: removes the last node from this list
  // return the data of the node that is removed, throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  abstract T removeFromTail();

  // takes an IPred<T> and produces the first node in this list 
  // for which the given predicate returns true
  abstract ANode<T> find(IPred<T> pred);

  // EFFECT: removes the given node from this Deque.
  abstract void removeNode(ANode<T> that);

}

// to represent a node containing the data
class Node<T> extends ANode<T> {
  T data;

  // constructor that initialize the next data and previous data 
  // to null 
  Node(T data) {
    super(null, null);
    this.data = data;
  }

  // constructor that initialize the next date and previous data
  // to the given nodes
  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    this.data = data;

    // if the next node and previous node are null, throw exception 
    // else update the given nodes to refer back to this node
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Given node is null");
    }
    else {
      next.prev = this;
      prev.next = this;
    }
  }

  //counts the number of nodes in a list Deque, not including 
  // header node and sentinel 
  int size() {
    return 1 + next.size();
  }

  //EFFECT: remove a node from this list 
  //return the data of the node that is removed
  T removeHelper() {
    this.next.prev = this.prev;
    this.prev.next = this.next;
    return this.data;
  }

  //EFFECT: removes the first node from this list
  // throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  T removeFromHead() {
    return removeHelper();
  }

  //EFFECT: removes the last node from this list
  // throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  T removeFromTail() {
    return removeHelper();
  }

  //takes an IPred<T> and produces the first node in this list 
  // for which the given predicate returns true
  ANode<T> find(IPred<T> pred) {
    if (pred.apply(data)) {
      return this;
    }
    else {
      return this.next.find(pred);
    }
  }

  //EFFECT: removes the given node from this Deque.
  void removeNode(ANode<T> that) {
    if (this == that) {
      removeHelper();
    }
    else {
      this.next.removeNode(that);
    }
  }
}

// to represent a sentinel that also have the next and previous node 
class Sentinel<T> extends ANode<T> {

  // constructor 
  Sentinel(ANode<T> next, ANode<T> prev) {
    super(next, prev);
  }

  // constructor that initializes the next and prev 
  // fields back to this sentinel 
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  //counts the number of nodes in a list Deque, not including 
  // header node and sentinel 
  int size() {
    return 0;
  }

  //EFFECT: remove a node from this list 
  T removeHelper() {
    throw new RuntimeException("Cannot remove from an empty list");
  }

  //EFFECT: removes the first node from this list, throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  T removeFromHead() {
    return removeHelper();
  }

  //EFFECT: removes the last node from this list, throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  T removeFromTail() {
    return removeHelper();
  }

  //takes an IPred<T> and produces the first node in this list 
  // for which the given predicate returns true
  ANode<T> find(IPred<T> pred) {
    return this;
  }

  //EFFECT: removes the given node from this Deque.
  void removeNode(ANode<T> that) {
    return;
  }
}

// to represent a Deque (list of <T>)
class Deque<T> {
  Sentinel<T> header;

  // constructor 
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // constructor that initialize the header to a new sentinel
  Deque() {
    this.header = new Sentinel<T>();
  }

  //counts the number of nodes in a list Deque, not including 
  // header node and sentinel 
  int size() {
    return this.header.next.size();
  }

  //EFFECT: consumes a value of type T and inserts it at the front of the list
  void addAtHead(T t) {
    this.header.next.addAtHead(t);
  }

  //EFFECT: consumes a value of type T and inserts it at the tail of this list
  void addAtTail(T t) {
    this.header.prev.addAtTail(t);
  }

  //removes the first node from this list, throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  T removeFromHead() {
    return this.header.next.removeFromHead();
  }

  //removes the last node from this list, throw a RuntimeException 
  // if an attempt is made to remove from an empty list. 
  T removeFromTail() {
    return this.header.prev.removeFromTail();
  }

  //takes an IPred<T> and produces the first node in this list 
  // for which the given predicate returns true
  ANode<T> find(IPred<T> pred) {
    return this.header.next.find(pred);
  }

  //EFFECT: removes the given node from this Deque.
  void removeNode(ANode<T> that) {
    this.header.next.removeNode(that);
  }
}

//Represents a boolean-valued question over values of type T
interface IPred<T> {
  // represents a boolean-valued question over values of type T
  boolean apply(T t);
}

// Represents a function object testing whether the given string's 
// length is longer than 2; 
class StringLengthOver2 implements IPred<String> {
  // is the given string's length longer than 2?
  public boolean apply(String s) {
    return s.length() > 2;
  }
}

// Examples and Tests 
class ExamplesDeque {
  Deque<String> deque1 = new Deque<String>();
  Sentinel<String> sentinel = new Sentinel<String>();

  Sentinel<String> sentinel1 = new Sentinel<String>();
  Node<String> node1 = new Node<String>("abc", sentinel1, sentinel1);
  Node<String> node2 = new Node<String>("bcd", sentinel1, node1);
  Node<String> node3 = new Node<String>("cde", sentinel1, node2);
  Node<String> node4 = new Node<String>("def", sentinel1, node3);

  Deque<String> deque2 = new Deque<String>(sentinel1);

  Sentinel<String> sentinel2 = new Sentinel<String>();
  Node<String> node5 = new Node<String>("b", sentinel2, sentinel2);
  Node<String> node6 = new Node<String>("ad", sentinel2, node5);
  Node<String> node7 = new Node<String>("dbc", sentinel2, node6);
  Node<String> node8 = new Node<String>("c", sentinel2, node7);
  Node<String> node9 = new Node<String>("aac", sentinel2, node8);

  Deque<String> deque3 = new Deque<String>(sentinel2);

  // to represent the initial data 
  void init() {
    deque1 = new Deque<String>();
    sentinel = new Sentinel<String>();

    sentinel1 = new Sentinel<String>();
    node1 = new Node<String>("abc", sentinel1, sentinel1);
    node2 = new Node<String>("bcd", sentinel1, node1);
    node3 = new Node<String>("cde", sentinel1, node2);
    node4 = new Node<String>("def", sentinel1, node3);
    deque2 = new Deque<String>(sentinel1);

    sentinel2 = new Sentinel<String>();
    node5 = new Node<String>("b", sentinel2, sentinel2);
    node6 = new Node<String>("ad", sentinel2, node5);
    node7 = new Node<String>("dbc", sentinel2, node6);
    node8 = new Node<String>("c", sentinel2, node7);
    node9 = new Node<String>("aac", sentinel2, node8);
    deque3 = new Deque<String>(sentinel2);
  }

  // test the method size 
  void testSize(Tester t) {
    init();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque3.size(), 5);
    t.checkExpect(node3.size(), 2);
    t.checkExpect(node4.size(), 1);
    t.checkExpect(sentinel1.size(), 0);
  }

  // test the method add
  void testAdd(Tester t) {
    init();
    // add a string at the front of the list 
    node5.add("a", node5, node5.prev);
    sentinel.add("a", sentinel, sentinel.prev);
    // check whether the string is added to the list 
    t.checkExpect(node5.size(), 5);
    t.checkExpect(node5.prev, 
        new Node<String>("a", node5, sentinel2));
    t.checkExpect(sentinel.size(), 0);
    t.checkExpect(sentinel.prev, 
        new Node<String>("a", sentinel, sentinel));
  }

  // test the method addAtHead 
  void testAddAtHead(Tester t) {
    // in class deque 
    // initial condition 
    init();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque1.header.next, sentinel);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque2.header.next, node1);
    // add a string to the given deque 
    deque1.addAtHead("a");
    deque2.addAtHead("a");
    // check whether the string is added to the list 
    t.checkExpect(deque1.size(), 1);
    t.checkExpect(deque1.header.next, 
        new Node<String>("a", sentinel, sentinel));
    t.checkExpect(deque2.size(), 5);
    t.checkExpect(deque2.header.next, 
        new Node<String>("a", node1, sentinel1));

    // in class Node && Sentinel 
    init();
    t.checkExpect(node1.size(), 4);
    t.checkExpect(node1.next, node2);
    t.checkExpect(sentinel.size(), 0);
    t.checkExpect(sentinel.next, sentinel);
    // add a string to the given sentinel or node 
    node1.addAtHead("a");
    sentinel.addAtHead("a");
    // check whether the string is added to the list 
    t.checkExpect(node1.size(), 4);
    t.checkExpect(node1.prev, 
        new Node<String>("a", node1, sentinel1));
    t.checkExpect(sentinel.size(), 0);
    t.checkExpect(sentinel.prev, 
        new Node<String>("a", sentinel, sentinel));
  }

  //test the method addAtTail 
  void testAddAtTail(Tester t) {
    // in class deque 
    // initial condition 
    init();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque1.header.next, sentinel);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque2.header.next, node1);
    // add a string to the end of given deque 
    deque1.addAtTail("a");
    deque2.addAtTail("a");
    // check whether the string is added to the list 
    t.checkExpect(deque1.size(), 1);
    t.checkExpect(deque1.header.prev, 
        new Node<String>("a", sentinel, sentinel));
    t.checkExpect(deque2.size(), 5);
    t.checkExpect(deque2.header.prev, 
        new Node<String>("a", sentinel1, node4));

    // in class Node && Sentinel 
    init();
    t.checkExpect(node4.size(), 1);
    t.checkExpect(node4.next, sentinel1);
    t.checkExpect(sentinel.size(), 0);
    t.checkExpect(sentinel.next, sentinel);
    // add a string to the end of given sentinel or node 
    node4.addAtTail("a");
    sentinel.addAtTail("a");
    // check whether the string is added to the list 
    t.checkExpect(node4.size(), 2);
    t.checkExpect(node4.next, 
        new Node<String>("a", sentinel1, node4));
    t.checkExpect(sentinel.size(), 0);
    t.checkExpect(sentinel.next, 
        new Node<String>("a", sentinel, sentinel));
  }

  // test the method removeHelper
  void testRemoveHelper(Tester t) {
    init();
    t.checkExpect(node1.removeHelper(), "abc");
    t.checkException(new RuntimeException("Cannot remove from an empty list"),
        this.sentinel, "removeHelper");
  }

  // test the method removeFromHead 
  void testRemoveFromHead(Tester t) {
    init();
    // in class deque 
    // remove the first node from this list 
    deque2.removeFromHead();
    // check whether the string is removed from the list 
    t.checkException(new RuntimeException("Cannot remove from an empty list"),
        this.deque1, "removeFromHead");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(deque2.header.next, node2);
    t.checkExpect(deque2.removeFromHead(), "bcd");

    // in class Node && Sentinel 
    init();
    // remove a node from the first of the given sentinel or node 
    node1.removeFromHead();
    // check whether the node is removed from the list 
    t.checkException(new RuntimeException("Cannot remove from an empty list"),
        this.sentinel, "removeFromHead");
    t.checkExpect(node2.prev, sentinel1);
    t.checkExpect(node5.removeFromHead(), "b");
  }

  // test the method removeFromTail
  void testRemoveFromTail(Tester t) {
    init();
    // in class deque 
    // remove the last node from this list 
    deque2.removeFromTail();
    // check whether the string is removed from the list 
    t.checkException(new RuntimeException("Cannot remove from an empty list"),
        this.deque1, "removeFromTail");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(deque2.header.prev, node3);
    t.checkExpect(deque3.removeFromTail(), "aac");

    // in class Node && Sentinel 
    init();
    // remove a node from the first of the given sentinel or node 
    node4.removeFromTail();
    // check whether the node is removed from the list 
    t.checkException(new RuntimeException("Cannot remove from an empty list"),
        this.sentinel, "removeFromTail");
    t.checkExpect(node3.next, sentinel1);
    t.checkExpect(node9.removeFromTail(), "aac");
  }

  // test the method find 
  void testFind(Tester t) {
    init();
    // in class deque 
    // find the node that has the string's length longer than 2
    t.checkExpect(deque1.find(new StringLengthOver2()), sentinel);
    t.checkExpect(deque2.find(new StringLengthOver2()), node1);
    t.checkExpect(deque3.find(new StringLengthOver2()), node7);

    // in class Node && Sentinel 
    // find the node that had the string's length longer than 2
    init();
    t.checkExpect(node1.find(new StringLengthOver2()), node1);
    t.checkExpect(node5.find(new StringLengthOver2()), node7);
    t.checkExpect(sentinel.find(new StringLengthOver2()), sentinel);
  }

  // test the method removeNode 
  void testRemoveNode(Tester t) {
    init();
    // in class deque 
    // remove the given node from this deque 
    deque2.removeNode(node4);
    t.checkExpect(deque2.header.prev, node3);

    // nothing will happen if we remove a node that does exsit in this deque 
    init();
    deque1.removeNode(node1);
    deque2.removeNode(node6);
    t.checkExpect(deque1.header.next, sentinel);
    t.checkExpect(deque1.header.prev, sentinel);
    t.checkExpect(deque2.header, sentinel1);

    // in class node and sentinel 
    init();
    node1.removeNode(node8);
    sentinel.removeNode(node1);
    t.checkExpect(node1.next, node2);
    t.checkExpect(sentinel.next, sentinel);

    init();
    // check whether the node is removed from this list
    node1.removeNode(node3);
    t.checkExpect(node1.next.next, node4);
  }

  // test the method apply 
  void testApply(Tester t) {
    t.checkExpect(new StringLengthOver2().apply("a"), false);
    t.checkExpect(new StringLengthOver2().apply("aab"), true);
  }
}