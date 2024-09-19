package net.lliira.game.tetris.core;

import java.util.*;

public class ScoreTracker {
    public static class PlayerScore {
        public final String player;
        public final int score;
        PlayerScore(String player, int score) {
            this.player = player;
            this.score = score;
        }
    }

    private static final int TOP_N = 10;

    private final PriorityQueue<PlayerScore> highScores;

    public ScoreTracker() {
        highScores = new PriorityQueue<>(TOP_N, Comparator.comparingInt(a -> a.score));
    }

    public synchronized void record(String player, int score) {
        highScores.offer(new PlayerScore(player, score));
        if (highScores.size() > TOP_N) highScores.poll();
    }

    public synchronized PlayerScore[] getHighScores() {
        // Need to revert the scores, since they are sorted from low to high.
        PlayerScore[] scores = new PlayerScore[highScores.size()];
        int idx = scores.length - 1;

        // Makes a copy and pop the values to maintain order
        PriorityQueue<PlayerScore> queue = new PriorityQueue(highScores);
        while(!queue.isEmpty()) {
            scores[idx--] = queue.poll();
        }
        return scores;
    }

    public synchronized void reset() {
        highScores.clear();
    }
}
