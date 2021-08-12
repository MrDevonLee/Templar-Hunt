package TemplarHunt;

import java.io.*;

/**
 * This class contains a list of high scores and the corresponding names
 * read from a file. It also updates the file whenever a player achieves a
 * new high score.
 *
 * @author Devon Lee
 */
public class HighScoresData
{
   public static final int NUM_HIGH_SCORES_SAVED = 5;

   private final File scoreFile;

   private final String[] names = new String[NUM_HIGH_SCORES_SAVED];
   private final int[] scores = new int[NUM_HIGH_SCORES_SAVED];

   public HighScoresData()
   {
      scoreFile = new File("HighScores.txt");
      String inputLine;
      try
      {
         BufferedReader jin = new BufferedReader(
                                  new FileReader(scoreFile));
         for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
         {
            try
            {
               if((inputLine = jin.readLine()) != null)
               {
                  names[i] = inputLine.substring(0,
                                       inputLine.lastIndexOf(' '));
                  scores[i] = Integer.parseInt(inputLine.substring(
                                      inputLine.lastIndexOf(' ') + 1));
               }
            }
            catch(IOException e)
            {
               System.out.println("An IO error occurred while " +
                                  "reading a line.");
               makeErrorArray();
            }
         }
      }

      catch(Exception e)
      {
         System.out.println("An error occurred while reading a line.");
      }
   }

   /**
    * Populates the high score name and score array with values to make it
    * clear that an error has occurred when trying to read the file. This
    * method should not be used under normal circumstances.
    */
   private void makeErrorArray()
   {
      for(int j = 0; j < NUM_HIGH_SCORES_SAVED; j++)
      {
         names[j] = "N/A";
         scores[j] = Integer.MIN_VALUE;
      }
   }


   /**
    * Gets the name on the high score list of a given index
    *
    * @param index The index of the name desired
    * @return The name corresponding to the given index
    */
   public String getName(int index)
   {
      return names[index];
   }

   /**
    * Gets the high score of a given index
    *
    * @param index The index of the score desired
    * @return The score corresponding to the given index
    */
   public int getScore(int index)
   {
      return scores[index];
   }

   /**
    * Returns the all-time highest score
    *
    * @return The value of the highest score
    */
   public int getHighestScore()
   {
      return scores[0];
   }

   /**
    * Returns whether or not the given score should be on the leaderboard
    *
    * @param score The score to be evaluated
    * @return True of the score should be on the leaderboard, false
    *         otherwise
    */
   public boolean isHighScore(int score)
   {
      return score >= scores[NUM_HIGH_SCORES_SAVED - 1];
   }


   /**
    * Sets the given name and score into the list of high score by bumping
    * each lower score down one slot. The score previously in last place is
    * discarded. If two scores are equal, the newer score takes precedence.
    *
    * @param name The name to be added to the high scores list
    * @param score The score to be added to the high scores list
    */
   public void setHighScore(String name, int score)
   {
      int indexToSet = getPlacementIndex(score);

      if(indexToSet < 4)
         for(int i = NUM_HIGH_SCORES_SAVED - 2; i >= indexToSet; i--)
         {
            names[i + 1] = names[i];
            scores[i + 1] = scores[i];
         }

      names[indexToSet] = name;
      scores[indexToSet] = score;

      StringBuilder updatedHighScores = new StringBuilder();
      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
         updatedHighScores.append(names[i]).append(" ").
                           append(scores[i]).append("\n");

      try
      {
         BufferedWriter jout = new BufferedWriter(
                                   new FileWriter(scoreFile));

         jout.write(updatedHighScores.toString());
         jout.close();
      }
      catch(IOException e)
      {
         System.out.println("An IO error occurred; " +
                            "no changes were saved.");
      }
   }

   /**
    * Gets the index of a potential high score, i.e. where it would fall in
    * the current list of high scores
    *
    * @param score The score to be evaluated
    * @return The index of which the score is greater in the current
    *         scores list or -1 if such a value cannot be found
    */
   private int getPlacementIndex(int score)
   {
      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
         if(score >= scores[i])
            return i;

      return -1;
   }
}

