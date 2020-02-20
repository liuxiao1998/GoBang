import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.net.*;

public class BoardComponent extends JComponent
{
    private short[][] grid; 
    private ArrayList<Ellipse2D> locationCollection;
    public int WHOWIN=0;
    private boolean order;
    private short BLACK=1;
    private short WHITE=-1;
    private short EMPTY=0;
    private int DEFAULT_WIDTH=720;
    private int DEFAULT_HEIGHT=720;
    private int CHESS_Radius=15;
    public  MouseHandler mouseEvent;



    private String serverIP;
    private int serverPort;
    private Socket newLink;
    private NetworkToolBox client;
    public BoardComponent(){

        mouseEvent=new MouseHandler();
        serverIP=new String("127.0.0.1");
        serverPort=5000;
        grid=new short[15][15];
        locationCollection=new ArrayList<Ellipse2D>();
        addMouseListener(mouseEvent);
        client=new NetworkToolBox(serverIP,serverPort);
        order=client.getOrder();
        if(order==false)
        {
            add(requestLoc(),false);

        }
    }
    private void sendLoc(int[] location)
    {
        String loc=String.format("%d+%d",location[0],location[1]);
        client.sendMessage(loc);

    }
    private int [] requestLoc()
    {
        System.out.println("mouse blocked");
        //this.removeMouseListener(mouseEvent);
        String msg=client.receiveMessage();
        if(msg!=null)
        {
            int[] loc=new int[2];
            String[] msgSplit=msg.split("\\+");
            loc[0]=Integer.parseInt(msgSplit[0]);
            loc[1]=Integer.parseInt(msgSplit[1]);
            System.out.println("mouse activated");
            //mouseEvent=new MouseHandler();
            //this.addMouseListener(mouseEvent);
            return loc;
        }
        else
        {
            System.out.println("mouse activated");
            //this.addMouseListener(mouseEvent);
            return null;
        }
    }








    private void add(int[] location,boolean mouseAdded)
    {
        int m=location[0];
        int n=location[1];
        if(grid[m][n]!=EMPTY){
            return;
        }

        if(locationCollection.size()%2==0)
        {
            grid[m][n]=BLACK;
        }
        else{
            grid[m][n]=WHITE;
        }

        int x=75+m*40;
        int y=75+n*40;


        locationCollection.add(new Ellipse2D.Double(x-CHESS_Radius,y-CHESS_Radius,2*CHESS_Radius,2*CHESS_Radius));
        
        this.paintImmediately(this.getBounds());
        this.checkWhoWin();
        
        if(mouseAdded==true)
        {
            System.out.println("绘制完成，发送");
            sendLoc(location);
            
        }
        else{
            System.out.println("完成接受绘画");
        }

    }

    public void restart(){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                grid[i][j]=EMPTY;
            }
        }
        locationCollection.clear();
        WHOWIN=0;
        this.repaint();
    }

    public boolean regret(){
        if(this.WHOWIN==0){
            this.remove();
            return false;
        }
        return true;
    }
    private void remove(){
        if(!locationCollection.isEmpty()){
            int last=locationCollection.size()-1;
            Ellipse2D lastPoint=locationCollection.get(last);
            int m=(int)Math.round((lastPoint.getX()-75)/40);
            int n=(int)Math.round((lastPoint.getY()-75)/40);
            grid[m][n]=EMPTY;
            locationCollection.remove(last);
            this.repaint();
        }
    }
    private boolean isOurTurn()
    {
        if(locationCollection.size()%2==0)
        {
            return order;
        }
        else
        {
            return !order;

        }
    }
    private void checkWhoWin()
    {
            switch(CheckTools.checkMap(grid)){
            case 1:

            WHOWIN=BLACK;
            if(order==true)
            {
                JOptionPane.showMessageDialog(null,"YOU WIN! The other player will call you dear husband.");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"YOU LOSE! Call the winner dear husband!");
            }

            break;
            case -1:

            WHOWIN=WHITE;
            if(order==false)
            {
                JOptionPane.showMessageDialog(null,"YOU WIN! The other player will call you dear husband.");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"YOU LOSE! Call the winner dear husband!");
            }
            break;
            default:

        }
    }



    private int[] pointToLocation(Point2D p)
    {
        int[] location=new int[2];
        double x=p.getX();
        double y=p.getY();
        if(x<60 || x>650 || y<60 || y>650){
            return null;
        }
        int m=(int)Math.round((x-75)/40.0);
        int n=(int)Math.round((y-75)/40.0);
        location[0]=m;
        location[1]=n;
        return location;

    }



    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed(MouseEvent event)
        {
            System.out.println("mouse pressed!");
            if(WHOWIN==0 && isOurTurn()==true)
            { 
                System.out.println("is our turn");
                add(pointToLocation(event.getPoint()),true);
                add(requestLoc(),false);
            }
        }
    }




    public void paintComponent(Graphics g){
        var g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        Image backGroundImage=new ImageIcon("BoardBackground.jpg").getImage();
        g2.drawImage(backGroundImage,0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT,null);

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
                g2.setPaint(new Color(255,251,240));
            }
            g2.draw(locationCollection.get(i));
            g2.fill(locationCollection.get(i));
        }

        int num= locationCollection.size()-1;
        if(num>-1){
        Ellipse2D currentPoint=locationCollection.get(num);
        double x=currentPoint.getX();
        double y=currentPoint.getY();
        x=x+CHESS_Radius;
        y=y+CHESS_Radius;
        var startL=new Point2D.Double(x-CHESS_Radius/2.0,y);
        var startR=new Point2D.Double(x+CHESS_Radius/2.0,y);
        var startU=new Point2D.Double(x,y+CHESS_Radius/2.0);
        var startD=new Point2D.Double(x,y-CHESS_Radius/2.0);
        var lineV=new Line2D.Double(startL,startR);
        var lineH=new Line2D.Double(startU,startD);
        if(num%2==0){
            g2.setPaint(Color.WHITE);
        }
        else{
            g2.setPaint(Color.BLACK);
        }
        g2.draw(lineH);
        g2.draw(lineV);
        }


    }

    public Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }


}