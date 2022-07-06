In addition to the interfaces and classes made for part 1 of the
assignment, for part 2, we made new classes and interfaces to manage
the layering functionality. 

ILayerModel -----

First, in our model package, we made a ILayerModel interface along 
with the LayerModelImpl to handle methods needed to add layers to an image. 
In our ILayerModel interface, we have methods to select, create, remove, 
and move layers. In addition, we have methods including applying an 
image transformation, getting the number of layers of an image, 
loading a layer, setting the visibility of a layer, and exporting layers 
and the image. The LayerModelImpl class implements all these methods and 
includes one private helper method to determine the validity of a given layer.

ILayerOperation -----

In a new package called LayeredOperation within the controller package, 
we have classes that create, select, delete, and determine the 
visibility of a layer. These classes call on their respective methods 
in the LayerModelImpl class and implements the interface ILayerOperation 
that has one method to apply those respective layerModelImpl methods to 
the classes. 

IController -----

While still within the controller package, we implemented
a controller interface and class that handles the controller, and the
requests passed into the controller for layering an image. 

ConverterUtil -----

The ConverterUtil class, still within the controller class, handles 
importing and exporting an image to any file type, including PPM, 
JPEG, and PNG files. 

Design approach -----

With our design in adding the new layering
functionality, we approached this similarly to how we designed
our IModel and ModelImpl class, and our image transformation classes 
from part 1 of the assignment. The ILayerModel was designed similarly
to the IModel interface, and the ILayerOperation interface, and the
classes that implemented it was designed similarly to our IOperation
interface, and the classes that implemented that.