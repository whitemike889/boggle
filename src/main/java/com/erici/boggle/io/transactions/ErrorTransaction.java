/*
 * ErrorTransaction.java
 *
 * Created on April 8, 2006, 3:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import org.w3c.dom.Document;

/**
 *
 * @author Eric Internicola
 */
public class ErrorTransaction extends SingleTextTransaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    public static final String      ERROR_OPPONENT_DISCONNECTED = "Other Player disconnected";
    public static final String      NODE    = "error";
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public ErrorTransaction(String error) throws DocumentCreationException
    {
        super(error);
    }
    
    public ErrorTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }

    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    public String getRootNodeName()
    {
        return NODE;
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================
    
    public void setError(String error)
    {
        setText(error);
    }
    
    public String getError()
    {
        return getText();
    }
    
}
