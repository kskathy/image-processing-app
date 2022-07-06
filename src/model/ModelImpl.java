package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements the IModel interface. It stores an IImage representation and includes
 * methods on applying filtering (blur and sharpen) and color transformations (grayscale and sepia)
 * on that image.
 */
public class ModelImpl implements IModel {

  private IImage image;

  /**
   * Constructor for the ModelImpl class.
   *
   * @param image The provided IImage
   */
  public ModelImpl(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Model cannot accept null image");
    }
    this.image = image;
  }

  /**
   * Method to apply a color transformation on an image (grayscale and sepia as of HW05).
   *
   * @param matrix The matrix needed to color transform images to grayscale or sepia images
   */
  @Override
  public void applyColorTransformation(List<List<Double>> matrix) {

    if (matrix == null) {
      throw new IllegalArgumentException("Cannot apply AbstractColorTransformation on null model");
    }

    if (!this.validMatrix(matrix)) {
      throw new IllegalArgumentException("Invalid matrix for colorTransformation");
    }

    if (matrix.size() != 3 || matrix.get(0).size() != 3) { // If not 3x3 matrix
      throw new IllegalArgumentException("Invalid size matrix for colorTransformation");
    }

    List<List<Integer>> pixels = this.copyPixels();
    List<List<Integer>> newImage = new ArrayList<>();

    for (int y = 0; y < this.image.getHeight(); y++) {
      for (int x = 0; x < this.image.getWidth(); x++) {
        List<Integer> pixel = pixels.get(x + (y * this.image.getWidth()));

        int r = pixel.get(0);
        int g = pixel.get(1);
        int b = pixel.get(2);

        List<Integer> transform = new ArrayList<>();

        for (int row = 0; row < 3; row++) {

          int val = (int) (matrix.get(row).get(0) * r + matrix.get(row).get(1) * g
              + matrix.get(row).get(2) * b);
          if (val > this.image.getMaxValue()) {
            val = this.image.getMaxValue();
          } else if (val < 0) {
            val = 0;
          }
          transform.add(val);
        }
        newImage.add(transform);
      }
    }
    this.image = new ImageImpl(this.image.getWidth(), this.image.getHeight(),
        this.image.getMaxValue(), newImage);
  }

  /**
   * Method to apply a kernel transformation on an image (blur and sharpen as of HW05).
   *
   * @param kernelR The first kernel needed to filter images through blurring and sharpening
   * @param kernelG The second kernel needed to filter images through blurring and sharpening
   * @param kernelB The third kernel needed to filter images through blurring and sharpening
   */
  @Override
  public void applyKernelTransformation(List<List<Double>> kernelR,
      List<List<Double>> kernelG, List<List<Double>> kernelB) {

    // Check matrix validity
    if (kernelR == null || kernelG == null || kernelB == null) {
      throw new IllegalArgumentException("Cannot kernel transformation on null model");
    }

    if (!validMatrix(kernelR) || !validMatrix(kernelG) || !validMatrix(kernelB)) {
      throw new IllegalArgumentException("Kernel transformation given invalid matrix");
    }

    List<List<List<Double>>> kernels = new ArrayList<>(
        Arrays.asList(kernelR, kernelG, kernelB));

    for (List<List<Double>> kernel : kernels) {
      if (kernel.size() % 2 == 0 || kernel.get(0).size() != kernel.size()) {
        // Ensure odd dimensions NxN
        throw new IllegalArgumentException("Given kernel must be of odd dimensions");
      }
    }

    List<List<Integer>> pixelsCopy = new ArrayList<>();

    int width = this.image.getWidth();
    int height = this.image.getHeight();
    int max = this.image.getMaxValue();

    List<List<Integer>> pixels = this.copyPixels();

    for (int i = 0; i < width * height; i++) {

      int r = this.clampedValue(this.filterHelper(kernelR, i, 'r', pixels));
      int g = this.clampedValue(this.filterHelper(kernelG, i, 'g', pixels));
      int b = this.clampedValue(this.filterHelper(kernelB, i, 'b', pixels));

      pixelsCopy.add(new ArrayList<Integer>(Arrays.asList(r, g, b)));
    }

    this.image = new ImageImpl(this.image.getWidth(), this.image.getHeight(),
        this.image.getMaxValue(), pixelsCopy);
  }

  /**
   * A helper method to apply the filter transformation.
   *
   * @param matrix     The matrix needed to apply the filter.
   * @param pixelIndex A single pixel index
   * @param channel    The channel of the pixel
   * @param pixels     An arraylist of an arraylist of pixels
   * @return An integer
   */
  private int filterHelper(List<List<Double>> matrix, int pixelIndex, char channel,
      List<List<Integer>> pixels) {

    int rgbChannel;

    switch (Character.toLowerCase(channel)) {
      case 'r':
        rgbChannel = 0;
        break;
      case 'g':
        rgbChannel = 1;
        break;
      case 'b':
        rgbChannel = 2;
        break;
      default:
        throw new IllegalArgumentException("Invalid channel given");
    }

    if (!validMatrix(matrix)) {
      throw new IllegalArgumentException("Invalid matrix");
    }

    if (matrix.size() % 2 == 0 || matrix.get(0).size() % 2 == 0) {
      throw new IllegalArgumentException("Given kernel must be of odd dimensions");
    }

    int width = this.image.getWidth();
    int height = this.image.getHeight();

    int matrixMidX = (matrix.get(0).size() / 2);
    int matrixMidY = (matrix.size() / 2);

    int pixelX = pixelIndex % width;
    int pixelY = pixelIndex / width;

    double newVal = 0;

    for (int yOffset = 0; yOffset <= matrix.size() / 2; yOffset++) { // Apply transformation
      for (int xOffset = 0; xOffset <= matrix.get(0).size() / 2; xOffset++) {

        ArrayList<Integer> offsetListX = new ArrayList<>(Arrays.asList(xOffset));
        ArrayList<Integer> offsetListY = new ArrayList<>(Arrays.asList(yOffset));

        if (xOffset != 0) {
          offsetListX.add(-xOffset);
        }
        if (yOffset != 0) {
          offsetListY.add(-yOffset);
        }

        for (Integer y : offsetListY) {
          for (Integer x : offsetListX) {
            if (validPixelCoord(pixelX + x, pixelY + y)) {
              newVal += (pixels.get((pixelX + x) + (pixelY + y) * width).get(rgbChannel) * matrix
                  .get(matrixMidY + x).get(matrixMidX + y));
            }
          }
        }
      }
    }

    return (int) newVal;
  }

  /**
   * A helper method to determine whether a matrix is valid or not.
   *
   * @param m The provided matrix
   * @return A boolean true or false statement to determine if a matrix is valid or not
   */
  protected boolean validMatrix(List<List<Double>> m) {
    if (m == null || m.isEmpty()) {
      throw new IllegalArgumentException("Given invalid matrix of null");
    }
    int width = m.get(0).size();
    for (List<Double> row : m) {
      if (row == null || row.size() != width) {
        return false;
      }
    }
    return true;
  }

  /**
   * A helper method to determine whether a pixel coordinate is valid or not.
   *
   * @param x The x coordinate of the pixel
   * @param y The y coordinate of the pixel
   * @return A boolean true or false statement to determine if a pixel coordinate is valid or not
   */
  protected boolean validPixelCoord(int x, int y) {
    return (x >= 0 && x < this.image.getWidth()) && (y >= 0 && y < this.image.getHeight());
  }

  /**
   * Helper method to deal with clamping.
   *
   * @param value The supposed value that is less than or beyond an image range.
   * @return The clamped integer value
   */
  protected int clampedValue(int value) {
    if (value < 0) {
      return 0;
    } else if (value > this.image.getMaxValue()) {
      return this.image.getMaxValue();
    } else {
      return value;
    }
  }

  /**
   * Observer for the width of the image in the model.
   *
   * @return the width of the image in this model
   */
  @Override
  public int getImageWidth() {
    return this.image.getWidth();
  }

  /**
   * Observer for the height of the image in the model.
   *
   * @return the height of the image in this model
   */
  @Override
  public int getImageHeight() {
    return this.image.getHeight();
  }

  /**
   * Observer for the maximum value of the image in the model.
   *
   * @return the maximum value of the image in this model
   */
  @Override
  public int getImageMax() {
    return this.image.getMaxValue();
  }

  /**
   * Returns a deep copy of the pixels of the image in the model. Pixels are represented as an
   * ArrayList of 3 integers.
   *
   * @return a deep copy of the pixels in the image in the model.
   */
  @Override
  public List<List<Integer>> copyPixels() {
    List<List<Integer>> copy = new ArrayList<>();

    int w = this.image.getWidth();
    int h = this.image.getHeight();

    for (int i = 0; i < w * h; i++) {
      copy.add(new ArrayList<>(this.image.getPixelAt(i % w, i / w)));
    }

    return new ArrayList<>(copy);

  }

  /**
   * Imports and creates an image given its parameters. The model requires an image to be
   * constructed with, but a decision was made to allow a new Image object to be imported to
   * replace the current image while working with another.
   *  @param width    width of the image
   * @param height   height of the image
   * @param maxValue maximum rgb value of an image
   * @param pixels   the pixels of the image
   */
  @Override
  public void importImage(int width, int height, int maxValue,
      List<List<Integer>> pixels) {

    try {
      IImage i = new ImageImpl(width, height, maxValue, pixels);
      this.image = i;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Cannot import an invalid image");
    }


  }

  /**
   * Exports a String of the current image's contents in PPM format (Width, Height, Max, then
   * Pixels. Excludes the P3 tag)
   *
   * @return IImage
   */
  @Override
  public String exportImage() {

    StringBuilder result = new StringBuilder();

    result.append(this.image.getWidth());
    result.append(" " + this.image.getHeight());
    result.append(" " + this.image.getMaxValue());

    List<List<Integer>> pixelsCopy = this.copyPixels();

    for (List<Integer> p : pixelsCopy) {
      for (Integer channel : p) {
        result.append(" " + channel);
      }
    }

    return result.toString();

  }

}



