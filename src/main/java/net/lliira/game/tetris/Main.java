package net.lliira.game.tetris;

import net.lliira.game.tetris.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        mainFrame.newGame();
    }
}