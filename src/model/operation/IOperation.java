package model.operation;

import model.IModel;

/**
 * This is the IOperation interface that contains the apply method to apply a image processing
 * effect.
 */
public interface IOperation {

  /**
   * Applies an image processing effect.
   *
   * @param model an IModel
   */
  void apply(IModel model);

}
