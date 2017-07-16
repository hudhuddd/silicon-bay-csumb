
/*
 ***********************************
 * Alejandro Guzman-Vega
 * Andrea Wieland
 * Huda Mutwakil
 * Brandon Lewis
 * CST 338
 * Timed High Card Game
 * Professor Cecil
 ***********************************
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Assig6
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static Timer timer;

   public static void main(String[] args)
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      // create new build game and deal the hands
      BuildGame build = new BuildGame(numPacksPerDeck, numJokersPerPack,
            numUnusedCardsPerPack, unusedCardsPerPack, NUM_CARDS_PER_HAND);
      build.deal();

      // import the card gifs
      GUICard.loadCardIcons();

      // pull the first two cards
      HandTable myCardTable = new HandTable("CardTable", NUM_CARDS_PER_HAND,
            NUM_PLAYERS, build);
      myCardTable.setSize(1200, 700);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      timer = new Timer(myCardTable);

      myCardTable.setVisible(true);
   }
}

/**
 ********** VIEW **********
 */
class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2; // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlTimer, pnlComputerHand, pnlHumanHand, pnlPlayArea,
         centerPanel, pnlScoreBoard;

   /**
    * The constructor filters input, adds any panels to the JFrame, and
    * establishes layouts according to the general description below.
    * 
    * @param title The title for the window
    * @param numCardsPerHand the number of cards per hand
    * @param numPlayers the number of players
    */
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      filterTitle(title);
      this.numCardsPerHand = filterNumCardsPerHand(numCardsPerHand);
      this.numPlayers = filternumPlayers(numPlayers);

      pnlComputerHand = new JPanel(new GridLayout(1, 1, 10, 10));
      pnlHumanHand = new JPanel(new GridLayout(1, 1, 10, 10));
      pnlPlayArea = new JPanel(new GridLayout(2, 2, 20, 20));
      pnlTimer = new JPanel();
      pnlScoreBoard = new JPanel();
      centerPanel = new JPanel(new BorderLayout());

      setLayout(new BorderLayout(10, 10));
      add(pnlComputerHand, BorderLayout.NORTH);
      add(pnlHumanHand, BorderLayout.SOUTH);
      add(centerPanel, BorderLayout.CENTER);
      add(pnlTimer, BorderLayout.EAST);
      add(pnlScoreBoard, BorderLayout.WEST);

      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      centerPanel.setBorder(new TitledBorder("Playing Area"));
      pnlTimer.setBorder(new TitledBorder("Timer"));
      pnlScoreBoard.setBorder(new TitledBorder("Score Board"));
   }

   /**
    * Verify input, if invalid do error correction and return a valid input.
    * 
    * @param numPlayers2 the unverified number of players
    * @return an int will be returned. If input was valid then it will be
    *         returned otherwise some int will be selected.
    */
   private int filternumPlayers(int numPlayers2)
   {
      if (numPlayers2 != MAX_PLAYERS)
         return MAX_PLAYERS;
      return numPlayers2;
   }

   /**
    * Verify input, if invalid do error correction and return a valid input.
    * 
    * @param numCardsPerHand2 the unverified number of cards per hand
    * @return an int will be returned. If input was valid then it will be
    *         returned otherwise some int will be selected.
    */
   private int filterNumCardsPerHand(int numCardsPerHand2)
   {
      if (numCardsPerHand <= 0 || numCardsPerHand > MAX_CARDS_PER_HAND)
         return 5;
      return numCardsPerHand2;
   }

   /**
    * Verify input, if invalid do error correction and return a valid input.
    * 
    * @param title the unverified title.
    * @return a string will be returned. If input was valid then it will be
    *         returned, otherwise some default string is returned.
    */
   private String filterTitle(String title)
   {
      if (title.length() == 0)
         return "GUI Card Game";
      return title;
   }

   /**
    * Get the number of cards per hand.
    * 
    * @return the numCardsPerHand
    */
   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   /**
    * Get the number of players
    * 
    * @return the numPlayers
    */
   public int getNumPlayers()
   {
      return numPlayers;
   }
}

