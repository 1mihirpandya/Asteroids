import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * This is the JFrame class with the Main Method for the Asteroids Game. It
 * instantiates the Display and Control class so those classes can have the game
 * started. It also organizes the layout and adds the components to
 * BorderLayout.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class AsteroidsGame extends JFrame
{
    // Object that will control the occurrences of the game.
    private Control control;

    // Label that will display player's score.
    public static JLabel scoreLabel = new JLabel();

    // Labels that will display player's lives remaining.
    public static JLabel lifeCount = new JLabel();

    // Button that will allow user to start Game
    public JButton startGameButton = new JButton();

    // Button that will allow user to view the high scores.
    private JButton viewHighScores = new JButton();


    /**
     * This constructor instantiates the Display and Control class. It
     * additionally sets up the buttons and the whole JPanel's layout using
     * BorderLayout.
     * 
     * @throws IOException
     *             FileNotFound Exception
     */
    public AsteroidsGame() throws IOException
    {
        Display display = new Display();
        control = new Control( display, this );

        // Sets Title of the JFrame.
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setTitle( "Asteroids" );
        this.setVisible( true );
        this.setBackground( Color.BLACK );
        // Sets up StartGame Button
        startGameButton.setForeground( Color.yellow );
        startGameButton.setFont( new Font( "Comfortaa", Font.PLAIN, 50 ) );
        startGameButton.setText( "Play Asteroids" );
        startGameButton.setOpaque( false );
        startGameButton.setContentAreaFilled( false );
        startGameButton.setBorderPainted( false );

        // Sets up ViewHighScores Button
        viewHighScores.setForeground( Color.BLUE );
        viewHighScores.setText( "High Scores" );

        // Sets up GameDisplay
        JPanel gameDisplay = new JPanel();
        gameDisplay.setBackground( Color.BLACK );
        gameDisplay.setLayout( new GridBagLayout() );
        display.addComponents( startGameButton );
        gameDisplay.add( display );

        // Sets up JLabel's on top panel.
        lifeCount = new JLabel();
        lifeCount.setForeground( Color.WHITE );
        lifeCount.setText( "Lives: " );
        scoreLabel = new JLabel();
        scoreLabel.setForeground( Color.WHITE );
        scoreLabel.setText( "Score: " );

        // Adds JLabels to the JPanel topPart
        JPanel topPart = new JPanel();
        topPart.setLayout( new FlowLayout() );
        topPart.setBackground( Color.black );
        topPart.add( viewHighScores, FlowLayout.LEFT );
        topPart.add( lifeCount, FlowLayout.CENTER );
        topPart.add( scoreLabel, FlowLayout.RIGHT );

        // Whole Panel adds the game display to the center and the topPanel to
        // the north using border layout.
        JPanel wholePanel = new JPanel();
        wholePanel.setLayout( new BorderLayout() );
        wholePanel.add( topPart, "North" );
        wholePanel.add( gameDisplay, "Center" );

        // JFrame assigned to the whole panel
        this.setContentPane( wholePanel );
        this.pack();

        // ActionListeners assigned for Buttons.
        startGameButton.addActionListener( control );
        startGameButton.setActionCommand( "startGameButton" );
        viewHighScores.addActionListener( control );
        viewHighScores.setActionCommand( "viewHighScores" );

    }


    /**
     * 
     * This sets the startGameButton's text
     * 
     * @param text
     *            text to set for the startGameButton
     */
    public void setStartGameButtonText( String text )
    {
        startGameButton.setText( text );
    }


    /**
     * 
     * This method sets whether the startGameButton is enabled or not. It will
     * be disabled when the user is playing the game.
     * 
     * @param bool
     *            whether or not to enable the startGameButton
     */
    public void enableStartGameButton( boolean bool )
    {
        startGameButton.setEnabled( bool );
    }


    /**
     * 
     * This method returns the viewHighScores Button.
     * 
     * @return viewHighScores Button
     */
    public AbstractButton getScoreButton()
    {
        return viewHighScores;
    }


    /**
     * 
     * This method returns the startGameButton.
     * 
     * @return startGameButton
     */
    public AbstractButton getMultiButton()
    {
        return startGameButton;
    }


    /**
     * Launches the Asteroids Game.
     * 
     * @param args
     *            default arguments
     * 
     * @throws IOException
     *             FileNotFound Exception
     */
    public static void main( String[] args ) throws IOException
    {
        new AsteroidsGame();
    }

}