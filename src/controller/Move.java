package controller;

import model.ILayerModel;

/**
 * A non-public class to move a layer to another layer index in an ILayerModel.
 */
class Move implements ILayerOperation {

  int from;
  int to;

  /**
   * The constructor.
   *
   * @param from Which layer index the user wants to move
   * @param to   The layer index that the user wants to move to
   */
  public Move(int from, int to) {
    this.from = from;
    this.to = to;
  }

  /**
   * Applies the moving a layer from the ILayerModel.
   *
   * @param m the ILayerModel
   */
  @Override
  public void apply(ILayerModel m) {
    m.moveLayer(from, to);
  }
}
