// to represent a Statue 
class Statue {
  String name;
  String artist;
  int year;
  double weight;
  boolean whole;
  
  // constructor 
  Statue(String name, String artist, int year, double weight, boolean whole) {
    this.name = name;
    this.artist = artist;
    this.year = year;
    this.weight = weight; // this is measured in pounds
    this.whole = whole;
  }
}

// examples (&tests) for the classes that represent statues 
class ExamplesStatue {
  ExamplesStatue() {}

  Statue statueOfLiberty = new Statue("Statue Of Liberty", "Bartholdi", 
      1886, 450000.0, true);
  Statue venusDeMilo = new Statue("Venus de Milo", "Alexandros of Antioch", 
      -115, 1984.16, false); 
  Statue david = new Statue("David", "Michelangelo Via Ricasoli", 
      1504, 12478.0, false);
}