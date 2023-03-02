package server;

import java.io.*;
import java.util.*;

// Server class
public class Server implements Runnable {
    Thread serverThread;
    public Server(){
        serverThread = new Thread(this);
        serverThread.start();
    }
    static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public void run(){
        System.out.println("Server Thread Loaded");
        // create client handler
        System.out.println();
        try {
            new ClientManager(5056);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long lastTime = System.nanoTime();
        double amountofticks = 60.0;
        double ns = 1000000000 / amountofticks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                step();
                delta--;
            }
        }
    }

    static public void step() {
        for (ClientHandler c : clients) {
            c.step();
        }

    }
}
