package model;

/**
 * This is the image creator interface. It contains methods to make a desired image.
 */
public interface ICreator {

  /**
   * Creates an image programmatically depending on the constructor of the implementing object.
   *
   * @return an IImage of the proper representation.
   */
  IImage createImage();
}
