/*
 * NameTest.java
 * JUnit based test
 *
 * Created on March 25, 2006, 10:02 AM
 */

package com.erici.boggle.io.transactions;

import java.io.ByteArrayInputStream;
import junit.framework.*;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.xml.utils.XMLUtils;
import com.xml.utils.XPathUtils;
import org.w3c.dom.*;

/**
 *
 * @author Eric Internicola
 */
public class NameTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String     NAME            = "Harry";
    private static final String     XML_TEST_PACKET = "<" + NameTransaction.NODE + ">" + NAME + "</" + NameTransaction.NODE + ">";
    private static final String     PATH_TO_NAME    = "/" + NameTransaction.NODE;
    
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public NameTest(String testName)
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
        TestSuite suite = new TestSuite(NameTest.class);
        
        return suite;
    }

    /**
     * Test of createDocument method, of class com.erici.boggle.io.transactions.NameTransaction.
     */
    public void testCreateDocument() throws Exception
    {
        System.out.println("createDocument");
        
        NameTransaction instance = new NameTransaction(NAME);
        
        instance.createDocument();
        
        assertEquals("Name does not match",
                NAME,
                XPathUtils.getString((Node)instance.getDocument(),PATH_TO_NAME));
        
    }

    /**
     * Test of parseDocument method, of class com.erici.boggle.io.transactions.NameTransaction.
     */
    public void testParseDocument() throws Exception
    {
        System.out.println("parseDocument");
        
        NameTransaction instance = new NameTransaction(XMLUtils.readDocument(new ByteArrayInputStream(XML_TEST_PACKET.getBytes())));
        
        instance.parseDocument();
        
        assertEquals("Names do not match",NAME,instance.getName());
    }

    /**
     * Test of getName method, of class com.erici.boggle.io.transactions.NameTransaction.
     */
    public void testGetName() throws DocumentCreationException
    {
        System.out.println("getName");
        
        NameTransaction instance = new NameTransaction(NAME);
        
        assertEquals("Names do not match",NAME,instance.getName());
    }

    /**
     * Test of setName method, of class com.erici.boggle.io.transactions.NameTransaction.
     */
    public void testSetName() throws DocumentCreationException
    {
        System.out.println("setName");
        
        NameTransaction instance = new NameTransaction((String)null);
        
        instance.setName(NAME);
        
        assertEquals("Names do not match",NAME,instance.getName());
    }
    
}
