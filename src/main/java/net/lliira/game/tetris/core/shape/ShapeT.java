package net.lliira.game.tetris.core.shape;

import java.awt.*;

public class ShapeT extends Shape {
  private static final Point[] N_BLOCKS = new Point[] {P(0, 0), P(1, 0), P(2, 0), P(1, 1)};
  private static final Point[] E_BLOCKS = new Point[] {P(1, 0), P(1, 1), P(1, 2), P(0, 1)};
  private static final Point[] S_BLOCKS = new Point[] {P(1, 0), P(0, 1), P(1, 1), P(2, 1)};
  private static final Point[] W_BLOCKS = new Point[] {P(0, 0), P(0, 1), P(0, 2), P(1, 1)};

  public ShapeT(Orientation orientation) {
    super(orientation);
  }

  @Override
  public Color getColor() {
    return Color.BLUE;
  }

  @Override
  protected Point[] getBaseBlocks(Orientation orientation) {
    return switch (orientation) {
      case North -> N_BLOCKS;
      case East -> E_BLOCKS;
      case South -> S_BLOCKS;
      case West -> W_BLOCKS;
    };
  }

  @Override
  protected Shape newShape(Orientation orientation) {
    return new ShapeT(orientation);
  }
}
