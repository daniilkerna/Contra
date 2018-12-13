import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ClientReadThread extends Thread
{
    private volatile Socket                    clientSocket;       // Copy of the original connection
    private volatile ObjectInputStream         clientObjectIn;     //
    private volatile Queue< ServerPacket >     serverPacketQueue;  // Queue of server packets sent to the client

    public ClientReadThread(Socket socket)
    {
        super();
        clientSocket       = socket;
        serverPacketQueue  = new ConcurrentLinkedQueue<>();

        try
        {
            clientObjectIn = new ObjectInputStream( clientSocket.getInputStream() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        System.out.println( "Read Thread: " + Thread.currentThread().getName() );

        while( clientSocket.isConnected() )
        {
            try {
                serverPacketQueue.add((ServerPacket) clientObjectIn.readObject());
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public Queue<ServerPacket> getServerPacketQueue() {
        return serverPacketQueue;
    }
}
