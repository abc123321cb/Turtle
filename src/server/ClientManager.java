package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientManager {

        public ClientManager(int port) throws IOException {
            ServerSocket ss = new ServerSocket(port);

            while (true) {
                Socket s = null;
                try {
                    // socket object to receive incoming client requests
                    s = ss.accept();

                    System.out.println("A new client is connected : " + s);

                    // obtaining input and out streams
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                    System.out.println("Assigning new thread for this client");

                    // create a new thread object
                    Server.clients.add(new ClientHandler(s, dis, dos));

                } catch (Exception e) {
                    s.close();
                    e.printStackTrace();
                    ss.close();// this is only so visual studio dosent complain
                }
            }
        }
    }
