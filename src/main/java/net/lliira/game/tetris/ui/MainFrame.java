package net.lliira.game.tetris.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
  private static final int UNIT_SIZE = 30;
  private static final int WIDTH = 10;
  private static final int HEIGHT = 20;

  private final GamePanel gamePanel;

  public MainFrame() {
    gamePanel = new GamePanel(WIDTH, HEIGHT, UNIT_SIZE);
    setContentPane(gamePanel);
    centerWindow();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Tetris");
    createMenu();
    pack();
  }

  private void createMenu() {
      JMenuBar menuBar = new JMenuBar();
      JMenu gameMenu = new JMenu("Game");
      JMenuItem newGameItem = new JMenuItem("New Game");
      newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
      gameMenu.add(newGameItem);

      JMenuItem exitItem = new JMenuItem("Exit");
      gameMenu.add(exitItem);
      menuBar.add(gameMenu);

      JMenu helpMenu = new JMenu("Help");
      JMenuItem aboutItem = new JMenuItem("About...");
      helpMenu.add(aboutItem);
      menuBar.add(helpMenu);

      setJMenuBar(menuBar);
  }

  private void centerWindow() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screenSize.width - getPreferredSize().width) / 2;
    int y = (screenSize.height - getPreferredSize().height) / 2;
    setLocation(x, y);
  }
}
