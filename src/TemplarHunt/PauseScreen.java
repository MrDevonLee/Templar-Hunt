package TemplarHunt;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 * Child of MenuScreen that displays the layout of the pause screen
 *
 * @author Devon Lee
 */
public class PauseScreen extends MenuScreen
{
   private static final double BACKGROUND_OPACITY  = 0.7;
   private static final int NUM_BUTTONS = 3;
   private static final Color[] BUTTON_COLORS = new Color[]
                                               {QUIT_BUTTON_COLOR,
                                                Color.LIGHTGOLDENRODYELLOW,
                                                Color.MEDIUMSEAGREEN};
   private static final String[] BUTTON_TEXT = new String[]
                                                    {QUIT_BUTTON_TEXT,
                                                     TITLE_BUTTON_TEXT,
                                                     PLAY_BUTTON_TEXT};

   /**
    * Sets up the layout and text of buttons
    *
    * @param pane The pane on which this layout is instantiated
    */
   public PauseScreen(Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, true, pane);
      background.setFill(Color.LAVENDER);
      title.setText("Paused");
      title.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 48));
      title.setLayoutX(335);
      title.setLayoutY(100);

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(BUTTON_COLORS[i]);
         buttonLabels.get(i).setText(BUTTON_TEXT[i]);
      }
   }
}
