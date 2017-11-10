package cs3500;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;
import cs3500.hw04.FreecellModelCreator;
import cs3500.hw04.FreecellMultiModel;

public class FreecellModelCreatorTest {
  FreecellMultiModel fmm1 = new FreecellMultiModel();
  FreecellModel fm1 = new FreecellModel();
  FreecellModelCreator multiC = new FreecellModelCreator();
  FreecellModelCreator singleC = new FreecellModelCreator();

  @Test
  public void testCreate() {
    assertEquals(true,
            multiC.create(FreecellModelCreator.GameType.MULTIMOVE) instanceof FreecellMultiModel);
    assertEquals(true,
            singleC.create(FreecellModelCreator.GameType.SINGLEMOVE) instanceof FreecellModel);
    // since the freecellMultiModel extends from the freecellModel it is an instanceof freecellmodel
    assertEquals(true,
            multiC.create(FreecellModelCreator.GameType.MULTIMOVE) instanceof FreecellModel);
    assertEquals(false,
            singleC.create(FreecellModelCreator.GameType.SINGLEMOVE) instanceof FreecellMultiModel);

  }

}
