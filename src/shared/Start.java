package shared;


import javax.swing.*;
import java.awt.*;
//import server.Server;
//import client.Client;
import client.Game;


public class Start {

    public static Window Game_Window;

    //static final Server server = new Server();
    static Game game;
    //static final Client client = new Client();

    public static void main(String[] args){
        //get options and such
        Options.refreshOptions();
        new Start();
    }
    
    public Start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });
    }
    public class Window extends JFrame {
        Window(){
            //setup frame
            
            setTitle("turtle game");
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //a bunch of bs to set up the game
            JPanel contentPane = new JPanel();
            contentPane.setLayout(null);

            //set up width and height of the game
            game = new Game(this);
            this.setPreferredSize(new Dimension(Options.GAME_WIDTH, Options.GAME_HEIGHT));
            game.setBounds(0,0,Options.GAME_WIDTH,Options.GAME_HEIGHT);
            contentPane.add(game);
            
            //stuff...?
            pack();
            setVisible(true);
            setLocationRelativeTo(null);
            setContentPane(game);
            // starts the music
            Music.play(0);
            
        }
    }
}