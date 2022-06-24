import tester.Tester;

interface IEntertainment {
  //compute the total price of this Entertainment
  public double totalPrice();

  //computes the minutes of entertainment of this IEntertainment
  public int duration();

  //produce a String that shows the name and price of this IEntertainment
  public String format();

  //is this IEntertainment the same as that one?
  public boolean sameEntertainment(IEntertainment that);

  //is this IEntertainment the same as that magazine?
  public boolean sameMagazine(Magazine that);

  //is this IEntertainment the same as that TVSeries?
  public boolean sameTVSeries(TVSeries that);

  //is this IEntertainment the same as that Podcast?
  public boolean samePodCast(Podcast that);
}

// to represent a type of entertainment 
abstract class AEntertainment implements IEntertainment {
  String name;
  double price;
  int installments;

  // constructor 
  AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }

  // compute the total price of this Entertainment
  public double totalPrice() {
    return this.price * this.installments;
  }

  // computes the minutes of entertainment of this Entertainment
  // with TVSeries and PodCast 50 minutes per episode
  public int duration() {
    return 50 * this.installments;
  }

  //produce a String that shows the name and price of this Entertainment
  public String format() {
    return this.name + ", " + String.valueOf(this.price) + ".";
  }

  //is this Entertainment the same as that one?
  public abstract boolean sameEntertainment(IEntertainment that);

  //is this Entertainment the same as that Magazine?
  public boolean sameMagazine(Magazine that) {
    return false;
  }

  //is this Entertainment the same as that TVSeries?
  public boolean sameTVSeries(TVSeries that) {
    return false;
  }

  //is this Entertainment the same as that Podcast?
  public boolean samePodCast(Podcast that) {
    return false;
  }
}

// to represent a Magazine 
class Magazine extends AEntertainment {
  String genre;
  int pages;

  // constructor
  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.genre = genre;
    this.pages = pages;
  }

  // computes the minutes of entertainment of this Magazine
  // which is 5 minutes per page and include all the installments 
  public int duration() {
    /*  TEMPLATE 
    Fields:
    ... this.name ...             -- String 
    ... this.price ...            -- double
    ... this.genre ...            -- String
    ... this.pages ...            -- int 
    ... this.installments ...     -- int 
    Methods:
    ... this.totalPrice ...                             -- double 
    ... this.duration() ...                             -- int
    ... this.format() ...                               -- String
    ... this.sameEntertainment(IEntertainment) ...      -- boolean
    ... this.sameMagazine(Magazine) ...                 -- boolean 
    ... this.sameTVSeries(TVSeries) ...                 -- boolean 
    ... this.samePodCast(PodCast ) ...                  -- boolean 
    Methods for fields:
    ... that.sameEntertainment(IEntertainment) ...      -- boolean 
    ... that.sameMagazine(Magazine) ...                 -- boolean 
    ... that.sameTVSeries(TVSeries) ...                 -- boolean 
    ... that.samePodCast(PodCast ) ...                  -- boolean 
     */
    return 5 * this.pages * this.installments;
  }

  //is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameMagazine(this);
  }

  //is this Magazine the same as that one?
  public boolean sameMagazine(Magazine that) {
    return this.name.equals(that.name)
        && this.price == that.price 
        && this.genre.equals(that.genre)
        && this.pages == that.pages
        && this.installments == that.installments;
  }
}

// to represent a TV series 
class TVSeries extends AEntertainment {
  String corporation;

  // constructor 
  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  //is this TVSeries the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    /*  TEMPLATE 
    Fields:
    ... this.name ...             -- String 
    ... this.price ...            -- double
    ... this.corporation ...      -- String
    ... this.installments ...     -- int 
    Methods:
    ... this.totalPrice ...                             -- double 
    ... this.duration() ...                             -- int
    ... this.format() ...                               -- String
    ... this.sameEntertainment(IEntertainment) ...      -- boolean
    ... this.sameMagazine(Magazine) ...                 -- boolean 
    ... this.sameTVSeries(TVSeries) ...                 -- boolean 
    ... this.samePodCast(PodCast ) ...                  -- boolean 
    Methods for fields:
    ... that.sameEntertainment(IEntertainment) ...      -- boolean 
    ... that.sameMagazine(Magazine) ...                 -- boolean 
    ... that.sameTVSeries(TVSeries) ...                 -- boolean 
    ... that.samePodCast(PodCast ) ...                  -- boolean 
     */
    return that.sameTVSeries(this);
  }

  //is this TVSeries the same as that one?
  public boolean sameTVSeries(TVSeries that) {
    return this.name.equals(that.name)
        && this.price == that.price
        && this.installments == that.installments
        && this.corporation.equals(that.corporation);
  }
}

// to represent a PodCast 
class Podcast extends AEntertainment {

