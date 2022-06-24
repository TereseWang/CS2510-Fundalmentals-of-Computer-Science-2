import tester.Tester;

// to represent a Polynomial
class Polynomial {
  ILoMonomial monomials;

  // constructor
  // if any of the monomials given to this polynomial have 
  // the same degree, it should not be constructed
  Polynomial(ILoMonomial monomials) {
    if (monomials.checkDegree()) {
      this.monomials = monomials;
    }
    else {
      throw new IllegalArgumentException("Invalid polynomial");
    }
  }

  // determines if this polynomial is the same as the given one 
  public boolean samePolynomial(Polynomial p) {
    /* Templates 
     * Fields:
     * ... this.monomials ... -- ILoMonomial
     * Methods:
     * ... this.samePolynomial(Polynomial) ... --boolean
     */
    return this.monomials.contain(p.monomials)
        && p.monomials.contain(this.monomials);
  }
}

// to represent ILoMonomial
interface ILoMonomial {
  // does any of the monomials in this ILoMonomial have the same degrees?
  boolean checkDegree();
  
  // is the given monomial have the different degree with any monomials 
  // in this list of monomials?
  boolean checkDegreeHelper(Monomial m);
  
  // is the given ILoMonomial contain this ILoMonomial?
  boolean contain(ILoMonomial m);
  
  // is this ILoMonomial contain the given monomial?
  boolean containHelper(Monomial m);

}

// to represent an empty list of monomials 
class MtLoMonomial implements ILoMonomial {
  MtLoMonomial() {}

  //does any of the monomials in this empty list not have the same degrees?
  public boolean checkDegree() {
    return true;
  }

  // is the given monomial have no same degree with any monomials 
  // in this list of monomials?
  public boolean checkDegreeHelper(Monomial m) {
    return true;
  }

  //is the given ILoMonomial contain this empty list?
  public boolean contain(ILoMonomial m) {
    return true;
  }

  //is this empty list contain the given monomial?
  public boolean containHelper(Monomial m) {
    return false;
  }
}

// to represent a list of monomials 
class ConsLoMonomial implements ILoMonomial {
  Monomial first;
  ILoMonomial rest;

  //constructor 
  ConsLoMonomial(Monomial first, ILoMonomial rest) {
    this.first = first;
    this.rest = rest;
  }

  //does any of the monomials in this ILoMonomial have no same degrees?
  public boolean checkDegree() {
    /* Templates:
     * Fields:
     * ... this.first ...   -- Monomial 
     * ... this.rest ...    -- ILoMonomial 
     * Methods:
     * ... this.checkDegree() ...    -- boolean 
     * ... this.checkDegreeHelper(Monomial) ... --boolean 
     * ... this.contain(ILoMonomial) ...   --boolean
     * ... this.containHelper(Monomial) ... --boolean 
     * Fields of Methods:
     * ... this.rest.checkDegree() ...    -- boolean 
     * ... this.rest.checkDegreeHelper(Monomial) ... --boolean 
     * ... this.rest.contain(ILoMonomial) ...   --boolean
     * ... this.rest.containHelper(Monomial) ... --boolean 
     * Parameters of Methods:
     * ... other.containHelper(Monomial) ...   --boolean 
     */
    return this.rest.checkDegreeHelper(this.first);
  }

  // is the given monomial have no same degree with any monomials 
  // in this list of monomials?
  public boolean checkDegreeHelper(Monomial other) {
    return !(this.first.checkDegree(other))
        && this.rest.checkDegreeHelper(other)
        && this.rest.checkDegreeHelper(this.first);
  }

  //is the given ILoMonomial contain this list of Monomials?
  public boolean contain(ILoMonomial other) {
    return (other.containHelper(this.first) 
        || this.first.coefficient == 0)
        && this.rest.contain(other);
  }

  //is this ILoMonomial contain the given Monomial?
  public boolean containHelper(Monomial other) {
    return this.first.checkMonomial(other) 
        || this.rest.containHelper(other);
  }
}

// to represent a monomial 
class Monomial { 
  int degree;
  int coefficient;

  // constructor
  // the degree of this monomial cannot be negative
  // else it will not be constructed
  Monomial(int degree, int coefficient) {
    this.coefficient = coefficient;
    if (degree >= 0) {
      this.degree = degree;
    }
    else {
      throw new IllegalArgumentException("Invalid degree: " + degree);
    }
  }
  
