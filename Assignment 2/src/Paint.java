import java.awt.Color;
import tester.Tester;

// to represent a paint 
interface IPaint {
  // compute the final color of this paint
  Color getFinalColor();

  // compute the total number of solid paints involved 
  // in producing final color of this paint 
  int countPaints();

  // compute the number of times this paint was mixed in some way 
  int countMixes(); 

  // compute how deeply mixtures are nested in the formula of this paint 
  int formulaDepth();

  // produce the paint as is, except all Darken mixtures should become Brighten
  // mixtures and vice versa for this paint.
  IPaint invert();

  // produce a String representing the contents of this paint, 
  // where the formula for the paint has been expanded only depth times.
  String mixingFormula(int depth);
}

// to represent a solid color 
class Solid implements IPaint {
  String name;
  Color color;

  // constructor 
  Solid(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  // return this color
  public Color getFinalColor() {
    /* Templates:
     * Fields:
     * this.name -- String 
     * this.color -- Color 
     * 
     * Methods: 
     * this.getFinalColor() -- Color
     * this.countPiants() -- int 
     * this.countMixes() -- int 
     * this.formulaDepth() -- int 
     * this.invert() -- IPaint
     * this.mixingFormula(int depth) -- String 
     */
    return this.color;
  }

  // return 1 
  public int countPaints() {
    return 1;
  }

  // nothing to do 
  public int countMixes() {
    return 0;
  }

  public int formulaDepth() {
    return 0;
  }

  //return this color
  public IPaint invert() {
    return this;
  }

  public String mixingFormula(int depth) {
    return this.name;
  }
}

// to represent a Combo paint 
class Combo implements IPaint {
  String name;
  IMixture operation;

  // constructor 
  Combo(String name, IMixture operation) {
    this.name = name;
    this.operation = operation;
  }

  // to computer the Color of this paint 
  public Color getFinalColor() {
    /* Templates:
     * Fields:
     * this.name -- String 
     * this.operation -- IMixture
     * 
     * Methods: 
     * this.getFinalColor() -- Color
     * this.countPiants() -- int 
     * this.countMixes() -- int 
     * this.formulaDepth() -- int 
     * this.invert() -- IPaint
     * this.mixingFormula(int depth) -- String 
     * 
     * Methods of Fields:
     * this.operation.getFinalColor() -- Color
     * this.operation.countPiants() -- int 
     * this.operation.countMixes() -- int 
     * this.operation.formulaDepth() -- int 
     * this.operation.invert() -- IPaint
     * this.operation.mixingFormula(int depth) -- String
     */
    return this.operation.getFinalColor();
  }

  // compute the total number of solid paints of this combo 
  public int countPaints() {
    return this.operation.countPaints();
  }

  // compute the total number of times this combo was mixed in some way
  public int countMixes() {
    return this.operation.countMixes();
  }

  //compute how deeply mixtures are nested for this combo 
  public int formulaDepth() {
    return this.operation.formulaDepth();
  }

  // produce the paint as is, invert the Darken to Brighten and vice versa for this combo.
  public IPaint invert() {
    return new Combo(this.name, this.operation.invert());
  }

  // produce the string representing the contents of this combo,
  // where expanded only depth times 
  public String mixingFormula(int depth) {
    if (depth <= 0) {
      return this.name;
    }
    else {
      return this.operation.mixingFormula(depth);
    }
  }
}

// to represent a mixture
interface IMixture {
  //compute the final color of this paint
  Color getFinalColor();

  // compute the total number of solid paints involved 
  // in producing final color of this mixture 
  int countPaints();

  // compute the number of times this mixture was mixed in some way 
  int countMixes(); 

  // compute how deeply mixtures are nested in the formula of this mixture 
  int formulaDepth();

  // produce the mixture as is, except all Darken mixtures should become Brighten
  // mixtures and vice versa for this mixture.
  IMixture invert();

  // produce a String representing the contents of this mixture, 
  // where the formula for the paint has been expanded only depth times.
  String mixingFormula(int depth);
}

// to represent a darken 
class Darken implements IMixture {
  IPaint paint;

  // constructor
  Darken(IPaint paint) {
    this.paint = paint;
  }

