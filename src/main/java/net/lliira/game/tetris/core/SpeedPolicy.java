package net.lliira.game.tetris.core;

public interface SpeedPolicy {
    long nextSpeed(GameStats stats);
}
