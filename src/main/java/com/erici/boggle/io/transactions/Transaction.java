/*
 * Transaction.java
 *
 * Created on March 25, 2006, 9:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.erici.boggle.io.transactions;

import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import com.xml.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.*;

/**
 *
 *
 *
 *
 * @author Eric Internicola
 */
public abstract class Transaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private Document document = null;

    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    public Transaction()
    {

    }

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets the Document object.
     * @param doc The Document to set.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public Transaction(Document doc) throws ReadingException
    {
        setDocument(doc);
        parseDocument();
    }

    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    //--------------------------------------------------------------------------
    // getRootNodeName
    //--------------------------------------------------------------------------
    /**
     * This method gives you the name of the Root Node of this class.
     * @return The name of the root node of this classes XML document.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public abstract String getRootNodeName();

    //--------------------------------------------------------------------------
    // createDocument
    //--------------------------------------------------------------------------
    /**
     * This method is responsible for creating the XML Document from the class
     * members so they can be transferred around.
     * @throws DocumentCreationException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected abstract void createDocument() throws DocumentCreationException;

    //--------------------------------------------------------------------------
    // parseDocument
    //--------------------------------------------------------------------------
    /**
     * This method is responsible for parsing the XML Document and setting
     * the appropriate class properties from it.
     * @throws ReadingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected abstract void parseDocument() throws ReadingException;

    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document doc)
    {
        this.document = doc;
    }

    public String toString()
    {
        try
        {
            return XMLUtils.toString(getDocument());
        }
        catch(Throwable t)
        {
            return super.toString();
        }
    }
}
