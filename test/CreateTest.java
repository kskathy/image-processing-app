import model.ILayerModel;
import model.LayerModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that creating a new layer works.
 */
public class CreateTest {

  ILayerModel m;

  @Before
  public void initData() {
    m = new LayerModelImpl();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateInvalidIndex1() {
    m.createLayer(2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateInvalidIndex2() {
    m.createLayer(-1);
  }

}