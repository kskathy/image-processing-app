package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Interface for ViewGUI to handle the view and layout of the GUI.
 */
public interface IViewGUI {

  /**
   * Renders a message to a view label.
   *
   * @param msg the message being rendered
   */
  void renderMessage(String msg);

  /**
   * Displays the GUI frame.
   */
  void display();

  /**
   * Handles all the action events.
   *
   * @param e the action event
   */
  void setListener(ActionListener e);

  /**
   * Gets the selected layer of the model.
   *
   * @return the index of the selected layer
   */
  int getSelectedLayer();

  /**
   * Outputs the filepath string of where the user wants the image to be saved.
   *
   * @return the filepath string
   */
  String showSaveDialog();

  /**
   * Outputs the filepath string of the image the user wants to load in.
   *
   * @return the filepath string
   */
  String showLoadDialog();

  /**
   * Updates the count of the number of layers.
   *
   * @param i the layer index
   */
  void updateLayerCount(int i);

  /**
   * Renders an image to the image label.
   *
   * @param img the BufferedImage to be rendered
   */
  void renderImage(BufferedImage img);

}
