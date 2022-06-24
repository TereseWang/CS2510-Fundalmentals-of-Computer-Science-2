// to represent a soup 
interface ISoup {
}

// to represent a broth of a soup 
class Broth implements ISoup {
  String type;
  
  // constructor 
  Broth(String type) {
    this.type = type;
  }
}

// to represent an ingredient of a soup 
class Ingredient implements ISoup {
  ISoup more;
  String name;
  
  // constructor 
  Ingredient(ISoup more, String name) {
    this.more = more;
    this.name = name;
  }
}

//examples for the class hierarchy that represents soup 
class ExamplesSoup {
  ExamplesSoup() {}
  
  ISoup chicken = new Broth("chicken");
  ISoup vanilla = new Broth("vanilla");
  ISoup carrots = new Ingredient(this.chicken, "carrots");
  ISoup horseradish = new Ingredient(this.vanilla, "horseradish");
  ISoup celery = new Ingredient(this.carrots, "celery");
  ISoup hotDog = new Ingredient(this.horseradish, "hot dogs");
  ISoup yummy = new Ingredient(this.celery, "noodles");
  ISoup noThankYou = new Ingredient(this.hotDog, "plum sauce");
}
