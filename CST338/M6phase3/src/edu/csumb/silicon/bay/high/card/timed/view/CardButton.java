package edu.csumb.silicon.bay.high.card.timed.view;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

import edu.csumb.silicon.bay.high.card.timed.controller.CardGameFramework;
import edu.csumb.silicon.bay.high.card.timed.controller.CardListener;
import edu.csumb.silicon.bay.high.card.timed.model.Card;
import edu.csumb.silicon.bay.high.card.timed.model.Hand;

public class CardButton extends JButton
{
   /**
    * 
    */
   private static final long serialVersionUID = -8105348049120075230L;
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
}