package cs3500;

import org.junit.Test;

import cs3500.hw02.Card;
import cs3500.hw02.Pile;
import cs3500.hw02.PileType;
import cs3500.hw02.Suits;

import static org.junit.Assert.assertEquals;

public class PileTest {
  Pile p1 = new Pile(PileType.CASCADE);
  Pile p2 = new Pile(PileType.OPEN);

  @Test
  public void pileState() {
    p1.cards.add(new Card(Suits.Club, 13));
    p1.cards.add(new Card(Suits.Spade, 1));
    assertEquals(" K♣, A♠", p1.toString());
  }


}