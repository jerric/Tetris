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

  public Dimension getDimension() {
    Point[] baseBlocks = getBaseBlocks(orientation);
    int minx = 4, miny = 4, maxx = 0, maxy = 0;
    for (Point block : baseBlocks) {
      minx = Math.min(minx, block.x);
      miny = Math.min(miny, block.y);
      maxx = Math.max(maxx, block.x);
      maxy = Math.max(maxy, block.y);
    }
    return new Dimension(maxx - minx + 1, maxy - miny + 1);
  }

}
