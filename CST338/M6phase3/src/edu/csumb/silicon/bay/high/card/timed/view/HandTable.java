package edu.csumb.silicon.bay.high.card.timed.view;

import javax.swing.Icon;
import javax.swing.JLabel;

import edu.csumb.silicon.bay.high.card.timed.controller.CardGameFramework;
import edu.csumb.silicon.bay.high.card.timed.model.Card;
import edu.csumb.silicon.bay.high.card.timed.model.Hand;

/*
 * extends CardTable to allow it to be responsive to changes fired from the
 * game.
 */
public class HandTable extends CardTable
{
   /**
    * 
    */
   private static final long serialVersionUID = -7144931244138991921L;

   // constructor when no played cards are received and play area should be
   // empty
   public HandTable(String title, int numCardsPerHand, int numPlayers,
         JLabel[] computerLabels, CardButton[] humanLabels,
         CardGameFramework highCardGame, Hand playerWinnings,
         Hand computerWinnings)
   {
      super(title, numCardsPerHand, numPlayers);
      addHandPanels(numCardsPerHand, computerLabels, humanLabels, highCardGame,
            playerWinnings, computerWinnings);
   }

   // constructor when played cards are received and should populate play area
   public HandTable(String title, int numCardsPerHand, int numPlayers,
         JLabel[] computerLabels, CardButton[] humanLabels,
         CardGameFramework highCardGame, Hand playerWinnings,
         Hand computerWinnings, Card humanPlayedCard, Card computerPlayedCard)
   {
      this(title, numCardsPerHand, numPlayers, computerLabels, humanLabels,
            highCardGame, playerWinnings, computerWinnings);
      addPlayPanel(humanPlayedCard, computerPlayedCard);
   }

   // receives the two played cards, stores them in labels, and adds icon labels
   // and text labels to center play area
   public void addPlayPanel(Card humanPlayedCard, Card computerPlayedCard)
   {
      Icon tempIcon;
      tempIcon = GUICard.getIcon(humanPlayedCard);
      JLabel humanCardLabel = new JLabel(tempIcon);
      JLabel humanCardText = new JLabel("You", JLabel.CENTER);

      tempIcon = GUICard.getIcon(computerPlayedCard);
      JLabel computerCardLabel = new JLabel(tempIcon);
      JLabel computerCardText = new JLabel("Computer", JLabel.CENTER);

      pnlPlayArea.add(humanCardLabel);
      pnlPlayArea.add(computerCardLabel);
      pnlPlayArea.add(humanCardText);
      pnlPlayArea.add(computerCardText);
   }

   // receives arrays of labels(computer) or CardButtons(player) and displays
   // them in the "hands" areas of the card table GUI
   public void addHandPanels(int numCardsPerHand, JLabel[] computerLabels,
         CardButton[] humanLabels, CardGameFramework highCardGame,
         Hand playerWinnings, Hand computerWinnings)
   {
      int k;
      Icon tempIcon;
      // CREATE LABELS ----------------------------------------------------
      for (k = 0; k < numCardsPerHand; k++)
      {
         // add back of card icon to all computer hand
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         this.pnlComputerHand.add(computerLabels[k]);

         // find the value of each card and convert to icon
         tempIcon = GUICard.getIcon(highCardGame.getHand(0).inspectCard(k));
         humanLabels[k] = new CardButton(tempIcon, k, numCardsPerHand, this,
               computerLabels, humanLabels, highCardGame, playerWinnings,
               computerWinnings, highCardGame.getHand(0).inspectCard(k),
               highCardGame.getHand(1).inspectCard(k));
         this.pnlHumanHand.add(humanLabels[k]);
      }

      // ADD LABELS TO PANELS -----------------------------------------
      for (k = 0; k < numCardsPerHand; k++)
      {
         this.pnlComputerHand.add(computerLabels[k]);
         this.pnlHumanHand.add(humanLabels[k]);
      }
   }

   // resets play area display
   public void clearPlayArea()
   {
      pnlPlayArea.removeAll();
   }

   // resets computer hand area display
   public void clearComputerHand()
   {
      pnlComputerHand.removeAll();
   }

   // resets player hand area display
   public void clearPlayerHand()
   {
      pnlHumanHand.removeAll();
   }
}
