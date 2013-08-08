/*
 * WordGuessTransaction.java
 *
 * Created on March 25, 2006, 4:42 PM
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
 * 
 * 
 * 
 * 
 * @author Eric Internicola
 */
public class WordGuessTransaction extends SingleTextTransaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    public static final String          NODE        = "guess";
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public WordGuessTransaction(String guess) throws DocumentCreationException
    {
        super(guess);
    }
    
    public WordGuessTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    public String getGuess()
    {
        return getText();
    }
    
    public void setGuess(String guess)
    {
        setText(guess);
    }

    public String getRootNodeName()
    {
        return NODE;
    }
    
}
