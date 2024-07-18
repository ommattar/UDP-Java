import java.net.DatagramPacket;
import java.net.DatagramSocket;



public class Receiver implements Runnable{
    DatagramSocket clientSocket;
    DatagramPacket receivePacket;
    String serverResponse;
    byte[] receiveData = new byte[1024];

    public Receiver(DatagramSocket clientSocket){
        this.clientSocket= clientSocket;
    }

    public void run(){
        try{
        while(true){
            receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(serverResponse);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}