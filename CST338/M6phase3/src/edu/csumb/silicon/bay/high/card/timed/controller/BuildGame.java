package edu.csumb.silicon.bay.high.card.timed.controller;

import edu.csumb.silicon.bay.high.card.timed.model.Card;
import edu.csumb.silicon.bay.high.card.timed.model.Hand;

public class BuildGame extends CardGameFramework
{

   Card[] cardStack1 = new Card[1000];
   int cardStackIndex1 = -1;
   Card[] cardStack2 = new Card[1000];
   int cardStackIndex2 = -1;
   public Hand computerHand;

   /**
    * @return the computerHand
    */
   public Hand getComputerHand()
   {
      return computerHand;
   }

   /**
    * @return the playerHand
    */
   public Hand getPlayerHand()
   {
      return playerHand;
   }

   public Hand playerHand;
   private int playerSkips;
   private int computerSkips;

   public BuildGame(int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
         int numCardsPerHand)
   {
      super(numPacks, numJokersPerPack, numUnusedCardsPerPack,
            unusedCardsPerPack, 2, numCardsPerHand);
      this.computerHand = super.getHand(0);
      this.playerHand = super.getHand(1);
   }

   /**
    * Get a copy of the card on top of stack 1. Return null if there are no
    * cards in the stack.
    * 
    * @return
    */
   public Card peekTopCardStack1()
   {
      if (cardStackIndex1 == -1)
      {
         return null;
      }
      else
      {
         return cardStack1[cardStackIndex1];
      }
   }

   /**
    * Get a copy of the card on top of stack 2. Return null if there are no
    * cards in the stack.
    * 
    * @return
    */
   public Card peekTopCardStack2()
   {
      if (cardStackIndex2 == -1)
      {
         return null;
      }
      else
      {
         return cardStack2[cardStackIndex2];
      }
   }

   /**
    * Compare current playerCard with card on top of stack 1 and see if is a
    * valid move. A valid move is defined as when you can put on a card that is
    * one value higher or one value lower. (6 on a 5 OR 4 on a 5, Q on a J OR T
    * on a J, etc.)
    * 
    * @param playerCard
    * @return true if move is valid and false otherwise.
    */
   public boolean validMoveStack1(Card playerCard)
   {
      if (this.peekTopCardStack1() == null)
      {
         return true;
      }
      else
      {
         int stackVal = this.peekTopCardStack1().getIntValue();
         int playerVal = playerCard.getIntValue();
         if (stackVal == playerVal - 1 || stackVal == playerVal + 1)
         {
            return true;
         }
         else
         {
            return false;
         }

      }
   }

   /**
    * Compare current playerCard with card on top of stack 1 and see if is a
    * valid move. A valid move is defined as when you can put on a card that is
    * one value higher or one value lower. (6 on a 5 OR 4 on a 5, Q on a J OR T
    * on a J, etc.)
    * 
    * @param playerCard
    * @return true if move is valid and false otherwise.
    */
   public boolean validMoveStack2(Card playerCard)
   {
      if (this.peekTopCardStack2() == null)
      {
         return true;
      }
      else
      {
         int stackVal = this.peekTopCardStack2().getIntValue();
         int playerVal = playerCard.getIntValue();
         if (stackVal == playerVal - 1 || stackVal == playerVal + 1)
         {
            return true;
         }
         else
         {
            return false;
         }

      }
   }

   public GameState playerPlayCard(int cardIndex, int stack1or2)
   {
      if (this.getNumCardsRemainingInDeck() <= 0)
      {
         if (playerSkips >= computerSkips)
         {
            return GameState.PLAYER_WON;
         }
         else
         {
            return GameState.COMPUTER_WON;
         }
      }
      boolean didPlayerPlay = false;
      if (cardIndex != -1)
      {
         if (getPlayerHand().inspectCard(cardIndex).getErrorFlag())
         {
            return GameState.ILLEGAL_MOVE;
         }
         Card playCard = getPlayerHand().playCard(cardIndex);
         if (stack1or2 == 1)
         {
            if (validMoveStack1(playCard))
            {
               cardStackIndex1++;
               cardStack1[cardStackIndex1] = playCard;
               getPlayerHand().takeCard(getCardFromDeck());
               didPlayerPlay = true;
            }
            else
            {
               getPlayerHand().takeCard(playCard);
               return GameState.ILLEGAL_MOVE;
            }
         }
         else
         {
            if (validMoveStack2(playCard))
            {
               cardStackIndex2++;
               cardStack2[cardStackIndex2] = playCard;
               getPlayerHand().takeCard(getCardFromDeck());
               didPlayerPlay = true;
            }
            else
            {
               getPlayerHand().takeCard(playCard);
               return GameState.ILLEGAL_MOVE;
            }
         }

      }
      else
      {
         playerSkips++;
         didPlayerPlay = false;
      }
      boolean didComputerPlay = computerPlayCard();
      if (!didPlayerPlay && !didComputerPlay)
      {
         Card cardFromDeckForStack1 = getCardFromDeck();
         cardStackIndex1++;
         cardStack1[cardStackIndex1] = cardFromDeckForStack1;
         Card cardFromDeckForStack2 = getCardFromDeck();
         if (cardFromDeckForStack2 != null)
         {
            cardStackIndex2++;
            cardStack2[cardStackIndex2] = cardFromDeckForStack2;
         }

      }
      return GameState.VALID_MOVE;
   }

   private boolean computerPlayCard()
   {
   	int numCards = computerHand.getNumCards();
   	for (int i = 0; i < numCards; i++)
   	{
   		Card card = computerHand.inspectCard(i);
   		if (validMoveStack2(card))
         {
            cardStackIndex2++;
            cardStack2[cardStackIndex2] = card;
            getComputerHand().takeCard(getCardFromDeck());
            return true;
         }
         else if (validMoveStack1(card))
         {
         	cardStackIndex1++;
         	cardStack1[cardStackIndex1] = card;
         	getComputerHand().takeCard(getCardFromDeck());
         	return true;
         }
   	}
   	computerSkips++;
   	return false;
      }
}