/*
 * extends CardTable to allow it to be responsive to changes fired from the
 * game.
 */
class HandTable extends CardTable
{
   static JLabel[] computerLabels;
   static CardButton[] humanLabels;
   private BuildGame build;
   ScoreBoard scoreBoard = new ScoreBoard();

   // constructor when no played cards are received and play area should be
   // empty
   public HandTable(String title, int numCardsPerHand, int numPlayers,
         BuildGame build)
   {
      super(title, numCardsPerHand, numPlayers);
      this.build = build;
      addHandPanels(computerLabels, humanLabels, build);
      addPlayPanel(build.peekTopCardStack1(), build.peekTopCardStack2());
   }

   // receives the two played cards, stores them in labels, and adds icon labels
   // and text labels to center play area
   public void addPlayPanel(Card stack1Card, Card stack2Card)
   {
      Icon tempIcon;
      tempIcon = GUICard.getIcon(stack1Card);
      StackButton stack1Label = new StackButton(tempIcon, build, 1);
      JLabel stack1Text = new JLabel("Stack 1", JLabel.CENTER);

      tempIcon = GUICard.getIcon(stack2Card);
      StackButton stack2Label = new StackButton(tempIcon, build, 2);
      JLabel stack2Text = new JLabel("Stack 2", JLabel.CENTER);

      pnlPlayArea.add(stack1Label);
      pnlPlayArea.add(stack2Label);
      pnlPlayArea.add(stack1Text);
      pnlPlayArea.add(stack2Text);

      JLabel instructions = new JLabel(
            "First click the stack to add to, then play your card");
      JButton noMoves = new JButton("I can't play any cards");
      noMoves.addActionListener(new noMovesListener(build, this));

      centerPanel.add(instructions, BorderLayout.NORTH);
      centerPanel.add(pnlPlayArea, BorderLayout.CENTER);
      centerPanel.add(noMoves, BorderLayout.SOUTH);

      pnlScoreBoard.add(scoreBoard);
   }

   // receives arrays of labels(computer) or CardButtons(player) and displays
   // them in the "hands" areas of the card table GUI
   public void addHandPanels(JLabel[] computerLabels, CardButton[] humanLabels,
         BuildGame build)
   {
      int k;
      int numComputerCards = build.getHand(0).getNumCards();
      int numPlayerCards = build.getHand(1).getNumCards();
      computerLabels = new JLabel[numComputerCards];
      humanLabels = new CardButton[numPlayerCards];

      Icon tempIcon;
      // CREATE LABELS ----------------------------------------------------
      for (k = 0; k < numComputerCards; k++)
      {
         // add back of card icon to all computer hand and add to computer hand
         // panel
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         this.pnlComputerHand.add(computerLabels[k]);
         this.pnlComputerHand.add(computerLabels[k]);
      }
      for (k = 0; k < numPlayerCards; k++)
      {
         tempIcon = GUICard.getIcon(build.getHand(1).inspectCard(k));
         humanLabels[k] = new CardButton(tempIcon, k, this, build);
         this.pnlHumanHand.add(humanLabels[k]);
      }

   }

   private void addScoreBoard()
   {
      scoreBoard.updateScore(0, build.computerSkips);
      scoreBoard.updateScore(1, build.playerSkips);
      pnlScoreBoard.add(scoreBoard);
   }

   // resets play area display
   private void clearPlayArea()
   {
      pnlPlayArea.removeAll();
   }

   // resets computer hand area display
   private void clearComputerHand()
   {
      pnlComputerHand.removeAll();
   }

   // resets player hand area display
   private void clearPlayerHand()
   {
      pnlHumanHand.removeAll();
   }

   private void clearScoreBoard()
   {
      pnlScoreBoard.removeAll();
   }

   public void clearTable()
   {
      clearPlayArea();
      clearComputerHand();
      clearPlayerHand();
      clearScoreBoard();
   }

   public void updateTable()
   {
      clearTable();
      addPlayPanel(build.peekTopCardStack1(), build.peekTopCardStack2());
      addHandPanels(computerLabels, humanLabels, build);
      addScoreBoard();
      revalidate();
      repaint();
   }
}

