package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.IImage;
import model.ILayerModel;
import model.LayerModelImpl;
import model.operation.Blur;
import model.operation.Grayscale;
import model.operation.Sepia;
import model.operation.Sharpen;
import view.IView;
import view.TextLayeredView;

/**
 * The text controller that works with the layered model to provide
 * transformations and layering functionality.
 */
public class TextLayeredControllerImpl implements IController {

  private final Readable r;
  private final Appendable a;
  private ILayerModel m;

  /**
   * The constructor.
   *
   * @param m The ILayerModel
   * @param r The readable in
   * @param a The appendable out
   */
  public TextLayeredControllerImpl(ILayerModel m, Readable r, Appendable a) {

    if (m == null || r == null || a == null) {
      throw new IllegalArgumentException("LayeredControllerImpl given null parameter");
    }
    this.m = m;
    this.r = r;
    this.a = a;
  }

  /**
   * Ignores extra tokens after commands.
   *
   * @throws IOException if something goes wrong in the controller
   */
  @Override
  public void start() throws IOException {

    IView view = new TextLayeredView(this.m, this.a);

    Scanner scan = new Scanner(r);

    while (scan.hasNext()) {

      view.render();
      view.renderMessage("Enter command: ");
      String command = scan.nextLine();
      Scanner scanLine = new Scanner(new StringReader(command));

      // Quits if any of the words in the next command is "QUIT"
      if (command.contains("QUIT")) {
        view.renderMessage("Quit.");
        return;
      }

      // Ignores extra tokens in the line after a command is completed.
      while (scanLine.hasNext()) {

        ILayerOperation op = null;

        switch (scanLine.next().toLowerCase()) { // Script is case insensitive
          case "create":
            try {
              int i = scanLine.nextInt();
              op = new Create(i);
            } catch (NoSuchElementException e) {
              view.renderMessage("Error: Create command given invalid input.");
            }
            break;
          case "delete":
            try {
              int i = scanLine.nextInt();
              op = new Delete(i);
            } catch (NoSuchElementException e) {
              view.renderMessage("Error: Delete command given invalid inputs.");
            }
            break;
          case "move":
            try {
              int from = scanLine.nextInt();
              int to = scanLine.nextInt();
              this.m.moveLayer(from - 1, to - 1);
            } catch (NoSuchElementException e) {
              view.renderMessage("Move command not given proper inputs.");
            } catch (IllegalArgumentException e) {
              view.renderMessage("Error: " + e.getMessage());
            }
            break;
          case "current":
            try {
              int i = scanLine.nextInt();
              op = new Current(i);
            } catch (NoSuchElementException e) {
              view.renderMessage("Error: " + e.getMessage());
            }
            break;
          case "visible":
            try {
              boolean visible = scanLine.nextBoolean();
              op = new Visible(visible);
            } catch (NoSuchElementException e) {
              view.renderMessage("Error: Visible command given invalid input.\n");
            }
            break;
          case "blur":
            try {
              m.applyToCur(new Blur());
            } catch (IllegalStateException e) {
              view.renderMessage("Error: " + e.getMessage());
            }
            break;
          case "sepia":
            try {
              m.applyToCur(new Sepia());
            } catch (IllegalStateException e) {
              view.renderMessage("Error: " + e.getMessage());
            }
            break;
          case "grayscale":
            try {
              m.applyToCur(new Grayscale());
            } catch (IllegalStateException e) {
              view.renderMessage("Error: " + e.getMessage());
            }
            break;
          case "sharpen":
            try {
              m.applyToCur(new Sharpen());
            } catch (IllegalStateException e) {
              view.renderMessage("Error: " + e.getMessage());
            }
            break;
          case "save":
            if (scanLine.hasNext()) {
              String name = scanLine.next();
              if (scanLine.hasNext()) {
                String format = scanLine.next();
                try {
                  this.save(m.exportVisible(), name, format);
                } catch (IllegalArgumentException e) {
                  view.renderMessage("Error: " + e.getMessage());
                }

              } else {
                view.renderMessage("Missing save format.\n");
              }

            } else {
              view.renderMessage("Missing filename.\n");
            }
            break;
          case "saveall":
            try {
              String filename = scanLine.next();
              String format = scanLine.next();
              this.saveAll(filename, format);
            } catch (NoSuchElementException e) {
              view.renderMessage("SaveAll command ran out of tokens to parse.\n");
            }
            break;
          case "load":
            if (scanLine.hasNext()) {
              String name = scanLine.next();
              try {
                this.load(name);
              } catch (IllegalArgumentException e) {
                view.renderMessage("Error: " + e.getMessage() + "\n");
              }
            } else {
              view.renderMessage("Load command missing file name.\n");
            }
            break;
          default:
            view.renderMessage("Invalid command. Please re-enter line.");
            break;
        }

        if (op != null) {
          try {
            op.apply(this.m);
          } catch (IllegalArgumentException e) {
            view.renderMessage("Error: " + e.getMessage());
          }
        }

      }
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
        this.m.setVisibility(scan.nextBoolean());

        int i = 2;

        while (scan.hasNext()) {
          this.m.createLayer(i - 1);
          this.m.selectLayer(i - 1);

          String name = scan.next();

          try {

            if (name.compareToIgnoreCase("empty") != 0) { // Leave layer empty if saved as empty
              this.loadCur(name);
              this.m.setVisibility(scan.nextBoolean());
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
        m.loadLayer(ConverterUtil.convertFromPPM(f));
      } else {
        m.loadLayer(ConverterUtil.convertFromOther(f));
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
   * @param format   the format of the file
   */
  private void save(String input, String fileName, String format) {

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
   * @param format   the format of the file
   * @throws IOException if something goes wrong with the FileWriter
   */
  private void saveAll(String fileName, String format) throws IOException {

    StringBuilder b = new StringBuilder();
    b.append("ML" + System.lineSeparator());
    Scanner scan;

    for (int i = 0; i < this.m.getNumLayers(); i++) {

      String image;

      try {
        image = this.m.exportLayer(i);

        scan = new Scanner(image);

        b.append(fileName + i + "." + format + System.lineSeparator());
        b.append(this.m.isVisible(i) + System.lineSeparator());

        this.save(image, fileName + i, format);

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
