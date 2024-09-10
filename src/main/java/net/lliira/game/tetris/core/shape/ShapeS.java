package net.lliira.game.tetris.core.shape;

import java.awt.*;

public class ShapeS extends Shape {
  private static final Point[] H_BLOCKS = new Point[] {P(1, 0), P(2, 0), P(0, 1), P(1, 1)};
  private static final Point[] V_BLOCKS = new Point[] {P(0, 0), P(0, 1), P(1, 1), P(1, 2)};

  public ShapeS(Orientation orientation) {
    super(orientation);
  }

  @Override
  public Color getColor() {
    return Color.GREEN;
  }

  @Override
  protected Point[] getBaseBlocks(Orientation orientation) {
    return switch (orientation) {
      case East, West -> H_BLOCKS;
      case North, South -> V_BLOCKS;
    };
  }

  @Override
  protected Shape newShape(Orientation orientation) {
    return new ShapeS(orientation);
  }
}