class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K +
   // joker
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   /**
    * 
    */
   public static void loadCardIcons()
   {
      if (!iconsLoaded)
      {
         String relativePath = "./images/";
         String extension = ".gif";
         for (int j = 0; j <= 3; j++)
         {
            for (int k = 0; k <= 13; k++)
            {
               ImageIcon image = new ImageIcon(
                     relativePath + turnIntIntoCardValue(k)
                           + turnIntIntoCardSuit(j) + extension);
               iconCards[k][j] = image;
            }
         }
         iconBack = new ImageIcon(relativePath + "BK" + extension);
         iconsLoaded = true;
      }
   }

   /**
    * Given an int return a String representing a card value
    * 
    * @param k int that represents a card value
    * @return A string representing a card value A, 2, 3, ... Q, K, X
    */
   private static String turnIntIntoCardValue(int k)
   {
      if (k < 0 || k > 13)
      {
         throw new IllegalArgumentException(
               "k is outside the range of card values, k: " + k);
      }
      String[] values =
      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
      return values[k];
   }

   /**
    * Given an int return a String representing a card suit
    * 
    * @param k int that represents a card suit
    * @return A string representing a card suit C, D, H, S
    */
   private static String turnIntIntoCardSuit(int j)
   {
      if (j < 0 || j > 3)
      {
         throw new IllegalArgumentException(
               "j is outside the range of card values, j: " + j);
      }
      String[] suits =
      { "C", "D", "H", "S" };
      return suits[j];
   }

   /**
    * Given a card return it's icon.
    * 
    * @param card A card object for which we can an icon
    * @return An icon object
    */
   public static Icon getIcon(Card card)
   {
      loadCardIcons();

      int theSuit = -1;
      Suit suit = card.getSuit();
      switch (suit)
      {
      case clubs:
         theSuit = 0;
         break;
      case diamonds:
         theSuit = 1;
         break;
      case hearts:
         theSuit = 2;
         break;
      case spades:
         theSuit = 3;
         break;
      }

      int theValue = -1;
      char value = card.getValue();
      if (Character.getNumericValue(value) >= 2
            && Character.getNumericValue(value) <= 9)
      {
         theValue = Character.getNumericValue(value) - 1;
      }
      else
      {
         switch (value)
         {
         case 'A':
            theValue = 0;
            break;
         case 'T':
            theValue = 9;
            break;
         case 'J':
            theValue = 10;
            break;
         case 'Q':
            theValue = 11;
            break;
         case 'K':
            theValue = 12;
            break;
         case 'X':
            theValue = 13;
            break;
         }
      }

      return iconCards[theValue][theSuit];
   }

   /**
    * Get the icon for the back of cards
    * 
    * @return An icon object representing the back of cards
    */
   public static Icon getBackCardIcon()
   {
      loadCardIcons();
      return iconBack;
   }
}

class StackButton extends JButton
{
   StackButton(Icon icon, BuildGame build, int stackIndex)
   {
      super(icon);
      addActionListener(new StackListener(build, stackIndex));
   }
}

class StackListener implements ActionListener
{
   private BuildGame build;
   private int stackIndex;

   StackListener(BuildGame build, int stackIndex)
   {
      this.build = build;
      this.stackIndex = stackIndex;
   }

   public void actionPerformed(ActionEvent e)
   {
      build.setStackIndex(stackIndex);
   }
}

class CardButton extends JButton
{
   CardButton(Icon icon, int cardIndex, HandTable table, BuildGame build)
   {
      super(icon);
      addActionListener(new CardListener(cardIndex, build, table));
   }
}

class CardListener implements ActionListener
{
   private HandTable table;
   private int cardIndex;
   private BuildGame build;

   // receives and stores all the parameters for actionPerformed()
   // functionality
   public CardListener(int cardIndex, BuildGame build, HandTable table)
   {
      this.build = build;
      this.cardIndex = cardIndex;
      this.table = table;
   }

