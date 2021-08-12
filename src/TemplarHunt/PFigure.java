package TemplarHunt;

import javafx.scene.layout.Pane;

/**
 * Abstract class governing the different moving parts of a round of the
 * game; also handles collisions between objects that implement its
 * abstract methods
 *
 * @author Donna Gavin
 */
public abstract class PFigure extends Pane implements Comparable
{
   protected int x, y;
   protected int width, height;
   protected int priority;
   protected Pane pane;

   /**
    * Basic parameterized constructor to specify starting location, size,
    * and pane
    *
    * @param startX beginning x location
    * @param startY beginning y location
    * @param width width of object
    * @param height height of object
    * @param pane pane the object is on
    */
   public PFigure(int startX, int startY, int width, int height, Pane pane)
   {
       x = startX;
       y = startY;
       this.width = width;
       this.height = height;
       priority = 1;
       this.pane = pane;
   }


   /**
    * Gets current x value of the figure
    *
    * @return x The x-coordinate of the figure
    */
   public int getX()
   {
      return x;
   }

   /**
    * Gets current y value of the figure
    *
    * @return y The y-coordinate of the figure
    */
   public int getY()
   {
      return y;
   }


   /**
    * Generic move method; only changes x and y
    *
    * @param deltaX change in x
    * @param deltaY change in y
    */
   public void move(int deltaX, int deltaY)
   {
      x += deltaX;
      y += deltaY;
   }

   /**
    * Hides the pane on which the figure lives
    */
   public void hide()
   {
      pane.setVisible(false);
   }

   /**
    * Abstract draw method to be implemented by other classes
    */
   abstract public void draw();


   /**
    * Compares two objects of type PFigure
    *
    * @param o object being compared
    * @return Priority of the PFigure or MAX_VALUE of o is not a PFigure
    */
   @Override
   public int compareTo(Object o)
   {
      if(o instanceof PFigure)
         return priority - ((PFigure)o).priority;
      return Integer.MAX_VALUE;
   }

   /**
    * Determines if two figures collided
    *
    * @param otherFig The figure that is being checked for a collision
    * @return True if the objects overlap, false otherwise
    */
   public boolean collidedWith(PFigure otherFig)
   {
      if (otherFig == null)
         return false;

      return (x + width) >= otherFig.x &&
             (otherFig.x + otherFig.width) >= x &&
             (y + height) >= otherFig.y &&
             (otherFig.y + otherFig.height) >= y;
   }
}