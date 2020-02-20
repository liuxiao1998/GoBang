import java.net.*;
import java.io.*;

public class MyTask implements Runnable{
        private Socket[] clientSocketPool;
        private PrintWriter outA;
        private PrintWriter outB;
        private BufferedReader inA;
        private BufferedReader inB;
        private boolean order;
        public MyTask(Socket[] socketPool)
        {
            order=true; //black acts first
            System.out.println("game start1!");
            clientSocketPool=socketPool;
            try
            {
            
            outA=new PrintWriter(clientSocketPool[0].getOutputStream(),true);
            inA=new BufferedReader(new InputStreamReader(clientSocketPool[0].getInputStream()));
            outB=new PrintWriter(clientSocketPool[1].getOutputStream(),true);
            inB=new BufferedReader(new InputStreamReader(clientSocketPool[1].getInputStream()));

            }

            catch(IOException e)
            {
                System.out.println("MyTask:25"+e);
            }

        }
        public void run() {
            String  inputData=new String();

            outA.println("ID:0");       //allocate id
            outB.println("ID:1");
            try
            {
                    System.out.println("game start!");

                    while(true)
                    {
                         if(order==true){
                        if((inputData=inA.readLine())!=null)
                        {
                            
                            System.out.println(inputData);
                            outB.println(inputData);
                            outA.println("ACK");
                        }
                        else
                        {
                            break;
                        }
                    }
                     else if(order==false)
                        {
                            if((inputData=inB.readLine())!=null)
                            {
                                System.out.println(inputData);
                                outA.println(inputData);
                                outB.println("ACK");
                            }
                        else
                        {
                            break;
                        }
                    }
                    order=!order;
                    if(order==true)
                    {
                        System.out.println("black's turn");
                    }
                    else
                    {
                        System.out.println("white's turn");
                    }

                    }
                    System.out.println("out of while");
                    System.out.println("A Game over!");
            }
            catch(Exception e){
                System.out.println(e);
            }
            finally{
                System.out.println("start-game-finally");
                try{
                outA.close();
                outB.close();
                inA.close();
                inB.close();
                clientSocketPool[0].close();
                clientSocketPool[1].close();
                clientSocketPool[0]=null;
                clientSocketPool[1]=null;
                }
                catch(IOException e){
                    System.out.println(e);
                }
            }
        }

    }