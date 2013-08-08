/*
 * Position.java
 *
 * Created on June 3, 2007, 12:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

//--------------------------------------------------------------------------
// Position
//--------------------------------------------------------------------------
/**
 * This class is used to keep track of the position of a walk.
 * @param
 * @return
 * @throws
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class Position
{
    private int i = -1;
    private int j = -1;
    
    public Position()
    {
        
    }
    
    public Position(int i,int j)
    {
        setI(i);
        setJ(j);
    }
    
    public int getI()
    {
        return i;
    }
    
    public void setI(int i)
    {
        this.i = i;
    }
    
    public int getJ()
    {
        return j;
    }
    
    public void setJ(int j)
    {
        this.j = j;
    }
    
    public boolean equals(Object o)
    {
        boolean equal = false;
        if(o instanceof Position)
        {
            Position p = (Position)o;
            equal = p.getI()==getI() && p.getJ()==getJ();
        }
        return equal;
    }
    
}
