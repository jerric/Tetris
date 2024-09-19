package net.lliira.game.tetris.core;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GameStorage {
  private static final String GAME_FILE = "lliira-tetris.json";
  private static final String DEFAULT_PLAYER = "Unknown";

  private static class Model {
    private String lastPlayer = DEFAULT_PLAYER;
    private ScoreTracker.PlayerScore[] highScores = new ScoreTracker.PlayerScore[0];
  }

  private final Gson gson;
  private Model model;

  public GameStorage() {
    gson = new Gson();
  }

  public String lastPlayer() {
      return model.lastPlayer;
  }

  public GameStorage lastPlayer(String player) {
      model.lastPlayer = player;
      return this;
  }

  public ScoreTracker getScoreTracker() {
      ScoreTracker tracker = new ScoreTracker();
      for (var score : model.highScores) {
          tracker.record(score.player, score.score);
      }
      return tracker;
  }

  public void load() throws IOException {
    File gameFile = new File(GAME_FILE);
    if (gameFile.exists()) {
      try (FileReader reader = new FileReader(GAME_FILE)) {
        Model model = gson.fromJson(reader, Model.class);
      }
    } else {
      model = new Model();
    }
  }
}
