/*
 * GameStatistics.java
 *
 * Created on April 2, 2006, 7:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author intere
 */
public class GameStatistics
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private BogglePlayer    player1             = null;
    private BogglePlayer    player2             = null;
    private Vector          player1Words        = null;
    private Vector          player2Words        = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Default Constructor; does not compute anything.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public GameStatistics()
    {
        setPlayer1Words(new Vector());
        setPlayer2Words(new Vector());
    }

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * 
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public GameStatistics(BogglePlayer player1, BogglePlayer player2)
    {
        setPlayer1Words(new Vector());
        setPlayer2Words(new Vector());
        
        setPlayer1(player1);
        setPlayer2(player2);
        
        compute();
    }    
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // compute
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void compute()
    {
        getPlayer1Words().clear();
        getPlayer2Words().clear();
        
        Logger.getLogger(getClass()).debug("player 1 words before computing: " + getPlayer1().getWords().toString());
        Logger.getLogger(getClass()).debug("player 2 words before computing: " + getPlayer2().getWords().toString());
        
        compareWords(getPlayer1().getWords(),getPlayer2().getWords(), getPlayer1Words());
        compareWords(getPlayer2().getWords(),getPlayer1().getWords(), getPlayer2Words());
        
        Logger.getLogger(getClass()).debug("player 1 words after computing: " + getPlayer1Words().toString());
        Logger.getLogger(getClass()).debug("player 2 words after computing: " + getPlayer2Words().toString());
        
        setPlayer1Points(scoreWords(getPlayer1Words()));
        setPlayer2Points(scoreWords(getPlayer2Words()));
    }
    
    //--------------------------------------------------------------------------
    // scoreWords
    //--------------------------------------------------------------------------
    /**
     * 
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    private int scoreWords(Vector words)
    {
        int points = 0;
        
        for(int i=0;i<words.size();i++)
        {
            points += howManyPoints(words.get(i).toString().length());
        }
        
        return points;
    }
    
    //--------------------------------------------------------------------------
    // howManyPoints
    //--------------------------------------------------------------------------
    /**
     * This method tells you how many points a word of "size" letters is.
     * @param size The length of the word.
     * @return The number of points that word is worth.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    private int howManyPoints(int size)
    {
        int points = 0;
        
        if(size==3||size==4)
        {
            points = 1;
        }
        else if(size==5)
        {
            points = 2;
        }
        else if(size==6)
        {
            points = 3;
        }
        else if(size==7)
        {
            points = 5;
        }
        else if(size>8)
        {
            points = 11;
        }
        
        return points;
    }
    
    //--------------------------------------------------------------------------
    // compareWords
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @return
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    private void compareWords(Vector words1, Vector words2, Vector unique)
    {
        unique.clear();
        
        for(int i=0;i<words1.size();i++)
        {
            boolean addIt = true;
            
            for(int j=0;j<words2.size();j++)
            {
                if(words1.get(i).equals(words2.get(j)))
                {
                    addIt = false;
                    break;
                }
            }
            
            if(addIt)
            {
                unique.add(words1.get(i));
            }
        }
    }
    
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================

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

    public int getPlayer1Points()
    {
        return getPlayer1().getScore();
    }

    public void setPlayer1Points(int player1Points)
    {
        getPlayer1().setScore(player1Points);
    }

    public int getPlayer2Points()
    {
        return getPlayer2().getScore();
    }

    public void setPlayer2Points(int player2Points)
    {
        getPlayer2().setScore(player2Points);
    }

    public Vector getPlayer1Words()
    {
        return player1Words;
    }

    public void setPlayer1Words(Vector player1Words)
    {
        this.player1Words = player1Words;
    }

    public Vector getPlayer2Words()
    {
        return player2Words;
    }

    public void setPlayer2Words(Vector player2Words)
    {
        this.player2Words = player2Words;
    }
    
}
