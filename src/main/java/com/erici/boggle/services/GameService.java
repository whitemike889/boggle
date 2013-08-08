/*
 * GameService.java
 *
 * Created on March 17, 2006, 10:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.services;

import com.erici.boggle.game.BoggleGameModel;
import java.util.Vector;
import com.erici.boggle.game.*;

/**
 * 
 * 
 * 
 * @author Eric Internicola
 */
public class GameService
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static GameService              instance    = null;
    private Vector<BoggleGameModel>         games       = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    private GameService()
    {
        games = new Vector();
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // findGame
    //--------------------------------------------------------------------------
    /**
     *     This method finds the game that has the provided player in it.
     *     @param playerName The player that you're trying to find a game for.
     *     @return The BoggleGame instance that the player is in.
     *          @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     *     
     */
    public BoggleGameModel findGame(String playerName)
    {
        BoggleGameModel game = null;
        
        for(int i=0;i<games.size();i++)
            {
            if(games.get(i).getPlayer1()!=null&&games.get(i).getPlayer1().getPlayerName().equals(playerName))
                {
                game = games.get(i);
                break;
            }
            else if(games.get(i).getPlayer2()!=null&&games.get(i).getPlayer2().getPlayerName().equals(playerName))
                {
                game = games.get(i);
                break;
            }
        }
        
        return null;
    }
    
    //--------------------------------------------------------------------------
    // createGame
    //--------------------------------------------------------------------------
    /**
     *          @param
     *     @return
     *     @throws
     *          @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     *     
     */
    public com.erici.boggle.game.BoggleGameModel createGame(String playerName)
    {
        BoggleGameModel game = findGame(playerName);
        
        if(game==null)
            {
            game = new BoggleGameModel();
            
        }
        
        return null;        
    }
    

    //--------------------------------------------------------------------------
    // get
    //--------------------------------------------------------------------------
    /**
     * Singleton Accessor method.
     * @return The GameService instance.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public static synchronized GameService get()
    {
        if(instance==null)
        {
            instance = new GameService();
        }
        
        return instance;
    }
    
}
