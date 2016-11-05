import java.applet.Applet;
import java.io.IOException;

import javax.swing.JFrame;


public class AsteroidsApplet extends Applet
{
    public void init()
    {
        try
        {
            System.out.println( "COMPLETED" );
            add( new AsteroidsGame() );

        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
