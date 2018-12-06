import java.net.*;
import java.net.Socket;

public class Server extends Thread
{
    private Socket                         serverClient;
    private ServerSocket                   serverSocket;
    private ServerReadThread               serverReadThread;
    private ServerWriteThread              serverWriteThread;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToClient( ServerPacket cp )
    {
        if( serverWriteThread == null )
            return;

        this.serverWriteThread.getServerPacketQueue().add( cp );
    }

    public ClientPacket readFromClient()
    {
        if( serverReadThread == null )
            return null;

        if( serverReadThread.getClientPacketQueue().isEmpty() )
            return null;

        return serverReadThread.getClientPacketQueue().remove();
    }

    public void run()
    {
        try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            serverClient = serverSocket.accept();

            serverReadThread = new ServerReadThread( serverClient );
            serverReadThread.start();

            serverWriteThread = new ServerWriteThread( serverClient );
            serverWriteThread.start();

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        while( serverClient.isConnected() );
    }
}
