/*
 * BoggleTimer.java
 *
 * Created on March 18, 2006, 6:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.game;

import com.erici.boggle.listeners.BoggleTimerListener;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Eric Internicola
 */
public class BoggleTimer extends Thread
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private static final int                DEFAULT_SECONDS = 90;  // 3 minutes.
    private static final int                ONE_SECOND      = 1000; // ms per second.
    private Date                            startTime       = null;
    private int                             timerSeconds    = DEFAULT_SECONDS;
    private int                             elapsedSeconds  = 0;
    private ArrayList<BoggleTimerListener>  listeners       = null;
    private boolean                         stop            = false;
    
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
    public BoggleTimer()
    {
        listeners = new ArrayList();
        
        String time = System.getProperty(Settings.GAME_TIMER);
        
        if(time!=null&&!"".equals(time))
        {
            timerSeconds = Integer.valueOf(time);
            Logger.getLogger(getClass()).info("Game timer defaults to: " + time);
        }
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // addListener
    //--------------------------------------------------------------------------
    /**
     * Add a BoggleTimerListener to the List of Listeners.
     * @param listener A BoggleTimerListener.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void addListener(BoggleTimerListener listener)
    {
        if(!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }
    
    //--------------------------------------------------------------------------
    // removeListener
    //--------------------------------------------------------------------------
    /**
     * Remove a BoggleTimerListener from the List.
     * @param listener The BoggleTimerListener to remove.
     * @return True if the listener was removed, false otherwise.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public boolean removeListener(BoggleTimerListener listener)
    {
        return listeners.remove(listener);
    }
    
    protected void fireUpdate()
    {
        for(int i=0;i<listeners.size();i++)
        {
            listeners.get(i).timerUpdate(getElapsedSeconds(),getTimerSeconds());
        }
    }
    
    protected void fireComplete()
    {
        for(int i=0;i<listeners.size();i++)
        {
            listeners.get(i).finished();
        }
    }
    
    //--------------------------------------------------------------------------
    // run
    //--------------------------------------------------------------------------
    /**
     * This method does all the work of running the timer, and stuff.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void run()
    {
        startTime = new Date();
        
        while(getElapsedSeconds()<getTimerSeconds()&&!stop)
        {
            try
            {
                sleep(ONE_SECOND);
                setElapsedSeconds((int)((new Date().getTime()-startTime.getTime())/ONE_SECOND));
                fireUpdate();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
        
        if(!stop)
        {
            fireComplete();
        }
    }
    
    //--------------------------------------------------------------------------
    // stopTimer
    //--------------------------------------------------------------------------
    /**
     * This method stops the timer.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void stopTimer()
    {
        stop = true;
    }
    
    //--------------------------------------------------------------------------
    // getTimerSeconds
    //--------------------------------------------------------------------------
    /**
     * The number of seconds that the Timer will wait for.
     * @return The number of seconds the timer is waiting for.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public int getTimerSeconds()
    {
        return timerSeconds;
    }
    
    public void setTimerSeconds(int timerSeconds)
    {
        this.timerSeconds = timerSeconds;
    }

    //--------------------------------------------------------------------------
    // getElapsedSeconds
    //--------------------------------------------------------------------------
    /**
     * The number of seconds that have gone by so far.
     * @return The time in seconds that have elapsed so far.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public int getElapsedSeconds()
    {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(int elapsedSeconds)
    {
        this.elapsedSeconds = elapsedSeconds;
    }
}
