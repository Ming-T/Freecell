package cs3500;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.PileType;
import cs3500.hw02.Suits;
import cs3500.hw04.FreecellMultiModel;

import static org.junit.Assert.assertEquals;

public class FreecellMultiModelTest {
  Card heart13 = new Card(Suits.Heart, 13);
  Card spade13 = new Card(Suits.Spade, 13);
  Card diamond1 = new Card(Suits.Diamond, 1);
  Card club12 = new Card(Suits.Club, 12);
  Card heart1 = new Card(Suits.Heart, 1);
  Card diamond12 = new Card(Suits.Diamond, 12);
  FreecellMultiModel m1 = new FreecellMultiModel();
  FreecellMultiModel m2 = new FreecellMultiModel();
  List<Card> invalidDeck = new ArrayList<Card>(
          Arrays.asList(spade13, club12, heart1, diamond12, diamond1));

  @Test
  public void testMove() {
    m2.startGame(m2.getDeck(), 6, 5, false);
    m2.move(PileType.CASCADE, 4, 7, PileType.CASCADE, 0);
    m2.move(PileType.CASCADE, 4, 6, PileType.CASCADE, 0);
    m2.move(PileType.CASCADE, 2, 8, PileType.OPEN, 0);
    m2.move(PileType.CASCADE, 2, 7, PileType.OPEN, 1);
    m2.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
    m2.move(PileType.CASCADE, 2, 5, PileType.CASCADE, 0);
    m2.move(PileType.CASCADE, 4, 5, PileType.OPEN, 2);
    // testing if it works with single move
    m2.move(PileType.CASCADE, 2, 4, PileType.OPEN, 3);

    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1: K♦\n" + "O2: Q♠\n"
            + "O3: 9♦\n" + "O4: 7♦\n" + "O5:\n"
            + "C1: A♠, 2♦, 4♠, 5♦, 7♠, 8♦, 10♠, J♦, K♠, Q♦, J♠, 10♦, 9♠\n"
            + "C2: A♥, 2♣, 4♥, 5♣, 7♥, 8♣, 10♥, J♣, K♥\n" + "C3: A♦, 3♠, 4♦, 6♠\n"
            + "C4: A♣, 3♥, 4♣, 6♥, 7♣, 9♥, 10♣, Q♥, K♣\n" + "C5: 2♠, 3♦, 5♠, 6♦, 8♠\n"
            + "C6: 2♥, 3♣, 5♥, 6♣, 8♥, 9♣, J♥, Q♣", m2.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument() {
    // testing invalid destination
    m2.move(PileType.CASCADE, 0, 8, PileType.CASCADE, 9);
    // testing out of bound cascade pile
    m2.move(PileType.CASCADE, 6, 8, PileType.CASCADE, 3);
    // testing out of bound open pile
    m2.move(PileType.OPEN, 6, 8, PileType.CASCADE, 3);
    // testing out of bound foundation pile
    m2.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 4);
    // testing move when the number of cards exceeds the maximum
    m2.move(PileType.CASCADE, 0, 8, PileType.CASCADE, 3);
    // testing move more then a card to foundation
    m2.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 2);
    // testing moving wrong card to foundation pile
    m2.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 4);
    // testing moving more than one card to open pile
    m2.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    // testing moving the wrong suit
    m2.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 5);
    // testing moving to an occupied open pile
    m2.move(PileType.CASCADE, 2, 4, PileType.OPEN, 0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeck() {
    m1.startGame(invalidDeck, 6, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCasPile() {
    m2.startGame(m2.getDeck(), 3, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenPile() {
    m1.startGame(m1.getDeck(), 5, 0, false);
  }

  @Test
  public void testStartGame() {
    m1.startGame(m1.getDeck(), 5, 5, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n" + "O3:\n"
            + "O4:\n" + "O5:\n" + "C1: A♠, 2♥, 3♦, 4♣, 6♠, 7♥, 8♦, 9♣, J♠, Q♥, K♦\n"
            + "C2: A♥, 2♦, 3♣, 5♠, 6♥, 7♦, 8♣, 10♠, J♥, Q♦, K♣\n"
            + "C3: A♦, 2♣, 4♠, 5♥, 6♦, 7♣, 9♠, 10♥, J♦, Q♣\n"
            + "C4: A♣, 3♠, 4♥, 5♦, 6♣, 8♠, 9♥, 10♦, J♣, K♠\n"
            + "C5: 2♠, 3♥, 4♦, 5♣, 7♠, 8♥, 9♦, 10♣, Q♠, K♥", m1.getGameState());
    m1.startGame(m1.getDeck(), 5, 5, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n" + "O3:\n"
            + "O4:\n" + "O5:\n" + "C1: A♠, 2♥, 3♦, 4♣, 6♠, 7♥, 8♦, 9♣, J♠, Q♥, K♦\n"
            + "C2: A♥, 2♦, 3♣, 5♠, 6♥, 7♦, 8♣, 10♠, J♥, Q♦, K♣\n"
            + "C3: A♦, 2♣, 4♠, 5♥, 6♦, 7♣, 9♠, 10♥, J♦, Q♣\n"
            + "C4: A♣, 3♠, 4♥, 5♦, 6♣, 8♠, 9♥, 10♦, J♣, K♠\n"
            + "C5: 2♠, 3♥, 4♦, 5♣, 7♠, 8♥, 9♦, 10♣, Q♠, K♥", m1.getGameState());
    assertEquals(true, heart13.equals(m1.cascadePiles.get(4).cards.get(9)));

  }


  @Test
  public void testGetGameState() {
    m1.startGame(m1.getDeck(), 5, 5, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n" + "O3:\n"
            + "O4:\n" + "O5:\n" + "C1: A♠, 2♥, 3♦, 4♣, 6♠, 7♥, 8♦, 9♣, J♠, Q♥, K♦\n"
            + "C2: A♥, 2♦, 3♣, 5♠, 6♥, 7♦, 8♣, 10♠, J♥, Q♦, K♣\n"
            + "C3: A♦, 2♣, 4♠, 5♥, 6♦, 7♣, 9♠, 10♥, J♦, Q♣\n"
            + "C4: A♣, 3♠, 4♥, 5♦, 6♣, 8♠, 9♥, 10♦, J♣, K♠\n"
            + "C5: 2♠, 3♥, 4♦, 5♣, 7♠, 8♥, 9♦, 10♣, Q♠, K♥", m1.getGameState());
    assertEquals("", m2.getGameState());

  }

  @Test
  public void testIsGameOver() {
    m1.startGame(m1.getDeck(), 52, 1, false);

    for (int i = 0; i < 52; i++) {
      m1.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
    assertEquals(true, m1.isGameOver());
  }
}
