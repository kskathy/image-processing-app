import model.IImage;
import model.ILayerModel;
import model.operation.IOperation;

/**
 * A mock to test the TextLayeredController.
 */
class MockLayerModel implements ILayerModel {

  private final StringBuilder b;

  public MockLayerModel(StringBuilder b) {
    this.b = b;
  }

  @Override
  public void selectLayer(int layer) {
    b.append("SelectLayer: " + layer + "\n");
  }

  @Override
  public void createLayer(int layer) {
    b.append("CreateLayer: " + layer + "\n");
  }

  @Override
  public void removeLayer(int layer) {
    b.append("RemoveLayer: " + layer + "\n");
  }

  @Override
  public void moveLayer(int from, int to) {
    b.append("MoveLayer: " + from + " " + to + "\n");
  }

  @Override
  public void applyToCur(IOperation op) {
    b.append("ApplyToCur: " + op.getClass() + "\n");
  }

  @Override
  public int getNumLayers() {
    return 10;
  }

  @Override
  public void loadLayer(IImage img) {

    b.append("LoadLayer: " + img.getWidth() + " " + img.getHeight() + " " + img.getMaxValue()
        + "\n");
  }

  @Override
  public void setVisibility(boolean visible) {
    this.b.append("SetVisibility: " + visible + "\n");
  }

  @Override
  public boolean isVisible(int index) {
    b.append("IsVisible");
    return false;
  }

  @Override
  public String exportVisible() {
    b.append("ExportVisible\n");
    return null;
  }

  @Override
  public String exportLayer(int index) {
    b.append("ExportLayer\n");
    return null;
  }
}
