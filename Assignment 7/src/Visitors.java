import tester.Tester;

//represent an arithmetic
interface IArith {
  //representing a visitor that visits an IArith and produces a result of type R.
  <R> R accept(IArithVisitor<R> visitor);
}

//represent a constant
class Const implements IArith {
  double num;

  //constructor
  Const(double num) {
    this.num = num;
  }

  //representing a visitor that visits an IArith and produces a result of type R
  //to return the result of applying the given visitor to this Const
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

//represent a formula
class Formula implements IArith {
  IFunc2<Double, Double, Double> fun;
  String name;
  IArith left;
  IArith right;  

  //constructor 
  Formula(IFunc2<Double, Double, Double> fun, 
      String name, IArith left, IArith right) {
    this.fun = fun;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  //representing a visitor that visits an IArith and produces a result of type R
  //to return the result of applying the given visitor to this Formula
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }
}

//Represents functions of signature A -> R, for some argument type A and
//result type R
interface IFunc<A, R> {
  // input some argument of type A and result type R
  R apply(A input);
}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
  // input two argument of type A and result type R
  R apply(A1 a1, A2 a2);
}

//Represents a function object defined over IArith that returns a R
interface IArithVisitor<R> extends IFunc<IArith, R> {
  // input a constant, returning a type R
  R visitConst(Const c);

  // input a formula, returning a type R
  R visitFormula(Formula f);
}

//A function object that computes the result of the arithmetic
class EvalVisitor implements IArithVisitor<Double> {

  // compute the result of a constant 
  public Double visitConst(Const c) {
    return c.num;
  }

  // compute the result of a formula 
  public Double visitFormula(Formula f) {
    return f.fun.apply(f.left.accept(this), f.right.accept(this));
  }

  // compute the result of an arithmetic 
  public Double apply(IArith s) {
    return s.accept(this);
  }
}

//A function object that produces a String showing the 
//fully-parenthesized expression in Racket-like prefix notation
class PrintVisitor implements IArithVisitor<String> {

  //produces a String showing the fully-parenthesized 
  //expression in Racket-like prefix notation of the given constant 
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  //produces a String showing the fully-parenthesized 
  //expression in Racket-like prefix notation of the given formula
  public String visitFormula(Formula f) {
    return "(" + f.name + " " 
        + f.left.accept(this) 
        + " " + f.right.accept(this) + ")";
  }

  //produces a String showing the fully-parenthesized 
  //expression in Racket-like prefix notation of the given arithmetic
  public String apply(IArith s) {
    return s.accept(this);
  }
}

//A function object that produces another IArith, 
//where every Const in the tree has been doubled
class DoublerVisitor implements IArithVisitor<IArith> {
  // double the given constant 
  public IArith visitConst(Const c) {
    return new Const(c.num * 2);
  }

  //double the constants of the given formula 
  public IArith visitFormula(Formula f) {
    return new Formula(f.fun, f.name, f.left.accept(this), f.right.accept(this));
  }

  //double the constants of the given arithmetic 
  public IArith apply(IArith s) {
    return s.accept(this);
  }
}

//A function object that produces a Boolean that is true if
//every constant in the tree is less than 10
class AllSmallVisitor implements IArithVisitor<Boolean> {

  // is the given constant less than 10
  public Boolean visitConst(Const c) {
    return c.num < 10;
  }

  // is every constants of the given formula less than 10
  public Boolean visitFormula(Formula f) {
    return f.left.accept(this) && f.right.accept(this);
  }

  // is every constants of the given arithmetic less than 10
  public Boolean apply(IArith s) {
    return s.accept(this);
  }
}

//A function object that produces a Boolean that is true 
//if anywhere there is a Formula named "div",
//the right argument does not evaluate to roughly zero
class NoDivBy0 implements IArithVisitor<Boolean> {
  //produces a Boolean that is true if anywhere there 
  //is a Formula named "div", the right argument does 
  //not evaluate to roughly zero
  public Boolean visitConst(Const c) {
    return true;
  }

  //produces a Boolean that is true if anywhere there 
  //is a Formula named "div", the right argument does 
  //not evaluate to roughly zero
  public Boolean visitFormula(Formula f) {
    if (f.name.equals("div")) {
      return Math.abs(new EvalVisitor().apply(f.right)) > 0.0001
          && f.left.accept(this) && f.right.accept(this);
    }
    else {
      return f.left.accept(this) && f.right.accept(this);
    }
  }

  //produces a Boolean that is true if anywhere there 
  //is a Formula named "div", the right argument does 
  //not evaluate to roughly zero
  public Boolean apply(IArith s) {
    return s.accept(this);
  }
}

// to represent a function object do the addition of 
// two double numbers 
class Plus implements IFunc2<Double, Double, Double> {
  // add the given two double numbers together 
  public Double apply(Double a1, Double a2) {
    return a1 + a2;
  }
}

// to represent a function object do the division of 
// two doubles 
class Div implements IFunc2<Double, Double, Double> {
  // divide the given two double numbers 
  public Double apply(Double a1, Double a2) {
    return a1 / a2;
  }
}

