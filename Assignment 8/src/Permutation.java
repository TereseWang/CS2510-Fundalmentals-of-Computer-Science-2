import java.util.*;

import tester.Tester;

class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = 
      new ArrayList<Character>(Arrays.asList(
          'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
          'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
          't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code 
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code 
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> result = new ArrayList<Character>();
    this.code = new ArrayList<Character>();
    for (int i = 0; i < alphabet.size(); i++) {
      result.add(alphabet.get(i));
    }
    for (int i = 0; i < alphabet.size(); i++) {
      int index = rand.nextInt(result.size());
      this.code.add(result.get(index));
      result.remove(index);
    }
    return this.code;
  }

  // produce an encoded String from the given String
  String encode(String source) {
    String result = "";
    for (int i = 0; i < source.length(); i++) {
      result = result + code.get(find(source.charAt(i), alphabet));
    }
    return result;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    String result = "";
    for (int i = 0; i < code.length(); i++) {
      result = result + alphabet.get(find(code.charAt(i), this.code));
    }
    return result;
  }

  //return the index of char in the given list
  int find(Character c, ArrayList<Character> list) {
    for (int i = 0; i < list.size(); i ++) {
      if (list.get(i) == c) {
        return i;
      }
    }
    return 0;
  }
}

class ExampleCode {
  PermutationCode c = new PermutationCode();

  ArrayList<Character> code = 
      new ArrayList<Character>(Arrays.asList(
          'c', 'o', 'x', 'e', 'f', 'i', 'g', 'h', 'j', 'k', 
          'm', 'b', 't', 'n', 'p', 'q', 's', 'r', 'a', 
          'v', 'l', 'u', 'w', 'y', 'z', 'd'));
  PermutationCode c1 = new PermutationCode(code);

  // test the method initEncoder 
  // check whether the length of the randomly generated list is equal to 26
  // and also check whether the elements in the randomly generated list is 
  // in the alphabet list
  void testInitEncoder(Tester t) {
    t.checkExpect(new HashSet<>(c.initEncoder()).equals(new HashSet<>(c.alphabet)), true);
    t.checkExpect(c.initEncoder().size(), 26);
  }

  // test the method decode 
  void testDecode(Tester t) {
    t.checkExpect(c1.decode("cqqbf"), "apple");
    t.checkExpect(c1.decode("hfbbp"), "hello");
    t.checkExpect(c1.decode("wprbe"), "world");
  }

  // test the method encode 
  void testEncode(Tester t) {
    t.checkExpect(c1.encode("apple"), "cqqbf");
    t.checkExpect(c1.encode("hello"), "hfbbp");
    t.checkExpect(c1.encode("world"), "wprbe");
  }

  // test the method find 
  void testFind(Tester t) {
    t.checkExpect(c1.find('c', code), 0);
    t.checkExpect(c1.find('d', code), 25);
    t.checkExpect(c1.find('l', code), 20);
  }
}