import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//Represents a single square of the game area
class Cell {
  // x y represent the coordinate of the cell of its top left corner 
  //in a logical sense not the coordinate of the actual board, 
  // for example, the first cell would be 0, 0 
  // and second cell would be 1, 0 for its top left corner 
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  // constructor for the actual board that connect each other 
  Cell(int x, int y, Color color, boolean flooded, 
      Cell left, Cell top, Cell right, Cell bottom) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  // constructor for creating initial board without connecting 
  // each cell 
  Cell(int x, int y, Color color, boolean flooded) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
  }

  // to render the image of this cell 
  WorldImage drawCell(int cellsize) {
    return new RectangleImage(cellsize, 
        cellsize, OutlineMode.SOLID, color);
  }
}

// To represent the worldstate 
class FloodItWorld extends World {
  // Defines an int constant to represent the size of the whole board 
  static final int BOARD_SIZE = 800;

  // Defines an int constant to reoresent the size of white spaces surrounds the board 
  static final int WHITESPACE_SIZE = 100;

  // Defines an int constant to represent the tick rate 
  static final double TICKRATE = 0.05;

  // represent a list of colors that can be choose from 
  static final ArrayList<Color> COLORS = new ArrayList<Color>(Arrays.asList(Color.red, 
      Color.blue, Color.green, Color.pink, Color.yellow, Color.cyan));

  Random rand;
  // All the cells of the game
  ArrayList<Cell> board;
  // To represent the number of cells in a row or column 
  int gridSize;
  int numColors;
  // whether the Game is flooding or not 
  boolean flooding;
  Color newC;
  Color oldC;
  int numClick;
  double timer;

  // constructor 
  FloodItWorld(int gridSize, int numColors) {
    this.rand = new Random();
    ArrayList<Cell> arr = new ArrayList<Cell>();

    // to make sure the number of colors of the board is up to six
    if (numColors <= 6) {
      this.numColors = numColors;
    }
    else {
      throw new IllegalArgumentException("Only up to six colors");
    }

    // to create a list of cells of random colors 
    for (int i = 0; i < gridSize; i ++) {
      for (int j = 0; j < gridSize; j++) {
        arr.add(new Cell(j, i, randomColor(numColors), false));
      }
    }

    // to connect each cells of this FloodItWorld 
    for (int i = 0; i < gridSize * gridSize; i++) {
      if (i >= gridSize) {
        arr.get(i).top = arr.get(i - gridSize);
      }
      if (i < gridSize * (gridSize - 1)) {
        arr.get(i).bottom = arr.get(i + gridSize);
      }

      if (i  % gridSize != 0) {
        arr.get(i).left = arr.get(i - 1);
      }

      if ((i - gridSize + 1) % gridSize != 0) {
        arr.get(i).right = arr.get(i + 1);
      }
    }
    this.board = arr;
    this.gridSize = gridSize;
    this.flooding = false;
    this.oldC = board.get(0).color;
    this.newC = oldC;
    this.numClick = 0;
    this.timer = 0;
  }

  //Constructor for testing random 
  FloodItWorld(int gridSize, int numColors, Random rand) {
    this.rand = rand;
    ArrayList<Cell> arr = new ArrayList<Cell>();

    // to make sure the number of colors of the board is up to six
    if (numColors <= 6) {
      this.numColors = numColors;
    }
    else {
      throw new IllegalArgumentException("Only up to six colors");
    }

    // to create a list of cells of random colors 
    for (int i = 0; i < gridSize; i ++) {
      for (int j = 0; j < gridSize; j++) {
        arr.add(new Cell(j, i, randomColor(numColors), false));
      }
    }

    // to connect each cells of this FloodItWorld 
    for (int i = 0; i < gridSize * gridSize; i++) {
      if (i >= gridSize) {
        arr.get(i).top = arr.get(i - gridSize);
      }
      if (i < gridSize * (gridSize - 1)) {
        arr.get(i).bottom = arr.get(i + gridSize);
      }

      if (i  % gridSize != 0) {
        arr.get(i).left = arr.get(i - 1);
      }

      if ((i - gridSize + 1) % gridSize != 0) {
        arr.get(i).right = arr.get(i + 1);
      }
    }
    this.board = arr;
    this.gridSize = gridSize;
    this.flooding = false;
    this.oldC = board.get(0).color;
    this.newC = oldC;
    this.numClick = 0;
    this.timer = 0;
  }

