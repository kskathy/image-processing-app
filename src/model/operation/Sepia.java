package model.operation;

import model.IModel;
import java.util.ArrayList;
import java.util.Arrays;
import model.operation.IOperation;

/**
 * This class contains the applies the sepia effect to an image.
 */
public class Sepia implements IOperation {

  /**
   * Applies a sepia effect on an image.
   *
   * @param model an IModel
   */
  @Override
  public void apply(IModel model) {
    model.applyColorTransformation(new ArrayList<>(
        Arrays.asList(new ArrayList<>(Arrays.asList(0.393, 0.769, 0.189)),
            new ArrayList<>(Arrays.asList(0.349, 0.686, 0.168)),
            new ArrayList<>(Arrays.asList(0.272, 0.534, 0.131)))));
  }

}
