import java.net.*;
import java.io.*;
import java.util.Scanner;
public class NetworkToolBox{
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;
    private boolean order;

    public boolean getOrder()
    {
        return order;
    }
    
    public NetworkToolBox(String ip, int port){
        try
        {
            String msg=new String();
            clientSocket=new Socket(ip,port);
            in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out=new PrintWriter(clientSocket.getOutputStream(),true);
            if((msg=in.readLine())!=null && msg.equals("ID:0"))
            {
                order=true;
                
            }
            else
            {
                order=false;
            }
        }
        catch(IOException e)
        {
            System.out.println("unable to connect to the server!");
            
        }

    }

    public boolean sendMessage(String message) {
        try
        {
            out.println(message);
            //return in.readLine();
            String smsg=new String();
            if((smsg=in.readLine())!=null && smsg.equals("ACK"))
                {
                return true;
                }
                System.out.println("unable to send message to the server!");
                return false;
                
        }
        catch(IOException e)
        {
            System.out.println("unable to send message to the server!");
            return false;
           

        }

    }

    public String receiveMessage(){
        String rmsg=new String();
        try
        {
            if((rmsg=in.readLine())!=null)
            {
                return rmsg;
            }
            else
            {
                System.out.println("failed to receive message from server!");
                return null;
                

            }
        }
        catch(IOException e)
        {
            System.out.println("failed to receive message from server!");
            return null;
                
        }

    }

    public void closeClient(){
        //clientSocket.shutdownOutput();
        try
        {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }

    }
