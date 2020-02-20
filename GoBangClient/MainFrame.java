import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;

public class MainFrame extends JFrame
{
    private JPanel buttonPanel;
    private BoardComponent drawBoard;
    private JButton buttonRegret;
    private JButton buttonRestart;
    public MainFrame() 
    {

        //this.getContentPane().setBackground(new Color(213,176,146));
        buttonPanel = new JPanel();
        drawBoard=new BoardComponent();
        buttonPanel.setLayout(new GridLayout(5,1));
        buttonRegret=new JButton("悔棋");
        buttonRestart=new JButton("重新开始");
        add(drawBoard,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.EAST);
        buttonRegret.addActionListener(new regretAction());
        buttonRestart.addActionListener(new restartAction());
        buttonPanel.add(buttonRegret);
        buttonPanel.add(buttonRestart);
        buttonRegret.setBackground(new Color(240,230,140));
        buttonRestart.setBackground(new Color(240,230,140));
        buttonPanel.setBackground(new Color(240,230,140));
        buttonPanel.setOpaque(true);
       pack();



    }


    private class regretAction implements ActionListener{
        public void actionPerformed(ActionEvent event){
            drawBoard.regret();
        }
    }

    private class restartAction implements ActionListener{
        public void actionPerformed(ActionEvent event){
            drawBoard.restart();
        }
    }
}