/*
***********************************
* Alejandro Guzman-Vega
* Andrea Wieland
* Huda Mutwakil
* Brandon Lewis
* CST 338
* Deck of Cards GUI
* Professor Cecil
***********************************
*/

import javax.swing.*;
import java.awt.*;

public class Assig5Phase1
{
   static final int NUM_CARD_IMAGES = 57;
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

   static void loadCardIcons()
   {
      int i = 0;
      String relativePath = "./images/";
      String extension = ".gif";
      for (int j = 0; j <= 3; j++)
      {
         for (int k = 0; k <= 13; k++)
         {
            ImageIcon image = new ImageIcon(
                  relativePath + turnIntIntoCardValue(k)
                        + turnIntIntoCardSuit(j) + extension);
            icon[i] = image;
            i++;
         }
      }
      icon[i] = new ImageIcon(relativePath + "BK" + extension);
   }

   static String turnIntIntoCardValue(int k)
   {
      String[] values =
      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
      return values[k];
   }

   static String turnIntIntoCardSuit(int j)
   {
      String[] suits =
      { "C", "D", "H", "S" };
      return suits[j];
   }

   public static void main(String[] args)
   {
      int k;

      // prepare the image icon array
      loadCardIcons();

      // establish main frame in which program will run
      JFrame mainWindow = new JFrame("Card Room");
      mainWindow.setSize(1150, 650);
      mainWindow.setLocationRelativeTo(null);

      mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      mainWindow.setLayout(layout);

      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];

      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);

      for (k = 0; k < NUM_CARD_IMAGES; k++)
         mainWindow.add(labels[k]);

      // show everything to the user
      mainWindow.setVisible(true);

   }

}