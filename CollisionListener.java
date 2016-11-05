/**
 * 
 * This interface will be implemented by Control, since Contorl is monitoring
 * what happens when two movers collide. It simply has one method and that is
 * moversCollided with the parameters being the two movers.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public interface CollisionListener
{

    /**
     * Determines that 2 movers have collided
     */
    public void moversCollided( Mover m1, Mover m2 );

}