import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;

// to represent a list of colors 
interface ILoColor {

  // check the number of colors from this list 
  // that is exact position and exact color of the given list 
  int exact(ILoColor other);

  // check the number of colors from this list 
  // that is exact position and exact color of the given ConsLoColor
  int exactHelp(ConsLoColor that);

  // return 1 if the given color is in the first of this list
  // return 0 otherwise;
  int exactHelper(Color other);

  // return the number of inexact color of this list 
  // from the given list
  int inexact(ILoColor other);

  // if the first color of this list appear in the 
  // given list and does not equal the first of the given list 
  // return 1 and continues on with the rest of this list.
  // otherwise, return 0 and continues on with the rest 
  int inexactHelper(ILoColor other);

  // whether the given color is in the list
  boolean matchColor(Color other);

  // remove the given color from this IList, if it is an empty list
  // remove the color from an empty list will just produce an empty list 
  ILoColor removeColor(Color other);

  // produce the number of colors in this list 
  int length();

  // render this list of circles on the scene, and place one circle 
  // on a rectangle, so there is space between each circles. 
  WorldImage circleImage();

  // render this list of circles on the scene, in a reversed order.
  WorldImage drawGuess();

  //get the color from the given order of this list 
  Color getColor(int num);

  // delete the first color of this list 
  // if this is an empty list, return the empty list 
  // since deleting an empty list returns nothing;
  ILoColor delete();
}

// to represent an empty list of color 
class MtLoColor implements ILoColor {

  // check the number of colors from this list 
  // that is exact position and exact color of the given list 
  public int exact(ILoColor other) {
    return 0;
  }

  //check the number of colors from this list 
  // that is exact position and exact color of the given ConsLoColor
  public int exactHelp(ConsLoColor that) {
    return 0;
  }

  //return 1 if the given color is in the first of this list
  // return 0 otherwise;
  public int exactHelper(Color other) {
    return 0;
  }

  //whether the given color is in the list
  public boolean matchColor(Color other) {
    return false;
  }

  //return the number of inexact color of this list 
  // from the given list
  public int inexact(ILoColor other) {
    return 0;
  }

  // if the first color of this list appear in the 
  // given list and does not equal the first of the given list 
  // return 1 and continues on with the rest of this list.
  // otherwise, return 0 and continues on with the rest 
  public int inexactHelper(ILoColor other) {
    return 0;
  }

  //remove the given color from this IList, if it is an empty list
  // remove the color from an empty list will just produce an empty list 
  public ILoColor removeColor(Color other) {
    return this;
  }

  //produce the number of colors in this list 
  public int length() {
    return 0;
  }

  //render this list of circles on the scene, and place one circle 
  // on a rectangle, so there is space between each circles. 
  public WorldImage circleImage() {
    return new EmptyImage();
  }

  //render this list of circles on the scene, in a reversed order.
  public WorldImage drawGuess() {
    return new EmptyImage();
  }

  // get the color from the given order of this list 
  public Color getColor(int num) {
    throw new RuntimeException("Can not get a color from an empty list");
  }

  // delete the first color of this list 
  //if this is an empty list, return the empty list 
  // since deleting an empty list returns nothing;
  public ILoColor delete() {
    return this;
  }
}

// to represent a list of colors 
class ConsLoColor implements ILoColor {
  Color first;
  ILoColor rest;

  // constructor 
  ConsLoColor(Color first, ILoColor rest) {
    this.first = first;
    this.rest = rest;
  }

  //check the number of colors from this list 
  // that is exact position and exact color of the given list 
  public int exact(ILoColor other) {
    return other.exactHelp(this);
  }

  //check the number of colors from this list 
  // that is exact position and exact color of the given ConsLoColor
  public int exactHelp(ConsLoColor that) {
    return this.exactHelper(that.first) + this.rest.exact(that.rest);
  }

  //return 1 if the given color is in the first of this list
  // return 0 otherwise;
  public int exactHelper(Color other) {
    if (this.first.equals(other)) {
      return 1;
    }
    else {
      return 0;
    }
  }

  //return the number of inexact color of this list 
  // from the given list
  public int inexact(ILoColor other) {
    return inexactHelper(other) - exact(other);
  }

