package controller;

import model.ILayerModel;
import model.operation.Sharpen;

/**
 * Applies a sharpening image transformation to a layer.
 */
class LayerSharpen implements ILayerOperation {

  /**
   * Applies sharpening to a layer in an ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.applyToCur(new Sharpen());
  }
}
