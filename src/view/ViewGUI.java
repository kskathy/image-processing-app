package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class handles the view and design layout for the GUI MVC.
 */
public class ViewGUI extends JFrame implements IViewGUI {

  private int layers = 1;
  private int selectedLayer = 1;

  private JMenuBar menuBar = new JMenuBar();

  private JMenu fileOp = new JMenu("File");
  private JMenuItem save = new JMenuItem("Save");
  private JMenuItem saveAll = new JMenuItem("Save All");
  private JMenuItem load = new JMenuItem("Load");

  private JMenu layerSelect = new JMenu("Select");
  private JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

  private JMenu layerOp = new JMenu("Layers");

  private JMenu visibleSubMenu = new JMenu("Visible");

  private JMenuItem visibleTrue = new JMenuItem("True");
  private JMenuItem visibleFalse = new JMenuItem("False");

  private JMenuItem create = new JMenuItem("Create");
  private JMenuItem delete = new JMenuItem("Delete");

  private JMenu transform = new JMenu("Transformations");

  private JMenuItem blur = new JMenuItem("Blur");
  private JMenuItem grayscale = new JMenuItem("Grayscale");
  private JMenuItem sharpen = new JMenuItem("Sharpen");
  private JMenuItem sepia = new JMenuItem("Sepia");

  private JLabel label = new JLabel();
  private JLabel imageLabel = new JLabel();
  private JFileChooser fc = new JFileChooser();
  private JPanel content = new JPanel();
  private JPanel imagePanel = new JPanel();
  private JScrollPane viewPort = new JScrollPane(imagePanel);

  /**
   * Constructor for the view.
   */
  public ViewGUI() {

    content.setLayout(new FlowLayout());

    imagePanel.add(imageLabel);
    viewPort.setPreferredSize(new Dimension(600, 600));
    content.add(viewPort);
    content.setPreferredSize(new Dimension(800, 800));

    imageLabel.setText("Insert Image.");

    save.setActionCommand("Save");
    saveAll.setActionCommand("SaveAll");
    load.setActionCommand("Load");

    blur.setActionCommand("Blur");
    grayscale.setActionCommand("Grayscale");
    sepia.setActionCommand("Sepia");
    sharpen.setActionCommand("Sharpen");

    fileOp.add(save);
    fileOp.add(saveAll);
    fileOp.add(load);

    transform.add(blur);
    transform.add(grayscale);
    transform.add(sharpen);
    transform.add(sepia);

    menuBar.add(fileOp);

    spinner.addChangeListener(new SpinnerListener());
    layerSelect.add(spinner);

    menuBar.add(layerSelect);

    create.setActionCommand("create");
    delete.setActionCommand("delete");

    visibleTrue.setActionCommand("visible");
    visibleFalse.setActionCommand("invisible");

    visibleSubMenu.add(visibleTrue);
    visibleSubMenu.add(visibleFalse);

    layerOp.add(create);
    layerOp.add(delete);
    layerOp.add(visibleSubMenu);

    menuBar.add(layerOp);
    menuBar.add(transform);

    this.setJMenuBar(menuBar);

    fc.setFileFilter(
        new FileNameExtensionFilter("PPM3, JPG, PNG, Custom TXT format", "txt", "jpg", "jpeg",
            "ppm", "png"));

    label = new JLabel("Please load in an image from the file bar.");
    label.setFont(new Font("Verdana", Font.PLAIN, 20));
    content.add(label);

    this.setContentPane(content);
    this.pack();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Detects when the spinner changes and updates the current layer.
   */
  class SpinnerListener implements ChangeListener {

    /**
     * Detects when the state has changed.
     *
     * @param e the changed event
     */
    @Override
    public void stateChanged(ChangeEvent e) {
      String value = spinner.getValue().toString();

      try {
        int layer = Integer.parseInt(value);

        if (layer > layers) {
          selectedLayer = layers;
          spinner.setValue(layers);
        } else {
          selectedLayer = layer;
        }

        System.out.println(selectedLayer);
      } catch (NumberFormatException ex) {
        return;
      }

    }
  }

  /**
   * Renders a message to a view label.
   *
   * @param msg the message being rendered
   */
  public void renderMessage(String msg) {
    label.setFont(new Font("Verdana", Font.PLAIN, 15));
    label.setText(msg);
  }

  /**
   * Displays the GUI frame.
   */
  @Override
  public void display() {
    this.setVisible(true);
  }


  /**
   * Handles all the action events.
   *
   * @param e the action event
   */
  public void setListener(ActionListener e) {
    save.addActionListener(e);
    saveAll.addActionListener(e);
    load.addActionListener(e);

    visibleTrue.addActionListener(e);
    visibleFalse.addActionListener(e);
    create.addActionListener(e);
    delete.addActionListener(e);

    sharpen.addActionListener(e);
    grayscale.addActionListener(e);
    sepia.addActionListener(e);
    blur.addActionListener(e);
  }

  /**
   * Gets the selected layer of the model.
   *
   * @return the index of the selected layer
   */
  @Override
  public int getSelectedLayer() {
    return this.selectedLayer;
  }


  /**
   * Outputs the filepath string of where the user wants the image to be saved.
   *
   * @return the filepath string
   */
  @Override
  public String showSaveDialog() {
    JPanel popUp = new JPanel();
    int val = fc.showSaveDialog(popUp);
    if (val == JFileChooser.APPROVE_OPTION) {
      System.out.println(fc.getSelectedFile());
      return fc.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
  }

  /**
   * Outputs the filepath string of the image the user wants to load in.
   *
   * @return the filepath string
   */
  @Override
  public String showLoadDialog() {
    int val = fc.showOpenDialog(new JPanel());
    if (val == JFileChooser.APPROVE_OPTION) {
      return fc.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
  }

  /**
   * Updates the count of the number of layers.
   *
   * @param i the layer index
   */
  @Override
  public void updateLayerCount(int i) {
    this.layers = i;

    String spin = spinner.getValue().toString();

    try {
      int val = Integer.parseInt(spin);
      if (val > i) {
        spinner.setValue(layers);
      }

    } catch (NumberFormatException e) {
      return;
    }
  }

  /**
   * Renders an image to the image label.
   *
   * @param img the BufferedImage to be rendered
   */
  @Override
  public void renderImage(BufferedImage img) {
    imageLabel.setIcon(new ImageIcon(img));
    imageLabel.setText(null);
  }
}

