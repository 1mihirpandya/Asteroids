import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * 
 * This class is the JPanel that holds all the game related occurrences. It's
 * refresh method will be called by controller every 32 milliseconds so that the
 * JPanel is repainted and all the Movers are drawn and moved appropriately.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class Display extends JPanel
{
    int iter = 0;

    JPanel scoreList = new JPanel(); // JPanel to organize HighScores

    // The Movers currently in the game display.
    private LinkedList<Mover> movers;

    // Objects interested in learning about collisions between
    // pairs of movers
    private Set<CollisionListener> listeners;

    JLabel data = new JLabel();

    // Movers that will be added to/removed from the game at the next
    // refresh
    private Set<Mover> pendingAdd;

    private Set<Mover> pendingRemove;

    private JLabel storyText; // Text describing story

    private JLabel controlsText; // Text describing controls

    private JLabel titleText; // Asteroids Title Text

    JLabel highScoresTitle = new JLabel(); // Title that says "High Scores"

    JLabel highScores = new JLabel(); // JLabel displaying high scores.

    private HashSet<Star> backgroundStars; // Stores the randomly located stars

    ScoreLinkedList scores; // Data structure to hold the scores.

    boolean draw = true;


    // private int iter;

    /**
     * Instantiates Display by creating background stars, setting up all the
     * JLabels, and instantiating all instance variables.
     * 
     * @throws IOException
     *             FileNotFound Exception
     */
    public Display() throws IOException
    {
        storyText = new JLabel();
        storyText.setFont( new Font( "Comfortaa", Font.PLAIN, 14 ) );
        controlsText = new JLabel();
        controlsText.setFont( new Font( "Comfortaa", Font.PLAIN, 17 ) );
        titleText = new JLabel();
        titleText.setFont( new Font( "Comfortaa", Font.PLAIN, 22 ) );
        movers = new LinkedList<Mover>();
        listeners = new HashSet<CollisionListener>();
        pendingAdd = new HashSet<Mover>();
        pendingRemove = new HashSet<Mover>();
        scoreList.setLayout( new BorderLayout() );
        scoreList.setBackground( Color.BLACK );

        scores = new ScoreLinkedList();
        setPreferredSize( new Dimension( 750, 750 ) );
        setMinimumSize( new Dimension( 750, 750 ) );
        setBackground( Color.black );
        setForeground( Color.white );
        setFont( new Font( Font.SANS_SERIF, Font.PLAIN, 120 ) );
        setFocusable( true );

        // Create the stars
        backgroundStars = new HashSet<Star>();
        createStars();
    }


    /**
     * Add a Mover to the Set of pendingAdd that will be added to movers next
     * refresh.
     * 
     * @param m
     *            Mover to add.
     */
    public void addMover( Mover m )
    {
        pendingAdd.add( m );
    }


    /**
     * 
     * This is the getter method that will return the LinkedList of Movers.
     * 
     * @return LinkedList of movers.
     */
    public LinkedList<Mover> getMovers()
    {
        return movers;
    }


    /**
     * Adds a mover to the Set of pendingRemove that will be removed from movers
     * next refresh.
     * 
     * @param m
     *            Mover to Remove.
     */
    public void removeMover( Mover m )
    {
        pendingRemove.add( m );
    }


    /**
     * 
     * This method sets the JLabel titleText.
     * 
     * @param text
     *            text to set title to
     */
    public void setTitleText( String text )
    {
        titleText.setForeground( Color.WHITE );
        titleText.setText( text );
    }


    /**
     * 
     * This sets the JLabel storyText.
     * 
     * @param text
     *            text to set story text to
     */
    public void setStoryText( String text )
    {
        storyText.setForeground( Color.white );
        storyText.setText( text );
    }


    /**
     * 
     * This method sets the JLabel controlsText.
     * 
     * @param text
     *            text to set to
     */
    public void setControlsText( String text )
    {
        controlsText.setForeground( Color.BLUE );
        controlsText.setText( text );
    }


    /**
     * 
     * This adds all the text components and the Button to the JPanel
     * 
     * @param comp
     *            startGameButton
     */
    public void addComponents( Component comp )
    {
        this.add( titleText );
        this.add( scoreList );
        this.add( storyText );
        this.add( comp );
        this.add( controlsText );
    }


    /**
     * Paint the Movers onto the Display JPanel.
     * 
     * @param g
     *            graphics object to draw the Movers.
     */
    @Override
    public void paintComponent( Graphics g )
    {

        Random rand = new Random();

        boolean isShip = false;
        // Calls Super paintcomponent method.
        super.paintComponent( g );
        // Draw each mover in its right location with custom color.
        for ( Mover m : movers )
        {
            if ( m instanceof SpaceShip )
            {
                isShip = true;
                g.setColor( new Color( 17, 150, 236 ) );
                if ( m.hittable == false )
                {
                    iter = iter + 1;
                    // draw = !draw;
                    if ( iter == 6 || iter == 5 || iter == 7 || iter == 8 )
                    {
                        m.drawShape( (Graphics2D)g );
                        if ( iter == 8 )
                            iter = 0;

                    }
                }
                else
                {
                    m.drawShape( (Graphics2D)g );
                }

            }
            else if ( m instanceof Asteroid )
            {
                g.setColor( new Color( 160, 160, 160 ) );
                m.drawShape( (Graphics2D)g );
            }
            else if ( m instanceof Bullet )
            {
                g.setColor( new Color( 255, 255, 33 ) );
                m.drawShape( (Graphics2D)g );
            }
            else if ( m instanceof UFO )
            {
                g.setColor( new Color( 52, 250, 102 ) );
                m.drawShape( (Graphics2D)g );
            }
            else if ( m instanceof SpaceJunk )
            {
                if ( m.getSize() == 0 ) // Asteroid's SpaceJunk
                {
                    g.setColor( new Color( 128, 128, 128 ) );
                    m.drawShape( (Graphics2D)g );
                }
                else if ( m.getSize() == 1 ) // UFO's SpaceJunk
                {
                    g.setColor( new Color( 52, 250, 102 ) );
                    m.drawShape( (Graphics2D)g );
                }
                else
                // SpaceShip's SpaceJunk
                {
                    g.setColor( new Color( 17, 150, 236 ) );
                    m.drawShape( (Graphics2D)g );
                }
            }
        }

        g.setColor( Color.white );

        // Draws the stars in background of display.
        for ( Star s : backgroundStars )
        {
            // Draws dots in location of stars.
            g.drawOval( s.getX(), s.getY(), 1, 1 );
        }
        // iter = 0;
    }


    /**
     * Creates the stars by adding stars to the HashSet of Stars.
     */
    private void createStars()
    {
        for ( int i = 0; i < 125; i++ )
        {
            backgroundStars.add( new Star() );
        }
    }


    /**
     * Adds the High Scores to the list that will be displayed. Also only shows
     * top 15 high scores.
     * 
     * @throws IOException
     *             FileNotFound Exception.
     */
    public void showScores() throws IOException
    {
        data.setForeground( Color.WHITE );
        data.setFont( new Font( "Comfortaa", Font.PLAIN, 25 ) );
        scores = new ScoreLinkedList(); // Adds all the contents in the file
                                        // already to linkedList.
        highScoresTitle.setForeground( Color.yellow );
        highScoresTitle.setFont( new Font( "Comfortaa", Font.PLAIN, 25 ) );
        highScoresTitle.setText( "High Scores" );
        highScores.setForeground( Color.WHITE );
        highScores.setFont( new Font( "Comfortaa", Font.PLAIN, 25 ) );
        String text = "";
        int count = 0;
        text += "<html><br>";
        String textScore = "";
        textScore += "<html><br>";
        for ( Score s : scores.getScoresLinkedList() ) // Iterates through
                                                       // ordered scores.
        {
            if ( count < 15 ) // Only displays top 15 High Scores.
            {
                text += s.getUsername() + "     <br>";
                textScore += "     " + s.getScore() + "<br>";
                count++;
            }
            else
            {
                break;
            }
        }
        text += "</html>";
        highScores.setText( text );
        data.setText( textScore );
        scoreList.add( highScoresTitle, "North" );
        JPanel content = new JPanel();
        content.setLayout( new BorderLayout() );
        content.setBackground( Color.BLACK );
        content.add( highScores, "West" );
        data.setBorder( new EmptyBorder( 0, 40, 0, 0 ) );
        content.add( data, "East" );
        scoreList.add( content, "Center" );
        scoreList.setBorder( new EmptyBorder( 0, 80, 0, 0 ) );
    }


    /**
     * Adds new Collision Listener.
     * 
     * @param listener
     *            listener to add.
     */
    public void addCollisionListener( CollisionListener listener )
    {
        listeners.add( listener );
    }


    /**
     * Removes existing Collision Listener.
     * 
     * @param listener
     *            listener to remove.
     */
    public void removeCollisionListener( CollisionListener listener )
    {
        listeners.remove( listener );
    }


    /**
     * Clears the screen's contents.
     */
    public void clearContents()
    {
        pendingRemove.clear();
        pendingAdd.clear();
        movers.clear();
        highScoresTitle.setText( "" );
        highScores.setText( "" );
        scores.clearLinkedListContents();
        titleText.setText( "" );
        storyText.setText( "" );
        controlsText.setText( "" );
    }


    /**
     * Iterates all the Movers to check if there is a collision between any two
     * movers.
     */
    private void checkForCollisions()
    {
        for ( Mover m1 : movers )
        {
            Iterator<Mover> iter = movers.descendingIterator();
            while ( iter.hasNext() )
            {
                Mover m2 = iter.next();
                if ( m2 == m1 )
                {
                    break;
                }
                if ( pendingRemove.contains( m1 ) )
                {
                    break;
                }
                if ( pendingRemove.contains( m2 ) )
                {
                    break;
                }
                if ( m1.collision( m2 ) )
                {
                    for ( CollisionListener listener : listeners )
                    {
                        // Control implements CollisionListener, so
                        // Control's method moversCollided will be called for
                        // these 2 Movers that
                        // collide.
                        listener.moversCollided( m1, m2 );
                    }
                }
            }
        }
    }


    /**
     * Completes any adds and removes of Mover objects needed to be done each
     * refresh called by control.
     */
    private void completeAddsAndRemoves()
    {
        Iterator<Mover> iterAdds = pendingAdd.iterator();
        while ( iterAdds.hasNext() )
        {
            movers.add( iterAdds.next() );
        }
        pendingAdd.clear();
        Iterator<Mover> iterRemoves = pendingRemove.iterator();
        while ( iterRemoves.hasNext() )
        {
            movers.remove( iterRemoves.next() );
        }
        pendingRemove.clear();
    }


    /**
     * This is called from the Control class every 32 milliseconds to update the
     * screen contents such as movement and it calls repaint to visually update
     * JPanel for display.
     */
    public void refresh()
    {
        completeAddsAndRemoves();
        Iterator<Mover> iter = movers.iterator();
        while ( iter.hasNext() )
        {
            iter.next().move();
        }
        checkForCollisions();
        repaint();
    }

}