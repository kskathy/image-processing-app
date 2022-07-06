package controller;

import model.ILayerModel;

/**
 * A non-public class to support creating a layer.
 */
class Create implements ILayerOperation {

  private int layer;

  /**
   * The constructor.
   *
   * @param layer the layer index to be created
   */
  public Create(int layer) {
    this.layer = layer - 1;  // Convert from more user-friendly indexing from 1
  }

  /**
   * Applies creating a new layer to an ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.createLayer(layer);
  }
}
