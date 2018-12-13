import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerWriteThread extends Thread
{
    private volatile Socket                serverClientSocket;
    private volatile ObjectOutputStream    serverObjectOut;
    private volatile Queue< ServerPacket > serverPacketQueue;

    public ServerWriteThread(Socket socket)
    {
        super();
        serverClientSocket = socket;
        serverPacketQueue  =  new ConcurrentLinkedQueue<>();

        try {
            serverObjectOut = new ObjectOutputStream(serverClientSocket.getOutputStream());
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while ( serverClientSocket.isConnected() )
        {
            try {
                if( !serverPacketQueue.isEmpty() )
                    serverObjectOut.writeObject( serverPacketQueue.remove() );
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
