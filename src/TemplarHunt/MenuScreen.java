package TemplarHunt;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

import static TemplarHunt.GUI.STAGE_SIZE;

/**
 * Class to create a full-screen menu overlaying the game. Has buttons, a
 * title, and an optional confirmation box. Designed to be extended to have
 * specific menu items in children classes.
 *
 * @author Devon Lee
 */
public class MenuScreen
{
   private static final double IN_FOCUS_OPACITY = 1.0;
   private static final double OUT_OF_FOCUS_OPACITY = 0.30;

   private static final Font TITLE_FONT = Font.font("Times New Roman", 48);

   protected static final Color QUIT_BUTTON_COLOR = Color.INDIANRED;

   protected static final String PLAY_BUTTON_TEXT = "Press \"P\" to Play";
   protected static final String TITLE_BUTTON_TEXT =
                                 "Press \"T\" to Return to Title Screen";
   protected static final String QUIT_BUTTON_TEXT = "Press \"Q\" to Quit";
   protected static final String BACK_BUTTON_TEXT =
                                 "Press \"B\" to go Back";

   private static final int CONFIRM_BOX_WIDTH = STAGE_SIZE / 3 + 40;
   private static final int CONFIRM_BOX_HEIGHT = 150;
   private static final int CONFIRM_BOX_X = STAGE_SIZE / 3 - 20;
   private static final int CONFIRM_BOX_Y = STAGE_SIZE / 3 - 30;

   private static final int CONFIRM_BOX_BUTTON_WIDTH = 60;
   private static final int CONFIRM_BOX_BUTTON_HEIGHT = 30;
   private static final int CONFIRM_BOX_YES_BUTTON_X = CONFIRM_BOX_X +
                            CONFIRM_BOX_WIDTH / 8;
   private static final int CONFIRM_BOX_NO_BUTTON_X = CONFIRM_BOX_X +
                            CONFIRM_BOX_WIDTH - CONFIRM_BOX_WIDTH / 8 -
                            CONFIRM_BOX_BUTTON_WIDTH;
   private static final int CONFIRM_BOX_BUTTON_Y = CONFIRM_BOX_Y + 80;

   private static final String CONFIRM_BOX_TITLE_TEXT =
                               "Quit to Title Screen?";
   private static final String CONFIRM_BOX_QUIT_TEXT =
                               "Quit Program?";

   protected Rectangle background;
   protected Label title;

   protected ArrayList<Rectangle> buttons;
   protected ArrayList<Label> buttonLabels;

   protected DropShadow shadow;
   protected DropShadow bigShadow;

   private final boolean hasConfirmBox;
   private Rectangle confirmBox;
   private Rectangle yesButton;
   private Rectangle noButton;
   private Label confirmBoxQuitText;
   private Label confirmBoxTitleText;
   private Label yesText;
   private Label noText;

   protected Pane pane;

