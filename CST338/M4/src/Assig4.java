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
      String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "
	      };      
            
         
      
      String[] sImageIn_2 =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "
	      };
     
      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // create you own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      System.out.println("Translated back from the image:");
      dm.translateImageToText();
      dm.displayTextToConsole();
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
    * @return boolean depending if scan was successful
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
    * @return true if image generation was succesful and false otherwise
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

   public static final int MIN_HEIGHT = 2;
   public static final int MIN_WIDTH = 2;

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
      boolean valid = checkSize(strData);
      if (!valid)
      {
         System.out.println("The data is not valid, we are not responsible for"
               + " anything that happens after this.");
      }
      this.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];

      for (int i = 0; i < strData.length; i++)
      {
         String row = strData[i];
         for (int k = 0; k < row.length(); k++)
         {
            if (row.charAt(k) == DataMatrix.BLACK_CHAR)
            {
               image_data[i][k] = true;
            } 
            else
            {
               image_data[i][k] = false;
            }
         }
      }

   }

   /**
    * Get the value of a specific pixel.
    * 
    * @param row The row of the pixel
    * @param col The column of the pixel
    * @return Use the return value for both the actual data and also the error
    *         condition. If there is an error; we just return false.
    */
   public boolean getPixel(int row, int col)
   {
      if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH)
      {
         return false;
      }
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
   public boolean setPixel(int row, int col, boolean value)
   {
      if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH)
      {
         return false;
      } 
      else
      {
         image_data[row][col] = value;
         return true;
      }
   }

   /**
    * It does the job of checking the incoming data for every conceivable size
    * or null error. Smaller is okay. Bigger or null is not.
    * 
    * @param data incoming data which will be verified for size
    * @return false if data is incorrect
    */
   private boolean checkSize(String[] data)
   {
      if (data == null)
      {
         System.out.println("The data is null, unable to do anything");
         return false;
      }
      
      if (data.length > MAX_HEIGHT)
      {
         System.out.println("There are too many rows");
         return false;
      }
      
      if (data.length < MIN_HEIGHT)
      {
         System.out.println("There are not enough rows");
         return false;
      }
      
      int lengthOfFirstRow = data[0].length();
      for (int i = 1; i < data.length; i++)
      {
         if (data[i].length() != lengthOfFirstRow)
         {
            System.out.println("The row length is not consistent");
            return false;
         }
      }
      
      if (lengthOfFirstRow > MAX_WIDTH)
      {
         System.out.println("There are too many columns");
         return false;
      }
      
      if (lengthOfFirstRow < MIN_WIDTH)
      {
         System.out.println("There are not enough columns");
         return false;
      }
      
      for (int i = 0; i < data.length; i++)
      {
         String row = data[i];
         for (int k = 0; k < row.length(); k++)
         {
            if (row.charAt(k) != DataMatrix.BLACK_CHAR
                  && row.charAt(k) != DataMatrix.WHITE_CHAR)
            {
               System.out.println("Some characters are not black or white");
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Display barcode image to console
    */
   public void displayToConsole()
   {
      for (int i = 0; i < image_data.length; i++)
      {
         for (int k = 0; k < image_data[i].length; k++)
         {
            if (image_data[i][k])
            {
               System.out.print(DataMatrix.BLACK_CHAR);
            } 
            else
            {
               System.out.print(DataMatrix.WHITE_CHAR);
            }
         }
         System.out.println("");
      }
   }

   public BarcodeImage clone() throws CloneNotSupportedException
   {
      if (image_data == null)
      {
         throw new CloneNotSupportedException();
      }
      BarcodeImage cloneBarImg = new BarcodeImage();

      cloneBarImg.image_data = new boolean[image_data.length][];
      for (int i = 0; i < this.image_data.length; i++)
      {
         cloneBarImg.image_data[i] = this.image_data[i].clone();
      }
      return cloneBarImg;
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
   private static final String BLANK_TEXT = "";//do these need to be private?
   private static final int MAX_SIGNAL_HEIGHT = 10;
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
      image = new BarcodeImage();
      text = BLANK_TEXT;
      actualWidth = 0;
      actualHeight = 0;
   }

   /**
    * Sets the image but leaves the text at its default value. Calls scan().
    * 
    * @param image
    */
   public DataMatrix(BarcodeImage image)
   {
      scan(image);
      text = BLANK_TEXT;
   }

   /**
    * Sets the text but leaves the image at its default value. Call readText()
    * and avoid duplication of code here.
    * 
    * @param text
    */
   public DataMatrix(String text)
   {
      image = new BarcodeImage();
      readText(text);
      actualHeight = 0;
      actualWidth = 0;
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
      try
      {
         this.image = bc.clone();
         cleanImage();
         actualHeight = computeSignalHeight();
         actualWidth = computeSignalWidth();
      } 
      catch (CloneNotSupportedException e)
      {
      }
      return false;
      // TODO: define true/false conditions, check try/catch syntax. Caleb asked
      // this question in the forum so wait for the response.
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
    * A mutator for text. Called by the constructor. If char has a higher value
    * than 255 it cannot be encoded with 8 pixels, so false is returned.
    * 
    * @param text
    */
   public boolean readText(String text)
   {
      for (int i = 0; i < text.length(); i++)
      {
         if (text.charAt(i) > 255)
         {
            return false;
         }
      }
      if (text.length() <= BarcodeImage.MAX_WIDTH - 2)
      {
         this.text = text;
      } 
      else
      {
         this.text = text.substring(0, BarcodeImage.MAX_WIDTH - 2);
      }
      return true;
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
      clearImage();
      // create bottom border
      setBottomBorder();
      actualWidth = computeSignalWidth();

      // set data values in image
      for (int i = 0; i < text.length(); i++)
      {
         int colInSignal = i + 1;
         writeCharToCol(colInSignal, text.charAt(i));
      }
      actualHeight = MAX_SIGNAL_HEIGHT;
      setLeftBorder();
      setTopBorder();
      setRightBorder();
      return false;
   }

   private void clearImage()
   {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int k = 0; k < BarcodeImage.MAX_WIDTH; k++)
         {
            image.setPixel(i, k, false);
         }
      }
   }

   /*
    * sets bottom border of image, equal to length of text plus two cells for
    * left and right borders
    */
   private void setBottomBorder()
   {
      for (int n = 0; n < text.length() + 2; n++)
      {
         image.setPixel(BarcodeImage.MAX_HEIGHT - 1, n, true);
      }
   }

   private void setTopBorder()
   {
      int topIndex = BarcodeImage.MAX_HEIGHT - actualHeight;
      for (int n = 0; n < text.length() + 2; n++)
      {
         if ((n % 2) == 0)
            image.setPixel(topIndex, n, true);
      }
   }

   private void setRightBorder()
   {
      int rightIndex = actualWidth - 1;
      int topIndex = BarcodeImage.MAX_HEIGHT - actualHeight;
      for (int n = topIndex; n < BarcodeImage.MAX_HEIGHT; n++)
      {
         if ((n % 2) == 1)
            image.setPixel(n, rightIndex, true);
      }
   }

   private void setLeftBorder()
   {
      int start = BarcodeImage.MAX_HEIGHT - actualHeight;
      for (int n = start; n <= BarcodeImage.MAX_HEIGHT - 1; n++)
      {
         image.setPixel(n, 0, true);
      }
   }

   /**
    * Given a char write a column representing that char.
    * 
    * @param col The column where the char will be written.
    * @param char The char that will be written.
    * @return true if char was written to column
    */
   private boolean writeCharToCol(int col, char ch)
   {
      int asciiValue = (int)(ch);
      int topLine = BarcodeImage.MAX_HEIGHT - 2;
      int power;

      for (int i = 1; i <= topLine; i++)
      { // loop through all row except top and bottom for borders
         power = topLine - i;
         if (asciiValue / (int) (Math.pow(2, power)) == 1)
         {
            image.setPixel(i, col, true);
            asciiValue = asciiValue % (int) (Math.pow(2, power));
         } 
         else
            image.setPixel(i, col, false);
      }
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
      text = BLANK_TEXT;
      for (int i = 1; i < actualWidth - 1; i++)
      {
         text += readCharFromCol(i);
      }
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
      int asciiValue = 0;
      for (int i = 0; i < actualHeight - 2; i++)
      {
         if (image.getPixel(BarcodeImage.MAX_HEIGHT - 2 - i, col))
         {
            asciiValue += (Math.pow(2, i));
         }
      }
      char letter = (char)(asciiValue);
      return letter;
   }

   /**
    * Prints out the text string to the console. Adds two spaces to beginning of
    * string to allow for spine and border
    */
   public void displayTextToConsole()
   {
      System.out.println("  " + text);
   }

   /**
    * Should display only the relevant portion of the image, clipping the excess
    * blank/white from the top and right. Also, show a border.
    */
   public void displayImageToConsole()
   {
      String topBorder = "--";
      for (int i = 0; i < actualWidth; i++)
      {
         topBorder += "-";
      }
      System.out.println(topBorder);

      int startRow = BarcodeImage.MAX_HEIGHT - actualHeight;
      String oneRow;
      for (int i = startRow; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         oneRow = "|";
         for (int k = 0; k < actualWidth; k++)
         {
            if (image.getPixel(i, k))
            {
               oneRow += BLACK_CHAR;
            } 
            else
            {
               oneRow += WHITE_CHAR;
            }
         }
         oneRow += "|";
         System.out.println(oneRow);
      }
   }

   /**
    * This methods use the "spine" of the array (left and bottom BLACK) to
    * determine the actual width.
    * 
    * @return The width of the of the signal.
    */
   private int computeSignalWidth()
   {
      int width = BarcodeImage.MAX_WIDTH;
      for (int i = BarcodeImage.MAX_WIDTH - 1; i > 0; i--)
      {
         if (image.getPixel(BarcodeImage.MAX_HEIGHT - 1, i))
         {
            break;
         }
         width--;
      }
      return width;
   }

   /**
    * This methods use the "spine" of the array (left and bottom BLACK) to
    * determine the actual height
    * 
    * @return The height of the of the signal.
    */
   private int computeSignalHeight()
   {
      int height = BarcodeImage.MAX_HEIGHT;
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         if (image.getPixel(i, 0))
            return height;
         height--;
      }
      return 0;
   }

   /**
    * This private method will make no assumption about the placement of the
    * "signal" within a passed-in BarcodeImage. In other words, the in-coming
    * BarcodeImage may not be lower-left justified.
    */
   private void cleanImage()
   {
      moveImageToLowerLeft();
   }

   /**
    * Move the signal to the lower left of the BarcodeImage
    */
   private void moveImageToLowerLeft()
   {
      shiftImageDown(findYOffset());
      shiftImageLeft(findXOffset());
   }

   /**
    * returns the # of rows the image needs to be shifted down. loops up from
    * bottom row, checking for any true values in the pixels At the end of each
    * row check, it increments the offset tally If it finds a true pixel
    * representing the bottom border, it returns offset
    * 
    */

   private int findYOffset()
   {
      int offset = 0;
      for (int i = BarcodeImage.MAX_HEIGHT - 1; i > 0; i--)
      {
         for (int k = 0; k < BarcodeImage.MAX_WIDTH; k++)
         {
            if (image.getPixel(i, k))
               return offset;
         }
         offset++;
      }
      return offset;
   }

   /**
    * Move the signal a certain number of pixels down in a BarcodeImage
    * 
    * @param offset The numbers of pixels to move the signal down
    */
   private void shiftImageDown(int offset)
   {
      for (int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {
         for (int k = 0; k < BarcodeImage.MAX_WIDTH; k++)
         {
            this.image.setPixel(i, k, this.image.getPixel(i - offset, k));
         }
      }
   }

   /**
    * returns the # of rows the image needs to be shifted down. loops up from
    * bottom row, checking for any true values in the pixels At the end of each
    * row check, it increments the offset tally If it finds a true pixel
    * representing the bottom border, it returns offset
    * 
    */

   private int findXOffset()
   {
      int offset = 0;
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int k = 0; k < BarcodeImage.MAX_WIDTH; k++)
         {
            if (image.getPixel(i, k))
               return offset;
            offset++;
         }
         offset = 0;
      }
      return offset;
   }

   /**
    * Move the signal a certain number of pixels left in a BarcodeImage
    * 
    * @param offset The numbers of pixels to move the signal left
    */

   private void shiftImageLeft(int offset)
   {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int k = 0; k < BarcodeImage.MAX_WIDTH; k++)
         {
            image.setPixel(i, k, image.getPixel(i, k + offset));
         }
      }
   }

   /**
    * Show the full image data including the blank top and right. It is a useful
    * debugging tool.
    */

   public void displayRawImage()
   {
      System.out.println("-");
      image.displayToConsole();
      System.out.println("-");
   }

   /**
    * Utility method that sets the image to white = false.
    */
   /*
    * private void clearImage() { // TODO Auto-generated method stub *** NOTE THIS FUNCTION IS DEFINED ON LINE 497.*** }
    */
}

