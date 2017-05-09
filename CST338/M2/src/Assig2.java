
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

	/*
	 * main() is the engine for the slots game. Here the first step is to receive
	 * the bet by calling getBet(). As long as the bet is not 0, the game will be
	 * played. The new TripleString pullString will be pulled resulting in the
	 * three random strings being chosen. getPayMultiplier will then determine the
	 * proper multiplier based on the strings and this will be used to calculate
	 * the winnings of the player. These winnings will then be added to the array
	 * of previous winnings so long as they do not exceed the maximum number of
	 * games. After this the pull and the winnings are displayed. If the user
	 * decides to enter 0, and thus quit the game, or the maximum number of games
	 * is reached, then the total winnings is displayed, the end message is
	 * delivered, and the game is finished.
	 */
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

	/*
	 * getBet() outputs instructions and asks for the betting amount from the
	 * user. The bet must be between 1 and 100 to run, or 0 to quit. getBet()
	 * checks whether the number is between 0 and 100, and will repeatedly ask
	 * the user for proper input if they continue to enter incorrectly. Once a
	 * number between 0 and 100 is entered this int is returned as the bet.
	 */
	public static int getBet()
	{
		String message = "Enter your bet amount in dollars. ( 1 to 100 ) or 0 " + "to quit";
		String errorMessage = "Error: input must be a positive integer between " + "1 and 100.";
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

	/*
	 * pull() creates a new TripleString and calls randString() 3 times to set
	 * the three strings. pull() takes no arguments but returns the new
	 * TripleString.
	 */
	public static TripleString pull()
	{
		TripleString ts = new TripleString();

		ts.setString1(randString());
		ts.setString2(randString());
		ts.setString3(randString());

		return ts;
	}

	/*
	 * display() takes a TripleString and int as its parameters and returns
	 * nothing. Its purpose is to print out thePull in string format and to let
	 * the user know if they have won, and how much they have won, or if they
	 * have lost.
	 */
	public static void display(TripleString thePull, int winnings)
	{

		System.out.println(thePull.toString());
		if (winnings == 0)
			System.out.println("Sorry, looks like you lost.");
		else
			System.out.println("Congrats, you won: $" + winnings);
	}

	/*
	 * randString() takes no arguments but returns one of four different strings
	 * which will be used to as the output for the slots. These will later be
	 * used to calculate the multiplier, and thus winnings for the pull.
	 */
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

	/*
	 * The getPayMultiplier() method uses a TripleString as a parameter and takes
	 * the three strings from the TripleString to determine which multiplier will
	 * be used for calculating the total winnings. The multiplier is determined
	 * by the order of the strings from thePull. After the multiplier is
	 * determined it is returned as an int. If thePull was not a winning pull
	 * than 0 is returned as the multiplier and the user lost.
	 */
	private static int getPayMultiplier(TripleString thePull)
	{
		String string1 = thePull.getString1();
		String string2 = thePull.getString2();
		String string3 = thePull.getString3();

		if (string1.equals("cherries") && !string2.equals("cherries"))
			return 5;
		else if (string1.equals("cherries") && string2.equals("cherries") && !string3.equals("cherries"))
			return 15;
		else if (string1.equals("cherries") && string2.equals("cherries") && string3.equals("cherries"))
			return 30;
		else if (string1.equals("BAR") && string2.equals("BAR") && string3.equals("BAR"))
			return 50;
		else if (string1.equals("7") && string2.equals("7") && string3.equals("7"))
			return 100;
		return 0;
	}
}

