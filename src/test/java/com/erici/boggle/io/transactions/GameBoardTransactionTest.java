/*
 * GameBoardTransactionTest.java
 * JUnit based test
 *
 * Created on March 25, 2006, 12:03 PM
 */

package com.erici.boggle.io.transactions;

import com.erici.boggle.io.exceptions.DocumentCreationException;
import junit.framework.*;
import com.erici.boggle.game.BoggleDice;
import com.xml.utils.XMLUtils;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Eric Internicola
 */
public class GameBoardTransactionTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String [][]TEST_BOARD = 
    {
        { "a", "b", "c", "d" },
        { "e", "f", "g", "h" },
        { "i", "j", "k", "l" },
        { "m", "n", "o", "p" },
    };
    private static final BoggleDice TEST_DICE   = new BoggleDice(TEST_BOARD);
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public GameBoardTransactionTest(String testName)
    {
        super(testName);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================


    protected void setUp() throws Exception
    {
    }

    protected void tearDown() throws Exception
    {
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(GameBoardTransactionTest.class);
        
        return suite;
    }

    /**
     * Test of createDocument method, of class com.erici.boggle.io.transactions.GameBoardTransaction.
     */
    public void testCreateDocument() throws Exception
    {
        System.out.println("createDocument");
        
        GameBoardTransaction instance = new GameBoardTransaction(TEST_DICE);
        instance.createDocument();
        
        GameBoardTransaction fromDocument = new GameBoardTransaction(instance.getDocument());
        
        assertEquals("Dice do not match", TEST_DICE,fromDocument.getDice());
        
    }

    /**
     * Test of parseDocument method, of class com.erici.boggle.io.transactions.GameBoardTransaction.
     */
    public void testParseDocument() throws Exception
    {
        System.out.println("parseDocument");
        
        ByteArrayInputStream in = new ByteArrayInputStream(("<" + GameBoardTransaction.NODE + ">" + TEST_DICE.toString() +
                "</" + GameBoardTransaction.NODE + ">").getBytes());
        
        GameBoardTransaction instance = new GameBoardTransaction(XMLUtils.readDocument(in));
        
        instance.parseDocument();
        
        assertEquals("Dice do not match", TEST_DICE, instance.getDice());
    }

    /**
     * Test of getDice method, of class com.erici.boggle.io.transactions.GameBoardTransaction.
     */
    public void testGetDice() throws DocumentCreationException
    {
        System.out.println("getDice");
        
        GameBoardTransaction instance = new GameBoardTransaction(TEST_DICE);
        
        BoggleDice result = instance.getDice();
        
        assertEquals("Dice do not match", TEST_DICE, result);
    }

    /**
     * Test of setDice method, of class com.erici.boggle.io.transactions.GameBoardTransaction.
     */
    public void testSetDice() throws DocumentCreationException
    {
        System.out.println("setDice");
        
        GameBoardTransaction instance = new GameBoardTransaction((BoggleDice)null);
        
        instance.setDice(TEST_DICE);
        
        assertEquals("Dice do not match", TEST_DICE,instance.getDice());
    }
    
}
