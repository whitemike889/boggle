/*
 * ComputerPlayerTest.java
 * JUnit based test
 *
 * Created on September 7, 2007, 11:21 PM
 */
package com.erici.boggle.computer;

import junit.framework.*;
import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.services.DiceService;

/**
 *
 * @author intere
 */
public class ComputerPlayerTest extends TestCase
{

    public ComputerPlayerTest(String testName)
    {
        super(testName);
    }

    protected void setUp() throws Exception
    {
    }

    protected void tearDown() throws Exception
    {
    }

    public void testComputerTest1() throws Exception
    {
        ComputerPlayer easy = new ComputerPlayer(ComputerPlayer.DIFFICULTY_EASY);
        ComputerPlayer med = new ComputerPlayer(ComputerPlayer.DIFFICULTY_MEDIUM);
        ComputerPlayer hard = new ComputerPlayer(ComputerPlayer.DIFFICULTY_HARD);
        BoggleDice dice = new BoggleDice();
        dice.rollDice();

        easy.setDice(dice);
        med.setDice(dice);
        hard.setDice(dice);

        System.out.println(dice);

        easy.start();
        med.start();
        hard.start();

        easy.getRunner().join(30000L);
        easy.setKeepGoing(false);
        med.setKeepGoing(false);
        hard.setKeepGoing(false);

        System.out.println("attempts: (easy=" + easy.getGuesses() + ", med=" + med.getGuesses() + ", hard=" + hard.getGuesses() + ")");
        System.out.println("correct:  (easy=" + easy.getGuestimator().getValidWords().size() + ", med=" + med.getGuestimator().getValidWords().size() +
                ", hard=" + hard.getGuestimator().getValidWords().size() + ")");
    }
}