  //if the first color of this list appear in the 
  // given list and does not equal the first of the given list 
  // return 1 and continues on with the rest of this list.
  // otherwise, return 0 and continues on with the rest 
  public int inexactHelper(ILoColor other) {
    if (other.matchColor(this.first)) {
      return 1 + rest.inexactHelper(other.removeColor(this.first));
    }
    else {
      return 0 + rest.inexactHelper(other);
    }
  }

  //whether the given color is in this list
  public boolean matchColor(Color other) {
    return this.first.equals(other)
        || this.rest.matchColor(other);
  }

  //remove the given color from this IList, 
  // if it is an empty list, return empty list, since
  // remove the color from an empty list will just produce an empty list 
  public ILoColor removeColor(Color color) {
    if (this.first.equals(color)) {
      return this.rest;
    }
    else {
      return new ConsLoColor(this.first, this.rest.removeColor(color));
    }
  }

  //produce the number of colors in this list 
  public int length() {
    return 1 + this.rest.length();
  }

  //render this list of circles on the scene, and place one circle 
  // on a rectangle, so there is space between each circles. 
  public WorldImage circleImage() {
    return new BesideImage(
        new OverlayImage(new CircleImage(10, OutlineMode.SOLID, this.first), 
            new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE)), 
        this.rest.circleImage());
  }

  //render this list of circles on the scene, in a reversed order.
  public WorldImage drawGuess() {
    return new BesideImage(this.rest.drawGuess(), 
        new OverlayImage(new CircleImage(10, OutlineMode.SOLID, this.first), 
            new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE)));
  }

  //get the color from the given order of this list 
  public Color getColor(int num) {
    if (num == 1) {
      return this.first;
    }
    else {
      return this.rest.getColor(num - 1);
    }
  }

  //delete the first color of this list 
  //if this is an empty list, return the empty list 
  // since deleting an empty list returns nothing;
  public ILoColor delete() {
    return this.rest;
  }
}

// to represent choices that had been guessed by the player 
class Guess {
  ILoColor color;
  int exact;
  int inexact;

  // constructor 
  Guess(ILoColor color, int exact, int inexact) {
    this.color = color;
    this.exact = exact;
    this.inexact = inexact;
  }

  // to draw the list of colors as circles in this guess
  // besides with the exact and inexact feedbacks 
  WorldImage circleImage() {
    return new BesideImage(this.color.drawGuess(), 
        textImage(exact), 
        textImage(inexact));
  }

  // to draw the texts of this exact and inexact feedbacks
  WorldImage textImage(int i) {
    return new OverlayImage(
        new TextImage(Integer.toString(i), 20, Color.black),
        new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.white));
  }

  //whether the number of exact is the same as the given answer
  boolean sameAns(int answer) {
    return this.exact == answer;
  }
}

// to represent a list of guesses 
interface ILoGuess {

  // to draw the list of Guesses
  WorldImage guessImage();

  // produce the number of guesses in this list 
  int length();

  //whether the number of exact is the same as the given answer
  boolean sameAns(int answer);
}

// to represent a empty list of guess
class MtLoGuess implements ILoGuess {

  //to draw the list of Guesses
  public WorldImage guessImage() {
    return new EmptyImage();
  }

  // produce the number of guesses in this list 
  public int length() {
    return 0;
  }

  //whether the number of exact is the same as the given answer
  public boolean sameAns(int answer) {
    return false;
  }
}

// represent a cons list of guesses
class ConsLoGuess implements ILoGuess {

  Guess first;
  ILoGuess rest;

  // constructor 
  ConsLoGuess(Guess first, ILoGuess rest) {
    this.first = first;
    this.rest= rest;
  }

  //to draw the list of Guesses
  public WorldImage guessImage() {
    return new AboveImage(this.first.circleImage(), this.rest.guessImage());
  }


  // produce the number of guesses in this list 
  public int length() {
    return 1 + this.rest.length();
  }

  //whether the number of exact is the same as the given answer
  public boolean sameAns(int answer) {
    return this.first.sameAns(answer);
  }
}


// represent the world
class MyWorld extends World{
  boolean duplicate; // whether duplicate is allowed or not 
  int length;   // length of sequence to be guessed 
  int numguesses; // number of guesses allowed 
  ILoColor basecolor; // list of colors that the sequence has been make off
  ILoColor answer; //list of colors that represents the answer
  ILoGuess guesses; 
  ILoColor unguess;
  Random rand;

