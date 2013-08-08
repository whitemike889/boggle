/*
 * GameStatisticsTest.java
 * JUnit based test
 *
 * Created on April 7, 2006, 7:36 PM
 */

package com.erici.boggle.game;

import junit.framework.*;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Eric Internicola
 */
public class GameStatisticsTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String         USER_1      = "John";
    private static final String         USER_2      = "William";
    private static final String[]       WORDS_1     = { "abc", "def", "ghi" };
    private static final String[]       WORDS_2     = { "xyz", "abc", "def" };
    
    private static final String[]       COMPLEX_WORDS_1     = {
        "alpha",                                    // 1 of the same words
        "beta","chi",                               // 2 distinct words (2 points)
        "delta",                                    // 1 of the same words
        "epsilon",                                  // 1 distinct words (5 points)
        "one","two","three"                         // 3 of the same words
            // 3 distinct words total
            // 7 points total.
    };
    private static final String[]       COMPLEX_WORDS_2     = {
        "alpha",                                    // 1 of the same words
        "bravo","charlie",                          // 2 distinct words (7 points)
        "delta",                                    // 1 of the same words
        "echo","foxtrot",                           // 2 distinct words (6 points)
        "one","two","three"                         // 3 of the same words
            // 4 distinct words total
            // 13 points total.
    };
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public GameStatisticsTest(String testName)
    {
        super(testName);
        if(!Logger.getRootLogger().getAllAppenders().hasMoreElements())
        {
            Logger.getRootLogger().addAppender(
                new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(),"System.out"));
        }
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
        TestSuite suite = new TestSuite(GameStatisticsTest.class);
        
        return suite;
    }
    
    /**
     * Test of compute method, of class com.erici.boggle.game.GameStatistics.
     */
    public void testComputeSimple()
    {
        System.out.println("compute Simple");
        
        
        BogglePlayer   PLAYER_1    = new BogglePlayer(USER_1,WORDS_1);
        BogglePlayer   PLAYER_2    = new BogglePlayer(USER_2,WORDS_2);
        
        GameStatistics instance = new GameStatistics(PLAYER_1,PLAYER_2);
        
        assertTrue(instance.getPlayer1Points()==1);
        assertTrue(instance.getPlayer2Points()==1);
        
    }
    
    public void testComputeComplex()
    {
        System.out.println("compute complex");
        
        BogglePlayer   PLAYER_1    = new BogglePlayer(USER_1,COMPLEX_WORDS_1);
        BogglePlayer   PLAYER_2    = new BogglePlayer(USER_2,COMPLEX_WORDS_2);
        
        GameStatistics instance = new GameStatistics(PLAYER_1,PLAYER_2);
        
        assertTrue(instance.getPlayer1Points()==7);
        assertTrue(instance.getPlayer2Points()==13);
        
    }
}
