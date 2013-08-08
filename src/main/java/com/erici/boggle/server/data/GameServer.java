/*
 * GameServer.java
 *
 * Created on March 25, 2006, 9:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.server.data;

import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.listeners.BoggleTimerListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;

/**
 *
 * @author Eric Internicola
 */
public class GameServer extends Thread
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private ServerSocket    sockListen  = null;
    private BoggleGame      game        = null;
    private boolean         keepRunning = true;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Default Constructor - start the server on an OS assigned port.
     * @throws IOException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public GameServer() throws IOException
    {
        this(0);
    }
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Start the gameserver a specific port.
     * @param port The port to start the server on.
     * @throws IOException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public GameServer(int port) throws IOException
    {
        sockListen = new ServerSocket(port,2);
        
        Logger.getRootLogger().info("Boggle Server running on port " + sockListen.getLocalPort());
        
        start();
    }
    
    //==========================================================================
    // METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // run
    //--------------------------------------------------------------------------
    /**
     * This method stays in a loop (until "killServer()" is called) and awaits
     * connections.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void run()
    {
        while(keepRunning)
        {
            try
            {
                awaitConnections();
            }
            catch(IOException ex)
            {
                Logger.getLogger(getClass()).fatal("Caught IOException waiting for connections; bailing",ex);
            }
        }
    }
    
    
    //--------------------------------------------------------------------------
    // killServer
    //--------------------------------------------------------------------------
    /**
     * This method closes the Server Socket, and sets "keepRunning" to false so
     * that the thread will die naturally.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void killServer()
    {
        keepRunning = false;
        
        try
        {
            Logger.getLogger(getClass()).debug("Killing Server");
            sockListen.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).warn("Error",ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // awaitConnections
    //--------------------------------------------------------------------------
    /**
     * This method is the method that's responsible for listening for connections
     * and then managing a game for them.
     * @throws IOException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void awaitConnections() throws IOException
    {
        Socket sock = sockListen.accept();
        Logger.getRootLogger().debug("Got player1");
        
        RemotePlayer player = null;
        
        try
        {
            player = new RemotePlayer(sock);
            Logger.getLogger(getClass()).debug("Player1 = " + player.getPlayerName());
            game = new BoggleGame(player);
        }
        catch (ReadingException ex)
        {
            Logger.getLogger(getClass()).info("Player 1 disconnected before player 2 connected: " + ex.getMessage());
            sock.close();
            return;
        }
        
        boolean tryAgain = true;
        
        while(tryAgain)
        {
            sock = sockListen.accept();
            try
            {
                player = new RemotePlayer(sock);
                tryAgain = false;
                Logger.getLogger(getClass()).debug("Player2 = " + player.getPlayerName());
                game.setPlayer2(player);
            }
            catch (ReadingException ex)
            {
                Logger.getLogger(getClass()).debug("Player 2 disconnected before sending name: " + ex.getMessage());
                sock.close();
            }
        }
        
        try
        {   
            sleep(1000);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
        
        game.start();
        
        try
        {
            game.join();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * This method gives you back the port that the server is running on.
     */
    public int getPort()
    {
        return sockListen.getLocalPort();
    }
}
