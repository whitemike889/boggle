/*
 * BoggleBoard.java
 *
 * Created on March 17, 2006, 11:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.erici.boggle.client.gui;

import com.erici.boggle.game.BoggleDice;
import com.erici.boggle.game.BoggleGameModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

//--------------------------------------------------------------------------
// CLASS BoggleBoard
//--------------------------------------------------------------------------
/**
 * This class is used to render a Boggle Game from a BoggleGameModel object.
 *
 * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
 */
public class BoggleBoard extends JComponent
{
    //==========================================================================
    //  VARIABLE(S)
    //==========================================================================
    private BoggleGameModel model     = null;
    
    //==========================================================================
    //  CONSTRUCTOR(S)
    //==========================================================================
    
    public BoggleBoard()
    {
        this(new BoggleGameModel());
    }

    //--------------------------------------------------------------------------
    // <>
    //--------------------------------------------------------------------------
    /**
     * This consructor sets the model object, and acts upon this when called
     * to paint.
     * @param model The Boggle Game Model.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public BoggleBoard(BoggleGameModel model)
    {
        setModel(model);
    }
    
    //==========================================================================
    //  METHOD(S)
    //==========================================================================
    
    //--------------------------------------------------------------------------
    // paint
    //--------------------------------------------------------------------------
    /**
     * This method draws the Boggle Board using the data in the model.
     * @param g The Graphics to draw on.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public void paint(Graphics g)
    {
        drawVerticalLines(g);
        drawHorizontalLines(g);
        g.setFont(new Font(null,Font.BOLD,(getWidth()+getHeight())/16));
        drawGame(g);
    }
    
    //--------------------------------------------------------------------------
    // drawVerticalLines
    //--------------------------------------------------------------------------
    /**
     * This method encapsulates the logic behind drawing the vertical lines of
     * the Boggle Board.
     * @param g The graphics to draw on.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void drawVerticalLines(Graphics g)
    {
        for(int i=0;i<getWidth();i+=getWidth()/4)
        {
            if(i!=3)
            {
                g.drawLine(i,0,i,getHeight());
            }
        }
    }
    
    //--------------------------------------------------------------------------
    // drawHorizontalLines
    //--------------------------------------------------------------------------
    /**
     * This method encapsulates the logic behind drawing the Horizontal Lines.
     * @param g The graphics to draw on.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void drawHorizontalLines(Graphics g)
    {
        for(int i=0;i<getHeight();i+=getHeight()/4)
        {
            if(i!=3)
            {
                g.drawLine(0,i,getWidth(),i);
            }
        }
    }
    
    //--------------------------------------------------------------------------
    // drawGame
    //--------------------------------------------------------------------------
    /**
     * This method is responsible for drawing the data in the Game Model on
     * the game board.
     * @param g The graphics to draw on.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    protected void drawGame(Graphics g)
    {
        int wStep = getWidth()/4;
        int hStep = getHeight()/4;
        
        if(model.getDice()!=null)
        {
            for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                {
                    String text = model.getDice().getTheRoll()[i][j];
                    Rectangle r = new Rectangle(i*wStep,j*hStep,wStep,hStep);
                    Point p = findDrawLocation(r,text,g);

                    g.drawString(text,(int)p.getX(),(int)p.getY());
                }
            }
        }
    }
    
    //--------------------------------------------------------------------------
    // findDrawLocation
    //--------------------------------------------------------------------------
    /**
     * This method determines where to draw the provided String to center it
     * between the Horizontal and Vertical lines.
     * @param bounds The bounds where the text can be drawn.
     * @param text The Text to be drawn.
     * @param g The Graphics that will be rendering the text (to determine the
     * font bounds).
     * @return The point where the text should be drawn.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public Point findDrawLocation(Rectangle bounds, String text, Graphics g)
    {
        Point p = new Point();
        
        int w = (int)(bounds.getWidth() - g.getFontMetrics().getStringBounds(text,g).getWidth())/2;
        int h = (int)(g.getFontMetrics().getStringBounds(text,g).getHeight()/3.0);
        
        p.setLocation(bounds.getX() + w,(int)bounds.getCenterY()+h);
        
        return p;
    }

    public BoggleGameModel getModel()
    {
        return model;
    }

    public void setModel(BoggleGameModel model)
    {
        this.model = model;
    }
    
    //--------------------------------------------------------------------------
    // main
    //--------------------------------------------------------------------------
    /**
     * Test Driver - load this panel in a resizable JFrame object.
     * @param args The command line arguments.
     *
     * @author <a href='mailto:intere@gmail.com'>Eric Internicola</a>
     */
    public static void main(String[] args)
    {
        final BoggleGameModel game = new BoggleGameModel();
        //game.getDice().rollDice();
        
        final JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        frame.setLayout(new BorderLayout());
        frame.add(new BoggleBoard(game),BorderLayout.CENTER);
        
        JButton button = new JButton("Redo");
        frame.add(button,BorderLayout.NORTH);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(game.getDice()==null)
                {
                    game.setDice(new BoggleDice());
                }
                game.getDice().rollDice();
                frame.repaint();
            }
        });
        
        frame.setSize(400,400);
        frame.setVisible(true);
    }
}
