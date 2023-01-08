import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{
    private InetAddress ipaddress;
    private DatagramSocket socket;
    private setup main;

    public GameClient(setup game, String ipaddress) {
        try {
            this.main = game;
            this.socket = new DatagramSocket();
            this.ipaddress = InetAddress.getByName(ipaddress);

        }catch (SocketException | UnknownHostException e){
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
            System.out.println("SERVER > " + new String(packet.getData()));

        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, 1331);
        try {
            socket.send(packet);
        } catch (IOException e){
            e.printStackTrace();
        }
    }








}
