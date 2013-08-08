/*
 * GameStatsTransaction.java
 *
 * Created on April 2, 2006, 8:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.game.GameStatistics;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import com.xml.utils.XMLUtils;
import com.xml.utils.XPathUtils;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author intere
 */
public class GameStatsTransaction extends Transaction
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    public static final String  NODE                    = "stats";
    protected static final String NODE_PLAYER           = "player";
    protected static final String ATTR_PLAYER_NUM       = "num";
    protected static final String NODE_NAME             = "name";
    protected static final String NODE_WORD_LIST        = "word-list";
    protected static final String NODE_WORD             = "word";
    protected static final String NODE_SCORE            = "score";
    protected static final String XPATH_PLAYER          = "/stats/player";
    protected static final String XPATH_NAME            = "./name";
    protected static final String XPATH_WORDS           = "./word-list/word";
    protected static final String XPATH_SCORE           = "./score";
    private GameStatistics        stats;
    
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
    public GameStatsTransaction()
    {
        super();
    }
    
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
    public GameStatsTransaction(GameStatistics stats) throws DocumentCreationException
    {
        setStats(stats);
        createDocument();
    }
    
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
    public GameStatsTransaction(Document doc) throws ReadingException
    {
        super(doc);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // getRootNodeName
    //--------------------------------------------------------------------------
    /**
     * Get the Root Node Name.
     * @return The Root Node Name.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public String getRootNodeName()
    {
        return NODE;
    }
    
    //--------------------------------------------------------------------------
    // createDocument
    //--------------------------------------------------------------------------
    /**
     * This method creates the Document from the Game Statistics.
     * @throws DocumentCreationException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void createDocument() throws DocumentCreationException
    {
        try
        {
            setDocument(XMLUtils.getNewDocument());
            Node n = (Node)getDocument().createElement(NODE);
            n.appendChild(createPlayerNode(stats.getPlayer1(),1,stats.getPlayer1Points()));
            n.appendChild(createPlayerNode(stats.getPlayer2(),2,stats.getPlayer2Points()));
            getDocument().appendChild(n);
        }
        catch (ParserConfigurationException ex)
        {
            throw new DocumentCreationException(ex);
        }
    }
    
    //--------------------------------------------------------------------------
    // createPlayerNode
    //--------------------------------------------------------------------------
    /**
     * This method creates the Player node from the provided Boggle Player and
     * score.
     * @param player The boggle player that you're creating a player for.
     * @param score The score that goes with the boggle player.
     * @return A Node that contains player, name, word list and score.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    private Node createPlayerNode(BogglePlayer player, int playerNum, int score)
    {
        Node p = (Node)getDocument().createElement(NODE_PLAYER);
        Attr a = getDocument().createAttribute(ATTR_PLAYER_NUM);
        a.setValue(String.valueOf(playerNum));
        p.getAttributes().setNamedItem(a);
        
        p.appendChild(XMLUtils.createTextNode(getDocument(),NODE_NAME,player.getPlayerName()));
        Node list = (Node)getDocument().createElement(NODE_WORD_LIST);
        for(int i=0;i<player.getWords().size();i++)
        {
            list.appendChild(XMLUtils.createTextNode(getDocument(),NODE_WORD,player.getWords().get(i).toString()));
        }
        p.appendChild(list);
        p.appendChild(XMLUtils.createTextNode(getDocument(),NODE_SCORE,String.valueOf(score)));
        
        return p;
    }
    
    //--------------------------------------------------------------------------
    // parseDocument
    //--------------------------------------------------------------------------
    /**
     * This method parses the XML Document object.
     * @throws ReadingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void parseDocument() throws ReadingException
    {
        setStats(new GameStatistics());
        NodeList list = null;
        
        try
        {
            list = XPathUtils.getNodeList((Node) getDocument(), XPATH_PLAYER);
        }
        catch (XPathExpressionException ex)
        {
            throw new ReadingException(ex);
        }
        
        if(list.getLength()>2)
        {
            throw new ReadingException("too many player nodes");
        }
        else if(list.getLength()<2)
        {
            throw new ReadingException("too few player nodes");
        }
        
        Node p1 = null;
        Node p2 = null;
        
        for(int i=0;i<list.getLength();i++)
        {
            int player = Integer.valueOf(list.item(i).getAttributes().getNamedItem(ATTR_PLAYER_NUM).getTextContent());
            switch(player)
            {
                case 1:
                    p1 = list.item(i);
                    break;
                    
                case 2:
                    p2 = list.item(i);
                    break;
                    
                default:
                    throw new ReadingException("Unexpected player number");
            }
        }
        
        getStats().setPlayer1(setupPlayer(p1));
        getStats().setPlayer2(setupPlayer(p2));
    }
    
    //--------------------------------------------------------------------------
    // setupPlayer
    //--------------------------------------------------------------------------
    /**
     * This method sets up the provided player from the provided player node.
     * @param playerNode The Player node to read data from.
     * @return The Player to setup.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    private BogglePlayer setupPlayer(Node playerNode) throws ReadingException
    {
        BogglePlayer player = new BogglePlayer();
        
        try
        {
            player.setPlayerName(XPathUtils.getString(playerNode,XPATH_NAME));
            NodeList words = XPathUtils.getNodeList(playerNode,XPATH_WORDS);
            for(int i=0;i<words.getLength();i++)
            {
                player.addWord(words.item(i).getTextContent());
            }
            
            player.setScore(Integer.valueOf(XPathUtils.getString(playerNode,XPATH_SCORE)));
        }
        catch (XPathExpressionException ex)
        {
            throw new ReadingException(ex);
        }
        
        return player;
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================
    
    public GameStatistics getStats()
    {
        return stats;
    }
    
    public void setStats(GameStatistics stats)
    {
        this.stats = stats;
    }
    
}
