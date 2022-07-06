package controller;

import model.ILayerModel;
import model.operation.Grayscale;

/**
 * Applies a grayscale image transformation to a layer.
 */
class LayerGrayscale implements ILayerOperation {

  /**
   * Applies grayscale to a layer in an ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.applyToCur(new Grayscale());
  }
}