   /**
    * Constructor
    *
    * @param backgroundOpacity The opacity of the screen
    * @param numButtons The number of buttons to display
    * @param wantConfirmBox If a confirmation box should be created with
    *                       the screen
    * @param pane The pane on which the menu is instantiated
    */
   public MenuScreen(double backgroundOpacity, int numButtons,
                     boolean wantConfirmBox, Pane pane)
   {
      this.pane = pane;

      background = new Rectangle(STAGE_SIZE, STAGE_SIZE);
      background.setOpacity(backgroundOpacity);

      title = new Label();
      title.setFont(TITLE_FONT);
      title.setTextAlignment(TextAlignment.CENTER);

      buttons = new ArrayList<>();
      buttonLabels = new ArrayList<>();

      shadow = new DropShadow();
      shadow.setHeight(8);
      shadow.setWidth(8);

      bigShadow = new DropShadow();
      bigShadow.setHeight(30);
      bigShadow.setWidth(30);

      for(int i = 0; i < numButtons; i++)
      {
         buttons.add(new Rectangle(360, 40));
         buttons.get(i).setX(STAGE_SIZE / 2 - 180);
         buttons.get(i).setY(735 - 80 * i);
         buttons.get(i).setStroke(Color.BLACK);
         buttons.get(i).setEffect(shadow);

         buttonLabels.add(new Label());
         buttonLabels.get(i).setLayoutX(STAGE_SIZE / 2 - 180 + 12);
         buttonLabels.get(i).setLayoutY(740 - 80 * i);
         buttonLabels.get(i).setTextAlignment(TextAlignment.CENTER);
         buttonLabels.get(i).setFont(Font.font("Times New Roman", 24));
      }

      buttons.trimToSize();
      buttonLabels.trimToSize();

      pane.getChildren().add(background);
      pane.getChildren().add(title);

      for(int i = 0; i < buttons.size(); i++)
      {
         pane.getChildren().add(buttons.get(i));
         pane.getChildren().add(buttonLabels.get(i));
      }

      hasConfirmBox = wantConfirmBox;
      if(wantConfirmBox)
         makeConfirmBox();
   }

   /**
    * Makes a confirmation box for quitting/returning to the title screen
    * if specified by the parameters of the constructor
    */
   private void makeConfirmBox()
   {
      confirmBox = new Rectangle(CONFIRM_BOX_X, CONFIRM_BOX_Y,
                                 CONFIRM_BOX_WIDTH, CONFIRM_BOX_HEIGHT);
      confirmBox.setFill(Color.ROSYBROWN);
      confirmBox.setEffect(bigShadow);

      yesButton = new Rectangle(CONFIRM_BOX_YES_BUTTON_X,
                                CONFIRM_BOX_BUTTON_Y,
                                CONFIRM_BOX_BUTTON_WIDTH,
                                CONFIRM_BOX_BUTTON_HEIGHT);
      yesButton.setFill(QUIT_BUTTON_COLOR);
      yesButton.setEffect(shadow);

      noButton = new Rectangle(CONFIRM_BOX_NO_BUTTON_X,
                               CONFIRM_BOX_BUTTON_Y,
                               CONFIRM_BOX_BUTTON_WIDTH,
                               CONFIRM_BOX_BUTTON_HEIGHT);
      noButton.setFill(Color.POWDERBLUE);
      noButton.setEffect(shadow);

      confirmBoxTitleText = new Label();
      confirmBoxTitleText.setText(CONFIRM_BOX_TITLE_TEXT);
      confirmBoxTitleText.setTextAlignment(TextAlignment.CENTER);
      confirmBoxTitleText.setFont(Font.font("Times New Roman", 32));
      confirmBoxTitleText.setLayoutX(CONFIRM_BOX_X + 30);
      confirmBoxTitleText.setLayoutY(CONFIRM_BOX_Y + 10);

      confirmBoxQuitText = new Label();
      confirmBoxQuitText.setText(CONFIRM_BOX_QUIT_TEXT);
      confirmBoxQuitText.setTextAlignment(TextAlignment.CENTER);
      confirmBoxQuitText.setFont(Font.font("Times New Roman", 32));
      confirmBoxQuitText.setLayoutX(CONFIRM_BOX_X + 60);
      confirmBoxQuitText.setLayoutY(CONFIRM_BOX_Y + 10);

      yesText = new Label();
      yesText.setText("Y");
      yesText.setTextAlignment(TextAlignment.CENTER);
      yesText.setFont(Font.font("Times New Roman", 24));
      yesText.setLayoutX(CONFIRM_BOX_YES_BUTTON_X + 20);
      yesText.setLayoutY(CONFIRM_BOX_BUTTON_Y + 2);

      noText = new Label();
      noText.setText("N");
      noText.setTextAlignment(TextAlignment.CENTER);
      noText.setFont(Font.font("Times New Roman", 24));
      noText.setLayoutX(CONFIRM_BOX_NO_BUTTON_X + 20);
      noText.setLayoutY(CONFIRM_BOX_BUTTON_Y + 2);


      pane.getChildren().add(confirmBox);
      pane.getChildren().add(yesButton);
      pane.getChildren().add(noButton);

      pane.getChildren().add(confirmBoxTitleText);
      pane.getChildren().add(confirmBoxQuitText);
      pane.getChildren().add(yesText);
      pane.getChildren().add(noText);
   }


