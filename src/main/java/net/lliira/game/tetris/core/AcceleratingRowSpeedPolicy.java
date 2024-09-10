package net.lliira.game.tetris.core;

/**
 * For every 100 rows removed, we will speed up by 100 ms. Speed is represented
 * as the intervals between each drop step (row) of a shape;
 */
public class AcceleratingRowSpeedPolicy implements SpeedPolicy {
    private static final long BASE_SPEED_MS = 1000;
    private static final int ROW_INTERVAL = 100;

    @Override
    public long nextSpeed(GameStats stats) {
        int mod = stats.rowsRemoved / ROW_INTERVAL;
        return Math.max(100, BASE_SPEED_MS - mod * 100);
    }
}
