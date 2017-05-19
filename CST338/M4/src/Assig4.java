/*
***********************************
* Alejandro Guzman-Vega
* Andrea Wieland
* Huda Mutwakil
* Brandon Lewis
* CST 338
* Optical Barcode Readers and Writers
* Professor Cecil
***********************************
*/
public class Assig4
{

   public static void main(String[] args)
   {

   }

}

/**
 * Any class that implements BarcodeIO is expected to store some version of an
 * image and some version of the text associated with that image.
 */
interface BarcodeIO
{
   /**
    * Accepts some image, represented as a BarcodeImage object, and stores a
    * copy of this image. Depending on the sophistication of the implementing
    * class, the internally stored image might be an exact clone of the
    * parameter, or a refined, cleaned and processed image. No translation is
    * done here - i.e., any text string that might be part of an implementing
    * class is not touched, updated or defined during the scan.
    * 
    * @param bc a BarcodeImage object that will be scanned.
    * @return boolean depending if scan was successfull
    */
   public boolean scan(BarcodeImage bc);

   /**
    * Accepts a text string to be eventually encoded in an image. No translation
    * is done here - i.e., any BarcodeImage that might be part of an
    * implementing class is not touched, updated or defined during the reading
    * of the text.
    * 
    * @param text Text to be turned into an image
    * @return true if read was succesfull and false otherwise
    */
   public boolean readText(String text);

   /**
    * Looks at the internal text stored in the implementing class and produces a
    * companion BarcodeImage, internally (or an image in whatever format the
    * implementing class uses). After this is called, we expect the implementing
    * object to contain a fully-defined image and text that are in agreement
    * with each other.
    * 
    * @return true if image generation was succesfull and false otherwise
    */
   public boolean generateImageFromText();

   /**
    * Looks at the internal image stored in the implementing class, and produces
    * a companion text string, internally. After this is called, we expect the
    * implementing object to contain a fully defined image and text that are in
    * agreement with each other.
    * 
    * @return true if text was generated from image and false otherwise
    */
   public boolean translateImageToText();

   /**
    * Prints out the text string to the console.
    */
   public void displayTextToConsole();

   /**
    * Prints out the image to the console. In our implementation, we will do
    * this in the form of a dot-matrix of blanks and asterisks, e.g.,
    */
   public void displayImageToConsole();
}

/**
 * This class will realize all the essential data and methods associated with a
 * 2D pattern, thought of conceptually as an image of a square or rectangular
 * bar code. This class has very little "smarts" in it, except for the
 * parameterized constructor. It mostly just stores and retrieves 2D data.
 *
 */
class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;

   private boolean[][] image_data;

   /**
    * Instantiates a 2D array (MAX_HEIGHT x MAX_WIDTH) and stuffs it all with
    * blanks (false).
    */
   public BarcodeImage()
   {
      this.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
   }

   /**
    * Takes a 1D array of Strings and converts it to the internal 2D array of
    * booleans. This constructor is a little tricky because the incoming image
    * is not the necessarily same size as the internal matrix. So, you have to
    * pack it into the lower-left corner of the double array, causing a bit of
    * stress if you don't like 2D counting. This is good 2D array exercise. The
    * DataMatrix class will make sure that there is no extra space below or left
    * of the image so this constructor can put it into the lower-left corner of
    * the array.
    * 
    * @param strData
    */
   public BarcodeImage(String[] strData)
   {
      // TODO: Complete constructor
   }

   /**
    * Get the value of a specific pixel.
    * 
    * @param row The row of the pixel
    * @param col The column of the pixel
    * @return Use the return value for both the actual data and also the error
    *         condition. If there is an error; we just return false.
    */
   boolean getPixel(int row, int col)
   {
      return image_data[row][col];
   }

   /**
    * Set value of a specific pixel
    * 
    * @param row The of of the pixel
    * @param col The column of the pixel
    * @param value The value the pixel will be set.
    * @return true if pixel was set, and false otherwise.
    */
   boolean setPixel(int row, int col, boolean value)
   {
      image_data[row][col] = value;
      return true; // TODO: Determine instances where we might return false
   }

   /**
    * It does the job of checking the incoming data for every conceivable size
    * or null error. Smaller is okay. Bigger or null is not.
    * 
    * @param data incoming data which will be verified for size
    */
   private boolean checkSize(String[] data)
   {
      return false; // TODO:
   }

   /**
    * Display barcode image to console
    */
   private void displayToConsole()
   {
      // TODO
   }

}

/**
 * This class is a pseudo Datamatrix data structure, not a true Datamatrix,
 * because it does not contain any error correction or encoding. However, it
 * does have the 2D array format and a left and bottom BLACK "spine" as well as
 * an alternating right and top BLACK-WHITE pattern.
 */
class DataMatrix implements BarcodeIO
{

   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;

