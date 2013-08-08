/*
 * BoggleStreamWriter.java
 *
 * Created on March 25, 2006, 5:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.reader;

import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.io.transactions.Transaction;
import com.xml.utils.XMLUtils;
import java.io.OutputStream;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;



/**
 * 
 * 
 * @author Eric Internicola
 */
public class BoggleStreamWriter
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private OutputStream    output      = null;
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor creates the BoggleStreamWrite from the provided 
     * OutputStream object.
     * @param output The OutputStream to write to.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleStreamWriter(OutputStream output)
    {
        setOutput(output);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // writeTransaction
    //--------------------------------------------------------------------------
    /**
     * This method writes a transaction to the Daemon.
     * @param trans The Transaction to write.
     * @throws WritingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public synchronized void writeTransaction(Transaction trans) throws WritingException
    {
        try
        {
            XMLUtils.writeDocument(trans.getDocument(),getOutput());
        }
        catch (TransformerConfigurationException ex)
        {
            throw new WritingException(ex);
        }
        catch (TransformerException ex)
        {
            throw new WritingException(ex);
        }
    }
    
    //==========================================================================
    // GETTER(S) & SETTER(S)
    //==========================================================================
    
    public OutputStream getOutput()
    {
        return output;
    }
    
    public void setOutput(OutputStream val)
    {
        this.output = val;
    }
    
}
