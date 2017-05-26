
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

public class Assig5Phase2
{
   public static void main(String[] args)
   {
      // Test for loadicons
      GUICard.getBackCardIcon();
   }

}

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
      // TODO Auto-generated method stub
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
      // TODO Auto-generated method stub
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
      // TODO Auto-generated method stub
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

class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K +
                                                             // joker
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   /**
    * 
    */
   private static void loadCardIcons()
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
   	switch(suit)
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
   	if(Character.getNumericValue(value) >= 2 || Character.getNumericValue(value) <= 9)
   	{
   		theValue = Character.getNumericValue(value);
   	}
   	else
   	{
   		switch(value)
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
 *Card objects contain chars for the face value
 *and suit strings. the class contains accessors, mutators
 *toString to display the card values, equals() to check
 *card equivalence, and private helpers methods
 */
class Card
{
   /**
   *array of constants for suit values
   */
   public enum Suit
   {
      clubs, diamonds, hearts, spades;
   }
   /*
    * Array of card face values
    */
   char[] validValues =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K' };

   private char value;
   private Suit suit;
   private boolean errorFlag;

   /**
    * @param value
    * @param suit
    * @param SPADES
    */

   Card(char theValue, Suit theSuit)
   {
      set(theValue, theSuit);
   }

   //default constructor
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
      } else
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
      } else
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
      } else
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

}

/**
 *
 */
class Hand
{
   public static int MAX_CARDS = 52;
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
    * adds a card to the next available position 
    * in the myCards array
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
      } else
      {
         return false;
      }
   }

   /**
    * @return card and remove from hand
    */
   public Card playCard()
   {
	  if(numCards > 0){
         numCards--;
         Card card = myCards[numCards];
         return card;
	  }else
      {
	     Card errorCard = new Card();
	     errorCard.set('E', Card.Suit.spades);
	     return errorCard;
	  }
   }

   /**
    *  a stringizer that the client can use prior to displaying 
    *  the entire hand.
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
         } else
         {
            returnString = returnString + myCards[i].toString();
         }
      }
      returnString = returnString + " )";
      return returnString;
   }

   /**
    * Accessor for an individual card.  Returns a card 
    * with errorFlag = true if k is bad.
    * @param k
    * @return card
    */
   public Card inspectCard(int k)
   {
      if (k < numCards && k >= 0)
      {
         return new Card(myCards[k].getValue(), myCards[k].getSuit());
      } else
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
   public final int MAX_CARDS = 6 * 52;
   private static Card[] masterPack = new Card[52];

   private Card[] cards;
   private int topCard;
   private int numPacks;
   private static boolean masterPackLoaded = false;

   /**
    *  a constructor that populates the arrays 
    *  and assigns initial values to members
    * @param numPacks
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      init(numPacks);

   }

   /*
    * default constructor, sets to one pack
    */
   public Deck()
   {
      this(1);
   }

   /**
    * re-populate cards[] with the standard 
    * 52 x numPacks cards.
    * @param numPacks
    */
   public void init(int numPacks)
   {
	  if(numPacks > 0 && numPacks <= 6)
	  {
         this.numPacks = numPacks;
         topCard = numPacks * 52;
         cards = new Card[topCard];
         for (int i = 0; i < topCard; i++)
         {
            cards[i] = masterPack[i % 52];
         }
	  }
	  //what should else case be? 
   }

   /**
    * mixes up the cards with the help of the standard 
    * random number generator.
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
    * returns and removes the card in the top occupied 
    * position of cards[].
    * @return card
    */
   public Card dealCard()
   {
      if (topCard > 0){
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
    * @param k
    * Accessor for an individual card.  Returns a card with 
    * errorFlag = true if k is bad
    */
   public Card inspectCard(int k)
   {
      if (k >= topCard || k < 0)
      {
         Card errorCard = new Card();
         errorCard.set('E', Card.Suit.spades);
         return errorCard;
      } else
      {
         return new Card(cards[k].getValue(), cards[k].getSuit());

      }
   }

   
   /*
    * called by the constructor, but only once. holds master values for
    * all cards in a pack
    */

   private static void allocateMasterPack()
   {
	  if(!masterPackLoaded)
	  {
         char[] validValues =
         { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K' };

         int i = 0;
         for (Card.Suit suit : Card.Suit.values())
         {
            for (char value : validValues)
            {
               masterPack[i] = new Card(value, suit);
               i++;
            }
            masterPackLoaded = true;
         }
      }
   }
}
// ****************CODE FROM PHASE 1****************
// static final int NUM_CARD_IMAGES = 57;
// static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
//
// static void loadCardIcons()
// {
// int i = 0;
// String relativePath = "./images/";
// String extension = ".gif";
// for (int j = 0; j <= 3; j++)
// {
// for (int k = 0; k <= 13; k++)
// {
// ImageIcon image = new ImageIcon(
// relativePath + turnIntIntoCardValue(k)
// + turnIntIntoCardSuit(j) + extension);
// icon[i] = image;
// i++;
// }
// }
// icon[i] = new ImageIcon(relativePath + "BK" + extension);
// }
//
// static String turnIntIntoCardValue(int k)
// {
// String[] values =
// { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
// return values[k];
// }
//
// static String turnIntIntoCardSuit(int j)
// {
// String[] suits =
// { "C", "D", "H", "S" };
// return suits[j];
// }
//
// public static void main(String[] args)
// {
// int k;
// // prepare the image icon array
// loadCardIcons();
// // establish main frame in which program will run
// JFrame mainWindow = new JFrame("Card Room");
// mainWindow.setSize(1150, 650);
// mainWindow.setLocationRelativeTo(null);
// mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// // set up layout which will control placement of buttons, etc.
// FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
// mainWindow.setLayout(layout);
// // prepare the image label array
// JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
// for (k = 0; k < NUM_CARD_IMAGES; k++)
// labels[k] = new JLabel(icon[k]);
// for (k = 0; k < NUM_CARD_IMAGES; k++)
// mainWindow.add(labels[k]);
// // show everything to the user
// mainWindow.setVisible(true);
// }
// ****************CODE FROM PHASE 1****************