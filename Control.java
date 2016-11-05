import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


/**
 * 
 * This is the main class of the Asteroids Game that will monitor and overlook
 * all the occurrences of the Asteroids Game. It will call Display's appropriate
 * methods to make changes to the display of the game when needed. There will be
 * a timer that goes off every 32 milliseconds that refreshes the display and
 * calls repaint.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class Control
                implements
                ActionListener,
                KeyListener,
                CollisionListener,
                CountdownTimerListener
{
    private boolean pause = false; // whether to pause

    private Random random; // Random object

    private Timer refresher; // timer for when to refresh game display

    private int amountOfAsteroids; // amount of asteroids

    private int points; // points obtained by user

    private int remainingLives; // lives remaining

    private int asteroidsDestroyed; // Asteroids destroyed by player

    private int progressCount; // count of progress made throughout game

    private HashSet<Integer> keysPressed; // keys pressed by user

    private boolean gunReadyToShoot; // whether users gun is ready to shoot once
                                     // again

    private SpaceShip spaceShip; // spaceship of player

    private AsteroidsGame game; // Game object

    private Display display; // game's display


    /**
     * This creates a control object that will control the occurrences of the
     * Game.
     * 
     * @param display
     *            Display object for control class to manipulate.
     * @param game
     *            Game object for control class to control
     */
    public Control( Display display, AsteroidsGame game )
    {
        // Sets up display, game, random object, and hash set to represent
        // keys pressed.
        this.display = display;
        this.game = game;
        random = new Random();
        keysPressed = new HashSet<Integer>();
        // Initializes amount of asteroids, progress count, and refresher which
        // will refresh game contents.
        amountOfAsteroids = 2;
        progressCount = 0;
        refresher = new Timer( 32, this );
        // Sets up ship and it's gunReadyToShoot boolean. Also initializes
        // points,
        // remaining lives, etc.
        gunReadyToShoot = true;
        spaceShip = new SpaceShip();
        asteroidsDestroyed = 0;
        remainingLives = 3;
        points = 0;
        // Displays splash screen and starts refresher.
        displaySplashScreen();
        refresher.start();
    }


    /**
     * This method will call the Display class's methods to visually paint the
     * splash screen for player.
     */
    private void displaySplashScreen()
    {
        // Sets up Display's JLabel's that will display the splash screen.
        display.clearContents();
        display.data.setText( "" );
        display.highScores.setText( "" );
        display.highScoresTitle.setText( "" );
        display.data.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
        display.scoreList.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );

        points = 0;
        remainingLives = 3;
        amountOfAsteroids = 2;
        game.getScoreButton().setText( "High Scores" );
        display.setTitleText( "Asteroids" );
        display.setStoryText( "<html><br>The Earth has entered an asteroid field, and countless asteroids are coming towards the Earth.<br>You are Earth's savior, and you must pilot a spacecraft that has advanced defense systems<br>to shoot down as many asteroids as you can before you die. It's a suicide mission!<br><br><br><br><br><br><br><br><br><br><br></html>" );
        display.setControlsText( "<html><br><br><br><br><br><br><br>Controls:<br>Up Arrow - Moves Ship Forward<br>Left Arrow - Rotates Direction of Ship Left<br>Right Arrow - Rotates Direction of Ship Right<br>SpaceBar - Shoots Bullets<br>P - Pause Game<br>You will have a 3 second spawn period<br>where you cannot be destroyed (in case you spawn<br>on an asteroid)</html>" );
        game.setStartGameButtonText( "Play Asteroids" );
        game.enableStartGameButton( true );
        spaceShip = new SpaceShip();
    }


    /**
     * Shows player game over display.
     */
    private void displayGameOver()
    {
        game.setStartGameButtonText( "Game Over" );
        new CountdownTimer( this, null, "", 2000 );
        display.removeKeyListener( this );
        display.removeCollisionListener( this );
    }


    /**
     * This method displays the Scores by first clearing current screen contents
     * and calling display's appropriate methods to display the top 15 high
     * scores.
     */
    public void displayScores()
    {
        game.getScoreButton().setText( "Play Again" );
        for ( int x = 0; x < display.getMovers().size(); x++ )
        {
            if ( display.getMovers()
                .get( x )
                .getClass()
                .getName()
                .equals( "UFO" ) )
            {
                ( (UFO)display.getMovers().get( x ) ).setTimerNull();
            }
        }
        display.clearContents();
        // Makes button text empty.
        game.setStartGameButtonText( "" );
        // Disables the start game button so players can't click on it.
        game.enableStartGameButton( false );
        try
        {
            display.showScores();
        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();
        }

    }


    /**
     * Get the number of progressions made such as going to splash screen, etc.
     * 
     * @return progressCount or how many progressions have been made.
     */
    public int getProgressCount()
    {
        return progressCount;
    }


    /**
     * Places enemies such as Asteroids and UFO's amount of them being based off
     * how far the player has gotten in the game.
     */
    private void displayEnemies()
    {
        int num = amountOfAsteroids++;
        Mover m;
        // This will basically add an asteroid per case for the greatest number
        // and only one for one 3 asteroids. Since num is incremented as time
        // goes on, More asteroids and other enemies are added as player goes on
        // and on.
        if ( num >= 3 )
        {
            m = new Asteroid( 0, 2, 100, 100 );
            m.setSpin( 2 * Math.PI * random.nextDouble() );
            m.setVelocity( 3, random.nextDouble() * 2 * Math.PI );
            display.addMover( m ); // Adds this mover to display's list of
                                   // movers.

            m = new Asteroid( 1, 2, 750 - 100, 100 );
            m.setSpin( 2 * Math.PI * random.nextDouble() );
            m.setVelocity( 3, random.nextDouble() * 2 * Math.PI );
            display.addMover( m );

            m = new Asteroid( 2, 2, 100, 750 - 100 );
            m.setSpin( 2 * Math.PI * random.nextDouble() );
            m.setVelocity( 3, random.nextDouble() * 2 * Math.PI );
            display.addMover( m );
        }
        if ( num >= 4 )
        {
            m = new Asteroid( 3, 2, 750 - 100, 750 - 100 );
            m.setSpin( 2 * Math.PI * random.nextDouble() );
            m.setVelocity( 3, random.nextDouble() * 2 * Math.PI );
            display.addMover( m );
        }
        if ( num >= 5 )
        {
            m = new Asteroid( 4, 2, 750 - 100, 750 - 100 );
            m.setSpin( 2 * Math.PI * random.nextDouble() );
            m.setVelocity( 3, random.nextDouble() * 2 * Math.PI );
            display.addMover( m );
        }
        // Player will not encounter UFO first wave but will get one in 2nd wave
        // and it will keep incrementing each wave.
        int n = amountOfAsteroids - 4;
        while ( n > 0 )
        {
            UFO u = new UFO( 200, 200, this );
            u.setSpin( Math.PI );
            u.setVelocity( 3, random.nextDouble() * 2 * Math.PI );
            display.addMover( u );
            shootUFOBullet( u );
            n--;
        }
    }


    /**
     * This method places the ship in the middle of the display.
     */
    private void displayShip()
    {
        gunReadyToShoot = true;
        spaceShip = new SpaceShip();

        spaceShip.setSpin( -Math.PI / 2 );
        // This sets the position to center
        spaceShip.setLocation( 750 / 2 + 20, 750 / 2 );
        display.addMover( spaceShip );
        new CountdownTimer( this, spaceShip, "hittable", 3000 );
    }


    /**
     * Sets up for a new game's initial screen.
     * 
     * @param startOver
     *            whether game is being restarted
     */
    private void initializeDisplay( boolean startOver )
    {

        // Clears the display
        display.clearContents();

        // Places the enemies, such as Asteroids and UFOs
        displayEnemies();

        if ( startOver )
        {
            // Resets the game stats
            remainingLives = 3;
            points = 0;
        }
        spaceShip = null;

        // Resets rest of game
        gunReadyToShoot = true;
        keysPressed.clear();
        asteroidsDestroyed = 0;

        // Places new ship
        displayShip();

        // Starts all listeners.
        display.removeCollisionListener( this );
        display.removeKeyListener( this );
        display.addCollisionListener( this );
        display.addKeyListener( this );

        // Display JPanel is given focus
        display.requestFocusInWindow();

    }


    /**
     * This method acts based off collision between two movers.
     * 
     * @param m1
     *            first mover involved in collision.
     * @param m2
     *            second mover involved in collision.
     */
    @Override
    public void moversCollided( Mover m1, Mover m2 )
    {
        // In case of collision between Asteroid and Bullet
        if ( m1 instanceof Asteroid && m2 instanceof Bullet )
        {
            asteroidCollision( (Asteroid)m1 );
            updateScore( m1 ); // Will add 10 points
            try
            {
                bulletCollision( (Bullet)m2 );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        else if ( m1 instanceof Bullet && m2 instanceof Asteroid )
        {
            asteroidCollision( (Asteroid)m2 );
            updateScore( m2 ); // Will add 10 points.
            try
            {
                bulletCollision( (Bullet)m1 );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        // In case of collision between Bullet and SpaceShip
        else if ( m1 instanceof Bullet && m2 instanceof SpaceShip )
        {
            shipCollision( (SpaceShip)m2 );
            try
            {
                bulletCollision( (Bullet)m1 );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        else if ( m1 instanceof SpaceShip && m2 instanceof Bullet )
        {
            shipCollision( (SpaceShip)m1 );
            try
            {
                bulletCollision( (Bullet)m2 );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        // In case of collision between Asteroid and SpaceShip
        else if ( m1 instanceof Asteroid && m2 instanceof SpaceShip )
        {
            asteroidCollision( (Asteroid)m1, (SpaceShip)m2 );
            updateScore( m1 ); // Adds 10 points.
            shipCollision( (SpaceShip)m2 );
        }
        else if ( m1 instanceof SpaceShip && m2 instanceof Asteroid )
        {
            asteroidCollision( (Asteroid)m2, (SpaceShip)m1 );
            updateScore( m2 ); // Adds 10 points.
            shipCollision( (SpaceShip)m1 );
        }
        // In case of collision between UFO and Bullet
        else if ( m1 instanceof UFO && m2 instanceof Bullet )
        {
            ufoCollision( (UFO)m1 );
            updateScore( m1 ); // Adds 20 points.
            try
            {
                bulletCollision( (Bullet)m2 );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        else if ( m1 instanceof Bullet && m2 instanceof UFO )
        {
            ufoCollision( (UFO)m2 );
            updateScore( m2 ); // Adds 20 points.
            try
            {
                bulletCollision( (Bullet)m1 );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        // In case of collision between UFO and SpaceShip
        else if ( m1 instanceof UFO && m2 instanceof SpaceShip )
        {
            shipCollision( (SpaceShip)m2 );
            updateScore( (UFO)m1 );
            ufoCollision( (UFO)m1 );
        }
        else if ( m1 instanceof SpaceShip && m2 instanceof UFO )
        {
            shipCollision( (SpaceShip)m1 );
            updateScore( (UFO)m2 );
            ufoCollision( (UFO)m2 );
        }
    }


    /**
     * Shows SpaceJunk of collision involving the SpaceShip.
     * 
     * @param s
     *            SpaceShip involved in collision.
     */
    private void makeSpaceShipJunk( SpaceShip s )
    {
        // Instantiates Space Junk object to be added as result of collision
        // based off location of ship.
        Mover spaceJunk = new SpaceJunk( s.getX(), s.getY(), 2 );
        spaceJunk.setVelocity( random.nextDouble() / 2,
            s.getSpin() + random.nextInt( 10 ) );
        // SpaceJunk removed after certain amount of time.
        new CountdownTimer( this, spaceJunk, "", 1300 + random.nextInt( 500 ) );
        display.addMover( spaceJunk ); // Adds to list of movers.

        spaceJunk = new SpaceJunk( s.getX(), s.getY(), 2 );
        spaceJunk.setVelocity( random.nextDouble() / 2,
            s.getSpin() + random.nextInt( 10 ) );
        new CountdownTimer( this, spaceJunk, "", 1300 + random.nextInt( 500 ) );
        display.addMover( spaceJunk );

        spaceJunk = new SpaceJunk( s.getX(), s.getY(), 2 );
        spaceJunk.setVelocity( random.nextDouble() / 2,
            s.getSpin() + random.nextInt( 10 ) );
        new CountdownTimer( this, spaceJunk, "", 1300 + random.nextInt( 500 ) );
        display.addMover( spaceJunk );
    }


    /**
     * Makes SpaceJunk for Asteroid collision.
     * 
     * @param a
     *            Asteroid involved in collision.
     */
    private void makeAsteroidJunk( Asteroid a )
    {
        // Creates 8 pieces of spaceJunk after asteroid collision.
        for ( int i = 0; i < 8; i++ )
        {
            // Instantiates spaceJunk based off location of Asteroid.
            Mover spaceJunk = new SpaceJunk( a.getX(), a.getY(), 0 );
            spaceJunk.setVelocity( random.nextDouble(),
                a.getSpin() + random.nextInt( 10 ) );
            // CountDown timer for when to remove spaceJunk.
            new CountdownTimer( this,
                spaceJunk,
                "",
                1300 + random.nextInt( 500 ) );
            display.addMover( spaceJunk );
        }
    }


    /**
     * 
     * This method displays the spaceJunk or debris for the UFO collision.
     * 
     * @param u
     *            ufo involved in collision
     */
    private void makeUFOJunk( UFO u )
    {
        // Instantiates spaceJunk based off location of ufo.
        Mover spaceJunk = new SpaceJunk( u.getX(), u.getY(), 1 );
        spaceJunk.setVelocity( random.nextDouble() / 2,
            u.getSpin() + random.nextInt( 10 ) );
        // CountDown timer for when to get rid of debris
        new CountdownTimer( this, spaceJunk, "", 1300 + random.nextInt( 500 ) );
        display.addMover( spaceJunk );

        spaceJunk = new SpaceJunk( u.getX(), u.getY(), 1 );
        spaceJunk.setVelocity( random.nextDouble() / 2,
            u.getSpin() + random.nextInt( 10 ) );
        new CountdownTimer( this, spaceJunk, "", 1300 + random.nextInt( 500 ) );
        display.addMover( spaceJunk );

        spaceJunk = new SpaceJunk( u.getX(), u.getY(), 1 );
        spaceJunk.setVelocity( random.nextDouble() / 2,
            u.getSpin() + random.nextInt( 10 ) );
        new CountdownTimer( this, spaceJunk, "", 1300 + random.nextInt( 500 ) );
        display.addMover( spaceJunk );
    }


    /**
     * 
     * This method is called when there is a UFO collision. It calls
     * makeUFODebris and it removes the UFO from list of movers.
     * 
     * @param u
     *            UFO involved in collision.
     */
    private void ufoCollision( UFO u )
    {
        makeUFOJunk( u ); // Shows debris or spaceJunk.
        display.removeMover( u ); // Removes from list of movers.
        u.setTimerNull(); // sets ufo's timer to null so it won't continue to
                          // shoot bullets.
    }


    /**
     * This method is called when there is a collision involving a SpaceShip.
     * 
     * @param s
     *            SpaceShip involved in collision.
     */
    private void shipCollision( SpaceShip s )
    {
        if ( s.hittable == true )
        {
            // Makes ship collision spaceJunk
            makeSpaceShipJunk( s );

            // Remove the ship from list of movers in display. Sets spaceShip to
            // null and decrements remainingLives.
            display.removeMover( s ); // Removes mover from screen.
            spaceShip = null;
            remainingLives--; // Decrements remainingLives.

            // Start timer for next round or wave.
            new ProgressTimer( 2500, progressCount, this );
        }
    }


    /**
     * This method will be called when there is a collision involving an
     * Asteroid.
     * 
     * @param a
     *            Asteroid involved in collision.
     * @param m2
     */
    private void asteroidCollision( Asteroid a, SpaceShip m2 )
    {
        if ( m2.hittable )
        {
            asteroidsDestroyed++;

            // Starts timer to check if wave of enemies is over.
            if ( asteroidsDestroyed == 28 )
            {
                new ProgressTimer( 4000, progressCount, this );
            }
            // Make asteroid spaceJunk
            makeAsteroidJunk( a );

            // The asteroid is removed from the list of movers and then the
            // display.
            display.removeMover( a );

            // Create the two smaller asteroids that the asteroid is broken down
            // into.
            int size = a.getSize();
            size = size - 1;
            if ( size >= 0 )
            {
                int speed = 5 - size;
                Asteroid a1 = new Asteroid( random.nextInt( 4 ),
                    size,
                    a.getX(),
                    a.getY() );
                Asteroid a2 = new Asteroid( random.nextInt( 4 ),
                    size,
                    a.getX(),
                    a.getY() );
                // Assigns random velocity and spin for the asteroids
                a1.setVelocity( speed, random.nextDouble() * 2 * Math.PI );
                a2.setVelocity( speed, random.nextDouble() * 2 * Math.PI );
                a1.setSpin( 2 * Math.PI * random.nextDouble() );
                a2.setSpin( 2 * Math.PI * random.nextDouble() );
                display.addMover( a1 );
                display.addMover( a2 );
            }
        }
    }


    /**
     * This method will be called when there is a collision involving an
     * Asteroid.
     * 
     * @param a
     *            Asteroid involved in collision.
     * @param m2
     */
    private void asteroidCollision( Asteroid a )
    {

        asteroidsDestroyed++;

        // Starts timer to check if wave of enemies is over.
        if ( asteroidsDestroyed == 28 )
        {
            new ProgressTimer( 4000, progressCount, this );
        }
        // Make asteroid spaceJunk
        makeAsteroidJunk( a );

        // The asteroid is removed from the list of movers and then the
        // display.
        display.removeMover( a );

        // Create the two smaller asteroids that the asteroid is broken down
        // into.
        int size = a.getSize();
        size = size - 1;
        if ( size >= 0 )
        {
            int speed = 5 - size;
            Asteroid a1 = new Asteroid( random.nextInt( 4 ),
                size,
                a.getX(),
                a.getY() );
            Asteroid a2 = new Asteroid( random.nextInt( 4 ),
                size,
                a.getX(),
                a.getY() );
            // Assigns random velocity and spin for the asteroids
            a1.setVelocity( speed, random.nextDouble() * 2 * Math.PI );
            a2.setVelocity( speed, random.nextDouble() * 2 * Math.PI );
            a1.setSpin( 2 * Math.PI * random.nextDouble() );
            a2.setSpin( 2 * Math.PI * random.nextDouble() );
            display.addMover( a1 );
            display.addMover( a2 );
        }
    }


    /**
     * A bullet is involved in a collision.
     * 
     * @param b
     *            bullet involved in collision.
     * @throws IOException
     *             FileNotFound Exception
     */
    public void bulletCollision( Bullet b ) throws IOException
    {
        // Removes the bullet from the list of movers and then the display.
        display.removeMover( b );
    }


    /**
     * 
     * This method shoots bullets to a UFO's right while it survives the bullets
     * of the player.
     * 
     * @param u
     *            UFO to shoot bullet for
     */
    public void shootUFOBullet( UFO u )
    {
        // Creates bullet object based off location of UFO's gun.
        Bullet bullet = new Bullet( u.getGunX(), u.getGunY() );
        bullet.setVelocity( 15, 0 * Math.PI );
        display.addMover( bullet );
        // Sets timer for when to get rid of bullet
        new CountdownTimer( this, bullet, "", 750 );
    }


    /**
     * Shoots a bullet from specified SpaceShip's gun.
     * 
     * @param s
     *            SpaceShip that is shooting a bullet
     */
    public void shootBullet( SpaceShip s )
    {
        // Creates a bullet based off of the location of the ship's gun.
        Mover bullet = new Bullet( s.getGunX(), s.getGunY() );
        bullet.setVelocity( 15, s.getSpin() ); // Shoots bullet at velocity of
                                               // 15 going at direction
                                               // spaceship facing.
        display.addMover( bullet );
        // Sets timer for when to get rid of bullet
        new CountdownTimer( this, bullet, "", 750 );
    }


    /**
     * This method is called when an action is performed for example press of
     * button or refresher timer going off.
     * 
     * @param e
     *            action event triggered
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        // The start button has been pressed. Stop whatever we're doing
        // and bring up the initial display
        if ( e.getSource() instanceof JButton )
        {
            // Start Game Button has been pressed.
            if ( e.getActionCommand().equals( "startGameButton" ) )
            {
                // Sets up new game
                progressCount++;
                display.clearContents();
                game.setStartGameButtonText( "" );
                game.enableStartGameButton( false );
                initializeDisplay( true );
            }
            // View High Scores Button has been pressed.
            else if ( e.getActionCommand().equals( "viewHighScores" ) )
            {
                if ( game.getScoreButton().getText().equals( "Play Again" ) )
                {
                    displaySplashScreen();
                }
                else
                {
                    displayScores();
                }
            }
        }
        // Refresher Timer has been triggered.
        else if ( e.getSource() == refresher )
        {
            // Iterates through keysPressed HashSet and calls Ship's methods
            // accordingly.
            for ( Integer i : keysPressed )
            {
                if ( i == KeyEvent.VK_UP )
                {
                    if ( spaceShip != null )
                    {
                        spaceShip.speedUp( 0.5 );
                    }
                }
                else if ( i == KeyEvent.VK_LEFT )
                {
                    if ( spaceShip != null )
                    {
                        spaceShip.spin( -Math.PI / 16 );
                    }
                }
                else if ( i == KeyEvent.VK_RIGHT )
                {
                    if ( spaceShip != null )
                    {
                        spaceShip.spin( Math.PI / 16 );
                    }
                }
                else if ( i == KeyEvent.VK_SPACE )
                {
                    if ( spaceShip != null && gunReadyToShoot )
                    {
                        shootBullet( spaceShip );
                        new CountdownTimer( this, spaceShip, "", 125 );
                        gunReadyToShoot = false;
                    }
                }
            }

            // Updates Display of remaining lives and points of player.
            AsteroidsGame.lifeCount.setText( "Lives:  " + remainingLives );
            AsteroidsGame.scoreLabel.setText( "Points: " + points );

            // Refreshes display of game.
            if ( display.getMovers().size() == 1 )
            {
                displayEnemies();
            }
            display.refresh(); // Calls display's repaint method redrawing the
                               // JPanel based off changes made per frame, or
                               // every time this method is called.
        }
    }


    /**
     * This updates score based off enemy hit. Gives 10 points per Asteroid and
     * 20 points per UFO.
     * 
     * @param enemyHit
     *            Enemy that is hit.
     */
    private void updateScore( Mover enemyHit )
    {
        if ( enemyHit instanceof Asteroid )
        {
            points += 10;
        }
        else if ( enemyHit instanceof UFO )
        {
            points += 20;
        }
    }


    /**
     * Based on the state of the control class it performs each progression.
     */
    public void performTransition()
    {

        // Records that progression has been made.
        progressCount++;

        // If no lives, display game over and end game.
        if ( remainingLives == 0 )
        {
            displayGameOver();
        }
        // If the asteroids destroyed equal the amount needed to be destroyed
        // and spaceship is dead it will progress player to next wave.
        else if ( asteroidsDestroyed == 28 && spaceShip == null )
        {
            initializeDisplay( false );
        }

        // If spaceship is destroyed and player still has lives displays another
        // spaceship.
        else
        {
            if ( spaceShip == null && remainingLives > 0 )
            {
                displayShip();
            }
        }
    }


    /**
     * Adds KeyEvents To keysPressed Hash Set
     * 
     * @param e
     *            Key pressed
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_P )
        {
            pause = !pause;
            if ( pause )
            {
                refresher.stop();
            }
            else
            {
                refresher.start();
            }
        }
        if ( e.getKeyCode() == KeyEvent.VK_UP )
        {
            keysPressed.add( KeyEvent.VK_UP );
        }
        else if ( e.getKeyCode() == KeyEvent.VK_LEFT )
        {
            keysPressed.add( KeyEvent.VK_LEFT );
        }
        else if ( e.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            keysPressed.add( KeyEvent.VK_RIGHT );
        }
        else if ( e.getKeyCode() == KeyEvent.VK_SPACE )
        {
            if ( gunReadyToShoot )
            {
                keysPressed.add( KeyEvent.VK_SPACE );
            }
        }
    }


    /**
     * Removes KeyEvents from keysPressed Hash Set
     * 
     * @param e
     *            Key just released.
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_UP )
        {
            keysPressed.remove( KeyEvent.VK_UP );
        }
        else if ( e.getKeyCode() == KeyEvent.VK_LEFT )
        {
            keysPressed.remove( KeyEvent.VK_LEFT );
        }
        else if ( e.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            keysPressed.remove( KeyEvent.VK_RIGHT );
        }
        else if ( e.getKeyCode() == KeyEvent.VK_SPACE )
        {
            keysPressed.remove( KeyEvent.VK_SPACE );
        }
    }


    /**
     * Nothing is typed, so nothing is done.
     * 
     * @param e
     *            KeyEvent of typed keys.
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        // Don't do anything.
    }


    /**
     * This method is called when the CountDownTimer's time reaches 0.
     * 
     * @param m
     *            Mover which has had its CountDownTimer Expired.
     */
    public void timeExpired( Mover m, String x )
    {
        String temp = x;

        // If Mover is Bullet it is removed.
        if ( m instanceof Bullet )
        {
            display.removeMover( m );
        }
        // If Mover is SpaceJunk it is removed.
        else if ( m instanceof SpaceJunk )
        {
            display.removeMover( m );
        }
        // If Mover is SpaceShip the gun is readyToShoot.
        else if ( m instanceof SpaceShip )
        {
            if ( m == spaceShip )
            {
                gunReadyToShoot = true;
                if ( temp == "hittable" )
                {
                    m.hittable = true;
                }
            }
        }
        // If Mover is null then the Game is Over and user is prompted for their
        // name to display on HighScores.
        else if ( m == null )
        {
            String name = JOptionPane.showInputDialog( null, "Enter your name" );
            if ( name != null && !name.equals( "" ) )
            {
                try
                {
                    // Adds the Score to the ScoreLinkedList and then those
                    // contents are written to the scores.txt file to save.
                    ScoreLinkedList s = new ScoreLinkedList();
                    s.newHighScore( name, points ); // Writes new high score to
                                                    // data structure.
                    s.writeScoresToFile(); // Writes contents of data structure
                                           // to file.
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
            displayScores(); // Displays High Score Page.
        }
    }
}