package edu.csumb.silicon.bay.high.card.timed;
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

import edu.csumb.silicon.bay.high.card.timed.controller.CardGameFramework;
import edu.csumb.silicon.bay.high.card.timed.model.Card;
import edu.csumb.silicon.bay.high.card.timed.model.Hand;
import edu.csumb.silicon.bay.high.card.timed.view.CardButton;
import edu.csumb.silicon.bay.high.card.timed.view.GUICard;
import edu.csumb.silicon.bay.high.card.timed.view.HandTable;

public class Assig6
{

   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;

   public static void main(String[] args)
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardGameFramework highCardGame = new CardGameFramework(numPacksPerDeck,
            numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      GUICard.loadCardIcons(); // import all the gifs
      highCardGame.deal(); // deal out the hands

      // establish main frame in which program will run
      HandTable myCardTable = new HandTable("CardTable", NUM_CARDS_PER_HAND,
            NUM_PLAYERS, new JLabel[NUM_CARDS_PER_HAND],
            new CardButton[NUM_CARDS_PER_HAND], highCardGame, new Hand(),
            new Hand());
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myCardTable.setVisible(true);
   }
}
