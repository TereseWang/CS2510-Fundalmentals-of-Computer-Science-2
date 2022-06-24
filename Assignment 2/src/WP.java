import tester.Tester;

//to represent a WebPage
class WebPage {
  String title;
  String url;
  ILoItem items;

  //constructor
  WebPage(String title, String url, ILoItem items) {
    this.title = title;
    this.url = url;
    this.items = items;
  }

  // to compute the total size of all images of this WebPage
  int totalImageSize() {
    /* Template:
     * Fields:
     * this.title -- String
     * this.url -- String 
     * this.items -- ILoItem 
     * Methods:
     * this.totalImageSize() -- int 
     * this.textLength() -- int
     * this.images() -- String 
     * this.imageHelper() -- String 
     * Methods of Fields 
     * this.items.totalImageSize() -- int 
     * this.items.textLength() -- int
     * this.items.images() -- String
     * this.items.imagesHelper() -- String
     */
    return this.items.totalImageSize();
  }

  // to compute the number of letters in all text appeares on this WebPage
  // including contents of the text, the names of the image files plus the file type
  // and the labels for links and the title for this WebPage
  int textLength() {
    return this.title.length() + this.items.textLength();
  }

  // to computer a string that contains all the names of images of this WebPage
  // seperate by a comma and a space for each image, but not the last one 
  String images() {
    String str = imagesHelper();
    if (str.length() > 1) {
      return str.substring(0, str.length() - 2);
    }
    else {
      return str;
    }
  }

  //to computer a string that contains all the names of images of this WebPage
  // Separate by a comma and a space for each image, including the last one 
  String imagesHelper() {
    return this.items.images();
  }
}

//to represent an List of Items (ILoItem)
interface ILoItem {
  //to compute the total size of all images of this list of items  
  int totalImageSize();
  
  //to compute the number of letters in all text appeares in this list of items 
  int textLength();
  
  // produce a string that contains names of all images in this list of items 
  String images();
}

// to represent an empty list
class MtLoItem implements ILoItem {
  MtLoItem() {}
  
  // nothing to do 
  public int totalImageSize() {
    return 0;
  }
  
  public int textLength() {
    return 0;
  }
  
  // return an empty string 
  public String images() {
    return "";
  }
}

// to represent a list of items 
class ConsLoItem implements ILoItem {
  IItem first;
  ILoItem rest;

  //constructor 
  ConsLoItem(IItem first, ILoItem rest) {
    this.first = first;
    this.rest = rest;
  }

  // to compute the total size of all images in this list of items 
  public int totalImageSize() {
    /* Templates:
     * Fields:
     * this.first -- IItem 
     * this.rest -- ILoItem 
     * Methods:
     * this.totalImageSize() -- int 
     * this.textLength() -- int
     * this.images() -- String 
     * Methods of Fields:
     * this.first.totalImageSize() -- int 
     * this.first.textLength() -- int 
     * this.first.images() -- String 
     * this.rest.totalImageSize() -- int 
     * this.rest.textLength() -- int 
     * this.rest.images() -- String 
     */
    return this.first.totalImageSize() + this.rest.totalImageSize();
  }

  // to compute the total number of texts in this list of items 
  public int textLength() {
    return this.first.textLength() + this.rest.textLength();
  }

  // to produce a string that contains of the names of images in this list of items 
  public String images() {
    return this.first.images() + this.rest.images();
  }
}

// to represent an item 
interface IItem {
  // to compute the total size of images in this IItem
  int totalImageSize();
  
  // to compute the total number of texts in this IItem 
  int textLength();
  
  // to produce a string that contains all names in this IItem
  String images();
}

// to represent an text 
class Text implements IItem {
  String contents;

  //constructor
  Text(String contents) {
    this.contents = contents;
  }

  //nothing to do 
  public int totalImageSize() {
    /* Templates:
     * this.contents ... String 
     * Methods: 
     * this.totalImageSize() -- int 
     * this.textLength() -- int 
     * this.images() -- String 
     */
    return 0;
  }
  
  // return the length of this text 
  public int textLength() {
    return this.contents.length();
  }
  
  // return an empty string 
  public String images() {
    return "";
  }
}

//to represent an image 
class Image implements IItem {
  String fileName;
  int size;
  String fileType;

  //constructor 
  Image(String filename, int size, String filetype) {
    this.fileName = filename;
    this.size = size;
    this.fileType = filetype;
  }

  public int totalImageSize() {
    /* Templates:
     * this.filename -- String 
     * this.size -- int 
     * this.filetype -- String
     * Methods: 
     * this.totalImageSize() -- int 
     * this.textLength() -- int 
     * this.images() -- String 
     */
    return this.size;
  }

  public int textLength() {
    return this.fileName.length() + this.fileType.length();
  }

  public String images() {
    return this.fileName + "." + this.fileType + ", ";
  }
}

// to represent a Link 
class Link implements IItem {
  String name;
  WebPage page;

