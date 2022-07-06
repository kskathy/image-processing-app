package controller;

import model.ILayerModel;

/**
 * The interface that applies the respective methods from the ILayerModel with the ILayerOperations.
 */
public interface ILayerOperation {

  /**
   * Applies the respective methods from the ILayerModel.
   *
   * @param m the ILayerModel
   */
  void apply(ILayerModel m);

}
