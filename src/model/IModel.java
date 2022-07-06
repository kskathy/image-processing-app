package model;

import java.util.List;

/**
 * Interface for the image processing model.
 */
public interface IModel {

  /**
   * Observer for the width of the image in the model.
   *
   * @return the width of the image in this model
   */
  int getImageWidth();

  /**
   * Observer for the height of the image in the model.
   *
   * @return the height of the image in this model
   */
  int getImageHeight();

  /**
   * Observer for the maximum value of the image in the model.
   *
   * @return the maximum value of the image in this model
   */
  int getImageMax();

  /**
   * Returns a deep copy of the pixels of the image in the model. Pixels are represented as an
   * ArrayList of 3 integers.
   *
   * @return a deep copy of the pixels in the image in the model.
   */
  List<List<Integer>> copyPixels();

  /**
   * Method to apply color transformation on an image (grayscale and sepia).
   *
   * @param matrix The matrix needed to color transform images to grayscale or sepia images
   */
  void applyColorTransformation(List<List<Double>> matrix);

  /**
   * Method to apply kernel or filter transformation on an image (blur and sharpen).
   *
   * @param kernelR The first kernel needed to filter images through blurring and sharpening
   * @param kernelG The second kernel needed to filter images through blurring and sharpening
   * @param kernelB The third kernel needed to filter images through blurring and sharpening
   */
  void applyKernelTransformation(List<List<Double>> kernelR,
      List<List<Double>> kernelG, List<List<Double>> kernelB);

  /**
   * Imports and creates an image given its parameters.
   *  @param width    width of the image
   * @param height   height of the image
   * @param maxValue maximum rgb value of an image
   * @param pixels   the pixels of the image
   */
  void importImage(int width, int height, int maxValue,
      List<List<Integer>> pixels);


  /**
   * Exports a copy of the given image data, ordered as width, height, max value, and individual
   * RGB channels of each pixel.
   *
   * @return a String representation of the image
   */
  String exportImage();


}
