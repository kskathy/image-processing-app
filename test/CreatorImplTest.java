import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.CreatorImpl;
import model.IImage;
import org.junit.Test;

/**
 * This test class tests the Model.CreatorImpl class. Tests are made to ensure that the constructor
 * for the class works, and that a checkerboard image is created correctly.
 */
public class CreatorImplTest {

  // The CreatorImpl constructor disallows negative height parameters.
  @Test(expected = IllegalArgumentException.class)
  public void creatorImplDisallowsNegativeHeight() {
    ArrayList<Color> colorList = new ArrayList<>();
    colorList.add(Color.BLUE);
    colorList.add(Color.CYAN);
    new CreatorImpl(-6, 5, colorList);
  }

  // The CreatorImpl constructor disallows negative width parameters.
  @Test(expected = IllegalArgumentException.class)
  public void creatorImplDisallowsNegativeWidth() {
    ArrayList<Color> colorList = new ArrayList<>();
    colorList.add(Color.BLUE);
    colorList.add(Color.CYAN);
    new CreatorImpl(5, -10, colorList);
  }

  // The CreatorImpl constructor disallows negative height and width parameters.
  @Test(expected = IllegalArgumentException.class)
  public void creatorImplDisallowsInvalidWidthAndHeight() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    colorList.add(Color.BLUE);
    colorList.add(Color.CYAN);
    new CreatorImpl(0, -10, colorList);
  }

  // The CreatorImpl constructor makes the default color white if no colors are present
  // in the arraylist.
  @Test
  public void creatorImplMakesDefaultColorWhiteIfNoColorsAreInList() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    new CreatorImpl(5, 10, colorList);
    ArrayList<Color> newColorList = new ArrayList<Color>();
    newColorList.add(Color.WHITE);
    assertEquals(colorList, newColorList);
  }

  // The createImage method disallows less than two colors for a checkerboard image.
  @Test(expected = IllegalArgumentException.class)
  public void checkerboardDisallowsLessThan2Colors() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    colorList.add(Color.BLUE);
    CreatorImpl create = new CreatorImpl(5, 10, colorList);
    File output = new File("/Users/Kathy/Downloads/Kathy_CS3500_HW5/Checker.ppm");
    create.createImage();
  }

  // The createImage method disallows more than two colors for a checkerboard image.
  @Test(expected = IllegalArgumentException.class)
  public void checkerboardDisallowsMoreThan2Colors() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    colorList.add(Color.BLUE);
    colorList.add(Color.CYAN);
    colorList.add(Color.RED);
    CreatorImpl create = new CreatorImpl(5, 10, colorList);
    File output = new File("/Users/Kathy/Downloads/Kathy_CS3500_HW5/Checker.ppm");
    create.createImage();
  }

  // The createImage method correctly creates a checkerboard image.
  @Test
  public void createCheckerboardTest() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    colorList.add(Color.BLUE);
    colorList.add(Color.RED);
    CreatorImpl create = new CreatorImpl(6, 5, colorList);
    IImage image = create.createImage();

    assertEquals(5, image.getWidth());
    assertEquals(6, image.getHeight());

    for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {

      List<Integer> curPixel = image.getPixelAt(i % image.getWidth(), i / image.getWidth());

      if (i % 2 == 0) {
        assertEquals(new ArrayList<Integer>(
                Arrays.asList(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue())),
            curPixel);
      } else {
        assertEquals(new ArrayList<Integer>(
                Arrays.asList(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue())),
            curPixel);
      }
    }

  }

  // The createImage method correctly creates a checkerboard image.
  @Test
  public void createCheckerboardTest2() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    colorList.add(Color.CYAN);
    colorList.add(Color.PINK);
    CreatorImpl create = new CreatorImpl(20, 20, colorList);

    IImage image = create.createImage();

    assertEquals(20, image.getWidth());
    assertEquals(20, image.getHeight());

    for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {

      List<Integer> curPixel = image.getPixelAt(i % image.getWidth(), i / image.getWidth());

      if (((i % 20) + i / 20) % 2 == 0) {
        assertEquals(new ArrayList<Integer>(
                Arrays.asList(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue())),
            curPixel);
      } else {
        assertEquals(new ArrayList<Integer>(
                Arrays.asList(Color.PINK.getRed(), Color.PINK.getGreen(), Color.PINK.getBlue())),
            curPixel);
      }
    }

  }

}
