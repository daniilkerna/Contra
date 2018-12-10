
import java.io.*;
import java.net.*;

public class Client extends Thread
{
    public volatile Socket                     clientSocket;
    public ClientReadThread                    clientReadThread;
    public ClientWriteThread                   clientWriteThread;

    private String                             clientIP;
    private int                                clientPort;

    public Client( String ip, int port )
    {
        super();
        clientIP   = ip;
        clientPort = port;
    }

    public void writeToServer( ClientPacket cp )
    {
        if( this.clientWriteThread == null )
            return;

        this.clientWriteThread.getClientPacketQueue().add( cp );
    }

    public ServerPacket readFromServer()
    {
        if( this.clientReadThread == null )
            return null;

        if( clientReadThread.getServerPacketQueue().isEmpty() )
            return null;

        return clientReadThread.getServerPacketQueue().remove();
    }

    public void run()
    {
        try {
            System.out.println("Connecting to " + clientIP + " on port " + clientPort);
            clientSocket      = new Socket(clientIP, clientPort);

            clientWriteThread = new ClientWriteThread( clientSocket );
            clientReadThread  = new ClientReadThread( clientSocket );

            clientWriteThread.start();
            clientReadThread.start();
        }
        catch ( Exception e ) {
            System.out.println( e );
        }
        while( clientSocket.isConnected() );
    }
}
