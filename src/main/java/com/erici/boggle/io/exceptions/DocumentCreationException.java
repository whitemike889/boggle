/*
 * DocumentCreationException.java
 *
 * Created on March 25, 2006, 9:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.io.exceptions;

/**
 *
 * @author Eric Internicola
 */
public class DocumentCreationException extends Exception
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
    public DocumentCreationException(String message)
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
    public DocumentCreationException(Throwable t)
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
    public DocumentCreationException(String message, Throwable t)
    {
        super(message,t);
    }
    
}
