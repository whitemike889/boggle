/*
 * BogglePlayer.java
 *
 * Created on March 17, 2006, 10:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

import java.util.Vector;

/**
 * 
 * 
 * @author Eric Internicola
 */
public class BogglePlayer
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private String  playerName          = null;
    private int     score               = 0;
    private Vector  words               = null;
    
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
    public BogglePlayer()
    {
        setWords(new Vector());
    }
    
    public BogglePlayer(String playerName)
    {
        this();
        setPlayerName(playerName);
    }
    
    public BogglePlayer(String playerName, Vector words)
    {
        setPlayerName(playerName);
        setWords(words);
    }
    
    public BogglePlayer(String playerName, String[]words)
    {
        this(playerName);
        for(int i=0;i<words.length;i++)
        {
            getWords().add(words[i]);
        }
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // addWord
    //--------------------------------------------------------------------------
    /**
     * This method adds a word to the players Word List.
     * @param word The Players new word.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public boolean addWord(String word)
    {
        String clean = word.trim().toLowerCase();
        boolean add = !words.contains(clean);
        if(add)
        {
            words.add(clean);
        }
        
        return add;
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public Vector getWords()
    {
        return words;
    }

    public void setWords(Vector words)
    {
        this.words = words;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}