   // upon receiving action event, triggers game logic, stores altered hand
   // data, and updates and refreshes game table GUI. player always plays
   // their card first, but semi-random behavior from the computer player
   // ensures that the computer won't always win
   public void actionPerformed(ActionEvent e)
   {
      // clears play areas so cards dont keep adding on
      table.clearTable();

      if (build.getStackIndex() == -1)
      {
         AnnouncementBox error = new AnnouncementBox(
               "Error: first choose a stack");
      }
      else
      {
         GameState gameState = build.playerPlayCard(cardIndex,
               build.getStackIndex());

         if (gameState == GameState.PLAYER_AND_COMPUTER_PLAYED)
         {
            new AnnouncementBox("The computer moved as well");
            table.updateTable();
         }
         else if (gameState == GameState.COMPUTER_PLAYED)
         {
            new AnnouncementBox("The computer played");
            table.updateTable();
         }
         else if (gameState == GameState.PLAYER_PLAYED)
         {
            table.updateTable();
         }
         else if (gameState == GameState.SKIPPED)
         {
            new AnnouncementBox("You both skipped");
            table.updateTable();
         }
         else if (gameState == GameState.ILLEGAL_MOVE)
         {
            AnnouncementBox error = new AnnouncementBox(
                  "You cannot place that card there");
            table.updateTable();
         }
         else if (gameState == GameState.COMPUTER_WON)
         {
            AnnouncementBox error = new AnnouncementBox("The computer WON");
         }
         else if (gameState == GameState.PLAYER_WON)
         {
            AnnouncementBox winner = new AnnouncementBox(
                  "Congratulations, you WON");
         }
      }
      build.setStackIndex(-1);
      table.updateTable();
   }
}

class noMovesListener implements ActionListener
{
   BuildGame build;
   HandTable table;

   noMovesListener(BuildGame build, HandTable table)
   {
      this.build = build;
      this.table = table;
   }

   public void actionPerformed(ActionEvent e)
   {
      table.clearTable();
      GameState gameState = build.playerPlayCard(-1, build.getStackIndex());
      if (gameState == GameState.PLAYER_AND_COMPUTER_PLAYED)
      {
         new AnnouncementBox("The computer moved as well");
         table.updateTable();
      }
      else if (gameState == GameState.COMPUTER_PLAYED)
      {
         new AnnouncementBox("The computer played");
         table.updateTable();
      }
      else if (gameState == GameState.PLAYER_PLAYED)
      {
         table.updateTable();
      }
      else if (gameState == GameState.SKIPPED)
      {
         new AnnouncementBox("You both skipped");
         table.updateTable();
      }
      else if (gameState == GameState.ILLEGAL_MOVE)
      {
         AnnouncementBox error = new AnnouncementBox(
            "You cannot place that card there");
         table.updateTable();
      }
      else if (gameState == GameState.COMPUTER_WON)
      {
         AnnouncementBox error = new AnnouncementBox("The computer WON");
      }
      else if (gameState == GameState.PLAYER_WON)
      {
         AnnouncementBox winner = new AnnouncementBox(
            "Congratulations, you WON");
      }
      table.updateTable();
   }
}

class AnnouncementBox
{
   static int HEIGHT = 200;
   static int WIDTH = 300;
   private JFrame window;
   private JLabel text;

   // constructor
   public AnnouncementBox(String input)
   {
      window = new JFrame();
      window.setSize(WIDTH, HEIGHT);
      window.setLocationRelativeTo(null);
      window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

      window.setLayout(new BorderLayout(10, 10));

      text = new JLabel(input);
      text.setHorizontalAlignment(SwingConstants.CENTER);
      text.setVerticalAlignment(SwingConstants.CENTER);
      window.add(text, BorderLayout.CENTER);
      JButton endButton = new JButton("OK");
      EndingListener buttonEar = new EndingListener(window);
      endButton.addActionListener(buttonEar);
      endButton.setHorizontalAlignment(SwingConstants.CENTER);
      endButton.setVerticalAlignment(SwingConstants.CENTER);
      window.add(endButton, BorderLayout.SOUTH);
      window.setVisible(true);

   }

