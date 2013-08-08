/*
 * BoggleServer.java
 *
 * Created on March 25, 2006, 8:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.erici.boggle.server;

import com.erici.boggle.game.Settings;
import com.erici.boggle.server.data.GameServer;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author Eric Internicola
 */
public class BoggleServer
{

    public BoggleServer() throws IOException
    {
        new GameServer(Integer.parseInt(System.getProperty(Settings.GAME_PORT)));
    }

    /** Creates a new instance of BoggleServer */
    public BoggleServer(String[] args) throws IOException
    {
        if(args.length > 1)
        {
            System.setProperty(Settings.GAME_TIMER, args[1]);
        }
        else if(args.length > 0)
        {
            if(args[0].toLowerCase().indexOf("-h") > -1)
            {
                help();
            }
            else
            {
                new GameServer(Integer.valueOf(args[0]));
            }
        }
        else
        {
            new GameServer();
        }
    }

    /**
     * Help information for this server.
     */
    public void help()
    {
        System.out.println("Sample Usage:  \n" +
                "BoggleServer -h\t\tdisplay this help message\n" +
                "BoggleServer [port] [game time]\n\n" +
                "port - The port that the Boggle Server should run on\n" +
                "game time - The time that each game will run.\n\n" +
                "If you run Boggle with no parameters, it will randomly select a port to run on and default the game time " +
                "to 90 seconds.");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(), "System.out"));
        new BoggleServer(args);
    }
}
