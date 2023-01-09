package client;
 // import statements
 import java.net.*;
 import java.io.*;
 import java.util.Scanner;

 public class Client
 {
     // initializing socket and input output streams
     private DataOutputStream dataOut  = null;
     private Scanner scan              = null;
     private Socket socket             = null;
     // constructor to create a socket with given IP and port address
     public Client(String address, int port)
     {
         // Establishing connection with server
         try
         {
              // creating an object of socket
             socket = new Socket(address, port);
             System.out.println("Connection Established!! ");
             System.out.println("input \"Finish\" to terminate the connection. ");
             // taking input from user
             scan = new Scanner(System.in);
             // opening output stream on the socket
             dataOut = new DataOutputStream(socket.getOutputStream());
         }
         catch(UnknownHostException uh)
         {
             System.out.println(uh);
         }
         catch(IOException io)
         {
             System.out.println(io);
         }
         // to store the input messages given by the user
         String str = "";
         // The reading continues until "Finish" is input
         while (!str.equals("Finish"))
         {
             str = scan.nextLine(); // reading input
             try
             {
                 dataOut.writeUTF(str); // writing to the underlying output stream
             }
             // For handling errors while writing to output stream
             catch(IOException io)
             {
                 System.out.println(io);
             }
         }
         System.out.println(" Connection Terminated!! ");
         // for closing the connection
         try
         {
             dataOut.close();
             socket.close();
         }
         catch(IOException io)
         {
             System.out.println(io);
         }
     }
 }