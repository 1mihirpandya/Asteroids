import javax.swing.*;
import java.awt.event.*;


/**
 * 
 * This class represents the Progress Timer and overlooks every phase of the
 * game giving a callback if the phase of the game has not changed or the
 * progress remains the same.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class ProgressTimer implements ActionListener
{

    // ProgressCount for when object created.
    private int progressCount;

    // Control object that contains method to increment and account for
    // progress.
    private Control control;

    // Timer that dictates Progress.
    private Timer timer;


    /**
     * Creates a Progress Timer based off number of milliseconds, progressCount,
     * and the Control object.
     * 
     * @param milliseconds
     *            milliseconds to set timer to.
     * @param progressCount
     *            number of progressions made.
     * @param control
     *            Control object that creates this timer.
     */
    public ProgressTimer( int milliseconds, int progressCount, Control control )
    {
        this.progressCount = progressCount;
        this.control = control;
        timer = new Timer( milliseconds, this );
        timer.addActionListener( this );
        timer.start();
    }


    /**
     * Makes a callback if progress has not been made or it remains in same
     * phase.
     * 
     * @param e
     *            ActionEvent for when timer goes off
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        timer.stop();
        if ( control.getProgressCount() == progressCount )
        {
            control.performTransition();
        }
    }
}