  // constructor for test 
  MyWorld(boolean duplicate, int length, int numguesses, ILoColor basecolor, 
      ILoColor answer, ILoGuess guesses, ILoColor unguess, Random rand) {
    this.length = length;
    this.numguesses = numguesses;
    this.basecolor = basecolor;
    this.duplicate = duplicate;
    this.answer = answer;
    this.guesses = guesses;
    this.unguess = unguess;
    this.rand = rand;
  }

  // constructor for actual game playing 
  MyWorld(boolean duplicate, int length, int numguesses, ILoColor basecolor){
    // if the length of sequence to be guessed is less than and equal to 0
    // throw exception 
    if (length > 0) {
      this.length = length;
    }
    else {
      throw new IllegalArgumentException(
          "Invalid Length: " + Integer.toString(length));
    }

    // if the number of guesses allowed is equal to and less than 0
    // throw exception 
    this.rand = new Random();
    if (numguesses > 0) {
      this.numguesses = numguesses;
    }
    else {
      throw new IllegalArgumentException(
          "Invalid Number of Guesses: " + Integer.toString(numguesses));
    }

    // if the length of the list of colors that make up the answer 
    // is less than 0 or equal to 0, throw exception 
    if (basecolor.length() > 0) {
      this.basecolor = basecolor;
    }
    else {
      throw new IllegalArgumentException("Invalid List of Colors.");
    }

    // if the duplicate is false and the length of the sequence to be guessed 
    // is longer than the basecolor length throw exception 
    if (duplicate == true || (length <= basecolor.length())) {
      this.duplicate = duplicate;
    }
    else {
      throw new IllegalArgumentException(
          "Invalid Choice: Duplicate should be allowed");
    }

    // if the duplicate is allowed, randomly generate a list of colors 
    // that allows duplication, otherwise, generate a list of colors 
    // which each color is unique 
    if (duplicate) {
      this.answer = randomGenerator(this.length);
    }
    else {
      this.answer = randomGeneratorD(this.length, new MtLoColor());
    }

    this.guesses = new MtLoGuess();
    this.unguess = new MtLoColor();
  }

  //produce a random number from 1 inclusive to the length of the list of basecolor
  int randomNumber() {
    return 1 + this.rand.nextInt(this.basecolor.length());
  }

  //generate a given length of list that contains random color from the list of basecolor
  //and no duplicates allowed
  ILoColor randomGenerator(int length) {
    if (length == 0) {
      return new MtLoColor();
    }
    else {
      return new ConsLoColor(basecolor.getColor(randomNumber()), 
          randomGenerator(length - 1));
    }
  }

  //generate a given length of list that contains random color from the list of basecolor
  //and allow duplicates
  ILoColor randomGeneratorD(int length, ILoColor list) {
    if (length == 0) {
      return new MtLoColor();
    }
    else {
      int num = randomNumber();
      if (list.matchColor(basecolor.getColor(num))) {
        return randomGeneratorD(length, list);
      }
      else {
        return new ConsLoColor(basecolor.getColor(num), 
            randomGeneratorD(length - 1 , 
                new ConsLoColor(basecolor.getColor(num), list)));
      }
    }
  }

  //render the images of circles on the scene 
  public WorldScene makeScene() {
    WorldScene s = getEmptyScene();
    return bg(s);
  }

  // render all the empty circles, guessed, unfinished guesses 
  // answers, and the feedbacks on to the scene 
  // as well as images that cover the answer 
  public WorldScene bg(WorldScene s) {
    s = s.placeImageXY(this.guesses.guessImage(), 
        40 + length * 30 / 2 + 30, 420 - 15 * (guesses.length()-1)); 
    s = s.placeImageXY(this.unguess.drawGuess(), 
        40 + (unguess.length()) * 15 , 420 - 30 * (guesses.length()));
    s = s.placeImageXY(this.basecolor.circleImage(), 
        40 + 30 * basecolor.length() / 2, 450);
    s = s.placeImageXY(this.emptyCircles(length, numguesses), 
        40 + length * 30 / 2, 450 - numguesses * 30 / 2 - 15);
    s = s.placeImageXY(this.answer.drawGuess(), 
        40 + length * 30 / 2, 40);
    s = s.placeImageXY(
        new RectangleImage(length * 30, 30, OutlineMode.SOLID, Color.black), 
        40 + length * 30 / 2, 40);
    return s;
  }

