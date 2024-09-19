package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.ScoreTracker;

import javax.swing.*;
import java.awt.*;

public class HighscoreDialog extends JDialog {
  private static final int MARGIN = 20;

  private final ScoreTracker scoreTracker;
  private final JPanel scoresPanel;

  public HighscoreDialog(JFrame parent, ScoreTracker scoreTracker) {
    super(parent, "Tetris High Scores", true);
    this.scoreTracker = scoreTracker;
    scoresPanel = new JPanel(new GridLayout(0, 3, 5, 5));
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.add(scoresPanel, BorderLayout.CENTER);
    createBorders(contentPanel);

    setContentPane(contentPanel);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    displayHighscores();
    pack();
    setLocationRelativeTo(parent);
    setResizable(false);
  }

  private void createBorders(JPanel contentPanel) {
    JPanel topPanel = new JPanel();
    topPanel.setPreferredSize(new Dimension(MARGIN, MARGIN));
    contentPanel.add(topPanel, BorderLayout.NORTH);

    JPanel leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension(MARGIN, MARGIN));
    contentPanel.add(leftPanel, BorderLayout.WEST);

    JPanel rightPanel = new JPanel();
    rightPanel.setPreferredSize(new Dimension(MARGIN, MARGIN));
    contentPanel.add(rightPanel, BorderLayout.EAST);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setPreferredSize(new Dimension(MARGIN, MARGIN));
    contentPanel.add(bottomPanel, BorderLayout.SOUTH);
  }

  private void displayHighscores() {
    // Adds the title row
    scoresPanel.add(new JLabel("Rank", JLabel.CENTER));
    scoresPanel.add(new JLabel("Player", JLabel.CENTER));
    scoresPanel.add(new JLabel("Score", JLabel.CENTER));

    int rank = 1;
    for (ScoreTracker.PlayerScore score : scoreTracker.getHighScores()) {
      scoresPanel.add(new JLabel(Integer.toString(rank++), JLabel.CENTER));
      scoresPanel.add(new JLabel(score.player, JLabel.CENTER));
      scoresPanel.add(new JLabel(Integer.toString(score.score), JLabel.TRAILING));
    }
  }
}
