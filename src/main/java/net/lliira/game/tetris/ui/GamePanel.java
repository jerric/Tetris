package net.lliira.game.tetris.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

  private final int width;
  private final int height;
  private final NextShapePanel nextShapePanel;
  private final BoardPanel boardPanel;

  public GamePanel(int width, int height, int unitSize) {
    this.width = width;
    this.height = height;
    this.nextShapePanel = new NextShapePanel();
    this.boardPanel = new BoardPanel(width, height, unitSize);
    this.setLayout(new BorderLayout());
    this.add(nextShapePanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);
  }
}
