package Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by luvsandondov on 9/20/14.
 * This class implements the Controller.Player for the game
 */
public class Player extends JPanel{
    /**************************************************************************************************************
    * Member variables
    * **************************************************************************************************************/
    private String name;
    private int score = 0;
    private boolean color;

    /**************************************************************************************************************
     * Constructors
     * **************************************************************************************************************/
    public Player(boolean color) {
        // there is
        this.color = color;

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(color) g.drawString("Player 1", 75, 75);
        else g.drawString("Player 2", 75, 75);

        g.drawString("Name: " + getName(), 50, 100);
        g.drawString("Score: " + getScore(), 50, 125);

    }

    /**************************************************************************************************************
     * Public Functions
     * **************************************************************************************************************/
     public String getName() { return name;}
    public void setName(String newName) {name = newName; }
    public int getScore() {return score;}
    public void setScore( int newScore) {score = newScore;}
    public boolean getColor() {return color;}
    public void setColor(boolean color) {this.color = color;}


     /**************************************************************************************************************
     * Helper functions
     * **************************************************************************************************************/
}

