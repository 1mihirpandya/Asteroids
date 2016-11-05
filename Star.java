import java.awt.Point;
import java.util.Random;


/**
 * 
 * This class simply holds the location of a star and it will be drawn in the
 * paint component method of the display class by simply drawing a white dot
 * using the location.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class Star
{
    private Point loc; // location of star.

    private Random rand; // random used to set random location of star.


    /**
     * This is the constructor that assigns star to random location in game
     * display.
     */
    public Star()
    {
        rand = new Random();
        loc = new Point( rand.nextInt( 750 ), rand.nextInt( 750 ) );
    }


    /**
     * 
     * This method returns the x-coordinate of star.
     * 
     * @return x-coordinate of star.
     */
    public int getX()
    {
        return (int)loc.getX();
    }


    /**
     * 
     * This method returns the y-coordinate of star.
     * 
     * @return y-coordinate of star.
     */
    public int getY()
    {
        return (int)loc.getY();
    }
}
