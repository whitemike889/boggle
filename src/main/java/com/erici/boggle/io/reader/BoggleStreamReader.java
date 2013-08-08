/*
 * BoggleStreamReader.java
 *
 * Created on March 25, 2006, 9:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.erici.boggle.io.reader;

import com.erici.boggle.io.exceptions.ReadingException;
import com.xml.transport.io.XMLInputStream;
import com.xml.utils.XMLUtils;
import java.io.InputStream;
import org.w3c.dom.Document;

/**
 *
 * @author Eric Internicola
 */
public class BoggleStreamReader
{

    private XMLInputStream xin = null;

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * Default Constructor.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleStreamReader(InputStream inputStream)
    {
        setInputStream(inputStream);
    }

    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    //--------------------------------------------------------------------------
    // read
    //--------------------------------------------------------------------------
    /**
     * This method reads an XML Document from the XMLInputStream.
     * @return The XML Document that was read from the Socket.
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public Document read() throws ReadingException
    {
        try
        {
            Document doc = XMLUtils.readDocument(xin);
            xin.reset();

            return doc;
        }
        catch(Throwable ex)
        {
            throw new ReadingException(ex);
        }
    }

    //--------------------------------------------------------------------------
    // setInputStream
    //--------------------------------------------------------------------------
    /**
     * This method initializes the XMLInputStream from the provided Input Stream.
     * @param inputStream The InputStream to read.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void setInputStream(InputStream inputStream)
    {
        xin = new XMLInputStream(inputStream);
    }

    public XMLInputStream getInputStream()
    {
        return xin;
    }
}
