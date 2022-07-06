import controller.GUILayeredControllerImpl;
import controller.IController;
import controller.TextLayeredControllerImpl;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import model.ILayerModel;
import model.LayerModelImpl;
import view.IViewGUI;
import view.ViewGUI;

/**
 * Main class for interactive use of the GUI.
 */
public class Main {

  /**
   * Main method to run the GUI MVC.
   *
   * @param args the arguments
   * @throws IOException if something goes wrong with the controller
   */
  public static void main(String[] args) throws IOException {
    ILayerModel m = new LayerModelImpl();
    IController c = null;
    if (args.length != 1) {
      System.out.println("Invalid number of command-line arguments");
    } else {
      if (args[0].equalsIgnoreCase("text")) {
        c = new TextLayeredControllerImpl(m, new InputStreamReader(System.in), System.out);
        c.start();
      } else if (args[0].equalsIgnoreCase("interactive")) {
        IViewGUI view = new ViewGUI();
        c = new GUILayeredControllerImpl(new LayerModelImpl(), view);
        c.start();
      }
      else {
        Scanner scan = new Scanner(args[0]);
        try {
          FileReader read = new FileReader(args[0]);
          c = new TextLayeredControllerImpl(m, read, System.out);
          c.start();
        } catch (IllegalArgumentException e) {
          System.out.println("Invalid command-line arguments: " + args[0]);
          return;
        }
      }
      return;
    }
  }

}

