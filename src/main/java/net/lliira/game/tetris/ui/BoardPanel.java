package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.Board;
import net.lliira.game.tetris.core.Game;
import net.lliira.game.tetris.core.shape.Shape;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

  private static final int MARGIN = 5;

  private final Game game;
  private final int unitSize;

  public BoardPanel(Game game, int width, int height, int unitSize) {
    super(true);
    this.game = game;
    this.unitSize = unitSize;
    int panelWidth = width * unitSize + MARGIN * 2;
    int panelHeight = height * unitSize + MARGIN * 2;
    setPreferredSize(new Dimension(panelWidth, panelHeight));
    setBackground(Color.GREEN);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    paintBackground(g2d);
    paintBlocks(g2d);
    paintShape(g2d);
  }

  private void paintBackground(Graphics2D g) {
    g.setColor(Color.ORANGE);
    int borderSize = MARGIN - 2;
    g.fillRect(borderSize, borderSize, getWidth() - 2 * borderSize, getHeight() - 2 * borderSize);
    g.setColor(Color.BLACK);
    g.fillRect(MARGIN, MARGIN, getWidth() - 2 * MARGIN, getHeight() - 2 * MARGIN);
  }

  private void paintBlocks(Graphics2D g) {
    Board.Row row = game.getBoard().getFirstRow();
    for (int y = 0; row != null; y++, row = row.getNext()) {
      Color[] blocks = row.getBlocks();
      for (int x = 0; x < blocks.length; x++) {
        if (blocks[x] == null) continue;
        UIHelper.drawBlock(g, toScreen(x), toScreen(y), unitSize, blocks[x]);
      }
    }
  }

  private void paintShape(Graphics2D g) {
    Shape shape = game.getShape();
    if (shape == null) return;

    Point[] blocks = shape.getBlocks(game.getShapeOrigin());
    for (Point block : blocks) {
      UIHelper.drawBlock(g, toScreen(block.x), toScreen(block.y), unitSize, shape.getColor());
    }
  }

  private int toScreen(int coord) {
    return coord * unitSize + MARGIN;
  }
}
