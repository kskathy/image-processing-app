package controller;

import model.ILayerModel;
import model.operation.Blur;

/**
 * Applies a blurring image transformation to a layer.
 */
class LayerBlur implements ILayerOperation {

  /**
   * Applies blurring to a layer in an ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.applyToCur(new Blur());
  }
}
