package controller;

import model.ILayerModel;
import model.operation.Sepia;

/**
 * Applies a sepia image transformation to a layer.
 */
class LayerSepia implements ILayerOperation {

  /**
   * Applies sepia to a layer in an ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.applyToCur(new Sepia());
  }
}