class TestString
{
   // Data from assignment
   static String[] validFromAssignment2 =
   { "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",
         "                                               ",
         "                                               ",
         "                                               "

   };

   // Data from assignment
   static String[] validFromAssignment3 =
   { "                                          ",
         "                                          ",
         "* * * * * * * * * * * * * * * * * * *     ",
         "*                                    *    ",
         "**** *** **   ***** ****   *********      ",
         "* ************ ************ **********    ",
         "** *      *    *  * * *         * *       ",
         "***   *  *           * **    *      **    ",
         "* ** * *  *   * * * **  *   ***   ***     ",
         "* *           **    *****  *   **   **    ",
         "****  *  * *  * **  ** *   ** *  * *      ",
         "**************************************    ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          "

   };

   // Data from assignment
   static String[] validFromAssignment1 =
   { "* * * * * * * * * * * * * * * * *    ",
         "*                                *   ",
         "****   * ***** **** **** ********    ",
         "*   *** ***************** ********   ",
         "*  * **  *   *   *  *    * **        ",
         "* *       * *  **    * * *    ****   ",
         "*     *   *    ** * *  *  *  ** *    ",
         "** * *** *****  **     * *      **   ",
         "****  *   **** ** *   *   *  * *     ",
         "**********************************   " };

   // Data from assignment
   static String[] validFromAssignment =
   { "       * * * * * * * * * * * * * * * * * *",
         "       *                                 *",
         "       ***** ** * **** ****** ** **** **  ",
         "       * **************      *************",
         "       **  *  *        *  *   *        *  ",
         "       * **  *     **    * *   * ****   **",
         "       **         ****   * ** ** ***   ** ",
         "       *   *  *   ***  *       *  ***   **",
         "       *  ** ** * ***  ***  *  *  *** *   ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          "

   };

