/*
 * WordValidator2.java
 *
 * Created on April 14, 2006, 10:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

import java.util.Vector;

/**
 *
 * @author Eric Internicola
 */
public class WordValidator
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final int    MAX_WORD_LEN    = 16;
    private static final int    MIN_WORD_LEN    = 3;
    
    private BoggleDice          dice            = null;
    private String              word            = null;
    private Vector<Position>    positions       = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public WordValidator()
    {
    }
    
    public WordValidator(BoggleDice dice)
    {
        setDice(dice);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // validates
    //--------------------------------------------------------------------------
    /**
     * This method tells you if the word validates.
     * @param word The word you want to validate.
     * @return True if the word is available on the board, false otherwise.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public boolean validates(String word)
    {
        setWord(word);
        return validates();
    }
    
    //--------------------------------------------------------------------------
    // validates
    //--------------------------------------------------------------------------
    /**
     * This method tells you if the word validates.
     * @return True if the word is available on the board, false otherwise.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public boolean validates()
    {
        boolean valid = true;
        setWord(getWord().trim().toLowerCase());
        
        // we can only use each letter once; so this would not be a valid case.
        if(getWord().length()>MAX_WORD_LEN||getWord().length()<MIN_WORD_LEN)
        {
            valid = false;
        }
        else
        {
            // now make sure each char is alpha-numerics.
            for(int i=0;i<getWord().length();i++)
            {
                if(getWord().charAt(i)<'a'||getWord().charAt(i)>'z')
                {
                    valid = false;
                    break;
                }
            }
            valid = valid && validateWord();
        }
        return valid;
    }
    
    //--------------------------------------------------------------------------
    // validateWord
    //--------------------------------------------------------------------------
    /**
     * This method will actually do the work of the validation of the word using
     * its recursive overloaded method that takes a position and a Vector of positions.
     * @return True if the word validates, False otherwise.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected boolean validateWord()
    {
        boolean valid = false;
        
        Vector<Position> positions = new Vector();
        
        valid = validateWord(0,positions);
        
        return valid;
    }
    
    //--------------------------------------------------------------------------
    // validateWord
    //--------------------------------------------------------------------------
    /**
     * This is the recursive workhorse of the validation.  This method iterates
     * through the board, searching for a matching String.  When a match is
     * found; it is added to the Position Vector and then called again recursively.
     * If the word is found on the board, then the walk that got us there is
     * stored in the Position Vector member property.
     * @param position The word position that we're currently working on.
     * @param positions The "walk" that got us to where we are; this is used
     * to detect collisions, and validate the walk.
     * @return True if there is a valid walk, false otherwise.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected boolean validateWord(int position, Vector<Position> positions)
    {
        boolean valid = false;
        
        if(position==getWord().length())
        {
            setPositions(positions);
            valid = true;
        }
        else
        {
            String [][]roll = getDice().getTheRoll();
            
            for(int i=0;i<roll.length;i++)
            {
                for(int j=0;j<roll[i].length;j++)
                {
                    // if the indexed character is equal to the roll, then lets check it out...
                    if(getPosition(position).equals(roll[i][j]))
                    {
                        Position p = new Position(i,j);
                        
                        if(!positions.contains(p))
                        {
                            if(position==0||isValidStep(positions.get(positions.size()-1),p))
                            {
                                positions.add(p);
                                valid = validateWord(increment(position),positions);
                                if(!valid)
                                {
                                    positions.remove(p);
                                }
                                else if(positions.size()==getWord().replaceAll("qu","q").length())
                                {
                                    setPositions(positions);
                                    return valid;
                                }
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }
    
    protected String getCurrentString(Vector<Position> v)
    {
        StringBuffer buff = new StringBuffer();
        for(int i=0;i<v.size();i++)
        {
            buff.append(getDice().getTheRoll()[v.get(i).getI()][v.get(i).getJ()]);
        }
        
        return buff.toString();
    }
    
    //--------------------------------------------------------------------------
    // getPosition
    //--------------------------------------------------------------------------
    /**
     * This method gets you the String at the provided position.  Why is this
     * a method?  Because Boggle can has some dice with a side of "qu" instead
     * of just a single character.  For this reason, we need a method to simplify
     * the task of getting the next letter, rather than doing the bookkeeping
     * elsewhere and cluttering other more complex methods.
     * @param position The position that we we the String at.
     * @return The String at the provided position.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected String getPosition(int position)
    {
        return getWord().substring(position,increment(position));
    }
    
    //--------------------------------------------------------------------------
    // increment
    //--------------------------------------------------------------------------
    /**
     * This method encapsulates the logic of incrementing the current position.
     * Why is this a method; again for the same reason that getPosition is a
     * method; to simplify the "qu" dilemna in other areas.  This simplifies the
     * logic in the validateWord methods.
     * @param position The position to be incremented.
     * @return The next incremental position based on the current position and
     * what the current token is.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected int increment(int position)
    {
        int newPosition = position;
        if(getWord().charAt(position)=='q')
        {
            newPosition += 2;
        }
        else
        {
            newPosition += 1;
        }
        
        return newPosition;
    }
    
    //--------------------------------------------------------------------------
    // isValidStep
    //--------------------------------------------------------------------------
    /**
     * This method will tell you if the step from position p1 to position p2 is
     * a valid one.  If it is only a step of one, then it is valid, otherwise
     * it is not valid.  This method does not take into account any state information
     * (historicaly walk information).  Only p1 and p2.
     * @param p1 The Starting position.
     * @param p2 The Ending position.
     * @return True if the step is valid, False otherwise.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected boolean isValidStep(Position p1, Position p2)
    {
        int di = Math.abs(p1.getI()-p2.getI());
        int dj = Math.abs(p1.getJ()-p2.getJ());
        
        return (di==0&&dj==1) || (di==1&&dj==0) || (di==1&&dj==1);
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================
    
    public BoggleDice getDice()
    {
        return dice;
    }
    
    public void setDice(BoggleDice dice)
    {
        this.dice = dice;
    }
    
    public String getWord()
    {
        return word;
    }
    
    public void setWord(String word)
    {
        this.word = word;
    }
    
    public Vector<Position> getPositions()
    {
        return positions;
    }
    
    public void setPositions(Vector<Position> positions)
    {
        this.positions = positions;
    }
}

