package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.Game;
import net.lliira.game.tetris.core.shape.Shape;

import javax.swing.*;
import java.awt.*;

public class NextShapePanel extends JPanel {
  private static final int MARGIN = 5;
  private static final int WIDTH = 4;
  private static final int HEIGHT = 15;

  private final Game game;
  private final int unitSize;

  public NextShapePanel(Game game, int unitSize) {
    super(true);
    this.game = game;
    this.unitSize = unitSize;
    int panelWidth = WIDTH * unitSize + MARGIN * 2;
    int panelHeight = HEIGHT * unitSize + MARGIN * 2;
    setPreferredSize(new Dimension(panelWidth, panelHeight));
    setBackground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int prevY = 1;
    for (Shape shape : game.getNextShapes()) {
      Dimension dimension = shape.getDimension();
      int x = dimension.width < 3 ? 2 : 1;
      int y = prevY + (dimension.height == 1 ? 0 : 1);
      drawShape(g2d, shape, new Point(x, y));
      prevY = y + (dimension.height == 1 ? 2 : dimension.height);;
    }
  }

  private void drawShape(Graphics2D g, Shape shape, Point origin) {
    Point[] blocks = shape.getBlocks(origin);
    for (Point block : blocks) {
      UIHelper.drawBlock(g, toScreen(block.x), toScreen(block.y), unitSize, shape.getColor());
    }
  }

  private int toScreen(int coord) {
    return coord * unitSize + MARGIN;
  }
}
