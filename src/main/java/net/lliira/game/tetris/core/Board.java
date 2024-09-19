package net.lliira.game.tetris.core;

import net.lliira.game.tetris.core.shape.Shape;

import java.awt.*;

public class Board {

  private static final Color FULL_ROW_COLOR = Color.WHITE;
  private static final Color FAILED_COLOR = Color.GRAY;

  public static class Row {
    private final Color[] blocks;
    private int count = 0;
    private Row next = null;

    Row(int width) {
      blocks = new Color[width];
    }

    public Color[] getBlocks() {
      return blocks;
    }

    public Row getNext() {
      return next;
    }

    void fill(Color color) {
      for (int i = 0; i < blocks.length; i++) {
        if (blocks[i] != null) blocks[i] = color;
      }
    }
  }

  private final int width;
  private final int height;
  private final Row headRow;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.headRow = new Row(0);
    addRows(height);
  }

  public int getWidth() {
    return width;
  }

  public Row getFirstRow() {
    return headRow.next;
  }

  public void reset() {
    headRow.next = null;
    addRows(height);
  }

  public boolean isConflict(Shape shape, Point origin) {
    Point[] blocks = shape.getBlocks(origin);
    for (Point block : blocks) {
      if (block.x < 0 || block.x >= width || block.y < 0 || block.y >= height) return true;
      int y = 0;
      for (Row row = headRow.next; row != null; row = row.next, y++) {
        if (block.y == y) {
          if (row.blocks[block.x] != null) return true;
          break;
        }
      }
    }
    return false;
  }

  public void placeShape(Shape shape, Point origin) {
    Point[] blocks = shape.getBlocks(origin);
    int y = 0;
    for (Row row = headRow.next; row != null; row = row.next, y++) {
      for (Point block : blocks) {
        if (block.y == y && row.blocks[block.x] == null) {
          row.blocks[block.x] = shape.getColor();
          row.count++;
        }
      }
    }
  }

  public int markFullRows() {
    int count = 0;
    for (Row row = headRow.next; row != null; row = row.next) {
      if (row.count == width) {
        row.fill(FULL_ROW_COLOR);
        count++;
      }
    }
    return count;
  }

  public void removeFullRows() {
    int removed = 0;
    for (Row row = headRow.next, previous = headRow; row != null; row = row.next) {
      if (row.count == width) {
        previous.next = row.next;
        removed++;
      } else {
        previous = row;
      }
    }
    addRows(removed);
  }

  public void fail() {
    for (Row row = headRow.next; row != null; row = row.next) {
      row.fill(FAILED_COLOR);
    }
  }

  private void addRows(int count) {
    Row remain = headRow.next;
    Row previous = headRow;
    for (int i = 0; i < count; i++) {
      previous.next = new Row(width);
      previous = previous.next;
    }
    previous.next = remain;
  }
}