   // on button click, closes window
   class EndingListener implements ActionListener
   {
      JFrame window;

      EndingListener(JFrame window)
      {
         this.window = window;
      }

      public void actionPerformed(ActionEvent e)
      {
         window.dispose();
      }
   }
}

/**
 ********** MODEL **********
 */
/**
 * array of constants for suit values
 */
enum Suit
{
   clubs, diamonds, hearts, spades;
}

class Hand
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

   public Card playCard(int cardIndex)
   {
      if (numCards == 0) // error
      {
         // Creates a card that does not work
         return new Card('M', Suit.spades);
      }
      // Decreases numCards.
      Card card = myCards[cardIndex];
      numCards--;
      for (int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i + 1];
      }
      myCards[numCards] = null;
      return card;
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

class Deck
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

class Card implements Comparable<Card>
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

/**
 ********** CONTROLLER **********
 */
enum GameState
{
   ILLEGAL_MOVE, VALID_MOVE, PLAYER_WON, COMPUTER_WON, SKIPPED, PLAYER_AND_COMPUTER_PLAYED, PLAYER_PLAYED, COMPUTER_PLAYED;
}

class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks; // # standard 52-card packs per deck
   // ignoring jokers or unused cards
   private int numJokersPerPack; // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack; // # cards removed from each pack
   private int numCardsPerHand; // # cards to deal each player
   private Deck deck; // holds the initial full deck and gets
   // smaller (usually) during play
   private Hand[] hand; // one Hand for each player
   private Card[] unusedCardsPerPack; // an array holding the cards not used
   // in the game. e.g. pinochle does not
   // use cards 2-8 of any suit

   public CardGameFramework(int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack, Card[] unusedCardsPerPack, int numPlayers,
         int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) // > 1
                                                                   // card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if (numCardsPerHand < 1 || numCardsPerHand > numPacks
            * (52 - numUnusedCardsPerPack) / numPlayers)
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck()
   {
      return deck.dealCard();
   }

   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard(unusedCardsPerPack[k]);

      // add jokers
      for (k = 0; k < numPacks; k++)
         for (j = 0; j < numJokersPerPack; j++)
            deck.addCard(new Card('X', Suit.values()[j]));

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard(deck.dealCard());
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   public Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1 || cardIndex < 0
            || cardIndex > numCardsPerHand - 1)
      {
         // Creates a card that does not work
         return new Card('M', Suit.spades);
      }

      // return the card played
      return hand[playerIndex].playCard(cardIndex);

   }

   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;

      return hand[playerIndex].takeCard(deck.dealCard());
   }

}

class BuildGame extends CardGameFramework
{

   Card[] cardStack1 = new Card[1000];
   int cardStackIndex1 = -1;
   Card[] cardStack2 = new Card[1000];
   int cardStackIndex2 = -1;
   public Hand computerHand;
   private int stackIndex = -1;

   /**
    * @return the computerHand
    */
   public Hand getComputerHand()
   {
      return computerHand;
   }

   public boolean setStackIndex(int index)
   {
      if (index == 1 || index == 2)
      {
         stackIndex = index;
         return true;
      }
      else if (index == -1)// to check for errors where a stack hasn't been
                           // chosen
      {
         stackIndex = index;
         return true;
      }
      else
      {
         System.out.println("Stack index error");
      }
      return false;
   }

   public int getStackIndex()
   {
      return stackIndex;
   }

   /**
    * @return the playerHand
    */
   public Hand getPlayerHand()
   {
      return playerHand;
   }

   public Hand playerHand;
   public int playerSkips;// *********same as below
   public int computerSkips;// *****************************change this back to
                            // private and make an accessor*********

