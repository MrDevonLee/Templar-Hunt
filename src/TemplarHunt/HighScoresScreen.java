package TemplarHunt;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static TemplarHunt.GUI.STAGE_SIZE;
import static TemplarHunt.HighScoresData.NUM_HIGH_SCORES_SAVED;

/**
 * Child of MenuScreen that provides a list of the highest scores obtained
 * in the game
 *
 * @author Devon Lee
 */
public class HighScoresScreen extends MenuScreen
{
   private static final double BACKGROUND_OPACITY = 1.0;
   private static final int NUM_BUTTONS = 1;

   private final Label[] names;
   private final Label[] scores;

   /**
    * Sets the layout for the screen where high scores are shown
    *
    * @param pane The pane on which the screen is instantiated
    */
   public HighScoresScreen(Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, false, pane);
      background.setFill(Color.GHOSTWHITE);
      title.setText("High Scores");
      title.setLayoutX(300);
      title.setLayoutY(100);

      names = new Label[NUM_HIGH_SCORES_SAVED];
      scores = new Label[NUM_HIGH_SCORES_SAVED];

      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i] = new Label();
         names[i].setTextAlignment(TextAlignment.CENTER);
         names[i].setFont(Font.font("Times New Roman", 36));
         names[i].setLayoutX(200);
         names[i].setLayoutY(260 + 60 * i);
         pane.getChildren().add(names[i]);

         scores[i] = new Label();
         scores[i].setTextAlignment(TextAlignment.CENTER);
         scores[i].setFont(Font.font("Times New Roman", 36));
         scores[i].setLayoutX(STAGE_SIZE - 200 - 60);
         scores[i].setLayoutY(260 + 60 * i);
         pane.getChildren().add(scores[i]);
      }

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(QUIT_BUTTON_COLOR);
         buttonLabels.get(i).setText(BACK_BUTTON_TEXT);
      }
   }

   /**
    * Updates the high score table
    *
    * @param data An object containing the list of the high scores data
    */
   public void updateHighScores(HighScoresData data)
   {
      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i].setText((i + 1) + ". " + data.getName(i));
         scores[i].setText(String.valueOf(data.getScore(i)));
      }
   }

   /**
    * Hides the high scores page
    */
   public void hide()
   {
      super.hide();

      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i].setVisible(false);
         scores[i].setVisible(false);
      }
   }

   /**
    * Shows the high scores page
    */
   public void show()
   {
      super.show();

      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i].setVisible(true);
         names[i].toFront();
         scores[i].setVisible(true);
         scores[i].toFront();
      }
   }
}
