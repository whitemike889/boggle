/*
 * BoggleServerTest.java
 * JUnit based test
 *
 * Created on April 4, 2006, 8:42 PM
 */

package com.erici.boggle.server;

import com.erici.boggle.game.*;
import com.erici.boggle.io.exceptions.DocumentCreationException;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.io.reader.BoggleStreamReader;
import com.erici.boggle.io.reader.BoggleStreamWriter;
import com.erici.boggle.io.transactions.*;
import com.erici.boggle.listeners.TransactionListener;
import com.erici.boggle.server.data.GameServer;
import java.io.IOException;
import java.net.Socket;
import junit.framework.*;
import org.apache.log4j.Logger;

//==========================================================================
//  CLASS BoggleServerTest
//==========================================================================
/**
 *
 *
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class BoggleServerTest extends TestCase
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String     HOST        = "localhost";
    private static final int        PORT        = 4292;
    private static final String     USER_1      = "Billy Joe";
    private static final String     USER_2      = "Wendy";
    private static final String[]   WORDS_1     = {
        "alpha","beta","chi","delta","epsilon",     // 5 distinct words
        "one","two","three"                         // 3 of the same words
    };
    private static final String[]   WORDS_2     = {
        "alpha","bravo","charlie",
        "delta","echo","foxtrot",                   // 6 distinct words
        "one","two","three"                         // 3 of the same words
    };
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Test Constructor.
     * @param testName the Test Name.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleServerTest(String testName)
    {
        super(testName);
        Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(),"System.out"));
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
        TestSuite suite = new TestSuite(BoggleServerTest.class);
        
        return suite;
    }
    
    /**
     * Test of main method, of class com.erici.boggle.server.BoggleServer.
     */
    public void testMain() throws Exception
    {
        // First setup the Game Server
        GameServer server = new GameServer(PORT);
        
        // Now create and startup the clients.
        ClientTest client1 = new ClientTest(USER_1,WORDS_1);
        ClientTest client2 = new ClientTest(USER_2,WORDS_2);
        
        // Send the clients names
        client1.sendName();
        client2.sendName();
        
        // Now the server has started the game.
        while(!(client1.isComplete()||client2.isComplete()))
        {
            System.out.println("Time = " + client1.getElapsed() + "/" + client1.getTotal());
            Thread.currentThread().sleep(1000);
        }
        
        assertEquals("Names do not match",client1.getName(),client2.getOpponent());
        assertEquals("Names do not match",client2.getName(),client1.getOpponent());
        
        assertEquals("Gameboards do not match",client1.getDice(),client2.getDice());
        
        assertEquals("Points do not match",client1.getStats().getPlayer1Points(),client2.getStats().getPlayer1Points());
        assertEquals("Points do not match",client1.getStats().getPlayer2Points(),client2.getStats().getPlayer2Points());
    }
    
    public void testMultipleGames() throws Exception
    {
        GameServer server = new GameServer(PORT);
        
        // Now create and startup the clients.
        ClientTest client1 = new ClientTest(USER_1,WORDS_1);
        ClientTest client2 = new ClientTest(USER_2,WORDS_2);
        
        // Send the clients names
        client1.sendName();
        client2.sendName();
        
        // Now the server has started the game.
        while(!(client1.isComplete()||client2.isComplete()))
        {
            System.out.println("Time = " + client1.getElapsed() + "/" + client1.getTotal());
            Thread.currentThread().sleep(1000);
        }
        
                
    }
    
    
    public class ClientTest extends BogglePlayer implements TransactionListener
    {
        //==========================================================================
        //  VARIABLE(S)
        //==========================================================================
        private String              name        = null;
        private String[]            guesses     = null;
        private String              opponent    = null;
        private GameStatistics      stats       = null;
        private BoggleDice          dice        = null;
        private boolean             complete    = false;
        private int                 elapsed     = 0;
        private int                 total       = 0;
        
        private TransactionReaderFactory
                                    reader      = null;
        private BoggleStreamWriter  writer      = null;
        
        //--------------------------------------------------------------------------
        // <>
        //--------------------------------------------------------------------------
        /**
         * This constructor takes the Name, and an array of guesses.
         * @param
         * @throws
         *
         * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
         */
        public ClientTest(String name, String[] guesses) throws IOException
        {
            setName(name);
            setGuesses(guesses);
            Socket sock = new Socket(HOST,PORT);
            reader = new TransactionReaderFactory(new BoggleStreamReader(sock.getInputStream()));
            writer = new BoggleStreamWriter(sock.getOutputStream());
            
            reader.registerListener(this);
            reader.start();
        }
        
        //==========================================================================
        //  VARIABLE(S)
        //==========================================================================
        
        public void sendName() throws DocumentCreationException, WritingException
        {
            writer.writeTransaction(new NameTransaction(getName()));
        }
        
        
        public void doGuesses()
        {
            for(int i=0;i<guesses.length;i++)
            {
                try
                {
                    writer.writeTransaction(new WordGuessTransaction(guesses[i]));
                }
                catch (WritingException ex)
                {
//                    ex.printStackTrace();
                    fail("Failed to guess word (" + guesses[i] + "): " + ex.toString());
                }
                catch (DocumentCreationException ex)
                {
//                    ex.printStackTrace();
                    fail("Failed to guess word (" + guesses[i] + "): " + ex.toString());
                }
            }
        }
        
        
        public void transactionOccurred(Transaction transaction)
        {
            if(transaction instanceof NameTransaction)
            {
                setOpponent(((NameTransaction)transaction).getName());
                System.out.println("Player " + getName() + " got opponent: " + getOpponent());
            }
            else if(transaction instanceof TimerTransaction)
            {
                TimerTransaction timer = (TimerTransaction)transaction;
                setElapsed(timer.getSeconds());
                setTotal(timer.getTotal());
            }
            else if(transaction instanceof GameBoardTransaction)
            {
                System.out.println("Player " + getName() + " got Gameboard");
                setDice(((GameBoardTransaction)transaction).getDice());
                try
                {
                    Thread.currentThread().sleep(500);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                doGuesses();
            }
            else if(transaction instanceof GameStatsTransaction)
            {
                setStats(((GameStatsTransaction)transaction).getStats());
                setComplete(true);
            }
        }
        
        //--------------------------------------------------------------------------
        // killClient
        //--------------------------------------------------------------------------
        /**
         * This method kills the Client.
         * @param
         * @return
         * @throws
         *
         * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
         */
        public void killClient() throws IOException
        {
            reader.getReader().getInputStream().getInputStream().close();
            writer.getOutput().close();
        }
        
        
        //==========================================================================
        //  GETTER(S) & SETTER(S)
        //==========================================================================
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public String[] getGuesses()
        {
            return guesses;
        }
        
        public void setGuesses(String[] guesses)
        {
            this.guesses = guesses;
        }
        
        public String getOpponent()
        {
            return opponent;
        }
        
        public void setOpponent(String opponent)
        {
            this.opponent = opponent;
        }
        
        public GameStatistics getStats()
        {
            return stats;
        }
        
        public void setStats(GameStatistics stats)
        {
            this.stats = stats;
        }
        
        public BoggleDice getDice()
        {
            return dice;
        }
        
        public void setDice(BoggleDice dice)
        {
            this.dice = dice;
        }
        
        public boolean isComplete()
        {
            return complete;
        }
        
        public void setComplete(boolean complete)
        {
            this.complete = complete;
        }
        
        public int getElapsed()
        {
            return elapsed;
        }
        
        public void setElapsed(int elapsed)
        {
            this.elapsed = elapsed;
        }
        
        public int getTotal()
        {
            return total;
        }
        
        public void setTotal(int total)
        {
            this.total = total;
        }
        
    }
}
