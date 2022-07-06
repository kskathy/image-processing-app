package controller;

import model.ILayerModel;

/**
 * A non-public class to delete a layer in an ILayerModel.
 */
class Delete implements ILayerOperation {

  private int layer;

  /**
   * The constructor.
   *
   * @param layer the layer index to be deleted
   */
  public Delete(int layer) {
    this.layer = layer - 1;
  }

  /**
   * Applies deleting a layer from an ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.removeLayer(layer); // Convert from more user-friendly indexing from 1
  }
}
