package cs3500;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;
import cs3500.hw02.Suits;

import static org.junit.Assert.assertEquals;

public class FreecellModelTest {
  Card h1 = new Card(Suits.Heart, 1);
  Card h2 = new Card(Suits.Heart, 2);
  Card h3 = new Card(Suits.Heart, 3);
  Card h4 = new Card(Suits.Heart, 4);
  Card d1 = new Card(Suits.Diamond, 1);
  Card d2 = new Card(Suits.Diamond, 2);
  Card d3 = new Card(Suits.Diamond, 3);
  Card d4 = new Card(Suits.Diamond, 4);
  Card c3 = new Card(Suits.Club, 3);
  Card s1 = new Card(Suits.Spade, 1);

  FreecellModel model1 = new FreecellModel();
  FreecellModel model2 = new FreecellModel();
  FreecellModel model3 = new FreecellModel();

  List<Card> shortDeck = new ArrayList<Card>(Arrays.asList(h1, h2, h3, h4, d1, d2, d3, d4, c3));
  List<Card> deck1 = model1.getDeck();


  @Test
  public void testGetDeck() {
    assertEquals(52, deck1.size());
    assertEquals(true, deck1.contains(h1));
  }

  @Test
  public void testStartGame() {
    model1.startGame(deck1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", model1.getGameState());
    model1.startGame(deck1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", model1.getGameState());
    assertEquals(true, h1.equals(model1.cascadePiles.get(1).cards.get(0)));
    model2.startGame(deck1, 4, 1, true);
    assertEquals(false, model2.cascadePiles.get(0).cards.get(0).equals(s1));
  }


  @Test
  public void testMove() {
    model1.startGame(deck1, 52, 2, false);
    model1.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(Suits.Spade, model1.foundation.get(0).cards.get(0).suit);
    assertEquals(0, model1.cascadePiles.get(0).cards.size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    model1.startGame(deck1, 4, 1, false);
    model1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    model1.move(PileType.CASCADE, 0, 7, PileType.CASCADE, 1);
    model1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 0);
    model1.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeck() {
    model3.startGame(shortDeck, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCasPile() {
    model3.startGame(deck1, 3, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenPile() {
    model3.startGame(deck1, 5, 0, false);
  }


  @Test
  public void testIsGameOver() {
    model1.startGame(deck1, 8, 1, false);
    model2.startGame(deck1, 52, 1, false);

    for (int i = 0; i < 52; i++) {
      model2.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i % 4);
    }
    assertEquals(false, model1.isGameOver());
    assertEquals(true, model2.isGameOver());

  }

  @Test
  public void testGetGameState() {
    model1.startGame(deck1, 8, 1, false);
    assertEquals("", model3.getGameState());
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n"
                    + "C1: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n" + "C2: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
                    + "C3: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n" + "C4: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
                    + "C5: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n" + "C6: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
                    + "C7: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n" + "C8: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣"
            , model1.getGameState());
  }


}