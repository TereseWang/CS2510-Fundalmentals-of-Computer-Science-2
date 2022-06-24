// to represent a monster resource
// which is composed of monsters and fusions and traps 
interface IResource {
}

// to represent a fusion 
class Fusion implements IResource {
  String name;
  Monster monster1;
  Monster monster2;
  
  // constructor
  Fusion(String name, Monster monster1, Monster monster2) {
    this.name = name;
    this.monster1 = monster1;
    this.monster2 = monster2;
  }
}

// to represent a monster 
class Monster implements IResource {
  String name;
  int hp;
  int attack;
  
  // constructor 
  Monster(String name, int hp, int attack) {
    this.name = name;
    this.hp = hp;
    this.attack = attack;
  }
}

// to represent a trap
class Trap implements IResource {
  String description;
  boolean continuous; 
  //true if this effect is continuous,
  //false if it is not continuous  
  
  // constructor 
  Trap(String description, boolean continuous) {
    this.description = description;
    this.continuous = continuous;
  }
}

//to represent actions 
interface IAction {
}

//to represent an attack 
//this IResource exclude the traps
class Attack implements IAction {
  IResource attacker;
  IResource defender;
  
  // constructor
  Attack(IResource attacker, IResource defender) {
    this.attacker = attacker;
    this.defender = defender;
  }
}

//to represent an activate
class Activate implements IAction {
  IResource trap;
  IResource target;
  
  //constructor
  Activate(IResource trap, IResource target) {
    this.trap = trap;
    this.target = target;
  }
}

class ExamplesGame {
  ExamplesGame() {}
  
  //examples for the class hierarchy that represents monster resources
  Monster kuriboh = new Monster("Kuriboh", 200, 100);
  Monster jinzo = new Monster("Jinzo", 500, 400);
  Monster likio = new Monster("Likio", 300, 100);
  IResource kios = new Monster("Kios", 100, 100);
  IResource jinzo1 = this.jinzo;
  IResource kurizo = new Fusion("Kurizo", this.kuriboh, this.jinzo);
  IResource kurikio = new Fusion("Kurikio", this.kuriboh, this.likio);
  IResource trapHole = new Trap("Kills a monster", false);
  IResource trapHole1 = new Trap("Makes the monster's hp 100", true);

  //examples for the classes that represent the attack 
  Attack attack1 = new Attack(this.jinzo1, this.kios);
  Attack attack2 = new Attack(this.kurizo, this.kios);
  //examples for the classes that represent the activate 
  Activate activate1 = new Activate(this.trapHole, this.kurizo);
  Activate activate2 = new Activate(this.trapHole1, this.jinzo1);
}

/* This is not a good definition if we need to enforce
 * all the restrictions listed in the problem. For example
 * in the Attack part, which we only need monsters and fusions,
 * not traps, but when we are compounding them together 
 * as one resources, such restriction cannot be enforced,
 * since monsters and fusions should be compound together
 * to be either the attacker or defender. I would say,
 * it would be better if we compound monsters and fusions
 * together rather than three things all together.  
 */















