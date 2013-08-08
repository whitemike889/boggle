/*
 * BoggleBoardController.java
 *
 * Created on March 18, 2006, 7:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.erici.boggle.client.tools;

import com.erici.boggle.client.gui.BoggleGUI;
import com.erici.boggle.game.Settings;
import com.erici.boggle.io.exceptions.WritingException;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author Eric Internicola
 */
public class BoggleBoardController
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private BoggleGUI ui = null;

    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    public BoggleBoardController() throws UnknownHostException, IOException, WritingException
    {
        this(System.getProperty(Settings.GAME_SERVER),
                Integer.parseInt(System.getProperty(Settings.GAME_PORT)),
                System.getProperty(Settings.USER_NAME));
    }

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Default Constructor.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleBoardController(String host, int port, String name) throws UnknownHostException, IOException, WritingException
    {
        ui = new BoggleGUI(host, port, name);
    }
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
}
