package cs3500.hw02;

/**
 * A enumeration representing different suits with different symbols.
 */
public enum Suits {
  Heart('♥'), Spade('♠'), Club('♣'), Diamond('♦');

  private final char symbol;

  /**
   * Constructs a suit.
   *
   * @param symbol the symbol of the corresponding suit
   */
  Suits(char symbol) {
    this.symbol = symbol;
  }

  /**
   * This method gets the symbol corresponding to this suit.
   *
   * @return the symbol of this suit
   */
  public char getSymbol() {
    return this.symbol;
  }

}
