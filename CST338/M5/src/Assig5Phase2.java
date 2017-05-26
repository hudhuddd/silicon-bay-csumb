
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
import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class Assig5Phase2
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;
      GUICard.loadCardIcons();

      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND,
            NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS ----------------------------------------------------
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         humanLabels[k] = new JLabel(tempIcon);
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         myCardTable.pnlComputerHand.add(computerLabels[k]);
         myCardTable.pnlHumanHand.add(humanLabels[k]);
      }
      
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         playedCardLabels[k] = new JLabel(tempIcon);
         
         if (k > 0)
         {
            playLabelText[k] = new JLabel("You", JLabel.CENTER);
         }
         else
         {
            playLabelText[k] = new JLabel("Computer" + k + 1, JLabel.CENTER);
         }
      }

      // ADD LABELS TO PANELS -----------------------------------------
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         myCardTable.pnlComputerHand.add(computerLabels[k]);
         myCardTable.pnlHumanHand.add(humanLabels[k]);
      }

      // and two random cards in the play region (simulating a computer/hum ply)
      // code goes here ...
      for (k = 0; k < NUM_PLAYERS*2; k++)
      {
         if (k < NUM_PLAYERS)
         {
            myCardTable.pnlPlayArea.add(playedCardLabels[k]);
         }
         else
         {
            myCardTable.pnlPlayArea.add(playLabelText[k - NUM_PLAYERS]);
         }
      }

      // show everything to the user
      myCardTable.setVisible(true);
   }
 

   private static void add(JLabel label1)
   {
      // TODO Auto-generated method stub
      // not sure if this is needed. See lines 140-142 
      
   }


   public static Card generateRandomCard()
   {
      Card.Suit suit = Card.Suit.values()[new Random().nextInt(4)];
      char c = Card.validValues[new Random().nextInt(14)];
      return new Card(c, suit);
   }
}

class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games
   static int DEFAULT_CARDS_PER_HAND = 5;
   static int DEFAULT_PLAYERS = 2;

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
      super(title);
      
      if(numCardsPerHand < 0 || numCardsPerHand > MAX_CARDS_PER_HAND)
      {
         this.numCardsPerHand = DEFAULT_CARDS_PER_HAND;
      }
      else
      {
         this.numCardsPerHand = numCardsPerHand;
      }
      
      if(numPlayers <= 0 || numPlayers > MAX_PLAYERS)
      {
         this.numPlayers = DEFAULT_PLAYERS;
      }
      else
      {
         this.numPlayers = numPlayers;
      }
      
      pnlComputerHand = new JPanel(new GridLayout(1, 1, 10, 10));
      pnlHumanHand = new JPanel(new GridLayout(1, 1, 10, 10));
      pnlPlayArea = new JPanel(new GridLayout(2, 2, 20, 20));
      
      setLayout (new BorderLayout(10, 10));
      add(pnlComputerHand, BorderLayout.NORTH);
      add(pnlHumanHand, BorderLayout.SOUTH);
      add(pnlPlayArea, BorderLayout.CENTER);
      
      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
      
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

class GUICard
{
   public static final int NUM_SUITS = 4;
   public static final int MAX_VALUES = 14;
   
   private static Icon[][] iconCards = new ImageIcon[MAX_VALUES][NUM_SUITS]; // 14 = A thru K +
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
         for (int j = 0; j < NUM_SUITS; j++)
         {
            for (int k = 0; k < MAX_VALUES; k++)
            {
               ImageIcon image = new ImageIcon(
                     relativePath + turnIntIntoCardValue(k)
                           + turnIntIntoCardSuit(j) + extension);
               iconCards[k][j] = image;
            }
            //do something here?
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
      // TODO: Add verification and throw IllegalArgumentException if it fails
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
      // TODO: Add verification and throw IllegalArgumentException if it fails
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
      case diamonds:
         theSuit = 1;
      case hearts:
         theSuit = 2;
      case spades:
         theSuit = 3;
      }

      int theValue = -1;
      char value = card.getValue();
      if (Character.getNumericValue(value) >= 2
            || Character.getNumericValue(value) <= 9)
      {
         theValue = Character.getNumericValue(value);
      }
      else
      {
         switch (value)
         {
         case 'A':
            theValue = 0;
         case 'T':
            theValue = 9;
         case 'J':
            theValue = 10;
         case 'Q':
            theValue = 11;
         case 'K':
            theValue = 12;
         case 'X':
            theValue = 13;
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
   public enum Suit
   {
      clubs, diamonds, hearts, spades;
   }

   /**
    * Array of card face values
    */
   public static char[] validValues =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

   public static char[] valuRanks =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };;
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
      if (thisVal == thatVal)
      {
         return 0;
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

   /**
    * @return card and remove from hand
    */
   public Card playCard()
   {
      if (numCards > 0)
      {
         numCards--;
         Card card = myCards[numCards];
         return card;
      }
      else
      {
         Card errorCard = new Card();
         errorCard.set('E', Card.Suit.spades);
         return errorCard;
      }
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
    * re-populate cards[] with the standard 56 × numPacks cards.
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