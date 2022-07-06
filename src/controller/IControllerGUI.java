package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Interface for the controller in the GUI. This interface handles the events of the GUI.
 */
public interface IControllerGUI extends IController, ActionListener {
  /**
   * Starts up the controller.
   *
   * @throws IOException if something goes wrong with the controller
   */
  void start() throws IOException;

  /**
   * Handles all the action events in the GUI.
   *
   * @param event the ActionEvent being handled
   */
  void actionPerformed(ActionEvent event);

}
