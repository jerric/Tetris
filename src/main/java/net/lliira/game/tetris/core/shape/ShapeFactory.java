package net.lliira.game.tetris.core.shape;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ShapeFactory {

  private final Random random;
  private final List<Function<Shape.Orientation, Shape>> shapeSuppliers;

  public ShapeFactory() {
    this(new Random());
  }

  public ShapeFactory(Random random) {
    this.random = random;
    shapeSuppliers =
        List.of(
            ShapeI::new,
            ShapeJ::new,
            ShapeL::new,
            ShapeO::new,
            ShapeS::new,
            ShapeT::new,
            ShapeZ::new);
  }

  public Shape createShape() {
    var supplier = shapeSuppliers.get(random.nextInt(shapeSuppliers.size()));
    Shape.Orientation[] orientations = Shape.Orientation.values();
    var orientation = orientations[random.nextInt(orientations.length)];
    return supplier.apply(orientation);
  }
}
