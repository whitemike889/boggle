/*
 * Dice.java
 *
 * Created on March 17, 2006, 4:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

import java.io.Serializable;
import java.util.Vector;

//--------------------------------------------------------------------------
// CLASS Dice
//--------------------------------------------------------------------------
/**
 * This class is used to manage the dice in a Boggle Game board.  The first
 * task was to define the dice (literally by taking each die from a physical
 * Boggle Game, and mapping it to a String in the <i>theDice</i> array below.
 * The next task was to create a way to "roll" a single die.  After this I 
 * needed a way to simulate randomly positioning the dice in the gameboard.
 * That is basically the functionality encapsulated here along with some public
 * APIs.
 * <br>
 * <b>Sample Usage</b>:
 * <pre>
 * BoggleDice dice = new BoggleDice();
 * dice.roll();
 * 
 * // print the dice roll using System.out
 * dice.printBoard();
 * 
 * // You can access each element of the roll using the
 * // following method call (where i and j are each an int
 * // between 0 and 3 inclusive):
 * // dice.getDiceRoll(i,j)
 * 
 * // You can also access the entire roll array directly using 
 * // the following method call: 
 * // String [][]roll = dice.getTheDice();
 * </pre>
 * 
 * 
 * 
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class BoggleDice
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final String [][]theDice = 
    {
        {"v","t","h","r","e","w"},
        {"qu","h","m","u","n","i"},
        {"d","l","y","e","r","v"},
        {"e","e","u","s","i","n"},
        {"g","h","w","e","e","n"},
        {"i","c","t","u","m","o"},
        {"a","e","a","n","g","e"},
        {"f","p","k","a","s","f"},
        {"h","c","p","o","s","a"},
        {"n","z","n","r","l","h"},
        {"o","a","w","t","o","t"},
        {"s","t","o","e","i","s"},
        {"j","b","o","a","b","o"},
        {"y","i","d","s","t","t"},
        {"d","e","x","l","i","r"},
        {"e","r","t","t","l","y"}
    };
    
    private String[][]      theRoll     = null;
    private int[]           diceOrder   = null;
    
    
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
    public BoggleDice()
    {
        setTheRoll(new String[4][4]);
        diceOrder = new int[theDice.length];
    }
    
    public BoggleDice(String[][] theRoll)
    {
        setTheRoll(theRoll);
        diceOrder = new int[theDice.length];
        for(int i=0; i<theDice.length; i++ )
        {
            diceOrder[i] = i;
        }
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================

    //--------------------------------------------------------------------------
    // roll
    //--------------------------------------------------------------------------
    /**
     * This method is responsible for giving you a "dice roll", a number between
     * 0 and 5 inclusive.
     * @return A "random" number between 0 and 5 inclusive.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected int roll()
    {
        return pickRandom(6);
    }
    
    //--------------------------------------------------------------------------
    // pickRandom
    //--------------------------------------------------------------------------
    /**
     * This method picks a random number between 0 and base-1 (base is the number
     * you provide).
     * @param base The "base" to select on.
     * @return An integer value between 0 and base.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected int pickRandom(int base)
    {
        return (int)(Math.random() * (double)base);
    }
    
    //--------------------------------------------------------------------------
    // selectOrder
    //--------------------------------------------------------------------------
    /**
     * This method selects the order of the dice that were rolled.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void selectOrder()
    {
        Vector v = new Vector();
        for(int i=0;i<theDice.length;i++)
        {
            v.add(new Integer(i));
        }
        
        int count = 0;
        
        while(v.size()!=0)
        {
            Integer iInt = (Integer)v.get(pickRandom(v.size()));
            diceOrder[count] = iInt.intValue();
            v.remove(iInt);
            ++count;
        }
    }
    
    //--------------------------------------------------------------------------
    // getDiceRoll
    //--------------------------------------------------------------------------
    /**
     * This method gives you the dice value at diceOrder[index], roll.
     * @param index The position in the Gameboard (0-15) that you want.
     * @param roll The roll value (0-5).
     * @return The String at diceOrder[index], roll.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public String getDiceRoll(int index, int roll)
    {
        return theDice[diceOrder[index]][roll];
    }
        
    //--------------------------------------------------------------------------
    // rollDice
    //--------------------------------------------------------------------------
    /**
     * This method is responsible for coordinating the "dice roll".  This method
     * takes care of "rolling" each die individually, as well as selecting a 
     * random displacement for the dice.  After calling this method, you should
     * are ready to start accessing the roll information.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void rollDice()
    {
        selectOrder();
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                int index = (i*4)+j;
                int roll = roll();
                String value = getDiceRoll(index,roll);
                getTheRoll()[i][j] = value;
            }
        }
    }
    
    //--------------------------------------------------------------------------
    // printBoard
    //--------------------------------------------------------------------------
    /**
     * This method is for debugging; it merely prints the board to standard out.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void printBoard()
    {
        for(int i=0;i<getTheRoll().length;i++)
        {
            for(int j=0;j<getTheRoll()[i].length;j++)
            {
                System.out.print(getTheRoll()[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    //--------------------------------------------------------------------------
    // toString
    //--------------------------------------------------------------------------
    /**
     * Overridden toString method - this is used to see the contents of the
     * Dice, as well as for serialization of the object.
     * @return A List of the Dice, separated by commas (for columns) and newlines
     * (for rows).
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        
        for(int i=0;i<getTheRoll().length;i++)
        {
            for(int j=0;j<getTheRoll()[i].length;j++)
            {
                if(j!=0)
                {
                    buff.append(',');
                }
                buff.append(getTheRoll()[i][j]);
            }
            buff.append('\n');
        }
        
        return buff.toString();
    }
    
    //--------------------------------------------------------------------------
    // getTheRoll
    //--------------------------------------------------------------------------
    /**
     * This method gives you access directly to the Roll Array itself.
     * @return A 2D array of the Dice Roll String.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public String[][] getTheRoll()
    {
        return theRoll;
    }

    public void setTheRoll(String[][] theRoll)
    {
        this.theRoll = theRoll;
    }
    
    //--------------------------------------------------------------------------
    // equals
    //--------------------------------------------------------------------------
    /**
     * This method compares another object to determine wether or not it is
     * equal.  If the object is another instance of a BoggleDice object, then
     * the toString() return values are used to determine the equality.
     * @param o The object to compare.
     * @return True if the provided object is a BoggleDice object and the value
     * of toString() matches the value of toString() for this object.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public boolean equals(Object o)
    {
        boolean equal = false;
        
        if(o instanceof BoggleDice)
        {
            equal = toString().equals(o.toString());
        }
        
        return equal;
    }
    
    //--------------------------------------------------------------------------
    // main
    //--------------------------------------------------------------------------
    /**
     * Test Driver; create a DiceRoll and output the toString() method to
     * System.out.
     * @param args The command line arguments.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public static void main(String[] args)
    {
        BoggleDice dice = new BoggleDice();
        dice.rollDice();
        System.out.println(dice.toString());
    }
}
