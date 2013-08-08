/*
 * WordListModel.java
 *
 * Created on March 18, 2006, 12:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.client.models;

import com.erici.boggle.client.listeners.ClientListener;
import com.erici.boggle.client.net.ClientPlayer;
import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.game.GameStatistics;
import javax.swing.DefaultListModel;

//--------------------------------------------------------------------------
// CLASS WordListModel
//--------------------------------------------------------------------------
/**
 * This class is repsonbile for interfacing between the word list and a 
 * an object that uses a ListModel as its model.
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class WordListModel extends DefaultListModel implements ClientListener
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private ClientPlayer      player        = null;
    
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
    public WordListModel(ClientPlayer player)
    {
        setPlayer(player);
    }
    

    //==========================================================================
    //  METHOD(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // getSize
    //--------------------------------------------------------------------------
    /**
     * The number of words the Player guessed.
     * @return The number of words that the player has guessed.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public int getSize()
    {
        if(getPlayer()!=null)
        {
            return getPlayer().getWords().size();
        }
        else
        {
            return 0;
        }
    }

    //--------------------------------------------------------------------------
    // getElementAt
    //--------------------------------------------------------------------------
    /**
     * This method gets you the word at the provided position.
     * @param index The element at which you want the word.
     * @return The word at the provided index.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public Object getElementAt(int index)
    {
        return getPlayer().getWords().get(index);
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================

    public BogglePlayer getPlayer()
    {
        return player;
    }

    public void setPlayer(ClientPlayer player)
    {
        if(this.player!=null)
        {
            this.player.registerListener(this);
        }
        
        this.player = player;
        
        if(player!=null)
        {
            player.registerListener(this);
            fireContentsChanged(this,0,player.getWords().size());
        }
        else
        {
            fireContentsChanged(this,0,0);
        }
    }

    public void gameRecieved(BoggleDice dice)
    {
    }

    public void opponentRecieved(String opponent)
    {
    }

    public void gameStatsRecieved(GameStatistics stats)
    {
    }

    public void wordGuessed(String guess)
    {
        fireContentsChanged(this,0,player.getWords().size());
    }

    public void timerUpdate(int seconds, int total)
    {
    }

    public void finished()
    {
    }

    public void errorRecieved(String error)
    {
    }
    
}
