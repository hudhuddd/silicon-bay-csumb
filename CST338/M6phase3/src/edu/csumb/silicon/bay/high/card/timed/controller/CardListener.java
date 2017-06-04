package edu.csumb.silicon.bay.high.card.timed.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JLabel;

import edu.csumb.silicon.bay.high.card.timed.model.Card;
import edu.csumb.silicon.bay.high.card.timed.model.Hand;
import edu.csumb.silicon.bay.high.card.timed.view.AnnouncementBox;
import edu.csumb.silicon.bay.high.card.timed.view.CardButton;
import edu.csumb.silicon.bay.high.card.timed.view.HandTable;

public class CardListener implements ActionListener
{
   private HandTable table;
   private Card playerCard;
   private Card computerCard;
   private int cards;
   private int cardIndex;
   private JLabel[] computerHand;
   private CardButton[] playerHand;
   private CardGameFramework highCardGame;
   private Hand playerWinnings;
   private Hand computerWinnings;

   // receives and stores all the parameters for actionPerformed()
   // functionality
   public CardListener(int cardIndex, int numCardsPerHand, HandTable table,
         JLabel[] computerHand, CardButton[] playerHand,
         CardGameFramework highCardGame, Hand playerWinnings,
         Hand computerWinnings, Card playerCard, Card computerCard)
   {
      this.table = table;
      this.playerCard = playerCard;
      this.computerCard = computerCard;
      this.cards = numCardsPerHand;
      this.computerHand = computerHand;
      this.playerHand = playerHand;
      this.highCardGame = highCardGame;
      this.cardIndex = cardIndex;
      this.playerWinnings = playerWinnings;
      this.computerWinnings = computerWinnings;
   }

   // upon receiving action event, triggers game logic, stores altered hand
   // data, and updates and refreshes game table GUI. player always plays
   // their card first, but semi-random behavior from the computer player
   // ensures that the computer won't always win
   public void actionPerformed(ActionEvent e)
   {
      // clears play areas so cards dont keep adding on
      table.clearPlayArea();
      table.clearComputerHand();
      table.clearPlayerHand();

      // pass index of selected card to play and remove from hand
      playerCard = highCardGame.playCard(0, cardIndex);
      // call computer logic function and play returned hand
      computerCard = getComputerPlay(highCardGame, playerCard);

      // compares card values, declares winner in announcementBox and stores
      // won cards in appropriate winnings array(in Hand object)
      if (playerCard.compareTo(computerCard) == 1)
      {
         new AnnouncementBox("You win this hand!");
         playerWinnings.takeCard(playerCard);
         playerWinnings.takeCard(computerCard);
      }
      else if (playerCard.compareTo(computerCard) == -1)
      {
         new AnnouncementBox("Computer wins this hand");
         computerWinnings.takeCard(playerCard);
         computerWinnings.takeCard(computerCard);
      }

      // update card table with played cards in center and reduced hands
      table.addPlayPanel(playerCard, computerCard);
      table.addHandPanels(cards, computerHand, playerHand, highCardGame,
            playerWinnings, computerWinnings);
      table.revalidate();
      table.repaint();

      // when all cards have been played, announcementBox declares final
      // winner
      if (cards == 0)
      {
         if (playerWinnings.getNumCards() > computerWinnings.getNumCards())
         {
            new AnnouncementBox("You won the game!");
         }
         else if (playerWinnings.getNumCards() < computerWinnings.getNumCards())
         {
            new AnnouncementBox("Sorry, you lost.");

         }
         else
         {
            new AnnouncementBox("It's a tie!");
         }
      }
   }

   // defines behavior of the computer player. based on random object,
   // either throws the hand by sending lowest card, or tries to win by
   // sending the lowest card that will beat the player's card
   private Card getComputerPlay(CardGameFramework highCardGame, Card playerCard)
   {
      Random randomGenerator = new Random();
      int randCard = randomGenerator.nextInt(3);
      Card tempCard;

      Hand computerHand = highCardGame.getHand(1);
      computerHand.sort();
      // coin flip determines behavior: lowest card or lowest beating card
      if (randCard == 0)
      {
         for (int i = 0; i < computerHand.getNumCards(); i++)
         {
            tempCard = computerHand.inspectCard(i);
            if (tempCard.compareTo(playerCard) > 0)
            {
               return highCardGame.playCard(1, i);
            }
         }
         // if no winning card is found, returns the lowest
         return highCardGame.playCard(1, 0);
      }
      else
      {
         return highCardGame.playCard(1, 0);
      }
   }
}
