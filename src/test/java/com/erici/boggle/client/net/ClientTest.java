/*
 * ClientTest.java
 * JUnit based test
 *
 * Created on March 25, 2006, 7:50 PM
 */

package com.erici.boggle.client.net;

import com.erici.boggle.server.data.GameServer;
import junit.framework.*;
import com.erici.boggle.io.exceptions.WritingException;
import java.io.IOException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

//==========================================================================
// CLASS ClientTest
//==========================================================================
/**
 *
 * @param
 * @return
 * @throws
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class ClientTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static boolean          setup   = false;
    private static final String     HOST    = "localhost";
    private static final int        PORT    = 5728;
    private static final String     USER_1  = "Philbert";
    private static final String     USER_2  = "Teddy";
    private GameServer  server              = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public ClientTest(String testName)
    {
        super(testName);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    protected synchronized void setupLogging()
    {
        if(!setup)
        {
            //Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(),"System.out"));
            Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(
//                    new org.apache.log4j.PatternLayout("[%t] %-5p %c %x - %m%n"),
                    new PatternLayout("[%-5p] %l - %m%n"),
                    "System.out"));
        }
    }
    
    protected void setUp() throws Exception
    {
        setupLogging();
        server = new GameServer(PORT);
    }
    
    protected void tearDown() throws Exception
    {
        server.killServer();
        server = null;
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(ClientTest.class);
        
        return suite;
    }    
    
    //--------------------------------------------------------------------------
    // testClient
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void testClient()
    {
        System.out.println("Testing Client");
        
        Client c1 = null;
        Client c2 = null;
        
        try
        {
            c1 = new Client(HOST,PORT,USER_1);
            c2 = new Client(HOST,PORT,USER_2);
                        
        }
        catch(Throwable t)
        {
            t.printStackTrace();
            Logger.getRootLogger().info("Test Client caught: " + t.getMessage(),t);
        }
        
        Logger.getLogger(getClass()).info("Joining Server for 20 seconds");
        try
        {
            c1.getPlayer().guessWord("flubber");
            c2.getPlayer().guessWord("sandy");
            
            server.join(20000);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(getClass()).info("Interupted", ex);
        }
        Logger.getLogger(getClass()).info("Un-Joining Server...");
        
    }
}
