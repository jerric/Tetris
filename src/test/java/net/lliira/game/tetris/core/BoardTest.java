package net.lliira.game.tetris.core;

import static org.testng.Assert.*;

import net.lliira.game.tetris.core.shape.Shape;
import net.lliira.game.tetris.core.shape.ShapeJ;
import net.lliira.game.tetris.core.shape.ShapeO;
import org.testng.annotations.Test;

import java.awt.*;

public class BoardTest {
  @Test
  public void noConflict() {
    Board board = new Board(10, 25);
    board.placeShape(new ShapeO(Shape.Orientation.East), new Point(4, 5));
    Shape shape = new ShapeJ(Shape.Orientation.East);
    assertFalse(board.isConflict(shape, new Point(4, 2)));
  }

  @Test
  public void hasConflictWithBlocks() {
    Board board = new Board(10, 25);
    board.placeShape(new ShapeO(Shape.Orientation.East), new Point(4, 5));
    Shape shape = new ShapeJ(Shape.Orientation.North);
    assertTrue(board.isConflict(shape, new Point(4, 3)));
  }

  @Test
  public void hasConflictWithLeftEdge() {
    Board board = new Board(10, 25);
    board.placeShape(new ShapeO(Shape.Orientation.East), new Point(4, 5));
    Shape shape = new ShapeJ(Shape.Orientation.East);
    assertTrue(board.isConflict(shape, new Point(-1, 5)));
  }
}