   /**
    * Constructs an empty, but non-null, image and text value. The initial image
    * should be all white, however, actualWidth and actualHeight should start at
    * 0, so it won't really matter what's in this default image, in practice.
    * The text can be set to blank, "", or something like "undefined".
    */
   public DataMatrix()
   {
      // TODO Auto-generated method stub
   }

   /**
    * Sets the image but leaves the text at its default value. Calls scan().
    * 
    * @param image
    */
   public DataMatrix(BarcodeImage image)
   {
      scan(image);
      // TODO Auto-generated method stub
   }

   /**
    * Sets the text but leaves the image at its default value. Call readText()
    * and avoid duplication of code here.
    * 
    * @param text
    */
   public DataMatrix(String text)
   {
      // TODO Auto-generated method stub
   }

   /**
    * A mutator for image. It is called by the constructor. Besides calling the
    * clone() method of the BarcodeImage class, this method will do a couple of
    * things including calling cleanImage() and then set the actualWidth and
    * actualHeight.
    * 
    * @param bc The BarcodeImage that is going to be copied scanned.s
    */
   public boolean scan(BarcodeImage bc)
   {
      // TODO Auto-generated method stub
      cleanImage();
      return false;
   }

   /**
    * @return Return the width of the BarcodeImage
    */
   public int getActualWidth()
   {
      return actualWidth;
   }

   /**
    * @return Return the height of the BarcodeImage
    */
   public int getActualHeight()
   {
      return actualHeight;
   }

   /**
    * A mutator for text. Called by the constructor.
    * 
    * @param text
    */
   public boolean readText(String text)
   {
      // TODO Auto-generated method stub
      return false;
   }

   /**
    * Looks at the internal text stored in the implementing class and produces a
    * companion BarcodeImage internally. After this is called, we expect the
    * object to contain a fully-defined image and text that are in agreement
    * with each other.
    * 
    * @return true if image generation was successful and false otherwise
    */
   public boolean generateImageFromText()
   {
      // TODO Auto-generated method stub
      return false;
   }

   /**
    * Given a char write a column representing that char.
    * 
    * @param col The column where the char will be written.
    * @param char The char that will be written.
    * @return true if char was written to column
    */
   private boolean writeCharToCol(int col, char car)
   {
      // TODO Auto-generated method stubs
      return false;
   }

   /**
    * Looks at the internal image stored in the implementing class, and produces
    * a companion text string, internally. After this is called, we expect
    * object to contain a fully defined image and text that are in agreement
    * with each other.
    * 
    * @return true if text was generated from image and false otherwise
    */
   public boolean translateImageToText()
   {
      // TODO Auto-generated method stub
      return false;
   }

   /**
    * Given a column, determine the char that column represents.
    * 
    * @param col The column from which the char will be determined.
    * @return A char
    */
   private char readCharFromCol(int col)
   {
      // TODO Auto-generated method stub
      return 'a';
   }

   /**
    * Prints out the text string to the console.
    */
   public void displayTextToConsole()
   {
      // TODO Auto-generated method stub

   }

   /**
    * Should display only the relevant portion of the image, clipping the excess
    * blank/white from the top and right. Also, show a border.
    */
   public void displayImageToConsole()
   {
      // TODO Auto-generated method stub
   }

   /**
    * This methods use the "spine" of the array (left and bottom BLACK) to
    * determine the actual width.
    * 
    * @return The width of the of the signal.
    */
   private int computeSignalWidth()
   {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * This methods use the "spine" of the array (left and bottom BLACK) to
    * determine the actual height
    * 
    * @return The height of the of the signal.
    */
   private int computeSignalHeight()
   {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * This private method will make no assumption about the placement of the
    * "signal" within a passed-in BarcodeImage. In other words, the in-coming
    * BarcodeImage may not be lower-left justified.
    */
   private void cleanImage()
   {
      // TODO Auto-generated method stub
   }

   /**
    * Move the signal to the lower left of the BarcodeImage
    */
   private void moveImageToLowerLeft()
   {
      // TODO Auto-generated method stub
   }

   /**
    * Move the signal a certain number of pixels down in a BarcodeImage
    * 
    * @param offset The numbers of pixels to move the signal down
    */
   private void shiftImageDown(int offset)
   {
      // TODO Auto-generated method stub
   }

   /**
    * Move the signal a certain number of pixels left in a BarcodeImage
    * 
    * @param offset The numbers of pixels to move the signal left
    */
   private void shiftImageLeft(int offset)
   {
      // TODO Auto-generated method stub
   }

   /**
    * Show the full image data including the blank top and right. It is a useful
    * debugging tool.
    */
   public void displayRawImage()
   {
      // TODO Auto-generated method stub
   }

   /**
    * Utility method that sets the image to white = false.
    */
   private void clearImage()
   {
      // TODO Auto-generated method stub
   }
}