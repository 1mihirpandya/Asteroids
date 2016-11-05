import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;


/**
 * 
 * This is the Asteroids class that will have its own unique shape. It has
 * variety of 4 different types of shapes, and it will be scaled in 3 possible
 * ways, allowing for 12 possible asteroids.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class Asteroid extends Mover
{
    private int size; // Size 0, 1, or 2.

    // Array used to scale size of asteroids. Scales by scalingArray[size]
    private double[] scalingArray = new double[] { 0.5, 1.0, 2.0 };

    // Shape that describes asteroid.
    private Shape shape;


    /**
     * Create an asteroid of the specified variety and size and position it at
     * the provided coordinates.
     * 
     * @param variety
     *            either 0, 1, 2, or 3. Represents 4 distinct shapes.
     * @param size
     *            either 0, 1, or 2. Represents the size of the asteroid. Used
     *            as index in scalingArray to scale.
     * @param x
     *            x-coordinate to initialize asteroid.
     * @param y
     *            y-coordinate to initialize asteroid.
     */
    public Asteroid( int variety, int size, double x, double y )
    {
        this.size = size;
        setLocation( x, y );
        Path2D.Double sketch = new Path2D.Double();

        // Checks which variety asteroid is.
        if ( variety == 0 )
        {
            sketch.moveTo( 60, 40 );
            sketch.lineTo( -28, 28 );
            sketch.lineTo( 0, 0 );
            sketch.lineTo( -28, -28 );
            sketch.lineTo( 48, -24 );
            sketch.closePath();
        }
        else if ( variety == 1 )
        {
            sketch.moveTo( 60, 60 );
            sketch.lineTo( -60, 60 );
            sketch.lineTo( -32, 0 );
            sketch.lineTo( -60, -80 );
            sketch.lineTo( 20, -60 );
            sketch.lineTo( 8, -20 );
            sketch.lineTo( 32, -32 );
            sketch.closePath();
        }
        else if ( variety == 2 )
        {
            sketch.moveTo( 70, -20 );
            sketch.lineTo( 10, 12 );
            sketch.lineTo( 60, 32 );
            sketch.lineTo( 20, 66 );
            sketch.lineTo( 4, 40 );
            sketch.lineTo( -20, 64 );
            sketch.lineTo( -36, 16 );
            sketch.lineTo( -20, -52 );
            sketch.lineTo( 4, -68 );
            sketch.lineTo( 18, -62 );
            sketch.closePath();
        }
        else
        {
            sketch.moveTo( 0, 80 );
            sketch.lineTo( -40, 32 );
            sketch.lineTo( -12, 0 );
            sketch.lineTo( -40, -32 );
            sketch.lineTo( 8, -40 );
            sketch.lineTo( 40, 40 );
            sketch.lineTo( 4, 40 );
            sketch.closePath();
        }

        // Scales to different sizes using size as index of scaling array.
        double scale = scalingArray[size];
        sketch.transform( AffineTransform.getScaleInstance( scale, scale ) );
        shape = sketch;
    }


    /**
     * This method is overriden to get the size of the Asteroid.
     * 
     * @return size of asteroid (0,1,or 2)
     */
    public int getSize()
    {
        return size;
    }


    /**
     * This returns the unique shape of the Asteroid.
     * 
     * @return shape of asteroid
     */
    public Shape getShape()
    {
        return shape;
    }

}