package net.lliira.game.tetris.core;

import net.lliira.game.tetris.core.shape.Shape;
import net.lliira.game.tetris.core.shape.ShapeFactory;

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

  public void start() {
    for (int i = 0; i < BUFFER_SIZE; i++) {
      nextShapes.offer(shapeFactory.createShape());
    }
    run();
  }

  public boolean moveDown() {

  }

  public boolean moveLeft() {

  }

  public boolean moveRight() {

  }

  public boolean rotateClockwise() {

  }

  private void run() {
    stats.status = GameStats.Status.Running;
    while(stats.status == GameStats.Status.Running) {
      if (shape == null && !newShape()) {
        // create new shape failed. game fails.
        fail();
        break;
      } else if (!moveDown()) {
        placeShape();
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

  }

  private void placeShape() {

  }

  private void fail() {

  }
}
