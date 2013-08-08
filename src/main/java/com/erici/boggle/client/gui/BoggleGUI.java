/*
 * BoggleGUI.java
 *
 * Created on March 17, 2006, 11:12 PM
 */
package com.erici.boggle.client.gui;

import com.erici.boggle.client.listeners.ClientListener;
import com.erici.boggle.client.net.Client;
import com.erici.boggle.client.tools.SpellCheckerTool;
import com.erici.boggle.game.*;
import com.erici.boggle.client.models.WordListModel;
import com.erici.boggle.game.GameStatistics;
import com.erici.boggle.io.exceptions.WritingException;
import com.erici.boggle.services.DiceService;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author  <a href="intere@gmail.com">Eric Internicola</a>
 */
public class BoggleGUI extends JFrame implements ClientListener
{

    private WordValidator validator = new WordValidator();
    private BoggleBoard board = null;
    private WordListModel wordModel = null;
    private Client client = null;
    private GameStatsDialog dialog = null;
    private String host = null;
    private int port = 0;
    private String username = null;

    /**
     * This constrcutor connects to the provided host on the provided port, sends
     * the username, and then awaits a game start.
     * @param host The host to connect to.
     * @param port The port to connect to.
     * @param name The username for the player to use.
     * @throws UnknownHostException
     * @throws IOException
     * @throws WritingException
     *
     */
    public BoggleGUI(String host, int port, String name)
    {
        setTitle("Boggle -- " + name + " --");
        setHost(host);
        setPort(port);
        setUsername(name);
        setBoard(new BoggleBoard());

        initComponents();

        jspBoard.setViewportView(getBoard());

        setupListeners();

        timerUpdate(0, 0);

        wordModel = new WordListModel(null);
        jlWords.setModel(wordModel);

        dialog = new GameStatsDialog(this, true);

        jtfWordEntry.requestFocus();

        setVisible(true);
        SpellCheckerTool.get();
    }

