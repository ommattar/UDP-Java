import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Serveur  {
    public static Map<String, List<Object>> clientMap = new HashMap<>();
    static byte[] receiveData;

    public static void addClient(String pseudo, int portNumber, InetAddress ipAddress) {
        List<Object> ipPortList = new ArrayList<>();
        ipPortList.add(ipAddress);
        ipPortList.add(portNumber);
        clientMap.put(pseudo, ipPortList);
    }

    

    public static boolean containsPort(int port) {
       boolean t=false;
       for (Map.Entry<String, List<Object>> entry: clientMap.entrySet()) {
        int port2 = Integer.parseInt(entry.getValue().get(1).toString());
        if (port==port2){
            t=true;
            break;
        }
       }
       return t;
    }

    public static String PortToPseudo(int port) {
        String ps ="";
        for (Map.Entry<String, List<Object>> entry: clientMap.entrySet()) {
         int port2 = Integer.parseInt(entry.getValue().get(1).toString());
         if (port==port2){
             ps = entry.getKey();
             return ps;
         }
         
        }
        return ps;
    
     }


    public static void main(String[] args) {
        try {

          
            DatagramSocket serverSocket = new DatagramSocket(5070);
        
            while (true){
                  
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (containsPort(receivePacket.getPort())==false){
                    addClient(clientMessage, receivePacket.getPort(), receivePacket.getAddress());
                    System.out.println("Client "+(receivePacket.getAddress().toString()).substring(1)+"@"+receivePacket.getPort()+" a été ajouté");
                }
                
                else {
                
            
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String clientPseudo = PortToPseudo(receivePacket.getPort());

                Traitement t= new Traitement(clientAddress, clientPort, clientPseudo, clientMessage, clientMap);
                Thread tr= new Thread(t);
                tr.start();
            }

        }


            /*String responseMessage = "Hello, Client!";
            byte[] sendData = responseMessage.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);

            serverSocket.send(sendPacket);*/
            
         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
