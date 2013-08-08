/*
 * BoggleGame.java
 *
 * Created on March 25, 2006, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.server.data;

import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.BoggleGameModel;
import com.erici.boggle.game.BoggleTimer;
import com.erici.boggle.game.GameStatistics;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.io.transactions.ErrorTransaction;
import com.erici.boggle.io.transactions.GameBoardTransaction;
import com.erici.boggle.io.transactions.GameStatsTransaction;
import com.erici.boggle.io.transactions.NameTransaction;
import com.erici.boggle.io.transactions.TimerTransaction;
import com.erici.boggle.listeners.BoggleTimerListener;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author Eric Internicola
 */
public class BoggleGame extends Thread implements BoggleTimerListener
{
    private BoggleGameModel     model       = new BoggleGameModel(new BoggleDice());
    private boolean             complete    = false;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets the First Player.
     * @param player1 The First Player.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleGame(RemotePlayer player1)
    {
        setPlayer1(player1);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // run
    //--------------------------------------------------------------------------
    /**
     * This is the method that is called from the "start" method of the Thread.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void run()
    {
        try
        {
            startGame();
        }
        catch (WritingException ex)
        {
            Logger.getLogger(getClass()).warn("Error Starting Game: " + ex.getMessage(),ex);
        }
        catch (DocumentCreationException ex)
        {
            Logger.getLogger(getClass()).warn("Error Starting Game: " + ex.getMessage(),ex);
        }
        
        model.setTimer(new BoggleTimer());
        model.getTimer().addListener(this);
        model.getTimer().start();
        
        try
        {
            model.getTimer().join();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(getClass()).warn("Boggle Game Interrupted: " + ex.getMessage(), ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // startGame
    //--------------------------------------------------------------------------
    /**
     * This method  makes the calls that are necessary for starting the game.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void startGame() throws DocumentCreationException, WritingException
    {
        Logger.getLogger(getClass()).debug("Server is starting game");
        sendNames();
        sendGame();
        getPlayer1().startClientListening();
        getPlayer2().startClientListening();
    }
    
    //--------------------------------------------------------------------------
    // sendGame
    //--------------------------------------------------------------------------
    /**
     * This method sends the gameboard to both of the opponents.
     * @throws WritingException
     * @throws DocumentCreationException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void sendGame()  throws WritingException, DocumentCreationException
    {
        getModel().getDice().rollDice();
        GameBoardTransaction trans = new GameBoardTransaction(getModel().getDice());
        getPlayer1().getWriter().writeTransaction(trans);
        getPlayer2().getWriter().writeTransaction(trans);
    }
    
    //--------------------------------------------------------------------------
    // sendNames
    //--------------------------------------------------------------------------
    /**
     * This method sends the Players names out to each of the opponents.
     * @throws WritingException
     * @throws DocumentCreationException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void sendNames() throws WritingException, DocumentCreationException
    {
        NameTransaction name = new NameTransaction(getPlayer1().getPlayerName());
        getPlayer2().getWriter().writeTransaction(name);
        
        name = new NameTransaction(getPlayer2().getPlayerName());
        getPlayer1().getWriter().writeTransaction(name);
    }
    
    //--------------------------------------------------------------------------
    // timerUpdate
    //--------------------------------------------------------------------------
    /**
     * This method sends a Timer Transaction to both of the players; informing
     * them of the current game time.
     * @param seconds The number of seconds elapsed.
     * @param total The total number of game seconds.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void timerUpdate(int seconds, int total)
    {
        TimerTransaction timer = null;
        try
        {
            timer = new TimerTransaction(seconds,total);
        }
        catch (DocumentCreationException ex)
        {
            Logger.getLogger(getClass()).warn("Error Creating Timer Document: " + ex.getMessage(), ex);
        }
        
        if(timer!=null)
        {
            try
            {
                getPlayer1().getWriter().writeTransaction(timer);
            }
            catch(WritingException ex)
            {
                model.getTimer().stopTimer();
                Logger.getLogger(getClass()).warn("Error sending timer document: " + ex.getMessage());
                notifyPlayer1Disconnected();
            }
            
            try
            {
                getPlayer2().getWriter().writeTransaction(timer);
            }
            catch (WritingException ex)
            {
                model.getTimer().stopTimer();
                Logger.getLogger(getClass()).warn("Error sending timer document: " + ex.getMessage());
                notifyPlayer2Disconnected();
            }
        }
    }
    
    //--------------------------------------------------------------------------
    // notifyPlayer1Disconnected
    //--------------------------------------------------------------------------
    /**
     * This method notifies Player 2 that Player 1 has disconnected.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void notifyPlayer1Disconnected()
    {
        try
        {
            getPlayer2().getWriter().writeTransaction(new ErrorTransaction(ErrorTransaction.ERROR_OPPONENT_DISCONNECTED));
        }
        catch (WritingException ex)
        {
            ex.printStackTrace();
        }
        catch (DocumentCreationException ex)
        {
            ex.printStackTrace();
        }
        
        try
        {
            getPlayer1().killConnection();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).debug("Player 2 is being closed: " + ex.getMessage());
        }
        
        try
        {
            getPlayer2().killConnection();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).debug("Player 1 is being closed: " + ex.getMessage());
        }
    }
    
    
    //--------------------------------------------------------------------------
    // notifyPlayer2Disconnected
    //--------------------------------------------------------------------------
    /**
     * This method notifies Player 1 that Player 2 has disconnected.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void notifyPlayer2Disconnected()
    {
        try
        {
            getPlayer1().getWriter().writeTransaction(new ErrorTransaction(ErrorTransaction.ERROR_OPPONENT_DISCONNECTED));
        }
        catch (WritingException ex)
        {
            Logger.getLogger(getClass()).warn("Writing Exception: " + ex.getMessage());
        }
        catch (DocumentCreationException ex)
        {
            Logger.getLogger(getClass()).warn("Error creating document: " + ex.getMessage());
        }
        
        try
        {
            getPlayer2().killConnection();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).debug("Player 1 is being closed: " + ex.getMessage());
        }
        try
        {
            getPlayer1().killConnection();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).debug("Player 2 is being closed: " + ex.getMessage());
        }
    }
    
    
    //--------------------------------------------------------------------------
    // finished
    //--------------------------------------------------------------------------
    /**
     * Signals that the game has finished.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void finished()
    {
        complete = true;
        
        GameStatistics stats = new GameStatistics(getPlayer1(),getPlayer2());
        GameStatsTransaction trans = null;
        
        try
        {
            trans = new GameStatsTransaction(stats);
        }
        catch (DocumentCreationException ex)
        {
            Logger.getLogger(getClass()).warn(ex.getMessage(),ex);
        }
        
        try
        {
            getPlayer1().getWriter().writeTransaction(trans);
        }
        catch (WritingException ex)
        {
            Logger.getLogger(getClass()).warn(ex.getMessage(),ex);
        }
        
        try
        {
            getPlayer2().getWriter().writeTransaction(trans);
        }
        catch (WritingException ex)
        {
            Logger.getLogger(getClass()).warn(ex.getMessage(),ex);
        }
        
        // now Kill the clients.
        try
        {
            getPlayer1().killConnection();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).warn("Error killing client 1: " + ex.getMessage(),ex);
        }
        try
        {
            getPlayer2().killConnection();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).warn("Error killing client 2: " + ex.getMessage(),ex);
        }
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================
    
    public RemotePlayer getPlayer1()
    {
        return (RemotePlayer)getModel().getPlayer1();
    }
    
    public void setPlayer1(RemotePlayer player1)
    {
        getModel().setPlayer1(player1);
    }
    
    public RemotePlayer getPlayer2()
    {
        return (RemotePlayer)getModel().getPlayer2();
    }
    
    public void setPlayer2(RemotePlayer player2)
    {
        getModel().setPlayer2(player2);
    }
    
    public BoggleGameModel getModel()
    {
        return model;
    }
    
    public void setModel(BoggleGameModel model)
    {
        this.model = model;
    }
}
