/*
 * WordGuestimator.java
 *
 * Created on September 7, 2007, 4:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.computer;

import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.WordValidator;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 *
 * @author Eric Internicola
 */
public class WordGuestimator
{
    private static final String[]   VOWELS              = { "a","e","i","o","u","y" };
    private static final String[]   CONSONANTS          = { "b","c","d","f","g","h","j","k","l","m","n","p","qu","r","s","t","v","w","x","z" };
    private static final String[]   COMMON_CONSONANTS   = { "r","t","s","l","n" };
    private static final String[]   DICTIONARIES        = { "eng_com.dic","custom.dic","center.dic","color.dic","ize.dic","labeled.dic","yze.dic" };
    private static Vector<String>   words               = null;
//    private BoggleDice              dice                = null;
    private Vector<String>          validWords          = null;
    private WordValidator           validator           = null;
    
    
    /** Creates a new instance of WordGuestimator */
    public WordGuestimator()
    {
        setValidWords(new Vector());
        setValidator(new WordValidator());
    }
    
    /**
     * This method loads the dictionaries.
     */
    public static synchronized void loadWords() throws IOException
    {
        setWords(new Vector());
        
        for(int i=0;i<DICTIONARIES.length;i++)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new BufferedInputStream(ClassLoader.getSystemResourceAsStream(DICTIONARIES[i]))));
            String data = reader.readLine();
            
            while(data!=null)
            {
                getWords().add(data);
                data = reader.readLine();
            }
            
            reader.close();
        }
    }
    
    /**
     * This method checks all of the words at the given index.
     */
    public boolean checkWordAtIndex(int index)
    {
        return checkWord(getWords().get(index));        
    }
    
    public boolean checkWord(String word)
    {
        boolean valid = false;
        
        try
        {
            valid = getValidator().validates(word);
            if(valid)
            {
                getValidWords().add(word);
                System.out.println("found word: " + word);
            }
        }
        catch(StringIndexOutOfBoundsException ex)
        {
            System.out.println("died on word: " + word);
            throw ex;
        }
        catch(NullPointerException ex)
        {
            System.out.println("died on word: " + word);
            throw ex;
        }
        
        return valid;
    }
    
    /**
     * This method does a brute force check of the words we've matched against the dictionary.
     */
    public void checkAllWords()
    {
        if(getDice()!=null)
        {
            getValidator().setDice(getDice());
            for(int i=0;i<getWords().size();i++)
            {
                checkWordAtIndex(i);
            }
        }
    }
    
    public BoggleDice getDice()
    {
        return getValidator().getDice();
    }
    
    public void setDice(BoggleDice dice)
    {
        getValidator().setDice(dice);
    }
    
    public static synchronized Vector<String> getWords()
    {
        if(words==null)
        {
            try
            {
                loadWords();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        
        return words;
    }
    
    protected static void setWords(Vector<String> theWords)
    {
        words = theWords;
    }
    
    public void setValidWords(Vector<String> validWords)
    {
        this.validWords = validWords;
    }
    
    public Vector<String> getValidWords()
    {
        return validWords;
    }
    
    public void setValidator(WordValidator validator)
    {
        this.validator = validator;
    }
    
    public WordValidator getValidator()
    {
        return validator;
    }
    
}
