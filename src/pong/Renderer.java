package pong;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Renderer extends JPanel{
    
    public void paint(Graphics g){
        super.paintComponent(g);
        
        Pong.pong.render((Graphics2D) g);
    }
    
}