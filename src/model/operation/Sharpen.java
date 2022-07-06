package model.operation;

import java.util.List;
import model.IModel;
import java.util.ArrayList;
import java.util.Arrays;
import model.operation.IOperation;

/**
 * This class contains the applies the sharpen effect to an image.
 */
public class Sharpen implements IOperation {

  /**
   * Applies a sharpening effect on an image.
   *
   * @param model an IModel
   */
  @Override
  public void apply(IModel model) {
    List<List<Double>> m = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 1., 0.25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)),
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125))));

    model.applyKernelTransformation(m, m, m);
  }
}