   /**
    * Sets the pane of the screen
    *
    * @param pane The pane to which the object will be set
    */
   public void setPane(Pane pane)
   {
      this.pane = pane;
   }

   protected void addElementsToCurrentPane()
   {
      pane.getChildren().add(background);
      pane.getChildren().add(title);

      for(int i = 0; i < buttons.size(); i++)
      {
         pane.getChildren().add(buttons.get(i));
         pane.getChildren().add(buttonLabels.get(i));
      }

      if(hasConfirmBox)
      {
         pane.getChildren().add(confirmBox);
         pane.getChildren().add(yesButton);
         pane.getChildren().add(noButton);

         pane.getChildren().add(confirmBoxTitleText);
         pane.getChildren().add(confirmBoxQuitText);
         pane.getChildren().add(yesText);
         pane.getChildren().add(noText);
      }
   }


   /**
    * Hides the elements of a generic MenuScreen; designed to be called
    * by children
    */
   public void hide()
   {
      background.setVisible(false);
      title.setVisible(false);

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setVisible(false);
         buttonLabels.get(i).setVisible(false);
      }

      hideConfirmBox();
   }

   /**
    * Hides the confirmation box
    */
   public void hideConfirmBox()
   {
      if(hasConfirmBox)
      {
         confirmBox.setVisible(false);
         yesButton.setVisible(false);
         noButton.setVisible(false);

         confirmBoxTitleText.setVisible(false);
         confirmBoxQuitText.setVisible(false);
         yesText.setVisible(false);
         noText.setVisible(false);

         title.setOpacity(IN_FOCUS_OPACITY);

         for(int i = 0; i < buttons.size(); i++)
         {
            buttons.get(i).setOpacity(IN_FOCUS_OPACITY);
            buttonLabels.get(i).setOpacity(IN_FOCUS_OPACITY);
         }
      }
   }

   /**
    * Shows the elements of a generic MenuScreen; designed to be called
    * by children
    */
   public void show()
   {
      background.setVisible(true);
      background.toFront();
      title.setVisible(true);
      title.toFront();

      for(int i = 0; i < buttons.size(); i++)
      {
         buttons.get(i).setVisible(true);
         buttons.get(i).toFront();
         buttonLabels.get(i).setVisible(true);
         buttonLabels.get(i).toFront();
      }
   }

   /**
    * Shows a confirmation box on the screen
    *
    * @param typeOfConfirmation Determines if the text of the box should
    *                           refer to the title screen or to quitting
    */
   public void showConfirmBox(char typeOfConfirmation)
   {
      if(hasConfirmBox)
      {
         confirmBox.setVisible(true);
         yesButton.setVisible(true);
         noButton.setVisible(true);
         if(typeOfConfirmation == 'T')
            confirmBoxTitleText.setVisible(true);
         else
            confirmBoxQuitText.setVisible(true);
         yesText.setVisible(true);
         noText.setVisible(true);

         confirmBox.toFront();
         yesButton.toFront();
         noButton.toFront();

         confirmBoxTitleText.toFront();
         confirmBoxQuitText.toFront();
         yesText.toFront();
         noText.toFront();

         title.setOpacity(OUT_OF_FOCUS_OPACITY);

         for(int i = 0; i < buttons.size(); i++)
         {
            buttons.get(i).setOpacity(OUT_OF_FOCUS_OPACITY);
            buttonLabels.get(i).setOpacity(OUT_OF_FOCUS_OPACITY);
         }
      }
   }
}