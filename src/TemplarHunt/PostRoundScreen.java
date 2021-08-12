package TemplarHunt;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static TemplarHunt.GUI.NUM_ROUNDS;

/**
 * Child of MenuScreen that displays the statistics about each round
 *
 * @author Devon Lee
 */
public class PostRoundScreen extends MenuScreen
{
   private static final double BACKGROUND_OPACITY  = 0.9;
   private static final int NUM_BUTTONS = 3;

   private static final String[] ROUND_STAT_TEXT = new String[]
                       {"Your score this round: ",
                        "Score threshold for this round: ",
                        "If you are seeing this, this game has a bug.",
                        "Your score this game: "};
   private static final String ROUND_PASS_TEXT =
                        "You passed this level. Congratulations!";
   private static final String ROUND_FAIL_TEXT =
                        "You did not pass. Try again next time!";
   private static final String ROUND_END_TEXT =
                        "You have finished all the rounds of the game!";

   private static final String[] BUTTON_TEXT = new String[]
                                 {"Press \"Q\" to Quit",
                                  "Press \"T\" to Return to Title Screen",
                                  "Press \"C\" to Continue"};


   private static final Color[] BUTTON_COLORS = new Color[]
                                {QUIT_BUTTON_COLOR,
                                 Color.LIGHTGOLDENRODYELLOW,
                                 Color.MEDIUMSEAGREEN};

   private final Label[] roundStatistics;

   /**
    * Sets up title text, stat list, and buttons for the end of a round
    *
    * @param pane The pane on which the screen is instantiated
    */
   public PostRoundScreen(Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, true, pane);
      background.setFill(Color.LIGHTPINK);
      title.setLayoutX(261);
      title.setLayoutY(100);

      roundStatistics = new Label[4];
      for(int i = 0; i < roundStatistics.length; i++)
      {
         roundStatistics[i] = new Label();
         roundStatistics[i].setTextAlignment(TextAlignment.CENTER);
         roundStatistics[i].setFont(Font.font("Times New Roman", 24));
         roundStatistics[i].setLayoutX(30);
         roundStatistics[i].setLayoutY(300 + 40 * i);
      }

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(BUTTON_COLORS[i]);
         buttonLabels.get(i).setText(BUTTON_TEXT[i]);
      }

   }

   /**
    * Adds all of the elements of the screen to the current pane of the
    * screen, necessary because the pane changes every round
    */
   public void addElementsToCurrentPane()
   {
      super.addElementsToCurrentPane();

      for(int i = 0; i < roundStatistics.length; i++)
      {
         pane.getChildren().add(roundStatistics[i]);
      }
   }

   /**
    * Updates the data fields of the screen
    * @param roundID The current round
    * @param roundScore The score achieved this round
    * @param totalScore The total score achieved this game
    * @param passedLevel If the player passed the level
    */
   public void updateScores(int roundID, int roundScore, int totalScore,
                            boolean passedLevel)
   {
      title.setText("End of Round " + roundID);

      roundStatistics[0].setText(ROUND_STAT_TEXT[0] + roundScore);
      roundStatistics[1].setText(ROUND_STAT_TEXT[1] +
                         GameEngine.getScoreThreshold(roundID));
      if(passedLevel)
      {
         background.setFill(Color.LIGHTGREEN);
         roundStatistics[2].setText(ROUND_PASS_TEXT);
      }
      else
      {
         background.setFill(Color.LIGHTPINK);
         roundStatistics[2].setText(ROUND_FAIL_TEXT);

         buttons.get(0).setVisible(false);
         buttonLabels.get(0).setVisible(false);
         buttons.get(1).setVisible(false);
         buttonLabels.get(1).setVisible(false);
      }
      roundStatistics[3].setText(ROUND_STAT_TEXT[3] + totalScore);

      if(roundID == NUM_ROUNDS)
      {
         background.setFill(Color.LIGHTBLUE);
         roundStatistics[1].setText(ROUND_STAT_TEXT[1] + "none");
         roundStatistics[2].setText(ROUND_END_TEXT);

         buttons.get(0).setVisible(false);
         buttonLabels.get(0).setVisible(false);
         buttons.get(1).setVisible(false);
         buttonLabels.get(1).setVisible(false);
      }

   }

   /**
    * Shows the post-round screen
    */
   public void show()
   {
      super.show();
      for(int i = 0; i < roundStatistics.length; i++)
      {
         roundStatistics[i].setVisible(true);
         roundStatistics[i].toFront();
      }
   }
}
