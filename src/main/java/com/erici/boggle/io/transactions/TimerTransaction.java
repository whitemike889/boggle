/*
 * TimerTransaction.java
 *
 * Created on March 25, 2006, 7:36 PM
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
 * @author Eric Internicola
 */
public class TimerTransaction extends SingleTextTransaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    public static final String      NODE        = "timer";
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================

    public TimerTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    public TimerTransaction(int seconds, int total) throws DocumentCreationException
    {
        super(seconds + "," + total);
    }
    
    

    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    public String getRootNodeName()
    {
        return NODE;
    }
    
    public int getSeconds()
    {
        try
        {
            return Integer.parseInt(getText().split(",")[0]);
        }
        catch(NumberFormatException ex)
        {
            
        }
        
        return 0;
    }
    
    public int getTotal()
    {
        try
        {
            return Integer.parseInt(getText().split(",")[1]);
        }
        catch(NumberFormatException ex)
        {
            
        }
        
        return 0;
    }


    
}
