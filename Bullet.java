import java.awt.Shape;
import java.awt.geom.Path2D;


/**
 * 
 * This class represents the shape of the Bullet and is instantiated relative to
 * the location of where a spaceship or UFO's gun is. It simply assigns its
 * unique shape to the shape instance variable.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */

public class Bullet extends Mover
{

    private Shape shape; // shape to describe bullet


    /**
     * Creates a bullet starting at the tip of the SpaceShip's gun or the UFO's
     * gun.
     * 
     * @param gunX
     *            x-coordinate of spaceship's gun or ufo's gun.
     * @param gunY
     *            y-coordinte of spaceship or ufo's gun.
     */
    public Bullet( double gunX, double gunY )
    {
        Path2D.Double sketch = new Path2D.Double();

        sketch.moveTo( gunX, gunY );
        sketch.lineTo( gunX + 1, gunY );
        sketch.lineTo( gunX + 1, gunY + 1 );
        sketch.lineTo( gunX, gunY + 1);
        sketch.closePath();
        shape = sketch;
    }


    /**
     * Returns the specific shape of the bullet.
     * 
     * @return shape of bullet
     */
    @Override
    public Shape getShape()
    {
        return shape;
    }

}