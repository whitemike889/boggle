/*
 * WordGuestimatorTest.java
 * JUnit based test
 *
 * Created on September 7, 2007, 7:37 PM
 */

package com.erici.boggle.computer;

import java.util.Date;
import junit.framework.*;
import com.erici.boggle.game.BoggleDice;

/**
 *
 * @author Eric Internicola
 */
public class WordGuestimatorTest extends TestCase
{
    private static final String[][]     BOARD_BROKEN_1  = {
        {"a","m","k","o"},
        {"n","w","d","e"},
        {"l","r","c","h"},
        {"a","i","n","e"}
    };
    private WordGuestimator instance;
    
    public WordGuestimatorTest(String testName)
    {
        super(testName);
    }
    
    protected void setUp() throws Exception
    {
        if(instance==null)
        {
             instance = new WordGuestimator();
             instance.loadWords();
        }
    }
    
    protected void tearDown() throws Exception
    {
    }
    
//    /**
//     * Test of loadWords method, of class com.erici.boggle.computer.player.WordGuestimator.
//     */
//    public void testLoadWords() throws Exception
//    {
//        System.out.println("loadWords");
//
//        WordGuestimator instance = new WordGuestimator();
//
//        instance.loadWords();
//
//        System.out.println("dictionary: " + instance.getWords().size() + " words");
//    }
    
    
    protected void performTests(BoggleDice dice) throws Exception
    {
        try
        {
            instance.setDice(dice);
            
            System.out.println("game board:\n" + instance.getDice());
            System.out.println("dictionary: " + instance.getWords().size() + " words");
            
            Date t1 = new Date();
            instance.checkAllWords();
            Date t2 = new Date();
            
            System.out.println("duration: " + (t2.getTime()-t1.getTime()) + " ms");
        }
        catch(Exception ex)
        {
            System.out.println("matched: " + instance.getValidWords().size() + " words");
            throw ex;
        }
        
        System.out.println("matched: " + instance.getValidWords().size() + " words");
    }
    
    public void testBrokenBoard1() throws Exception
    {
        System.out.println("testBrokenBoard1");
        performTests(new BoggleDice(BOARD_BROKEN_1));
    }
    
    public void testGuesses() throws Exception
    {
        
        System.out.println("testGuesses");
        
        for(int i=0;i<10;i++)
        {
            BoggleDice dice = new BoggleDice();
            dice.rollDice();
            performTests(dice);
        }
    }
}
