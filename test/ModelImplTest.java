import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.CreatorImpl;
import model.ICreator;
import model.IImage;
import model.IModel;
import model.ImageImpl;
import model.ModelImpl;
import org.junit.Test;

/**
 * This is the test class for processing images through filtering and color transformations.
 */
public class ModelImplTest {

  // One pixel image to be used to initialize models.
  private final IImage pixelImage = new ImageImpl(1, 1, 255, new ArrayList<>(Arrays
      .asList(new ArrayList<>(Arrays.asList(100, 110, 120)))));


  ArrayList<Integer> pixel = new ArrayList<Integer>(Arrays.asList(1, 1, 1));

  // 3x3 image with pixels (1, 1, 1)
  private final IImage threeByThreeImage = new ImageImpl(3, 3, 9,

          Arrays.asList(pixel, pixel, pixel, pixel, pixel, pixel, pixel, pixel, pixel));

  private final IImage threeByTwoImage = new ImageImpl(3, 2, 255,

          Arrays.asList(pixel, pixel, pixel, pixel, pixel, pixel));

  // the ModelImpl constructor disallows a null image parameter.
  @Test(expected = IllegalArgumentException.class)
  public void modelImplDisallowsNullImage() {
    new ModelImpl(null);
  }

  // applyColorTransformation() disallows a null matrix
  @Test(expected = IllegalArgumentException.class)
  public void colorTransformationDisallowsNullMatrix() {
    ModelImpl model = new ModelImpl(this.pixelImage);
    model.applyColorTransformation(null);
  }

  // applyColorTransformation() disallows an invalid matrix
  @Test(expected = IllegalArgumentException.class)
  public void colorTransformationDisallowsInvalidMatrix() {
    List<List<Double>> matrix = new ArrayList<>();
    ModelImpl model = new ModelImpl(this.pixelImage);
    model.applyColorTransformation(matrix);
  }

  // applyColorTransformation() disallows an invalid matrix size
  @Test(expected = IllegalArgumentException.class)
  public void colorTransformationDisallowsInvalidMatrixSize() {
    List<List<Double>> matrix = new ArrayList<>();

    // Creates a 4x3 matrix when only 3x3 can be used in colorTransformation
    ArrayList<Double> rgb = new ArrayList<>();
    rgb.add(0.5);
    rgb.add(0.5);
    rgb.add(0.5);
    ArrayList<Double> rgb2 = new ArrayList<>();
    rgb2.add(0.5);
    rgb2.add(0.5);
    rgb2.add(0.5);
    ArrayList<Double> rgb3 = new ArrayList<>();
    rgb3.add(0.5);
    rgb3.add(0.5);
    rgb3.add(0.5);
    ArrayList<Double> rgb4 = new ArrayList<>();
    rgb4.add(0.5);
    rgb4.add(0.5);
    rgb4.add(0.5);
    matrix.add(rgb);
    matrix.add(rgb2);
    matrix.add(rgb3);
    matrix.add(rgb4);
    ModelImpl model = new ModelImpl(this.pixelImage);
    model.applyColorTransformation(matrix);
  }

