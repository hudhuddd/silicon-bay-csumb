package edu.csumb.silicon.bay.high.card.timed.model;

import java.util.Random;

/**
 * Card objects contain chars for the face value and suit strings. the class
 * contains accessors, mutators toString to display the card values, equals() to
 * check card equivalence, and private helpers methods
 */
public class Card implements Comparable<Card>
{
   /**
    * Array of card face values
    */
   public static char[] validValues =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

   public static char[] valuRanks =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

   public static Suit[] suitRanks =
   { Suit.clubs, Suit.diamonds, Suit.hearts, Suit.spades };

   private char value;
   private Suit suit;
   private boolean errorFlag;

   /**
    * Constructor, that takes a value and a suit and creates a card.
    * 
    * @param value
    * @param suit
    */
   public Card(char theValue, Suit theSuit)
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
      if (thisVal == thatVal)// if values are equal, compare suits
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

   static Card generateRandomCard()
   {
      Suit suit = Suit.values()[new Random().nextInt(4)];
      char c = validValues[new Random().nextInt(14)];
      return new Card(c, suit);
   }

   public int getIntValue()
   {
      int value = 0;
      for (char i : validValues)
      {
         if (i == this.value)
         {
            break;
         }
         else
         {
            value++;
         }
      }
      return value;
   }
}
