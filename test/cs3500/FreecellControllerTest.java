package cs3500;

import org.junit.Test;

import java.io.StringReader;
import java.util.List;


import cs3500.hw02.Card;
import cs3500.hw02.FreecellModel;

import cs3500.hw03.FreecellController;


import static org.junit.Assert.assertEquals;

public class FreecellControllerTest {
  Readable rd1 = new StringReader("C1 1 F1 C5 1 F1 C9 1 F1 C13 1 F1 C17 1 F1 C21 1 F1 C25 1 F1 "
          + "C29 1 F1 C33 1 F1 C37 1 F1 C41 1 F1 C45 1 F1 C49 1 F1 " + "C2 1 F2 C6 1 F2 C10 1 F2 " +
          "C14 1 F2 C18 1 F2 C22 1 F2 C26 1 F2 C30 1 F2 C34 1 F2 C38 1 F2 C42 1 F2 C46 1 F2 " +
          "C50 1 F2 C3 1 F3 C7 1 F3 C11 1 F3 C15 1 F3 C19 1 F3 C23 1 F3 C27 1 F3 C31 1 F3 " +
          "C35 1 F3 C39 1 F3 C43 1 F3 C47 1 F3 C51 1 F3 C4 1 F4 C8 1 F4 C12 1 F4 C16 1 F4 " +
          "C20 1 F4 C24 1 F4 C28 1 F4 C32 1 F4 C36 1 F4 C40 1 F4 C44 1 F4 C48 1 F4 C52 1 F4  1");

  Readable rdq = new StringReader("Q");
  Readable rdInvalidS = new StringReader("C0 1 O2");
  Readable rdInvalidC = new StringReader("C1 2 O2");
  Readable rdInvalidD = new StringReader("C1 1 O2");
  Readable rd2 = new StringReader("C1 q 4");
  Readable rd3 = new StringReader("C1 13 O2");
  Readable rdEmpty = new StringReader("");
  Readable rd4 = new StringReader("C1 4 hello yolo q");
  Readable rd5 = new StringReader("C1 hello O1");

  Appendable ap1 = new StringBuffer();
  Appendable ap2 = new StringBuffer("yolo");
  FreecellModel model1 = new FreecellModel();
  FreecellModel model2 = new FreecellModel();
  FreecellModel model3 = null;
  List<Card> deck1 = model1.getDeck();
  FreecellController control1 = new FreecellController(rd1, ap1);
  FreecellController control2 = new FreecellController(rdq, ap1);
  FreecellController control3 = new FreecellController(rdInvalidC, ap1);
  FreecellController control4 = new FreecellController(rdInvalidD, ap1);
  FreecellController control5 = new FreecellController(rdInvalidS, ap1);
  FreecellController control6 = new FreecellController(rd2, ap1);
  FreecellController control7 = new FreecellController(rd3, ap1);
  FreecellController control8 = new FreecellController(rdEmpty, ap1);
  FreecellController control9 = new FreecellController(rd4, ap1);
  FreecellController control10 = new FreecellController(rd5, ap1);
  FreecellController control11 = new FreecellController(rdEmpty, ap2);

  // testing is game over
  @Test
  public void testGameOver() {
    control1.playGame(deck1, model1, 52, 2, false);
    String output = ap1.toString();
    assertEquals("Game over.", output.substring(24303));
    assertEquals(true, model1.isGameOver());
  }

  // test couldn't start game
  @Test
  public void testCantStop() {
    control8.playGame(deck1, model1, 0, 2, false);
    assertEquals("Could not start game.", ap1.toString());
  }

  // test wrong card Index
  @Test
  public void testWrongCardI() {
    control10.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nWrong CardIndex" + "\nInvalid move", ap1.toString());
  }

  @Test
  public void testPlayGameQ() {
    control2.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nGame quit prematurely.", ap1.toString());
  }

  // testing playgame with invalid card index
  @Test
  public void testPlayGameIC() {
    control3.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nInvalid move", ap1.toString());
  }

  // testing plagame with invalid destination
  @Test
  public void testPlayGameID() {
    control4.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nInvalid move", ap1.toString());
  }

  // testing playgame with invalid source
  @Test
  public void testPlayGameIS() {
    control5.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nInvalid move", ap1.toString());
  }

  // testing playgame with "q" in the middle of the 3 sequence
  @Test
  public void testPlayGame() {
    assertEquals("", ap1.toString());
    control6.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nGame quit prematurely.", ap1.toString());
  }

  @Test
  public void testPlayGame2() {
    control9.playGame(deck1, model2, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣"
            + "\nInvalid move" + "\nWrong Pile Type"
            + "\nGame quit prematurely.", ap1.toString());
  }

  // testing playgame with a valid move
  @Test
  public void testValidMove() {
    control7.playGame(deck1, model1, 4, 2, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2: K♠\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", ap1.toString());
  }

  // testing if the deck was shuffled
  @Test
  public void testIsShuffled() {
    control7.playGame(deck1, model2, 4, 2, true);
    assertEquals(false, model1.cascadePiles.equals(model2.cascadePiles));
  }

  // testing null deck input for freecell Controller
  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    control1.playGame(null, model2, 4, 5, false);
  }

  // testing null constructor for freecell Controller
  @Test(expected = IllegalStateException.class)
  public void testNullConstructor() {
    FreecellController control2 = new FreecellController(null, null);
  }

  // testing null model
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    control1.playGame(deck1, model3, 4, 5, true);
  }

  // testing nonempty appendable
  @Test
  public void testNonAppend() {
    control11.playGame(deck1, model1, 4, 2, false);
    assertEquals("yoloF1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n"
            + "C1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", ap2.toString());

  }
}
