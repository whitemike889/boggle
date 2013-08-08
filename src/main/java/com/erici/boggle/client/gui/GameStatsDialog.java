/*
 * GameStatsDialog.java
 *
 * Created on April 4, 2006, 5:22 PM
 */

package com.erici.boggle.client.gui;

import com.erici.boggle.game.BogglePlayer;
import com.erici.boggle.game.GameStatistics;
import java.awt.Frame;
import java.util.Vector;
import javax.swing.JDialog;
import org.apache.log4j.Logger;

//==========================================================================
//  CLASS GameStatsDialog
//==========================================================================
/**
 *
 *
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class GameStatsDialog extends JDialog
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private GameStatistics      stats           = null;
    private Vector              CommonWords     = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     *
     * @param
     * @throws
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public GameStatsDialog(Frame parent, boolean modal)
    {
        super(parent, modal);
        setTitle("Game Results");
        initComponents();
    }
    
    //--------------------------------------------------------------------------
    // renderStats
    //--------------------------------------------------------------------------
    /**
     * This method renders the stats and displays them nicely for you.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void renderStats()
    {
        getStats().compute();
        
        StringBuffer buff = new StringBuffer();
        
        renderPlayerStats(buff);
        
        // Player 1 Words
        renderCommonWords(buff);
        
        for(int i=0;i<getStats().getPlayer1().getWords().size();i++)
        {
            if(getStats().getPlayer1Words().contains(getStats().getPlayer1().getWords().get(i)))
            {
                renderWord(buff,getStats().getPlayer1().getWords().get(i).toString(),true);
            }
        }
        
        buff.append("</td><td valign='top'>");        
               
        // Player 2 Words
        renderCommonWords(buff);
        
        for(int i=0;i<getStats().getPlayer2().getWords().size();i++)
        {
            if(getStats().getPlayer2Words().contains(getStats().getPlayer2().getWords().get(i)))
            {
                renderWord(buff,getStats().getPlayer2().getWords().get(i).toString(),true);
            }
        }
        
        buff.append("</td></tr></table>");
        
        
        jtpOutput.setContentType("text/html");
        jtpOutput.setText(buff.toString());
        jtpOutput.setEditable(false);
    }
    
    protected synchronized void renderCommonWords(StringBuffer buff)
    {
        if(CommonWords==null)
        {
            CommonWords = new Vector();
        }
        
        for(int i=0;i<getStats().getPlayer1().getWords().size();i++)
        {
            String word = getStats().getPlayer1().getWords().get(i).toString();
            
            if(getStats().getPlayer2Words().contains(word))
            {
                CommonWords.add(word);
            }
            
            renderWord(buff,word,false,i!=0);
        }
    }
    
    /**
     * Render the word, and default "newline" to true.
     */
    protected void renderWord(StringBuffer buff, String word, boolean distinct)
    {
        renderWord(buff,word,distinct,true);
    }
    
    /**
     * This renders the provided word in the provided buffer with the font appropriate to wether or not
     * it is distinct.  Additionally you can specify true if you want a newline (BR tag) rendered before this element.
     */
    protected void renderWord(StringBuffer buff, String word, boolean distinct, boolean newline)
    {
        if(newline)
        {
            buff.append("<br>");
        }
        
        if(!distinct)
        {
            buff.append("<font color='red'>");
        }
        else
        {
            buff.append("<font color='blue'>");
        }
        buff.append(word);
        buff.append("</font>");
    }
    
    protected void renderPlayerStats(StringBuffer buff)
    {
        buff.append("<html><head><title>Game Results</title></head><body>");
        buff.append("<table border='1'><tr><td>Name</td><td>");
        
        if(getStats().getPlayer1Points()>getStats().getPlayer2Points())
        {
            buff.append("<b>" + getStats().getPlayer1().getPlayerName() + "</b>");
        }
        else
        {
            buff.append(getStats().getPlayer1().getPlayerName());
        }
        
        buff.append("</td><td>");
        
        if(getStats().getPlayer2Points()>getStats().getPlayer1Points())
        {
            buff.append("<b>" + getStats().getPlayer2().getPlayerName() + "</b>");
        }
        else
        {
            buff.append(getStats().getPlayer2().getPlayerName());
        }
        
        buff.append("</td></tr>");
        buff.append("<tr><td>Points</td><td align='center'>" + getStats().getPlayer1Points() + "</td><td align='center'>" +
                getStats().getPlayer2Points() + "</td></tr>");
        buff.append("<td valign='top'>Words</td><td valign='top'>");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jspScroller = new javax.swing.JScrollPane();
        jtpOutput = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jspScroller.setViewportView(jtpOutput);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jspScroller, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jspScroller, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    public static void main(String[] args)
    {
        Logger.getRootLogger().addAppender(
                new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout(),"System.out"));
        
        GameStatsDialog dialog = new GameStatsDialog(null,true);
        String words1[] = {"barn", "hen", "chicken", "rooster", "cow", "moth", "pork", "joe", "bob"};
        String words2[] = {"barn", "chicken", "turkey", "cow", "giraffe", "hen", "bob"};
        BogglePlayer p1 = new BogglePlayer("Susan", words1);
        BogglePlayer p2 = new BogglePlayer("George",words2);
        
        GameStatistics stats = new GameStatistics();
        stats.setPlayer1(p1);
        stats.setPlayer2(p2);
        
        dialog.setStats(stats);
        dialog.setVisible(true);
    }
    
    //==========================================================================
    //  GETTER(S) & SETTER(S)
    //==========================================================================
    
    public GameStatistics getStats()
    {
        return stats;
    }
    
    public void setStats(GameStatistics stats)
    {
        this.stats = stats;
        renderStats();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jspScroller;
    private javax.swing.JTextPane jtpOutput;
    // End of variables declaration//GEN-END:variables
    
}