  //constructor 
  Link(String name, WebPage page) {
    this.name = name;
    this.page = page;
  }

  public int totalImageSize() {
    /* Templates:
     * this.name -- String 
     * this.page -- WebPage 
     * Methods: 
     * this.totalImageSize() -- int 
     * this.textLength() -- int 
     * this.images() -- String 
     * Methods of fields 
     * this.page.totalImageSize() -- int 
     * this.page.textLength() -- int 
     * this.page.images() -- String 
     * this.page.imagesHelper() -- String 
     */
    return this.page.totalImageSize();
  }

  public int textLength() {
    return this.name.length() + page.textLength();
  }

  // return a String that contains all the names of images in this Link
  public String images() {
    return this.page.imagesHelper();
  }
}

// examples and test in the class WebPage 
class ExamplesWebPage {
  ExamplesWebPage() {} 
  
  MtLoItem mtList = new MtLoItem();

  Text htdpt1 = new Text("How to Design Programs");
  Image htdpi1 = new Image("htdp", 4300, "tiff");
  ILoItem htdpLoi = new ConsLoItem(htdpt1, new ConsLoItem(htdpi1, mtList));
  WebPage HtDP = new WebPage("HtDP", "htdp.org",htdpLoi);

  Text oodt1 = new Text("Stay classy, Java");
  Link oodl1 = new Link("Back to the Future", HtDP);
  ILoItem oodLoi = new ConsLoItem(oodt1, new ConsLoItem(oodl1, mtList));
  WebPage OOD = new WebPage("OOD", "ccs.neu.edu/OOD", oodLoi);

  Text t1 = new Text("Home sweet home");
  Image i1 = new Image("wvh-lab", 400, "png");
  Text t2 = new Text("The staff");
  Image i2 = new Image("profs", 240, "jpeg");
  Link l1 = new Link("A Look Back", HtDP);
  Link l2 = new Link("A Look Ahead", OOD);
  ILoItem fundisLoi = new ConsLoItem(t1, 
      new ConsLoItem(i1, 
          new ConsLoItem(t2, 
              new ConsLoItem(i2, 
                  new ConsLoItem(l1, 
                      new ConsLoItem(l2, mtList))))));
  WebPage fundiesWP = new WebPage("Fundies II", "ccs.neu.edu/Fundies2", 
      this.fundisLoi);

  IItem ratei1 = new Image("housing", 300, "png");
  ILoItem rateLoi = new ConsLoItem(ratei1, mtList);
  WebPage RATE = new WebPage("Room Rates", 
      "northeastern.edu/housing/rate", rateLoi);

  IItem hui1 = new Image("housing", 300, "png");
  IItem hul1 = new Link("Rates", RATE);
  ILoItem uhLoi = new ConsLoItem(hui1, new ConsLoItem(hul1, mtList));
  WebPage UH = new WebPage("University Housing",
      "northeastern.edu/housing/university",uhLoi);

  IItem movei1 = new Text("Move-in/out Calender");
  IItem movel1 = new Link("First Year", UH);
  ILoItem calenderLoi = new ConsLoItem(movei1, new ConsLoItem(movel1, mtList));
  WebPage Calender = new WebPage("Move Dates",
      "northeastern.edu/housing/calender", calenderLoi);

  IItem webt1 = new Text("Living At Northeastern");
  IItem webi1 = new Image("application", 100, "jpeg");
  IItem webi2 = new Image("housing", 300, "png");
  IItem webl1 = new Link("First Year", UH);
  IItem webl2 = new Link("Move in/out", Calender);
  IItem webl3 = new Link("Rates", RATE);
  IItem webl4 = new Link("University Housing", UH);
  ILoItem webLoi = new ConsLoItem(webt1, 
      new ConsLoItem(webi1, 
          new ConsLoItem(webi2, 
              new ConsLoItem(webl1, 
                  new ConsLoItem(webl2, 
                      new ConsLoItem(webl3, 
                          new ConsLoItem(webl4, mtList)))))));
  WebPage web = new WebPage("University Housing Online", "northeastern.edu/housing",webLoi);

  // test the method totalImageSize in the class WebPage 
  boolean testTotalImageSize(Tester t) {
    return t.checkExpect(this.fundiesWP.totalImageSize(),9240);
  }

  // test the method textLength in the class WebPage 
  boolean testTextLength(Tester t) {
    return t.checkExpect(this.fundiesWP.textLength(), 182);
  }

  // test the method images in the class WebPage 
  boolean testImages(Tester t) {
    return t.checkExpect(this.fundiesWP.images(), "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff");
  }
}

/* For the example fundiesWP, the images of htdp will appear twice, since there are 
 * two links that both goes to the htdp, which will load the page htdp to show up twice.
 * Therefore the method images will double count those from htdp page, since it reaches htdp twice.
 * Other methods, such as textLength and totalImageSize have the similar behavior. 
 */


