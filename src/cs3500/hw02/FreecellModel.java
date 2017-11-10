package cs3500.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a freecellModel that would includes starting a game and the methods in the
 * freecellOperations.
 */
public class FreecellModel implements FreecellOperations<Card> {
  public List<Pile> cascadePiles;
  public List<Pile> openPiles;
  public List<Pile> foundation;
  public List<Card> dealDeck;

  /**
   * Constructs a freecellModel with empty arraylists for each field.
   */
  public FreecellModel() {
    this.cascadePiles = new ArrayList<Pile>();
    this.openPiles = new ArrayList<Pile>();
    this.foundation = new ArrayList<Pile>();
    this.dealDeck = dealDeck;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>(52);
    for (int i = 1; i <= 13; i++) {
      deck.add(new Card(Suits.Spade, i));
      deck.add(new Card(Suits.Heart, i));
      deck.add(new Card(Suits.Diamond, i));
      deck.add(new Card(Suits.Club, i));
    }
    return deck;
  }

  /**
   * Check if the given deck is a valid deck with 52 cards and one of each suits and each value.
   *
   * @param deck a list of cards
   * @return true if it's a valid deck, false otherwise
   */
  private boolean validDeck(List<Card> deck) {
    boolean allCards = true;
    for (int i = 1; i <= 13; i++) {
      allCards = allCards && deck.contains(new Card(Suits.Spade, i));
      allCards = allCards && deck.contains(new Card(Suits.Diamond, i));
      allCards = allCards && deck.contains(new Card(Suits.Club, i));
      allCards = allCards && deck.contains(new Card(Suits.Heart, i));
    }
    return allCards;
  }

  /**
   * Deals the deck of cards in the class field into cascade piles in order of the pile.
   */
  private void dealCards() {
    int count = 0;
    int numOfCascadePile = this.cascadePiles.size();
    for (int i = 0; i < this.dealDeck.size(); i++) {
      Card addCard = this.dealDeck.get(i);
      if (count < numOfCascadePile) {
        this.cascadePiles.get(count).cards.add(addCard);
        count = count + 1;
      } else {
        count = 0;
        this.cascadePiles.get(count).cards.add(addCard);
        count = count + 1;
      }

    }
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (this.validDeck(deck)) {
      this.dealDeck = deck;
    } else {
      throw new IllegalArgumentException("Invalid Deck");
    }
    this.foundation = new ArrayList<Pile>(Arrays.asList(new Pile(PileType.FOUNDATION),
            new Pile(PileType.FOUNDATION), new Pile(PileType.FOUNDATION),
            new Pile(PileType.FOUNDATION)));
    if (numCascadePiles >= 4 && numCascadePiles < 53) {
      this.cascadePiles = new ArrayList<Pile>();
      for (int i = 0; i < numCascadePiles; i++) {
        this.cascadePiles.add(new Pile(PileType.CASCADE));
      }
    } else {
      throw new IllegalArgumentException("Cascade Pile need to be at least 4 piles");
    }
    if (numOpenPiles >= 1 && numOpenPiles < 53) {
      this.openPiles = new ArrayList<Pile>();
      for (int i = 0; i < numOpenPiles; i++) {
        this.openPiles.add(new Pile(PileType.OPEN));
      }
    } else {
      throw new IllegalArgumentException("Open Pile need to be at least 1 piles");
    }

    if (shuffle) {
      Collections.shuffle(this.dealDeck);
      this.dealCards();
    } else {
      this.dealCards();
    }

  }


  /**
   * Moves the given Card to the given destination.
   *
   * @param target    the card that is being moved
   * @param toPile    the pile number of the given destination type, starting at 0
   * @param toPileNum the index of the card to be moved from the destination pile, starting at 0
   */
  private void moveTo(Card target, PileType toPile, int toPileNum) {
    switch (toPile) {
      case OPEN:
        openPiles.get(toPileNum).cards.add(target);
        break;
      case FOUNDATION:
        foundation.get(toPileNum).cards.add(target);
        break;
      case CASCADE:
        cascadePiles.get(toPileNum).cards.add(target);
        break;
      default:
        throw new IllegalArgumentException("No Such place");
    }
  }

  /**
   * Given the location of a certain card check if the card could be move to the destination pile.
   *
   * @param target the card that is going to be moved
   * @param dest   the pile number of the given destination type, starting at 0
   * @param tPN    the index of the card to be moved from the destination pile, starting at 0
   * @return true if the card could be move to the destination pile, false otherwise.
   */

