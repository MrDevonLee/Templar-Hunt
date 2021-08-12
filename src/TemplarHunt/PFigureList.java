package TemplarHunt;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static TemplarHunt.GUI.STAGE_SIZE;

/**
 * This class contains all of the elements of a round of the game including
 * the player, scoreboard, enemies, and zones.
 */
public class PFigureList
{
   public static final int NUM_ZONES = 9;

   private static final int[] ENEMIES_IN_ROUND = new int[]
                                                     {3, 3, 4, 4, 5, 6, 6};
   private static final int[] SPECIAL_ENEMIES_IN_ROUND = new int[]
                                                     {0, 0, 0, 1, 1, 1, 2};
   private static final int RANDOM_ITERATION_MAX = 100000;

   private final Player player;
   private final Scoreboard scoreboard;
   private final ArrayList<Enemy> enemies = new ArrayList<>();
   private final Zone[] zones = new Zone[NUM_ZONES];

   /**
    * Constructor that creates the player and NPCs for the round; the
    * number of figures created depends on the round
    *
    * Dirt file obtained from deviantart.com
    *
    * @param roundID The ID of the current round
    * @param pane The pane on which the figures of the list are
    *             instantiated
    */
   public PFigureList(int roundID, Pane pane)
   {
      //Background for round
      try
      {
            ImageView background = new ImageView("file:Dirt.jpg");
            background.setX(0);
            background.setY(0);
            background.setOpacity(0.7);
            background.setFitHeight(STAGE_SIZE);
            background.setFitWidth(STAGE_SIZE);
            pane.getChildren().add(background);
      }
      catch(Exception e)
      {
         System.out.println("Could not find background image: " + e);
      }

      player = new Player(pane);
      scoreboard = new Scoreboard(roundID, pane);

      for(int i = 0; i < ENEMIES_IN_ROUND[roundID - 1]; i++)
         enemies.add(new Enemy(false, pane));
      for(int i = 0; i < SPECIAL_ENEMIES_IN_ROUND[roundID - 1]; i++)
         enemies.add(new Enemy(true, pane));
      enemies.trimToSize();

      for(int i = 0; i < zones.length; i++)
         zones[i] = new Zone((i % 3) * (STAGE_SIZE / 3),
                             (i / 3) * (STAGE_SIZE / 3),
                              i % 3 == 1, i % 2 != 0,
                              roundID, pane);

      randomizeStartPositions();
   }

   /**
    * Uses random number to start the various figures at pseudo-random
    * points on the board for a more interesting game experience
    */
   private void randomizeStartPositions()
   {
      int iterations;

      iterations = (int) (Math.random() * RANDOM_ITERATION_MAX);
      for(int i = 0; i < iterations; i++)
         scoreboard.move();

      for(int i = 0; i < enemies.size(); i++)
      {
         iterations = (int) (Math.random() * RANDOM_ITERATION_MAX);
         for(int j = 0; j < iterations; j++)
            enemies.get(i).move();
      }
   }

   /**
    * Gets the player
    *
    * @return The player object of the current round
    */
   public Player getPlayer()
   {
      return player;
   }

   /**
    * Gets the scoreboard object
    *
    * @return The scoreboard object of the current round
    */
   public Scoreboard getScoreboard()
   {
      return scoreboard;
   }

   /**
    * Gets the scoreboard object
    *
    * @param index The index of the enemy desired
    * @return The scoreboard object of the current round
    */
   public Enemy getEnemy(int index)
   {
      return enemies.get(index);
   }

   /**
    * Returns the zones array
    *
    * @return The array of zones for the round
    */
   public Zone[] getZones()
   {
      return zones;
   }

   /**
    * Returns the number of enemies (regular and special) present in the
    * round
    *
    * @return The number of enemies in the round
    */
   public int getNumEnemies()
   {
      return enemies.size();
   }

   /**
    * Performs moving animation for all figures by hiding them, moving them
    * according to their various algorithms, and then showing them again.
    * All parameters are used for drawing the scoreboard
    *
    * @param clock The time left in the round
    * @param roundScore The score achieved in the current round
    * @param totalScore The score achieved in the current game
    * @param highScore The highest score achieved in all games
    */
   public void hideMoveDrawAll(int clock, int roundScore,
                               int totalScore, int highScore)
   {
      for(int i = 0; i < enemies.size(); i++)
      {
         enemies.get(i).hide();
         enemies.get(i).move();
         enemies.get(i).draw();
         enemies.get(i).toFront();
      }

      for(int i = 0; i < zones.length; i++)
      {
         zones[i].hide();
         zones[i].move();
         zones[i].draw();
      }

      scoreboard.hide();
      scoreboard.move();
      scoreboard.drawScoreboard(clock, roundScore, totalScore, highScore);
      scoreboard.toFront();

      player.toFront();
   }

   /**
    * Resets the static logic variables for the enemies and zones
    */
   public void resetFigCount()
   {
      Enemy.resetFigureAndTagCount();
      Zone.resetNumZones();
   }
}
