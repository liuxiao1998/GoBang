import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
public class GoBangClient
{
    public static void main(String[] args){

        EventQueue.invokeLater(()->
        {
        var myFrame=new MainFrame();
        myFrame.setTitle("CALL ME HUSBAND V3.0");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        });

    }

}