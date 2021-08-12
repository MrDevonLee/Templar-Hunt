package TemplarHunt;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Child of MenuScreen that provides a description of the game and its
 * main objectives
 *
 * @author Devon Lee
 * @author Nathan Laures
 */
public class InstrScreen extends MenuScreen
{
   private static final int NUM_BUTTONS = 1;
   private static final double BACKGROUND_OPACITY = 1.0;

   private final Label txtField;

   /**
    * Sets up the layout and text of the instructions and buttons
    *
    * @param pane The pane on which this layout is instantiated
    */
   public InstrScreen(Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, false, pane);
      background.setFill(Color.MISTYROSE);
      title.setText("Instructions");
      title.setLayoutX(325);
      title.setLayoutY(100);

      txtField = new Label();
      txtField.setLayoutX(20);
      txtField.setLayoutY(200);
      txtField.setFont(Font.font("Times New Roman", 18));
      txtField.setText("Loosely based on the Assassin’s Creed series," +
            " this game sets you as the Templar organization as they\n" +
            "are trying to capture some of the assassins. To do this " +
            "properly you must catch them within the friendly,\ngrey " +
            "territory. This will get you 20 points. If they are " +
            "caught in the yellow, enemy territory you will \nlose 20 " +
            "points and be paralyzed for a short period. If the two " +
            "sides are in different areas or in white,\nneutral " +
            "area, you will not gain points. In being near the game " +
            "statistics box you will steadily gain \npoints, though " +
            "not as rapidly as catching assassins. It would be " +
            "similar to gathering information on\ntargets. The goal " +
            "is to gain as many points as possible in each round. " +
            "Each round there are also \nscore caps you must reach " +
            "in order to get to the next level, " +
            "so don’t skip acting for too long.");

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(QUIT_BUTTON_COLOR);
         buttonLabels.get(i).setText(BACK_BUTTON_TEXT);
      }

      pane.getChildren().add(txtField);
   }

   /**
    * Hides the information screen layout
    */
   public void hide()
   {
      super.hide();
      txtField.setVisible(false);
   }

   /**
    * Shows the information screen layout
    */
   public void show()
   {
      super.show();
      txtField.setVisible(true);
      txtField.toFront();
   }
}