  // return m rows of outlined circles above each other,
  // with m to be equal to the number of guesses allowed 
  public WorldImage emptyCircles(int length, int guessallowed) {
    if (guessallowed == 0) {
      return new EmptyImage();
    }
    else {
      return new AboveImage((emptyCirclesHelper(length)),
          emptyCircles(length, guessallowed - 1));
    }
  }

  // draw a row of n outlined circles, with n to be the given length 
  public WorldImage emptyCirclesHelper(int length) {
    if (length == 0) {
      return new EmptyImage();
    }
    else {
      return new BesideImage(new OverlayImage(
          new CircleImage(10, OutlineMode.OUTLINE, Color.black), 
          new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE)), 
          emptyCirclesHelper(length - 1));
    }
  }

  // key handler:
  // if the key entered is a number that is greater than 0 and less than the length of 
  // base color, add the choose color to the unguessed list 
  // if the unfinished guess list equals to the length of sequence to be guessed 
  // check and exact and inexact and make that unguess guessed.
  // if the key is a backspace, delete the color just entered 
  // else will just return the original world.
  public MyWorld onKeyEvent(String key) {
    if (numberOrNot(key)) {
      if (Integer.parseInt(key) > 0 && Integer.parseInt(key) <= basecolor.length()
          && unguess.length() < length) {
        return new MyWorld(duplicate, length, numguesses, 
            basecolor, answer, guesses,
            new ConsLoColor(this.basecolor.getColor(Integer.valueOf(key)),
                this.unguess), this.rand);
      }
      else
        return this;
    }
    else if (key.equals("backspace") || key.equals("delete")) {
      return new MyWorld(duplicate, length, numguesses, 
          basecolor, answer, guesses, unguess.delete(), this.rand);
    }
    else if ((length == this.unguess.length()) && (key.equals("enter"))) {
      return new MyWorld(this.duplicate, this.length, numguesses, this.basecolor, 
          this.answer, new ConsLoGuess(new Guess(this.unguess, this.unguess.exact(answer), 
              this.unguess.inexact(answer)), this.guesses), new MtLoColor(), this.rand);
    }
    else {
      return this;
    }
  }

  // whether the entered key is a number
  boolean numberOrNot(String key)
  {
    if (key.equals("1")
        || key.equals("2") 
        || key.equals("3") 
        || key.equals("4") 
        || key.equals("5") 
        || key.equals("6") 
        || key.equals("7") 
        || key.equals("8") 
        || key.equals("9") 
        || key.equals("0")) {
      return true;
    }
    else {
      return false;
    }
  }

  // end the game is the guess is the same as the answer 
  // to the length of guesses is greater than the number of guesses allowed 
  // else continues on the game 
  public WorldEnd worldEnds() {
    if (this.guesses.sameAns(this.answer.length())
        || guesses.length() >= numguesses) {
      return new WorldEnd(true, this.makeAFinalScene());
    } 
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // if the game wins, render the win scene else 
  // render the lose scene 
  public WorldScene makeAFinalScene() {
    WorldScene s = getEmptyScene();
    s = s.placeImageXY(this.guesses.guessImage(), 
        40 + length*30/2 + 30, 420 - 15*(guesses.length()-1)); 
    s = s.placeImageXY(this.unguess.drawGuess(), 
        40+(unguess.length())*15 , 420 - 30*(guesses.length()));
    s = s.placeImageXY(this.basecolor.circleImage(), 
        40 + 30*basecolor.length()/2, 450);
    s = s.placeImageXY(this.emptyCircles(length, numguesses), 
        40 + length*30/2, 450 - numguesses*30/2 - 15);
    s = s.placeImageXY(this.answer.drawGuess(), 
        40 + length*30/2, 40);
    if (this.guesses.sameAns(this.answer.length())) {
      s = s.placeImageXY(new TextImage("WIN!", 20, Color.black), 200, 40);
    }
    else {
      s = s.placeImageXY(new TextImage("LOSE!", 20, Color.black), 200, 40);
    }
    return s;
  }
}


