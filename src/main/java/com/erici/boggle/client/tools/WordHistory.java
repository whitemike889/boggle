/*
 * WordHistory.java
 *
 * Created on November 25, 2006, 8:59 AM
 *
 */

package com.erici.boggle.client.tools;

import java.util.Vector;

/**
 *
 * @author root
 */
public class WordHistory
{
    //==========================================================================
    // Variable(s)
    //==========================================================================
    private Vector<String>      wordCache;
    private int                 wordIndex;
    private boolean             resetPostAdd;

    //==========================================================================
    // Constructor(s)
    //==========================================================================

    /** Creates a new instance of WordHistory */
    public WordHistory()
    {
        wordCache = new Vector();
    }
    
    //==========================================================================
    // Method(s)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // addWord
    //--------------------------------------------------------------------------
    /**
     * This method adds a word to the cache.  It will always be the last word
     * in the cache (even if it already exists in the cache).
     * @param word The word to add to the cache.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void addWord(String word)
    {
        wordCache.add(word);
    }
    
    public void reset()
    {
        wordIndex = wordCache.size()-1;
    }
    
    public String getCurrentWord()
    {
        return wordCache.get(wordIndex);
    }
    
    public String incrementWord()
    {
        wordIndex++;
        if(wordIndex>=wordCache.size())
        {
            reset();
        }
        return getCurrentWord();
    }
    
    public String decrementWord()
    {
        wordIndex--;
        if(wordIndex<0)
        {
            wordIndex = 0;
        }
        
        return decrementWord();
    }
    
    
    //==========================================================================
    // Getter(s) and Setter(s)
    //==========================================================================

    public boolean isResetPostAdd()
    {
        return resetPostAdd;
    }

    public void setResetPostAdd(boolean resetPostAdd)
    {
        this.resetPostAdd = resetPostAdd;
    }
    
}
