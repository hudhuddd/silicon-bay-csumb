/*
***********************************
* Alejandro Guzman-Vega
* Andrea Wieland
* Huda Mutwakil
* Brandon Lewis
* CST 338
* Deck of Cards
* Professor Cecil
***********************************
*/

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class Assig3
{
   static Scanner scanner = new Scanner(System.in);

   public static void main(String[] args)
   {
   
      // Test of Card class
      System.out.println("*************** TEST OF CARD CLASS ***************");
      Card aceOfSpades = new Card();
      System.out.println(aceOfSpades.toString());
      assert !aceOfSpades.getErrorFlag();

      Card illegal = new Card('R', Card.Suit.spades);
      System.out.println(illegal.toString());
      assert illegal.getErrorFlag();

      Card jOfClubs = new Card('J', Card.Suit.clubs);
      System.out.println(jOfClubs.toString());
      assert !jOfClubs.getErrorFlag();

      System.out.println("");

      aceOfSpades.set('R', Card.Suit.spades);
      System.out.println(aceOfSpades.toString());
      assert aceOfSpades.getErrorFlag();

      illegal.set('Q', Card.Suit.spades);
      System.out.println(illegal.toString());
      assert !illegal.getErrorFlag();

      System.out.println(jOfClubs.toString());
      assert !jOfClubs.getErrorFlag();

      System.out.println("*************** TEST OF HAND CLASS ***************");
      Card fiveOfHearts = new Card('5', Card.Suit.hearts);
      Card sevenOfHearts = new Card('7', Card.Suit.hearts);
      Card queenOfDiamonds = new Card('Q', Card.Suit.diamonds);
      Card fourOfDiamonds = new Card('4', Card.Suit.diamonds);
      Card jackOfSpades = new Card('J', Card.Suit.spades);

      Hand hand = new Hand();

      System.out.println("****Testing takeCard()****");
      for (int i = 0; true; i++)
      {
         Card cardToAdd = null;
         switch (i % 5)
         {
         case 0:
            cardToAdd = fiveOfHearts;
            break;
         case 1:
            cardToAdd = sevenOfHearts;
            break;
         case 2:
            cardToAdd = queenOfDiamonds;
            break;
         case 3:
            cardToAdd = fourOfDiamonds;
            break;
         case 4:
            cardToAdd = jackOfSpades;
            break;
         }
         if (!hand.takeCard(cardToAdd))
         {
            System.out.println("Hand full");
            break;
         }
      }
      System.out.println("****Testing hand.toString() when full****");
      System.out.println(hand.toString());

      System.out.println("****Testing inspectCard()****");
      System.out.println(hand.inspectCard(0).toString());
      System.out.println(hand.inspectCard(1).toString());
      System.out.println(hand.inspectCard(-1).toString());
      System.out.println(hand.inspectCard(10000).toString());

      System.out.println("****Testing playCard()****");

      while (hand.getNumCards() > 0)
      {
         Card playCard = hand.playCard();
         System.out.println(playCard.toString());
      }
      System.out.println("****Testing hand.toString() when empty****");
      System.out.println(hand.toString());

      System.out.println("*************** TEST OF DECK CLASS ***************");
      System.out.println("****Dealing Cards****");
      Deck deck = new Deck(2);
      while (deck.getTopCard() > 0)
      {
         Card playCard = deck.dealCard();
         System.out.println(playCard.toString());
      }
      System.out.println("****Dealing Cards after shuffle****");
      deck.init(2);
      deck.shuffle();
      while (deck.getTopCard() > 0)
      {
         Card playCard = deck.dealCard();
         System.out.println(playCard.toString());
      }
      
            
      Deck cardDeck = new Deck(1);
      
      int players = getPlayers();

      Hand[] gameHands = new Hand[players];
      //initialize hands array
      for ( int i = 0; i < gameHands.length; i++)
         gameHands[i] = new Hand( ); 
      
      //deal and display unshuffled deck
      gameHands = dealPlayers(gameHands, cardDeck, players);
      System.out.println("Here are our hands, from unshuffled deck:\n");
      showHands(gameHands);
      
      //reset deck and hands
      cardDeck.init(1);
      for (int i = 0; i < gameHands.length; i++)
          gameHands[i].resetHand();   
      
      //shuffle, re-deal, re-print
      cardDeck.shuffle();
      gameHands = dealPlayers(gameHands, cardDeck, players);
      System.out.println("Here are our hands, from shuffled deck:\n");
      showHands(gameHands);      
   }
   
   
   /*Accepts hand array, drawing deck, and number of players. loops
    * through each hand object in array, drawing card, until there are
    * no deck cards left. returns Hand array 
    */
   public static Hand[] dealPlayers(Hand[] gameHands, Deck cardDeck, int players){
	   int n = 0;
	   while (cardDeck.getTopCard() > 0){
	      if (n < players){
	         gameHands[n].takeCard(cardDeck.dealCard());
	         n++;
	         if (n == players)
	            n = 0;
	      }
	   } 
	   return gameHands;
   }
   
   /*
    * Displays all hands in Hand array, looping through array and then through
    * each card in array, calling inspectCard() and toString() to print
    */
   public static void showHands(Hand[] gameHands){
	   for (int i = 0; i < gameHands.length; i++){
	      System.out.println("Hand:\n");
	      String handString = "";
	      for (int k = 0; k < gameHands[i].getNumCards(); k++){
	         handString += gameHands[i].inspectCard(k).toString();
	      	 handString += ", ";
	      }
	      System.out.println(handString + "\n");
	   }
   }
   
   /*
    * Asks for number of players. Validates correct answer and returns int
    */
   public static int getPlayers()
   {
      String message = "Enter number of players (1 to 10):";
      String errorMessage = "Error: enter number from 1 to 10";
      int players = -1;

      while (players <= 0 || players >= 10)
      {
         System.out.println(message);
         players = scanner.nextInt();
         if (players >= 0 && players <= 10)
            break;
         else
            System.out.println(errorMessage);
      }
      return players;
   }
}

