package net.lliira.game.tetris.ui;

import net.lliira.game.tetris.core.shape.Shape;

import java.awt.*;

public final class UIHelper {

  public static void drawBlock(Graphics2D g, int screenX, int screenY, int unitSize, Color color) {
    int boundX = screenX + unitSize - 1, boundY = screenY + unitSize - 1;
    // Draws highlight
    g.setColor(getHighlight(color));
    g.drawLine(screenX, screenY, boundX - 1, screenY);
    g.drawLine(screenX, screenY + 1, boundX - 2, screenY + 1);
    g.drawLine(screenX, screenY + 2, screenX, boundY);
    g.drawLine(screenX + 1, screenY + 2, screenX + 1, boundY - 1);

    // draw lowlight
    g.setColor(getLowlight(color));
    g.drawLine(screenX + 1, boundY, boundX, boundY);
    g.drawLine(screenX + 2, boundY - 1, boundX, boundY - 1);
    g.drawLine(boundX, screenY + 1, boundX, boundY - 1);
    g.drawLine(boundX - 1, screenY + 2, boundX - 1, boundY - 2);

    // draw the main color
    g.setColor(color);
    g.fillRect(screenX + 1, screenY + 1, unitSize - 4, unitSize - 4);
  }

  private static Color getHighlight(Color color) {
    return new Color(
        (255 + color.getRed()) / 2, (255 + color.getGreen()) / 2, (255 + color.getBlue()) / 2);
  }

  private static Color getLowlight(Color color) {
    return new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2);
  }
}
