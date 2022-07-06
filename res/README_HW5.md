Our code essentially has a model interface that encapsulates
the other classes and interfaces within it. 

IImage -----

The IImage interface contains methods an Image representation should be 
able to implement. It contains observer methods that are able to return the width, 
height, maximum rgb value, and pixels of a valid image. 

ImageImpl --

The ImageImpl class implements the IImage interface along with the methods. The 
constructor for this class determines the validity of the image given the height, 
width, maximum value, and pixels parameters of an image. This representation of an 
image stores the width, height, and max value as Integers and the pixels as an
ArrayList of ArrayList<Integer> (Where the internal arraylist is size 3 and represents the pixel). 

IModel -----
The IModel contains methods needed in order to successfully apply an image 
processing transformation, import or export methods for an image, and observer 
methods needed to get information of an image in the model.
Specifically, the applyColorTransformation() method contains
generalized code needed to transform an image with a matrix (for gray and sepia)
 and the applyKernelTransformation() method contains
generalized code needed to apply a kernel transformation (for blur and sharpen).

ModelImpl ---

The ModelImpl implements the methods of the interface, along
with protected and private helper methods. The constructor for 
this class takes in a valid IImage that the methods are being 
called on. 

IOperation --

The IOperation interface contains an apply method
that applies an image processing transformation to an IModel. The 
classes, Blur, Grayscale, Sepia, and Sharpen all implement the 
IOperation interface and calls on their respective transformation
method from the IModel interface to carry out their image processing
application. Having an IOperation interface with
only the apply method establishes a relationship
with all the classes that would implement it, which we believe to improve extensibility. 

ICreator --

Then, we have the ICreator interface which is responsible for 
creating images programmatically. It contains the method createImage() to create the desired image. 

CreatorImpl --- 

CreatorImpl implements ICreator. Its createImage() method returns an IImage/ImageImpl of a checkerboard given 
the desired height, width, and colors for the desired checkerboard. 

Finally, we include a Main class to take in basic user input from the conosole,
process an image, and and export it. Users are able to process an image with an operation however many times, and
then finally choose to export the image at the end. 


Citations -----

Aoun Image is an original creation.

Duck Image Citation: 
Adam, How To Draw A Duck, Draw Central, 10 June 2019, 
https://drawcentral.com/2012/06/how-to-draw-duck.html.