class ExamplesILoColor {
  ILoColor empty = new MtLoColor();
  ILoColor correctSequence = new ConsLoColor(Color.RED, 
      new ConsLoColor(Color.GREEN, 
          new ConsLoColor(Color.BLUE, 
              new ConsLoColor(Color.GREEN,
                  new ConsLoColor(Color.RED,
                      new ConsLoColor(Color.GREEN, empty))))));

  ILoColor guessSequence = new ConsLoColor(Color.RED, 
      new ConsLoColor(Color.BLUE, 
          new ConsLoColor(Color.GREEN, 
              new ConsLoColor(Color.GREEN,
                  new ConsLoColor(Color.BLUE,
                      new ConsLoColor(Color.BLUE, empty))))));

  ILoColor listOfColor= new ConsLoColor(Color.cyan, 
      new ConsLoColor(Color.pink, 
          new ConsLoColor(Color.blue, 
              new ConsLoColor(Color.green, new MtLoColor()))));
  ILoColor listOfColor2= new ConsLoColor(Color.pink, 
      new ConsLoColor(Color.pink, 
          new ConsLoColor(Color.blue, 
              new ConsLoColor(Color.green, new MtLoColor()))));
  ILoColor listOfColorReverse= new ConsLoColor(Color.green, 
      new ConsLoColor(Color.blue, new ConsLoColor(Color.pink, 
          new ConsLoColor(Color.cyan, new MtLoColor()))));
  ILoColor listOfColorRemovedPink= new ConsLoColor(Color.cyan, 
      new ConsLoColor(Color.blue, new ConsLoColor(Color.green, new MtLoColor())));
  ILoColor listOfColorRemovedGreen= new ConsLoColor(Color.cyan, 
      new ConsLoColor(Color.pink, new ConsLoColor(Color.blue, new MtLoColor())));
  
  ILoColor baseColor = new ConsLoColor(Color.cyan, 
      new ConsLoColor(Color.pink, new ConsLoColor(Color.blue, new MtLoColor())));

  ILoColor listOfColor3 = new ConsLoColor(Color.blue, new MtLoColor());
  
  Guess guess1 = new Guess(listOfColor3, 2, 2);
  Guess guess2 = new Guess(listOfColor2, 0, 2);
  Guess guess3 = new Guess(listOfColor, 2, 0);
  ILoGuess emptyGuess = new MtLoGuess();
  ILoGuess loGuess = new ConsLoGuess(guess1, 
      new ConsLoGuess(guess2, 
          new ConsLoGuess(guess3, emptyGuess)));
  
  //In class ILoColor 
  //test exact Method
  boolean testExact(Tester t) {
    return t.checkExpect(listOfColor.exact(listOfColor), 4)
        && t.checkExpect(listOfColor.exact(new MtLoColor()), 0)
        && t.checkExpect(listOfColor.exact(listOfColor2), 3)
        && t.checkExpect(new MtLoColor().exact(listOfColor), 0);
  }

  //test exactHelp Method
  boolean testExactHelp(Tester t) {
    return t.checkExpect(listOfColor.exactHelp((ConsLoColor)listOfColor), 4)
        && t.checkExpect(listOfColor.exactHelp((ConsLoColor)listOfColor2), 3)
        && t.checkExpect(new MtLoColor().exactHelp((ConsLoColor)listOfColor), 0);
  }

  //test exactHelper Method
  boolean testExactHelper(Tester t) {
    return t.checkExpect(((ConsLoColor)listOfColor).exactHelper(Color.pink), 0)
        && t.checkExpect(((ConsLoColor)listOfColor).exactHelper(Color.cyan), 1)
        && t.checkExpect(((ConsLoColor)listOfColor).exactHelper(Color.black), 0)
        && t.checkExpect(((ConsLoColor)listOfColor).exactHelper(Color.yellow), 0);
  }

  //test inexact Method
  boolean testInExact(Tester t) {
    return t.checkExpect(listOfColor.inexact(listOfColor), 0)
        && t.checkExpect(listOfColor.inexact(new MtLoColor()), 0)
        && t.checkExpect(listOfColor.inexact(listOfColorReverse), 4)
        && t.checkExpect(new MtLoColor().inexact(listOfColor), 0);
  }


