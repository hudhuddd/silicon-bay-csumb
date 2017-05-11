import java.util.Scanner;

public class Assig3
{
   static Scanner scanner = new Scanner(System.in);
   
   public static void main(String[] args)
   {
      
   }
   
   public static class Card
   {
      public enum Suit
      {
         clubs, diamonds, hearts, spades;
      }
      
      private char value;
      private Suit suit;
      private boolean errorFlag;
      
      public Card(char value, Suit suit)
      {
         /*The constructor should call the proper mutator(s).  
          *Overload this to cope with a client that wants to instantiate without
          *parameters and use 'A' and 'spades' as the default value and suit when not supplied.  
          *Provide at least two constructors -- no parameters and all parameters -- 
          *or more if you wish.  Because we have the errorFlag member, the constuctor 
          *(via the mutator), can set that member when it gets bad data; 
          *it does not have to assign default values upon receipt of bad data.
          *This is a new technique for us.
          *Again, default card (no parameters passed) is the ('A', spades).
          */
      }
      
      public String toString()
      {
         /* a stringizer that the client can use prior to displaying the card
          * It provides a clean representation of the card.
          * If errorFlag == true, it should return correspondingly reasonable reflection of this fact 
          * (something like "[ invalid ]" rather than a suit and value).
          */
      }
      
      public boolean set(char value, Suit suit)
      {
         /* a mutator that accepts the legal values established in the earlier section.
          * When bad values are passed, errorFlag is set to true and other values can be left
          * in any state (even partially set).
          * If good values are passed, they are stored and errorFlag is set to false.
          * Make use of the private helper, listed below.
         */
         return errorFlag;
      }
      
      public char getValue()
      {
         return value;
      }
      
      public Suit getSuit()
      {
         return suit;
      }
      
      public boolean getErrorFlag()
      {
         return errorFlag;
      }
      
      public boolean equals(Card card)
      {
         // returns true if all the fields (members) are identical and false, otherwise.
         return errorFlag;
      }
      
      private boolean isValid(char value, Suit suit)
      {
         /* a private helper method that returns true or false, 
          * depending on the legality of the parameters. 
          * Note that, although it may be impossible for suit to be illegal 
          * (due to its enum-ness), we pass it, anyway, in anticipation of possible 
          * changes to the type from enum to, say, char or int, someday.  
          * We only need to test value, at this time.
          */
         return errorFlag;
      }
   }
   
   public class Hand
   {
      
   }
   
   public class Deck
   {
      
   }

}
