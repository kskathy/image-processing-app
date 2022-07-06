package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.IImage;
import model.ImageImpl;

/**
 * Utility class to facilitate conversion between image formats and IImage implementations.
 */
public class ConverterUtil {

  /**
   * Converts a string with data including image width, height, max, and pixels to a ppm formatted
   * string.
   *
   * @param data the string with data
   * @return the ppm formatted string
   */
  public static String toPPM(String data) {

    if (data == null) {
      throw new IllegalArgumentException("Conversion to PPM format given null input.");
    }

    return "P3" + System.lineSeparator() + data;
  }

  /**
   * Takes in a file string path and converts it into a BufferedImage.
   *
   * @param s The filepath
   * @return The BufferedImage
   */
  public static BufferedImage fromFileToBuffered(String s) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(String.valueOf(s)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return img;
  }

  /**
   * Converts a string with data including image width, height, max, and pixels to a BufferedImage.
   *
   * @param data the string with data
   * @return the BufferedImage
   */
  public static BufferedImage toOtherFile(String data) {
    if (data == null) {
      throw new IllegalArgumentException("Conversion to other file format given null parameter.");
    }

    Scanner scan = new Scanner(data);
    int width = scan.nextInt();
    int height = scan.nextInt();
    int max = scan.nextInt();

    BufferedImage img = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = scan.nextInt();
        int g = scan.nextInt();
        int b = scan.nextInt();

        img.setRGB(x, y, (r << 16) | (g << 8) | b);
      }
    }

    return new BufferedImage(img.getColorModel(), img.copyData(null),
        img.isAlphaPremultiplied(),
        null);

  }

  /**
   * Converts a file supported by ImageIO to an IImage.
   *
   * @param f the file name
   * @return an IImage
   */
  public static IImage convertFromOther(File f) {

    if (f == null) {
      throw new IllegalArgumentException("Conversion from other file format given null file.");
    }

    try {
      BufferedImage img = ImageIO.read(f);

      List<List<Integer>> pixels = new ArrayList<>();

      for (int y = 0; y < img.getHeight(); y++) {
        for (int x = 0; x < img.getWidth(); x++) {
          int pixel = img.getRGB(x, y);

          pixels.add(new ArrayList<Integer>(
              Arrays.asList((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, pixel & 0xff)));

        }
      }

      // Max value of 255 by default for jpg, png
      return new ImageImpl(img.getWidth(), img.getHeight(), 255, pixels);

    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read file in conversion");
    }
  }

  /**
   * Converts a ppm file to an IImage.
   *
   * @param f the file name
   * @return an IImage
   */
  public static IImage convertFromPPM(File f) {

    if (f == null) {
      throw new IllegalArgumentException("Conversion from PPM format given null file.");
    }

    int width;
    int height;
    int max;

    List<List<Integer>> pixels;

    Scanner scan;

    try {
      scan = new Scanner(new FileInputStream(f));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + f.getName() + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (scan.hasNextLine()) {
      String s = scan.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    scan = new Scanner(builder.toString());

    if (!scan.next().equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: Should begin with P3");
    }

    try {
      width = scan.nextInt();
      height = scan.nextInt();
      max = scan.nextInt();
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Invalid PPM file: File missing image details");
    }

    ArrayList<ArrayList<Integer>> rgb = new ArrayList<>();
    while (scan.hasNext()) {

      try {
        int r = scan.nextInt();
        int g = scan.nextInt();
        int b = scan.nextInt();

        ArrayList<Integer> pixel = new ArrayList<>(Arrays.asList(r, g, b));

        for (Integer i : pixel) { // Check for validity of values
          if (i < 0 || i > max) {
            throw new IllegalArgumentException("Invalid channel value");
          }
        }

        rgb.add(new ArrayList<Integer>(pixel));
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid PPM file: Invalid rgb values");
      }

    }

    if (rgb.size() == width * height) { // Check that pixels matches given width & height
      pixels = new ArrayList<>(rgb);
    } else {
      throw new IllegalArgumentException(
          "Number of pixels does not match given width and height");
    }

    return new ImageImpl(width, height, max, pixels);

  }

}
