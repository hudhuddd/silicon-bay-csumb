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

import java.util.Scanner;

public class Assig3
{
   static Scanner scanner = new Scanner(System.in);

   public static void main(String[] args)
   {

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
      CLUBS, DIAMONDS, HEARTS, SPADES;
   }

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
      set('A', Suit.valueOf("SPADES"));
   }

   /**
    * a stringizer that the client can use prior to displaying the card It
    * provides a clean representation of the card. If errorFlag == true, it
    * should return correspondingly reasonable reflection of this fact
    * (something like "[ invalid ]" rather than a suit and value).
    * @param value
    * @param suit
    * @return
    * 
    */
   public String toString()
   {
      if (errorFlag == false)
      {
         return value + "   " + suit;
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
    * @return errorFlag
    */
   public boolean set(char value, Suit suit)
   {
      
      errorFlag = true;

      return errorFlag;
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
      return false;
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
      return errorFlag;
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
      return false;
   }

   /**
    * @return
    */
   public Card playCard()
   {
      Card card = myCards[numCards];
      numCards--;
      return card;
   }

   /**
    * @return
    */
   public String toString()
   {
      return null;
   }

   /**
    * @param k
    * @return
    */
   public Card inspectCard(int k)
   {
      return null;

   }
}

/**
 *
 */
class Deck
{
   public final int MAX_CARDS = 6 * 52;
   private static Card[] masterPack;

   private Card[] cards;
   private int topCard;
   private int numPacks;

   /**
    * @param numPacks
    */
   public Deck(int numPacks)
   {
      this.numPacks = numPacks;
      topCard = numPacks * 52;
      cards = new Card[topCard];
   }

   /**
    * @param numPacks
    */
   public void init(int numPacks)
   {

   }

   /**
    * 
    */
   public void shuffle()
   {

   }

   /**
    * @return
    */
   public Card dealCard()
   {
      return null;

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
      return null;

   }

   private static void allocateMasterPack()
   {

   }
}
