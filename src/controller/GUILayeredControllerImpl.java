package controller;

import static controller.ConverterUtil.toOtherFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.IImage;
import model.ILayerModel;
import model.LayerModelImpl;
import model.operation.Blur;
import model.operation.Grayscale;
import model.operation.Sepia;
import model.operation.Sharpen;
import view.IViewGUI;

/**
 * The GUI Layered Controller class that handles the GUI controller which is in charge of the event
 * handling.
 */
public class GUILayeredControllerImpl implements IControllerGUI, ActionListener {

  private ILayerModel model;
  private IViewGUI view;

  /**
   * The constructor.
   *
   * @param model the ILayerModel
   * @param view  the view for the MVC
   */
  public GUILayeredControllerImpl(ILayerModel model, IViewGUI view) {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    view.setListener(this);
    view.display();
  }

  /**
   * Starts up the controller.
   *
   * @throws IOException if something goes wrong with the controller
   */
  @Override
  public void start() throws IOException {
    view.display();
  }

  /**
   * Handles all the action events in the GUI.
   *
   * @param event the ActionEvent being handled
   */
  @Override
  public void actionPerformed(ActionEvent event) {

    model.selectLayer(view.getSelectedLayer() - 1);
    System.out.println(view.getSelectedLayer());

    String command = event.getActionCommand();
    IImage image;
    String fileName;

    switch (command.toLowerCase()) {
      case "save":
        fileName = view.showSaveDialog();
        if (fileName != null) {
          view.renderMessage("Saved successfully!");
        } else {
          view.renderMessage("Saved unsuccessfully:( Please try again.");
        }
        try {
          save(model.exportVisible(), fileName);
        } catch (IllegalArgumentException e) {
          view.renderMessage(e.getMessage());
        }
        break;
      case "saveall":
        fileName = view.showSaveDialog();
        if (fileName != null) {
          try {
            saveAll(fileName);
            view.renderMessage("Saved successfully!");
          } catch (IOException e) {
            view.renderMessage(e.getMessage());
          } catch (IllegalArgumentException e) {
            view.renderMessage(e.getMessage());
          }
        } else {
          view.renderMessage("Saved unsuccessfully. Please try again.");
        }
        break;
      case "load":
        String s = view.showLoadDialog();
        load(s);
        String newString = model.exportVisible();
        BufferedImage bufferedImage = toOtherFile(newString);
        view.renderImage(bufferedImage);
        view.renderMessage("Please select an operation to perform from the menu bar.");
        break;
      case "blur":
        model.applyToCur(new Blur());
        break;
      case "sharpen":
        model.applyToCur(new Sharpen());
        break;
      case "sepia":
        model.applyToCur(new Sepia());
        break;
      case "grayscale":
        model.applyToCur(new Grayscale());
        break;
      case "create":
        model.createLayer(model.getNumLayers());
        view.renderMessage(
            "Please remember to load and select a new image after creating a layer.");
        break;
      case "delete":
        if (model.getNumLayers() <= 1) {
          view.renderMessage("Error: Cannot delete the last layer.");
        } else {
          model.removeLayer(view.getSelectedLayer() - 1);
        }
        break;
      case "visible":
        model.setVisibility(true);
        break;
      case "invisible":
        model.setVisibility(false);
        break;
      default:
        throw new IllegalStateException("Unexpected command: " + command.toLowerCase());
    }

    view.updateLayerCount(model.getNumLayers());

    try {
      String string = model.exportVisible();
      BufferedImage newImg = toOtherFile(string);
      view.renderImage(newImg);
    } catch (IllegalArgumentException e) {
      view.renderMessage("Error: " + e.getMessage());
    }

  }

  /**
   * Helper to load a file.
   *
   * @param fileName the filename path
   */
  private void load(String fileName) {

    try {
      File f = new File(fileName);
      FileInputStream reader = new FileInputStream(f);
      Scanner scan = new Scanner(reader);

      ILayerModel newModel = new LayerModelImpl();

      String token = scan.next();

      if (token.equals("ML")) { // Tag for save text file

        this.loadCur(scan.next());
        this.model.setVisibility(scan.nextBoolean());

        int i = 2;

        while (scan.hasNext()) {
          this.model.createLayer(i - 1);
          this.model.selectLayer(i - 1);

          String name = scan.next();

          try {

            if (name.compareToIgnoreCase("empty") != 0) { // Leave layer empty if saved as empty
              this.loadCur(name);
              this.model.setVisibility(scan.nextBoolean());
              i++;
            }
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed loading layer " + i + ".\n");
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Layer " + i + " missing visibility tag.\n");
          }
        }

      } else {
        this.loadCur(fileName);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found.\n");
    }

  }

  /**
   * Helper to load the current layer from filename.
   *
   * @param fileName the filename path
   */
  private void loadCur(String fileName) {

    try {
      File f = new File(fileName);
      FileInputStream reader = new FileInputStream(f);
      Scanner scan = new Scanner(reader);

      IImage img;

      if (scan.next().equals("P3")) { // If PPM file
        model.loadLayer(ConverterUtil.convertFromPPM(f));
      } else {
        model.loadLayer(ConverterUtil.convertFromOther(f));
      }

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("No file found for name: " + fileName);
    }

  }

  /**
   * Helper to save the last layer image.
   *
   * @param input    the inputted string for the image content
   * @param fileName the filename wanted
   */
  private void save(String input, String fileName) {

    String format;
    int extension = fileName.lastIndexOf(".");

    if (extension == -1 || extension == fileName.length() - 1) {
      throw new IllegalArgumentException("Given file path has no extension.");
    } else {
      format = fileName.substring(extension + 1);
    }

    // Check for and remove leading boolean
    Scanner scan;

    if (format.equals("ppm")) {

      try { // Export to ppm
        FileWriter w = new FileWriter(new File(fileName + ".ppm"));
        w.write("P3" + System.lineSeparator());

        scan = new Scanner(input);

        while (scan.hasNext()) {
          w.write(scan.next() + System.lineSeparator());
        }
        w.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot write to file.\n");
      }
    } else { // Export to other
      File f = new File(fileName + "." + format);

      try {
        ImageIO.write(ConverterUtil.toOtherFile(input), format, f);
      } catch (IOException e) {
        throw new IllegalArgumentException(
            "Failed writing to image file of format " + format + ".\n");
      }
    }
  }

  /**
   * Helper to save all the layers of the image.
   *
   * @param fileName the filename wanted
   * @throws IOException if something goes wrong with the FileWriter
   */
  private void saveAll(String fileName) throws IOException {

    String format;
    int extension = fileName.lastIndexOf(".");

    if (extension == -1 || extension == fileName.length() - 1) {
      throw new IllegalArgumentException("Given file path has no extension.");
    } else {
      format = fileName.substring(extension + 1);
    }

    StringBuilder b = new StringBuilder();
    b.append("ML" + System.lineSeparator());
    Scanner scan;

    for (int i = 0; i < this.model.getNumLayers(); i++) {

      String image;

      try {
        image = this.model.exportLayer(i);

        scan = new Scanner(image);

        b.append(fileName + i + "." + format + System.lineSeparator());
        b.append(this.model.isVisible(i) + System.lineSeparator());

        this.save(image, fileName + i);

      } catch (IllegalStateException e) {
        b.append("EMPTY" + System.lineSeparator());
      }

    }

    scan = new Scanner(b.toString());
    File f = new File(fileName + ".txt");
    FileWriter w = new FileWriter(f);

    while (scan.hasNext()) {
      w.write(scan.nextLine() + System.lineSeparator());
    }
    w.close();
  }

}


