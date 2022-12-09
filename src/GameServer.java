import java.io.IOException;
import java.net.*;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private Main main;

    public GameServer(setup game) {
        try {
            this.main = game;
            this.socket = new DatagramSocket(1331);
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            if (message.equalsIgnoreCase("ping")) {
                System.out.println("CLIENT > " + message);
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipaddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, 1331);
        try {
            socket.send(packet);
        } catch (IOException e){
            e.printStackTrace();
        }
    }








}