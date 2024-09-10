package net.lliira.game.tetris.core;

public class BonusScorePolicy implements ScorePolicy {
    private static int BASE_SCORE = 1;

    @Override
    public int score(int rowsRemoved, GameStats stats) {
        int sum = 0;
        for (int i = 1; i <= rowsRemoved; i++) {
            sum += i;
        }
        return sum * BASE_SCORE;
    }
}
