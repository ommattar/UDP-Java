import java.net.*;


public class UDPPortScanner implements Runnable {
    
    public static void scanUDPPorts(int startPort, int endPort) {
        for (int port = startPort; port <= endPort; port++) {
            try {
                DatagramSocket socket = new DatagramSocket(port);
                socket.close();
                System.out.println("Port " + port + " : Ouvert");
            } catch (SocketException e) {
                System.out.println("Port " + port + " : Fermé");
            }
        }
    }
    
    public  void run() {
        
        scanUDPPorts(1000, 2000);
    }
}
