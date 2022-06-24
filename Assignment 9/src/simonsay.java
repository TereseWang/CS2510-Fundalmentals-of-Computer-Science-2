import java.util.Random;
import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
// and predefined colors (Color.RED, Color.GRAY, etc.)

// to represent a light with a color and its position
class Light {
  Color color;
  Posn pos;

  Light(Color color, Posn pos) {
    this.color = color;
    this.pos = pos;
  }

  // to draw the image of the light with OriginalColor 
  public WorldImage drawLight() {
    return new RectangleImage(100, 100, "solid", this.color);
  }  

  // check if this Light equal the given light
  public boolean equal(Light l) {
    return this.color.equals(l.color)
        && this.pos.x == this.pos.x
        && this.pos.y == this.pos.y;
  }

  public Light changeColor() {
    return new Light(Color.white, this.pos);
  }
}

// represents a sequence of light colors
interface ILoLights {
  // return the length of the game;
  public int length();
  // get the color specific in the list with the given index 
  public Light getLight(int index);
  public ILoLights removeLight(int index);
  public ILoLights reverse();
  public ILoLights reverseHelper(ILoLights other);
}

class MtLoLights implements ILoLights {
  MtLoLights() {

  }

  @Override
  public int length() {
    return 0;
  }

  public Light getLight(int index) {
    throw new RuntimeException("Cannot get Light from an MtLoLight");
  }

  public ILoLights removeLight(int index) {
    return this;
  }

  public ILoLights reverse() {
    return this;
  }

  public ILoLights reverseHelper(ILoLights other) {
    return other;
  }
}

class ConsLoLights implements ILoLights {
  Light first;
  ILoLights rest;

  ConsLoLights(Light first, ILoLights rest) {
    this.first = first;
    this.rest = rest;
  }

  public int length() {
    return 1 + this.rest.length();
  }

  public Light getLight(int index) {
    if(index == 0) {
      return this.first;
    }
    else {
      return this.rest.getLight(index - 1);
    }
  }

  public ILoLights removeLight(int index) {
    if (index == 0) {
      return this.rest;
    }
    else {
      return new ConsLoLights(this.first, this.rest.removeLight(index - 1));
    }
  }

  public ILoLights reverse() {
    return reverseHelper(new MtLoLights());
  }

  public ILoLights reverseHelper(ILoLights other) {
    return this.rest.reverseHelper(new ConsLoLights(this.first, other));
  }
}

//game class
class SimonSays extends World {

  ILoLights blinkSequence;
  ILoLights presetSequence;
  ILoLights answer;
  boolean animated; //true is the world is currently blinking a list of lights 
  Random rand;
  int blinkLength; // the number of times totally will the color blink 
  Light currFlashing;
  boolean lose;

  //The constructor for use in testing, with a specified Random object
  SimonSays(ILoLights blinkSequence, ILoLights presetSequence, boolean animated, 
      Random rand, int blinkLength, Light currFlashing, ILoLights answer, boolean lose) {
    this.blinkSequence = blinkSequence;
    this.presetSequence = presetSequence;
    this.animated = animated;
    this.rand = rand;
    this.blinkLength = blinkLength;
    this.currFlashing = currFlashing;
    this.answer = answer;
    this.lose = false;
  }

  //The constructor for use in "real" games
  SimonSays() {
    this.rand = new Random();
    this.presetSequence = new ConsLoLights(new Light(Color.blue, new Posn(100, 100)), 
        new ConsLoLights(new Light(Color.green, new Posn(100, 300)), 
            new ConsLoLights(new Light(Color.red, new Posn(300, 100)), 
                new ConsLoLights(new Light(Color.yellow, new Posn(300, 300)), 
                    new MtLoLights()))));
    this.blinkLength = 1;
    this.blinkSequence = randomGenerator(this.blinkLength);
    this.animated = true;
    this.currFlashing = new Light(Color.black, new Posn(500, 500));
    this.answer = new MtLoLights();
  }

  public int randomNumber() {
    return this.rand.nextInt(4);
  }


  public ILoLights randomGenerator(int i) {
    if (i == 0) {
      return new MtLoLights();
    }
    else {
      return new ConsLoLights(randomGeneratorH(randomNumber()),
          randomGenerator(i - 1));
    }
  }

  public Light randomGeneratorH(int n) {
    if(n == 0) {
      return new Light(Color.blue, new Posn(100, 100));
    }
    else if (n == 1) {
      return new Light(Color.green, new Posn(100, 300));
    }
    else if (n == 2) {
      return new Light(Color.red, new Posn(300, 100));
    }
    else {
      return new Light(Color.yellow, new Posn(300, 300));
    }
  }

