/*
 * BoggleGameTest.java
 * JUnit based test
 *
 * Created on March 25, 2006, 4:54 PM
 */

package com.erici.boggle.server.data;

import junit.framework.*;
import com.erici.boggle.game.BoggleGameModel;

/**
 *
 * @author Eric Internicola
 */
public class BoggleGameTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final int        port        = 2020;
    private static final String     host        = "localhost";
    private static final String     USER_1      = "Harry";
    private static final String     USER_2      = "Bill";
    
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================

    public BoggleGameTest(String testName)
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
        TestSuite suite = new TestSuite(BoggleGameTest.class);
        
        return suite;
    }

    /**
     * Test of startGame method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testStartGame() throws Exception
    {
        System.out.println("startGame");
        
        BoggleGame instance = null;
        
        instance.startGame();
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer1 method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testGetPlayer1()
    {
        System.out.println("getPlayer1");
        
        BoggleGame instance = null;
        
        RemotePlayer expResult = null;
        RemotePlayer result = instance.getPlayer1();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlayer1 method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testSetPlayer1()
    {
        System.out.println("setPlayer1");
        
        RemotePlayer player1 = null;
        BoggleGame instance = null;
        
        instance.setPlayer1(player1);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer2 method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testGetPlayer2()
    {
        System.out.println("getPlayer2");
        
        BoggleGame instance = null;
        
        RemotePlayer expResult = null;
        RemotePlayer result = instance.getPlayer2();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlayer2 method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testSetPlayer2()
    {
        System.out.println("setPlayer2");
        
        RemotePlayer player2 = null;
        BoggleGame instance = null;
        
        instance.setPlayer2(player2);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModel method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testGetModel()
    {
        System.out.println("getModel");
        
        BoggleGame instance = null;
        
        BoggleGameModel expResult = null;
        BoggleGameModel result = instance.getModel();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModel method, of class com.erici.boggle.server.data.BoggleGame.
     */
    public void testSetModel()
    {
        System.out.println("setModel");
        
        BoggleGameModel model = null;
        BoggleGame instance = null;
        
        instance.setModel(model);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
