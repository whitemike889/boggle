/*
 * BoggleSettings.java
 *
 * Created on April 8, 2006, 3:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

//--------------------------------------------------------------------------
// CLASS BoggleSettings
//--------------------------------------------------------------------------
/**
 * This class is used to configure the BoggleGame.
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class BoggleSettings
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private int         gameTime    = 180;  // 3 minutes
    
    /** Creates a new instance of BoggleSettings */
    public BoggleSettings(int gameTime)
    {
        setGameTime(gameTime);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================

    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================

    public int getGameTime()
    {
        return gameTime;
    }

    public void setGameTime(int gameTime)
    {
        this.gameTime = gameTime;
    }
    
}
