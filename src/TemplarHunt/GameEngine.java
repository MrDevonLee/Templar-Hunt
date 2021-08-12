package TemplarHunt;

import java.util.Timer;

import static TemplarHunt.GUI.NUM_ROUNDS;

/**
 * This class controls all of the non-GUI logic for the game, including the
 * round timer, scoring, and the distinction between rounds. It also
 * records all-time high scores to a file and provides that information in
 * the game.
 *
 * @author Devon Lee
 */
public class GameEngine
{
   public static final int[] ROUND_SCORE_THRESHOLDS = new int[]
                                                     {200, 200, 250,
                                                      300, 350, 400,
                                                      Integer.MIN_VALUE};
   private static final int ENEMY_SCORE = 20;
   private static final int SPECIAL_ENEMY_MULTIPLIER = 2;
   private static final int SIGN_SCORE = 1;
   private static final int CHEAT_POINTS = 10;
   private static final int ROUND_TIME_SEC = 60;
   private static final int SEC_TO_MILLISEC = 1000;
   private static final int COOL_DOWN_TIME = 700;
   public static final int IMMUNITY_TIME = 1500;

   private static int roundID = 0;
   private static int totalScore = 0;

   private int roundScore;
   private int clock_ms;
   private long coolDownTimer;

   private Timer roundTimer;
   protected TimerHelper decrementClock;
   private static boolean cheatModeOverride = false;

   /**
    * Creates a new game engine; designed to be created very round because
    * roundID increments with each new engine
    */
   public GameEngine()
   {
      roundID++;
      roundScore = 0;
      clock_ms = ROUND_TIME_SEC * SEC_TO_MILLISEC;
      roundTimer = new Timer();
      decrementClock = new TimerHelper(clock_ms);
   }


   /**
    * Gets the number of the current round
    *
    * @return The number of the current round
    */
   public int getRoundID()
   {
      return roundID;
   }

   /**
    * Returns the remaining time on the clock, rounded down to a whole
    * number of seconds
    *
    * @return The time remaining (in seconds) of the round
    */
   public int getTimeRemaining()
   {
      clock_ms = decrementClock.getClock();
      return clock_ms / SEC_TO_MILLISEC;
   }

   /**
    * Gets the points earned in the current round
    *
    * @return The points scored in this round
    */
   public int getRoundScore()
   {
      return roundScore;
   }

   /**
    * Gets the points earned in the current game
    *
    * @return The points scored in this game
    */
   public int getTotalScore()
   {
      return totalScore;
   }

   /**
    * Gets the minimum number of points the player must earn this round in
    * order to advance onto the next round
    *
    * @param roundID The round of which to return the score threshold
    * @return The score threshold for the given round
    */
   public static int getScoreThreshold(int roundID)
   {
      return ROUND_SCORE_THRESHOLDS[roundID - 1];
   }

   /**
    * Returns if "cheat mode" is currently enables
    *
    * @return True if "cheat mode" is enabled false otherwise
    */
   public boolean getOverrideStatus()
   {
      return cheatModeOverride;
   }

   /**
    * Toggles the "cheat mode" for the game
    */
   public void setOverride()
   {
      cheatModeOverride = !cheatModeOverride;
   }


   /**
    * Starts a countdown of the clock member to time a given round. The
    * TimerTask executes every second
    */
   public void start()
   {
      coolDownTimer = System.currentTimeMillis() + IMMUNITY_TIME;

      // Runs the "run" method of TimerHelper every 1 millisecond, no delay
      roundTimer.schedule(decrementClock, 0, 1);
   }

   /**
    * Pauses the timer of the game; typically called when the game
    * animation has been suspended
    */
   public void pauseTimer()
   {
      roundTimer.cancel();
   }

   /**
    * Resumes the timer of the game
    */
   public void resumeTimer()
   {
      roundTimer = new Timer();
      decrementClock = new TimerHelper(clock_ms);
      start();
   }


   /**
    * Adjusts the round and total score of the player based upon the
    * parameters given
    * @param zoneMultiplier A score multipler dependent on the zones
    *                       associated with the collision
    * @param specialEnemy Whether or not the enemy in question was a
    *                     special enemy
    */
   public void scoreEnemyCollision(int zoneMultiplier,
                                   boolean specialEnemy)
   {
      int collisionScore = ENEMY_SCORE;
      if(specialEnemy)
         collisionScore *= SPECIAL_ENEMY_MULTIPLIER;

      roundScore += collisionScore * zoneMultiplier;
      totalScore += collisionScore * zoneMultiplier;
   }

   /**
    * Adds the value of one sign collision to the score of the player
    */
   public void scoreSignCollision()
   {
      roundScore += SIGN_SCORE;
      totalScore += SIGN_SCORE;
   }

   /**
    * Adds a pre-defined number of points to the score of the player
    */
   public void addCheatPoints()
   {
      roundScore += CHEAT_POINTS;
      totalScore += CHEAT_POINTS;
   }

   /**
    * Subtracts a pre-defined number of points from the score of the player
    */
   public void subtractCheatPoints()
   {
      roundScore -= CHEAT_POINTS;
      totalScore -= CHEAT_POINTS;
   }


   /**
    * Determines if enough time has passed since the cool down counter was
    * last reset
    *
    * @return True if sufficient time has passed; false otherwise
    */
   public boolean sufficientCoolDownTime()
   {
      return System.currentTimeMillis() - coolDownTimer > COOL_DOWN_TIME;
   }

   /**
    * Resets the cool down timer to 0 in game time, or the time of the
    * present moment
    */
   public void resetCoolDownTimer()
   {
      coolDownTimer = System.currentTimeMillis();
   }

   /**
    * Resets the cool down timer to 0 in game time, or the time of the
    * present moment, plus whatever extra time the function call specified
    *
    * @param extraTime An additional buffer (in ms) to add to the reset so
    *                  that the following cool down is longer than normal
    */
   public void resetCoolDownTimer(int extraTime)
   {
      coolDownTimer = System.currentTimeMillis() + extraTime;
   }


   /**
    * Returns if the round is over (i.e. the clock is at zero)
    *
    * @return True if the round is over, false otherwise
    */
   public boolean roundOver()
   {
      return clock_ms < 0;
   }

   /**
    * Returns if the user passed the level (i.e. if the round score is
    * greater than the pre-defined threshold)
    *
    * @return True if the user passed the round, false otherwise
    */
   public boolean passedLevel()
   {
      return !lastRound() && roundScore >=
              ROUND_SCORE_THRESHOLDS[roundID - 1];
   }

   /**
    * Returns if this round is the last round of the game; there are seven
    *
    * @return True if this is the last round, false otherwise
    */
   public boolean lastRound()
   {
      return roundID == NUM_ROUNDS;
   }

   /**
    * Resets the static variables of the class for a new game
    */
   public void resetGame()
   {
      roundID = 0;
      totalScore = 0;
      cheatModeOverride = false;
   }
}