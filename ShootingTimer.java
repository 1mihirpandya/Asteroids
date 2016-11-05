import javax.swing.*;
import java.awt.event.*;


/**
 * 
 * This class represents the ShootingTimer that UFO will use to determine when
 * to shoot its bullets.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class ShootingTimer implements ActionListener
{

    // The Mover that has this shooting timer
    private UFO u;

    // Timer that actually determines when to shoot.
    private Timer timer;

    // Reference to control object that actually controls the bullet shooting.
    private Control control;


    /**
     * This constructor creates the object using the UFO, the time for it to be
     * set, and the control object.
     * 
     * @param u
     *            UFO that will contain ShootingTimer
     * @param milliseconds
     *            milliseconds that the timer will be for.
     * @param c
     *            Control object that sets up this timer.
     */
    public ShootingTimer( UFO u, int milliseconds, Control c )
    {
        this.u = u;
        control = c;
        timer = new Timer( milliseconds, this );
        timer.addActionListener( this );
        timer.start();
    }


    /**
     * 
     * This method stops the Timer for when the UFO is dead and it needs to stop
     * shooting.
     */
    public void stopTimer()
    {
        timer.stop();
    }


    /**
     * When the time has passed, the timer is stopped, and the bullet is shot
     * and once again the timer starts.
     * 
     * @param e
     *            ActionEvent that will be triggered when timer is done.
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        timer.stop();
        control.shootUFOBullet( u );
        timer.start();
    }
}