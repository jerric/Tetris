package net.lliira.game.tetris.core.shape;

import java.awt.*;

public class ShapeI extends Shape {
  private static final Point[] H_BLOCKS = new Point[] {P(0, 1), P(1, 1), P(2, 1), P(3, 1)};
  private static final Point[] V_BLOCKS = new Point[] {P(1, 0), P(1, 1), P(1, 2), P(1, 3)};

  public ShapeI(Orientation orientation) {
    super(orientation);
  }

  @Override
  public Color getColor() {
    return Color.CYAN;
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
    return new ShapeI(orientation);
  }
}
