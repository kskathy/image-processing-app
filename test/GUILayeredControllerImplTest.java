import static org.junit.Assert.assertEquals;

import controller.GUILayeredControllerImpl;
import controller.IControllerGUI;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for GUI Controller Impl.
 */
public class GUILayeredControllerImplTest {

  IControllerGUI c;
  StringBuilder viewLog;
  StringBuilder modelLog;
  ActionEvent a;

  @Before
  public void initData() {
    viewLog = new StringBuilder();
    modelLog = new StringBuilder();
  }

  @Test
  public void start() throws IOException {
    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.start();
    assertEquals("SetListener: " + c.toString() + "\n"
        + "Display\n", viewLog.toString());
  }

  @Test
  public void testLoad() {

    a = new ActionEvent("", 0, "load");
    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "ShowLoadDialog\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testSave() {

    a = new ActionEvent("", 0, "save");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "ShowSaveDialog\n"
        + "RenderMessage: Saved unsuccessfully. Please try again.\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testSaveAll() {
    a = new ActionEvent("", 0, "saveall");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "ShowSaveDialog\n"
        + "RenderMessage: Saved unsuccessfully. Please try again.\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testCreate() {
    a = new ActionEvent("", 0, "create");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "CreateLayer: 10\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "RenderMessage: Please remember to load and select a new image after creating a layer.\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testDelete() {
    a = new ActionEvent("", 0, "delete");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "RemoveLayer: 0\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testVisible() {

    a = new ActionEvent("", 0, "visible");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "SetVisibility: true\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testInvisible() {

    a = new ActionEvent("", 0, "invisible");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "SetVisibility: false\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testGrayScale() {

    a = new ActionEvent("", 0, "grayscale");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ApplyToCur: class model.operation.Grayscale\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }


  @Test
  public void testSharpen() {

    a = new ActionEvent("", 0, "sharpen");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ApplyToCur: class model.operation.Sharpen\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testSepia() {

    a = new ActionEvent("", 0, "sepia");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ApplyToCur: class model.operation.Sepia\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test
  public void testBlur() {

    a = new ActionEvent("", 0, "blur");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);

    assertEquals("SelectLayer: 0\n"
        + "ApplyToCur: class model.operation.Blur\n"
        + "ExportVisible\n", modelLog.toString());

    assertEquals("SetListener: " + c.toString() + "\n"
        + "GetSelectedLayer\n"
        + "UpdateLayerCount: 10\n"
        + "RenderMessage: No valid visible layers.\n"
        + "RenderImage\n", viewLog.toString());

  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommand() {
    a = new ActionEvent("", 0, "badCommand");

    c = new GUILayeredControllerImpl(new MockLayerModel(modelLog), new MockViewGUI(
        viewLog));
    c.actionPerformed(a);
  }

}