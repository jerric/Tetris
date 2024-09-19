package net.lliira.game.tetris.core;

import net.lliira.game.tetris.core.shape.Shape;
import net.lliira.game.tetris.core.shape.ShapeFactory;
import net.lliira.game.tetris.core.shape.ShapeI;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class Game {
  private static final int BUFFER_SIZE = 3;
  private static final long FULL_ROW_SLEEP = 500;

  private final ShapeFactory shapeFactory;
  private final SpeedPolicy speedPolicy;
  private final ScorePolicy scorePolicy;
  private final Board board;
  private final LinkedList<Shape> nextShapes;
  private final GameStats stats;
  private final List<Consumer<GameStats>> gameStatsListeners;
  private final GameStorage storage;

  private Shape shape;
  private Point origin;

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
    gameStatsListeners = new LinkedList<>();
    storage = new GameStorage();
    new Thread(this::run).start();
  }

  public void registerGameStatsListener(Consumer<GameStats> listener) {
    gameStatsListeners.add(listener);
  }

  public GameStats getStats() {
    return stats;
  }

  public Shape getShape() {
    return shape;
  }

  public Point getShapeOrigin() {
    return origin;
  }

  public Board getBoard() {
    return board;
  }

  public List<Shape> getNextShapes() {
    return nextShapes;
  }

  public GameStorage getStorage() {
    return storage;
  }

  public synchronized void start() {
    for (int i = 0; i < BUFFER_SIZE; i++) {
      nextShapes.offer(shapeFactory.createShape());
    }
    stats.status = GameStats.Status.Running;
    triggerListeners();
  }

  public synchronized void reset() {
    stats.reset();
    board.reset();
    shape = null;
    origin = null;
    nextShapes.clear();
    stats.status = GameStats.Status.NotStarted;
    triggerListeners();
  }


  public void exit() {
    stats.status = GameStats.Status.Exit;
  }

  private void run() {
    while (stats.status != GameStats.Status.Exit) {
      long speed = 100L;
      if (stats.status == GameStats.Status.Running) {
        if (shape == null && !newShape()) {
          // create new shape failed. game fails.
          fail();
        } else {
          moveDown();
        }
        speed = speedPolicy.nextSpeed(stats);
      }
      try {
        Thread.sleep(speed);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public synchronized void fail() {
    board.fail();
    stats.status = GameStats.Status.Failed;
    storage.getScoreTracker().record(storage.getPlayer(), stats.score);
    try {
      storage.save();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    triggerListeners();
  }

  public synchronized boolean moveDown() {
    if (shape == null) return false;
    Point newOrigin = new Point(origin.x, origin.y + 1);
    if (board.isConflict(shape, newOrigin)) {
      placeShape();
      return false;
    }
    origin = newOrigin;
    triggerListeners();
    return true;
  }

  public synchronized boolean moveLeft() {
    if (shape == null) return false;
    Point newOrigin = new Point(origin.x - 1, origin.y);
    if (board.isConflict(shape, newOrigin)) return false;
    origin = newOrigin;
    triggerListeners();
    return true;
  }

  public synchronized boolean moveRight() {
    if (shape == null) return false;
    Point newOrigin = new Point(origin.x + 1, origin.y);
    if (board.isConflict(shape, newOrigin)) return false;
    origin = newOrigin;
    triggerListeners();
    return true;
  }

  public synchronized boolean rotateClockwise() {
    if (shape == null) return false;
    Shape newShape = shape.rotateClockwise();
    if (board.isConflict(newShape, origin)) return false;
    shape = newShape;
    triggerListeners();
    return true;
  }

  private void triggerListeners() {
    for (var listener : gameStatsListeners) {
      listener.accept(stats);
    }
  }

  private synchronized boolean newShape() {
    Shape shape = nextShapes.poll();
    nextShapes.offer(shapeFactory.createShape());

    Point origin = new Point(board.getWidth() / 2 - 2, 0);
    if (board.isConflict(shape, origin)) return false;
    this.shape = shape;
    this.origin = origin;
    triggerListeners();
    return true;
  }

  private void placeShape() {
    board.placeShape(shape, origin);
    shape = null;
    origin = null;
    stats.shapesPlaced++;
    int rows = board.markFullRows();
    triggerListeners();
    // Sleeps a bit for UI to catch up the rendering.
    try {
      Thread.sleep(FULL_ROW_SLEEP);
    } catch (InterruptedException e) {
      // do nothing
    }
    if (rows > 0) {
      stats.rowsRemoved += rows;
      stats.score += scorePolicy.score(rows, stats);
      board.removeFullRows();
      triggerListeners();
    }
  }
}
