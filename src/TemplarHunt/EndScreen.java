package TemplarHunt;

import TemplarHunt.HighScoresData;
import TemplarHunt.MenuScreen;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static TemplarHunt.GUI.STAGE_SIZE;
import static TemplarHunt.HighScoresData.NUM_HIGH_SCORES_SAVED;

/**
 * Child of MenuScreen that displays the statistics achieved that game
 *
 * @author Devon Lee
 */
public class EndScreen extends MenuScreen
{
   private static final double BACKGROUND_OPACITY = 1.0;
   private static final int NUM_BUTTONS = 3;
   private static final Color[] buttonColors = new Color[]
                                              {QUIT_BUTTON_COLOR,
                                               Color.LIGHTGOLDENRODYELLOW,
                                               Color.MEDIUMSEAGREEN};
   private static final String PLAY_AGAIN_BUTTON_TEXT =
                               "Press \"P\" to Play Again";
   private static final String[] END_SCREEN_TEXT = new String[]
                                                 {QUIT_BUTTON_TEXT,
                                                  TITLE_BUTTON_TEXT,
                                                  PLAY_AGAIN_BUTTON_TEXT};
   private static final Font SCORES_FONT = Font.font("Times New Roman",36);
   private static final int MAX_NAME_LENGTH = 15;

   private final Label subtitle;
   private final Label[] names;
   private final Label[] scores;
   private final Label playerName;
   private final Label playerScore;
   private final TextField nameEntry;

   /**
    * Sets up title text, high score list, buttons, and text field for the
    * end of the game
    *
    * @param data The high score data to be displayed
    * @param pane The pane on which the screen is instantiated
    */
   public EndScreen(HighScoresData data, Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, false, pane);
      background.setFill(Color.PALETURQUOISE);
      title.setText("The End");
      title.setLayoutX(325);
      title.setLayoutY(100);

      subtitle = new Label();
      subtitle.setText("You got a high score! Enter your name below.");
      subtitle.setTextAlignment(TextAlignment.CENTER);
      subtitle.setFont(Font.font("Times New Roman", 24));
      subtitle.setLayoutX(170);
      subtitle.setLayoutY(160);
      pane.getChildren().add(subtitle);
      subtitle.setVisible(false);

      playerName = new Label();
      playerName.setText("You:");
      playerName.setTextAlignment(TextAlignment.CENTER);
      playerName.setFont(SCORES_FONT);
      playerName.setLayoutX(200);
      playerName.setLayoutY(500);
      pane.getChildren().add(playerName);

      playerScore = new Label();
      playerScore.setTextAlignment(TextAlignment.CENTER);
      playerScore.setFont(SCORES_FONT);
      playerScore.setLayoutX(STAGE_SIZE - 200 - 60);
      playerScore.setLayoutY(500);
      pane.getChildren().add(playerScore);

      nameEntry = new TextField();
      nameEntry.setFont(SCORES_FONT);
      nameEntry.setMaxWidth(250);
      nameEntry.setLayoutX(275);
      nameEntry.setLayoutY(490);
      pane.getChildren().add(nameEntry);

      names = new Label[NUM_HIGH_SCORES_SAVED];
      scores = new Label[NUM_HIGH_SCORES_SAVED];

      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i] = new Label();
         names[i].setTextAlignment(TextAlignment.CENTER);
         names[i].setFont(SCORES_FONT);
         names[i].setLayoutX(200);
         names[i].setLayoutY(200 + 60 * i);
         pane.getChildren().add(names[i]);

         scores[i] = new Label();
         scores[i].setTextAlignment(TextAlignment.CENTER);
         scores[i].setFont(SCORES_FONT);
         scores[i].setLayoutX(STAGE_SIZE - 200 - 60);
         scores[i].setLayoutY(200 + 60 * i);
         pane.getChildren().add(scores[i]);
      }

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(buttonColors[i]);
         buttonLabels.get(i).setText(END_SCREEN_TEXT[i]);
      }

      updateHighScores(data);
   }


   /**
    * Gets the name the player entered for themselves upon achieving a high
    * score
    *
    * @return The name of the player, maximum of 15 characters
    */
   public String getName()
   {
      String name = nameEntry.getText();
      if(name.length() > MAX_NAME_LENGTH)
         name = name.substring(0, MAX_NAME_LENGTH);

      return name;
   }


   /**
    * Updates the score listing of the screen from a score data object
    *
    * @param data An object containing information about the high scores
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
    * Hides the player name text entry box and reacquires the newly-updated
    * high score information
    *
    * @param data An object containing information about the high scores
    */
   public void embedHighScore(HighScoresData data)
   {
      updateHighScores(data);
      playerName.setVisible(false);
      playerScore.setVisible(false);
      nameEntry.setVisible(false);
   }


   /**
    * Hides the end screen
    */
   public void hide()
   {
      super.hide();
      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i].setVisible(false);
         names[i].toFront();
         scores[i].setVisible(false);
         scores[i].toFront();
      }
      subtitle.setVisible(false);
      playerName.setVisible(false);
      playerScore.setVisible(false);
      nameEntry.setVisible(false);
   }

   /**
    * Shows the end screen
    *
    * @param gotHighScore Whether or not a high score was achieved, used to
    *                     display a text box for name entry or not
    * @param score The score acheived by the player
    */
   public void show(boolean gotHighScore, int score)
   {
      super.show();
      for(int i = 0; i < NUM_HIGH_SCORES_SAVED; i++)
      {
         names[i].setVisible(true);
         names[i].toFront();
         scores[i].setVisible(true);
         scores[i].toFront();
      }

      playerName.setVisible(true);
      playerName.toFront();
      playerScore.setVisible(true);
      playerScore.toFront();
      playerScore.setText(String.valueOf(score));

      if(gotHighScore)
      {
         subtitle.setVisible(true);
         subtitle.toFront();
         nameEntry.setText("");
         nameEntry.setVisible(true);
         nameEntry.toFront();
      }
   }
}
