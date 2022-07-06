package model.operation;

import java.util.List;
import model.IModel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains the applies the grayscale effect to an image.
 */
public class Grayscale implements IOperation {

  /**
   * Applies a grayscale effect on an image.
   *
   * @param model an IModel
   */
  @Override
  public void apply(IModel model) {
    List<List<Double>> m = new ArrayList<>(Arrays
        .asList(new ArrayList<>(Arrays.asList(0.2126, 0.7152, 0.0722)),
            new ArrayList<>(Arrays.asList(0.2126, 0.7152, 0.0722)),
            new ArrayList<>(Arrays.asList(0.2126, 0.7152, 0.0722))));

    model.applyColorTransformation(m);
  }
}
