package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

  private final int width;
  private final int height;
  private final NextShapePanel nextShapePanel;
  private final BoardPanel boardPanel;
  private final Game game;

  public GamePanel(int width, int height, int unitSize) {
    this.width = width;
    this.height = height;
    game = new Game(width, height);

    this.nextShapePanel = new NextShapePanel(game, unitSize);
    this.boardPanel = new BoardPanel(game, width, height, unitSize);
    this.setLayout(new BorderLayout());

    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(new GameStatsPanel(game), BorderLayout.NORTH);
    leftPanel.add(nextShapePanel, BorderLayout.CENTER);

    this.add(leftPanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);

    KeyboardFocusManager keyboardManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    keyboardManager.addKeyEventDispatcher(this::processKeyInput);

    game.registerGameStatsListener(_ -> repaint());
    game.start();
  }

  private boolean processKeyInput(KeyEvent event) {
    if (event.getID() != KeyEvent.KEY_PRESSED) return false;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
      if (!game.moveLeft())
        toolkit.beep();
    } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (!game.moveRight())
        toolkit.beep();
    } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
      game.moveDown();
    } else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
      if (!game.rotateClockwise())
        toolkit.beep();
    }
    return true;
  }
}
