import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
public class GoBang
{
    public static void main(String[] args){

        EventQueue.invokeLater(()->
        {
        var myFrame=new MainFrame();
        myFrame.setTitle("五子棋好老公好老婆版");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        });

    }

}