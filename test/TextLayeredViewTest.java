import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import model.IImage;
import model.ILayerModel;
import model.ImageImpl;
import model.LayerModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.TextLayeredView;

/**
 * Test class for TextLayeredView class. Tests are made to ensure that each model state is correctly
 * being outputted.
 */
public class TextLayeredViewTest {

  ILayerModel m;
  Appendable a = new StringBuilder();
  private ArrayList<Integer> pixel = new ArrayList<>(Arrays.asList(10, 20, 30));
  private final IImage threeByThreeImage = new ImageImpl(3, 3, 255,
      Arrays.asList(pixel, pixel, pixel, pixel, pixel, pixel, pixel, pixel,
          Arrays.asList(80, 90, 100)));

  private ArrayList<Integer> pixel2 = new ArrayList<>(Arrays.asList(13, 12, 11));

  private final IImage threeByThreeImage2 = new ImageImpl(3, 3, 255,
      Arrays.asList(pixel2, pixel, pixel, pixel, pixel, pixel, pixel, pixel,
          Arrays.asList(80, 90, 100)));

  @Before
  public void initData() {
    m = new LayerModelImpl();
  }

  // view cannot have null model
  @Test(expected = IllegalArgumentException.class)
  public void viewCannotHaveNullModel() {
    TextLayeredView view = new TextLayeredView(null, a);
  }

  // view cannot have null appendable
  @Test(expected = IllegalArgumentException.class)
  public void viewCannotHaveNullAppendable() {
    TextLayeredView view = new TextLayeredView(m, null);
  }

  // render correctly displays empty layer
  @Test
  public void testRenderEmpty() {
    TextLayeredView view = new TextLayeredView(m, a);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: Empty\n", a.toString());
  }

  // render correctly displays content of layer
  @Test
  public void testRenderOutput() {
    m.loadLayer(threeByThreeImage);
    TextLayeredView view = new TextLayeredView(m, a);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 10 20 30 \n", a.toString());
  }

  // render correctly creates a layer
  @Test
  public void testRenderCreate() {
    m.loadLayer(threeByThreeImage);
    TextLayeredView view = new TextLayeredView(m, a);
    m.createLayer(1);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 10 20 30 \n"
        + "Layer 2 [true]: Empty\n", a.toString());
  }


  // render appendable correctly displays contents of all layers
  @Test
  public void testRenderCreateAndOutput() {
    TextLayeredView view = new TextLayeredView(m, a);
    m.loadLayer(threeByThreeImage);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.createLayer(2);
    m.selectLayer(2);
    m.loadLayer(threeByThreeImage);
    m.createLayer(3);
    m.selectLayer(3);
    m.loadLayer(threeByThreeImage);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 10 20 30 \n"
        + "Layer 2 [true]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 10 20 30 \n"
        + "Layer 4 [true]: 3 3 255 10 20 30 \n", a.toString());
  }

  // render appendable correctly displays moving a layer
  @Test
  public void testRenderMove() {
    TextLayeredView view = new TextLayeredView(m, a);
    m.loadLayer(threeByThreeImage2);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.createLayer(2);
    m.selectLayer(2);
    m.loadLayer(threeByThreeImage2);
    m.createLayer(3);
    m.selectLayer(3);
    m.loadLayer(threeByThreeImage);
    view.render();
    m.moveLayer(3, 1);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 13 12 11 \n"
        + "Layer 2 [true]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 13 12 11 \n"
        + "Layer 4 [true]: 3 3 255 10 20 30 \n"
        + "--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 13 12 11 \n"
        + "Layer 2 [true]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 10 20 30 \n"
        + "Layer 4 [true]: 3 3 255 13 12 11 \n", a.toString());
  }

  // render appendable correctly displays removing a layer
  @Test
  public void testRenderRemove() {
    TextLayeredView view = new TextLayeredView(m, a);
    m.loadLayer(threeByThreeImage2);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.createLayer(2);
    m.selectLayer(2);
    m.loadLayer(threeByThreeImage2);
    m.createLayer(3);
    m.selectLayer(3);
    m.loadLayer(threeByThreeImage);
    view.render();
    m.removeLayer(3);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 13 12 11 \n"
        + "Layer 2 [true]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 13 12 11 \n"
        + "Layer 4 [true]: 3 3 255 10 20 30 \n"
        + "--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 13 12 11 \n"
        + "Layer 2 [true]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 13 12 11 \n", a.toString());
  }

  // render appendable correctly displays visibility of a layer
  @Test
  public void testRenderVisibility() {
    TextLayeredView view = new TextLayeredView(m, a);
    m.loadLayer(threeByThreeImage2);
    m.createLayer(1);
    m.selectLayer(1);
    m.loadLayer(threeByThreeImage);
    m.createLayer(2);
    m.selectLayer(2);
    m.loadLayer(threeByThreeImage2);
    m.createLayer(3);
    m.selectLayer(3);
    m.loadLayer(threeByThreeImage);
    view.render();
    m.selectLayer(1);
    m.setVisibility(false);
    view.render();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 13 12 11 \n"
        + "Layer 2 [true]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 13 12 11 \n"
        + "Layer 4 [true]: 3 3 255 10 20 30 \n"
        + "--- CURRENT LAYERS ---\n"
        + "Layer 1 [true]: 3 3 255 13 12 11 \n"
        + "Layer 2 [false]: 3 3 255 10 20 30 \n"
        + "Layer 3 [true]: 3 3 255 13 12 11 \n"
        + "Layer 4 [true]: 3 3 255 10 20 30 \n", a.toString());
  }


}
