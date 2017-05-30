
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
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class Assig5Phase3
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static CardButton[] humanLabels = new CardButton[NUM_CARDS_PER_HAND];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
   static Hand playerWinnings = new Hand();
   static Hand computerWinnings = new Hand();

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
            NUM_PLAYERS, computerLabels, humanLabels, highCardGame,
            playerWinnings, computerWinnings);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      myCardTable.setVisible(true);
   }

   static Card generateRandomCard()
   {
      Card.Suit suit = Card.Suit.values()[new Random().nextInt(4)];
      char c = Card.validValues[new Random().nextInt(14)];
      return new Card(c, suit);
   }
}

/*
 class CardButton defines the buttons that display in frame as the
 user's playable hand. it uses the GUIcard icons and presents it in a button
 that fires to the inner CardListener class. 
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
   /*
   listener for the cardButton action event
   */
   class CardListener implements ActionListener
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

      //receives and stores all the parameters for actionPerformed() functionality
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
      
      //upon receiving action event, triggers game logic, stores altered hand 
      //data, and updates and refreshes game table GUI
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
            AnnouncementBox winner = new AnnouncementBox("You win this hand!");
            playerWinnings.takeCard(playerCard);
            playerWinnings.takeCard(computerCard);
         }
         else if (playerCard.compareTo(computerCard) == -1)
         {
            AnnouncementBox winner = new AnnouncementBox(
                  "Computer wins this hand");
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
               AnnouncementBox finalScore = new AnnouncementBox(
                     "You won the game!");
            }
            else if (playerWinnings.getNumCards() < computerWinnings
                  .getNumCards())
            {
               AnnouncementBox finalScore = new AnnouncementBox(
                     "Sorry, you lost.");

            }
            else
            {
               AnnouncementBox finalScore = new AnnouncementBox("It's a tie!");
            }

         }
      }

      //defines behavior of the computer player. based on random coin flip,
      //either throws the hand by sending lowest card, or tries to win by
      //sending the lowest card that will beat the player's card
      private Card getComputerPlay(CardGameFramework highCardGame,
            Card playerCard)
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
}

/*
defines pop-up message box
*/
class AnnouncementBox
{
   static int HEIGHT = 200;
   static int WIDTH = 300;
   private JFrame window;
   private JLabel text;

   //constructor
   AnnouncementBox(String input)
   {
      window = new JFrame();
      window.setSize(WIDTH, HEIGHT);
      window.setLocationRelativeTo(null);
      window.setDefaultCloseOperation(window.DISPOSE_ON_CLOSE);

      window.setLayout(new BorderLayout(10, 10));

      text = new JLabel(input);
      text.setHorizontalAlignment(SwingConstants.CENTER);
      text.setVerticalAlignment(SwingConstants.CENTER);
      window.add(text, BorderLayout.CENTER);
      JButton endButton = new JButton("OK");
      EndingListener buttonEar = new EndingListener(window);
      endButton.addActionListener(buttonEar);
      endButton.setHorizontalAlignment(SwingConstants.CENTER);
      endButton.setVerticalAlignment(SwingConstants.CENTER);
      window.add(endButton, BorderLayout.SOUTH);
      window.setVisible(true);

   }
   
   //on button click, closes window
   class EndingListener implements ActionListener
   {
      JFrame window;

      EndingListener(JFrame window)
      {
         this.window = window;
      }

      public void actionPerformed(ActionEvent e)
      {
         window.dispose();
      }
   }
}

/*
 defines layout of playing table GUI, validating that all parameters
 are legal
*/
class CardTable extends JFrame
{
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
      String frameTitle = filterTitle(title);
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


/*
 extends CardTable to allow it to be responsive to changes fired from the game. 
*/
class HandTable extends CardTable
{
   //constructor when no played cards are received and play area should be empty
   public HandTable(String title, int numCardsPerHand, int numPlayers,
         JLabel[] computerLabels, CardButton[] humanLabels,
         CardGameFramework highCardGame, Hand playerWinnings,
         Hand computerWinnings)
   {
      super(title, numCardsPerHand, numPlayers);
      addHandPanels(numCardsPerHand, computerLabels, humanLabels, highCardGame,
            playerWinnings, computerWinnings);
   }

