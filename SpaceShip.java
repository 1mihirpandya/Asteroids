import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;


/**
 * 
 * This class represents the SpaceShip by adding customized movement by
 * overriding Mover's move to add friction. Additionally it has a speedUp method
 * that will be called each time the user presses the up arrow key. It will also
 * keep track off the gun's location so that the bullet is drawn as coming from
 * the gun.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class SpaceShip extends Mover
{

    // The shape of the spaceship
    private Shape shape;

    // Speed limit of Spaceship
    final int speedLimit = 15;

    // Friction constant for applyFriction method
    final double friction = -0.05;


    /**
     * This constructor constructs the SpaceShip class and sets its unique
     * shape.
     */
    public SpaceShip()
    {
        Path2D.Double sketch = new Path2D.Double();

        sketch.moveTo( 20, 0 );
        sketch.lineTo( -20, 12 );
        sketch.lineTo( -20, -12 );
        sketch.closePath();

        shape = sketch;
    }


    /**
     * Returns the x-coordinate of the spaceship's gun. Moves point according to
     * changes in spaceship's position and rotation first.
     * 
     * @return x-coordinate of spaceship's gun.
     */
    public double getGunX()
    {
        Point2D.Double point;

        point = new Point2D.Double( 20, 0 );

        moveAndRotatePoint( point );
        return point.getX();
    }


    /**
     * Returns the y-coordinate of the spaceship's gun. Moves point according to
     * changes in spaceship's position and rotation first.
     * 
     * @return y-coordinate of spaceship's gun.
     */
    public double getGunY()
    {
        Point2D.Double point;

        point = new Point2D.Double( 20, 0 );

        moveAndRotatePoint( point );
        return point.getY();
    }


    /**
     * Returns unique shape of spaceship.
     * 
     * @return shape of SpaceShip.
     */
    @Override
    public Shape getShape()
    {
        return shape;
    }


    /**
     * 
     * This will speed the ship up for when the user holds the forward arrow
     * key.
     * 
     * @param speed
     *            speed to speed up ship by.
     */
    public void speedUp( double speed )
    {
        setXSpeed( getXSpeed() + ( speed * Math.cos( getSpin() ) ) );
        setYSpeed( getYSpeed() + ( speed * Math.sin( getSpin() ) ) );
        if ( Math.sqrt( Math.pow( getXSpeed(), 2 ) + Math.pow( getYSpeed(), 2 ) ) > speedLimit )
        {
            setXSpeed( getXSpeed() - ( speed * Math.cos( getSpin() ) ) );
            setYSpeed( getYSpeed() - ( speed * Math.sin( getSpin() ) ) );
        }
    }


    /**
     * 
     * This method will simulate friction by having a force going against the
     * spaceship's movement.
     */
    public void applyFriction()
    {
        if ( getXSpeed() != 0 || getYSpeed() != 0 )
        {
            double deltaX = friction
                * getXSpeed()
                / Math.sqrt( Math.pow( getXSpeed(), 2 )
                    + Math.pow( getYSpeed(), 2 ) );
            double deltaY = friction
                * getYSpeed()
                / Math.sqrt( Math.pow( getXSpeed(), 2 )
                    + Math.pow( getYSpeed(), 2 ) );
            if ( Math.abs( deltaX ) > Math.abs( getXSpeed() )
                || Math.abs( deltaY ) > Math.abs( getYSpeed() ) )
            {
                setXSpeed( 0 );
                setYSpeed( 0 );
            }
            else
            {
                setXSpeed( getXSpeed() + deltaX );
                setYSpeed( getYSpeed() + deltaY );
            }
        }
    }


    /**
     * Customizes the move method by Mover class by adding friction.
     */
    @Override
    public void move()
    {
        super.move();
        applyFriction();
    }

}