  // constructor for testing handlers
  FloodItWorld(int gridSize, int numColors, Random rand,
      ArrayList<Cell> board, boolean flooding, int numClick,
      double timer, Color oldC, Color newC) {
    this.gridSize = gridSize;
    this.numColors = numColors;
    this.rand = rand;
    this.board = board;
    this.flooding = flooding;
    this.numClick = numClick;
    this.timer = timer;
    this.oldC = oldC;
    this.newC = newC;
  }


  // To randomly generate a color 
  Color randomColor(int numColors) {
    int r = rand.nextInt(numColors);
    return FloodItWorld.COLORS.get(r);
  }

  // render the image of the cells and board on to the WorldScene
  public WorldScene makeScene() {
    WorldScene s = getEmptyScene();
    int cellsize = FloodItWorld.BOARD_SIZE / this.gridSize;
    int boardsize = FloodItWorld.BOARD_SIZE + FloodItWorld.WHITESPACE_SIZE * 2;
    int halfwhite = FloodItWorld.WHITESPACE_SIZE / 2;
    int numChance = (int)(gridSize * 2 * numColors * 0.1488);

    // render the list of cells on to the board 
    for (int i = 0; i < gridSize * gridSize; i ++) {
      s.placeImageXY(board.get(i).drawCell(cellsize), 
          board.get(i).x * cellsize 
          + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE,
          board.get(i).y * cellsize 
          + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE);
    }

    // render the number of times clicked 
    // and the number of click allowed on to the board 
    s.placeImageXY(new TextImage(
        numClick + "/" + numChance, halfwhite - 10, Color.black), 
        FloodItWorld.WHITESPACE_SIZE, boardsize - halfwhite);

    // render the instruction on the board 
    s.placeImageXY(new AboveAlignImage(AlignModeX.LEFT,
        new TextImage("↑↓To ReSet GridSize", (halfwhite / 2), Color.black),
        new TextImage("←→To ReSet NumColors", (halfwhite / 2), Color.black)), 
        boardsize - FloodItWorld.WHITESPACE_SIZE * 2, boardsize - halfwhite);

    // render the timer on the board 
    s.placeImageXY(new TextImage("" + (int)timer, halfwhite - 20, Color.black), 
        boardsize - halfwhite * 2, halfwhite);

    // render the number of colors and gridSize on the board 
    s.placeImageXY(new AboveAlignImage(AlignModeX.LEFT, 
        new TextImage(gridSize + " X " + gridSize, halfwhite / 2, Color.black),
        new TextImage(numColors + " Colors", halfwhite / 2, Color.black)), 
        halfwhite * 2, halfwhite);

    // render the title and win or lose on the board 
    if (isFlooded() && this.numClick <= numChance) {
      s.placeImageXY(new TextImage("Win!", halfwhite, this.board.get(0).color), 
          boardsize / 2, halfwhite);
      s.placeImageXY(new TextImage("Please Press R", halfwhite, Color.black), 
          boardsize / 2, boardsize / 2);
    }

    else if (!isFlooded() && this.numClick > numChance) {
      s.placeImageXY(new TextImage("Lose!", halfwhite, Color.red), 
          boardsize / 2, halfwhite);
    }

    else if (isFlooded() && this.numClick > numChance) {
      s.placeImageXY(new TextImage("Lose!", halfwhite, Color.red), 
          boardsize / 2, halfwhite);
      s.placeImageXY(new TextImage("Please Press R", halfwhite, Color.black), 
          boardsize / 2, boardsize / 2);
    }

    else {
      s.placeImageXY(new TextImage("Flood It World", halfwhite, Color.black), 
          boardsize / 2, halfwhite);
    }
    return s;
  }