/**
 *
 */
class Card
{
   /**
   *
   */
   public enum Suit
   {
      clubs, diamonds, hearts, spades;
   }

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
      if (this.suit == card.suit && this.value == card.value)
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

   public boolean setErrorFlag(boolean errorFlag)
   {
      this.errorFlag = errorFlag;
      return true;
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
    * 
    */
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * 
    */
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * @return
    */
   public int getNumCards()
   {
      return numCards;
   }

   /**
    * @param card
    * @return
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
    * @return
    */
   public Card playCard()
   {
      numCards--;
      Card card = myCards[numCards];
      return card;
   }

   /**
    * @return
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
    * @param k
    * @return
    */
   public Card inspectCard(int k)
   {
      if (k < numCards && k >= 0)
      {
         return new Card(myCards[k].getValue(), myCards[k].getSuit());
      } else
      {
         Card errorCard = new Card();
         errorCard.setErrorFlag(true);
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

   /**
    * @param numPacks
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      this.numPacks = numPacks;
      topCard = numPacks * 52;
      cards = new Card[topCard];
      for (int i = 0; i < topCard; i++)
      {
         cards[i] = masterPack[i % 52];
      }

   }

   /*
    * 
    */
   public Deck()
   {
      this(1);
   }

   /**
    * @param numPacks
    */
   public void init(int numPacks)
   {
      this.numPacks = numPacks;
      topCard = numPacks * 52;
      cards = new Card[topCard];
      for (int i = 0; i < topCard; i++)
      {
         cards[i] = masterPack[i % 52];
      }
   }

   /**
    * 
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
    * @return card
    */
   public Card dealCard()
   {
      Card card = cards[topCard - 1];
      topCard--;
      cards = Arrays.copyOf(cards, cards.length - 1);
      return card;
   }

   /**
    * @return
    */
   public int getTopCard()
   {
      return topCard;
   }

   /**
    * @param k
    * @return
    */
   public Card inspectCard(int k)
   {
      if (k >= topCard || k < 0)
      {
         Card card = new Card();
         card.setErrorFlag(true);
         return card;
      } else
      {
         return new Card(cards[k].getValue(), cards[k].getSuit());

      }
   }

   
   
   private static void allocateMasterPack()
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
      }
   }
}

/*
Output:
*************** TEST OF CARD CLASS ***************
A of spades
** illegal **
J of clubs

** illegal **
Q of spades
J of clubs
*************** TEST OF HAND CLASS ***************
****Testing takeCard()****
Hand full
****Testing hand.toString() when full****
Hand = ( 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts, Q of diamonds, 4 of diamonds, J of spades, 5 of hearts, 7 of hearts )
****Testing inspectCard()****
5 of hearts
7 of hearts
** illegal **
** illegal **
****Testing playCard()****
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
J of spades
4 of diamonds
Q of diamonds
7 of hearts
5 of hearts
****Testing hand.toString() when empty****
Hand = (  )
*************** TEST OF DECK CLASS ***************
****Dealing Cards****
K of spades
Q of spades
J of spades
T of spades
9 of spades
8 of spades
7 of spades
6 of spades
5 of spades
4 of spades
3 of spades
2 of spades
A of spades
K of hearts
Q of hearts
J of hearts
T of hearts
9 of hearts
8 of hearts
7 of hearts
6 of hearts
5 of hearts
4 of hearts
3 of hearts
2 of hearts
A of hearts
K of diamonds
Q of diamonds
J of diamonds
T of diamonds
9 of diamonds
8 of diamonds
7 of diamonds
6 of diamonds
5 of diamonds
4 of diamonds
3 of diamonds
2 of diamonds
A of diamonds
K of clubs
Q of clubs
J of clubs
T of clubs
9 of clubs
8 of clubs
7 of clubs
6 of clubs
5 of clubs
4 of clubs
3 of clubs
2 of clubs
A of clubs
K of spades
Q of spades
J of spades
T of spades
9 of spades
8 of spades
7 of spades
6 of spades
5 of spades
4 of spades
3 of spades
2 of spades
A of spades
K of hearts
Q of hearts
J of hearts
T of hearts
9 of hearts
8 of hearts
7 of hearts
6 of hearts
5 of hearts
4 of hearts
3 of hearts
2 of hearts
A of hearts
K of diamonds
Q of diamonds
J of diamonds
T of diamonds
9 of diamonds
8 of diamonds
7 of diamonds
6 of diamonds
5 of diamonds
4 of diamonds
3 of diamonds
2 of diamonds
A of diamonds
K of clubs
Q of clubs
J of clubs
T of clubs
9 of clubs
8 of clubs
7 of clubs
6 of clubs
5 of clubs
4 of clubs
3 of clubs
2 of clubs
A of clubs
****Dealing Cards after shuffle****
3 of clubs
5 of diamonds
8 of spades
T of clubs
2 of clubs
2 of diamonds
A of clubs
8 of diamonds
3 of spades
7 of hearts
6 of clubs
3 of hearts
6 of spades
J of clubs
3 of hearts
7 of spades
A of hearts
5 of clubs
6 of hearts
T of spades
J of hearts
8 of spades
8 of clubs
A of spades
J of diamonds
2 of hearts
Q of spades
K of clubs
4 of clubs
5 of diamonds
J of spades
Q of spades
5 of hearts
4 of clubs
7 of spades
3 of spades
K of clubs
T of diamonds
8 of hearts
A of diamonds
2 of diamonds
J of clubs
2 of spades
6 of hearts
A of diamonds
7 of diamonds
5 of spades
4 of spades
9 of clubs
6 of diamonds
Q of clubs
7 of hearts
T of hearts
J of diamonds
T of clubs
8 of hearts
5 of spades
8 of diamonds
T of diamonds
A of clubs
9 of hearts
A of hearts
4 of spades
2 of spades
Q of clubs
K of spades
8 of clubs
4 of hearts
K of hearts
9 of hearts
5 of hearts
7 of diamonds
4 of hearts
6 of diamonds
7 of clubs
Q of diamonds
6 of spades
9 of spades
Q of hearts
2 of clubs
9 of spades
9 of diamonds
9 of diamonds
9 of clubs
K of diamonds
K of spades
J of spades
3 of clubs
T of hearts
K of diamonds
Q of hearts
3 of diamonds
4 of diamonds
5 of clubs
6 of clubs
2 of hearts
4 of diamonds
T of spades
K of hearts
A of spades
J of hearts
3 of diamonds
7 of clubs
Q of diamonds
Enter number of players (1 to 10):
7
Here are our hands, from unshuffled deck:

Hand:

K of spades, 6 of spades, Q of hearts, 5 of hearts, J of diamonds, 4 of diamonds, T of clubs, 3 of clubs, 

Hand:

Q of spades, 5 of spades, J of hearts, 4 of hearts, T of diamonds, 3 of diamonds, 9 of clubs, 2 of clubs, 

Hand:

J of spades, 4 of spades, T of hearts, 3 of hearts, 9 of diamonds, 2 of diamonds, 8 of clubs, A of clubs, 

Hand:

T of spades, 3 of spades, 9 of hearts, 2 of hearts, 8 of diamonds, A of diamonds, 7 of clubs, 

Hand:

9 of spades, 2 of spades, 8 of hearts, A of hearts, 7 of diamonds, K of clubs, 6 of clubs, 

Hand:

8 of spades, A of spades, 7 of hearts, K of diamonds, 6 of diamonds, Q of clubs, 5 of clubs, 

Hand:

7 of spades, K of hearts, 6 of hearts, Q of diamonds, 5 of diamonds, J of clubs, 4 of clubs, 

Here are our hands, from shuffled deck:

Hand:

8 of hearts, 6 of diamonds, Q of spades, K of clubs, A of diamonds, 2 of hearts, 2 of clubs, 3 of hearts, 

Hand:

T of diamonds, K of hearts, 6 of spades, 7 of diamonds, 8 of clubs, 7 of clubs, 2 of spades, J of clubs, 

Hand:

3 of clubs, 9 of spades, 6 of clubs, 8 of diamonds, 6 of hearts, 9 of diamonds, 4 of clubs, Q of hearts, 

Hand:

4 of diamonds, 7 of hearts, 9 of clubs, 9 of hearts, 5 of diamonds, J of diamonds, 2 of diamonds, 

Hand:

K of diamonds, 4 of hearts, 5 of clubs, 3 of diamonds, K of spades, 4 of spades, 5 of spades, 

Hand:

Q of clubs, A of hearts, 8 of spades, T of spades, 5 of hearts, T of hearts, A of clubs, 

Hand:

T of clubs, A of spades, J of hearts, 7 of spades, Q of diamonds, J of spades, 3 of spades, 


*/
