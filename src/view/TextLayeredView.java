package view;

import java.io.IOException;
import java.util.Scanner;
import model.ILayerModel;

/**
 * This class implements IView and represents a textual view of the ILayerModel that can be printed
 * to console screen.
 */
public class TextLayeredView implements IView {

  ILayerModel model;
  Appendable a;

  /**
   * Class constructor that creates a new TextLayeredView from the given model state.
   *
   * @param model An instance of the ILayerModel
   * @param a     An instance of the appendable
   */
  public TextLayeredView(ILayerModel model, Appendable a) {

    if (model == null) {
      throw new IllegalArgumentException("TextView given null model.");
    }
    if (a == null) {
      throw new IllegalArgumentException("TextView given null appendable.");
    }

    this.model = model;
    this.a = a;

  }

  /**
   * Renders the model to the provided data destination.
   */
  @Override
  public void render() {

    Scanner scan;

    try {
      this.a.append("--- CURRENT LAYERS ---\n");
      for (int i = 1; i <= this.model.getNumLayers(); i++) {
        String layerString = "";

        try {
          scan = new Scanner(this.model.exportLayer(i - 1));

          for (int j = 0; j < 6; j++) {
            layerString += scan.next() + " ";
          }

        } catch (IllegalStateException e) {
          layerString = "Empty";
        }

        this.a
            .append("Layer " + i + " [" + this.model.isVisible(i - 1) + "]: " + layerString + "\n");

      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Text rendering of layers failed.");
    }


  }

  /**
   * Renders a specific message to the provided data destination.
   *
   * @param msg the message to be transmitted
   */
  @Override
  public void renderMessage(String msg) {

    try {
      this.a.append(msg + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write message to appendable.");
    }
  }
}