  // on tick simulator 
  public void onTick() {
    // show the flood effect of the game 
    if (flooding) {
      board.get(0).flooded = true;
      boolean floodingDone = true;
      ArrayList<Cell> flooded = new ArrayList<Cell>();

      for (int i = 0; i < gridSize * gridSize; i++) {
        if (board.get(i).flooded) {
          flooded.add(board.get(i));
        }
      }

      for (int i = 0; i < flooded.size(); i++) {
        Cell top = flooded.get(i).top;
        Cell bottom = flooded.get(i).bottom;
        Cell left = flooded.get(i).left;
        Cell right = flooded.get(i).right;

        if (top != null && top.color.equals(oldC)) {
          top.color = newC; 
          top.flooded = true; 
          floodingDone = false;
        }

        if (bottom != null && bottom.color.equals(oldC)) {
          bottom.color = newC; 
          bottom.flooded = true; 
          floodingDone = false;
        }

        if (left != null && left.color.equals(oldC)) {
          left.color = newC; 
          left.flooded = true; 
          floodingDone = false;
        }

        if (right != null && right.color.equals(oldC)) {
          right.color = newC; 
          right.flooded = true; 
          floodingDone = false;
        }
        flooded.get(i).flooded = false;
      }

      if (floodingDone) {
        oldC = newC;
        flooding = false;
      }
    }
    // count time each tick rate 
    timer = timer + TICKRATE;
  }

  // mouse click simulator 
  public void onMouseClicked(Posn pos) {
    int cellsize = FloodItWorld.BOARD_SIZE / this.gridSize;

    for (int i = 0; i < gridSize * gridSize; i++) {
      if (Math.abs((board.get(i).x * cellsize + cellsize / 2 +
          FloodItWorld.WHITESPACE_SIZE) - pos.x) < cellsize / 2
          && Math.abs((board.get(i).y * cellsize + cellsize / 2 +
              FloodItWorld.WHITESPACE_SIZE) - pos.y) < cellsize / 2) {
        if (!flooding && !board.get(i).color.equals(newC)) {
          newC = board.get(i).color;
          board.get(0).color = newC;
          flooding = true;
          numClick += 1;
        }
      }
    }
  }

  // to represent the initial condition 
  public void init() {
    ArrayList<Cell> arr = new ArrayList<Cell>();

    for (int i = 0; i < gridSize; i ++) {
      for (int j = 0; j < gridSize; j++) {
        arr.add(new Cell(j, i, randomColor(numColors), false));
      }
    }

    for (int i = 0; i < gridSize * gridSize; i++) {
      if (i >= gridSize) {
        arr.get(i).top = arr.get(i - gridSize);
      }
      if (i < gridSize * (gridSize - 1)) {
        arr.get(i).bottom = arr.get(i + gridSize);
      }

      if (i  % gridSize != 0) {
        arr.get(i).left = arr.get(i - 1);
      }

      if ((i - gridSize + 1) % gridSize != 0) {
        arr.get(i).right = arr.get(i + 1);
      }
    }
    this.board = arr;
    this.flooding = false;
    this.oldC = board.get(0).color;
    this.newC = oldC;
    this.numClick = 0;
    this.timer = 0;
  }

  // to reset the game is r is pressed 
  public void onKeyEvent(String key) {
    if (key.equals("r") || key.equals("R") ) {
      init();
    }

    if (key.equals("up") && gridSize < 20) {
      gridSize = gridSize + 1;
      init();
    }

    if (key.equals("down") && gridSize > 2) {
      gridSize = gridSize - 1;
      init();
    }

    if (key.equals("left") && numColors > 2) {
      numColors = numColors - 1;
      init();
    }

    if (key.equals("right") && numColors < 6) {
      numColors = numColors + 1;
      init();
    }
  }

  // is every single cells flooded? 
  public boolean isFlooded() {
    int index = 0;
    boolean result = true;
    while (index < (board.size() - 1) && result) {
      if (board.get(index).color != board.get(index + 1).color) {
        result = false;
      }
      else {
        index = index + 1;
      }
    }
    return result;
  }
}


class ExampleGameBoard {
  FloodItWorld world = new FloodItWorld(3, 3);

