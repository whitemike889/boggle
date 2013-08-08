/*
 * TransactionReaderFactory.java
 *
 * Created on March 25, 2006, 5:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.io.exceptions.ReadingException;
import com.erici.boggle.io.reader.BoggleStreamReader;
import com.erici.boggle.listeners.TransactionListener;
import com.xml.utils.XMLUtils;
import java.io.IOException;
import java.util.Vector;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

//==========================================================================
//  CLASS TransactionReaderFactory
//==========================================================================
/**
 *
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class TransactionReaderFactory extends Thread
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private BoggleStreamReader              reader          = null;
    private Vector<TransactionListener>     listeners       = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets the Reader and initializes the Listeners container.
     * @param reader The BoggleStreamReader object to set.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public TransactionReaderFactory(BoggleStreamReader reader)
    {
        setReader(reader);
        listeners = new Vector();
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // run
    //--------------------------------------------------------------------------
    /**
     * The "Worker Method".
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void run()
    {
        while(true)
        {
            try
            {
                analyzeDocument(getReader().read());
            }
            catch (ReadingException ex)
            {
                Logger.getLogger(getClass()).warn("ReadingException: " + ex.getMessage());
                killReader();
                return;
            }
        }
    }
    
    //--------------------------------------------------------------------------
    // killReader
    //--------------------------------------------------------------------------
    /**
     * This method closes the InputStream that this Reader is using.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void killReader()
    {
        try
        {
            getReader().getInputStream().getInputStream().close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass()).info("Error closing Reader's Input Stream: " + ex.getMessage());
        }
    }
    
    //--------------------------------------------------------------------------
    // analyzeDocument
    //--------------------------------------------------------------------------
    /**
     * This method checks the incoming XML Document; and depending on the
     * root node name, creates the corresponding Transaction subclass and
     * notifies the listeners that a Transaction has "occurred".
     * @param doc The XML Document to look at.
     * @throws ReadingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void analyzeDocument(Document doc) throws ReadingException
    {
        String name = doc.getDocumentElement().getNodeName();
        try
        {
            Logger.getLogger(getClass()).debug("Transaction: " + XMLUtils.toString(doc));
        }
        catch (TransformerConfigurationException ex)
        {
            throw new ReadingException(ex);
        }
        catch (TransformerException ex)
        {
            throw new ReadingException(ex);
        }
        
        if(NameTransaction.NODE.equals(name))
        {
            notifyTransactionOccurred(new NameTransaction(doc));
        }
        else if(GameBoardTransaction.NODE.equals(name))
        {
            notifyTransactionOccurred(new GameBoardTransaction(doc));
        }
        else if(WordGuessTransaction.NODE.equals(name))
        {
            notifyTransactionOccurred(new WordGuessTransaction(doc));
        }
        else if(TimerTransaction.NODE.equals(name))
        {
            notifyTransactionOccurred(new TimerTransaction(doc));
        }
        else if(GameStatsTransaction.NODE.equals(name))
        {
            notifyTransactionOccurred(new GameStatsTransaction(doc));
        }
        else if(ErrorTransaction.NODE.equals(name))
        {
            notifyTransactionOccurred(new ErrorTransaction(doc));
        }
        else
        {
            System.out.println("Invalid XML Root Node: " + name);
        }
    }
    
    //--------------------------------------------------------------------------
    // notifyTransactionOccurred
    //--------------------------------------------------------------------------
    /**
     * This method notifies the listeners that a transaction has occurred.
     * @param transaction The Transaction to notify listeners of.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void notifyTransactionOccurred(Transaction transaction)
    {
        for(int i=0;i<listeners.size();i++)
        {
            listeners.get(i).transactionOccurred(transaction);
        }
    }
    
    //--------------------------------------------------------------------------
    // registerListener
    //--------------------------------------------------------------------------
    /**
     * This method adds the listener into the Listeners Container.
     * @param listener The TransactionListner to add to the container.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void registerListener(TransactionListener listener)
    {
        if(!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }
    
    //--------------------------------------------------------------------------
    // removeListener
    //--------------------------------------------------------------------------
    /**
     * This method removes the listener from the listeners container.
     * @param listener The Listener to remove.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void removeListener(TransactionListener listener)
    {
        listeners.remove(listener);
    }
    
    //==========================================================================
    // GETTER(S) & SETTER(S)
    //==========================================================================
    
    
    public BoggleStreamReader getReader()
    {
        return reader;
    }
    
    public void setReader(BoggleStreamReader val)
    {
        this.reader = val;
    }
    
    public Vector<TransactionListener> getListeners()
    {
        return listeners;
    }
    
    public void setListeners(Vector<TransactionListener> val)
    {
        this.listeners = val;
    }
    
}