  //test inexactHelper Method
  boolean testInExactHelper(Tester t) {
    return t.checkExpect(listOfColor.inexactHelper(listOfColor), 4)
        && t.checkExpect(listOfColor.inexactHelper(new MtLoColor()), 0)
        && t.checkExpect(listOfColor.inexactHelper(listOfColorReverse), 4)
        && t.checkExpect(new MtLoColor().inexactHelper(listOfColor), 0);
  }

  //test removeColor Method
  boolean testRemoveColor(Tester t) {
    return t.checkExpect(listOfColor.removeColor(Color.green), 
        listOfColorRemovedGreen)
        && t.checkExpect(listOfColor.removeColor(Color.black), 
            listOfColor)
        && t.checkExpect(listOfColor.removeColor(Color.pink), 
            listOfColorRemovedPink)
        && t.checkExpect(new MtLoColor().removeColor(Color.pink), new MtLoColor());
  }

  //test matchColor Method
  boolean testMatchColor(Tester t) {
    return t.checkExpect(listOfColor.matchColor(Color.pink), true)
        && t.checkExpect(listOfColor.matchColor(Color.black), false)
        && t.checkExpect(listOfColor.matchColor(Color.green), true)
        && t.checkExpect(new MtLoColor().matchColor(Color.pink), false);
  }

  //test length Method
  boolean testLength(Tester t) {
    return t.checkExpect(listOfColor.length(), 4)
        && t.checkExpect(listOfColorRemovedPink.length(), 3)
        && t.checkExpect(guessSequence.length(), 6)
        && t.checkExpect(new MtLoColor().length(), 0);
  }

  //test getColor Method
  boolean testGetColor(Tester t) {
    return t.checkExpect(listOfColor.getColor(1), Color.cyan)
        && t.checkExpect(listOfColor.getColor(2), Color.pink)
        && t.checkExpect(listOfColorRemovedPink.getColor(2), Color.blue);
  }

  //test delete Method
  boolean testDelete(Tester t) {
    return t.checkExpect(listOfColor.delete(), 
        new ConsLoColor(Color.pink, 
            new ConsLoColor(Color.blue, 
                new ConsLoColor(Color.green, new MtLoColor()))))
        && t.checkExpect(listOfColorRemovedGreen.delete(), 
            new ConsLoColor(Color.pink, 
                new ConsLoColor(Color.blue, new MtLoColor())))
        && t.checkExpect(new MtLoColor().delete(), new MtLoColor());
  }

