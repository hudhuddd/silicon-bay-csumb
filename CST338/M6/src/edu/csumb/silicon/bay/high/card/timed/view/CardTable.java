package edu.csumb.silicon.bay.high.card.timed.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/*
 * defines layout of playing table GUI, validating that all parameters are legal
 */
public class CardTable extends JFrame
{
   /**
    * 
    */
   private static final long serialVersionUID = 6565038698784532828L;
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   /**
    * The constructor filters input, adds any panels to the JFrame, and
    * establishes layouts according to the general description below.
    * 
    * @param title The title for the window
    * @param numCardsPerHand the number of cards per hand
    * @param numPlayers the number of players
    */
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      filterTitle(title);
      this.numCardsPerHand = filterNumCardsPerHand(numCardsPerHand);
      this.numPlayers = filternumPlayers(numPlayers);

      pnlComputerHand = new JPanel(new GridLayout(1, 1, 10, 10));
      pnlHumanHand = new JPanel(new GridLayout(1, 1, 10, 10));
      pnlPlayArea = new JPanel(new GridLayout(2, 2, 20, 20));

      setLayout(new BorderLayout(10, 10));
      add(pnlComputerHand, BorderLayout.NORTH);
      add(pnlHumanHand, BorderLayout.SOUTH);
      add(pnlPlayArea, BorderLayout.CENTER);

      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
   }

   /**
    * Verify input, if invalid do error correction and return a valid input.
    * 
    * @param numPlayers2 the unverified number of players
    * @return an int will be returned. If input was valid then it will be
    *         returned otherwise some int will be selected.
    */
   private int filternumPlayers(int numPlayers2)
   {
      if (numPlayers2 != MAX_PLAYERS)
         return MAX_PLAYERS;
      return numPlayers2;
   }

   /**
    * Verify input, if invalid do error correction and return a valid input.
    * 
    * @param numCardsPerHand2 the unverified number of cards per hand
    * @return an int will be returned. If input was valid then it will be
    *         returned otherwise some int will be selected.
    */
   private int filterNumCardsPerHand(int numCardsPerHand2)
   {
      if (numCardsPerHand <= 0 || numCardsPerHand > MAX_CARDS_PER_HAND)
         return 5;
      return numCardsPerHand2;
   }

   /**
    * Verify input, if invalid do error correction and return a valid input.
    * 
    * @param title the unverified title.
    * @return a string will be returned. If input was valid then it will be
    *         returned, otherwise some default string is returned.
    */
   private String filterTitle(String title)
   {
      if (title.length() == 0)
         return "GUI Card Game";
      return title;
   }

   /**
    * Get the number of cards per hand.
    * 
    * @return the numCardsPerHand
    */
   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   /**
    * Get the number of players
    * 
    * @return the numPlayers
    */
   public int getNumPlayers()
   {
      return numPlayers;
   }
}
