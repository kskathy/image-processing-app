package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the image creator implementation class. This class creates a checkerboard image
 * programmatically.
 */
public class CreatorImpl implements ICreator {

  /**
   * The height of the created image.
   */
  private int height;

  /**
   * The width of the created image.
   */
  private int width;

  /**
   * The colors of the created image.
   */
  private ArrayList<Color> colors;

  /**
   * Constructor for CreateImpl.
   *
   * @param height the height of the created image.
   * @param width  the width of the created image.
   * @param colors the colors of the created image.
   */
  public CreatorImpl(int height, int width, ArrayList<Color> colors) {

    if (colors == null) {
      throw new IllegalArgumentException("Null colors");
    }

    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Invalid parameters.");
    }
    if (colors.size() < 1) {
      colors.add(Color.WHITE);
    }
    this.height = height;
    this.width = width;
    this.colors = colors;
  }

  /**
   * Helper to get the first three lines (the ppm string, height and width, and the maximum rgb
   * value) of a ppm image.
   */
  private int getMax() {
    ArrayList<Integer> colorValues = new ArrayList<>();

    for (int i = 0; i < colors.size(); i++) {
      colorValues.add(colors.get(i).getRed());
      colorValues.add(colors.get(i).getGreen());
      colorValues.add(colors.get(i).getBlue());
    }
    return Collections.max(colorValues);
  }

  /**
   * returns an IImage of a checkerboard with the parameters stored by this object.
   *
   * @return
   */
  @Override
  public IImage createImage() {

    if (colors.size() < 2 || colors.size() > 2) {
      throw new IllegalArgumentException("checkerboards can only have two colors");
    }

    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    for (int rows = 0; rows < height; rows++) {
      for (int cols = 0; cols < width; cols++) {
        if ((rows + cols) % 2 == 0) {
          pixels.add(new ArrayList<Integer>(Arrays
              .asList(colors.get(0).getRed(), colors.get(0).getGreen(), colors.get(0).getBlue())));
        } else {
          pixels.add(new ArrayList<Integer>(Arrays
              .asList(colors.get(1).getRed(), colors.get(1).getGreen(), colors.get(1).getBlue())));
        }
      }
    }

    List<List<Integer>> newPixels = new ArrayList<>(pixels);

    return new ImageImpl(width, height, this.getMax(), newPixels);

  }
}
