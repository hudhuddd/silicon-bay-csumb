
/*
***********************************
* Alejandro Guzman-Vega
* Andrea Wieland
* Huda Mutwakil
* Brandon Lewis
* CST 338
* Casino
* Professor Cecil
***********************************
*/

import java.util.Scanner;

public class Assig2
{
   static Scanner scanner = new Scanner(System.in);

   public static void main(String[] args)
   {
      int bet = getBet();
      while (bet != 0)
      {
         TripleString pullString = pull();
         int payMultiplier = getPayMultiplier(pullString);
         int winnings = bet * payMultiplier;
         if (!TripleString.saveWinnings(winnings))
         {
            System.out.println("You broke the machine! Out of memory!");
            break;
         }
         display(pullString, winnings);
         bet = getBet();
      }
      System.out.println(TripleString.displayWinnings());
      scanner.close();
   }

   public static int getBet()
   {
      String message = "Enter your bet amount in dollars. ( 1 to 100 ) or 0 "
            + "to quit";
      String errorMessage = "Error: input must be a positive integer between "
            + "1 and 100.";
      int bet = -1;

      while (bet <= 0 || bet >= 100)
      {
         System.out.println(message);
         bet = scanner.nextInt();
         if (bet >= 0 && bet <= 100)
            break;
         else
            System.out.println(errorMessage);
      }
      return bet;
   }

   public static TripleString pull()
   {
      TripleString ts = new TripleString();

      ts.setString1(randString());
      ts.setString2(randString());
      ts.setString3(randString());

      return ts;
   }

   public static void display(TripleString thePull, int winnings)
   {

      System.out.println(thePull.toString());
      if (winnings == 0)
         System.out.println("Sorry, looks like you lost.");
      else
         System.out.println("Congrats, you won: $" + winnings);
   }

   private static String randString()
   {
      int randomNum = (int) (Math.random() * 1000) + 1;
      String output = "";

      if (randomNum <= 125)
         output = "7";
      else if (randomNum <= 250)
         output = "space";
      else if (randomNum <= 500)
         output = "cherries";
      else
         output = "BAR";

      return output;
   }

   private static int getPayMultiplier(TripleString thePull)
   {
      String string1 = thePull.getString1();
      String string2 = thePull.getString2();
      String string3 = thePull.getString3();

      if (string1.equals("cherries") && !string2.equals("cherries"))
         return 5;
      else if (string1.equals("cherries") && string2.equals("cherries")
            && !string3.equals("cherries"))
         return 15;
      else if (string1.equals("cherries") && string2.equals("cherries")
            && string3.equals("cherries"))
         return 30;
      else if (string1.equals("BAR") && string2.equals("BAR")
            && string3.equals("BAR"))
         return 50;
      else if (string1.equals("7") && string2.equals("7")
            && string3.equals("7"))
         return 100;
      return 0;
   }
}


/*
The TripleString class has three private String data members initialized to an 
empty String, two public ints to limit the max length of a String and max 
number of pulls the user may do, a private array to track winnings and a 
private int to track the number of pulls. The class contains an empty 
constructor method, a private  boolean method to test the validity of the 
input String, three accessors to see each  member strings, and three public
mutators to set them. The public toString() method  returns one long string 
that contains all the member strings, saveWinnings() stores the winnings from
each pull in an array, and displayWinnings() prints them at the end of the 
program run
*/
class TripleString
{
   private String string1;
   private String string2;
   private String string3;

   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;

   private static int[] pullWinnings = new int[40];
   private static int numPulls = 0;

   
//TripleString constructor. Creates class object and initiates member 
//strings to empty
   public TripleString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }

   
//Accepts String parameter and tests that it is shorter than the max 
//length and non-null. If String is valid, returns true, else false
   private boolean validString(String str)
   {
      if (str.length() <= MAX_LEN & str != null)
      {
         return true;
      } else
      {
         return false;
      }
   }

//Accessor. Returns string value of first String member
   public String getString1()
   {
      return string1;
   }

//Mutator. Receives a String as a parameter from the client and sets first
//String member to that value. Tests for String validity by call validString
//and returns false if String is invalid. Else sets value and returns true
   public boolean setString1(String string1)
   {
      if (validString(string1))
      {
         this.string1 = string1;
         return true;
      } else
      {
         return false;
      }
   }

//Accessor. Returns string value of second String member
   public String getString2()
   {
      return string2;
   }

//Mutator. Receives a String as a parameter from the client and sets 2nd
//String member to that value. Tests for String validity by call validString
//and returns false if String is invalid. Else sets value and returns true
   public boolean setString2(String string2)
   {
      if (validString(string2))
      {
         this.string2 = string2;
         return true;
      } 
      else
      {
         return false;
      }
   }

//Accessor. Returns string value of third String member
   public String getString3()
   {
      return string3;
   }

//Mutator. Receives a String as a parameter from the client and sets third
//String member to that value. Tests for String validity by call validString
//and returns false if String is invalid. Else sets value and returns true
   public boolean setString3(String string3)
   {
      if (validString(string3))
      {
         this.string3 = string3;
         return true;
      }
      else
      {
         return false;
      }
   }

//Combines the three private String members into one String with spaces 
//and returns it
   public String toString()
   {
      return string1 + "   " + string2 + "   " + string3;
   }

   
//Receives int winnings from client. Test that max pulls have not been 
//exceeded. If they have, returns false. Else stores winnings in next
//array location, increments pulls tracker numPulls, and returns true
   public static boolean saveWinnings(int winnings)
   {
      if (numPulls < MAX_PULLS)
      {
         pullWinnings[numPulls] = winnings;
         numPulls++;
         return true;
      } 
      else
      {
         return false;
      }
   }

   
//Returns String message to summarize winnings to the user. returnString
//is initialized with standard message, then array values are 
//concatenated onto the String while the total winnings are added and
//stored in totalWinnings. A message and the value of totalWinnings
//are concatenated last, and returnString is returned to client
   public static String displayWinnings()
   {
      String returnString = "Thanks for playing at the Casino!\n"
            + "Your individual winnings were:\n";
      int totalWinnings = 0;
   //Loops through pullWinnings array until numPulls is reached.
   //The value at each index is displayed in the String, and added to
   //the total winnings int
      for (int i = 0; i < numPulls; i++)
      {
         int val = pullWinnings[i];
         returnString = returnString.concat(val + " ");
         totalWinnings = totalWinnings + val;
      }
      returnString = returnString + "\nYour total winnings were: "
            + totalWinnings;
      return returnString;
   }
}
