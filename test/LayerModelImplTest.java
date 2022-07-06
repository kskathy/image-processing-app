import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import model.IImage;
import model.ILayerModel;
import model.ImageImpl;
import model.LayerModelImpl;
import model.operation.Blur;
import model.operation.Grayscale;
import model.operation.Sepia;
import model.operation.Sharpen;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class to test methods provided in the ILayerModel interface and implemented class. Tests
 * are made to ensure that layer functionalities work correctly.
 */
public class LayerModelImplTest {

  ILayerModel m;

  private ArrayList<Integer> pixel = new ArrayList<Integer>(Arrays.asList(10, 20, 30));

  private final IImage threeByThreeImage = new ImageImpl(3, 3, 255,

      Arrays.asList(pixel, pixel, pixel, pixel, pixel, pixel, pixel, pixel,
          Arrays.asList(80, 90, 100)));

  private IImage smallImage = new ImageImpl(1, 1, 255,
      Arrays.asList(new ArrayList<>(Arrays.asList(80, 90, 100))));
  private IImage blackPixel = new ImageImpl(1, 1, 255,
      Arrays.asList(new ArrayList<>(Arrays.asList(0, 0, 0))));

  private IImage twoByTwo = new ImageImpl(2, 2, 255,
      Arrays.asList(Arrays.asList(80, 90, 100), Arrays.asList(255, 255, 255),
          Arrays.asList(255, 255, 255), Arrays.asList(80, 90, 100)));

  @Before
  public void initData() {
    m = new LayerModelImpl();
  }

  // cannot select a layer that does not exist
  @Test(expected = IllegalArgumentException.class)
  public void selectLayerAtInvalidIndex() {
    m.selectLayer(3);
  }


  // cannot select a negative index layer that does not exist
  @Test(expected = IllegalArgumentException.class)
  public void selectLayerAtInvalidIndex2() {
    m.selectLayer(-1);
  }


  // cannot create a layer at invalid index since there's no first layer yet
  @Test(expected = IllegalArgumentException.class)
  public void testCreateAtInvalidIndex1() {
    m.createLayer(2);
  }

  // invalid index with given negative index
  @Test(expected = IllegalArgumentException.class)
  public void testCreateAtInvalidIndex2() {
    m.createLayer(-1);
  }

  // creates a layer given a valid index
  @Test
  public void testCreateAtLayer1() {
    m.createLayer(1);
    assertEquals(2, m.getNumLayers());
  }

