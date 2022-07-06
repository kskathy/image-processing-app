package controller;

import model.ILayerModel;

/**
 * A non-public class to select the current layer in an ILayerModel.
 */
class Current implements ILayerOperation {

  private int i;

  /**
   * The constructor.
   *
   * @param i the layer index to be selected
   */
  public Current(int i) {
    this.i = i - 1; // Convert from more user-friendly indexing from 1
  }

  /**
   * Applies the selection of a layer.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.selectLayer(i);
  }
}
