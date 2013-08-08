/*
 * GameBoardTransaction.java
 *
 * Created on March 25, 2006, 11:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import com.erici.boggle.game.*;
import com.xml.utils.XMLUtils;
import com.xml.utils.XPathUtils;
import org.w3c.dom.Node;

/**
 * 
 * 
 * 
 * @author Eric Internicola
 */
public class GameBoardTransaction extends Transaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    public static final String     NODE    = "dice";
    private BoggleDice  dice;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public GameBoardTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    /**
     * Creates a new instance of GameBoardTransaction
     */
    public GameBoardTransaction(BoggleDice dice) throws DocumentCreationException
    {
        setDice(dice);
        createDocument();
    }
    
    protected void createDocument() throws DocumentCreationException
    {
        try
        {
            setDocument(XMLUtils.getNewDocument());
        }
        catch (ParserConfigurationException ex)
        {
            throw new DocumentCreationException(ex);
        }
        
        getDocument().appendChild(XMLUtils.createTextNode(getDocument(),getRootNodeName(),getDice().toString()));
    }
    
    
    protected void parseDocument() throws ReadingException
    {
        String data;
        
        try
        {
            data = XPathUtils.getString((Node) getDocument(), "/" + getRootNodeName());
        }
        catch (XPathExpressionException ex)
        {
            throw new ReadingException(ex);
        }
        
        String []lines = data.split("\n");
        setDice(new BoggleDice());
        
        for(int i=0;i<lines.length;i++)
        {
            String []dice = lines[i].split(",");
            for(int j=0;j<dice.length;j++)
            {
                getDice().getTheRoll()[i][j] = dice[j];
            }
        }
    }
    
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================
    
    public BoggleDice getDice()
    {
        return dice;
    }
    
    public void setDice(BoggleDice dice)
    {
        this.dice = dice;
    }

    public String getRootNodeName()
    {
        return NODE;
    }
    
}
