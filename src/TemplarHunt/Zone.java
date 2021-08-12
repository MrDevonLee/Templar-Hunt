package TemplarHunt;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static TemplarHunt.GUI.STAGE_SIZE;

/**
 * This class defines movement and draw methods for the PFig class and
 * serves as a slowly-moving background for the game. Different instances
 * of this class can look and behave differently for added complexity.
 *
 * @author Devon Lee and Nathan Laures
 */
public class Zone extends PFigure
{
   public static final int ZONE_SIZE = STAGE_SIZE / 3;
   private static final int MOVEMENT_LENGTH = ZONE_SIZE / 2;

   private static int numZones;
   private static int moveCounterIndex = 1;
   private static boolean moveOppositeX = false;

   private final int roundID;
   private final ImageView pic;
   private int xVel;
   private int yVel;
   private int moveCounter;
   private boolean hayZone = false;

   /**
    * Parameterized constructor for background zones. Color is chosen based
    * upon an alternating static variable
    *
    * Hay file obtained from barlowflowerfarm.com
    * Cobblestone file obtained from freestocktextures.com
    *
    * @param startX The starting x-coordinate of the zone
    * @param startY The starting y-coordinate of the zone
    * @param moveOppositeY Tells the zone to move opposite of the standard
    *                      direction by default
    * @param hayZone Denotes which image the zone will display and how it
    *                will affect the player points-wise
    * @param roundID The number of the round, used to determine movement
    * @param pane The pane on which the object lies
    */
   public Zone(int startX, int startY, boolean moveOppositeY,
               boolean hayZone, int roundID, Pane pane)
   {
      super(startX, startY, ZONE_SIZE, ZONE_SIZE, pane);

      this.roundID = roundID;
      moveCounter = 0;

      numZones++;

      ImageView temp;
      try
      {
         if(hayZone)
            temp = new ImageView("file:Hay.png");
         else
            temp = new ImageView("file:Cobble.jpg");
      }
      catch(Exception e)
      {
         temp = null;
         System.out.println("Could not find hay image: " + e);
      }
      pic = temp;

      xVel = 1;
      yVel = 1;
      if(moveOppositeY)
         yVel = -yVel;
      if(hayZone)
         this.hayZone = true;
      if(numZones >= 4 && numZones <= 6)
         xVel = -xVel;

      draw();
   }

   /**
    * Reports as to whether the zone in question is a hay zone, as opposed
    * to a cobble zone
    *
    * @return True if the zone is a hay (bad) zone, false otherwise
    */
   public boolean isHayZone()
   {
      return hayZone;
   }

   /**
    * Cycle the background squares around; it has different algorithms for
    * different rounds of the game
    */
   public void move()
   {
      if(roundID == 2 || (roundID >= 4 && moveOppositeX))
      {
         x += xVel;

         if(x >= STAGE_SIZE)
            x = -(int) ZONE_SIZE;
         else if(x <= -ZONE_SIZE)
            x = STAGE_SIZE;
      }
      if(roundID == 3 || (roundID >= 4 && !moveOppositeX))
      {
         y += yVel;

         if(y >= STAGE_SIZE)
            y = -(int) ZONE_SIZE;
         else if(y <= -ZONE_SIZE)
            y = STAGE_SIZE;
      }
      if(roundID == 4)
      {
         moveCounter++;
         if(moveCounter >= MOVEMENT_LENGTH)
         {
            moveCounter = 0;
            moveOppositeX = !moveOppositeX;
         }
      }
      if(roundID == 5)
      {
         moveCounter++;
         if((moveCounter * MOVEMENT_LENGTH) % moveCounterIndex == 0)
         {
            moveCounterIndex++;
            moveOppositeX = !moveOppositeX;
         }
      }
      if(roundID == 6)
      {
         moveCounter++;
         if((moveCounter / MOVEMENT_LENGTH) % 2 == 0)
            moveOppositeX = !moveOppositeX;
      }
      if(roundID == 7)
      {
         moveCounter++;
         if((moveCounter / MOVEMENT_LENGTH) % 2 == 0)
         {
            moveOppositeX = !moveOppositeX;
            yVel = -yVel;
         }
      }

   }

   /**
    * Draws the zone with one of two different images depending on the
    * hayZone parameter set by the constructor.
    */
   public void draw()
   {
      getChildren().clear();

      Rectangle background = new Rectangle(x, y, ZONE_SIZE, ZONE_SIZE);
      background.setFill(Color.WHITE);
      background.toBack();
      getChildren().add(background);

      if(pic != null)
      {
         pic.setX(x);
         pic.setY(y);
         pic.setFitHeight(ZONE_SIZE);
         pic.setFitWidth(ZONE_SIZE);
         pic.setOpacity(0.6);
         pic.toFront();
         getChildren().add(pic);

         pane.setVisible(true);
      }
   }

   /**
    * Resets the number of zones and the move counter from
    * any previous round/game.
    */
   public static void resetNumZones()
   {
      numZones = 0;
      moveCounterIndex = 1;
   }
}
