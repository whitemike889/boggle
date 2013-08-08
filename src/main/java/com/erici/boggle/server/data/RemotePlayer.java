/*
 * RemotePlayer.java
 *
 * Created on March 25, 2006, 9:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.server.data;

import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.io.reader.BoggleStreamReader;
import com.erici.boggle.io.reader.BoggleStreamWriter;
import com.erici.boggle.io.transactions.*;
import com.erici.boggle.listeners.TransactionListener;
import java.io.IOException;
import java.net.Socket;
import org.apache.log4j.Logger;


/**
 *
 * @author Eric Internicola
 */
public class RemotePlayer extends BogglePlayer implements TransactionListener
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private Socket                      socket          = null;
    private BoggleStreamReader          reader          = null;
    private BoggleStreamWriter          writer          = null;
    private TransactionReaderFactory    factory         = null;
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor Initializes the RemotePlayer on the provided Socket.
     * @param socket The Socket to read/write from.
     * @throws IOException
     * @throws ReadingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public RemotePlayer(Socket socket) throws IOException, ReadingException
    {
        setSocket(socket);
        setReader(new BoggleStreamReader(socket.getInputStream()));
        setWriter(new BoggleStreamWriter(socket.getOutputStream()));
        setFactory(new TransactionReaderFactory(getReader()));
        
        setPlayerName(new NameTransaction(getReader().read()).getName());
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // readName
    //--------------------------------------------------------------------------
    /**
     * This method reads the NameTransaction from the Remote Player.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     * @throws ReadingException
     */
    protected void readName(NameTransaction name)
    {
        Logger.getRootLogger().info("Name Transaction: " + name.getName());
        setPlayerName(name.getName());
    }
    
    //--------------------------------------------------------------------------
    // startClientListening
    //--------------------------------------------------------------------------
    /**
     * This method is what tells the RemotePlayer to start listening for client
     * transactions.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void startClientListening()
    {
        getFactory().registerListener(this);
        getFactory().start();
    }    
    
    //--------------------------------------------------------------------------
    // transactionOccurred
    //--------------------------------------------------------------------------
    /**
     * This method handles the case when a transaction occurs.
     * @param transaction The transaction that occurred.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void transactionOccurred(Transaction transaction)
    {
        
        if(transaction instanceof NameTransaction)
        {
            readName((NameTransaction)transaction);
        }
        else if(transaction instanceof WordGuessTransaction)
        {
            String guess = ((WordGuessTransaction)transaction).getGuess();
            getWords().add(guess);
            Logger.getLogger(getClass()).debug("Server - got guess: " + guess);
        }
        else
        {
            Logger.getLogger(getClass()).info("Server - Unknown Transaction: " + transaction.toString());        
        }
    }
    
    //--------------------------------------------------------------------------
    // killConnection
    //--------------------------------------------------------------------------
    /**
     * This method kills the connection to the Client.
     * @throws IOException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void killConnection() throws IOException
    {
        reader.getInputStream().getInputStream().close();
        writer.getOutput().close();
    }
    
    //--------------------------------------------------------------------------
    // sendError
    //--------------------------------------------------------------------------
    /**
     * This method sends an Error to the Client.
     * @param error The Error message to send to the client.
     * @throws WritingException
     * @throws DocumentCreationException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void sendError(String error) throws WritingException, DocumentCreationException
    {
        ErrorTransaction et = new ErrorTransaction(error);
        writer.writeTransaction(et);
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
    
    public BoggleStreamReader getReader()
    {
        return reader;
    }
    
    public void setReader(BoggleStreamReader reader)
    {
        this.reader = reader;
    }
    
    public TransactionReaderFactory getFactory()
    {
        return factory;
    }
    
    public void setFactory(TransactionReaderFactory factory)
    {
        this.factory = factory;
    }
    
    public BoggleStreamWriter getWriter()
    {
        return writer;
    }
    
    public void setWriter(BoggleStreamWriter writer)
    {
        this.writer = writer;
    }
    
}
