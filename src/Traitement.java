import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.List;

public class Traitement implements Runnable {
     InetAddress IP;
     InetAddress RecIP;
     int Port;
     int RecPort;
     String Pseudo;
     String RecPseudo;
     String message;
     private Map<String, List<Object>> clientMap;


    public Traitement(InetAddress IP, int Port,String Pseudo, String message, Map<String, List<Object>> clientMap){
        this.IP=IP;
        this.Port=Port;
        this.Pseudo=Pseudo;
        this.message =message;
        this.clientMap=clientMap;
    }

    public String PseudoReceiver(String m){
        int pindex = m.indexOf(':');
        return (m.substring(0,pindex)).trim();
    }

    public boolean containsPseudo(String pseudo) {
        return clientMap.containsKey(pseudo);
    }

    public String returnUsers(){
        StringBuilder tableBuilder = new StringBuilder();


        String header = String.format("%-20s %-15s %s%n", "Pseudo", "Addresse IP", "Port");
        String sep = String.format("---------------------------------------------------%n");
        tableBuilder.append(header);
        tableBuilder.append(sep);

        
        for (Map.Entry<String, List<Object>> entry: clientMap.entrySet()) {
            String port2 = entry.getValue().get(1).toString();
            String ip2 = (entry.getValue().get(0).toString()).substring(1);
            String pseudo2 = entry.getKey();

            String row = String.format("%-20s %-15s %s%n",pseudo2,ip2,port2);
            tableBuilder.append(row);
           
           }

           String M = tableBuilder.toString();
           return M;
    }

    public String getIP (String p){
        List<Object> data = clientMap.get(p);
        return (data.get(0).toString()).substring(1);
    }

    public int getPort (String p){
        List<Object> data = clientMap.get(p);
        return Integer.parseInt(data.get(1).toString());
    }

    public String MessageReceiver(String m){
        int pindex = m.indexOf(':');
        return (m.substring(pindex+1)).trim();
    }
    
    public void run(){
        try{
        DatagramSocket serverSocket = new DatagramSocket();
        if (message.indexOf(":")==-1){
            if ((message.trim().toLowerCase()).equals("utilisateur")){
                String m =(returnUsers());
                byte[] sendData = m.getBytes();
       
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, Port);
                serverSocket.send(sendPacket);
                System.out.println("Liste des utilisateurs a été envoyé a "+(IP).toString().substring(1)+"@" +Port);

            }

            else{
                String m =("Serveur: Erreur, Réessayer!");
       byte[] sendData = m.getBytes();
       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, Port);
       serverSocket.send(sendPacket);
            }
        }

        else{
       RecPseudo = PseudoReceiver(message);
       

       if (containsPseudo(RecPseudo)){
       RecIP = InetAddress.getByName(getIP(Pseudo));
       RecPort = getPort(RecPseudo);

       String m =(Pseudo+" : "+MessageReceiver(message));
       byte[] sendData = m.getBytes();
       
       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, RecIP, RecPort);
       serverSocket.send(sendPacket);
       System.out.println(IP.toString().substring(1)+"@" +Port+" a envoyé un message a "+RecIP.toString().substring(1)+"@" +RecPort);
       }

       else{
        String m =("Serveur: Erreur, utilisateur non trouvé!");
       byte[] sendData = m.getBytes();
       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, Port);
       serverSocket.send(sendPacket);
       
       }
    }
       serverSocket.close();
    
    } catch (Exception e) {
        e.printStackTrace();
    }



    }
}

