package TemplarHunt;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static TemplarHunt.GUI.STAGE_SIZE;
import static TemplarHunt.PFigureList.NUM_ZONES;
import static TemplarHunt.Zone.ZONE_SIZE;

/**
 * This class handles the enemies of our game. It creates them on the game
 * pane then deals with various needs based on if the assassin is caught
 * and when the character gets reset.
 *
 * @author Devon Lee
 * @author Nathan Laures
 * @author Donna Gavin
 */
public class Enemy extends PFigure
{
   private static final int SIZE = 100;
   private static final int SPECIAL_MOVE_PROBABILITY = 150;

   private ImageView icon;
   private final boolean isSpecial;

   private int xVel = 1;
   private int yVel = 1;
   private int xCenter;
   private int yCenter;
   private boolean tagged;

   private static int numFigs = 0;
   private static int numTagged = 0;

   /**
    * Constructor for the enemy character; assigns a random starting
    * position on the pane and draws the figure at the end
    *
    * Regular enemy file obtained from PikPNG.com
    * Special enemy file obtained from Teemato.com
    *
    * @param isSpecial Whether the enemy is a special enemy or not
    * @param pane - pane it is created on
    */
   public Enemy(boolean isSpecial, Pane pane)
   {
      super((int) (STAGE_SIZE * Math.random()),
            (int) (STAGE_SIZE * Math.random()), SIZE, SIZE,  pane);

      this.isSpecial = isSpecial;
      try
      {
         if(isSpecial)
            icon = new ImageView("file:SuperAssassin.jpg");
         else
            icon = new ImageView("file:Assassin.png");

         numFigs++;
         draw();
      }
      catch(Exception e)
      {
         System.out.println("An error occured: " + e);
      }
   }


   /**
    * Reports whether the calling figure is currently tagged
    *
    * @return True if the calling figure is tagged, false otherwise
    */
   public boolean isTagged()
   {
      return tagged;
   }

   /**
    * Reports whether the calling enemy has special significance in the
    * game, such as higher point rewards
    *
    * @return True if the enemy is a special enemy, false otherwise
    */
   public boolean isSpecialEnemy()
   {
      return isSpecial;
   }

   /**
    * Gets the total number of PicFig objects in existence
    *
    * @return The total number of PicFig objects
    */
   public static int getNumFigs()
   {
      return numFigs;
   }


   /**
    * Bounces the figure around the screen
    */
   public void move()
   {
      super.move(xVel, yVel);

      if(isSpecial)
      {
         if(((int) (Math.random() * SPECIAL_MOVE_PROBABILITY)) == 1)
            xVel = -xVel;
         else if(((int) (Math.random() * SPECIAL_MOVE_PROBABILITY)) == 1)
            yVel = -yVel;
      }

      if(xVel < 0 && x <= 0 || xVel > 0 && x + width >= STAGE_SIZE)
         xVel = - xVel;
      if(yVel < 0 && y <= 0 || yVel > 0 && y + height >= STAGE_SIZE)
         yVel = - yVel;
   }

   /**
    * Creates the image at the current coordinates of the object
    */
   public void draw()
   {
      if(icon != null)
      {
         icon.setX(x);
         icon.setY(y);
         xCenter = x + SIZE / 2;
         yCenter = y + SIZE / 2;
         icon.setFitHeight(height);
         icon.setFitWidth(width);

         getChildren().clear();
         getChildren().add(icon);
         pane.setVisible(true);
      }
   }


   /**
    * Marks the calling enemy as tagged, hides it, and increments the total
    * number of enemies tagged
    *
    * @param zones The list of zones with which to check collisions
    * @return The multiplier associated with the zone(s) with which a
    * figure has collided, +1/-1 per zone
    */
   public int handleCollision(Zone[] zones)
   {
      this.tagged = true;
      this.setVisible(false);
      numTagged++;

      int zoneMultiplier = 0;

      for(int i = 0; i < NUM_ZONES; i++)
         if(centeredInZone(zones[i]))
         {
            if(zones[i].isHayZone())
               zoneMultiplier--;
            else
               zoneMultiplier++;
         }

      return zoneMultiplier;
   }

   /**
    * Tests if the center of the assassin is within the given zone
    *
    * @param zone The zone to be tested
    * @return True if the enemy is in the zone, false otherwise
    */
   private boolean centeredInZone(Zone zone)
   {
      return xCenter > zone.getX() && xCenter < zone.getX() + ZONE_SIZE &&
             yCenter > zone.getY() && yCenter < zone.getY() + ZONE_SIZE;
   }

   /**
    * Determines of all enemies have been tagged and therefore need to be
    * reset to an untagged state
    *
    * @return True if all of the enemies have been tagged, false otherwise
    */
   public static boolean needToResetTags()
   {
      return numTagged == numFigs;
   }

   /**
    * Resets the "tag" status of the calling figure, restores the opacity
    * of the calling figure, and resets the number of enemies tagged.
    */
   public void unTag()
   {
      this.tagged = false;
      this.setVisible(true);
      numTagged--;
   }


   /**
    * Resets the figures and the tagged figures to zero
    */
   public static void resetFigureAndTagCount()
   {
      numFigs = 0;
      numTagged = 0;
   }
}
