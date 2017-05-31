
/*
***********************************
* Alejandro Guzman-Vega
* Andrea Wieland
* Huda Mutwakil
* Brandon Lewis
* CST 338
* Timed High Card Game
* Professor Cecil
***********************************
*/

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class Assig6 extends JFrame
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
   static CardButton[] humanLabels = new CardButton[NUM_CARDS_PER_HAND];
   
   // MODELS
   public static void main(String[] args)
   {

   }


   // VIEW
   
   /*
    * class CardButton defines the buttons that display in frame as the user's
    * playable hand. it uses the GUIcard icons and presents it in a button that
    * fires to the inner CardListener class.
    */
   class CardButton extends JButton
   {
      static final int NUM_PLAYERS = 2;
      static final String title = "Cardtable";

      CardButton(Icon icon, int cardIndex, int numCardsPerHand, HandTable table,
            JLabel[] computerHand, CardButton[] playerHand,
            CardGameFramework highCardGame, Hand playerWinnings,
            Hand computerWinnings, Card playerCard, Card computerCard)
      {
         super(icon);
         addActionListener(new CardListener(cardIndex, numCardsPerHand - 1, table,
               computerHand, playerHand, highCardGame, playerWinnings,
               computerWinnings, playerCard, computerCard));
      }


   // CONTROLLERS
   
}