  Cell cell0 = new Cell(0, 0, Color.BLUE, false, null, null,
      this.cell1, this.cell2);
  Cell cell1 =  new Cell(1, 0, Color.RED, false, cell0, null,
      null, this.cell3);
  Cell cell2 = new Cell(0, 1, Color.CYAN, false, null, cell0,
      this.cell3, null);
  Cell cell3 = new Cell(1, 1, Color.BLUE, false, cell2, cell1,
      null, null);
  ArrayList<Cell> board1 = new ArrayList<Cell>(Arrays.asList(
      cell0, cell1, cell2, cell3));
  FloodItWorld world2 = new FloodItWorld(2, 2, new Random(2),
      board1, false, 0, 0.0, Color.BLUE, Color.RED);

  // initial condition
  void init() {
    cell0 = new Cell(0, 0, Color.BLUE, false, null, null,
        this.cell1, this.cell2);
    cell1 =  new Cell(1, 0, Color.RED, false, cell0, null,
        null, this.cell3);
    cell2 = new Cell(0, 1, Color.CYAN, false, null, cell0,
        cell3, null);
    cell3 = new Cell(1, 1, Color.BLUE, false, cell2, cell1,
        null, null);
    board1 = new ArrayList<Cell>(Arrays.asList(
        cell0, cell1, cell2, cell3));
    world2 = new FloodItWorld(2, 2, new Random(2),
        board1, false, 0, 0.0, Color.BLUE, Color.RED);
  }

  // test the method DrawCell
  void testDrawCell(Tester t) {
    Cell c1 = new Cell(0, 0, Color.BLACK, false);
    Cell c2 = new Cell(4, 6, Color.BLUE, false);
    Cell c3 = new Cell(2, 3, Color.CYAN, true);
    t.checkExpect(c1.drawCell(200), new RectangleImage(200, 200, OutlineMode.SOLID, Color.BLACK));
    t.checkExpect(c2.drawCell(160), new RectangleImage(160, 160, OutlineMode.SOLID, Color.BLUE));
    t.checkExpect(c3.drawCell(80), new RectangleImage(80, 80, OutlineMode.SOLID, Color.CYAN));
  }

  // test constructor to see whether cells in the list are connected
  void testConnectness(Tester t) {
    t.checkExpect(world.board.size(), 9);
    t.checkExpect(world.board.get(0).right, world.board.get(1));
    t.checkExpect(world.board.get(0).left, null);
    t.checkExpect(world.board.get(0).top, null);
    t.checkExpect(world.board.get(0).bottom, world.board.get(3));
    t.checkExpect(world.board.get(1).left, world.board.get(0));
    t.checkExpect(world.board.get(4).top, world.board.get(1));
  }

