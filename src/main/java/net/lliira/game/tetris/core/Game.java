package net.lliira.game.tetris.core;

import net.lliira.game.tetris.core.shape.Shape;
import net.lliira.game.tetris.core.shape.ShapeFactory;
import net.lliira.game.tetris.core.shape.ShapeI;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
  private static final int BUFFER_SIZE = 3;

  private final ShapeFactory shapeFactory;
  private final SpeedPolicy speedPolicy;
  private final ScorePolicy scorePolicy;
  private final Board board;
  private final Queue<Shape> nextShapes;
  private Shape shape;
  private Point origin;
  private GameStats stats;

  public Game(int width, int height) {
    this(
        width,
        height,
        new ShapeFactory(),
        new AcceleratingRowSpeedPolicy(),
        new BonusScorePolicy());
  }

  public Game(
      int width,
      int height,
      ShapeFactory shapeFactory,
      SpeedPolicy speedPolicy,
      ScorePolicy scorePolicy) {
    this.shapeFactory = shapeFactory;
    this.speedPolicy = speedPolicy;
    this.scorePolicy = scorePolicy;
    board = new Board(width, height);
    nextShapes = new LinkedList<>();
    stats = new GameStats();
  }

  public void reset() {
    stats.reset();
    board.reset();
    shape = null;
    origin = null;
    nextShapes.clear();
  }

  public void start() {
    for (int i = 0; i < BUFFER_SIZE; i++) {
      nextShapes.offer(shapeFactory.createShape());
    }
    run();
  }

  public boolean moveDown() {
    if (shape == null) return false;
    Point newOrigin = new Point(origin.x, origin.y + 1);
    if (board.isConflict(shape, newOrigin)) {
      placeShape();
      return false;
    }
    origin = newOrigin;
    return true;
  }

  public boolean moveLeft() {
    if (shape == null) return false;
    Point newOrigin = new Point(origin.x -1, origin.y);
    if (board.isConflict(shape, newOrigin)) return false;
    origin = newOrigin;
    return true;
  }

  public boolean moveRight() {
    if (shape == null) return false;
    Point newOrigin = new Point(origin.x + 1, origin.y);
    if (board.isConflict(shape, newOrigin)) return false;
    origin = newOrigin;
    return true;
  }

  public boolean rotateClockwise() {
    if (shape == null) return false;
    Shape newShape = shape.rotateClockwise();
    if (board.isConflict(newShape, origin)) return false;
    shape = newShape;
    return true;
  }

  private void run() {
    stats.status = GameStats.Status.Running;
    while(stats.status == GameStats.Status.Running) {
      if (shape == null && !newShape()) {
        // create new shape failed. game fails.
        fail();
        break;
      } else {
        moveDown();
      }
      long speed = speedPolicy.nextSpeed(stats);
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
  }

  private boolean newShape() {
    Shape shape = nextShapes.poll();
    nextShapes.offer(shapeFactory.createShape());

    Point origin = new Point(board.getWidth() / 2 - 2, 0);
    if (board.isConflict(shape, origin)) return false;
    this.shape = shape;
    this.origin = origin;
    return true;
  }

  private void placeShape() {
    board.placeShape(shape, origin);
    shape = null;
    origin = null;
    stats.shapesPlaced++;
    int rows = board.markFullRows();
    if (rows > 0) {
      stats.rowsRemoved += rows;
      stats.score += scorePolicy.score(rows, stats);
      board.removeFullRows();
    }
  }

  private void fail() {
    board.fail();
    stats.status = GameStats.Status.Failed;
  }
}
