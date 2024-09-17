package net.lliira.game.tetris.core.shape;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ShapeFactoryTest {

  @Test
  public void createShapes() {
    boolean hasI = false,
        hasJ = false,
        hasL = false,
        hasO = false,
        hasS = false,
        hasT = false,
        hasZ = false;
    ShapeFactory factory = new ShapeFactory();
    for (int i = 0; i < 100; i++) {
      Shape shape = factory.createShape();
      if (shape instanceof ShapeI) hasI = true;
      else if (shape instanceof ShapeJ) hasJ = true;
      else if (shape instanceof ShapeL) hasL = true;
      else if (shape instanceof ShapeO) hasO = true;
      else if (shape instanceof ShapeS) hasS = true;
      else if (shape instanceof ShapeT) hasT = true;
      else if (shape instanceof ShapeZ) hasZ = true;
    }
    assertTrue(hasI);
    assertTrue(hasJ);
    assertTrue(hasL);
    assertTrue(hasO);
    assertTrue(hasS);
    assertTrue(hasT);
    assertTrue(hasZ);
  }
}
