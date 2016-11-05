import java.awt.Shape;
import java.awt.geom.*;
import java.util.Random;


/**
 * 
 * This class assigns the shape of the SpaceJunk taking parameters of size and
 * location. The size will represent which Mover's spaceJunk it will be.(0 =
 * Asteroid, 1 = UFO, 2 = SpaceShip).
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class SpaceJunk extends Mover
{
    // Represents the size of the debris.
    private int size;

    // Shape of spaceJunk (0 = Asteroid, 1 = UFO, 2 = SpaceShip)
    private Shape shape;


    /**
     * 
     * @param x
     *            x-coordinate to create spaceJunk at.
     * @param y
     *            y-coordinate to create spaceJunk at.
     * @param size
     *            size of the spaceJunk (0 = Asteroid, 1 = UFO, 2 = SpaceShip)
     */
    public SpaceJunk( double x, double y, int size )
    {

        setLocation( x, y ); // sets location.

        this.size = size; // sets its size.

        // This will contain the shape
        Path2D.Double sketch = new Path2D.Double();

        Random random = new Random();

        // Create space junk according to size
        if ( size == 0 )
        {
            sketch.moveTo( 0, 0 );
            sketch.lineTo( 3, random.nextInt( 3 ) );
            sketch.closePath();
        }
        else if ( size == 1 )
        {
            sketch.moveTo( 0, 0 );
            sketch.lineTo( 5, random.nextInt( 5 ) );
            sketch.closePath();
        }
        else
        {
            sketch.moveTo( 0, 0 );
            sketch.lineTo( 15, random.nextInt( 25 ) );
            sketch.closePath();
        }
        shape = sketch; // Assigns shape of SpaceJunk
    }


    /**
     * Returns the shape of the spacejunk.
     * 
     * @return shape of SpaceJunk
     */
    @Override
    public Shape getShape()
    {
        return shape;
    }


    public int getSize()
    {
        return size;
    }

}