  public WorldScene makeScene() {
    WorldScene empty = new WorldScene(400, 400);
    return bg(empty);
  }

  public WorldScene bg(WorldScene s) {
    s = s.placeImageXY(bgHelper(0, this.currFlashing), getPosnX(0), getPosnY(0));
    s = s.placeImageXY(bgHelper(1, this.currFlashing), getPosnX(1), getPosnY(1));
    s = s.placeImageXY(bgHelper(2, this.currFlashing), getPosnX(2), getPosnY(2));
    s = s.placeImageXY(bgHelper(3, this.currFlashing), getPosnX(3), getPosnY(3));
    return s;
  }

  public WorldImage bgHelper(int i, Light curr) {
    if (curr.pos.x == presetSequence.getLight(i).pos.x
        && curr.pos.y == presetSequence.getLight(i).pos.y) {
      return new OverlayImage(curr.changeColor().drawLight(),
          presetSequence.getLight(i).drawLight());
    }
    else {
      return presetSequence.getLight(i).drawLight();
    }
  }

  public int getPosnX(int i) {
    return presetSequence.getLight(i).pos.x;
  }

  public int getPosnY(int i) {
    return presetSequence.getLight(i).pos.y;
  }



  public SimonSays onTick() {
    if (animated) {
      if (blinkSequence.length() != 0) {
        if (!currFlashing.equal(blinkSequence.getLight(0))) {
          return new SimonSays(this.blinkSequence, this.presetSequence, this.animated, 
              this.rand, this.blinkLength, new Light(this.blinkSequence.getLight(0).color, 
                  this.blinkSequence.getLight(0).pos), 
              new ConsLoLights(blinkSequence.getLight(0), this.answer), this.lose);
        }
        else {
          return new SimonSays(this.blinkSequence.removeLight(0), this.presetSequence, this.animated, 
              this.rand, this.blinkLength, new Light(Color.black, new Posn(1000, 1000)), this.answer, this.lose);
        }
      }
      else {
        return new SimonSays(this.blinkSequence, this.presetSequence, false, 
            this.rand, this.blinkLength, new Light(Color.white, new Posn(1000, 1000)), this.answer, this.lose);
      }
    }
    else {
      return this;
    }
  }
  // mouse click simulator 
  public SimonSays onMouseClicked(Posn pos) {
    if(animated) {
      return this;
    }
    else {
      if(answer.length() != 0) {
        if((answer.getLight(0).pos.x >= pos.x - 50) && (answer.getLight(0).pos.x <= pos.x + 50)
            && (answer.getLight(0).pos.y >= pos.y - 50) && (answer.getLight(0).pos.y <= pos.y + 50)) {
          return new SimonSays(this.blinkSequence, this.presetSequence, this.animated,
              this.rand, this.blinkLength, this.currFlashing, this.answer.removeLight(0), this.lose);
        }
        else {
          return new SimonSays(this.blinkSequence, this.presetSequence, this.animated, this.rand, this.blinkLength, this.currFlashing,
              this.answer, true);
        }
      }
      else {
        return new SimonSays(randomGenerator(this.blinkLength + 1), this.presetSequence, true,
            this.rand, this.blinkLength + 1, new Light(Color.black, new Posn(500, 500)), new MtLoLights(), false);
      }
    }
  }
  
  public WorldEnd worldEnds() {
    if (this.lose) {
      return new WorldEnd(true, this.makeAFinalScene());
    } 
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }
  public WorldScene makeAFinalScene() {
    WorldScene s = getEmptyScene();
    s = s.placeImageXY(new TextImage("You Lose!", 50, Color.black), 200, 200);
    s = s.placeImageXY(bgHelper(0, this.currFlashing), getPosnX(0), getPosnY(0));
    s = s.placeImageXY(bgHelper(1, this.currFlashing), getPosnX(1), getPosnY(1));
    s = s.placeImageXY(bgHelper(2, this.currFlashing), getPosnX(2), getPosnY(2));
    s = s.placeImageXY(bgHelper(3, this.currFlashing), getPosnX(3), getPosnY(3));
    return s;
  }
}


class ExamplesSimonSays {
  boolean testBigBang(Tester t) {
    SimonSays w = new SimonSays();
    return w.bigBang(400,400, 0.7);
  }
}