  // to compute the final color of this darken 
  public Color getFinalColor() {
    /* Templates:
     * Fields:
     * this.paint -- IPaint
     * 
     * Methods: 
     * this.getFinalColor() -- Color
     * this.countPiants() -- int 
     * this.countMixes() -- int 
     * this.formulaDepth() -- int 
     * this.invert() -- IMixture
     * this.mixingFormula(int depth) -- String 
     * 
     * Methods of Fields:
     * this.paint.getFinalColor() -- Color
     * this.paint.countPiants() -- int 
     * this.paint.countMixes() -- int 
     * this.paint.formulaDepth() -- int 
     * this.paint.invert() -- IMixture
     * this.paint.mixingFormula(int depth) -- String
     */
    return this.paint.getFinalColor().darker();
  }

  public int countPaints() {
    return 1 + this.paint.countPaints();
  }

  public int countMixes() {
    return 1 + this.paint.countMixes();
  }

  public int formulaDepth() {
    return 1 + this.paint.formulaDepth();
  }

  // to produce a Brighten that invert the this Darken
  public IMixture invert() {
    return new Brighten(this.paint.invert());
  }

  // to produce the string that represent the contents of this Darken 
  public String mixingFormula(int depth) {
    return "darken(" + this.paint.mixingFormula(depth - 1) + ")";
  }
}

// to represent a Brighten
class Brighten implements IMixture {
  IPaint paint;

  // constructor 
  Brighten(IPaint paint) {
    this.paint = paint;
  }

  // to compute the final color of this Brighten 
  public Color getFinalColor() {
    /* Templates:
     * Fields:
     * this.paint -- IPaint
     * 
     * Methods of Fields:
     * this.paint.getFinalColor() -- Color
     * this.paint.countPiants() -- int 
     * this.paint.countMixes() -- int 
     * this.paint.formulaDepth() -- int 
     * this.paint.invert() -- IMixture
     * this.paint.mixingFormula(int depth) -- String
     */
    return this.paint.getFinalColor().brighter();
  }

  public int countPaints() {
    return 1 + this.paint.countPaints();
  }

  public int countMixes() {
    return 1 + this.paint.countMixes();
  }

  public int formulaDepth() {
    return 1 + this.paint.formulaDepth();
  }

  // to compute the Darken that inverted this Brighten 
  public IMixture invert() {
    return new Darken(this.paint.invert());
  }

  // to produce the String that represents the content of this Brighten 
  public String mixingFormula(int depth) {
    return "brighten(" + this.paint.mixingFormula(depth - 1) + ")";
  }
}

// to represent a bland of paints 
class Blend implements IMixture {
  IPaint paint1;
  IPaint paint2;

  // constructor
  Blend(IPaint paint1, IPaint paint2) {
    this.paint1 = paint1;
    this.paint2 = paint2;
  }

  // compute the final color of this blend 
  // with half of each color value of one paint to another
  public Color getFinalColor() {
    /* Templates:
     * Fields:
     * this.paint1 -- IPaint
     * this.paint2 -- IPaint 
     * 
     * Methods: 
     * this.getFinalColor() -- Color
     * this.countPiants() -- int 
     * this.countMixes() -- int 
     * this.formulaDepth() -- int 
     * this.invert() -- IMixture
     * this.mixingFormula(int depth) -- String 
     * 
     * Methods of Fields:
     * this.paint1.getFinalColor() -- Color
     * this.paint1.countPiants() -- int 
     * this.paint1.countMixes() -- int 
     * this.paint1.formulaDepth() -- int 
     * this.paint1.invert() -- IMixture
     * this.paint1.mixingFormula(int depth) -- String
     * this.paint2.getFinalColor() -- Color
     * this.paint2.countPiants() -- int 
     * this.paint2.countMixes() -- int 
     * this.paint2.formulaDepth() -- int 
     * this.paint2.invert() -- IMixture
     * this.paint2.mixingFormula(int depth) -- String
     */
    int redBlend =  (this.paint1.getFinalColor().getRed() / 2)
        + (this.paint2.getFinalColor().getRed() / 2);
    int greenBlend = (this.paint1.getFinalColor().getGreen() / 2)
        + (this.paint2.getFinalColor().getGreen() / 2);
    int blueBlend = (this.paint1.getFinalColor().getBlue() / 2)
        + (this.paint2.getFinalColor().getBlue() / 2);
    return new Color(redBlend, greenBlend, blueBlend); 
  }

  public int countPaints() {
    return this.paint1.countPaints() + this.paint2.countPaints();
  }

  public int countMixes() {
    return 1 + this.paint1.countMixes() + this.paint2.countMixes();
  }

