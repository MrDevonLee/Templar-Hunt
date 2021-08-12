package TemplarHunt;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Child of MenuScreen that defines the layout of the title page of the
 * game and controls the visibility of the various title page features
 * outside of the game itself
 *
 * @author Devon Lee
 * @author Nathan Laures
 */
public class TitleScreen extends MenuScreen
{
   private static final double BACKGROUND_OPACITY = 1.0;
   private static final int NUM_BUTTONS = 5;
   private static final Color[] BUTTON_COLORS = new Color[]
                                               {QUIT_BUTTON_COLOR,
                                                Color.GOLD,
                                                Color.LIGHTSKYBLUE,
                                                Color.BISQUE,
                                                Color.MEDIUMSEAGREEN};
   private static final String[] BUTTON_TEXT = new String[]
                                {QUIT_BUTTON_TEXT,
                                 "Press \"H\" for High Scores",
                                 "Press \"C\" for Controls",
                                 "Press \"I\" for Instructions",
                                 "Press \"P\" to Play"};

   private final InstrScreen instrScreen;
   private final ControlsScreen ctrlScreen;
   private final HighScoresScreen scoreScreen;


   /**
    * Sets up the layout of the title screen of the game
    *
    * @param pane The pane on which this layout is instantiated
    */
   public TitleScreen(Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, false, pane);
      background.setFill(Color.LIGHTGOLDENRODYELLOW);
      title.setText("The Templar's Hunt");
      title.setLayoutX(200);
      title.setLayoutY(100);

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(BUTTON_COLORS[i]);
         buttonLabels.get(i).setText(BUTTON_TEXT[i]);
      }

      instrScreen = new InstrScreen(pane);
      ctrlScreen = new ControlsScreen(pane);
      scoreScreen = new HighScoresScreen(pane);
   }

   /**
    * Gets the movement test character
    *
    * @return The movement test character
    */
   public Player getTestDummy()
   {
      return ctrlScreen.getTestDummy();
   }


   /**
    * Sends data to the high scores screen to be displayed
    *
    * @param data An object containing data about the high scores
    */
   public void updateHighScores(HighScoresData data)
   {
      scoreScreen.updateHighScores(data);
   }


   /**
    * Hides the instructions page
    */
   public void hideInstructions()
   {
      instrScreen.hide();
   }

   /**
    * Hides the controls page
    */
   public void hideControls()
   {
      ctrlScreen.hide();
   }

   /**
    * Hides the high scores page
    */
   public void hideScores()
   {
      scoreScreen.hide();
   }


   /**
    * Shows the instruction page
    */
   public void showInstructions()
   {
      instrScreen.show();
   }

   /**
    * Shows the controls page
    */
   public void showControls()
   {
      ctrlScreen.show();
   }

   /**
    * Shows the high scores page
    */
   public void showScores()
   {
      scoreScreen.show();
   }
}
