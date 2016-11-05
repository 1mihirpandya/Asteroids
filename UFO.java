import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;


/**
 * 
 * This is the UFO class that has its own distinct shape. It does not add any
 * customization and the random movement is simply implemented from Control
 * class. It has a ShootingTimer that will call its shoot method every time the
 * timer goes off.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class UFO extends Mover
{
    Shape shape; // shape of Mover

    ShootingTimer shoot; // ShootingTimer that determines when UFO shoots
                         // bullet.


    /**
     * This sets up the shape of the UFO by sketching it and assigning shape
     * instance variable to sketch. Also instantiates ShootingTimer.
     * 
     * @param x
     *            x-coordinate to set its location to.
     * @param y
     *            y-coordinate to set its location to.
     * @param c
     *            Control class that will control the UFO.
     */
    public UFO( int x, int y, Control c )
    {
        super();
        Path2D.Double sketch = new Path2D.Double();
        sketch.moveTo( -20, 0 );
        sketch.lineTo( 20, 0 );
        sketch.lineTo( 14, 10 );
        sketch.lineTo( -14, 10 );
        sketch.lineTo( -20, 0 );
        sketch.lineTo( -14, -10 );
        sketch.lineTo( 14, -10 );
        sketch.lineTo( 20, 0 );
        sketch.moveTo( -14, 10 );
        sketch.lineTo( -8, 20 );
        sketch.lineTo( 8, 20 );
        sketch.lineTo( 14, 10 );
        shape = sketch;
        setLocation( x, y );
        shoot = new ShootingTimer( this, 1000, c );
    }


    /**
     * 
     * This method sets the timer of the UFO null and this will prevent the UFO
     * from shooting once it is dead.
     */
    public void setTimerNull()
    {
        shoot.stopTimer();
    }


    /**
     * 
     * This returns the gun's x-coordinate.
     * 
     * @return gun's x-coordinate.
     */
    public double getGunX()
    {
        Point2D.Double point;
        point = new Point2D.Double( -20, 0 );
        moveAndRotatePoint( point );
        return point.getX();
    }


    /**
     * 
     * This returns the gun's y-coordinate.
     * 
     * @return gun's y-coordinate.
     */
    public double getGunY()
    {
        Point2D.Double point;
        point = new Point2D.Double( -20, 0 );
        moveAndRotatePoint( point );
        return point.getY();
    }


    /**
     * Overridden getShape method of UFO class.
     * 
     * @return shape of UFO.
     */
    @Override
    public Shape getShape()
    {
        return shape;
    }

}
