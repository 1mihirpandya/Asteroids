/**
 * 
 * This Interface is the CountdownTimerListener and it has simply one method
 * timeExpired which is implemented by CountdownTimer class.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public interface CountdownTimerListener
{

    /**
     * Called when CountDown timer reaches 0 and time is up.
     * 
     * @param m
     *            Mover that has the CountdownTimer
     * @param temp 
     */
    public void timeExpired( Mover m, String temp  );

}