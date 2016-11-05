import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * 
 * This is the data structure that will hold the LinkedList of all the High
 * Scores. It will read in the Scores from scores.txt each time it is
 * instantiated since the data structures are cleared during a new run. It also
 * has a method to write the contents of the data structure to the file.
 *
 * @author Kaushik, Mihir, Karthick
 * @version May 30, 2015
 * @author Period: 6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class ScoreLinkedList
{
    private LinkedList<Score> scores; // LinkedList of High Scores.


    /**
     * This constructor will read all of the contents in scores.txt and put it
     * back in the LinkedList since its contents are cleared each run of the
     * game.
     * 
     * @throws FileNotFoundException
     *             if File is not found
     */
    public ScoreLinkedList() throws FileNotFoundException
    {
        scores = new LinkedList<Score>();
        Scanner scan = new Scanner( new File( "scores.txt" ) ); // Scanner
        while ( scan.hasNext() )
        {
            String s = scan.nextLine();
            if ( s.contains( " " ) )
            {
                // username and Score are separated by a space.
                int index = s.indexOf( " " );
                // Adds new high scores to LinkedList
                newHighScore( s.substring( 0, index ),
                    Integer.parseInt( s.substring( index + 1 ).trim() ) );
            }
        }
        scan.close();
    }


    /**
     * 
     * This method will clear the contents of the file at the moment and replace
     * it with the new contents of the data structure.
     */
    public void writeScoresToFile()
    {
        clearFileContents(); // Clears current file contents.
        try
        {
            // Writes out the scores by the format: username, space, score.
            FileWriter out = new FileWriter( "scores.txt", true );
            BufferedWriter writer = new BufferedWriter( out );
            Iterator<Score> iter = scores.iterator();
            while ( iter.hasNext() )
            {
                Score top = iter.next();
                writer.write( top.getUsername() + " " + top.getScore() );
                writer.newLine();
            }
            writer.close();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }


    /**
     * 
     * This returns the LinkedList of Scores
     * 
     * @return LinkedList of high scores.
     */
    public LinkedList<Score> getScoresLinkedList()
    {
        return scores;
    }


    /**
     * 
     * This method adds a new high score to the LinkedList and then calls
     * Collections.sort to sort the contents based off the comparator defined in
     * Score class.
     * 
     * @param name
     *            player's name
     * @param score
     *            score player got
     */
    public void newHighScore( String name, Integer score )
    {
        scores.add( new Score( name, score ) );
        Collections.sort( scores, comp );
    }


    /**
     * 
     * This method clears the contents of the file, since FileWriter's second
     * parameter is false.
     */
    private void clearFileContents()
    {
        FileWriter out;
        try
        {
            out = new FileWriter( "scores.txt", false );
            BufferedWriter writer = new BufferedWriter( out );
            writer.write( "" );
            writer.close();
            out.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

    }


    /**
     * 
     * This method clears the contents of the LinkedList of Scores.
     */
    public void clearLinkedListContents()
    {
        while ( !scores.isEmpty() )
        {
            scores.remove();
        }
    }

    /**
     * This is the Comparator that will be used to compare two scores.
     */
    public Comparator<Score> comp = new Comparator<Score>()
    {
        public int compare( Score score1, Score score2 )
        {
            int s1 = score1.getScore();
            int s2 = score2.getScore();
            if ( s1 > s2 )
            {
                return -1;
            }
            else if ( s1 < s2 )
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    };
}
