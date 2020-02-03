import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;


public class DrawBoard extends JComponent
{
    private short[][] grid; 
    private ArrayList<Ellipse2D> locationCollection;
    public static int WHOWIN=0;


    private  static final short BLACK=1;
    private  static final short WHITE=-1;
    private  static final short EMPTY=0;
    private static final int DEFAULT_WIDTH=750;
    private static final int DEFAULT_HEIGHT=750;
    private static final int CHESS_Radius=15;


    private short checkLineFinished(ArrayList<Short> line)
    {
        //System.out.println(line);
        int countBlack=0;
        int countWhite=0;
        for(int i=0;i<line.size();i++)
        {
            if(line.get(i)==BLACK){
                countWhite=0;
                countBlack+=1;
            }

            else if(line.get(i)==WHITE){
                countBlack=0;
                countWhite+=1;
            }
            else if(line.get(i)==EMPTY){
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

    private void add(Point2D p)
    {
        double x=p.getX();
        double y=p.getY();
        if(x<60 || x>650 || y<60 || y>650){
            return;
        }
        int m=(int)Math.round((x-75)/40.0);
        int n=(int)Math.round((y-75)/40.0);
        x=75+m*40;
        y=75+n*40;
        locationCollection.add(new Ellipse2D.Double(x-CHESS_Radius,y-CHESS_Radius,2*CHESS_Radius,2*CHESS_Radius));
        if(locationCollection.size()%2==0)
        {
            grid[m][n]=WHITE;
        }
        else{
            grid[m][n]=BLACK;
        }
        repaint();
        switch(checkMap()){
            case 1:
            System.out.println("BLAKC WIN!");
            WHOWIN=BLACK;
            break;
            case -1:
            System.out.println("WHITE WIN!");
            WHOWIN=WHITE;
            break;
            default:

        }
    }

    private Point2D find(Point2D p)
    {
        for(Ellipse2D r:locationCollection){
            if(r.contains(p))    return p;
        }
        return null;

    }

    private class MouseHandler extends MouseAdapter
    {
        public void mouseClicked(MouseEvent event)
        {
            Point2D current=find(event.getPoint());
            if(current==null && event.getClickCount() >=2 && WHOWIN==0)
            { 
                add(event.getPoint());
            }
        }
    }


    public DrawBoard(){
        grid=new short[15][15];
        locationCollection=new ArrayList<Ellipse2D>();
        addMouseListener(new MouseHandler());
    }

    public void paintComponent(Graphics g){
        var g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i=1;i<14;i++){
            var startUp=new Point2D.Double(75+i*40,75);
            var endDown=new Point2D.Double(75+i*40,635);
            var lineVertical=new Line2D.Double(startUp,endDown);

            var startLeft=new Point2D.Double(75,75+i*40);
            var endRight=new Point2D.Double(635,75+i*40);
            var lineHorizontal=new Line2D.Double(startLeft,endRight);
            g2.draw(lineVertical);
            g2.draw(lineHorizontal);
        }
           g2.draw(new Rectangle2D.Double(75,75,560,560));

        for(int i=0;i<locationCollection.size();i++){
            if(i%2==0){
                g2.setPaint(Color.BLACK);
            }
            else{
                g2.setPaint(Color.WHITE);
            }
            g2.draw(locationCollection.get(i));
            g2.fill(locationCollection.get(i));
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }


}