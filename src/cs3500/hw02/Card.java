package cs3500.hw02;

/**
 * This is a class that represent Cards with suits and card value.
 */
public class Card {
  public Suits suit;
  public int value;

  /**
   * Constructs a card with suits and value.
   *
   * @param suit  the suit of the card
   * @param value the value of the card
   */
  public Card(Suits suit, int value) {
    this.suit = suit;
    if (value > 0 && value <= 13) {
      this.value = value;
    } else {
      throw new IllegalArgumentException("out of range");
    }

  }


  @Override
  public String toString() {
    switch (this.value) {
      case 1:
        return "A" + this.suit.getSymbol();
      case 11:
        return "J" + this.suit.getSymbol();
      case 12:
        return "Q" + this.suit.getSymbol();
      case 13:
        return "K" + this.suit.getSymbol();
      default:
        return Integer.toString(this.value) + this.suit.getSymbol();
    }
  }

  /**
   * Check if the given suit and this suit have alternating colors.
   *
   * @param pre a previous card from the same pile
   * @return true if this card and the given card have different color suits, false otherwise
   */
  public Boolean rightSuit(Card pre) {
    switch (suit) {
      case Heart:
        return ((pre.suit == Suits.Club || pre.suit == Suits.Spade) && this.value == pre.value - 1);
      case Diamond:
        return ((pre.suit == Suits.Club || pre.suit == Suits.Spade) && this.value == pre.value - 1);
      case Club:
        return ((pre.suit == Suits.Heart || pre.suit == Suits.Diamond)
                && this.value == pre.value - 1);
      case Spade:
        return ((pre.suit == Suits.Heart || pre.suit == Suits.Diamond)
                && this.value == pre.value - 1);
      default:
        return false;
    }
  }

  @Override
  public boolean equals(Object o) {
    return ((o instanceof Card) && (((Card) o).value == this.value)
            && ((Card) o).suit == this.suit);
  }

  @Override
  public int hashCode() {
    switch (suit) {
      case Spade:
        return this.value * 14;
      case Heart:
        return this.value * 15;
      case Diamond:
        return this.value * 16;
      case Club:
        return this.value * 17;
      default:
        return 0;
    }
  }


}