   //constructor when played cards are received and should populate play area
   public HandTable(String title, int numCardsPerHand, int numPlayers,
         JLabel[] computerLabels, CardButton[] humanLabels,
         CardGameFramework highCardGame, Hand playerWinnings,
         Hand computerWinnings, Card humanPlayedCard, Card computerPlayedCard)
   {
      this(title, numCardsPerHand, numPlayers, computerLabels, humanLabels,
            highCardGame, playerWinnings, computerWinnings);
      addPlayPanel(humanPlayedCard, computerPlayedCard);
   }

   //receives the two played cards, stores them in labels, and adds icon labels
   //and text labels to center play area
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

   //receives arrays of labels(computer) or CardButtons(player) and displays
   //them in the "hands" areas of the card table GUI
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

   //resets play area display
   public void clearPlayArea()
   {
      pnlPlayArea.removeAll();
   }

   //resets computer hand area display
   public void clearComputerHand()
   {
      pnlComputerHand.removeAll();
   }
   
   //resets player hand area display
   public void clearPlayerHand()
   {
      pnlHumanHand.removeAll();
   }
}

class GUICard
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
      Card.Suit suit = card.getSuit();
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

/**
 * Card objects contain chars for the face value and suit strings. the class
 * contains accessors, mutators toString to display the card values, equals() to
 * check card equivalence, and private helpers methods
 */
class Card implements Comparable<Card>
{
   /**
    * array of constants for suit values
    */
   public static enum Suit
   {
      clubs, diamonds, hearts, spades;
   }

   /**
    * Array of card face values
    */
   public static char[] validValues =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

   public static char[] valuRanks =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };
   
   public static Suit[] suitRanks = {Suit.clubs, Suit.diamonds, 
		   Suit.hearts, Suit.spades};
   
   private char value;
   private Suit suit;
   private boolean errorFlag;

   /**
    * Constructor, that takes a value and a suit and creates a card.
    * 
    * @param value
    * @param suit
    */
   Card(char theValue, Suit theSuit)
   {
      set(theValue, theSuit);
   }

   /**
    * Default constructor, sets card to Ace of Spaces by default.
    */
   Card()
   {
      set('A', Suit.spades);
   }

   /**
    * a stringizer that the client can use prior to displaying the card It
    * provides a clean representation of the card. If errorFlag == true, it
    * should return correspondingly reasonable reflection of this fact
    * (something like "[ invalid ]" rather than a suit and value).
    * 
    * @param value
    * @param suit
    * @return
    * 
    */
   public String toString()
   {
      if (errorFlag)
      {
         return "** illegal **";
      }
      else
      {
         return value + " of " + suit;
      }

   }

   /**
    * a mutator that accepts the legal values established in the earlier
    * section. When bad values are passed, errorFlag is set to true and other
    * values can be left in any state (even partially set). If good values are
    * passed, they are stored and errorFlag is set to false. Make use of the
    * private helper, listed below.
    * 
    * @param value
    * @param suit
    */
   public boolean set(char value, Suit suit)
   {
      if (isValid(value, suit))
      {
         this.value = value;
         this.suit = suit;
         errorFlag = false;
         return true;
      }
      else
      {
         errorFlag = true;
         return false;
      }
   }

   /**
    * @return value
    */
   public char getValue()
   {
      return value;
   }

   /**
    * @return suit
    */
   public Suit getSuit()
   {
      return suit;
   }

   /**
    * @return errorFlag
    */
   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   /**
    * returns true if all the fields (members) are identical and false,
    * otherwise.
    * 
    * @param card
    * @return true or false
    */
   public boolean equals(Card card)
   {
      if (!errorFlag && this.suit == card.suit && this.value == card.value)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * A private helper method that returns true or false, depending on the
    * legality of the parameters. Note that, although it may be impossible for
    * suit to be illegal (due to its enum-ness), we pass it, anyway, in
    * anticipation of possible changes to the type from enum to, say, char or
    * int, someday. We only need to test value, at this time.
    * 
    * @param value
    * @param suit
    * @return errorFlag
    */
   private boolean isValid(char value, Suit suit)
   {
      for (char validValue : validValues)
      {
         if (validValue == value)
         {
            return true;
         }
      }
      return false;
   }

   static void arraySort(Card[] cards, int arraySize)
   {
      for (int i = 1; i < arraySize; i++)
      {
         for (int j = 1; j < arraySize; j++)
         {
            if (cards[j - 1].compareTo(cards[j]) > 0)
            {
               Card tempCard;
               tempCard = cards[j];
               cards[j] = cards[j - 1];
               cards[j - 1] = tempCard;
            }
         }
      }
   }

   @Override
   public int compareTo(Card thatCard)
   {
      int thisVal = 0;
      for (char i : validValues)
      {
         if (i == this.value)
         {
            break;
         }
         else
         {
            thisVal++;
         }
      }
      int thatVal = 0;
      for (char i : validValues)
      {
         if (i == thatCard.value)
         {
            break;
         }
         else
         {
            thatVal++;
         }
      }
      if (thisVal == thatVal)//if values are equal, compare suits
      {
         int thisSuit = 0;
         for (Suit i : suitRanks)
         {
            if (i == this.suit)
            {
               break;
            }
            else
            {
               thisSuit++;
            }
         }
         int thatSuit = 0;
         for (Suit i : suitRanks)
         {
            if (i == thatCard.suit)
            {
               break;
            }
            else
            {
               thatSuit++;
            }
         }
         if (thisSuit > thatSuit)
        	 return 1;
         else
        	 return -1;
      }
      else if (thisVal > thatVal)
      {
         return 1;
      }
      else
      {
         return -1;
      }
   }
}

/**
 *
 */
class Hand
{
   public static int MAX_CARDS = 56;
   private Card[] myCards;
   private int numCards;
   private boolean arrayLoaded = false;