  // is this Monomial and other Monomial have the same degree?
  boolean checkDegree(Monomial other) {
    /* Templates 
     * Fields:
     * ... this.degree ... -- int 
     * ... this.coefficent ... -- int 
     * Methods:
     * ... this.checkDegree(Monomial) ... --boolean
     * ... this.checkMonomail(Monomial) ... --boolean 
     * Fields of parameters:
     * ... other.degree ... -- int 
     * ... other.coefficient ... -- int 
     */
    return this.degree == other.degree;
  }
  
  // is this Monomial the same of other Monomial?
  boolean checkMonomial(Monomial other) {
    return this.degree == other.degree
        && this.coefficient == other.coefficient;
  }
}

class ExamplesMonomials {
  ExamplesMonomials() {}
  
  Monomial m1 = new Monomial(0,5);
  Monomial m2 = new Monomial(2,3);
  Monomial m3 = new Monomial(1,0);
  Monomial m4 = new Monomial(1,3);
  Monomial m5 = new Monomial(2,0);
  Monomial m6 = new Monomial(3,5);

  ILoMonomial lom = new MtLoMonomial();
  ILoMonomial lom1 = new ConsLoMonomial(this.m1, this.lom);
  ILoMonomial lom2 = new ConsLoMonomial(m1, 
      new ConsLoMonomial(m2, 
          new ConsLoMonomial(m3,this.lom)));
  ILoMonomial lom3 = new ConsLoMonomial(m2, new ConsLoMonomial(m1, this.lom));
  ILoMonomial lom4 = new ConsLoMonomial(m4, new ConsLoMonomial(m6, this.lom));
  ILoMonomial lom5 = new ConsLoMonomial(m1, new ConsLoMonomial(m2, this.lom));

  Polynomial p1 = new Polynomial(this.lom1);
  Polynomial p2 = new Polynomial(this.lom2);
  Polynomial p3 = new Polynomial(this.lom3);
  Polynomial p4 = new Polynomial(this.lom4);
  Polynomial p5 = new Polynomial(this.lom5);
  

  // test InValid Monomials (exceptions)
  boolean testInValidDegree(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Invalid degree: -3"), "Monomial", -3, 0);
  }

  // test Invalid Polynomials (exceptions)
  boolean testInValidPolynomial(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Invalid polynomial"), "Polynomial", 
        new ConsLoMonomial(new Monomial(1, 3), 
            new ConsLoMonomial(new Monomial(1, 0), new MtLoMonomial())))
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid polynomial"), "Polynomial", 
            new ConsLoMonomial(new Monomial(1, 3), 
                new ConsLoMonomial(new Monomial(2, 0),
                    new ConsLoMonomial(new Monomial(1, 4), new MtLoMonomial()))));
  }
  
  // test the method samePolynomial in the class Polynomial
  boolean testSamePolynomial(Tester t) {
    return t.checkExpect(this.p3.samePolynomial(p2), true)
        && t.checkExpect(this.p2.samePolynomial(p3), true)
        && t.checkExpect(this.p2.samePolynomial(p4), false)
        && t.checkExpect(this.p3.samePolynomial(p5), true);
  }

  // test the method checkMonomial in the class Monomials
  boolean testCheckMonomial(Tester t) {
    return t.checkExpect(this.m1.checkMonomial(m1), true)
        && t.checkExpect(this.m1.checkMonomial(m2), false);
  }
  
  // test the method checkDegree in the class Monomials and ILoMonomial
  boolean testCheckDegree(Tester t) {
    return t.checkExpect(this.lom2.checkDegree(), true)
        && t.checkExpect(this.lom.checkDegree(), true)
        && t.checkExpect(this.m3.checkDegree(m3), true)
        && t.checkExpect(this.m1.checkDegree(m4), false);
  }
  
  // test the method checkDegreeHelper in the class ILoMonomials
  boolean testCheckDegreeHelper(Tester t) {
    return t.checkExpect(this.lom1.checkDegreeHelper(m1), false)
        && t.checkExpect(this.lom.checkDegreeHelper(m1), true)
        && t.checkExpect(this.lom3.checkDegreeHelper(m3), true);
  }
  
  // test the method containHelper in the class ILoMonomials
  boolean testContainHelper(Tester t) {
    return t.checkExpect(this.lom1.containHelper(m1), true)
        && t.checkExpect(this.lom4.containHelper(m5), false)
        && t.checkExpect(this.lom.containHelper(m1), false);
  }
  
  // test the method contain in the class ILoMonomials 
  boolean testContain(Tester t) {
    return t.checkExpect(this.lom1.contain(lom), false)
        && t.checkExpect(this.lom3.contain(lom2), true)
        && t.checkExpect(this.lom.contain(lom1), true);
  }
}