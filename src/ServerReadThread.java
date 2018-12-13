import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerReadThread extends Thread
{
    private volatile Socket                serverClientSocket;
    private volatile ObjectInputStream     serverObjectIn;
    private volatile Queue< ClientPacket > clientPacketQueue;

    public ServerReadThread(Socket socket)
    {
        super();
        this.serverClientSocket = socket;
        this.clientPacketQueue  = new ConcurrentLinkedQueue<>();

        try {
            serverObjectIn = new ObjectInputStream(serverClientSocket.getInputStream());
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while( serverClientSocket.isConnected() )
        {
            try {
                clientPacketQueue.add( (ClientPacket)serverObjectIn.readObject() );
            }
            catch (Exception e)
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
