package net.lliira.game.tetris.core.shape;

import java.awt.*;

public abstract class Shape {

  public enum Orientation {
    North,
    East,
    South,
    West;
  }

  protected static Point P(int x, int y) {
    return new Point(x, y);
  }

  private Orientation orientation;

  protected Shape(Orientation orientation) {
    this.orientation = orientation;
  }

  public abstract Color getColor();

  protected abstract Point[] getBaseBlocks(Orientation orientation);

  protected abstract Shape newShape(Orientation orientation);

  public Shape rotateClockwise() {
    Orientation[] all = Orientation.values();
    int index = (orientation.ordinal() + 1) % all.length;
    return newShape(all[index]);
  }

  public Shape rotateCounterClockwise() {
    Orientation[] all = Orientation.values();
    int index = (orientation.ordinal() + all.length - 1) % all.length;
    return newShape(all[index]);
  }

  public Point[] getBlocks(Point origin) {
    Point[] baseBlocks = getBaseBlocks(orientation);
    Point[] blocks = new Point[baseBlocks.length];
    for (int i = 0; i < blocks.length; i++) {
      Point base = baseBlocks[i];
      blocks[i] = P(base.x + origin.x, base.y + origin.y);
    }
    return blocks;
  }
}
