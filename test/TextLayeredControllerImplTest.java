import static org.junit.Assert.assertEquals;

import controller.TextLayeredControllerImpl;
import java.io.IOException;
import java.io.StringReader;
import model.ILayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test the TextLayeredController class. Tests are made to ensure that the controller
 * and model are working correctly for the IlayerModel.
 */
public class TextLayeredControllerImplTest {

  StringBuilder log;
  ILayerModel m;
  StringReader r;
  TextLayeredControllerImpl c;

  @Before
  public void initTest() {
    log = new StringBuilder();
    m = new MockLayerModel(log);
  }

  @Test
  public void testCreateLayer() throws IOException {
    c = new TextLayeredControllerImpl(m, new StringReader("create 2\n create 3"), log);
    c.start();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Enter command: \n"
        + "CreateLayer: 1\n"
        + "--- CURRENT LAYERS ---\n"
        + "Enter command: \n"
        + "CreateLayer: 2\n", log.toString());
  }


  @Test
  public void testLoadLayer() throws IOException {
    c = new TextLayeredControllerImpl(m, new StringReader("load aoun.ppm"), log);
    c.start();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Enter command: \n"
        + "LoadLayer: 454 550 255\n", log.toString());
  }

  @Test
  public void testSelectLayer() throws IOException {
    c = new TextLayeredControllerImpl(m, new StringReader("current 1"), log);
    c.start();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Enter command: \n"
        + "SelectLayer: 0\n", log.toString());
  }

  @Test
  public void testSetVisibility() throws IOException {
    c = new TextLayeredControllerImpl(m, new StringReader("visible false"), log);
    c.start();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Enter command: \n"
        + "SetVisibility: false\n", log.toString());
  }

  @Test
  public void testSave() throws IOException {
    c = new TextLayeredControllerImpl(m, new StringReader("save name jpg"), log);
    c.start();
    assertEquals("--- CURRENT LAYERS ---\n"
        + "Enter command: \n"
        + "ExportVisible\n"
        + "Error: Conversion to other file format given null parameter.\n", log.toString());
  }


}