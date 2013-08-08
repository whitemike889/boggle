/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erici.boggle;

import com.erici.boggle.client.tools.BoggleBoardController;
import com.erici.boggle.game.Settings;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.server.data.GameServer;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sourceforge.napkinlaf.NapkinLookAndFeel;
import org.apache.log4j.Logger;

/**
 *
 * @author intere
 */
public class Main
{

    public static final String MODE_SERVER = "-server";
    public static final String MODE_CLIENT = "-client";
    public static final String MODE_HELP = "-h";
    public static final int RETURN_NO_ERROR = 0;
    public static final int RETURN_NO_ARGS = -1;
    public static final int RETURN_MISSING_ARGS = -2;
    private String mode;
    private String port;
    private String host;
    private String username;
    private String gameTime;

    public Main()
    {
        setMode(MODE_HELP);
        System.setProperty(Settings.GAME_PORT, "0");
    }

    public static void main(String[] args) throws IOException, UnknownHostException, WritingException
    {
        Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(), "System.out"));
        Main m = new Main();
        m.parseArgs(args);
    }

    public static void usage(int returnValue)
    {
        System.out.println("Usage Information:");
        System.out.println("java -jar Boggle options");
        System.out.println("Options:");
        System.out.println("Options can be one of:");
        System.out.println();

        System.out.println(Properties.GAMESERVER + " <hostname or ip>");
        System.out.println("\tTells the client what the game server hostname (or ip) is.");
        System.out.println("\tThe \"hostname or ip\" is the host to connect to.");
        System.out.println();

        System.out.println(Properties.PORT + " <port number>");
        System.out.println("\tTells the client what port the game server is running on, or tells the server what port to run on.");
        System.out.println("\tThe \"port number\" is the port to run on.");
        System.out.println();

        System.out.println(Properties.TIME + " <game length>");
        System.out.println("\tTells the server how long the game should run.");
        System.out.println("\tthe \"game length\" is specified in seconds");
        System.out.println();

        System.out.println(Properties.USERNAME + " <username>");
        System.out.println("\tTells the client what to use as a username.");

        System.exit(returnValue);
    }

    /** 
     * This method parses the command line arguments.
     */
    public void parseArgs(String[] args) throws IOException, UnknownHostException, WritingException
    {

        if(args.length == 0)
        {
            usage(RETURN_NO_ARGS);
        }
        else
        {
            parseProperties(args);

            if(MODE_HELP.equals(getMode()))
            {
                usage(RETURN_NO_ERROR);
            }
            else if(MODE_CLIENT.equals(getMode()))
            {
                startClient();
            }
            else if(MODE_SERVER.equals(getMode()))
            {
                new GameServer();
            }
        }
    }

    /**
     * This method starts up the Client, including the Look and Feel.
     */
    protected void startClient() throws UnknownHostException, IOException, WritingException
    {
        try
        {
            UIManager.setLookAndFeel(new NapkinLookAndFeel());
        }
        catch(UnsupportedLookAndFeelException ex)
        {
            System.out.println("Could not load Napkin Look and feel");
        }

        new BoggleBoardController();
    }

    /** This method sets a system property and increments and returns the index.
    @param propertyName The name of the System Property to set.
    @param value The value of the System Property to set.
    @param index The index to increment.
    @return The incremented Index.
     */
    protected int setProperty(String propertyName, String value, int index)
    {
        System.out.println("Setting property\"" + propertyName + "\" to \"" + value + "\".");
        System.getProperties().setProperty(propertyName, value);
        return ++index;
    }

    /**
    This method parses the command line parameters to determine if they pertain
    to a specific property or not.  If they do, they are assigned to the
    corresponding System Property.  Otherwise, they're ignored.
    @param args The Command Line arguments to parse.
     */
    public void parseProperties(String[] args)
    {
        for(int i = 0; i < args.length; i++)
        {
            if(Properties.PORT.equals(args[i]))
            {
                i = setProperty(Settings.GAME_PORT, args[i + 1], i);
            }
            else if(Properties.TIME.equals(args[i]))
            {
                i = setProperty(Settings.GAME_TIMER, args[i+1], i);
            }
            else if(Properties.USERNAME.equals(args[i]))
            {
                i = setProperty(Settings.USER_NAME, args[i+1], i);
            }
            else if(Properties.GAMESERVER.equals(args[i]))
            {
                i = setProperty(Settings.GAME_SERVER, args[i+1], i);
            }
            else if(MODE_CLIENT.equals(args[i]) || MODE_HELP.equals(args[i]) || MODE_SERVER.equals(args[i]))
            {
                setMode(args[i]);
            }
        }
    }

    public String getHost()
    {
        return host;
    }

    public String getMode()
    {
        return mode;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getPort()
    {
        return port;
    }

    public String getUsername()
    {
        return username;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getGameTime()
    {
        return gameTime;
    }

    public void setGameTime(String gameTime)
    {
        this.gameTime = gameTime;
    }

    public interface Properties
    {

        public static final String PORT             = "-port";
        public static final String USERNAME         = "-username";
        public static final String GAMESERVER       = "-gameserver";
        public static final String TIME             = "-time";
    }
}
