package model;

import java.util.List;

/**
 * An interface representation of an image.
 */
public interface IImage {

  /**
   * Return the pixel at the given coordinates in the format of a list with r, g, and b values in
   * that order.
   *
   * @param x the x coordinate
   * @param y the x coordinate
   * @return An array list that represents the pixel
   */
  public List<Integer> getPixelAt(int x, int y);

  /**
   * Gets the width of an image.
   *
   * @return an integer representing the width
   */
  public int getWidth();

  /**
   * Gets the height of an image.
   *
   * @return an integer representing the height
   */
  public int getHeight();

  /**
   * Gets the maximum value of a color in an image.
   *
   * @return an integer representing the max value
   */
  public int getMaxValue();

}
