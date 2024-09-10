package net.lliira.game.tetris.core.shape;

import java.awt.*;

public class ShapeO extends Shape {

  private static final Point[] BLOCKS = new Point[] {P(0, 0), P(0, 1), P(1, 0), P(1, 1)};

  public ShapeO(Orientation orientation) {
    super(orientation);
  }

  @Override
  public Color getColor() {
    return Color.YELLOW;
  }

  @Override
  protected Point[] getBaseBlocks(Orientation orientation) {
    return BLOCKS;
  }

  @Override
  protected Shape newShape(Orientation orientation) {
    return new ShapeO(orientation);
  }
}
