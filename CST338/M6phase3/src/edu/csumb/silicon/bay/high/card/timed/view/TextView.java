package edu.csumb.silicon.bay.high.card.timed.view;

import java.util.Scanner;

import edu.csumb.silicon.bay.high.card.timed.controller.BuildGame;
import edu.csumb.silicon.bay.high.card.timed.controller.GameState;
import edu.csumb.silicon.bay.high.card.timed.model.Card;

public class TextView
{
   BuildGame buildGame;
   static Scanner scanner = new Scanner(System.in);

   public TextView(BuildGame buildGame)
   {
      this.buildGame = buildGame;
      while (true)
      {
         printPlayerCards();
         printStacks();
         int cardIndex = cardInput();
         int stackIndex = 1;
         if(cardIndex != -1) {
            stackIndex = stackInput();
         }
         GameState gameState = buildGame.playerPlayCard(cardIndex, stackIndex);
         if (gameState == GameState.VALID_MOVE)
         {
            System.out.println("That was a valid move");
         }
         else if (gameState == GameState.ILLEGAL_MOVE)
         {
            System.out.println("That was an illegal move");
         }
         else if (gameState == GameState.COMPUTER_WON)
         {
            System.out.println("The computer WON");
            break;
         }
         else if (gameState == GameState.PLAYER_WON)
         {
            System.out.println("You WON!!");
            break;
         }
      }
   }

   private int stackInput()
   {
      System.out.println("Select a stack to place card, select 1 or 2");
      return scanner.nextInt();
   }

   private int cardInput()
   {
      System.out.println(
            "Select a number card index, between beginning with 0, or -1 to do nothing");
      return scanner.nextInt();
   }

   private void printStacks()
   {
      Card topCard1 = buildGame.peekTopCardStack1();
      Card topCard2 = buildGame.peekTopCardStack2();
      if (topCard1 == null)
      {
         System.out.println("There are no card on stack 1");
      }
      else
      {
         System.out
               .println("The card on top of stack 1 is " + topCard1.toString());
      }
      if (topCard2 == null)
      {
         System.out.println("There are no card on stack 2");
      }
      else
      {
         System.out
               .println("The card on top of stack 2 is " + topCard2.toString());
      }
   }

   private void printPlayerCards()
   {
      System.out.println("Your cards are:");
      System.out.println(buildGame.getPlayerHand().toString());
   }
}
