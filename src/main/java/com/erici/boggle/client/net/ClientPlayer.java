/*
 * ClientPlayer.java
 *
 * Created on March 25, 2006, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.client.net;

import com.erici.boggle.client.listeners.ClientListener;
import com.erici.boggle.game.*;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.io.reader.BoggleStreamReader;
import com.erici.boggle.io.reader.BoggleStreamWriter;
import com.erici.boggle.io.transactions.*;
import com.erici.boggle.listeners.TransactionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Eric Internicola
 */
public class ClientPlayer extends BogglePlayer implements TransactionListener
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private Socket                      socket          = null;
    private BoggleStreamWriter          writer          = null;
    private BoggleDice                  dice            = null;
    private String                      opponent        = null;
    private TransactionReaderFactory    factory         = null;
    private Vector<ClientListener>      clientListeners = null;
    private Vector<String>              words           = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets up the clients connectors, and ships off the first
     * bit of information to the Server (the Players NameTransaction).
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     * @param socket The Socket to get the reader & write from.
     * @throws IOException
     * @throws WritingException
     */
    public ClientPlayer(Socket socket, String name) throws IOException, WritingException
    {
        clientListeners = new Vector();
        words = new Vector();
        
        setPlayerName(name);
        setSocket(socket);
        setWriter(new BoggleStreamWriter(getSocket().getOutputStream()));
        writeName();
        
        factory = new TransactionReaderFactory(new BoggleStreamReader(getSocket().getInputStream()));
        factory.registerListener(this);
        factory.start();
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // writeName
    //--------------------------------------------------------------------------
    /**
     * This method sends the Players NameTransaction off to the Server.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     * @throws WritingException
     */
    protected void writeName() throws WritingException
    {
        try
        {
            writer.writeTransaction(new NameTransaction(getPlayerName()));
        }
        catch (DocumentCreationException ex)
        {
            throw new WritingException(ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // transactionOccurred
    //--------------------------------------------------------------------------
    /**
     * This method is called whenever a Transaction comes in from the Server.
     * @param transaction The Transaction that occurred.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void transactionOccurred(Transaction transaction)
    {
        if(transaction instanceof GameBoardTransaction)
        {
            setDice(((GameBoardTransaction)transaction).getDice());
            alertGameBoard(getDice());
            Logger.getLogger(getClass()).debug("Got Dice: " + getDice().toString());
        }
        else if(transaction instanceof NameTransaction)
        {
            setOpponent(((NameTransaction)transaction).getName());
            alertOpponentName(getOpponent());
            Logger.getLogger(getClass()).debug("Got Opponent: " + getOpponent());
        }
        else if(transaction instanceof TimerTransaction)
        {
            TimerTransaction timer = (TimerTransaction)transaction;
            alertTimer(timer.getSeconds(),timer.getTotal());
            Logger.getLogger(getClass()).debug("Got Timer: " + timer.getSeconds() + "/" + timer.getTotal());
        }
        else if(transaction instanceof GameStatsTransaction)
        {
            GameStatsTransaction stats = (GameStatsTransaction)transaction;
            alertStatsRecieved(stats.getStats());
            Logger.getLogger(getClass()).debug("Got Stats: ");
            killConnection();
        }
        else if(transaction instanceof ErrorTransaction)
        {
            ErrorTransaction error = (ErrorTransaction)transaction;
            alertErrorRecieved(error.getError());
            Logger.getLogger(getClass()).error("Server reported error: " + error.getError());
        }
        else
        {
            Logger.getLogger(getClass()).debug("Client: Unknown Transaction: " + transaction.toString());
        }
    }
    
    //--------------------------------------------------------------------------
    // killConnection
    //--------------------------------------------------------------------------
    /**
     * This method kills the connection to the Boggle server.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void killConnection()
    {
        try
        {
            factory.getReader().getInputStream().getInputStream().close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(getClass()).warn("Error closing input stream: " + ex.getMessage(),ex);
        }
        try
        {
            writer.getOutput().close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).warn("Error closing output stream: " + ex.getMessage(),ex);
        }
        try
        {
            getSocket().close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).warn("Error closing socket: " + ex.getMessage(),ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // guessWord
    //--------------------------------------------------------------------------
    /**
     * This method sends a guess out to the Server.
     * @param guess The Word to guess.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void guessWord(String guess)
    {
        try
        {
            if(addWord(guess))
            {
                WordGuessTransaction trans = new WordGuessTransaction(guess);
                getWriter().writeTransaction(trans);
                alertWordGuessed(guess);
            }
        }
        catch (DocumentCreationException ex)
        {
            Logger.getLogger(getClass()).warn("Document Creation Exception: " + ex.getMessage(),ex);
        }
        catch(WritingException ex)
        {
            Logger.getLogger(getClass()).warn("Writing Exception: " + ex.getMessage(),ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // alertErrorRecieved
    //--------------------------------------------------------------------------
    /**
     * This method alerts the listeners that an error has occurred.
     * @param error The error that was reported.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void alertErrorRecieved(String error)
    {
        for(int i=0;i<clientListeners.size();i++)
        {
            clientListeners.get(i).errorRecieved(error);
        }
    }
    
    //--------------------------------------------------------------------------
    // alertGameBoard
    //--------------------------------------------------------------------------
    /**
     * This method alerts the Client Listeners that a Gameboard has been recieved.
     * @param dice The Boggle Dice that came in.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void alertGameBoard(BoggleDice dice)
    {
        for(int i=0;i<clientListeners.size();i++)
        {
            clientListeners.get(i).gameRecieved(dice);
        }
    }
    
    //--------------------------------------------------------------------------
    // alertOpponentName
    //--------------------------------------------------------------------------
    /**
     * This method tells the Client Listeners that the opponent has been recieved.
     * @param name The Name of the opponent.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void alertOpponentName(String name)
    {
        for(int i=0;i<clientListeners.size();i++)
        {
            clientListeners.get(i).opponentRecieved(name);
        }
    }
    
    //--------------------------------------------------------------------------
    // alertStatsRecieved
    //--------------------------------------------------------------------------
    /**
     * This method alerts the Client Listeners that the Game Stastics have been
     * recieved.
     * @param stats The Game statistics.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void alertStatsRecieved(GameStatistics stats)
    {
        for(int i=0;i<clientListeners.size();i++)
        {
            clientListeners.get(i).gameStatsRecieved(stats);
        }
    }
    
    //--------------------------------------------------------------------------
    // alertTimer
    //--------------------------------------------------------------------------
    /**
     * This method alerts the Client Listeners that a Timer event has arrived.
     * @param seconds The number of elapsed seconds.
     * @param total The total number of seconds in the game.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void alertTimer(int seconds, int total)
    {
        for(int i=0;i<clientListeners.size();i++)
        {
            clientListeners.get(i).timerUpdate(seconds,total);
        }
    }
    
    //--------------------------------------------------------------------------
    // alertWordGuessed
    //--------------------------------------------------------------------------
    /**
     * This method tells the Client Listener that a word has been guessed.
     * @param guess The word that was guessed.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void alertWordGuessed(String guess)
    {
        for(int i=0;i<clientListeners.size();i++)
        {
            clientListeners.get(i).wordGuessed(guess);
        }
    }
    
    //--------------------------------------------------------------------------
    // registerListener
    //--------------------------------------------------------------------------
    /**
     * Adds a listener to the clientListeners.
     * @param listener The ClientListener to register.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     * @param listener ClientListener.
     */
    public void registerListener(ClientListener listener)
    {
        if(!clientListeners.contains(listener))
        {
            clientListeners.add(listener);
        }
    }
    
    //--------------------------------------------------------------------------
    // removeListener
    //--------------------------------------------------------------------------
    /**
     * Remove a listener from the Listeners.
     * @param listener The listener to remove.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void removeListener(ClientListener listener)
    {
        clientListeners.remove(listener);
    }
    
    //==========================================================================
    // GETTER(S) & SETTER(S)
    //==========================================================================
    
    public Socket getSocket()
    {
        return socket;
    }
    
    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
    
    public BoggleStreamWriter getWriter()
    {
        return writer;
    }
    
    public void setWriter(BoggleStreamWriter writer)
    {
        this.writer = writer;
    }
    
    public BoggleDice getDice()
    {
        return dice;
    }
    
    public void setDice(BoggleDice dice)
    {
        this.dice = dice;
    }
    
    public String getOpponent()
    {
        return opponent;
    }
    
    public void setOpponent(String opponent)
    {
        this.opponent = opponent;
    }
    
}
