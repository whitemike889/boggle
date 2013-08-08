/*
 * BoggleTimerListener.java
 *
 * Created on March 18, 2006, 6:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.listeners;

//--------------------------------------------------------------------------
// aMethod
//--------------------------------------------------------------------------
/**
 *
 * @param
 * @return
 * @throws
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public interface BoggleTimerListener
{
    public void timerUpdate(int seconds,int total);
    public void finished();
}
