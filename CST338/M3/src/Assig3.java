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

   private char value;
   private Suit suit;
   private boolean errorFlag;

   /**
    *
    */
   public enum Suit
   {
      CLUBS, DIAMONDS, HEARTS, SPADES;
   }

   /**
    * @param value
    * @param suit
    */
   public Card(char value, Suit suit)
   {

   }

   /**
    * a stringizer that the client can use prior to displaying the card It
    * provides a clean representation of the card. If errorFlag == true, it
    * should return correspondingly reasonable reflection of this fact
    * (something like "[ invalid ]" rather than a suit and value).
    * 
    */
   public String toString()
   {
      return null;

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
    * @return
    */
   public boolean set(char value, Suit suit)
   {

      return errorFlag;
   }

   /**
    * @return
    */
   public char getValue()
   {
      return value;
   }

   /**
    * @return
    */
   public Suit getSuit()
   {
      return suit;
   }

   /**
    * @return
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
    * @return
    */
   public boolean equals(Card card)
   {
      return errorFlag;
   }

   /**
    * a private helper method that returns true or false, depending on the
    * legality of the parameters. Note that, although it may be impossible for
    * suit to be illegal (due to its enum-ness), we pass it, anyway, in
    * anticipation of possible changes to the type from enum to, say, char or
    * int, someday. We only need to test value, at this time.
    * 
    * @param value
    * @param suit
    * @return
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

}

/**
 *
 */
class Deck
{

}
