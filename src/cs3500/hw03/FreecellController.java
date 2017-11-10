package cs3500.hw03;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;

/**
 * Freecell Controller that implements the Icontroller interface and plays the given model.
 */
public class FreecellController implements IFreecellController {
  private final Readable in;
  private final Appendable out;

  // constants that help store the value in the user input
  private PileType from;
  private int fromI;
  private int cardIndex;
  private PileType to;
  private int toI;
  private int sequence = 0;


  /**
   * Constructor for the freecellController.
   *
   * @param rd represents the user input
   * @param ap represents the output
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalStateException("null constructor");
    } else {
      this.in = rd;
      this.out = ap;
    }
  }

  @Override
  public void playGame(List deck, FreecellOperations model, int numCascades,
                       int numOpens, boolean shuffle) {
    if (deck == null || model == null) {
      throw new IllegalArgumentException("null input");
    }
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
      this.appendHelper(model.getGameState());
    } catch (IllegalArgumentException e) {
      this.appendHelper("Could not start game.");
    }
    Scanner scan = new Scanner(this.in);
    while (scan.hasNext() && !model.isGameOver()) {
      String next = scan.next();

      if (next.equals("Q") || next.equals("q")) {
        this.appendHelper("\n" + "Game quit prematurely.");
        return;
      } else if (this.sequence == 0) {
        this.sequence = this.sequence + 1;
        try {
          this.from = this.pileHelper(next.charAt(0));
          this.fromI = Integer.parseInt(next.substring(1)) - 1;
        } catch (IllegalArgumentException e) {
          this.appendHelper("\n" + e.getMessage());
        }
      } else if (this.sequence == 1) {
        this.sequence = this.sequence + 1;
        try {
          this.cardIndex = Integer.parseInt(next.substring(0)) - 1;
        } catch (IllegalArgumentException e) {
          this.appendHelper("\n" + "Wrong CardIndex");
        }
      } else if (this.sequence == 2) {
        this.sequence = 0;
        try {
          this.to = this.pileHelper(next.charAt(0));
          this.toI = Integer.parseInt(next.substring(1)) - 1;
          model.move(from, fromI, cardIndex, to, toI);
          this.appendHelper("\n" + model.getGameState());
        } catch (IllegalArgumentException e) {
          this.appendHelper("\n" + "Invalid move");
        }
      }

      if (model.isGameOver()) {
        this.appendHelper("\n" + "Game over.");
      }


    }


  }

  /**
   * A appendable helper that help append the given message onto the output.
   *
   * @param mess represents the message being appended
   */
  private void appendHelper(String mess) {
    try {
      this.out.append(mess);
    } catch (IOException e) {
      // not suppose to do anything once it catches the error
    }
  }

  /**
   * A helper method that helps differentiate the different PileType.
   *
   * @param p character representation of the PileType
   * @return a PileType and an exception if non of the character matches
   */
  private PileType pileHelper(Character p) {
    switch (p) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("Wrong Pile Type");
    }
  }
}
