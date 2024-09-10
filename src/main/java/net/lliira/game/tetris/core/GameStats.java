package net.lliira.game.tetris.core;

public class GameStats {
    public enum Status {
        NotStarted,
        Running,
        Failed
    }
    public Status status = Status.NotStarted;
    public int shapesPlaced;
    public int rowsRemoved;
    public int score;
}
