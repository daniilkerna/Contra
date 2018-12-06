import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ClientWriteThread extends Thread
{
    private volatile Socket                clientSocket;
    private volatile ObjectOutputStream    clientObjectOut;
    private volatile Queue< ClientPacket > clientPacketQueue;

    public ClientWriteThread( Socket socket )
    {
        super();
        clientSocket      = socket;
        clientPacketQueue = new ConcurrentLinkedQueue<>();

        try
        {
            clientObjectOut = new ObjectOutputStream( clientSocket.getOutputStream() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        System.out.println( "Write Thread: " + Thread.currentThread().getName() );
        while( clientSocket.isConnected() )
        {
            try
            {
                if( !clientPacketQueue.isEmpty() )
                    clientObjectOut.writeObject( clientPacketQueue.remove() );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public Queue<ClientPacket> getClientPacketQueue() {
        return clientPacketQueue;
    }
}