    /**
     * This method starts a new game unless there is an exception.  If there is
     * an exception, then an error message is displayed.
     *
     */
    public void newGame()
    {
        try
        {
            timerUpdate(0, 0);
            board.getModel().setDice(null);
            board.repaint();
            client = new Client(getHost(), getPort(), getUsername());
            wordModel.setPlayer(client.getPlayer());
            client.getPlayer().registerListener(this);
        }
        catch(Throwable t)
        {
            JOptionPane.showMessageDialog(this, t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //--------------------------------------------------------------------------
    // addText
    //--------------------------------------------------------------------------
    /**
     * This method adds the provided text to the word model.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void addText()
    {
        String word = jtfWordEntry.getText();

        if(validator.validates(word))
        {
            if(SpellCheckerTool.get().checkWord(word))
            {
                client.getPlayer().guessWord(jtfWordEntry.getText());
                jtfWordEntry.setText("");
                jlWords.ensureIndexIsVisible(jlWords.getModel().getSize() - 1);
            }
            else
            {
                jtfWordEntry.setForeground(Color.blue);
                jtfWordEntry.setSelectionStart(0);
                jtfWordEntry.setSelectionEnd(jtfWordEntry.getText().length());
            }
        }
        else
        {
            jtfWordEntry.setForeground(Color.red);
            jtfWordEntry.setSelectionStart(0);
            jtfWordEntry.setSelectionEnd(jtfWordEntry.getText().length());
        }
    }


    /**
     * This method creates and assigns the listeners to the buttons.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void setupListeners()
    {
        jbAdd.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                addText();
            }
        });

        jtfWordEntry.addKeyListener(new KeyListener()
        {

            public void keyPressed(KeyEvent e)
            {
            }

            public void keyReleased(KeyEvent e)
            {
                jtfWordEntry.setForeground(Color.black);
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    addText();
                }
                else if(e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_U))
                {
                    jtfWordEntry.setText("");
                }
            }

            public void keyTyped(KeyEvent e)
            {
            }
        });
    }


    /**
     * This method is called when the gameboard is recieved.
     * @param dice The BoggleDice that were recieved.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void gameRecieved(BoggleDice dice)
    {
        validator.setDice(dice);
        getBoard().getModel().setDice(dice);
        unlockForm();
        getBoard().repaint();
    }


    /**
     * This method is called when the Opponent is recieved from the Server.
     * @param opponent The name of the user you're playing against.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void opponentRecieved(String opponent)
    {
        jlOpponent.setText(opponent);
    }


    /**
     * This method is called when a timer transaction is recieved.
     * @param seconds The number of elapsed seconds.
     * @param total The total number of elapsed seconds.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void timerUpdate(int seconds, int total)
    {
        int remain = total - seconds;

        int min = (remain / 60);
        int sec = (remain % 60);

        String time = min + ":";
        if(sec < 10)
        {
            time += "0";
        }
        time += sec;

        jlTime.setText(time);

        if(seconds >= total)
        {
            lockForm();
        }
    }

    public void finished()
    {

    }


    /**
     * This method is called when game statistics are recieved.
     * @param stats The Game Statistics that were recieved.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void gameStatsRecieved(GameStatistics stats)
    {
        dialog.setStats(stats);
        dialog.setVisible(true);
    }


    /**
     * This method locks the form.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void lockForm()
    {
        jtfWordEntry.setEnabled(false);
        jbAdd.setEnabled(false);
    }


    /**
     * This method unlocks the form.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void unlockForm()
    {
        jtfWordEntry.setEnabled(true);
        jbAdd.setEnabled(true);
        jtfWordEntry.requestFocus();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jLabel1 = new javax.swing.JLabel();
        jtfWordEntry = new javax.swing.JTextField();
        jbAdd = new javax.swing.JButton();
        jspWords = new javax.swing.JScrollPane();
        jlWords = new javax.swing.JList();
        jToolBar1 = new javax.swing.JToolBar();
        jl1 = new javax.swing.JLabel();
        jlTime = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jlOpponent = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jspBoard = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmiNewGame = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jmiQuit = new javax.swing.JMenuItem();
        jmBoard = new javax.swing.JMenu();
        jmiRotate90 = new javax.swing.JMenuItem();
        jmiRotate180 = new javax.swing.JMenuItem();
        jmiRotate270 = new javax.swing.JMenuItem();
        jmInfo = new javax.swing.JMenu();
        jmiShowStats = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jLabel1.setText("Boggle Board");

        jbAdd.setText("add");

        jspWords.setViewportView(jlWords);

        jToolBar1.setFloatable(false);
        jl1.setText("Time: ");
        jToolBar1.add(jl1);

        jlTime.setText("0");
        jToolBar1.add(jlTime);

        jSeparator1.setMaximumSize(new java.awt.Dimension(100, 0));
        jSeparator1.setMinimumSize(new java.awt.Dimension(2, 0));
        jToolBar1.add(jSeparator1);

        jLabel3.setText("Opponent: ");
        jToolBar1.add(jLabel3);

        jlOpponent.setText(" Unknown");
        jToolBar1.add(jlOpponent);

        jLabel2.setText("Words");

        jspBoard.setMaximumSize(new java.awt.Dimension(100, 100));
        jspBoard.setMinimumSize(new java.awt.Dimension(100, 100));

        jmFile.setMnemonic('f');
        jmFile.setText("File");
        jmFile.setToolTipText("File Menu");
        jmiNewGame.setMnemonic('n');
        jmiNewGame.setText("New Game");
        jmiNewGame.setToolTipText("Select this if you want to start a new game.");
        jmiNewGame.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmiNewGameActionPerformed(evt);
            }
        });

        jmFile.add(jmiNewGame);

        jmFile.add(jSeparator2);

        jmiQuit.setMnemonic('q');
        jmiQuit.setText("Quit");
        jmiQuit.setToolTipText("Quit Boggle");
        jmiQuit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmiQuitActionPerformed(evt);
            }
        });

        jmFile.add(jmiQuit);

        jMenuBar1.add(jmFile);

        jmBoard.setText("Board");
        jmiRotate90.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jmiRotate90.setText("Rotate 90");
        jmiRotate90.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmiRotate90ActionPerformed(evt);
            }
        });

        jmBoard.add(jmiRotate90);

        jmiRotate180.setText("Rotate 180");
        jmiRotate180.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmiRotate180ActionPerformed(evt);
            }
        });

        jmBoard.add(jmiRotate180);

        jmiRotate270.setText("Rotate -90");
        jmiRotate270.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmiRotate270ActionPerformed(evt);
            }
        });

        jmBoard.add(jmiRotate270);

        jMenuBar1.add(jmBoard);

        jmInfo.setMnemonic('i');
        jmInfo.setText("Information");
        jmInfo.setToolTipText("Information about your game");
        jmiShowStats.setMnemonic('r');
        jmiShowStats.setText("Game Results");
        jmiShowStats.setToolTipText("Show the game results.");
        jmiShowStats.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmiShowStatsActionPerformed(evt);
            }
        });

        jmInfo.add(jmiShowStats);

        jMenuBar1.add(jmInfo);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(jtfWordEntry, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbAdd))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jspBoard, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jspWords, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jspBoard, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jbAdd)
                            .add(jtfWordEntry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jspWords, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 230, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jmiRotate270ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jmiRotate270ActionPerformed
    {//GEN-HEADEREND:event_jmiRotate270ActionPerformed

        if(board.getModel().getDice() != null)
        {
            DiceService.rotateDice(board.getModel().getDice(), DiceService.ROTATE_270_DEG);
            board.repaint();
        }//GEN-LAST:event_jmiRotate270ActionPerformed
    }                                            

    private void jmiRotate180ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jmiRotate180ActionPerformed
    {//GEN-HEADEREND:event_jmiRotate180ActionPerformed
        if(board.getModel().getDice() != null)
        {
            DiceService.rotateDice(board.getModel().getDice(), DiceService.ROTATE_180_DEG);
            board.repaint();
        }
    }//GEN-LAST:event_jmiRotate180ActionPerformed

    private void jmiRotate90ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jmiRotate90ActionPerformed
    {//GEN-HEADEREND:event_jmiRotate90ActionPerformed
        if(board.getModel().getDice() != null)
        {
            DiceService.rotateDice(board.getModel().getDice(), DiceService.ROTATE_090_DEG);
            board.repaint();
        }
    }//GEN-LAST:event_jmiRotate90ActionPerformed

    private void jmiNewGameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jmiNewGameActionPerformed
    {//GEN-HEADEREND:event_jmiNewGameActionPerformed
        newGame();
    }//GEN-LAST:event_jmiNewGameActionPerformed

    private void jmiShowStatsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jmiShowStatsActionPerformed
    {//GEN-HEADEREND:event_jmiShowStatsActionPerformed
        dialog.setVisible(true);
    }//GEN-LAST:event_jmiShowStatsActionPerformed

    private void jmiQuitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jmiQuitActionPerformed
    {//GEN-HEADEREND:event_jmiQuitActionPerformed
        dispose();
    }//GEN-LAST:event_jmiQuitActionPerformed


    public BoggleBoard getBoard()
    {
        return board;
    }

    public void setBoard(BoggleBoard board)
    {
        this.board = board;
    }

    public void wordGuessed(String guess)
    {
    }

    public void errorRecieved(String error)
    {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbAdd;
    private javax.swing.JLabel jl1;
    private javax.swing.JLabel jlOpponent;
    private javax.swing.JLabel jlTime;
    private javax.swing.JList jlWords;
    private javax.swing.JMenu jmBoard;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmInfo;
    private javax.swing.JMenuItem jmiNewGame;
    private javax.swing.JMenuItem jmiQuit;
    private javax.swing.JMenuItem jmiRotate180;
    private javax.swing.JMenuItem jmiRotate270;
    private javax.swing.JMenuItem jmiRotate90;
    private javax.swing.JMenuItem jmiShowStats;
    private javax.swing.JScrollPane jspBoard;
    private javax.swing.JScrollPane jspWords;
    private javax.swing.JTextField jtfWordEntry;
    // End of variables declaration//GEN-END:variables
}
