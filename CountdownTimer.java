import javax.swing.*;
import java.awt.event.*;


/**
 * 
 * This class represents a CountdownTimer and it is used to know when to do
 * operations timely. For Example, Control class uses CountdownTimer to
 * determine when to remove the Bullets from the display.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class CountdownTimer implements ActionListener
{

    // The Mover involved.
    private Mover mover;

    // Timer to countdown.
    private Timer timer;

    // Object notified when time is expired.
    private CountdownTimerListener listener;

    private String temp;


    /**
     * Constructs the actual timer based off listener, mover, and time
     * parameters. Starts timer.
     * 
     * @param listener
     *            listener that uses the listener. Control in our case.
     * @param mover
     *            mover that will be manipulated as a result of the timer
     *            expiring.
     * @param milliseconds
     *            milliseconds to set the countdowntimer for.
     */
    public CountdownTimer(
        CountdownTimerListener listener,
        Mover mover,
        String x,
        int milliseconds )
    {
        this.listener = listener;
        this.mover = mover;
        timer = new Timer( milliseconds, this );
        timer.start();
        temp = x;
    }


    /**
     * When the timer has finished, it is stopped and the timeExpired method is
     * called. It will be called within Control, since Control will implement
     * CountdownTimerListener.
     * 
     * @param e
     *            ActionEvent that is triggered when timer goes off. Calls
     *            listener's timeExpired method.
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        timer.stop();
        listener.timeExpired( mover, temp );
    }

}