/*
 * The TripleString class has three private String data members initialized to
 * an empty String, two public ints to limit the max length of a String and max
 * number of pulls the user may do, a private array to track winnings and a
 * private int to track the number of pulls. The class contains an empty
 * constructor method, a private boolean method to test the validity of the
 * input String, three accessors to see each member strings, and three public
 * mutators to set them. The public toString() method returns one long string
 * that contains all the member strings, saveWinnings() stores the winnings from
 * each pull in an array, and displayWinnings() prints them at the end of the
 * program run
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

	// TripleString constructor. Creates class object and initiates member
	// strings to empty
	public TripleString()
	{
		string1 = "";
		string2 = "";
		string3 = "";
	}

	// Accepts String parameter and tests that it is shorter than the max
	// length and non-null. If String is valid, returns true, else false
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

	// Accessor. Returns string value of first String member
	public String getString1()
	{
		return string1;
	}

	// Mutator. Receives a String as a parameter from the client and sets first
	// String member to that value. Tests for String validity by call validString
	// and returns false if String is invalid. Else sets value and returns true
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

	// Accessor. Returns string value of second String member
	public String getString2()
	{
		return string2;
	}

	// Mutator. Receives a String as a parameter from the client and sets 2nd
	// String member to that value. Tests for String validity by call validString
	// and returns false if String is invalid. Else sets value and returns true
	public boolean setString2(String string2)
	{
		if (validString(string2))
		{
			this.string2 = string2;
			return true;
		} else
		{
			return false;
		}
	}

	// Accessor. Returns string value of third String member
	public String getString3()
	{
		return string3;
	}

	// Mutator. Receives a String as a parameter from the client and sets third
	// String member to that value. Tests for String validity by call validString
	// and returns false if String is invalid. Else sets value and returns true
	public boolean setString3(String string3)
	{
		if (validString(string3))
		{
			this.string3 = string3;
			return true;
		} else
		{
			return false;
		}
	}

	// Combines the three private String members into one String with spaces
	// and returns it
	public String toString()
	{
		return string1 + "   " + string2 + "   " + string3;
	}

	// Receives int winnings from client. Test that max pulls have not been
	// exceeded. If they have, returns false. Else stores winnings in next
	// array location, increments pulls tracker numPulls, and returns true
	public static boolean saveWinnings(int winnings)
	{
		if (numPulls < MAX_PULLS)
		{
			pullWinnings[numPulls] = winnings;
			numPulls++;
			return true;
		} else
		{
			return false;
		}
	}

	// Returns String message to summarize winnings to the user. returnString
	// is initialized with standard message, then array values are
	// concatenated onto the String while the total winnings are added and
	// stored in totalWinnings. A message and the value of totalWinnings
	// are concatenated last, and returnString is returned to client
	public static String displayWinnings()
	{
		String returnString = "Thanks for playing at the Casino!\n" + "Your individual winnings were:\n";
		int totalWinnings = 0;
		// Loops through pullWinnings array until numPulls is reached.
		// The value at each index is displayed in the String, and added to
		// the total winnings int
		for (int i = 0; i < numPulls; i++)
		{
			int val = pullWinnings[i];
			returnString = returnString.concat(val + " ");
			totalWinnings = totalWinnings + val;
		}
		returnString = returnString + "\nYour total winnings were: $" + totalWinnings;
		return returnString;
	}
}

/*
**********************************************************************
*                         TEST RUN
*Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
5
7   BAR   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
7
space   BAR   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
30
cherries   BAR   BAR
Congrats, you won: $150
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
40
BAR   BAR   BAR
Congrats, you won: $2000
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
50
7   space   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
4
BAR   cherries   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
30
BAR   BAR   space
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
20
cherries   7   cherries
Congrats, you won: $100
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
66
cherries   BAR   cherries
Congrats, you won: $330
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
50
BAR   space   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
55
space   BAR   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
39
cherries   7   space
Congrats, you won: $195
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
90
space   BAR   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
29
BAR   BAR   BAR
Congrats, you won: $1450
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
34
cherries   BAR   BAR
Congrats, you won: $170
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
23
BAR   BAR   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
22
cherries   7   BAR
Congrats, you won: $110
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
66
BAR   BAR   BAR
Congrats, you won: $3300
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
90
7   cherries   space
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
58
BAR   space   space
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
69
BAR   cherries   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
90
BAR   cherries   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
58
BAR   cherries   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
44
BAR   BAR   BAR
Congrats, you won: $2200
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
22
BAR   7   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
34
cherries   cherries   cherries
Congrats, you won: $1020
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
43
7   cherries   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
45
BAR   cherries   space
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
65
cherries   7   cherries
Congrats, you won: $325
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
76
cherries   cherries   space
Congrats, you won: $1140
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
32
BAR   BAR   BAR
Congrats, you won: $1600
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
34
BAR   space   BAR
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
56
cherries   BAR   BAR
Congrats, you won: $280
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
-1
Error: input must be a positive integer between 1 and 100.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
93
BAR   BAR   BAR
Congrats, you won: $4650
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
58
7   BAR   space
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
69
cherries   BAR   BAR
Congrats, you won: $345
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
03
BAR   cherries   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
83
BAR   cherries   cherries
Sorry, looks like you lost.
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
77
cherries   BAR   BAR
Congrats, you won: $385
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
93
cherries   BAR   7
Congrats, you won: $465
Enter your bet amount in dollars. ( 1 to 100 ) or 0 to quit
90
You broke the machine! Out of memory!
Thanks for playing at the Casino!
Your individual winnings were:
0 0 150 2000 0 0 0 100 330 0 0 195 0 1450 170 0 110 3300 0 0 0 0 0 2200 0 1020 0 0 325 1140 1600 0 
280 4650 0 345 0 0 385 465 
Your total winnings were: $20215
**********************************************************************
*/