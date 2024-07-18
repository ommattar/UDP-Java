import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;



public class Client  {
    public static void main(String[] args){
        String message;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer Pseudo: ");
        String Pseudo = scanner.nextLine();
        
    
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            Receiver rec = new Receiver(clientSocket);
            Thread recThread = new Thread(rec);
            recThread.start();


            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 5070;

          
            byte[] sendData = Pseudo.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            clientSocket.send(sendPacket);

            Thread.sleep(1000);

          

            while (true){
                System.out.print("");
                message = scanner.nextLine();
                sendData = message.getBytes();

                sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);
            
            
         }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
