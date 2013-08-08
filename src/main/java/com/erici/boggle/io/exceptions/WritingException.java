/*
 * WritingException.java
 *
 * Created on March 25, 2006, 5:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.exceptions;

/**
 *
 * @author Eric Internicola
 */
public class WritingException extends Exception
{
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets the message.
     * @param message The message to set.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public WritingException(String message)
    {
        super(message);
    }
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets the source of the Reading exception.
     * @param t The Source of the Reading Exception.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public WritingException(Throwable t)
    {
        super(t);
    }
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This constructor sets teh Message and the source of the Reading Exception.
     * @param message The message to set.
     * @param t The Source of the Reading Exception.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public WritingException(String message, Throwable t)
    {
        super(message,t);
    }
}