   // Null String
   static String[] errorNullString = null;

   // Too many rows
   static String[] errorTooManyRows =
   { "       * * * * * * * * * * * * * * * * * *",
         "       *                                 *",
         "       ***** ** * **** ****** ** **** **  ",
         "       * **************      *************",
         "       **  *  *        *  *   *        *  ",
         "       * **  *     **    * *   * ****   **",
         "       **         ****   * ** ** ***   ** ",
         "       *   *  *   ***  *       *  ***   **",
         "       *  ** ** * ***  ***  *  *  *** *   ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",
         "                                          ",

   };

   // Too many columns
   static String[] errorTooManyColumns =
   { "* *                                                                 ",
         "***                                                                 ",
         "*                                                                   ",
         "***                                                                 ",
         "*                                                                   ",
         "***                                                                 ",
         "*                                                                   ",
         "***                                                                 ",
         "*                                                                   ",
         "***                                                                 "

   };

   // Empty
   static String[] validEmpty =
   { "* ", "**", "* ", "**", "* ", "**", "* ", "**", "* ", "**" };

   // Empty
   static String[] validEmpty2 =
   { "* ", "**"

   };

   // Inconsistent Length
   static String[] errorInconsistentLength =
   { "* ", "**", "* ", "**", "* ", "**", "* ", "**", "*", "**" };

   // One character
   static String[] oneCharacter =
   { "* *", "***", "*  ", "***", "*  ", "***", "*  ", "***", "*  ", "***" };
}
