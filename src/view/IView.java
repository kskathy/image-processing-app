package view;


/**
 * This is the interface for TextLayeredView that represents the view of the ILayeredModel.
 */
public interface IView {

  /**
   * Renders the model to the provided data destination.
   */
  void render();

  /**
   * Renders a specific message to the provided data destination.
   *
   * @param msg the message to be transmitted
   */
  void renderMessage(String msg);

}