   /**
    * default constructor
    */
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * resets entire array of cards in hand to new Cards
    */
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * @return numCards
    */
   public int getNumCards()
   {
      return numCards;
   }

   /**
    * adds a card to the next available position in the myCards array
    * 
    * @param card
    * @returns bool value
    */
   public boolean takeCard(Card card)
   {
      if (numCards < MAX_CARDS)
      {
         myCards[numCards] = new Card(card.getValue(), card.getSuit());
         numCards++;
         return true;
      }
      else
      {
         return false;
      }
   }

   //returns a card at a specific index in the hand and removes it from the
   //array
   public Card playCard(int location)
   {
      if (numCards != 0)
      {
         return removeCard(location);
      }
      else
      {
         Card errorCard = new Card();
         errorCard.set('E', Card.Suit.spades);
         return errorCard;
      }
   }

   //returns a card at an index, replaces that index element with last card
   //in array, sets final array element to null, and decreases hand size
   //by decrementing numCards
   private Card removeCard(int index)
   {
      Card cardToReturn = myCards[index];
      myCards[index] = myCards[numCards - 1];
      myCards[numCards - 1] = null;
      numCards--;
      return cardToReturn;
   }

   /**
    * a stringizer that the client can use prior to displaying the entire hand.
    * 
    * @return string
    */
   public String toString()
   {
      String returnString = "Hand = ( ";
      for (int i = 0; i < numCards; i++)
      {
         if (i != numCards - 1)
         {
            returnString = returnString + myCards[i].toString() + ", ";
         }
         else
         {
            returnString = returnString + myCards[i].toString();
         }
      }
      returnString = returnString + " )";
      return returnString;
   }

   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }

   /**
    * Accessor for an individual card. Returns a card with errorFlag = true if k
    * is bad.
    * 
    * @param k
    * @return card
    */
   public Card inspectCard(int k)
   {
      if (k < numCards && k >= 0)
      {
         return new Card(myCards[k].getValue(), myCards[k].getSuit());
      }
      else
      {
         Card errorCard = new Card();
         errorCard.set('E', Card.Suit.spades);
         return errorCard;
      }
   }
}

/**
 *
 */
class Deck
{
   public final int MAX_CARDS = 6 * 56;
   private static Card[] masterPack = new Card[56];

   private Card[] cards;
   private int topCard;
   private int numPacks;
   private static boolean masterPackLoaded = false;

   /**
    * a constructor that populates the arrays and assigns initial values to
    * members
    * 
    * @param numPacks
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      init(numPacks);

   }

   /**
    * Make sure that there are not too many instances of the card in the deck if
    * you add it. Return false if there will be too many. It should put the card
    * on the top of the deck.
    * 
    * @param card
    * @return
    */
   boolean addCard(Card newCard)
   {
      int count = 0;
      int max = numPacks;
      for (Card card : cards)
      {
         if (newCard.equals(card))
         {
            count++;
         }
         if (count >= max)
         {
            return false;
         }
      }
      cards = Arrays.copyOf(cards, cards.length + 1);
      cards[topCard] = newCard;
      topCard++;
      return true;
   }

