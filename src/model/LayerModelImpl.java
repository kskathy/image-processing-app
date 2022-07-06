package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.operation.IOperation;

/**
 * This class implements the ILayerModel interface. It stores the current layer, all layers, layers
 * to be added, and the visibility of layers. This model works to provide layering functionality
 * while applying image transformations.
 */
public class LayerModelImpl implements ILayerModel {

  // Protected fields allow for potential extension
  protected int curLayer; // Current layer, indexed from 0
  protected int width = -1; // -1 indicates uninitialized width/height
  protected int height = -1;
  protected ArrayList<IModel> layers;

  // Note: visibility follows the layer itself and not the layer number. Ex. In a LayerModelImpl
  // with two visible layers, making the first invisible and then moving it to the second layer
  // means the image originally in the second layer is now visible in the first layer after
  // being shifted up. The second layer/image is now invisible.
  protected HashMap<IModel, Boolean> visibility;

  /**
   * Constructor for the LayerModelImpl class. Creates an empty layer to be filled later by
   * default.
   */
  public LayerModelImpl() {
    this.curLayer = 0;
    this.layers = new ArrayList<>();
    this.layers.add(null);
    this.visibility = new HashMap<IModel, Boolean>();
  }

  /**
   * Selects a layer given the layer index.
   *
   * @param layer the layer index
   */
  @Override
  public void selectLayer(int layer) {

    if (!this.validLayer(layer)) {
      throw new IllegalArgumentException("Selected invalid layer index: " + layer);
    }

    this.curLayer = layer;
  }

  /**
   * Creates a layer given the layer index for which it's created at.
   *
   * @param layer the layer index
   */
  @Override
  public void createLayer(int layer) {

    if (layer != this.layers.size() && !this.validLayer(layer)) {
      throw new IllegalArgumentException("createLayer given invalid index: " + layer);
    }

    // null model represents lack of image/empty layer
    this.layers.add(layer, null);
    this.curLayer = layer;
  }

  /**
   * Removes a layer given the layer index it is to be removed at.
   *
   * @param layer the layer index
   */
  @Override
  public void removeLayer(int layer) {

    if (!this.validLayer(layer)) {
      throw new IllegalArgumentException("removeLayer given invalid index: " + layer);
    }
    if (this.layers.size() == 1) {
      throw new IllegalArgumentException("Cannot remove the last layer");
    }

    this.visibility.remove(this.layers.remove(layer));
  }

  /**
   * Moves a layer from the provided layer index to the layer index it wants to be moved to.
   *
   * @param from source layer index
   * @param to   destination layer index
   */
  @Override
  public void moveLayer(int from, int to) {

    // If valid indices
    if (this.validLayer(from) && this.validLayer(to)) {

      if (from == to) { // Do nothing if move to same spot
        return;
      }

      if (from < to) { // If moving to a later index
        // Sub 1 from destination index to account for elements shifting up
        this.layers.add(to - 1, this.layers.remove(from));
      } else if (from > to) { // If moving to an earlier index
        this.layers.add(to, this.layers.remove(from));
      }

      this.curLayer = to; // Set pointer to moved location.

    } else {
      throw new IllegalArgumentException("Invalid move call: " + from + "to " + to);
    }
  }

  /**
   * Applies an IOperation (blur, sharpen, grayscale, or sepia) to a layer.
   *
   * @param op the IOperation being applied
   * @throws IllegalStateException if the provided layer is not initialized
   */
  @Override
  public void applyToCur(IOperation op) throws IllegalStateException {
    if (op == null) {
      throw new IllegalArgumentException("Given a null operation to apply.");
    }

    if (this.layers.get(curLayer) == null) { // Null indicates image has not been loaded into layer
      throw new IllegalStateException("Layer " + (curLayer + 1) + " not initialized");
    } else {
      op.apply(this.layers.get(curLayer));
    }
  }

  /**
   * Gets the number of layers in the model.
   *
   * @return the number of layers
   */
  @Override
  public int getNumLayers() {
    return this.layers.size();
  }

  /**
   * Loads an IImage to a layer.
   *
   * @param img the IImage
   */
  @Override
  public void loadLayer(IImage img) {

    if (img == null) {
      throw new IllegalArgumentException("Cannot load null image.");
    }

    if (this.layers.size() == 1 || this.width == -1
        || this.height == -1) { // If only one layer, can load an image of different dimensions
      this.width = img.getWidth();
      this.height = img.getHeight();
    } else if (img.getHeight() != this.height || img.getWidth() != this.width) {
      throw new IllegalArgumentException("Must load an image of the same dimensions");
    }

    if (this.layers.get(curLayer) != null) { // If replacing an existing image in the layer

      List<List<Integer>> pixels = new ArrayList<>();

      for (int i = 0; i < img.getWidth() * img.getHeight(); i++) {
        pixels.add(img.getPixelAt(i % img.getWidth(), i / img.getWidth()));
      }

      this.layers.get(curLayer)
          .importImage(img.getWidth(), img.getHeight(), img.getMaxValue(), new ArrayList<>(pixels));
    } else {
      this.layers.set(curLayer, new ModelImpl(img)); // Create new Model for image

      // add to visibility map and set visible by default
      this.visibility.put(this.layers.get(curLayer), true);
    }

  }

  /**
   * Sets the visibility of a layer.
   *
   * @param visible whether or not the layer should be visible or not
   */
  @Override
  public void setVisibility(boolean visible) {
    if (this.layers.get(curLayer) == null) {
      throw new IllegalArgumentException("Cannot set visibility of an empty layer.");
    }
    this.visibility.replace(this.layers.get(curLayer), visible);
  }

  /**
   * Determines if the layer at a given index is visible or not.
   *
   * @param index the layer index
   * @return a boolean determining the layer visibility
   */
  @Override
  public boolean isVisible(int index) {

    if (!this.validLayer(index)) {
      throw new IllegalArgumentException("Cannot check visibility of invalid layer.");
    }

    IModel m = this.layers.get(index);

    if (m == null) {
      // Empty layers are visible by default. Operations cannot be performed on empty
      // layers to change its state/visibility either
      return true;
    }

    return this.visibility.get(m);

  }

  /**
   * Exports the visible layer.
   *
   * @return a string of the layer content (width, height, max, and pixels)
   */
  @Override
  public String exportVisible() {

    for (int i = this.layers.size() - 1; i >= 0; i--) {
      if (this.visibility.get(this.layers.get(i)) != null && this.visibility
          .get(this.layers.get(i))) {
        return this.layers.get(i).exportImage();
      }
    }
    throw new IllegalArgumentException("No visible layers to export.");
  }

  /**
   * Exports a layer, visible or not, at the provided index.
   *
   * @param index the layer index
   * @return a string of the layer content (width, height, max, and pixels)
   */
  @Override
  public String exportLayer(int index) {

    if (!this.validLayer(index)) {
      throw new IllegalArgumentException("Invalid layer index for export.");
    }

    IModel m = this.layers.get(index);

    if (m == null) {
      throw new IllegalStateException("Layer at given index is not filled.");
    }

    return m.exportImage();

  }

  /**
   * Helper to determine if a layer is valid or not.
   *
   * @param layer the layer index
   * @return a boolean determining if it's valid or not
   */
  private boolean validLayer(int layer) {
    return layer >= 0 && layer < this.layers.size();
  }

}
