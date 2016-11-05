import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


/**
 * 
 * This class represents any moving object in the game of Asteroids. It has a
 * getShape method that will be overriden by each subclass since they will have
 * distinct shapes. It also has all the basic movement methods.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public abstract class Mover
{
    boolean hittable = true;
    // Speed of mover in both x and y direction.
    private double xSpeed, ySpeed;

    // Rotation of Mover in radians.
    private double spin;

    // Current location of mover.
    private Point loc;

    // Current Shape of mover.
    private Shape shape;


    /**
     * This instantiates a Mover setting its speed to 0, its spin to 0, and its
     * location to (0,0)
     */
    public Mover()
    {
        xSpeed = 0;
        ySpeed = 0;
        spin = 0;
        loc = new Point( 0, 0 );
        hittable = false;
    }


    /**
     * 
     * This is the getter method for the xSpeed.
     * 
     * @return x component of mover's speed.
     */
    public double getXSpeed()
    {
        return xSpeed;
    }


    /**
     * 
     * This is the getter method for the ySpeed.
     * 
     * @return y component of mover's speed.
     */
    public double getYSpeed()
    {
        return ySpeed;
    }


    /**
     * 
     * This is the setter method for the xSpeed.
     * 
     * @param xspeed
     *            double to set xSpeed to
     */
    public void setXSpeed( double xspeed )
    {
        xSpeed = xspeed;
    }


    /**
     * 
     * This is the setter method for the ySpeed.
     * 
     * @param yspeed
     *            double to set ySpeed to
     */
    public void setYSpeed( double yspeed )
    {
        ySpeed = yspeed;
    }


    /**
     * 
     * This sets the velocity of the mover using speed, direction, and simple
     * physics.
     * 
     * @param speed
     *            speed to set mover to
     * @param direction
     *            direction to set mover's velocity to
     */
    public void setVelocity( double speed, double direction )
    {
        xSpeed = Math.cos( direction ) * speed;
        ySpeed = Math.sin( direction ) * speed;
    }


    /**
     * 
     * This is the setter method for the spin.
     * 
     * @param rad
     *            spin angle in radians.
     */
    public void setSpin( double rad )
    {
        spin = rad;
    }


    /**
     * 
     * This will spin the mover from its current spin by the given amount of
     * radians.
     * 
     * @param rad
     *            radians to spin mover by
     */
    public void spin( double rad )
    {
        spin += rad;
    }


    /**
     * 
     * This is the getter method for the spin of the mover.
     * 
     * @return spin
     */
    public double getSpin()
    {
        return spin;
    }


    /**
     * 
     * This sets the location of the Mover in the screen.
     * 
     * @param x
     *            x-coordinate to set mover to.
     * @param y
     *            y-coordinate to set mover to.
     */
    public void setLocation( double x, double y )
    {
        loc.setLocation( x, y );
    }


    /**
     * 
     * This is the getter method for the x-coordinate of the mover's location.
     * 
     * @return x-coordinate of mover's location.
     */
    public double getX()
    {
        return loc.getX();
    }


    /**
     * 
     * This is the getter method for the y-coordinate of the mover's location.
     * 
     * @return y-coordinate of mover's location.
     */
    public double getY()
    {
        return loc.getY();
    }


    /**
     * 
     * This is the abstract method that all the subclasses of Mover will
     * override. All subclasses will have their own distinct shape so this
     * method will be abstract.
     * 
     * @return Shape object representing mover's shape.
     */
    abstract Shape getShape();


    /**
     * 
     * This method physically moves the Mover by modifying it's location as an
     * instance variable and transforming the shape of the mover by translating
     * and rotating it. Additionally it will check if the mover is out of bounds
     * and it will move it back to the other side of the screen.
     */
    public void move()
    {
        // Gets the original shape
        Shape originalShape = getShape();

        loc.setLocation( getX() + xSpeed, getY() + ySpeed );

        // Transforms mover by adjusting spin and translating it
        AffineTransform trans = AffineTransform.getTranslateInstance( loc.getX(),
            loc.getY() );
        trans.concatenate( AffineTransform.getRotateInstance( spin ) );
        shape = trans.createTransformedShape( originalShape );

        // Checks for out of bounds.
        Rectangle2D bounds = shape.getBounds2D();
        if ( bounds.getMaxX() < 0 )
        {
            loc.setLocation( getX() + 750
                + ( bounds.getMaxX() - bounds.getMinX() ),
                getY() );
        }
        if ( bounds.getMinX() >= 750 )
        {
            loc.setLocation( getX()
                + ( -750 - ( bounds.getMaxX() - bounds.getMinX() ) ),
                getY() );
        }
        if ( bounds.getMaxY() < 0 )
        {
            loc.setLocation( getX(),
                getY() + 750 + ( bounds.getMaxY() - bounds.getMinY() ) );
        }
        if ( bounds.getMinY() >= 750 )
        {
            loc.setLocation( getX(), getY()
                + ( -750 - ( bounds.getMaxY() - bounds.getMinY() ) ) );
        }

    }


    /**
     * 
     * This method moves and rotates a point.
     * 
     * @param point
     *            the point to move and rotate.
     */
    public void moveAndRotatePoint( Point2D.Double point )
    {
        AffineTransform trans = AffineTransform.getTranslateInstance( loc.getX(),
            loc.getY() );
        trans.concatenate( AffineTransform.getRotateInstance( spin ) );
        trans.transform( point, point );
    }


    /**
     * 
     * This will return whether or not there is a collision between this mover
     * and the other mover, using area's intersect method.
     * 
     * @param other
     *            other mover to check if there is a collision between this and
     *            the other mover.
     * @return whether or not there is a collision between the two movers.
     */
    public boolean collision( Mover other )
    {
        Area a = new Area( shape );
        a.intersect( new Area( other.shape ) );
        return !a.isEmpty();
    }


    /**
     * 
     * This method will actually draw the shape of the mover and will be called
     * from display class paint component.
     * 
     * @param g
     *            graphics object that will draw shape.
     */
    public void drawShape( Graphics2D g )
    {
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON );
        if ( shape != null )
        {
            g.draw( shape );
        }
        else
        {
            shape = getShape();
        }
    }


    /**
     * This is meant to be overriden by SpaceJunk to determine which size it is
     * and therefore which Mover it belongs to.
     * 
     * @return size
     */
    public int getSize()
    {
        return 0;
    }
}