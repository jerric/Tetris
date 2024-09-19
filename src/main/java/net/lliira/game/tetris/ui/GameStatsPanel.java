package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.Game;
import net.lliira.game.tetris.core.GameStats;

import javax.swing.*;
import java.awt.*;

public class GameStatsPanel extends JPanel {

    private final Game game;
    private final JTextField txtPlayer;
    private final JTextField txtScore;
    private final JTextField txtRowsRemoved;

    public GameStatsPanel(Game game) {
        this.game = game;
        setLayout(new GridLayout(0, 2, 5, 5));

        add(new JLabel("Player:", JLabel.TRAILING));
        txtPlayer = new JTextField("Player1", 8);
        txtPlayer.setEditable(false);
        add(txtPlayer);

        add(new JLabel("Score:", JLabel.TRAILING));
        txtScore = new JTextField("0", 8);
        txtScore.setEditable(false);
        add(txtScore);

        add (new JLabel("Rows:", JLabel.TRAILING));
        txtRowsRemoved = new JTextField("0", 8);
        txtRowsRemoved.setEditable(false);
        add(txtRowsRemoved);

        game.registerGameStatsListener(this::updateStats);
    }

    private void updateStats(GameStats stats) {
        txtScore.setText(Integer.toString(stats.score));
        txtRowsRemoved.setText(Integer.toString(stats.rowsRemoved));
    }
}
