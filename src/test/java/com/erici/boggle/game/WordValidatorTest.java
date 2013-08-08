

/*
 * WordValidatorTest.java
 * JUnit based test
 *
 * Created on April 12, 2006, 10:39 PM
 */

package com.erici.boggle.game;

import com.erici.boggle.computer.WordGuestimator;
import junit.framework.*;
import java.util.Vector;

/**
 *
 * @author Eric Internicola
 */
public class WordValidatorTest extends TestCase
{
    private static final String[][]     BOARD_SIMPLE        = {
        {"a","b","c","d"},
        {"e","f","g","h"},
        {"i","j","k","l"},
        {"m","n","o","p"}
    };
    
    private static final String         SIMPLE_VALID_1      = "aeim";
    private static final String         SIMPLE_VALID_2      = "abcd";
    private static final String         SIMPLE_VALID_3      = "afkp";
    private static final String         SIMPLE_VALID_4      = "jgfk";
    private static final String         SIMPLE_VALID_5      = "abefijmnopklghdc";
    private static final String         SIMPLE_INVALID_1    = "aabb";
    private static final String         SIMPLE_INVALID_2    = "pmad";
    private static final String         SIMPLE_INVALID_3    = "ekgf";
    private static final String         SIMPLE_INVALID_4    = "mjga";
    private static final String         SIMPLE_INVALID_5    = "abefijmnopkd";
    
    private static final String[][]     BOARD_COMPLEX       = {
        {"a","b", "c","d"},
        {"e","qu","f","g"},
        {"i","j", "k","l"},
        {"m","n", "o","p"}
    };
    
    private static final String         COMPLEX_VALID_1     = "qua";
    private static final String         COMPLEX_VALID_2     = "equa";
    private static final String         COMPLEX_VALID_3     = "bqujn";
    private static final String         COMPLEX_VALID_4     = "equfg";
    private static final String         COMPLEX_INVALID_1   = "aqupl";
    private static final String         COMPLEX_INVALID_2   = "qugfe";
    
    private static final String[][]     BOARD_BROKEN        = {
        {"e","s","j","t"},
        {"p","e","n","o"},
        {"i","d","e","o"},
        {"d","s","e","h"}
    };
    
    private static final String         BROKEN_VALID_1      = "speed";
    private static final String         BROKEN_VALID_2      = "speeds";
    private static final String         BROKEN_VALID_3      = "seed";
    private static final String         BROKEN_VALID_4      = "jones";
    
    private static final String         BROKEN_INVALID_1    = "";
    
    private static final String[][]     BOARD_BROKEN2       = {
        {"qu","i","g","r"},
        {"f","r","s","e"},
        {"s","o","e","t"},
        {"e","o","m","j"}
    };
    
    private static final String         BROKEN2_VALID_1     = "quire";
    private static final String         BROKEN2_VALID_2     = "quires";
    private static final String         BROKEN2_INVALID_1   = "jequ";
    private static final String         BROKEN2_INVALID_2   = "quofs";
    
    private static final String[][]     BOARD_BROKEN3       = {
        {"s","qu","z","a"},
        {"l","e", "s","j"},
        {"c","t", "d","u"},
        {"s","t", "e","h"}
    };
    
    private static final String         BROKEN3_VALID_1     = "quet";
    private static final String         BROKEN3_VALID_2     = "quets";
    private static final String         BROKEN3_INVALID_1   = "test";
    private static final String         BROKEN3_INVALID_2   = "blah";
    
    private static final String[][]     BOARD_BROKEN4       = {
        {"qu","k","e","b"},
        {"w", "e","h","r"},
        {"s", "e","e","a"},
        {"y", "y","g","c"}
    };
    
    private static final String         BROKEN4_VALID_1     = "que";
    private static final String         BROKEN4_VALID_2     = "queer";
    private static final String         BROKEN4_INVALID_1   = "quoth";
    private static final String         BROKEN4_INVALID_2   = "reach";
    
    private static final String[][]     BOARD_BROKEN5  = {
        {"a","m","k","o"},
        {"n","w","d","e"},
        {"l","r","c","h"},
        {"a","i","n","e"}
    };
    
    private static final String         BROKEN5_VALID_1     = "iraq";
    
