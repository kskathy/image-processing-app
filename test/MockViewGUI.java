import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import view.IViewGUI;

/**
 * Mock class for GUI view.
 */
public class MockViewGUI implements IViewGUI {

  StringBuilder log;

  public MockViewGUI(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void renderMessage(String msg) {
    log.append("RenderMessage: " + msg + "\n");
  }

  @Override
  public void display() {
    log.append("Display\n");
  }

  @Override
  public void setListener(ActionListener e) {
    log.append("SetListener: " + e.toString() + "\n");
  }

  @Override
  public int getSelectedLayer() {
    log.append("GetSelectedLayer\n");
    return 1;
  }

  @Override
  public String showSaveDialog() {
    log.append("ShowSaveDialog\n");
    return null;
  }

  @Override
  public String showLoadDialog() {
    log.append("ShowLoadDialog\n");
    return null;
  }

  @Override
  public void updateLayerCount(int i) {
    log.append("UpdateLayerCount: " + i + "\n");
  }

  @Override
  public void renderImage(BufferedImage img) {
    log.append("RenderImage\n");
  }
}
