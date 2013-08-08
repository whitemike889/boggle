/*
 * BoggleGame.java
 *
 * Created on March 17, 2006, 10:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

/**
 *
 * @author Eric Internicola
 */
public class BoggleGameModel
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private BoggleDice      dice        = null;
    private BoggleTimer     timer       = null;
    private BogglePlayer    player1     = null;
    private BogglePlayer    player2     = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Default Constructor.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleGameModel()
    {
        
    }
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets the Boggle Dice.
     * @param dice The BoggleDice object to set.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleGameModel(BoggleDice dice)
    {
        setDice(dice);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================


    public BoggleDice getDice()
    {
        return dice;
    }

    public void setDice(BoggleDice dice)
    {
        this.dice = dice;
    }

    public BogglePlayer getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(BogglePlayer player1)
    {
        this.player1 = player1;
    }

    public BogglePlayer getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(BogglePlayer player2)
    {
        this.player2 = player2;
    }

    public BoggleTimer getTimer()
    {
        return timer;
    }

    public void setTimer(BoggleTimer timer)
    {
        this.timer = timer;
    }
    
}