  public int formulaDepth() {
    return 1 + Math.max(this.paint1.formulaDepth(), this.paint2.formulaDepth());
  }

  public IMixture invert() {
    return new Blend(this.paint1.invert(), this.paint2.invert());
  }

  // produce the string that represent the content of this blend 
  public String mixingFormula(int depth) {
    return "blend(" + this.paint1.mixingFormula(depth - 1) + ", " 
        + this.paint2.mixingFormula(depth - 1) + ")";
  }
}

class ExamplesPaint {
  ExamplesPaint() {}

  IPaint red = new Solid("red", new Color(255, 0, 0));
  IPaint green = new Solid("green", new Color(0, 255, 0));
  IPaint blue = new Solid("blue", new Color(0, 0, 255));

  IMixture khakib = new Blend(red, green);
  IPaint khaki = new Combo("khaki", khakib);

  IMixture yellowb = new Brighten(khaki);
  IPaint yellow = new Combo("yellow", yellowb);

  IMixture purpleb = new Blend(red, blue);
  IPaint purple = new Combo("purple", purpleb);

  IMixture darkPurpleb = new Darken(purple);
  IPaint darkPurple = new Combo("dark purple", darkPurpleb);

  IMixture mauveb = new Blend(purple, khaki);
  IPaint mauve = new Combo("mauve", mauveb);

  IMixture pinkb = new Brighten(mauve);
  IPaint pink = new Combo("pink", pinkb);

  IMixture coralb = new Blend(pink, khaki);
  IPaint coral = new Combo("coral", coralb);

  IMixture brownb = new Blend(yellow, purple);
  IPaint brown = new Combo("brown", brownb);

  IMixture orangeb = new Blend(yellow, red);
  IPaint orange = new Combo("orange", orangeb);

  IMixture grayb = new Blend(orange, blue);
  IPaint gray = new Combo("gray", grayb);

  IMixture violetb = new Blend(darkPurple, mauve);
  IPaint violet = new Combo("violet", violetb);

  // test the method getFinalColor in class IPaint 
  boolean testGetFinalColor(Tester t) {
    return t.checkExpect(this.red.getFinalColor(), new Color(255, 0, 0))
        && t.checkExpect(this.yellow.getFinalColor(), new Color(181, 181, 0))
        && t.checkExpect(this.khaki.getFinalColor(), new Color(127, 127, 0))
        && t.checkExpect(this.mauve.getFinalColor(), new Color(126, 63, 63));
  }

  // test the method countPaints in class IPaint 
  boolean testCountPaints(Tester t) {
    return t.checkExpect(this.red.countPaints(), 1)
        && t.checkExpect(this.darkPurple.countPaints(), 3)
        && t.checkExpect(this.mauve.countPaints(), 4);
  }

  // test the method countMixes in class IPaint 
  boolean testCountMixes(Tester t) {
    return t.checkExpect(this.red.countMixes(), 0)
        && t.checkExpect(this.darkPurple.countMixes(), 2)
        && t.checkExpect(new Blend(darkPurple, darkPurple).countMixes(), 5);
  }

  // test the method formulaDepth in class IPaint 
  boolean testFormulaDepth(Tester t) {
    return t.checkExpect(this.khaki.formulaDepth(), 1)
        && t.checkExpect(this.gray.formulaDepth(), 4)
        && t.checkExpect(this.darkPurple.formulaDepth(), 2)
        && t.checkExpect(new Blend(darkPurple, darkPurple).formulaDepth(), 3);
  }

  // test the method invert in class IPaint 
  boolean testInvert(Tester t) {
    return t.checkExpect(this.khaki.invert(), this.khaki)
        && t.checkExpect(this.pink.invert(), new Combo("pink", new Darken(mauve)))
        && t.checkExpect(this.darkPurple.invert(), 
            new Combo("dark purple", new Brighten(purple)))
        && t.checkExpect(this.coral.invert(), 
            new Combo("coral", 
                new Blend(new Combo("pink", new Darken(mauve)), khaki)));
  }

  // test the method mixingFormula in class IPaint
  boolean testMixingFormula(Tester t) {
    return t.checkExpect(this.pink.mixingFormula(0), "pink")
        && t.checkExpect(this.pink.mixingFormula(2), 
            "brighten(blend(purple, khaki))")
        && t.checkExpect(this.pink.mixingFormula(3),
            "brighten(blend(blend(red, blue), blend(red, green)))");
  }
}