  // creates a layer given a valid index
  @Test
  public void testCreateAtLayer2() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    assertEquals(5, m.getNumLayers());
  }


  // cannot remove layer at invalid index since there's no first layer created
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAtInvalidIndex1() {
    m.removeLayer(2);
  }

  // invalid index given negative index
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAtInvalidIndex2() {
    m.removeLayer(-3);
  }

  // cannot delete layer that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAtInvalidIndex3() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.removeLayer(5);
  }

  // layer 3 removed
  // 3 layers left (including topmost layer at index 0)
  @Test
  public void testRemoveAtValidIndex() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.removeLayer(3);
    assertEquals(3, m.getNumLayers());
  }

  // layer 4, 3, and 2 removed
  // 3 layers left (including last layer at index 0)
  @Test
  public void testRemoveAtValidIndex2() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    m.createLayer(5);
    m.removeLayer(4);
    m.removeLayer(3);
    m.removeLayer(2);
    assertEquals(3, m.getNumLayers());
  }

  // all layers removed
  // 1 layers left (including last layer at index 0)
  @Test
  public void testDRemoveAtValidIndex3() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    m.createLayer(5);
    m.removeLayer(5);
    m.removeLayer(4);
    m.removeLayer(3);
    m.removeLayer(2);
    m.removeLayer(1);
    assertEquals(1, m.getNumLayers());
  }

  // cannot delete the very last layer (layer at index 0)
  @Test(expected = IllegalArgumentException.class)
  public void testCannotRemoveAtLastLayer() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    m.createLayer(5);
    m.removeLayer(5);
    m.removeLayer(4);
    m.removeLayer(3);
    m.removeLayer(2);
    m.removeLayer(1);
    m.removeLayer(0);
  }

  // invalid move at index since there's no first layer created
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtInvalidIndex1() {
    m.moveLayer(2, 1);
  }

  // invalid move at index given negative index
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtInvalidIndex2() {
    m.moveLayer(-5, -1);
  }

  // invalid index given negative index
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtInvalidIndex3() {
    m.moveLayer(-5, 2);
  }

  // invalid index given negative index
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtInvalidIndex4() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    m.moveLayer(4, -1);
  }

  // cannot move to layer that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtInvalidIndex5() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    m.moveLayer(4, 5);
  }

  // cannot move a layer that doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtInvalidIndex6() {
    m.createLayer(1);
    m.createLayer(2);
    m.createLayer(3);
    m.createLayer(4);
    m.moveLayer(5, 4);
  }

  // correctly moves a layer
  @Test
  public void testMoveLayer() {
    m.loadLayer(smallImage);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(blackPixel);
    assertEquals("1 1 255 0 0 0", m.exportVisible());
    m.moveLayer(1, 0);
    assertEquals("1 1 255 80 90 100", m.exportVisible());
  }


  // LayerSepia disallows state where layer 0 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurSepiaDisallowsLayerNotInitialized() {
    m.applyToCur(new Sepia());
  }

  // LayerSepia disallows state where layer 1 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurSepiaDisallowsLayerNotInitialized2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sepia());
    assertEquals("2 2 255 119 106 82 255 255 238 255 255 238 119 106 82", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.applyToCur(new Sepia());
  }

  // correctly applies sepia to layer (tests applyToCur and exportVisible)
  @Test
  public void testApplyToCurSepia() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sepia());
    assertEquals("2 2 255 119 106 82 255 255 238 255 255 238 119 106 82", m.exportVisible());
  }

  // testing two layers
  @Test
  public void testApplyToCurSepia2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sepia());
    assertEquals("2 2 255 119 106 82 255 255 238 255 255 238 119 106 82", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sepia());
    assertEquals("2 2 255 119 106 82 255 255 238 255 255 238 119 106 82", m.exportVisible());
  }


  // layers must have the same dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testApplyToCurSepiaDisallowsDifferentDimensionLayers() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sepia());
    assertEquals("2 2 255 119 106 82 255 255 238 255 255 238 119 106 82", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Sepia());
  }


  // LayerGray disallows state where layer 0 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurGrayDisallowsLayerNotInitialized() {
    m.applyToCur(new Grayscale());
  }

  // LayerGray disallows state where layer 1 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurGrayDisallowsLayerNotInitialized2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.applyToCur(new Grayscale());
  }

  // correctly applies grayscale to layer (tests applyToCur and exportVisible)
  @Test
  public void testApplyToCurGray() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88", m.exportVisible());
  }

  // testing two layers
  @Test
  public void testApplyToCurGray2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88", m.exportVisible());
  }


  // layers must have the same dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testApplyToCurGrayDisallowsDifferentDimensionLayers() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Grayscale());
  }


  // LayerBlur disallows state where layer 0 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurBlurDisallowsLayerNotInitialized() {
    m.applyToCur(new Blur());
  }

  // LayerBlur disallows state where layer 1 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurBlurDisallowsLayerNotInitialized2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Blur());
    assertEquals("2 2 255 88 91 95 99 102 104 99 102 104 88 91 95", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.applyToCur(new Blur());
  }

  // correctly applies blur to layer (tests applyToCur and exportVisible)
  @Test
  public void testApplyToCurBlur() {
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Blur());

    assertEquals(
        "3 3 255 5 11 16 7 15 22 5 11 16 7 15 22 14 24 34 16 23 31 5 11 16 16 23 31 23 28 34",
        m.exportVisible());
  }

  // testing two layers
  @Test
  public void testApplyToCurBlur2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Blur());
    assertEquals("2 2 255 88 91 95 99 102 104 99 102 104 88 91 95", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
    m.applyToCur(new Blur());
    assertEquals("2 2 255 88 91 95 99 102 104 99 102 104 88 91 95", m.exportVisible());
  }


  // layers must have the same dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testApplyToCurBlurDisallowsDifferentDimensionLayers() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Blur());
    assertEquals("2 2 255 88 91 95 99 102 104 99 102 104 88 91 95", m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Blur());
  }


  // LayerSharpen disallows state where layer 0 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurSharpenDisallowsLayerNotInitialized() {
    m.applyToCur(new Sharpen());
  }

  // LayerSharpen disallows state where layer 1 has not been initialized
  @Test(expected = IllegalStateException.class)
  public void testApplyToCurSharpenDisallowsLayerNotInitialized2() {
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Sharpen());
    assertEquals(
        "3 3 255 2 13 25 10 28 47 2 13 25 10 28 47 47 77 107 36 55 73 2 13 25 36 55 73 81 92 103",
        m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.applyToCur(new Sharpen());
  }

  // correctly applies sharpen to layer (tests applyToCur and exportVisible)
  @Test
  public void testApplyToCurSharpen() {
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Sharpen());

    assertEquals(
        "3 3 255 2 13 25 10 28 47 2 13 25 10 28 47 47 77 107 36 55 73 2 13 25 36 55 73 81 92 103",
        m.exportVisible());
  }

  // testing two layers
  @Test
  public void testApplyToCurSharpen2() {
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Sharpen());
    assertEquals(
        "3 3 255 2 13 25 10 28 47 2 13 25 10 28 47 47 77 107 36 55 73 2 13 25 36 55 73 81 92 103",
        m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Sharpen());
    assertEquals(
        "3 3 255 2 13 25 10 28 47 2 13 25 10 28 47 47 77 107 36 55 73 2 13 25 36 55 73 81 92 103",
        m.exportVisible());
  }


  // layers must have the same dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testApplyToCurSharpenDisallowsDifferentDimensionLayers() {
    m.loadLayer(threeByThreeImage);
    m.applyToCur(new Sharpen());
    assertEquals(
        "3 3 255 2 13 25 10 28 47 2 13 25 10 28 47 47 77 107 36 55 73 2 13 25 36 55 73 81 92 103",
        m.exportVisible());

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sharpen());
  }


  // correctly gets the number of layers
  @Test
  public void getNumLayers() {
    assertEquals(1, m.getNumLayers());
    m.createLayer(0);
    m.createLayer(0);
    assertEquals(3, m.getNumLayers());
    m.removeLayer(0);
    assertEquals(2, m.getNumLayers());
  }

  // cannot load in layers of different dimensions
  @Test(expected = IllegalArgumentException.class)
  public void testLoadLayerDifferentSize() {
    m.loadLayer(smallImage);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
  }

  // cannot load in a null image
  @Test(expected = IllegalArgumentException.class)
  public void testLoadLayerNullImage() {
    m.loadLayer(null);
  }

  // correctly loads an image
  @Test
  public void testLoadLayer() {
    m.loadLayer(smallImage);
    m.setVisibility(true); // Would throw exception on an imageless layer
    // Does not throw exception despite different size because replacing the only image
    m.loadLayer(twoByTwo);
    assertEquals("2 2 255 80 90 100 255 255 255 255 255 255 80 90 100", m.exportLayer(0));
  }


  // cannot test if layer is visible at invalid layer
  @Test(expected = IllegalArgumentException.class)
  public void isVisibleDisallowsInvalidLayer() {
    m.isVisible(5);
  }

  // cannot test if layer is visible at invalid negative layer
  @Test(expected = IllegalArgumentException.class)
  public void isVisibleDisallowsNegativeLayer() {
    m.isVisible(-1);
  }

  // correctly determines if a layer is visible when true
  @Test
  public void testIsVisible1() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sharpen());
    m.setVisibility(true);
    assertEquals(true, m.isVisible(0));
  }

  // correctly determines if a layer is visible when false
  @Test
  public void testIsVisible2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sharpen());
    m.setVisibility(false);
    assertEquals(false, m.isVisible(0));
  }

  // correctly determines if a layer is visible given multiple layers
  @Test
  public void testIsVisible3() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sharpen());
    m.setVisibility(false);
    assertEquals(false, m.isVisible(0));

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    m.setVisibility(true);
    assertEquals(true, m.isVisible(1));
  }

  // cannot set visibility of an empty layer
  @Test(expected = IllegalArgumentException.class)
  public void testSetVisibilityEmptyLayer() {
    m.setVisibility(true);
  }

  // tests visibility of a layer
  @Test
  public void testSetVisibility1() {
    m.loadLayer(smallImage);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(blackPixel);
    assertEquals("1 1 255 0 0 0", m.exportVisible());
    m.setVisibility(false);
    assertEquals("1 1 255 80 90 100", m.exportVisible());
  }

  // tests visibility of a layer
  @Test
  public void testSetVisibility2() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sharpen());
    m.setVisibility(false);
    assertEquals("2 2 255 227 240 252 255 255 255 255 255 255 227 240 252",
        m.exportLayer(0));

    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(twoByTwo);
    m.applyToCur(new Grayscale());
    m.setVisibility(true);
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88",
        m.exportLayer(1));

    // exports the visible layer
    assertEquals("2 2 255 88 88 88 254 254 254 254 254 254 88 88 88",
        m.exportVisible());

  }

  // tests exporting a layer
  @Test
  public void testExportLayer() {
    m.loadLayer(twoByTwo);
    m.applyToCur(new Sharpen());
    m.setVisibility(false);
    assertEquals("2 2 255 227 240 252 255 255 255 255 255 255 227 240 252",
        m.exportLayer(0));
  }

  // tests exporting the visible layer
  @Test
  public void exportVisible() {
    m.loadLayer(smallImage);
    assertEquals("1 1 255 80 90 100", m.exportVisible());
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(blackPixel);
    assertEquals("1 1 255 0 0 0", m.exportVisible());
    m.setVisibility(false);
    assertEquals("1 1 255 80 90 100", m.exportVisible());
  }

}