package cs3500.hw03;

import java.util.List;

import cs3500.hw02.FreecellOperations;

/**
 * A freecell controller that runs the game and renders the game state.
 *
 * @param <K> that represents the type of the controller
 */
public interface IFreecellController<K> {

  /**
   * Play the game with the controller.
   *
   * @param deck        the deck of the model
   * @param model       the freecell model for the controller
   * @param numCascades numbers of cascade piles in the model
   * @param numOpens    numbers of open piles in the model
   * @param shuffle     shuffle the deck if true, otherwise do not shuffle
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades,
                int numOpens, boolean shuffle);
}
