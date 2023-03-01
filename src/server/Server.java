package server;

import java.io.*;
import java.util.*;

// Server class
public class Server {
    static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public static void main(String[] args) throws IOException {
        // create client handler
        new ClientManager(5056);
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
