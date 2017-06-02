package edu.csumb.silicon.bay.high.card.timed.view;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import edu.csumb.silicon.bay.high.card.timed.model.Card;
import edu.csumb.silicon.bay.high.card.timed.model.Suit;

public class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K +
   // joker
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   /**
    * 
    */
   public static void loadCardIcons()
   {
      if (!iconsLoaded)
      {
         String relativePath = "./images/";
         String extension = ".gif";
         for (int j = 0; j <= 3; j++)
         {
            for (int k = 0; k <= 13; k++)
            {
               ImageIcon image = new ImageIcon(
                     relativePath + turnIntIntoCardValue(k)
                           + turnIntIntoCardSuit(j) + extension);
               iconCards[k][j] = image;
            }
         }
         iconBack = new ImageIcon(relativePath + "BK" + extension);
         iconsLoaded = true;
      }
   }

   /**
    * Given an int return a String representing a card value
    * 
    * @param k int that represents a card value
    * @return A string representing a card value A, 2, 3, ... Q, K, X
    */
   private static String turnIntIntoCardValue(int k)
   {
      if (k < 0 || k > 13)
      {
         throw new IllegalArgumentException(
               "k is outside the range of card values, k: " + k);
      }
      String[] values =
      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
      return values[k];
   }

   /**
    * Given an int return a String representing a card suit
    * 
    * @param k int that represents a card suit
    * @return A string representing a card suit C, D, H, S
    */
   private static String turnIntIntoCardSuit(int j)
   {
      if (j < 0 || j > 3)
      {
         throw new IllegalArgumentException(
               "j is outside the range of card values, j: " + j);
      }
      String[] suits =
      { "C", "D", "H", "S" };
      return suits[j];
   }

   /**
    * Given a card return it's icon.
    * 
    * @param card A card object for which we can an icon
    * @return An icon object
    */
   public static Icon getIcon(Card card)
   {
      loadCardIcons();

      int theSuit = -1;
      Suit suit = card.getSuit();
      switch (suit)
      {
      case clubs:
         theSuit = 0;
         break;
      case diamonds:
         theSuit = 1;
         break;
      case hearts:
         theSuit = 2;
         break;
      case spades:
         theSuit = 3;
         break;
      }

      int theValue = -1;
      char value = card.getValue();
      if (Character.getNumericValue(value) >= 2
            && Character.getNumericValue(value) <= 9)
      {
         theValue = Character.getNumericValue(value) - 1;
      }
      else
      {
         switch (value)
         {
         case 'A':
            theValue = 0;
            break;
         case 'T':
            theValue = 9;
            break;
         case 'J':
            theValue = 10;
            break;
         case 'Q':
            theValue = 11;
            break;
         case 'K':
            theValue = 12;
            break;
         case 'X':
            theValue = 13;
            break;
         }
      }

      return iconCards[theValue][theSuit];
   }

   /**
    * Get the icon for the back of cards
    * 
    * @return An icon object representing the back of cards
    */
   public static Icon getBackCardIcon()
   {
      loadCardIcons();
      return iconBack;
   }
}
