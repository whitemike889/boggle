/*
 * NameTransaction.java
 *
 * Created on March 25, 2006, 9:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import org.w3c.dom.*;

//--------------------------------------------------------------------------
// CLASS NameTransaction
//--------------------------------------------------------------------------
/**
 * This class is used to pass a name back and forth between the client and
 * server.
 * 
 * 
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class NameTransaction extends SingleTextTransaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    public static final String      NODE    = "name";
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public NameTransaction(String name) throws DocumentCreationException
    {
        super(name);
    }
    
    public NameTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    
    public String getName()
    {
        return getText();
    }
    
    public void setName(String name)
    {
        setText(name);
    }

    public String getRootNodeName()
    {
        return NODE;
    }
    
    
}
