package edu.csumb.silicon.bay.high.card.timed.model;

import java.util.Arrays;
import java.util.Random;

/**
*
*/
public class Deck
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
   public boolean addCard(Card newCard)
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
   public boolean removeCard(Card card)
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
   public int getNumCards()
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
         errorCard.set('E', Suit.spades);
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
         for (Suit suit : Suit.values())
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
