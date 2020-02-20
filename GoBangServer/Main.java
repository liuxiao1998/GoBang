import java.net.*;   
public class Main{
    public static final int port=5000;
    
    public static void main(String[] args){

        GoBangServer server=new GoBangServer(port);
        server.start();
        server.stop();
    }

}