   public BuildGame(int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
         int numCardsPerHand)
   {
      super(numPacks, numJokersPerPack, numUnusedCardsPerPack,
            unusedCardsPerPack, 2, numCardsPerHand);
      this.computerHand = super.getHand(0);
      this.playerHand = super.getHand(1);
      Card cardFromDeckForStack1 = getCardFromDeck();
      cardStackIndex1++;
      cardStack1[cardStackIndex1] = cardFromDeckForStack1;
      Card cardFromDeckForStack2 = getCardFromDeck();
      cardStackIndex2++;
      cardStack2[cardStackIndex2] = cardFromDeckForStack2;
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
         if (playerSkips <= computerSkips)
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
         return GameState.SKIPPED;
      }
      if (didComputerPlay && didPlayerPlay)
      {
         return GameState.PLAYER_AND_COMPUTER_PLAYED;
      }
      else if (didPlayerPlay)
      {
         return GameState.PLAYER_PLAYED;
      }
      else
      {
         return GameState.COMPUTER_PLAYED;
      }
   }

   private boolean computerPlayCard()
   {
      boolean computerAble = isComputerAbleToPlayCard();
      if (computerAble)
      {
         for (int i = 0; i < computerHand.getNumCards(); i++)
         {
            boolean validStack1 = validMoveStack1(computerHand.inspectCard(i));
            if (validStack1)
            {
               cardStackIndex1++;
               cardStack1[cardStackIndex1] = computerHand.playCard(i);
               computerHand.takeCard(getCardFromDeck());
               return true;
            }
            boolean validStack2 = validMoveStack2(computerHand.inspectCard(i));
            if (validStack2)
            {
               cardStackIndex2++;
               cardStack2[cardStackIndex2] = computerHand.playCard(i);
               computerHand.takeCard(getCardFromDeck());
               return true;
            }
         }
         return false;
      }
      else
      {
         computerSkips++;
         return false;
      }
   }

   private boolean isComputerAbleToPlayCard()
   {
      for (int i = 0; i < computerHand.getNumCards(); i++)
      {
         boolean validStack1 = validMoveStack1(computerHand.inspectCard(i));
         boolean validStack2 = validMoveStack2(computerHand.inspectCard(i));
         if (validStack1 || validStack2)
         {
            return true;
         }
      }
      return false;
   }
}

/*
 * class defines the display and methods for graphic timer to run in a card
 * table. calling object or program must have a handtable (or child class)
 * object with a JPanel pnlTimer data member, as this class will modify the
 * handtable display to update the graphic timer. table must also have
 * clearTimerArea() and addTimerPanel() methods
 */
class Timer extends Thread
{
   private JButton startstop = new JButton("Start / Stop");
   public TimerFace face;
   public HandTable table;
   private boolean running = false;
   private int seconds = 0;
   private static int NUM_DIGITS = 10;
   private static Icon[] clockNums = new ImageIcon[NUM_DIGITS];// holds num
                                                               // icons
   private static Icon dots;// holds ':' icon
   private static boolean iconsLoaded = false; // ensures icons are only loaded
                                               // once
   private static int FACE_WIDTH = 5; // 5 icons on clock face
   private static JLabel[] timerPanels = new JLabel[FACE_WIDTH];// panels that
                                                                // hold the
                                                                // icons
   private static int clockBrain[] =
   { 0, 0, 0, 0, 0 }; // controls the calculation

   Timer(HandTable table)
   {
      this.table = table;
      Timer.loadClockIcons();
      face = new TimerFace(table);
      TimerListener listener = new TimerListener();
      startstop.addActionListener(listener);
   }

   private static void loadClockIcons()
   {
      String num;
      if (!iconsLoaded)
      {
         String relativePath = "./clocknums/";
         String extension = ".png";
         for (int j = 0; j < NUM_DIGITS; j++)
         {
            num = Integer.toString(j);
            ImageIcon image = new ImageIcon(relativePath + num + extension);
            clockNums[j] = image;
         }
         dots = new ImageIcon(relativePath + "X" + extension);
         iconsLoaded = true;
      }
   }

