package controller;

import java.io.IOException;

/**
 * Interface for the controller. An implementation will work with the layered model
 * interface to provide image transformations and layering on images.
 */
public interface IController {

  /**
   * Starts up the controller.
   *
   * @throws IOException if something goes wrong with the controller
   */
  void start() throws IOException;

}
