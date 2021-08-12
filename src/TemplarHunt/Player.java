package TemplarHunt;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;

import static TemplarHunt.GUI.STAGE_SIZE;

/**
 * Creates the player character with the shapes class and handles
 * the movement and paralysis of that character
 *
 * @author Nathan Laures
 * @author Donna Gavin
 */
public class Player extends PFigure
{
   private static final int SIZE = 80;
   private static final int PARALYZE_TIME = 1000;

   private boolean paralyzed;
   private long paralyzeCounter;

   /**
    * Constructor for the player character
    * @param pane The pane on which the figure is created
    */
   public Player(Pane pane)
   {
      super((STAGE_SIZE / 2) - (SIZE / 2), (STAGE_SIZE / 2) - (SIZE / 2),
            SIZE, SIZE, pane);

      paralyzed = false;
      draw();
   }

   /**
    * Moves the character by the specified amount in the x and y directions
    *
    * @param deltaX Change in x
    * @param deltaY Change in y
    */
   public void move(int deltaX, int deltaY)
   {
      super.move(deltaX, deltaY);
      if(x < -width / 2)
         x = (STAGE_SIZE - width / 2);
      else if((x + width / 2) > pane.getWidth())
         x = -width / 2;
      if(y < -height / 2)
         y = (STAGE_SIZE - height / 2);
      else if ((y + height / 2) > STAGE_SIZE)
         y = -height / 2;
   }

   /**
    * Draws the character with the Shapes class and makes it visible
    */
   public void draw()
   {
      Circle circle1 = new Circle(x + width/2 , y + height/2,
                                  width/2);
      circle1.setStroke(Color.BLACK);
      circle1.setFill(Color.WHITE);

      Rectangle rectangle1 = new Rectangle(x + 10, y + height/2 - 4,
                                           width - 20, 8);
      rectangle1.setFill(Color.RED);

      Rectangle rectangle2 = new Rectangle(x + width/2 - 4, y + 10,
                                           8, height - 20);
      rectangle2.setFill(Color.RED);

      Polygon polygon1 = new Polygon();
      polygon1.getPoints().addAll((double)x+10, (double)y+width/2-8,
                                  (double)x+18, (double)y+width/2,
                                  (double)x+10, (double)y+width/2+8);
      polygon1.setFill(Color.RED);

      Polygon polygon2 = new Polygon();
      polygon2.getPoints().addAll((double)x+width-10, (double)y+width/2-8,
                                  (double)x+width-18, (double)y+width/2,
                                  (double)x+width-10, (double)y+width/2+8);
      polygon2.setFill(Color.RED);

      Polygon polygon3 = new Polygon();
      polygon3.getPoints().addAll((double)x+width/2-8, (double)y+10,
                                  (double)x+width/2, (double)y+18,
                                  (double)x+width/2+8, (double)y+10);
      polygon3.setFill(Color.RED);

      Polygon polygon4 = new Polygon();
      polygon4.getPoints().addAll((double)x+width/2-8, (double)y+width-10,
                                  (double)x+width/2, (double)y+width-18,
                                  (double)x+width/2+8, (double)y+width-10);
      polygon4.setFill(Color.RED);

      getChildren().clear();
      getChildren().addAll(circle1, rectangle1, rectangle2, polygon1,
                           polygon2, polygon3, polygon4);

      pane.setVisible(true);
   }

   /**
    * Starts a period during which the calling figure is paralyzed and
    * therefore will not respond to user input
    */
   public void paralyze()
   {
      paralyzed = true;
      paralyzeCounter = System.currentTimeMillis();
   }

   /**
    * Evaluates whether the calling figure is paralyzed
    *
    * @return True if the calling figure is paralyzed, false otherwise
    */
   public boolean isParalyzed()
   {
      if(System.currentTimeMillis() - paralyzeCounter > PARALYZE_TIME)
         paralyzed = false;
      return paralyzed;
   }
}