   private void updateClock(HandTable table)
   {
      if (seconds == 5999)
         running = false; // ensures clock can't run over what fits on the clock
                          // face
      if (running)
         clockBrain[4]++;// so clock does not advance if start has been pressed
      if (clockBrain[4] == 10)
      {
         clockBrain[4] = 0;
         clockBrain[3]++;
      }
      if (clockBrain[3] == 6)
      {
         clockBrain[3] = 0;
         clockBrain[1]++;
      }
      if (clockBrain[1] == 10)
      {
         clockBrain[1] = 0;
         clockBrain[0]++;
      }

      face.updateClockFace(table, clockBrain);
   }

   // pauses thread for 999 milliseconds before updating clock face
   private void doNothing(int milliseconds)
   {
      try
      {
         Thread.sleep(milliseconds);
      }
      catch (InterruptedException e)
      {
         System.out.println("Unexpected interrupt");
         System.exit(0);
      }
   }

   public void run()
   {
      while (seconds < 1800) // max time on timer is 30 minutes
      {
         if (running)
         {
            doNothing(999);
            updateClock(table);
            seconds++;
         }
         else
         {
            updateClock(table);
            break;
         }
      }
   }

   public void setRunningFalse()
   {
      running = false;
   }

   /*
    * creates an action listener for the start/stop button. if button has never
    * been clicked, it fires run and changes clicked to true if clicked
    * again(while click is true), it changes clicked to false so that run()
    * breaks out of the update loop
    */
   private class TimerListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (!running)
         {
            running = true;
            start();
         }
         else
            running = false;// causes run to break out of loop
      }
   }

   /*
    * TimerFace extends JPanel and handles all updates to the clock GUI display
    */
   private class TimerFace extends JPanel
   {
      JPanel timerFace = new JPanel(new GridLayout(2, 1));
      JPanel numbers;

      // constructor loads the clock face onto the calling HandTable
      TimerFace(HandTable table)
      {
         loadDisplay(table);
      }

      // creates the clock face jpanel and fills it with all 0 icons
      // adds it to the passed HandTable
      private void loadDisplay(HandTable table)
      {

         numbers = new JPanel(new FlowLayout());
         timerFace.setSize(100, 50);

         for (int i = 0; i < FACE_WIDTH; i++)
         {
            if (i != 2)
               timerPanels[i] = new JLabel(clockNums[0]);
            else
               timerPanels[i] = new JLabel(dots);
            numbers.add(timerPanels[i]);
         }
         timerFace.add(numbers);
         timerFace.add(startstop);
         addTimerPanel(table);
      }

      // clears the timer face, updates icon to reflect the updated
      // clockbrain values, clears and replaces the timer on the
      // playing table
      private void updateClockFace(HandTable table, int[] clock)
      {
         timerFace.removeAll();
         numbers.removeAll();
         for (int i = 0; i < FACE_WIDTH; i++)
         {
            if (i != 2)
               timerPanels[i] = new JLabel(clockNums[clock[i]]);
            numbers.add(timerPanels[i]);
         }

         timerFace.add(numbers);
         if (running)
            timerFace.add(startstop);

         clearTimerArea(table);
         addTimerPanel(table);
         table.revalidate();
         table.repaint();
      }

      // receives the HandTable object and adds the timerFace to the
      // designated timer panel
      public void addTimerPanel(HandTable table)
      {
         table.pnlTimer.add(timerFace);
      }

      // resets timer area display on playing table
      private void clearTimerArea(HandTable table)
      {
         table.pnlTimer.removeAll();
      }
   }
}

class ScoreBoard extends JPanel
{
   private JLabel computerText = new JLabel("Computer misses");
   private JLabel playerText = new JLabel("Your misses");
   private JLabel computerScore;
   private JLabel playerScore;

   ScoreBoard()
   {
      computerScore = new JLabel("00");
      playerScore = new JLabel("00");
      setLayout(new GridLayout(4, 1));
      add(computerText);
      add(computerScore);
      add(playerText);
      add(playerScore);
   }

   public void updateScore(int player, int score)
   {
      this.removeAll();
      String scoreString = Integer.toString(score);
      if (player == 0)
         computerScore = new JLabel(scoreString);
      if (player == 1)
         playerScore = new JLabel(scoreString);

      add(computerText);
      add(computerScore);
      add(playerText);
      add(playerScore);
   }
}
