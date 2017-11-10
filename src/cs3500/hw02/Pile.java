package cs3500.hw02;

import java.util.List;
import java.util.ArrayList;

/**
 * Representing a pile of cards with the pile type and the list of cards in that pile.
 */
public class Pile {
  public PileType type;
  public List<Card> cards;

  /**
   * Constructs a empty pile of the given pile type.
   *
   * @param type the PileType of the pile
   */
  public Pile(PileType type) {
    cards = new ArrayList<Card>();
    switch (type) {
      case OPEN:
        this.cards = new ArrayList<Card>();
        break;
      case CASCADE:
        this.cards = new ArrayList<Card>();
        break;
      case FOUNDATION:
        this.cards = new ArrayList<Card>();
        break;
      default:
        new IllegalArgumentException("Wrong type of file");
    }
  }

  /**
   * The representation of the pile in string format.
   *
   * @return a string of the pile
   */
  @Override
  public String toString() {
    String pileString = "";
    for (int i = 0; i < this.cards.size(); i++) {
      if (i == 0) {
        pileString = pileString + " " + this.cards.get(i).toString();
      } else {
        pileString = pileString + ", " + this.cards.get(i).toString();
      }
    }
    return pileString;
  }
}
