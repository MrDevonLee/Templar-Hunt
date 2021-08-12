package TemplarHunt;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Child of MenuScreen that handles functions and layout of the control
 * screen to inform the player of the character and game controls
 *
 * @author Devon Lee
 * @author Nathan Laures
 */
public class ControlsScreen extends MenuScreen
{
   private static final double BACKGROUND_OPACITY = 1.0;
   private static final int NUM_BUTTONS = 1;

   private final Player testDummy;
   private final Label txtField;

   /**
    * Sets up the layout and text of the control instructions, buttons, and
    * test player
    *
    * @param pane The pane on which this layout is instantiated
    */
   public ControlsScreen(Pane pane)
   {
      super(BACKGROUND_OPACITY, NUM_BUTTONS, false, pane);
      background.setFill(Color.PALETURQUOISE);
      title.setText("Controls");
      title.setLayoutX(325);
      title.setLayoutY(100);

      txtField = new Label();
      txtField.setLayoutX(20);
      txtField.setLayoutY(200);
      txtField.setFont(Font.font("Times New Roman", 18));
      txtField.setText("""
      Movement:
      Up: up arrow or W key
      Down: down arrow or S key
      Left: left arrow or A key
      Right: right arrow or D key
      Pro tip: Spam both at once for faster movement
 
      Game Control:
      Pause: P key
      Mute: M key
 
      Cheats (While in Game):
      Toggle cheats on and off: O key (letter)
      Add points: U key
      Subtract points: I key
      End round: E key
      Progress to next round (even if you lost): O key (letter)""");

      testDummy = new Player(pane);

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setFill(QUIT_BUTTON_COLOR);
         buttonLabels.get(i).setText(BACK_BUTTON_TEXT);
      }

      pane.getChildren().add(txtField);
      pane.getChildren().add(testDummy);
   }

   /**
    * Gets a test character figure that can do basic movements
    *
    * @return The test player object
    */
   public Player getTestDummy()
   {
      return testDummy;
   }

   /**
    * Hides the controls page
    */
   public void hide()
   {
      super.hide();
      txtField.setVisible(false);
      testDummy.setVisible(false);
   }

   /**
    * Shows the controls page
    */
   public void show()
   {
      super.show();
      txtField.setVisible(true);
      txtField.toFront();
      testDummy.setVisible(true);
      testDummy.toFront();
   }
}