    private WordValidator simpleInstance    = null;
    private WordValidator complexInstance   = null;
    private WordValidator brokenInstance    = null;
    private WordValidator brokenInstance2   = null;
    private WordValidator brokenInstance3   = null;
    private WordValidator brokenInstance4   = null;
    private WordValidator brokenInstance5   = null;
    
    
    public WordValidatorTest(String testName)
    {
        super(testName);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    protected void setUp() throws Exception
    {
        simpleInstance = new WordValidator(new BoggleDice(BOARD_SIMPLE));
        complexInstance = new WordValidator(new BoggleDice(BOARD_COMPLEX));
        brokenInstance = new WordValidator(new BoggleDice(BOARD_BROKEN));
        brokenInstance2 = new WordValidator(new BoggleDice(BOARD_BROKEN2));
        brokenInstance3 = new WordValidator(new BoggleDice(BOARD_BROKEN3));
        brokenInstance4 = new WordValidator(new BoggleDice(BOARD_BROKEN4));
        brokenInstance5 = new WordValidator(new BoggleDice(BOARD_BROKEN5));
    }
    
    protected void tearDown() throws Exception
    {
        simpleInstance = null;
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(WordValidatorTest.class);
        
        return suite;
    }
    
    public void testSimpleValid()
    {
        System.out.println("Simple Valid Test");
        
        assertTrue("SIMPLE_VALID_1 did not validate",simpleInstance.validates(SIMPLE_VALID_1));
        assertTrue("SIMPLE_VALID_2 did not validate",simpleInstance.validates(SIMPLE_VALID_2));
        assertTrue("SIMPLE_VALID_3 did not validate",simpleInstance.validates(SIMPLE_VALID_3));
        assertTrue("SIMPLE_VALID_4 did not validate",simpleInstance.validates(SIMPLE_VALID_4));
        assertTrue("SIMPLE_VALID_5 did not validate",simpleInstance.validates(SIMPLE_VALID_5));
    }
    
    
    public void testSimpleInvalid()
    {
        System.out.println("Simple Invalid Test");
        assertFalse("SIMPLE_INVALID_1 validated",simpleInstance.validates(SIMPLE_INVALID_1));
        assertFalse("SIMPLE_INVALID_2 validated",simpleInstance.validates(SIMPLE_INVALID_2));
        assertFalse("SIMPLE_INVALID_3 validated",simpleInstance.validates(SIMPLE_INVALID_3));
        assertFalse("SIMPLE_INVALID_4 validated",simpleInstance.validates(SIMPLE_INVALID_4));
        assertFalse("SIMPLE_INVALID_5 validated",simpleInstance.validates(SIMPLE_INVALID_5));
    }
    
    public void testComplexValid()
    {
        System.out.println("Complex Valid Test");
        assertTrue("Complex Valid String1 did not validate",complexInstance.validates(COMPLEX_VALID_1));
        assertTrue("Complex Valid String2 did not validate",complexInstance.validates(COMPLEX_VALID_2));
        assertTrue("Complex Valid String3 did not validate",complexInstance.validates(COMPLEX_VALID_3));
        assertTrue("Complex Valid String4 did not validate",complexInstance.validates(COMPLEX_VALID_4));
    }
    
    public void testComplexInvalid()
    {
        System.out.println("Complex Invalid Test");
        assertFalse("Complex Invalid String1 validated",complexInstance.validates(COMPLEX_INVALID_1));
        assertFalse("Complex Invalid String2 validated",complexInstance.validates(COMPLEX_INVALID_2));
    }
    
    public void testBrokenValid()
    {
        System.out.println("Test Broken Valid");
        
        assertTrue("Broken Valid Test 1 did not validate",brokenInstance.validates(BROKEN_VALID_1));
        assertTrue("Broken Valid Test 2 did not validate",brokenInstance.validates(BROKEN_VALID_2));
        assertTrue("Broken Valid Test 3 did not validate",brokenInstance.validates(BROKEN_VALID_3));
        assertTrue("Broken Valid Test 4 did not validate",brokenInstance.validates(BROKEN_VALID_4));
    }
    
    public void testBrokenInvalid()
    {
        System.out.println("Test Broken Invalid");
        assertFalse("Broken Invalid Test 1 validated",brokenInstance.validates(BROKEN_INVALID_1));
    }
    
    public void testBroken2Valid()
    {
        System.out.println("Test Broken 2 Valid");
        
        assertTrue("Broken 2 Valid Test 1 did not validate",brokenInstance2.validates(BROKEN2_VALID_1));
        assertTrue("Broken 2 Valid Test 2 did not validate",brokenInstance2.validates(BROKEN2_VALID_2));
    }
    
    public void testBroken2Invalid()
    {
        System.out.println("Test Broken 2 Invalid");
        assertFalse("Broken 2 Invalid Test 1 validated",brokenInstance2.validates(BROKEN2_INVALID_1));
        assertFalse("Broken 2 Invalid Test 2 validated",brokenInstance2.validates(BROKEN2_INVALID_2));
    }
    
    public void testBroken3Valid()
    {
        System.out.println("Test Broken 3 Valid");
        
        assertTrue("Broken 3 Valid Test 1 did not validate",brokenInstance3.validates(BROKEN3_VALID_1));
        assertTrue("Broken 4 Valid Test 2 did not validate",brokenInstance3.validates(BROKEN3_VALID_2));
    }
    
    public void testBroken3Invalid()
    {
        System.out.println("Test Broken 3 Invalid");
        
        assertFalse("Broken 3 Invalid Test 1 validated",brokenInstance3.validates(BROKEN3_INVALID_1));
        assertFalse("Broken 3 Invalid Test 2 validated",brokenInstance3.validates(BROKEN3_INVALID_2));
    }
    
    public void testBroken4Valid()
    {
        System.out.println("Test Broken 4 Valid");
        assertTrue("Broken 4 Valid Test 1 did not validate",brokenInstance4.validates(BROKEN4_VALID_1));
        assertTrue("Broken 4 Valid Test 2 did not validate",brokenInstance4.validates(BROKEN4_VALID_2));
    }
    
    public void testBroken5Valid()
    {
        System.out.println("Test Broken 5 Valid");
        assertFalse("Broken 5 Valid Test 1 did not validate",brokenInstance5.validates(BROKEN5_VALID_1));
    }
    
}

