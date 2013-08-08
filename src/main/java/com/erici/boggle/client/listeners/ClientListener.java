/*
 * ClientListener.java
 *
 * Created on March 30, 2006, 6:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.client.listeners;

import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.GameStatistics;
import com.erici.boggle.listeners.BoggleTimerListener;

/**
 *
 * @author intere
 */
public interface ClientListener extends BoggleTimerListener
{
    public void gameRecieved(BoggleDice dice);
    public void opponentRecieved(String opponent);
    public void gameStatsRecieved(GameStatistics stats);
    public void wordGuessed(String guess);
    public void errorRecieved(String error);
}
