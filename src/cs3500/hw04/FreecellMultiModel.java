package cs3500.hw04;


import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;

/**
 * A freecell operation that extends from the freecell model. Unlike the freecellmodel , this model
 * handles multi move features for the freecell.
 */
public class FreecellMultiModel extends FreecellModel {
  // keep tracks of the amount of empty piles in the model
  private int emptyCas = 0;
  private int emptyOpen = 0;


  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType
          destination, int destPileNumber) {
    emptyOpen = 0;
    emptyCas = 0;
    int size = this.getSize(source, pileNumber);
    List<Card> target = this.getTargetL(source, pileNumber, cardIndex);
    this.checkEmpty();
    boolean validEmpty = target.size() <= ((emptyOpen + 1) * Math.pow(2, emptyCas));
    if (source == PileType.FOUNDATION && validEmpty
            && checkMoveTo(target, destination, destPileNumber)) {
      this.moveT(target, destination, destPileNumber);
      foundation.get(pileNumber).cards.remove(cardIndex);
    } else if (source == PileType.OPEN && validEmpty
            && checkMoveTo(target, destination, destPileNumber)) {
      this.moveT(target, destination, destPileNumber);
      openPiles.get(pileNumber).cards.remove(cardIndex);
    } else if (source == PileType.CASCADE && validEmpty && validCMoveF(pileNumber, cardIndex)
            && target.size() > 0 && checkMoveTo(target, destination, destPileNumber)) {
      this.moveT(target, destination, destPileNumber);
      cascadePiles.get(pileNumber).cards.removeAll(target);
    } else {
      throw new IllegalArgumentException("Invalid move");
    }
  }

  /**
   * Updates the amount of empty cascade piles and empty open piles in the model.
   */
  private void checkEmpty() {
    for (int i = 0; i < cascadePiles.size(); i++) {
      if (cascadePiles.get(i).cards.size() == 0) {
        emptyCas = emptyCas + 1;
      } // no else case, since it does nothing when it's not empty

    }
    for (int i = 0; i < openPiles.size(); i++) {
      if (openPiles.get(i).cards.size() == 0) {
        emptyOpen = emptyOpen + 1;
      } // no else case, since it does nothing when it's not empty

    }
  }

  /**
   * This helper method help gets the source pile size to clean the code.
   *
   * @param s     is the source pile type
   * @param pileN is the source pile number
   * @return the size of the given source pile
   */
  private int getSize(PileType s, int pileN) {
    if (s == PileType.FOUNDATION && pileN >= 0 && pileN < foundation.size()) {
      return this.foundation.get(pileN).cards.size();
    } else if (s == PileType.OPEN && pileN >= 0 && pileN < openPiles.size()) {
      return this.openPiles.get(pileN).cards.size();
    } else if (s == PileType.CASCADE && pileN >= 0 && pileN < cascadePiles.size()) {
      return this.cascadePiles.get(pileN).cards.size();
    } else {
      throw new IllegalArgumentException("Invalid Source");
    }

  }

  /**
   * This helper method will check for invalid source pile and return a list of cards that is being
   * moved.
   *
   * @param s     is the source pile type
   * @param pileN is the source pile number
   * @param cardI is the source pile card index
   * @return the list of cards that is being moved
   */
  private List<Card> getTargetL(PileType s, int pileN, int cardI) {
    int sSize = getSize(s, pileN);
    if (s == PileType.OPEN && cardI == sSize - 1) {
      return this.openPiles.get(pileN).cards.subList(cardI, sSize);
    } else if (s == PileType.FOUNDATION && cardI == sSize - 1) {
      return this.foundation.get(pileN).cards.subList(cardI, sSize);
    } else if (s == PileType.CASCADE && validCMoveF(pileN, cardI)) {
      return this.cascadePiles.get(pileN).cards.subList(cardI, sSize);
    } else {
      throw new IllegalArgumentException("Invalid source");
    }

  }

  /**
   * This is a help method to help check if the given cascade pile is valid to be move.
   *
   * @param pileNumber is the cascade pile number
   * @param cardIndex  is the cascade pile's starting card index
   * @return true if the cards are valid to be moved from the given cascade pile, false otherwise
   */
  private boolean validCMoveF(int pileNumber, int cardIndex) {
    int cSize = cascadePiles.get(pileNumber).cards.size();
    boolean trace = true;
    try {
      List<Card> targetL = cascadePiles.get(pileNumber).cards.subList(cardIndex, cSize);
      for (int i = 0; i < targetL.size() - 1; i++) {
        trace = trace && targetL.get(i + 1).rightSuit(targetL.get(i));
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid source");
    }
    return trace;

  }

  /**
   * This is a helper method to check if the destination pile is valid.
   *
   * @param target is the list of cards that is being moved
   * @param dest   is the destination pile type
   * @param destPN is the destination pile number
   * @return true if the destination is valid, false otherwise
   */
  private Boolean checkMoveTo(List<Card> target, PileType dest, int destPN) {
    Card first = target.get(0);
    if (dest == PileType.FOUNDATION) {
      int foundSize = foundation.get(destPN).cards.size();
      if (foundSize == 0) {
        return target.size() == 1 && first.value == 1;
      } else {
        return target.size() == 1
                && first.suit == foundation.get(destPN).cards.get(foundSize - 1).suit
                && first.value - 1 == foundation.get(destPN).cards.get(foundSize - 1).value;
      }
    } else if (dest == PileType.OPEN) {
      return target.size() == 1 && openPiles.get(destPN).cards.size() == 0;
    } else if (dest == PileType.CASCADE) {
      int casSize = cascadePiles.get(destPN).cards.size();
      if (casSize == 0) {
        return true;
      } else {
        return first.rightSuit(cascadePiles.get(destPN).cards.get(casSize - 1));
      }
    } else {
      throw new IllegalArgumentException("Invalid destination");
    }
  }

  /**
   * The helper mehtod that actually moves the list of cards after both source and destination are
   * valid.
   *
   * @param tar    is the list of cards that is being moved
   * @param dest   is the destination pile type
   * @param destPN is the destination pile number
   */

  private void moveT(List<Card> tar, PileType dest, int destPN) {
    if (dest == PileType.FOUNDATION) {
      foundation.get(destPN).cards.addAll(tar);
    } else if (dest == PileType.OPEN) {
      openPiles.get(destPN).cards.addAll(tar);
    } else if (dest == PileType.CASCADE) {
      cascadePiles.get(destPN).cards.addAll(tar);
    } else {
      throw new IllegalArgumentException("Invalid Move");
    }
  }

}
