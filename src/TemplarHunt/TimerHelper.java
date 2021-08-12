package TemplarHunt;

import java.util.TimerTask;

/**
 * Class designed to maintain the timer functions in the GameEngine class
 *
 * @author Devon Lee
 */
public class TimerHelper extends TimerTask
{
   private int clock;

   /**
    * Sets the clock
    * @param clock Time for the timer to count down
    */
   public TimerHelper(int clock)
   {
      this.clock = clock;
   }

   /**
    * Gets the current clock value
    * @return The current value of the clock in milliseconds
    */
   public int getClock()
   {
      return clock;
   }

   /**
    * Decrements the clock
    */
   @Override
   public void run()
   {
      clock--;
   }
}
