/*
 * ComputerPlayer.java
 *
 * Created on September 7, 2007, 4:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.computer;

import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.BogglePlayer;
import java.util.Vector;

/**
 *
 * @author Eric Internicola
 */
public class ComputerPlayer extends BogglePlayer implements Runnable
{
    public static final String  COMPUTER            = "Computer";
    public static final int     DIFFICULTY_EASY     = 300;
    public static final int     DIFFICULTY_MEDIUM   = 150;
    public static final int     DIFFICULTY_HARD     = 200;
    public static final long    TIMER               = 100L;
    private WordGuestimator     guestimator         = null;
    private Thread              runner              = null;
    private int                 guesses             = 0;
    private boolean             keepGoing           = true;
    private int                 difficulty          = DIFFICULTY_EASY;
    private Vector<String>      guessed             = null;
    private boolean             lastWordIsHit       = false;
    private int                 wordIndex           = -1;
    private int                 offset              = -9;
    
    /** Creates a new instance of ComputerPlayer */
    public ComputerPlayer()
    {
        this(DIFFICULTY_EASY);
    }
    
    public ComputerPlayer(int difficulty)
    {
        super(COMPUTER);
        setGuestimator(new WordGuestimator());
        setDifficulty(difficulty);
        setGuessed(new Vector());
    }
    
    public BoggleDice getDice()
    {
        return getGuestimator().getDice();
    }
    
    public void setDice(BoggleDice dice)
    {
        getGuestimator().setDice(dice);
    }
    
    public void setGuestimator(WordGuestimator guestimator)
    {
        this.guestimator = guestimator;
    }
    
    public WordGuestimator getGuestimator()
    {
        return guestimator;
    }
    
    public void start()
    {
        setRunner(new java.lang.Thread(this));
        getRunner().start();
    }
    
    public void run()
    {
        try
        {
            while(isKeepGoing())
            {
                for(int i=0;i<getDifficulty();i++)
                {
                    switch(getDifficulty())
                    {
                        case DIFFICULTY_EASY:
                        {
                            methodRandom();
                            break;
                        }
                        case DIFFICULTY_MEDIUM:
                        {
                            methodHybrid();
                            break;
                        }
                        case DIFFICULTY_HARD:
                        {
                            methodDictionary(guesses);
                            break;
                        }
                    }
                }
                Thread.sleep(TIMER);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void methodHybrid()
    {
        if(!lastWordIsHit)
        {
            methodRandom();
        }
        else
        {
            if(offset==10)
            {
                lastWordIsHit = false;
                methodHybrid();
            }
            else
            {
                int index = wordIndex + offset;
                if(index < 0)
                {
                    ++offset;
                    methodHybrid();
                }
                else if(index>=getGuestimator().getWords().size())
                {
                    offset=10;
                    methodHybrid();
                }
                else
                {
                    methodDictionary(index);
                    ++offset;
                }
            }
        }        
    }
    
    public void methodDictionary(int index)
    {
        getGuestimator().checkWordAtIndex(index);
        getGuessed().add(getGuestimator().getWords().get(index));
        ++guesses;
    }
    
    public void methodRandom()
    {
        String word = getRandomWord();
        while(getGuessed().contains(word))
        {
            word = getRandomWord();
        }
        
        lastWordIsHit = getGuestimator().checkWord(word);
        getGuessed().add(word);
        ++guesses;
    }
    
    /**
     * This method gets you a random word.
     */
    public String getRandomWord()
    {
        wordIndex = (int)(Math.random() * (double)getGuestimator().getWords().size());
        String word = getGuestimator().getWords().get(wordIndex);
        
        return word;
    }
    
    public Thread getRunner()
    {
        return runner;
    }
    
    public int getGuesses()
    {
        return guesses;
    }
    
    public boolean isKeepGoing()
    {
        return keepGoing;
    }
    
    public void setGuesses(int guesses)
    {
        this.guesses = guesses;
    }
    
    public void setKeepGoing(boolean keepGoing)
    {
        this.keepGoing = keepGoing;
    }
    
    public void setRunner(Thread runner)
    {
        this.runner = runner;
    }
    
    public int getDifficulty()
    {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }
    
    public Vector<String> getGuessed()
    {
        return guessed;
    }
    
    public void setGuessed(Vector<String> guessed)
    {
        this.guessed = guessed;
    }
}
