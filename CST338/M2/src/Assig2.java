
public class Assig2
{

   public static void main(String[] args)
   {
      TripleString tp = new TripleString();
      tp.setString1("Who");
      tp.setString2("Why");
      tp.setString3("Wha");
      for (int i = 0; i <= 100; i++)
      {
         System.out.println(tp.saveWinnings(i));
      }
      System.out.println(tp.displayWinnings());
      System.out.println(tp.toString());
   }
}

class TripleString
{
   private String string1;
   private String string2;
   private String string3;

   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;

   private static int[] pullWinnings = new int[40];
   private static int numPulls = 0;

   public TripleString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }

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

   public String getString1()
   {
      return string1;
   }

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

   public String getString2()
   {
      return string2;
   }

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

   public String getString3()
   {
      return string3;
   }

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

   public String toString()
   {
      return "String 1: " + string1 + ", String 2:  " + string2 + ", String 3: "
            + string3;

   }

   public boolean saveWinnings(int winnings)
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

   public String displayWinnings()
   {
      String returnString = "";
      int totalWinnings = 0;
      for (int i = 0; i < pullWinnings.length; i++)
      {
         int val = pullWinnings[i];
         returnString = returnString + " " + val + " ";
         totalWinnings = totalWinnings + val;
      }
      returnString = returnString + "\nYour total winnings were: "
            + totalWinnings;
      return returnString;
   }
}
