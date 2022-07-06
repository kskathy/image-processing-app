package controller;

import model.ILayerModel;

/**
 * A non-public class to set the visibility a layer in an ILayerModel.
 */
class Visible implements ILayerOperation {

  private boolean visible;

  /**
   * The constructor.
   *
   * @param visible boolean determining if the layer is visible or not.
   */
  public Visible(boolean visible) {
    this.visible = visible;
  }

  /**
   * Applies the visibility of a layer.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.setVisibility(visible);
  }
}