// examples and tests 
class ExampleVisitor {
  IArith c0 = new Const(0.0);
  IArith c1 = new Const(0.005);
  IArith c2 = new Const(-0.003);
  IArith c1D = new Const(0.010);
  IArith c2D = new Const(-0.006);
  IArith c3 = new Const(4.0);

  //0.005 + -0.003
  IArith f1 = new Formula(new Plus(), "plus", c1, c2);

  //0.010 + -0.006
  IArith f1D = new Formula(new Plus(), "plus", c1D, c2D);

  //20 / 4
  IArith f2 = new Formula(new Div(), "div", new Const(20), new Const(4));

  //(0.005 + -0.003) / 0.0
  IArith fD0 = new Formula(new Div(), "div", f1, c0);

  //(0.005 + -0.003) + 4
  IArith f3 = new Formula(new Plus(), "plus", f1, c3);

  // test the method apply 
  void testApply(Tester t) {
    t.checkInexact(new EvalVisitor().apply(c1), 0.005, 0.001);
    t.checkExpect(new PrintVisitor().apply(f1).equals(
        "(plus 0.005 -0.003)"), true);
    t.checkExpect(new Div().apply(4.0, 2.0), 2.0);
    t.checkExpect(new Plus().apply(4.0, 2.0), 6.0);
  }

  // test the method accept 
  void testAccept(Tester t) {
    t.checkInexact(this.f1.accept(new EvalVisitor()), 0.002, 0.001);
    t.checkExpect(this.f1.accept(new PrintVisitor()).equals(
        "(plus 0.005 -0.003)"), true);
    t.checkExpect(this.f1.accept(new DoublerVisitor()), f1D);
    t.checkExpect(this.f1.accept(new AllSmallVisitor()), true);
    t.checkExpect(this.f1.accept(new NoDivBy0()), true);
  }

  // test the method visitConst 
  void testVisitConst(Tester t) {
    t.checkInexact(new EvalVisitor().visitConst((Const) c1), 0.005, 0.001);
    t.checkExpect(new PrintVisitor().visitConst((Const) c1).equals( "0.005"), true);
    t.checkExpect(new DoublerVisitor().visitConst((Const) c1), new Const(0.01));
    t.checkExpect(new AllSmallVisitor().visitConst((Const) c1), true);
    t.checkExpect(new NoDivBy0().visitConst((Const) c1), true);
  }

  // test the method visitFormula
  void testVisitFormula(Tester t) {
    t.checkInexact(new EvalVisitor().visitFormula((Formula) f1), 0.002, 0.001);
    t.checkExpect(new PrintVisitor().visitFormula((Formula) f1).equals(
        "(plus 0.005 -0.003)"), true);
    t.checkExpect(new DoublerVisitor().visitFormula((Formula) f1), f1D);
    t.checkExpect(new AllSmallVisitor().visitFormula((Formula) f1), true);
    t.checkExpect(new NoDivBy0().visitFormula((Formula) f1), true);
  }

  // test the function object of EvalVisitor 
  void testEvalVisitor(Tester t) {
    t.checkInexact(new EvalVisitor().apply(f1), 0.002, 0.001);
    t.checkInexact(new EvalVisitor().apply(f1D), 0.004, 0.001);
    t.checkInexact(new EvalVisitor().apply(f3), 4.002, 0.001);
  }

  // test the function object of PrintVisitor 
  void testPrintVisitor(Tester t) {
    t.checkExpect(new PrintVisitor().apply(f1).equals(
        "(plus 0.005 -0.003)"), true);
    t.checkExpect(new PrintVisitor().apply(f3).equals(
        "(plus (plus 0.005 -0.003) 4.0)"), true);

  }

  // test the function object of class DoublerVisitor 
  void testDoublerVisitor(Tester t) {
    t.checkExpect(new DoublerVisitor().apply(f1), f1D);
    t.checkExpect(new DoublerVisitor().apply(f3), new Formula(new Plus(), "plus", 
        new Formula(new Plus(), "plus", c1D, c2D),
        new Const(8.0)));
  }

  // test the function object of class AllSmallVisitor
  void testAllSmallVisitor(Tester t) {
    t.checkExpect(new AllSmallVisitor().apply(f1), true);
    t.checkExpect(new AllSmallVisitor().apply(f2), false);
  }

  // test the function object of class NoDivBy0
  void testNoDivBy0(Tester t) {
    t.checkExpect(new NoDivBy0().apply(fD0), false);
    t.checkExpect(new NoDivBy0().apply(f2), true);
  }

  //test the function object of class Div
  void testDiv(Tester t) {
    t.checkInexact(new Div().apply(6.0, 2.0), 3.0, 0.001);
    t.checkInexact(new Div().apply(3.0, 2.0), 1.5, 0.001);
  }

  //test the function object of class Plus 
  void testPlus(Tester t) {
    t.checkInexact(new Plus().apply(6.0, 2.0), 8.0, 0.001);
    t.checkInexact(new Plus().apply(3.0, 2.0), 5.0, 0.001);
  }
}