  // test the method circleImage
  boolean testCircleImage(Tester t) {
    return t.checkExpect(this.listOfColorRemovedPink.circleImage(),  
        new BesideImage(
            new OverlayImage(new CircleImage(10, OutlineMode.SOLID, Color.cyan), 
                new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE)),
            new OverlayImage(new CircleImage(10, OutlineMode.SOLID, Color.blue), 
                new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE)),
            new BesideImage(
                new OverlayImage(new CircleImage(10, OutlineMode.SOLID, Color.green), 
                    new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE)),
                new EmptyImage())))
        && t.checkExpect(this.empty.circleImage(), new EmptyImage());
  }

  //test the method draw guess
  boolean testDrawGuess(Tester t) {
    return t.checkExpect(this.listOfColorRemovedPink.drawGuess(),  
        new BesideImage(new BesideImage( new BesideImage(new EmptyImage(),
            new OverlayImage(new CircleImage(10, OutlineMode.SOLID, Color.green), 
                new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE))), 
            new OverlayImage(new CircleImage(10, OutlineMode.SOLID, Color.blue), 
                new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE))),
            new OverlayImage(new CircleImage(10, OutlineMode.SOLID, Color.cyan), 
                new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE))))
        && t.checkExpect(this.empty.drawGuess(), new EmptyImage());
  }

  // In class Guess 
  //test the method circleImage
  boolean testCircleImage2(Tester t) {
    return t.checkExpect(this.guess1.circleImage(), 
        new BesideImage(this.listOfColor3.drawGuess(), 
            this.guess1.textImage(2), 
            this.guess1.textImage(2)))
        &&  t.checkExpect(this.guess2.circleImage(), 
            new BesideImage(this.listOfColor2.drawGuess(), 
                this.guess2.textImage(0), 
                this.guess2.textImage(2)));
  }
  
  //test the method textImage 
  boolean testTextImage(Tester t) {
    return t.checkExpect(this.guess1.textImage(0), new OverlayImage(
        new TextImage(Integer.toString(0), 20, Color.black),
        new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.white)))
        && t.checkExpect(this.guess2.textImage(1), new OverlayImage(
            new TextImage(Integer.toString(1), 20, Color.black),
            new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.white)));
  }
  
  //test the method sameAns
  boolean testSameAns(Tester t) {
    return t.checkExpect(this.guess1.sameAns(2), true)
        && t.checkExpect(this.guess2.sameAns(3), false);
  }
  
  //In class ILoGuess 
  // test the method guessImage
  boolean testGuessImage(Tester t) {
    return t.checkExpect(this.loGuess.guessImage(), 
        new AboveImage(guess1.circleImage(), 
            new AboveImage(guess2.circleImage(), 
            new AboveImage(guess3.circleImage(), new EmptyImage()))))
        && t.checkExpect(this.emptyGuess.guessImage(), new EmptyImage());
  }
  
  //test the method length
  boolean testLengthG(Tester t) {
    return t.checkExpect(this.loGuess.length(), 3)
        && t.checkExpect(this.emptyGuess.length(), 0);
  }
  
  //test the method sameAns
  boolean testSameAnsLOG(Tester t) {
    return t.checkExpect(this.loGuess.sameAns(2), true)
        && t.checkExpect(this.loGuess.sameAns(4), false)
        && t.checkExpect(this.emptyGuess.sameAns(2), false);
  }
  
  // In class MyWorld 
  int randomSeed = 5;
  MyWorld rand = new MyWorld(true, 3, 12, baseColor, new MtLoColor(), 
      new MtLoGuess(), new MtLoColor(), new Random(randomSeed));

  MyWorld rand2 = new MyWorld(true, 3, 12, baseColor, new MtLoColor(), 
      new MtLoGuess(), new MtLoColor(), new Random(randomSeed));

  //test exception
  boolean testValidMyWorld(Tester t) {
    return  t.checkConstructorException(
        new IllegalArgumentException(
            "Invalid Length: -1"), "MyWorld"
        , true, -1, 10, listOfColor)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "Invalid Number of Guesses: -2"), "MyWorld"
            , true, 3, -2, listOfColor)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "Invalid List of Colors."), "MyWorld"
            , true, 3, 10, new MtLoColor())
        && t.checkConstructorException(
            new IllegalArgumentException(
                "Invalid Choice: Duplicate should be allowed"), "MyWorld"
            , false, 6, 10, listOfColor);
  }
  
  // test the method bg
  boolean testBG(Tester t) {
    return t.checkExpect(rand.bg(rand.getEmptyScene()), 
        rand.getEmptyScene()
        .placeImageXY(new EmptyImage(), 
            40 + 3 * 30 / 2 + 30, 420 - 15 * (0 - 1))
        .placeImageXY(new EmptyImage(), 
            40 + 0 * 15 , 420 - 30 * 0)
        .placeImageXY(baseColor.circleImage(), 
            40 + 30 * 4 / 2, 450)
        .placeImageXY(rand.emptyCircles(3, 12), 
            40 + 3 * 30 / 2, 450 - 12 * 30 / 2 - 15)
        .placeImageXY(rand.answer.drawGuess(), 
            40 + 3 * 30 / 2, 40)
        .placeImageXY(
            new RectangleImage(3 * 30, 30, OutlineMode.SOLID, Color.black), 
            40 + 3 * 30 / 2, 40));
  }
  
  //test the method makeAFinalScene
  boolean testMakeAFinalScene(Tester t) {
    return t.checkExpect(rand.makeAFinalScene(), 
        rand.getEmptyScene()
        .placeImageXY(new EmptyImage(), 
            40 + 3 *30/2 + 30, 420 - 15*(0 - 1))
        .placeImageXY(new EmptyImage(), 
            40+(0)*15 , 420 - 30*(0))
        .placeImageXY(baseColor.circleImage(), 
            40 + 30* baseColor.length()/2, 450)
        .placeImageXY(rand.emptyCircles(3, 12), 
            40 + 3 *30/2, 450 - 12 *30/2 - 15)
        .placeImageXY(rand.answer.drawGuess(), 
            40 + 3 *30/2, 40)
        .placeImageXY(new TextImage("LOSE!", 20, Color.black), 200, 40));
  }
  
  //test the method make scene 
  boolean testMakeScene(Tester t) {
    return t.checkExpect(rand.makeScene(), rand.bg(rand.getEmptyScene()));
  }
 

  //test drawEmptyCircles
  boolean testDrawEmptyCircles(Tester t) {
    WorldImage img = rand.emptyCirclesHelper(3);
    return t.checkExpect(this.rand.emptyCircles(3, 3),
        new AboveImage(img, new AboveImage(img, new AboveImage(img, new EmptyImage()))));
  }

  //test drawEmptyCirclesHelper
  boolean testDrawEmptyCirclesHelper(Tester t) {
    WorldImage img = new OverlayImage(
        new CircleImage(10, OutlineMode.OUTLINE, Color.black), 
        new RectangleImage(30, 30, OutlineMode.OUTLINE, Color.WHITE));

    return t.checkExpect(this.rand.emptyCirclesHelper(3),
        new BesideImage(img, new BesideImage(img, new BesideImage(img, new EmptyImage()))));
  }

  //test onkeyevent
  boolean testOnKeyEvent(Tester t) {
    MyWorld m = rand.onKeyEvent("1").onKeyEvent("1").onKeyEvent("1");
    ILoColor l = new ConsLoColor(Color.cyan, new ConsLoColor(Color.cyan, 
        new ConsLoColor(Color.cyan, new MtLoColor())));
    Guess g = new Guess(l, 0, 0);
    ILoGuess log = new ConsLoGuess(g, new MtLoGuess());

    return t.checkExpect(this.rand.onKeyEvent("a"), this.rand)
        && t.checkExpect(this.rand.onKeyEvent("1"), 
            new MyWorld(true, 3, 12, baseColor, new MtLoColor(), new MtLoGuess(),
                new ConsLoColor(Color.cyan, new MtLoColor()), new Random(randomSeed)))
        && t.checkExpect(this.rand.onKeyEvent("8"), this.rand)
        && t.checkExpect(this.rand.onKeyEvent("backspace"), this.rand)

        && t.checkExpect(m.onKeyEvent("enter"), 
            new MyWorld(true, 3, 12, baseColor, new MtLoColor(), log ,
                new MtLoColor(), new Random(randomSeed)));
  }

  //test numberOrNot
  boolean testNumberOrNot(Tester t) {
    return t.checkExpect(this.rand.numberOrNot("1"), true)
        && t.checkExpect(this.rand.numberOrNot("2"), true)
        && t.checkExpect(this.rand.numberOrNot("3"), true)
        && t.checkExpect(this.rand.numberOrNot("4"), true)
        && t.checkExpect(this.rand.numberOrNot("5"), true)
        && t.checkExpect(this.rand.numberOrNot("6"), true)
        && t.checkExpect(this.rand.numberOrNot("7"), true)
        && t.checkExpect(this.rand.numberOrNot("8"), true)
        && t.checkExpect(this.rand.numberOrNot("9"), true)
        && t.checkExpect(this.rand.numberOrNot("0"), true)
        && t.checkExpect(this.rand.numberOrNot("a"), false)
        ;
  }

  //test WorldEnds
  boolean testWorldEnds(Tester t) {
    ILoColor l = new ConsLoColor(Color.cyan, new ConsLoColor(Color.cyan, 
        new ConsLoColor(Color.cyan, new MtLoColor())));
    MyWorld mw = new MyWorld(true, 3, 12, baseColor, l, new MtLoGuess(), new MtLoColor(), new Random(randomSeed));
    MyWorld w = mw.onKeyEvent("1").onKeyEvent("1").onKeyEvent("1").onKeyEvent("enter");

    return t.checkExpect(w.worldEnds(),
        new WorldEnd(true, w.makeAFinalScene()));
  }
  
  // the actual BigBang Game 
  boolean testBigBang(Tester t) {
    ILoColor listOfColor= new ConsLoColor(Color.cyan, new ConsLoColor(Color.pink, new ConsLoColor(Color.blue, new ConsLoColor(Color.green, new MtLoColor()))));

    MyWorld w = new MyWorld(true, 3, 12, listOfColor);
    int worldWidth = 300;
    int worldHeight = 500;
    double tickRate = 0.1;
    return w.bigBang(worldWidth, worldHeight, tickRate);
  }
}





