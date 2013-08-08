/*
 * SingleTextTransaction.java
 *
 * Created on March 25, 2006, 4:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import com.xml.utils.XMLUtils;
import com.xml.utils.XPathUtils;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 
 * 
 * @author Eric Internicola
 */
public abstract class SingleTextTransaction extends Transaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private String      text;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================

    public SingleTextTransaction(String text) throws DocumentCreationException
    {
        setText(text);
        createDocument();
    }
    
    public SingleTextTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // aMethod
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void createDocument() throws DocumentCreationException
    {
        try
        {
            setDocument(XMLUtils.getNewDocument());
            getDocument().appendChild(XMLUtils.createTextNode(getDocument(),getRootNodeName(),getText()));
        }
        catch (ParserConfigurationException ex)
        {
            throw new DocumentCreationException(ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // aMethod
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void parseDocument() throws ReadingException
    {
        try
        {
            setText(XPathUtils.getString((Node)getDocument(),"/" + getRootNodeName()));
        }
        catch (XPathExpressionException ex)
        {
            throw new ReadingException(ex);
        }
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String val)
    {
        this.text = val;
    }
    
}
