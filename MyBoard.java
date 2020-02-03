import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;


public class MyBoard
{
    private short[][] grid; 
    private  short black=1;
    private  short white=-1;
    private  short empty=0;

    public boolean setBlack(int x,int y){
        if(-1<x && x<grid.length && -1<y && y<grid[0].length){
            if(grid[x][y]==empty){
                grid[x][y]=black;
                return true;
            }
            else{
                return false;
                //do somthing
            }
        }
        else{
            return false;
            //exception throw
        }


    }

    public boolean setWhite(int x,int y){
        if(-1<x && x<grid.length && -1<y && y<grid[0].length){
            if(grid[x][y]==empty){
                grid[x][y]=white;
                return true;
            }
            else{
                return false;
                //do somthing
            }
        }
        else{
            return false;
            //exception throw
        }
    }
    private short checkLineFinished(ArrayList<Short> line)
    {
        //System.out.println(line);
        int countBlack=0;
        int countWhite=0;
        for(int i=0;i<line.size();i++)
        {
            if(line.get(i)==black){
                countWhite=0;
                countBlack+=1;
            }

            else if(line.get(i)==white){
                countBlack=0;
                countWhite+=1;
            }
            else if(line.get(i)==empty){
                countBlack=0;
                countWhite=0;
            }
            if(countBlack==5){
                return 1;
            }
            else if(countWhite==5){
                return -1;
            }
        }
        return 0;
    }

    public short checkMap(){
        var aLine=new ArrayList<Short>();
        short res=0;

        //横向检查(___)
        for(int i=0;i<grid.length;i++){
            aLine.clear();
            for(int j=0;j<grid[i].length;j++){
                aLine.add((Short)grid[i][j]);
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        //竖直方向检查(|||)
         for(int i=0;i<grid[0].length;i++){
            aLine.clear();
            for(int j=0;j<grid.length;j++){
                aLine.add((Short)grid[j][i]);
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        //右斜检查(\\\)
        for(int i=0;i<grid.length-4;i++){
            aLine.clear();
            int x=i;
            int y=0;
            while(x<grid.length){
                aLine.add((Short)grid[x][y]);
                x+=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        for(int j=1;j<grid[0].length-4;j++){

            aLine.clear();
            int x=0;
            int y=j;
            while(y<grid[0].length){
                aLine.add((Short)grid[x][y]);
                x+=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }
        //左斜检查  (///)
        for(int i=4;i<grid.length;i++){
            aLine.clear();
            int x=i;
            int y=0;
            while(x>=0){
                aLine.add((Short)grid[x][y]);
                x-=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        for(int i=1;i<grid[0].length-4;i++){
            aLine.clear();
            int x=grid.length-1;
            int y=i;
            while(y<grid[0].length){
                aLine.add((Short)grid[x][y]);
                x-=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }
        return 0;
    }

    public void paintMap(){
        for(int m=0;m<=grid.length;m++){
                System.out.printf("%02d  ",m);
            }
        System.out.println();
        for(int i=0;i<grid[0].length;i++){
            System.out.printf("%02d  ",i+1);

            for(int j=0;j<grid.length;j++){
                switch(grid[i][j]){
                    case 1:
                    System.out.print("B");
                    break;
                    case -1:
                    System.out.print("W");
                    break;
                    case 0:
                     System.out.print("*");
                     default:


                }
                System.out.print("   ");
            }
            System.out.println();
            System.out.println();
        }
    }
    public MyBoard(int width,int height) 
    {
        //define the width and height of chess board
        grid=new short[width][height];
       // setSize(width*50,height*50);

    }
}

