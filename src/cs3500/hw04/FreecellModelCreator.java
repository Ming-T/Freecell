package cs3500.hw04;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;

/**
 * A factory class that determines whether the model is a single move model or multi move model.
 */
public class FreecellModelCreator {
  public enum GameType { SINGLEMOVE, MULTIMOVE }

  /**
   * Creates a freecell model based on the given type.
   *
   * @param type represents the model type as enum
   * @return a new freecell Operation
   */
  public static FreecellOperations create(GameType type) {
    if (type == GameType.SINGLEMOVE) {
      return new FreecellModel();
    } else {
      return new FreecellMultiModel();
    }
  }
}
