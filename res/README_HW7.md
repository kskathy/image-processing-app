For the last part of the assignment, we effectively implemented the GUI
MVC with the GUILayeredControllerImpl connecting the ViewGUI and
our LayerModelImpl. Along with the interfaces and classes we 
already had from the past two assignments, we implemented the 
following new interfaces and classes to support the GUI.

IControllerGUI -----
In our controller package, we made an IControllerGUI interface 
that extends our IController interface from last week. The new
IControllerGUI interface handles all the action events from the GUI.

GUILayeredControllerImpl -----
Still in our controller package, we have the GUILayeredControllerImpl
that implements the IControllerGUI. This class essentially handles
all the user-generated events while interacting with the
ILayerModel and having it joined with the ViewGUI.

IViewGUI -----
In our view package, we implemented a new interface, IViewGUI
that contains methods needed to carry out the visual interface
components of the GUI. 

ViewGUI -----
Finally, still in our view package, the ViewGUI implements the 
IViewGUi and all its methods to effectively handle the design
and layout of the GUI application. It successfully implements
image transformation functionality, layering functionality,
saving and loading functionality, and loading and executing a script
from file. It also displays the image. All functionalities are
included in the menu.

IMosaicDownscaleModel -----
This interface represents how we went about implementing the 
mosaic and downscaling image transformations onto an image. 

Main -----
Our main class effectively runs the GUI application. Note that the
old scripts work with the current iteration. 

Design approach -----
With our design in adding the GUI application functionality,
we separated the components into the Model, View, and Controller. 
Our model was simply our ILayerModel from last week's assignment 
which provides the programming interface for the controller to use;
our view interacts with the model to visually represent everything;
and the controller joins the view and model and provides listeners
for the events. Together, all three components allow for the GUI. 

