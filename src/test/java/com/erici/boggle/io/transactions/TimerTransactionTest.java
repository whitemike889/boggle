/*
 * TimerTransactionTest.java
 * JUnit based test
 *
 * Created on March 25, 2006, 7:42 PM
 */

package com.erici.boggle.io.transactions;

import junit.framework.*;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.ReadingException;
import org.w3c.dom.Document;

/**
 *
 * @author Eric Internicola
 */
public class TimerTransactionTest extends TestCase
{
    
    public TimerTransactionTest(String testName)
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
        TestSuite suite = new TestSuite(TimerTransactionTest.class);
        
        return suite;
    }

//    /**
//     * Test of getRootNodeName method, of class com.erici.boggle.io.transactions.TimerTransaction.
//     */
//    public void testGetRootNodeName()
//    {
//        System.out.println("getRootNodeName");
//        
//        TimerTransaction instance = null;
//        
//        String expResult = "";
//        String result = instance.getRootNodeName();
//        assertEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getSeconds method, of class com.erici.boggle.io.transactions.TimerTransaction.
//     */
//    public void testGetSeconds()
//    {
//        System.out.println("getSeconds");
//        
//        TimerTransaction instance = null;
//        
//        int expResult = 0;
//        int result = instance.getSeconds();
//        assertEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTotal method, of class com.erici.boggle.io.transactions.TimerTransaction.
//     */
//    public void testGetTotal()
//    {
//        System.out.println("getTotal");
//        
//        TimerTransaction instance = null;
//        
//        int expResult = 0;
//        int result = instance.getTotal();
//        assertEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    public void testAll() throws DocumentCreationException, ReadingException
    {
        System.out.println("Test All");
        
        final int SEC = 5;
        final int TOTAL = 300;
        
        TimerTransaction trans = new TimerTransaction(SEC,TOTAL);
        
        TimerTransaction reader = new TimerTransaction(trans.getDocument());
        
        assertEquals("Seconds don't match",SEC,reader.getSeconds());
        assertEquals("Totals don't match", TOTAL,reader.getTotal());
        
    }

    /**
     * Test of getRootNodeName method, of class com.erici.boggle.io.transactions.TimerTransaction.
     */
    public void testGetRootNodeName()
    {
        System.out.println("getRootNodeName");
        
        TimerTransaction instance = null;
        
        String expResult = "";
        String result = instance.getRootNodeName();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeconds method, of class com.erici.boggle.io.transactions.TimerTransaction.
     */
    public void testGetSeconds()
    {
        System.out.println("getSeconds");
        
        TimerTransaction instance = null;
        
        int expResult = 0;
        int result = instance.getSeconds();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotal method, of class com.erici.boggle.io.transactions.TimerTransaction.
     */
    public void testGetTotal()
    {
        System.out.println("getTotal");
        
        TimerTransaction instance = null;
        
        int expResult = 0;
        int result = instance.getTotal();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}