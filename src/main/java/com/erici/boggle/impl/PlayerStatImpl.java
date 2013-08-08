/*
 * PlayerStatImpl.java
 *
 * Created on June 9, 2007, 4:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.impl;

import com.erici.boggle.interfaces.PlayerStat;

/**
 *
 * @author intere
 */
public class PlayerStatImpl implements PlayerStat
{
    private int     points      = 0;
    private String  username    = null;
    
    /** Creates a new instance of PlayerStatImpl */
    public PlayerStatImpl(String username, int points)
    {
        this.username = username;
        this.points = points;
    }

    public String getUsername()
    {
        return username;
    }

    public int getPoints()
    {
        return points;
    }
    
}
