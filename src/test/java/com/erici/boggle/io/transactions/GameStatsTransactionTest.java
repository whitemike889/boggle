/*
 * GameStatsTransactionTest.java
 * JUnit based test
 *
 * Created on April 3, 2006, 9:38 PM
 */

package com.erici.boggle.io.transactions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import junit.framework.*;
import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.game.GameStatistics;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import com.xml.utils.XMLUtils;
import com.xml.utils.XPathUtils;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.*;

/**
 *
 * @author intere
 */
public class GameStatsTransactionTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String     NAME_1  = "Sella";
    private static final String     NAME_2  = "Thomas";
    private static final String[]   LIST_1  = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" };
    private static final String[]   LIST_2  = { "one", "three", "five", "seven", "nine" };
    private static final int        SCORE_1 = 10;
    private static final int        SCORE_2 = 7;
    private static String           XML_DOC = null;
    
    private BogglePlayer    player1     = null;
    private BogglePlayer    player2     = null;
    private GameStatistics  stats       = null;
    
    
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public GameStatsTransactionTest(String testName)
    {
        super(testName);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // setup
    //--------------------------------------------------------------------------
    /**
     * Sets up the environment for the test.
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void setUp()
    {
        player1 = new BogglePlayer();
        player1.setPlayerName(NAME_1);
        player2 = new BogglePlayer();
        player2.setPlayerName(NAME_2);
        
        for(int i=0;i<LIST_1.length;i++)
        {
            player1.addWord(LIST_1[i]);
        }
        
        for(int i=0;i<LIST_2.length;i++)
        {
            player2.addWord(LIST_2[i]);
        }
        
        StringBuffer buff = new StringBuffer();
        buff.append("<" + GameStatsTransaction.NODE + ">");
        appendPlayer(buff,NAME_1,1,SCORE_1,LIST_1);
        appendPlayer(buff,NAME_2,2,SCORE_2,LIST_2);
        buff.append("</" + GameStatsTransaction.NODE + ">");
        
        XML_DOC= buff.toString();
    }
    
    private void appendPlayer(StringBuffer buff, String name, int num, int score, String[] words)
    {
        buff.append("<" + GameStatsTransaction.NODE_PLAYER + " " + GameStatsTransaction.ATTR_PLAYER_NUM + "=\"" + num + "\">");
        buff.append("<" + GameStatsTransaction.NODE_NAME + ">");
        buff.append(name);
        buff.append("</" + GameStatsTransaction.NODE_NAME + ">");
        buff.append("<" + GameStatsTransaction.NODE_WORD_LIST + ">");
        for(int i=0;i<words.length;i++)
        {
            buff.append("<" + GameStatsTransaction.NODE_WORD + ">");
            buff.append(words[i]);
            buff.append("</" + GameStatsTransaction.NODE_WORD + ">");
        }
        buff.append("</" + GameStatsTransaction.NODE_WORD_LIST + ">");
        buff.append("<" + GameStatsTransaction.NODE_SCORE + ">");
        buff.append(score);
        buff.append("</" + GameStatsTransaction.NODE_SCORE + ">");
        buff.append("</" + GameStatsTransaction.NODE_PLAYER + ">");
    }
    
    protected void tearDown() throws Exception
    {
        player1 = null;
        player2 = null;
        XML_DOC = null;
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(GameStatsTransactionTest.class);
        
        return suite;
    }
    
    /**
     * Test of getRootNodeName method, of class com.erici.boggle.io.transactions.GameStatsTransaction.
     */
    public void testGetRootNodeName()
    {
        System.out.println("getRootNodeName");
        
        GameStatsTransaction instance = new GameStatsTransaction();
        
        assertEquals(GameStatsTransaction.NODE,instance.getRootNodeName());
    }
    
    /**
     * Test of createDocument method, of class com.erici.boggle.io.transactions.GameStatsTransaction.
     */
    public void testCreateDocument()
    {
        System.out.println("createDocument");
        
        stats = new GameStatistics(player1,player2);
        GameStatsTransaction instance;
        try
        {
            instance = new GameStatsTransaction();
            instance.setStats(stats);
            instance.createDocument();
        }
        catch (DocumentCreationException ex)
        {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }
    
    /**
     * Test of parseDocument method, of class com.erici.boggle.io.transactions.GameStatsTransaction.
     */
    public void testParseDocument()
    {
        System.out.println("parseDocument");
        
        ByteArrayInputStream in = new ByteArrayInputStream(XML_DOC.getBytes());
        
        try
        {
            GameStatsTransaction instance = new GameStatsTransaction(XMLUtils.readDocument(in));

            assertEquals(NAME_1,instance.getStats().getPlayer1().getPlayerName());
            assertEquals(NAME_2,instance.getStats().getPlayer2().getPlayerName());

            for(int i=0;i<LIST_1.length;i++)
            {
                assertEquals(LIST_1[i],instance.getStats().getPlayer1().getWords().get(i));
            }

            for(int i=0;i<LIST_2.length;i++)
            {
                assertEquals(LIST_2[i],instance.getStats().getPlayer2().getWords().get(i));
            }
            
            assertEquals(SCORE_1,instance.getStats().getPlayer1().getScore());
            assertEquals(SCORE_2,instance.getStats().getPlayer2().getScore());
            
            assertEquals(SCORE_1,instance.getStats().getPlayer1Points());
            assertEquals(SCORE_2,instance.getStats().getPlayer2Points());
        }
        catch(Throwable t)
        {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }    
}
