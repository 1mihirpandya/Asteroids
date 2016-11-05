/**
 * 
 * This class represents a Score. It has two instance variables being the
 * username of the player and the score they obtained. It also implements a
 * comparator that is based off comparing the score int.
 *
 * @author Kaushik, Karthick, Mihir
 * @version May 30, 2015
 * @author Period:6
 * @author Assignment: Asteroids
 *
 * @author Sources: none
 */
public class Score implements Comparable<Score>
{
    private String username; // Username of Player.(Prompted for name in Game
                             // Over screen)

    private int score; // Score the player got.


    /**
     * This constructor sets the instance variables, username and score.
     * 
     * @param username
     *            username of player.
     * @param score
     *            score player got.
     */
    public Score( String username, int score )
    {
        this.username = username;
        this.score = score;
    }


    /**
     * 
     * This is the getter for the username.
     * 
     * @return username of player.
     */
    public String getUsername()
    {
        return username;
    }


    /**
     * 
     * This is the getter for the score.
     * 
     * @return score player got.
     */
    public int getScore()
    {
        return score;
    }


    /**
     * This is the compareTo method that is simply based off comparing two Score
     * objects' int value for score.
     */
    @Override
    public int compareTo( Score other )
    {
        return this.score - other.getScore();
    }
}
