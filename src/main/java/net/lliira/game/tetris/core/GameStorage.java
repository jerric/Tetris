package net.lliira.game.tetris.core;

import com.google.gson.Gson;

import java.io.*;

public class GameStorage {
  private static final String GAME_FILE = "lliira-tetris.json";
  private static final String DEFAULT_PLAYER = "Unknown";

  private static class Model {
    private String lastPlayer = DEFAULT_PLAYER;
    private ScoreTracker.PlayerScore[] highScores = new ScoreTracker.PlayerScore[0];
  }

  private final Gson gson;
  private final ScoreTracker scoreTracker;
  private String player;

  public GameStorage() {
    gson = new Gson();
    scoreTracker = new ScoreTracker();
    try {
      load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public ScoreTracker getScoreTracker() {
    return scoreTracker;
  }

  public void load() throws IOException {
    File gameFile = new File(GAME_FILE);
    scoreTracker.reset();
    if (gameFile.exists()) {
      try (FileReader reader = new FileReader(GAME_FILE)) {
        Model model = gson.fromJson(reader, Model.class);
        player = model.lastPlayer;
        for (var score : model.highScores) {
          scoreTracker.record(score.player, score.score);
        }
      }
    } else {
      player = DEFAULT_PLAYER;
    }
  }

  public void save() throws IOException {
    try (FileWriter writer = new FileWriter(GAME_FILE, false)) {
      Model model = new Model();
      model.lastPlayer = player;
      model.highScores = scoreTracker.getHighScores();
      String json = gson.toJson(model);
      writer.write(json);
    }
  }
}
