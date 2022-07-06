import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.IImage;
import model.ImageImpl;
import org.junit.Test;

/**
 * This is the test class for the ImageImpl class. Tests are made to ensure that a representation of
 * an image is valid.
 */
public class ImageImplTest {

  private final ArrayList<Integer> defaultPixel = new ArrayList<>(Arrays.asList(0, 0, 0));
  private final List<List<Integer>> pixels = Arrays
      .asList(defaultPixel, defaultPixel, defaultPixel, defaultPixel, defaultPixel, defaultPixel);
  private final IImage threeByTwo = new ImageImpl(3, 2, 1, pixels);
  private List<List<Integer>> list1 = new ArrayList<>();

  // ImageImpl disallows a null pixel parameter
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplCannotHaveNullPixel() {
    new ImageImpl(5, 5, 200, null);
  }

  // ImageImpl disallows a negative width
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplCannotHaveNegativeWidth() {
    new ImageImpl(-5, 5, 200, list1);
  }

  // ImageImpl disallows a negative height
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplCannotHaveNegativeHeight() {
    new ImageImpl(10, -1, 200, list1);
  }

  // ImageImpl disallows a negative maximum rgb value
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplCannotHaveNegativeMaxValue() {
    new ImageImpl(10, -1, -150, list1);
  }

  // ImageImpl disallows a maximum rgb value greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplCannotHaveNegativeValueGreaterThan255() {
    new ImageImpl(10, -1, 256, list1);
  }

  // ImageImpl does not allow for the pixel size to not match the given width and height
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplCannotHaveUnmatchedPixelSize() {
    new ImageImpl(10, 1, 255, list1);
  }

  // ImageImpl disallows an invalid channel
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplDisallowsInvalidChannels() {
    List<List<Integer>> pixels = new ArrayList<>();
    ArrayList<Integer> rgb = new ArrayList<>();
    rgb.add(0);
    rgb.add(0);
    rgb.add(255);
    ArrayList<Integer> rgb2 = new ArrayList<>();
    rgb2.add(0);
    rgb2.add(0);
    rgb2.add(255);
    ArrayList<Integer> rgb3 = new ArrayList<>();
    rgb3.add(0);
    rgb3.add(0);
    rgb3.add(255);
    ArrayList<Integer> rgb4 = new ArrayList<>();
    rgb4.add(-1); // an invalid channel value
    rgb4.add(0);
    rgb4.add(255);
    pixels.add(rgb);
    pixels.add(rgb2);
    pixels.add(rgb3);
    pixels.add(rgb4);
    new ImageImpl(2, 2, 255, pixels);
  }

  // ImageImpl disallows an invalid pixel
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplDisallowsInvalidPixel() {
    List<List<Integer>> pixels = new ArrayList<>();
    ArrayList<Integer> rgb = new ArrayList<>();
    rgb.add(0);
    rgb.add(0);
    rgb.add(255);
    ArrayList<Integer> rgb2 = new ArrayList<>();
    rgb2.add(0);
    rgb2.add(0);
    rgb2.add(255);
    ArrayList<Integer> rgb3 = new ArrayList<>();
    rgb3.add(0);
    rgb3.add(0);
    rgb3.add(255);
    ArrayList<Integer> rgb4 = new ArrayList<>();
    rgb4.add(0);
    rgb4.add(255); // invalid pixel since there's only a red and green value
    pixels.add(rgb);
    pixels.add(rgb2);
    pixels.add(rgb3);
    pixels.add(rgb4);
    new ImageImpl(2, 2, 255, pixels);
  }

  // ImageImpl disallows invalid channel value
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplDisallowsInvalidChannelValue1() {
    // ppm file has a negative rgb value
    new ImageImpl(1, 1, 1,
        Arrays.asList(new ArrayList<Integer>(Arrays.asList(-1, 1, 1)))); // -1 Red channel
  }

  // ImageImpl disallows invalid channel value
  @Test(expected = IllegalArgumentException.class)
  public void ImageImplDisallowsInvalidChannelValue2() {
    // 2 Green & Blue channel greater than max value of 1
    new ImageImpl(1, 1, 1,
        Arrays.asList(new ArrayList<Integer>(Arrays.asList(0, 2, 2))));

  }


  // getPixelAt() returns the correct pixels at the given coordinates
  @Test
  public void getPixelAtReturnsCorrectPixels() {

    ArrayList<Integer> wrongPixel = new ArrayList<>(Arrays.asList(0, 0, 0));
    ArrayList<Integer> correctPixel = new ArrayList<>(Arrays.asList(1, 1, 1));

    List<List<Integer>> pixels = new ArrayList<>(
        Arrays.asList(wrongPixel, wrongPixel, correctPixel, wrongPixel));

    ImageImpl image = new ImageImpl(2, 2, 1, pixels);

    // Correct pixel should be at coords (0, 1)
    assertEquals(correctPixel, image.getPixelAt(0, 1));
  }


  // getWidth() returns the correct width of the image
  @Test
  public void getWidthReturnsTheCorrectWidth() {
    assertEquals(3, threeByTwo.getWidth());
  }

  // getHeight() returns the correct height of the image
  @Test
  public void getHeightReturnsTheCorrectHeight() {
    assertEquals(2, threeByTwo.getHeight());
  }

  // getMaxValue() returns the correct maximum rgb value of the image
  @Test
  public void getMaxValue() {
    assertEquals(1, threeByTwo.getMaxValue());
  }


}