  // test Illegal Argument 
  boolean testValidNumColors(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Only up to six colors"), 
        "FloodItWorld", 1, 8);
  }

  // test the method randomColor
  void testRandomColor(Tester t) {
    int randomSeed = 5;
    Random r = new Random(randomSeed);
    int num = r.nextInt(6);
    int num2 = r.nextInt(6);
    FloodItWorld world1 = new FloodItWorld(1, 1, new Random(randomSeed));
    t.checkExpect(world1.randomColor(6), FloodItWorld.COLORS.get(num2));
  }

  // test the method makeScene 
  void testMakeScene(Tester t) {
    init();

    WorldScene s = world2.getEmptyScene();

    int cellsize = FloodItWorld.BOARD_SIZE / world2.gridSize;
    int boardsize = FloodItWorld.BOARD_SIZE + FloodItWorld.WHITESPACE_SIZE * 2;
    int halfwhite = FloodItWorld.WHITESPACE_SIZE / 2;
    int numChance = (int)(world2.gridSize * 2 * world2.numColors * 0.1488);

    s.placeImageXY(world2.board.get(0).drawCell(cellsize), 
        world2.board.get(0).x * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE,
        world2.board.get(0).y * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE);
    s.placeImageXY(world2.board.get(1).drawCell(cellsize), 
        world2.board.get(1).x * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE,
        world2.board.get(1).y * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE);
    s.placeImageXY(world2.board.get(2).drawCell(cellsize), 
        world2.board.get(2).x * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE,
        world2.board.get(2).y * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE);
    s.placeImageXY(world2.board.get(3).drawCell(cellsize), 
        world2.board.get(3).x * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE,
        world2.board.get(3).y * cellsize 
        + (cellsize / 2) + FloodItWorld.WHITESPACE_SIZE);
    s.placeImageXY(new TextImage(
        0 + "/" + numChance,  - 10, Color.black), 
        FloodItWorld.WHITESPACE_SIZE, boardsize - halfwhite);
    s.placeImageXY(new AboveAlignImage(AlignModeX.LEFT,
        new TextImage("↑↓To ReSet GridSize", (halfwhite / 2), Color.black),
        new TextImage("←→To ReSet NumColors", (halfwhite / 2), Color.black)), 
        boardsize - FloodItWorld.WHITESPACE_SIZE * 2, boardsize - halfwhite);
    s.placeImageXY(new TextImage("" + (int)world2.timer, halfwhite - 20, Color.black), 
        boardsize - halfwhite * 2, halfwhite);
    s.placeImageXY(new AboveAlignImage(AlignModeX.LEFT, 
        new TextImage(world2.gridSize + " X " + world2.gridSize, halfwhite / 2, Color.black),
        new TextImage(world2.numColors + " Colors", halfwhite / 2, Color.black)), 
        halfwhite * 2, halfwhite);
    s.placeImageXY(new TextImage("Flood It World", halfwhite, Color.black), 
        boardsize / 2, halfwhite);
    t.checkExpect(world2.makeScene(), s);

    init();
    world2.board.get(0).color = Color.red;
    world2.board.get(2).color = Color.red;
    world2.board.get(3).color = Color.red;
    world2.numClick = 2;
    s.placeImageXY(new TextImage("Win!", halfwhite, world2.board.get(0).color), 
        boardsize / 2, halfwhite);
    s.placeImageXY(new TextImage("Please Press R", halfwhite, Color.black), 
        boardsize / 2, boardsize / 2);
    t.checkExpect(world2.makeScene(), s);

    world2.numClick = 10;
    s.placeImageXY(new TextImage("Lose!", halfwhite, Color.red), 
        boardsize / 2, halfwhite);
    s.placeImageXY(new TextImage("Please Press R", halfwhite, Color.black), 
        boardsize / 2, boardsize / 2);
    t.checkExpect(world2.makeScene(), s);

    init();
    world2.numClick = 10;
    s.placeImageXY(new TextImage("Lose!", halfwhite, Color.red), 
        boardsize / 2, halfwhite);
    t.checkExpect(world2.makeScene(), s);
  }

  // test the method onTick
  void testOnTick(Tester t) {
    // test timer 
    init();
    t.checkExpect(world2.timer, 0.0);
    world2.onTick();
    t.checkExpect(world2.timer, 0.05);

    // test the flooding effect 
    FloodItWorld worldTest = new FloodItWorld(2, 2);

    worldTest.board.get(0).color = Color.blue;
    worldTest.board.get(1).color = Color.blue;
    worldTest.board.get(2).color = Color.RED;
    worldTest.board.get(3).color = Color.blue;

    worldTest.oldC = Color.BLUE; 
    worldTest.newC = Color.red;
    worldTest.flooding = true;
    worldTest.onTick();

    t.checkExpect(worldTest.board.get(0).color, Color.blue);
    t.checkExpect(worldTest.board.get(0).flooded, false);
    t.checkExpect(worldTest.board.get(1).color, Color.red);
    t.checkExpect(worldTest.board.get(1).flooded, true);
    t.checkExpect(worldTest.board.get(2).color, Color.red);
    t.checkExpect(worldTest.board.get(2).flooded, false);
    t.checkExpect(worldTest.board.get(3).color, Color.BLUE);   

  }

  // test the method mouseClicked
  void testMouseClicked(Tester t) {
    int cellsize = FloodItWorld.BOARD_SIZE / 2;

    FloodItWorld worldTest = new FloodItWorld(2, 2);

    worldTest.board.get(0).color = Color.blue;
    worldTest.board.get(1).color = Color.blue;
    worldTest.board.get(2).color = Color.blue;
    worldTest.board.get(3).color = Color.red;
    worldTest.oldC = Color.blue;
    worldTest.newC = Color.blue;

    worldTest.onMouseClicked(new Posn(FloodItWorld.WHITESPACE_SIZE + cellsize * 2 - 1, 
        FloodItWorld.WHITESPACE_SIZE + cellsize * 2 - 1));

    t.checkExpect(worldTest.flooding, true);
    t.checkExpect(worldTest.newC, Color.red);
    t.checkExpect(worldTest.board.get(0).color, Color.red);
    t.checkExpect(worldTest.board.get(0).flooded, false);
    t.checkExpect(worldTest.board.get(1).color, Color.blue);
    t.checkExpect(worldTest.board.get(1).flooded, false);
    t.checkExpect(worldTest.board.get(2).color, Color.blue);
    t.checkExpect(worldTest.board.get(2).flooded, false);
    t.checkExpect(worldTest.board.get(3).color, Color.red);
    t.checkExpect(worldTest.board.get(3).flooded, false);
  }

  // test the method init() 
  void testInit(Tester t) {
    init();
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.flooding, false);
    t.checkExpect(world2.oldC, Color.blue);
    t.checkExpect(world2.newC, Color.red);
    t.checkExpect(world2.numClick, 0);
    t.checkExpect(world2.timer, 0.0);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);
    world2.init();
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.flooding, false);
    t.checkExpect(world2.oldC, world2.board.get(0).color);
    t.checkExpect(world2.newC, world2.oldC);
    t.checkExpect(world2.numClick, 0);
    t.checkExpect(world2.timer, 0.0);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);
  }

  // test the method onKeyEvent
  void testOnKeyEvent(Tester t) {
    init();
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.flooding, false);
    t.checkExpect(world2.oldC, Color.blue);
    t.checkExpect(world2.newC, Color.red);
    t.checkExpect(world2.numClick, 0);
    t.checkExpect(world2.timer, 0.0);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);

    world2.onKeyEvent("r");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.flooding, false);
    t.checkExpect(world2.oldC, world2.board.get(0).color);
    t.checkExpect(world2.newC, world2.oldC);
    t.checkExpect(world2.numClick, 0);
    t.checkExpect(world2.timer, 0.0);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);

    init();
    world2.onKeyEvent("R");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.flooding, false);
    t.checkExpect(world2.oldC, world2.board.get(0).color);
    t.checkExpect(world2.newC, world2.oldC);
    t.checkExpect(world2.numClick, 0);
    t.checkExpect(world2.timer, 0.0);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);

    init();
    world2.onKeyEvent("up");
    t.checkExpect(world2.board.size(), 9);
    t.checkExpect(world2.gridSize, 3);
    t.checkExpect(world2.numColors, 2);
    init();
    world2.gridSize = 20;
    world2.onKeyEvent("up");
    t.checkExpect(world2.gridSize, 20);
    t.checkExpect(world2.numColors, 2);

    init();
    world2.onKeyEvent("down");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);
    init();
    world2.gridSize = 4;
    world2.onKeyEvent("down");
    t.checkExpect(world2.board.size(), 9);
    t.checkExpect(world2.gridSize, 3);
    t.checkExpect(world2.numColors, 2);

    init();
    world2.onKeyEvent("right");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 3);
    init();
    world2.numColors = 6;
    world2.onKeyEvent("right");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 6);

    init();
    world2.onKeyEvent("left");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 2);
    init();
    world2.numColors = 6;
    world2.onKeyEvent("left");
    t.checkExpect(world2.board.size(), 4);
    t.checkExpect(world2.gridSize, 2);
    t.checkExpect(world2.numColors, 5);
  }

  // test the method isFlooded 
  void testIsFlooded(Tester t) {
    init();
    t.checkExpect(world2.isFlooded(), false);
    world2.board.get(0).color = Color.red;
    world2.board.get(2).color = Color.red;
    world2.board.get(3).color = Color.red;
    t.checkExpect(world2.isFlooded(), true); 
  }

  // bigBang 
  void testBigBang(Tester t) {
    FloodItWorld w = new FloodItWorld(15, 3);
    w.bigBang(FloodItWorld.BOARD_SIZE + 2 * FloodItWorld.WHITESPACE_SIZE, 
        FloodItWorld.BOARD_SIZE + 2 * FloodItWorld.WHITESPACE_SIZE, FloodItWorld.TICKRATE);
  }
}