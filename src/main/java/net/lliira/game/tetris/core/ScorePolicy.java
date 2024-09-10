package net.lliira.game.tetris.core;

public interface ScorePolicy {
    int score(int rowsRemoved, GameStats stats);
}
