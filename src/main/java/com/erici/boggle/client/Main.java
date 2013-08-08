/*
 * Main.java
 *
 * Created on March 17, 2006, 11:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.client;

import com.erici.boggle.client.tools.BoggleBoardController;
import com.erici.boggle.io.exceptions.WritingException;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sourceforge.napkinlaf.NapkinLookAndFeel;
import org.apache.log4j.Logger;

/**
 *
 * @author intere
 */
public class Main
{
    //==========================================================================
    // METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // main
    //--------------------------------------------------------------------------
    /**
     * Entry Point into application.
     * @param args Command Line Arguments.
     * @throws UnknownHostException
     * @throws IOException
     * @throws WritingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public static void main(String args[]) throws UnknownHostException, IOException, WritingException
    {
        Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(),"System.out"));
        
        if(args.length!=3)
        {
            Logger.getLogger(Main.class).info("Usage: BoggleGUI [host] [port] [name]\n\n\thost = The host running the Boggle Server\n\t" +
                "port = The port the Boggle Server is running on\n\tname Your Username");
        }
        else
        {
            try
            {
                UIManager.setLookAndFeel(new NapkinLookAndFeel());
            }
            catch (UnsupportedLookAndFeelException ex)
            {
                System.out.println("Could not load Napkin Look and feel");
            }
            new BoggleBoardController(args[0],Integer.parseInt(args[1]),args[2]);
        }
    }
    
}
