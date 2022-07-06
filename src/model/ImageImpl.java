package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the IImage interface. This class provides a representation of an image. It
 * stores the key data of an image, being width, height, max value, and the pixels.
 */
public class ImageImpl implements IImage {
  // INVARIANT: The number of pixels is always equal to the size (width x height) of the image
  // INVARIANT: Every channel in each pixel is > 0 and <= the max value.

  private final int width;
  private final int height;
  private final int maxValue;
  private final ArrayList<ArrayList<Integer>> pixels;

  /**
   * Constructor that represents a valid Image.
   *  @param width    the width of an image
   * @param height   the height of an image
   * @param maxValue the maximum value of a color in an image
   * @param pixels   the pixels in an image
   */
  public ImageImpl(int width, int height, int maxValue, List<List<Integer>> pixels) {
    if (width < 0 || height < 0 || maxValue < 0) {
      throw new IllegalArgumentException("Invalid image parameter");
    }

    if (pixels == null) {
      throw new IllegalArgumentException("pixels is null");
    }

    this.width = width;
    this.height = height;
    this.maxValue = maxValue;

    if (pixels.size() != this.width * this.height) {
      throw new IllegalArgumentException("Number of pixels does not match given width and height");
    }

    ArrayList<ArrayList<Integer>> temp = new ArrayList<>();

    for (List<Integer> pixel : pixels) {
      if (pixel.size() == 3) { // Check for valid pixel size
        for (Integer i : pixel) { // Check for validity of values
          if (i < 0 || i > this.maxValue) {
            throw new IllegalArgumentException(
                "Invalid channel value: " + i + " for max of " + this.maxValue);

          }
        }
        temp.add(new ArrayList<Integer>(pixel));
      } else {
        throw new IllegalArgumentException("ImageImpl given invalid pixel");
      }
    }

    this.pixels = new ArrayList<>(temp);

  }

  /**
   * Return the pixel at the given coordinates in the format of a list with r, g, and b values in
   * that order.
   *
   * @param x the x coordinate
   * @param y the x coordinate
   * @return An array list that represents the pixel
   */
  @Override
  public List<Integer> getPixelAt(int x, int y) {
    return new ArrayList<Integer>(this.pixels.get((y * width) + x));
  }

  /**
   * Gets the width of an image.
   *
   * @return an integer representing the width
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of an image.
   *
   * @return an integer representing the height
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the maximum value of a color in an image.
   *
   * @return an integer representing the max value
   */
  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

}
