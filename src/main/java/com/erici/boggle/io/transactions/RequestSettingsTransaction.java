/*
 * RequestSettingsTransaction.java
 *
 * Created on April 8, 2006, 3:22 PM
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
public class RequestSettingsTransaction extends SingleTextTransaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String         NODE        = "request-settings";
    
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
    public RequestSettingsTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    public RequestSettingsTransaction(boolean yesOrNo) throws DocumentCreationException
    {
        super(String.valueOf(yesOrNo));
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

    public void setRequestSettings(boolean yesOrNo)
    {
        setText(String.valueOf(yesOrNo));
    }
    
    public boolean isRequestSettings()
    {
        return Boolean.valueOf(getText());
    }
}
