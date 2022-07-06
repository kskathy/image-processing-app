package model;

import model.operation.IOperation;

/**
 * Interface for the layer functionality represented as an ILayerModel.
 */
public interface ILayerModel {

  /**
   * Selects a layer given the layer index.
   *
   * @param layer the layer index
   */
  void selectLayer(int layer);

  /**
   * Creates a layer given the layer index for which it's created at.
   *
   * @param layer the layer index
   */
  void createLayer(int layer);

  /**
   * Removes a layer given the layer index it is to be removed at.
   *
   * @param layer the layer index
   */
  void removeLayer(int layer);

  /**
   * Moves a layer from the provided layer index to the layer index it wants to be moved to.
   *
   * @param from source layer index
   * @param to   destination layer index
   */
  void moveLayer(int from, int to);

  /**
   * Applies an IOperation (blur, sharpen, grayscale, or sepia) to a layer.
   *
   * @param op the IOperation being applied
   * @throws IllegalStateException if the provided layer is not initialized
   */
  void applyToCur(IOperation op);

  /**
   * Gets the number of layers in the model.
   *
   * @return the number of layers
   */
  int getNumLayers();

  /**
   * Loads an IImage to a layer.
   *
   * @param img the IImage
   */
  void loadLayer(IImage img);

  /**
   * Sets the visibility of a layer.
   *
   * @param visible whether or not the layer should be visible or not
   */
  void setVisibility(boolean visible);

  /**
   * Determines if the layer at a given index is visible or not.
   *
   * @param index the layer index
   * @return a boolean determining the layer visibility
   */
  boolean isVisible(int index);

  /**
   * Exports the visible layer.
   *
   * @return a string of the layer content (width, height, max, and pixels)
   */
  String exportVisible();

  /**
   * Exports a layer, visible or not, at the provided index.
   *
   * @param index the layer index
   * @return a string of the layer content (width, height, max, and pixels)
   */
  // Replace exportAll() with more flexible exportLayer to remove inefficiencies with exporting
  // all layers when only one is needed.
  String exportLayer(int index);

}