  // constructor 
  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  //is this Podcast the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    /*  TEMPLATE 
    Fields:
    ... this.name ...             -- String 
    ... this.price ...            -- double
    ... this.installments ...     -- int 
    Methods:
    ... this.totalPrice ...                             -- double 
    ... this.duration() ...                             -- int
    ... this.format() ...                               -- String
    ... this.sameEntertainment(IEntertainment) ...      -- boolean
    ... this.sameMagazine(Magazine) ...                 -- boolean 
    ... this.sameTVSeries(TVSeries) ...                 -- boolean 
    ... this.samePodCast(PodCast ) ...                  -- boolean 
    Methods for fields:
    ... that.sameEntertainment(IEntertainment) ...      -- boolean 
    ... that.sameMagazine(Magazine) ...                 -- boolean 
    ... that.sameTVSeries(TVSeries) ...                 -- boolean 
    ... that.samePodCast(PodCast ) ...                  -- boolean 
     */
    return that.samePodCast(this);
  }

  //is this PodCast the same as that one?
  public boolean samePodCast(Podcast that) {
    return this.name.equals(that.name)
        && this.price == that.price
        && this.installments == that.installments;
  }
}

class ExamplesEntertainment {
  Magazine rollingStonem = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment rollingStone = this.rollingStonem;
  
  TVSeries houseofCardst = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment houseOfCards = this.houseofCardst;
  
  Podcast serialp = new Podcast("Serial", 0.0, 8);
  IEntertainment serial = this.serialp;

  Magazine newYorkTimesm = new Magazine("New York Times", 1.25, "News", 30, 100);
  IEntertainment newYorkTimes = this.newYorkTimesm;
  
  TVSeries gameOfThronest = new TVSeries("Game Of Thrones", 2.99, 71, "HBO");
  IEntertainment gameOfThrones = this.gameOfThronest;
  
  Podcast theHabitp = new Podcast("The Habit", 0.1, 89);
  IEntertainment theHabit = this.theHabitp;

  //testing the method total price 
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001) 
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.newYorkTimes.totalPrice(), 1.25 * 100, .0001) 
        && t.checkInexact(this.gameOfThrones.totalPrice(), 2.99 * 71, .0001)
        && t.checkInexact(this.theHabit.totalPrice(), 0.1 * 89, .0001);
  }  

  //testing the method duration 
  boolean testDuration(Tester t) {
    return t.checkExpect(this.rollingStone.duration(), 5 * 60 * 12) 
        && t.checkExpect(this.houseOfCards.duration(), 50 * 13)
        && t.checkExpect(this.serial.duration(), 50 * 8);
  }

  //testing the method format 
  boolean testFormat(Tester t) {
    return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.") 
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
        && t.checkExpect(this.theHabit.format(), "The Habit, 0.1.");
  }

  //testing the method SameEntertainment
  boolean testSameEntertainment(Tester t) {
    return t.checkExpect(this.rollingStone.sameEntertainment(this.newYorkTimes), false)
        && t.checkExpect(this.rollingStone.sameEntertainment(this.rollingStone), true)
        && t.checkExpect(this.rollingStone.sameEntertainment(this.serial), false)
        && t.checkExpect(this.rollingStone.sameEntertainment(this.houseOfCards), false)
        && t.checkExpect(this.houseOfCards.sameEntertainment(this.houseOfCards), true)
        && t.checkExpect(this.houseOfCards.sameEntertainment(this.newYorkTimes), false)
        && t.checkExpect(this.houseOfCards.sameEntertainment(this.serial), false)
        && t.checkExpect(this.houseOfCards.sameEntertainment(this.gameOfThrones), false)
        && t.checkExpect(this.serial.sameEntertainment(this.serial), true)
        && t.checkExpect(this.serial.sameEntertainment(this.rollingStone), false)
        && t.checkExpect(this.serial.sameEntertainment(this.houseOfCards), false)
        && t.checkExpect(this.serial.sameEntertainment(this.theHabit), false);
  }

  //testing the method SameMagazine 
  boolean testSameMagazine(Tester t) {
    return t.checkExpect(this.rollingStonem.sameMagazine(this.rollingStonem), true)
        && t.checkExpect(this.rollingStonem.sameMagazine(this.newYorkTimesm), false);
  }

  //testing the method SameTVSeries  
  boolean testSameTVSeries(Tester t) {
    return t.checkExpect(this.houseofCardst.sameTVSeries(this.houseofCardst), true)
        && t.checkExpect(this.houseofCardst.sameTVSeries(this.gameOfThronest), false);
  }

  //testing the method SamePodCast 
  boolean testSamePodCast(Tester t) {
    return t.checkExpect(this.serialp.samePodCast(this.serialp), true)
        && t.checkExpect(this.serialp.samePodCast(this.theHabitp), false);
  }
}