/*
 * Client.java
 *
 * Created on March 25, 2006, 4:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.client.net;

import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.io.reader.BoggleStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Eric Internicola
 */
public class Client
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private Socket              socket          = null;
    private ClientPlayer        player          = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor connects to the provided host on the provided port, and
     * creates the player (which then does the first task; sending the player
     * name to the server).
     * @param host The host to connect to.
     * @param port The port to connect on.
     * @param name The Player's Name.
     * @throws UnknownHostException
     * @throws IOException
     * @throws WritingException
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public Client(String host, int port, String name) throws UnknownHostException, IOException, WritingException
    {
        setSocket(new Socket(host, port));
        setPlayer(new ClientPlayer(getSocket(), name));
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    public ClientPlayer getPlayer()
    {
        return player;
    }

    public void setPlayer(ClientPlayer player)
    {
        this.player = player;
    }

    
    
}
