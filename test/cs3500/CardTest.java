package cs3500;

import org.junit.Test;

import cs3500.hw02.Card;
import cs3500.hw02.Suits;

import static org.junit.Assert.assertEquals;

public class CardTest {

  Card heart5 = new Card(Suits.Heart, 5);
  Card spade11 = new Card(Suits.Spade, 11);
  Card diamond2 = new Card(Suits.Diamond, 2);
  Card club13 = new Card(Suits.Club, 13);
  Card heart1 = new Card(Suits.Heart, 1);
  Card diamond12 = new Card(Suits.Diamond, 12);


  @Test
  public void constructorCheck() {
    assertEquals(5, heart5.value);
  }

  @Test
  public void constructorCheck2() {
    assertEquals(Suits.Heart, heart5.suit);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardState() {
    assertEquals("5♥", heart5.toString());
    assertEquals(new IllegalArgumentException("out of range"), new Card(Suits.Spade, 15));
    assertEquals("A♥", heart1.toString());
    assertEquals("Q♦", diamond12.toString());
    assertEquals("K♣", club13.toString());
    assertEquals("J♠", spade11.toString());
  }

  @Test
  public void testRightSuit() {
    assertEquals(false, diamond2.rightSuit(heart1));
    assertEquals(true, spade11.rightSuit(diamond12));
    assertEquals(true, diamond12.rightSuit(club13));
    assertEquals(false, diamond2.rightSuit(heart5));
  }

  @Test
  public void testEqualsAndHashCode() {
    assertEquals(true, heart1.equals(heart1));
    assertEquals(false, heart5.equals(diamond12));
    assertEquals(15, heart1.hashCode());
  }


}