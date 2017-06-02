package edu.csumb.silicon.bay.high.card.timed.model;

/**
 *
 */
public class Hand
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

   // returns a card at a specific index in the hand and removes it from the
   // array
   public Card playCard(int location)
   {
      if (numCards != 0)
      {
         return removeCard(location);
      }
      else
      {
         Card errorCard = new Card();
         errorCard.set('E', Suit.spades);
         return errorCard;
      }
   }

   // returns a card at an index, replaces that index element with last card
   // in array, sets final array element to null, and decreases hand size
   // by decrementing numCards
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
         errorCard.set('E', Suit.spades);
         return errorCard;
      }
   }
}