  private Boolean canMoveTo(Card target, PileType dest, int tPN) {
    switch (dest) {
      case FOUNDATION:
        if (tPN >= 0 && tPN < foundation.size() && this.foundation.get(tPN).cards.size() == 0) {
          return target.value == 1;
        } else {
          int fLast = this.foundation.get(tPN).cards.size() - 1;
          Card fCard = this.foundation.get(tPN).cards.get(fLast);
          return (target.suit == fCard.suit) && (target.value - 1 == fCard.value);
        }
      case CASCADE:
        if (tPN >= 0 && tPN < cascadePiles.size() && this.cascadePiles.get(tPN).cards.size() == 0) {
          return true;
        } else {
          int casLast = this.cascadePiles.get(tPN).cards.size() - 1;
          Card casCard = this.cascadePiles.get(tPN).cards.get(casLast);
          return target.rightSuit(casCard);
        }
      case OPEN:
        if (tPN >= 0 && tPN < openPiles.size()) {
          return this.openPiles.get(tPN).cards.size() == 0;
        } else {
          return false;
        }
      default:
        return false;
    }
  }


  /**
   * Given the location of a certain card check if the card could be move from the source pile.
   *
   * @param source the type of source pile that the card is removing from
   * @param fPN    the pile number of the given source type, starting at 0
   * @param fCI    the index of the card to be moved from the source pile, starting at 0
   * @return true if the card could be move from the source pile, false otherwise.
   */

  private Boolean canMoveFrom(PileType source, int fPN, int fCI) {
    if (fPN >= 0 && fCI >= 0) {
      switch (source) {
        case FOUNDATION:
          return fPN >= 0 && fPN <= 3 && foundation.get(fPN).cards.size() - 1 == fCI;
        case CASCADE:
          return fPN >= 0 && fPN <= cascadePiles.size() - 1
                  && (cascadePiles.get(fPN).cards.size() - 1) == fCI;
        case OPEN:
          return fPN >= 0 && fPN <= openPiles.size() - 1
                  && openPiles.get(fPN).cards.size() - 1 == fCI;
        default:
          return false;
      }
    } else {
      throw new IllegalArgumentException("Invalid source Pile");
    }
  }


  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) {
    if (source == PileType.FOUNDATION && this.canMoveFrom(source, pileNumber, cardIndex)
            && this.canMoveTo(foundation.get(pileNumber).cards.get(cardIndex),
            destination, destPileNumber)) {
      this.moveTo(foundation.get(pileNumber).cards.get(cardIndex), destination, destPileNumber);
      foundation.get(pileNumber).cards.remove(cardIndex);
    } else if (source == PileType.OPEN && this.canMoveFrom(source, pileNumber, cardIndex)
            && this.canMoveTo(openPiles.get(pileNumber).cards.get(cardIndex),
            destination, destPileNumber)) {
      this.moveTo(openPiles.get(pileNumber).cards.get(cardIndex), destination, destPileNumber);
      openPiles.get(pileNumber).cards.remove(cardIndex);
    } else if (source == PileType.CASCADE && this.canMoveFrom(source, pileNumber, cardIndex)
            && this.canMoveTo(cascadePiles.get(pileNumber).cards.get(cardIndex),
            destination, destPileNumber)) {
      this.moveTo(cascadePiles.get(pileNumber).cards.get(cardIndex), destination, destPileNumber);
      cascadePiles.get(pileNumber).cards.remove(cardIndex);
    } else {
      throw new IllegalArgumentException("Invalid move");
    }

  }

  @Override
  public boolean isGameOver() {
    boolean finished = true;
    for (int i = 0; i < foundation.size(); i++) {
      finished = finished && foundation.get(i).cards.size() == 13;
    }
    for (int i = 0; i < openPiles.size(); i++) {
      finished = finished && openPiles.get(i).cards.size() == 0;
    }
    for (int i = 0; i < cascadePiles.size(); i++) {
      finished = finished && cascadePiles.get(i).cards.size() == 0;
    }
    return finished;
  }

  @Override
  public String getGameState() {
    String finalS = "";
    for (int i = 0; i < this.foundation.size(); i++) {
      finalS = finalS + "F" + Integer.toString(i + 1) + ":" + this.foundation.get(i).toString()
              + "\n";
    }
    for (int i = 0; i < this.openPiles.size(); i++) {
      finalS = finalS + "O" + Integer.toString(i + 1) + ":" + this.openPiles.get(i).toString()
              + "\n";
    }
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      if (i == cascadePiles.size() - 1) {
        finalS = finalS + "C" + Integer.toString(i + 1) + ":"
                + this.cascadePiles.get(i).toString();
      } else {
        finalS = finalS + "C" + Integer.toString(i + 1) + ":"
                + this.cascadePiles.get(i).toString() + "\n";
      }
    }
    return finalS;

  }
}
