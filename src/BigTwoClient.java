import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;

/**
 * The BigTwoClient class is used to build the Client side of the BigTwo game
 *
 * @author Lee Yat Hei
 *
 */
public class BigTwoClient implements  NetworkGame
{
    /**
     *  a BigTwo object for the Big Two card game.
     */
    private BigTwo game;
    /**
     * a BigTwoGUI object for the Big Two card game.
     */
    private BigTwoGUI gui;
    /**
     * a socket connection to the game server.
     */
    private Socket sock;
    /**
     * an ObjectOutputStream for sending messages to the server.
     */
    private ObjectOutputStream oos;
    /**
     * an ObjectInputStream for receiving messages tp the server.
     */
    private ObjectInputStream ois;
    /**
     * an integer specifying the playerID (i.e., index) of the local player.
     */
    private int playerID;
    /**
     * a string specifying the name of the local player.
     */
    private String playerName;
    /**
     *  a string specifying the IP address of the game server.
     */
    private String serverIP;
    /**
     *  an integer specifying the TCP port of the game server.
     */
    private int serverPort;
    /**
     * Constructor of the BigTwoClient class.Creating a Client Connection
     *
     * @param game a BigTwo object associated with this UI
     * @param gui the gui of the BigTwo Game
     */
    public BigTwoClient(BigTwo game, BigTwoGUI gui){

        this.game = game;
        this.gui  = gui;
        //Image Icon = new ImageIcon(getClass().getClassLoader().getResource("PlayerIcon/Logo.jpg")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        String playerNameInput = JOptionPane.showInputDialog(null,"Please enter your name: ","name",JOptionPane.INFORMATION_MESSAGE);

        setServerIP(JOptionPane.showInputDialog(null,"Please enter the IP address of the server:","Server Address",JOptionPane.INFORMATION_MESSAGE));
        this.setPlayerName(playerNameInput);
        connect();

        this.gui.repaint();
    }

    /**
     *  a method for making a socket connection with the game server.
     */
    @Override
    public void connect() {
        //setServerIP("127.0.0.1");
        setServerPort(2396);
        try {
            sock = new Socket(serverIP, serverPort);
            oos=new ObjectOutputStream(sock.getOutputStream());
            ois=new ObjectInputStream (sock.getInputStream());
            System.out.println("networking established");

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server Connection Error", "Server Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        Thread readerThread = new Thread(new ServerHandler());
        readerThread.start();
        //System.out.println(getPlayerName());
        sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1,this.getPlayerName()));
        sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));



    }

    /**
     * an inner class that implements the Runnable interface and Handle the receiving message from the server
     */

    class ServerHandler implements Runnable
    {
        /**
         * a method to Handle the receiving message from the server and create a new thread
         *
         */
        @Override
        public void run()
        {
            CardGameMessage cardGameMessage = null;
            try {

                while ((cardGameMessage = (CardGameMessage) ois.readObject()) != null) {
                    parseMessage(cardGameMessage);
                    System.out.println("receive message from : player" + cardGameMessage.getPlayerID());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            gui.repaint();
        }
    }

    /**
     * a method for parsing the messages
     * received from the game server.
     * @param message the message receive from the server
     */
    @Override
    public void parseMessage(GameMessage message)
    {
        switch (message.getType()) {
            case CardGameMessage.PLAYER_LIST:
            	if (message.getPlayerID()>=0 && message.getPlayerID()<=3) {
	                setPlayerID(message.getPlayerID());
	                gui.setActivePlayer(playerID);
	                
            	}
                for (int i = 0; i < 4; i++) {
                    if (((String[]) message.getData())[i] != null) {
                        game.getPlayerList().get(message.getPlayerID()).setName(((String[]) message.getData())[i]);
                        gui.setExistence(i);
                    }
                }
                break;
            case CardGameMessage.JOIN:
                game.getPlayerList().get(message.getPlayerID()).setName((String) message.getData());
                gui.setExistence(message.getPlayerID());
                gui.BoardcastJoinMessage(serverIP,serverPort);
                gui.repaint();
                break;
            case CardGameMessage.FULL:
                setPlayerID(-1);
                gui.BoardcastFullMessage(message.getPlayerID());
                gui.repaint();
                break;
            case CardGameMessage.QUIT:
                gui.BoardcastLoseMessage(message.getPlayerID());
                gui.cancelExistence(message.getPlayerID());
                game.getPlayerList().get(message.getPlayerID()).setName((""));
                if (game.endOfGame() == false) {
                    gui.disable();
                    sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
                    for (int i = 0; i < 4; i++) {
                        game.getPlayerList().get(i).removeAllCards();
                    }
                }
                gui.setIsStart(false);
                gui.repaint();
                break;
            case CardGameMessage.READY:
                gui.BoardcastReadyMessage(game.getPlayerList().get(message.getPlayerID()).getName());
                gui.setActivePlayer(playerID);
                gui.repaint();
                //handsontable
                //gui.disable();
                break;
            case CardGameMessage.START:

                gui.BoardcastStartMessage();
                gui.setIsStart(true);
                game.start((BigTwoDeck) message.getData());
                gui.setActivePlayer(playerID);
                //gui.enable();
                gui.promptActivePlayer();
                
                gui.repaint();
                break;
            case CardGameMessage.MOVE:
                game.checkMove(message.getPlayerID(), (int[]) message.getData());
                gui.repaint();
                if (message.getPlayerID()==playerID){
                    gui.enable();
                }else{
                    gui.disable();
                }

                gui.repaint();
                break;
            case CardGameMessage.MSG:
                gui.printMsg((String) message.getData());
                break;
            default:
                gui.BoardcastErrorMessage();
                break;
        }
        gui.repaint();

    }

    /**
     * – a method for sending the specified
     * message to the game server.
     * @param message the message are going to send
     */
    @Override
    public void sendMessage(GameMessage message) {
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  a method for getting the playerID (i.e., index) of the local player.
     * @return the playerID of the local player
     */
    @Override
    public int getPlayerID() {
        return playerID;
    }

    /**
     * a method for setting the playerID (i.e., index) of the local player.
     * @param playerID the id is going to set
     */
    @Override
    public void setPlayerID(int playerID) {
        this.playerID=playerID;
    }

    /**
     * a method for getting the name of the local player.
     * @return the name of the local player
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * a method for setting the name of the local player.
     * @param playerName the playerName is going to set
     */
    @Override
    public void setPlayerName(String playerName) {
        if (playerName.length()==0){
            this.playerName = "Player"+playerID;
            game.getPlayerList().get(playerID).setName("Player"+playerID);
        }else{
            this.playerName = playerName;
            game.getPlayerList().get(playerID).setName(playerName);
        }

    }

    /**
     * a method for getting the IP address of the game server.
     * @return the IP address of the game server.
     */
    @Override
    public String getServerIP() {
        return serverIP;
    }

    /**
     * a method for setting the IP address of the game server.
     * @param serverIP the server IP is going to set
     */
    @Override
    public void setServerIP(String serverIP) {
        this.serverIP=serverIP;
    }

    /**
     * a method for getting the TCP port of the game server.
     * @return the TCP port
     */
    @Override
    public int getServerPort() {
        return serverPort;
    }

    /**
     * a method for setting the TCP port of the game server.
     * @param serverPort the server port is going to set
     */
    @Override
    public void setServerPort(int serverPort) {
        this.serverPort=serverPort;
    }



}