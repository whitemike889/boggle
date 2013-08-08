/*
 * TransactionListener.java
 *
 * Created on March 25, 2006, 6:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.listeners;

import com.erici.boggle.io.transactions.Transaction;
import com.erici.boggle.io.transactions.*;

/**
 * 
 * 
 * @author Eric Internicola
 */
public interface TransactionListener
{
    public void transactionOccurred(Transaction transaction);
}
