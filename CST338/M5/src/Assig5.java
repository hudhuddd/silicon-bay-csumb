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
import java.io.File;

public class Assig5
{
   static final int NUM_CARD_IMAGES = 57;
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
      
   static void loadCardIcons()
   {
      int i = 0;
      for (int k = 0; k <= 13; k++)
         {
            for (int j = 0; j <= 3; j++)
            {
               ImageIcon image = new ImageIcon("./images/" + turnIntIntoCardValue(k) + turnIntIntoCardSuit(j) + ".gif");
               icon[i] = image;
               i++;      
            }
         }
      }

   static String turnIntIntoCardValue(int k)
   {
      return null;
    
      
   }
   
   static String turnIntIntoCardSuit(int j)
   {
      return null;
      
   }
   
   public static void main(String[] args)
   {
      int k;
      
      loadCardIcons();
      
      JFrame mainWindow = new JFrame("Card Room");
      mainWindow.setSize(1150, 650);
      mainWindow.setLocationRelativeTo(null);
      mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);   
      mainWindow.setLayout(layout);
      
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);
      
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         mainWindow.add(labels[k]);
      
      mainWindow.setVisible(true);
      
   }
   

}
