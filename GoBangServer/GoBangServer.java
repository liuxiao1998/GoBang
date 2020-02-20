import java.io.*;
import java.net.*;

public class GoBangServer{
    private int port;
    private ServerSocket serverSocket;
    private Socket[] socketPool=new Socket[2];
    public GoBangServer(int portGiven)
    {
        port=portGiven;
        socketPool[0]=null;
        socketPool[1]=null;
    }
    public void start()
    {
        System.out.println("wait accept");
        try
        {
        serverSocket=new ServerSocket(port);
         while(true){
            System.out.println("wait accept");
            Socket clientLink=serverSocket.accept();
            if(socketPool[0]==null)
            {
                socketPool[0]=clientLink;
                continue;
            }
            else if(socketPool[1]==null)
            {
                socketPool[1]=clientLink;
                MyTask myTask=new MyTask(socketPool);
                new Thread(myTask).start();
                socketPool=new Socket[2];
            }


        }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }

    public void stop()
    {
        try{
        serverSocket.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

    }




}