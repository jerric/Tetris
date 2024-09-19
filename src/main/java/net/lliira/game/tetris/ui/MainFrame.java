package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.Game;
import net.lliira.game.tetris.core.GameStats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
  private static final int UNIT_SIZE = 30;
  private static final int WIDTH = 10;
  private static final int HEIGHT = 20;

  private final Game game;
  private final GamePanel gamePanel;

  public MainFrame() {
      game  = new Game(WIDTH, HEIGHT);
    gamePanel = new GamePanel(game, WIDTH, HEIGHT, UNIT_SIZE);
    setContentPane(gamePanel);
    centerWindow();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Tetris");
    createMenu();
    pack();

      game.registerGameStatsListener(this::gameUpdated);
  }

  private void createMenu() {
      JMenuBar menuBar = new JMenuBar();
      JMenu gameMenu = new JMenu("Game");

      JMenuItem newGameItem = new JMenuItem("New Game");
      newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
      newGameItem.addActionListener(_ -> newGame());
      gameMenu.add(newGameItem);

      JMenuItem highscoreItem = new JMenuItem("High Scores");
      highscoreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
      highscoreItem.addActionListener(_ -> showHighScores());
      gameMenu.add(highscoreItem);

      gameMenu.addSeparator();

      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(_ -> exit());
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

  public void newGame() {
    game.reset();
    String player = JOptionPane.showInputDialog(this, "Who is playing?", game.getStorage().getPlayer());
    game.getStorage().setPlayer(player);
    game.start();
  }

  private void showHighScores() {
      HighscoreDialog highscoreDialog = new HighscoreDialog(this, game.getStorage().getScoreTracker());
      highscoreDialog.setVisible(true);
  }

  private void exit() {
    game.exit();
    System.exit(0);
  }

  private void gameUpdated(GameStats stats) {
      if (stats.status == GameStats.Status.Failed) {
          if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                  this, "Play again?", "Game Over!", JOptionPane.YES_NO_OPTION)) {
              game.reset();
              game.start();
          }
      }
  }
}
