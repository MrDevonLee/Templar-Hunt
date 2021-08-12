package TemplarHunt;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static TemplarHunt.GUI.STAGE_SIZE;

/**
 * This class controls a game object that also displays statistics about
 * the game including scores and time remaining, and it also serves as a
 * way for the player to score points
 *
 * @author Devon Lee
 */
public class Scoreboard extends PFigure
{
   private static final int WIDTH = 140;
   private static final int HEIGHT = 96;
   private static final int TIMER_BOX_SIZE = 30;

   private static final Font TITLE_FONT = Font.font("Times New Roman", 24);
   private static final Font BODY_FONT = Font.font("Times New Roman", 18);

   private final int roundID;
   private int xVel = 1;
   private int yVel = 1;

   /**
    * Constructor for the scoreboard figure
    *
    * @param roundID The current round of the game; used to display the
    *                round when rendered
    * @param p The pane on which this figure is instantiated
    */
   public Scoreboard(int roundID, Pane p)
   {
      super((int) (STAGE_SIZE * Math.random()),
            (int) (STAGE_SIZE * Math.random()),  WIDTH,  HEIGHT,  p);

      this.roundID = roundID;
      draw();
   }

   /**
    * Bounces the figure around the screen
    */
   public void move()
   {
      super.move(xVel, yVel);

      if(xVel < 0 && x <= 0 || xVel > 0 && x + width >= STAGE_SIZE)
         xVel = - xVel;
      if(yVel < 0 && y <= 0 || yVel > 0 && y + height >= STAGE_SIZE)
         yVel = - yVel;
   }

   /**
    * Unused method; needed to be implemented from the abstract method in
    * abstract PFigure
    */
   public void draw(){}

   /**
    * Draw method specific for the scoreboard
    *
    * @param clock The time remaining in the round (rounded down)
    * @param roundScore The score for the current round
    * @param totalScore The total score of all rounds
    * @param highScore The highest score achieved in the game
    */
   public void drawScoreboard(int clock, int roundScore, int totalScore,
                              int highScore)
   {
      Rectangle rect = new Rectangle(x, y, width, height);
      rect.setStroke(Color.BLACK);
      rect.setFill(Color.LIGHTSTEELBLUE);
      Rectangle timerRect = new Rectangle(x + width - TIMER_BOX_SIZE, y,
                                          TIMER_BOX_SIZE, TIMER_BOX_SIZE);
      timerRect.setStroke(Color.BLACK);
      timerRect.setFill(Color.SLATEGRAY);

      StringBuilder clockValue = new StringBuilder();
      if(clock >= 0)
      {
         clockValue.append(clock);
         if(clock < 10)
            clockValue.insert(0, "0"); // Clock reads ## or 0#
      }
      else
         clockValue.append("00");

      Label roundNumber = new Label("Round: " + roundID);
      Label timer = new Label(clockValue.toString());
      Label roundScoreLabel = new Label("Round Score: " + roundScore);
      Label totalScoreLabel = new Label("Total Score: " + totalScore);
      Label highScoreLabel = new Label("High Score: " + highScore);

      timer.setTextAlignment(TextAlignment.CENTER);
      roundNumber.setTextAlignment(TextAlignment.CENTER);
      roundScoreLabel.setTextAlignment(TextAlignment.CENTER);
      totalScoreLabel.setTextAlignment(TextAlignment.CENTER);
      highScoreLabel.setTextAlignment(TextAlignment.CENTER);

      timer.setFont(TITLE_FONT);
      roundNumber.setFont(TITLE_FONT);
      roundScoreLabel.setFont(BODY_FONT);
      totalScoreLabel.setFont(BODY_FONT);
      highScoreLabel.setFont(BODY_FONT);

      if(clock <= 10)
         timer.setTextFill(Color.CRIMSON);
      else
         timer.setTextFill(Color.WHITE);

      roundNumber.setLayoutX(x + 4);
      roundNumber.setLayoutY(y + 1);

      timer.setLayoutX(x + 113);
      timer.setLayoutY(y);

      roundScoreLabel.setLayoutX(x + 5);
      roundScoreLabel.setLayoutY(y + 31);

      totalScoreLabel.setLayoutX(x + 4);
      totalScoreLabel.setLayoutY(y + 51);

      highScoreLabel.setLayoutX(x + 4);
      highScoreLabel.setLayoutY(y + 71);

      getChildren().clear();
      getChildren().addAll(rect, timerRect, roundNumber, timer,
                           roundScoreLabel, totalScoreLabel,
                           highScoreLabel);

      pane.setVisible(true);
   }
}
