/*
 * DiceService.java
 *
 * Created on June 8, 2007, 9:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.services;

import com.erici.boggle.game.BoggleDice;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author intere
 */
public class DiceService
{
    public static final int ROTATE_090_DEG      = 0;
    public static final int ROTATE_180_DEG      = 1;
    public static final int ROTATE_270_DEG      = 2;
    
    /** should not be instantiated. */
    private DiceService()
    {
    }
    
    /**
     * This method rotates the Boggle Dice in the specified direction.
     * @param dice The dice board that you want rotated.
     * @param direction The direction; one of: ROTATE_090_DEG, ROTATE_180_DEG, ROTATE_270_DEG.
     * @return
     * @throws UnsupportedOperationException if you don't provide one of: ROTATE_090_DEG, ROTATE_180_DEG or ROTATE_270_DEG.
     */
    public static void rotateDice(BoggleDice dice, int direction)
    {
        switch(direction)
        {
            case ROTATE_090_DEG:
            {
                rotate090(dice);
                break;
            }
            
            case ROTATE_180_DEG:
            {
                rotate180(dice);
                break;
            }
            
            case ROTATE_270_DEG:
            {
                rotate270(dice);
                break;
            }
            
            default:
            {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }
    
    /**
     * This method rotates the gameboard in the following way:
     * [ w, x, y, z ]
     * [ a, b, c, d ]
     * [ f, g, h, i ]
     * [ j, k, l, m ]
     * becomes
     * [ j, f, a, w ]
     * [ k, g, b, x ]
     * [ l, h, c, y ]
     * [ m, i, d, z ]
     */
    protected static void rotate090(BoggleDice dice)
    {        
        int length = dice.getTheRoll().length;
        String [][]roll  = new String[length][length];
        
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                roll[i][j] = dice.getTheRoll()[j][length-1-i];
            }
        }
        
        dice.setTheRoll(roll);
    }
    
    /**
     * This method rotates the gameboard in the following way:
     * [ w, x, y, z ]
     * [ a, b, c, d ]
     * [ f, g, h, i ]
     * [ j, k, l, m ]
     * becomes
     * [ m, l, k, j ]
     * [ i, h, g, f ]
     * [ d, c, b, a ]
     * [ z, y, x, w ]
     */
    protected static void rotate180(BoggleDice dice)
    {
        int length = dice.getTheRoll().length;
        String [][]roll  = new String[length][length];
        
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                roll[i][j] = dice.getTheRoll()[length-1-i][length-1-j];
            }
        }
        
        dice.setTheRoll(roll);
    }
    
    /**
     * This method rotates the gameboard in the following way:
     * [ w, x, y, z ]
     * [ a, b, c, d ]
     * [ f, g, h, i ]
     * [ j, k, l, m ]
     * becomes
     * [ z, d, i, m ]
     * [ y, c, h, l ]
     * [ x, b, g, k ]
     * [ w, a, f, j ]
     *
     * (0,n-1), (1, n-1)
     */
    protected static void rotate270(BoggleDice dice)
    {        
        int length = dice.getTheRoll().length;
        String [][]roll  = new String[length][length];
        
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                roll[i][j] = dice.getTheRoll()[length-1-j][i];
            }
        }
        
        dice.setTheRoll(roll);
    }
}
