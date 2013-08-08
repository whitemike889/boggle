/*
 * DiceTest.java
 * JUnit based test
 *
 * Created on March 17, 2006, 6:51 PM
 */

package com.erici.boggle.game;

import junit.framework.*;

/**
 *
 * @author Eric Internicola
 */
public class BoggleDiceTest extends TestCase
{
    
    public BoggleDiceTest(String testName)
    {
        super(testName);
    }

    protected void setUp() throws Exception
    {
    }

    protected void tearDown() throws Exception
    {
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(BoggleDiceTest.class);
        
        return suite;
    }

    /**
     * Test of roll method, of class com.erici.boggle.Dice.
     */
    public void testRoll()
    {
        System.out.println("roll");
        
        final int NUM_ROLLS = 1000; // Out of 1000 rolls; we expect to hit the top & bottom.
        final int MAX = 5;
        final int MIN = 0;
        
        int min = 100;
        int max = -1;
        
        
        BoggleDice instance = new BoggleDice();
        
        for(int i=0;i<NUM_ROLLS;i++)
        {
            int roll = instance.roll();
            min = Math.min(roll,min);
            max = Math.max(roll,max);
        }
        
        assertEquals("Did not get correct Max",MAX,max);
        assertEquals("Did not get correct Min",MIN,min);        
    }
    
}