  // applyColorTransformation() works
  @Test
  public void colorTransformationWorksForValidMatrix() {
    List<List<Double>> matrix = new ArrayList<>();
    ArrayList<Double> rgb = new ArrayList<>();
    rgb.add(1.);
    rgb.add(1.);
    rgb.add(1.);
    ArrayList<Double> rgb2 = new ArrayList<>();
    rgb2.add(2.);
    rgb2.add(2.);
    rgb2.add(2.);
    ArrayList<Double> rgb3 = new ArrayList<>();
    rgb3.add(2.);
    rgb3.add(2.);
    rgb3.add(2.);
    matrix.add(rgb);
    matrix.add(rgb2);
    matrix.add(rgb3);
    ModelImpl model = new ModelImpl(threeByThreeImage);
    model.applyColorTransformation(matrix);

    // Ensure dimensions are not changed
    assertEquals(3, model.getImageWidth());
    assertEquals(3, model.getImageHeight());
    assertEquals(9, model.getImageMax());

    ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3, 6, 6));

    for (List<Integer> pixel : model.copyPixels()) {
      assertEquals(expected, pixel);
    }

    model = new ModelImpl(new ImageImpl(1, 1, 255,

            Arrays.asList(new ArrayList<>(Arrays.asList(10, 5, 15)))));

    model.applyColorTransformation(matrix);

    // Ensure dimensions are not changed
    assertEquals(1, model.getImageWidth());
    assertEquals(1, model.getImageHeight());
    assertEquals(255, model.getImageMax());

    expected = new ArrayList<>(Arrays.asList(30, 60, 60));
    assertEquals(expected, model.copyPixels().get(0));

  }


  // applyKernelTransformation() disallows a null kernels
  @Test(expected = IllegalArgumentException.class)
  public void kernelTransformationDisallowsNullMatrix() {
    List<List<Double>> kernel = new ArrayList<>();
    ArrayList<Double> rgb = new ArrayList<>();
    rgb.add(0.5);
    rgb.add(0.5);
    rgb.add(0.5);
    ArrayList<Double> rgb2 = new ArrayList<>();
    rgb2.add(0.5);
    rgb2.add(0.5);
    rgb2.add(0.5);
    ArrayList<Double> rgb3 = new ArrayList<>();
    rgb3.add(0.5);
    rgb3.add(0.5);
    rgb3.add(0.5);
    kernel.add(rgb);
    kernel.add(rgb2);
    kernel.add(rgb3);
    ModelImpl model = new ModelImpl(threeByThreeImage);
    model.applyKernelTransformation(null, kernel, null);
  }

  // applyKernelTransformation() disallows an invalid matrix
  @Test(expected = IllegalArgumentException.class)
  public void kernelTransformationDisallowsInvalidMatrix() {
    List<List<Double>> kernel = new ArrayList<>();
    ArrayList<Double> rgb = new ArrayList<>();
    rgb.add(0.5);
    rgb.add(0.5);
    rgb.add(0.5);
    ArrayList<Double> rgb2 = new ArrayList<>();
    rgb2.add(0.5);
    rgb2.add(0.5);
    rgb2.add(0.5);
    ArrayList<Double> rgb3 = new ArrayList<>();
    rgb3.add(0.5);
    rgb3.add(0.5);
    rgb3.add(0.5);
    kernel.add(rgb);
    kernel.add(rgb2);
    kernel.add(rgb3);
    List<List<Double>> matrix2 = new ArrayList<>();
    List<List<Double>> matrix3 = new ArrayList<>();
    ModelImpl model = new ModelImpl(pixelImage);
    model.applyKernelTransformation(kernel, matrix2, matrix3);
  }

  // applKernelTransformation() must have kernels that are of odd dimensions
  @Test(expected = IllegalArgumentException.class)
  public void kernelTransformationDisallowsInvalidMatrixSize() {
    List<List<Double>> kernel = new ArrayList<>();
    ArrayList<Double> rgb = new ArrayList<>();
    rgb.add(0.5);
    rgb.add(0.5);
    rgb.add(0.5);
    ArrayList<Double> rgb2 = new ArrayList<>();
    rgb2.add(0.5);
    rgb2.add(0.5);
    rgb2.add(0.5);
    ArrayList<Double> rgb3 = new ArrayList<>();
    rgb3.add(0.5);
    rgb3.add(0.5);
    rgb3.add(0.5);
    kernel.add(rgb);
    kernel.add(rgb2);
    kernel.add(rgb3);
    kernel.add(rgb3); // dimension is 4
    ModelImpl model = new ModelImpl(threeByThreeImage);
    model.applyKernelTransformation(kernel, kernel, kernel);
  }

  // applyColorTransformation() works
  @Test
  public void kernelTransformationWorksForValidMatrix() {

    // Red channel kernel to demonstrate proper filtering
    List<List<Double>> kernel = new ArrayList<>();
    ArrayList<Double> rgb = new ArrayList<>();
    rgb.add(1.);
    rgb.add(1.);
    rgb.add(1.);
    kernel.add(rgb);
    kernel.add(rgb);
    kernel.add(rgb);

    // Green channel kernel to demonstrate lower bound clamping
    List<List<Double>> kernel2 = new ArrayList<>();
    ArrayList<Double> rgbb = new ArrayList<>();
    rgbb.add(-1.);
    rgbb.add(-1.);
    rgbb.add(-1.);
    kernel2.add(rgbb);
    kernel2.add(rgbb);
    kernel2.add(rgbb);

    // Blue channel kernel to demonstrate upper bound clamping
    List<List<Double>> kernel3 = new ArrayList<>();
    ArrayList<Double> rgbbb = new ArrayList<>();
    rgbbb.add(3.);
    rgbbb.add(3.);
    rgbbb.add(3.);
    kernel3.add(rgbbb);
    kernel3.add(rgbbb);
    kernel3.add(rgbbb);

    ModelImpl model = new ModelImpl(threeByThreeImage);
    model.applyKernelTransformation(kernel, kernel2, kernel3);

    List<List<Integer>> pixels = model.copyPixels();

    ArrayList<Integer> corner = new ArrayList<>(Arrays.asList(4, 0, 9));
    ArrayList<Integer> edge = new ArrayList<>(Arrays.asList(6, 0, 9));
    ArrayList<Integer> center = new ArrayList<>(Arrays.asList(9, 0, 9));

    ArrayList<ArrayList<Integer>> expected = new ArrayList<>(
        Arrays.asList(corner, edge, corner, edge, center, edge, corner, edge, corner));

    assertEquals(expected, pixels);

  }


  // getImageWidth() works
  @Test
  public void testGetImageWidth() {
    ModelImpl model = new ModelImpl(threeByTwoImage);
    assertEquals(3, model.getImageWidth());

    ModelImpl model2 = new ModelImpl(threeByThreeImage);
    assertEquals(3, model2.getImageWidth());
  }

  // getImageHeight() works
  @Test
  public void testGetImageHeight() {
    ModelImpl model = new ModelImpl(threeByTwoImage);
    assertEquals(2, model.getImageHeight());

    ModelImpl model2 = new ModelImpl(threeByThreeImage);
    assertEquals(3, model2.getImageHeight());

  }

  // getImageMax() works
  @Test
  public void testGetImageMax() {
    ModelImpl model = new ModelImpl(threeByTwoImage);
    assertEquals(255, model.getImageMax());
    ModelImpl model2 = new ModelImpl(threeByThreeImage);
    assertEquals(9, model2.getImageMax());
  }

  // copyPixels() works and contains the given values of a pixel
  @Test
  public void testCopyPixels() {
    ModelImpl model = new ModelImpl(pixelImage);
    ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
    expected.add(new ArrayList<Integer>(Arrays.asList(100, 110, 120)));

    assertEquals(expected, model.copyPixels());

    model = new ModelImpl(threeByTwoImage);

    ArrayList<Integer> pixel = new ArrayList<>();
    pixel.add(1);
    pixel.add(1);
    pixel.add(1);

    for (List<Integer> p : model.copyPixels()) {
      assertEquals(pixel, p);
    }

  }

  @Test
  public void testImport() {

    ICreator creator = new CreatorImpl(3, 3, new ArrayList<>(Arrays.asList(Color.BLACK,
        Color.MAGENTA)));
    IModel model = new ModelImpl(creator.createImage());

    model.importImage(1, 1, 255,
        new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(100, 150, 255)))));

    assertEquals(1, model.getImageHeight());
    assertEquals(1, model.getImageWidth());
    assertEquals(255, model.getImageMax());

    assertEquals(
        new ArrayList<ArrayList<Integer>>(
            Arrays.asList(new ArrayList<Integer>(Arrays.asList(100, 150, 255)))),
        model.copyPixels());

  }

  @Test
  public void testExport() {

    ICreator creator = new CreatorImpl(2, 3, new ArrayList<>(Arrays.asList(Color.BLACK,
        Color.MAGENTA)));
    IModel model = new ModelImpl(creator.createImage());

    assertEquals(
        "3 2 255 0 0 0 255 0 255 0 0 0 255 0 255 0 0 0 255 0 255",
        model.exportImage());

    model = new ModelImpl(threeByThreeImage);
    assertEquals(
        "3 3 9 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
        model.exportImage());

  }


}