   /**
    * you are looking to remove a specific card from the deck. Put the current
    * top card into its place. Be sure the card you need is actually still in
    * the deck, if not return false.
    * 
    * @param card
    * @return
    */
   boolean removeCard(Card card)
   {
      boolean exists = false;
      for (int i = 0; i < topCard; i++)
      {
         if (card.equals(cards[i]))
         {
            Card tempCard = cards[topCard - 1];
            cards[topCard - 1] = cards[i];
            cards[i] = tempCard;
            exists = true;
            break;
         }
      }
      dealCard();
      return exists;
   }

   /**
    * put all of the cards in the deck back into the right order according to
    * their values. Is there another method somewhere that already does this
    * that you could refer to?
    */
   void sort()
   {
      Card.arraySort(cards, topCard);
   }

   /**
    * return the number of cards remaining in the deck.
    * 
    * @return
    */
   int getNumCards()
   {
      return topCard;
   }

   /*
    * default constructor, sets to one pack
    */
   public Deck()
   {
      this(1);
   }

   /**
    * re-populate cards[] with the standard 56 x numPacks cards.
    * 
    * @param numPacks
    */
   public void init(int numPacks)
   {
      if (numPacks > 0 && numPacks <= 6)
      {
         this.numPacks = numPacks;
         topCard = numPacks * 56;
         cards = new Card[topCard];
         for (int i = 0; i < topCard; i++)
         {
            cards[i] = masterPack[i % 56];
         }
      }
      // what should else case be?
   }

   /**
    * mixes up the cards with the help of the standard random number generator.
    */
   public void shuffle()
   {
      Random rndGenerator = new Random();
      for (int i = cards.length - 1; i > 0; i--)
      {
         int index = rndGenerator.nextInt(i + 1);
         Card card = cards[index];
         cards[index] = cards[i];
         cards[i] = card;
      }
   }

   /**
    * returns and removes the card in the top occupied position of cards[].
    * 
    * @return card
    */
   public Card dealCard()
   {
      if (topCard > 0)
      {
         Card card = cards[topCard - 1];
         topCard--;
         cards = Arrays.copyOf(cards, cards.length - 1);
         return card;
      }
      else
         return null;
   }

   /**
    * @return topCard
    */
   public int getTopCard()
   {
      return topCard;
   }

   /**
    * @param k Accessor for an individual card. Returns a card with errorFlag =
    *           true if k is bad
    */
   public Card inspectCard(int k)
   {
      if (k >= topCard || k < 0)
      {
         Card errorCard = new Card();
         errorCard.set('E', Card.Suit.spades);
         return errorCard;
      }
      else
      {
         return new Card(cards[k].getValue(), cards[k].getSuit());

      }
   }

   /*
    * called by the constructor, but only once. holds master values for all
    * cards in a pack
    */

   private static void allocateMasterPack()
   {
      if (!masterPackLoaded)
      {
         int i = 0;
         for (Card.Suit suit : Card.Suit.values())
         {
            for (char value : Card.validValues)
            {
               masterPack[i] = new Card(value, suit);
               i++;
            }
            masterPackLoaded = true;
         }
      }
   }
}

class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks; // # standard 52-card packs per deck
                         // ignoring jokers or unused cards
   private int numJokersPerPack; // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack; // # cards removed from each pack
   private int numCardsPerHand; // # cards to deal each player
   private Deck deck; // holds the initial full deck and gets
                      // smaller (usually) during play
   private Hand[] hand; // one Hand for each player
   private Card[] unusedCardsPerPack; // an array holding the cards not used
                                      // in the game. e.g. pinochle does not
                                      // use cards 2-8 of any suit

   public CardGameFramework(int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack, Card[] unusedCardsPerPack, int numPlayers,
         int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) // > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if (numCardsPerHand < 1 || numCardsPerHand > numPacks
            * (52 - numUnusedCardsPerPack) / numPlayers)
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck()
   {
      return deck.dealCard();
   }

   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard(unusedCardsPerPack[k]);

      // add jokers
      for (k = 0; k < numPacks; k++)
         for (j = 0; j < numJokersPerPack; j++)
            deck.addCard(new Card('X', Card.Suit.values()[j]));

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard(deck.dealCard());
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1 || cardIndex < 0
            || cardIndex > numCardsPerHand - 1)
      {
         // Creates a card that does not work
         return new Card('M', Card.Suit.spades);
      }

      // return the card played
      return hand[playerIndex].playCard(cardIndex);

   }

   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;

      return hand[playerIndex].takeCard(deck.dealCard());
   }

}
