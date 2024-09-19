package net.lliira.game.tetris.core;

public class GameStats {
    public enum Status {
        NotStarted,
        Running,
        Failed,
        Exit,
    }
    public Status status = Status.NotStarted;
    public int shapesPlaced;
    public int rowsRemoved;
    public int score;

    public void reset() {
        status = Status.NotStarted;
        shapesPlaced = 0;
        rowsRemoved = 0;
        score = 0;
    }
}
