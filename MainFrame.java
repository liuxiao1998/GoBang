import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;


public class MainFrame extends JFrame
{
    public MainFrame() 
    {
        //define the width and height of chess board
       // setSize(width*50,height*50);
       var drawBoard=new DrawBoard();
       drawBoard.setBackground(Color.RED);
        add(drawBoard);
        pack();
    }



}