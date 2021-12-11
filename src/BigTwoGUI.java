import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * The BigTwoGUI class is used to build the GUI for the Big Two card game.
 *
 * @author Lee Yat Hei
 *
 */
public class BigTwoGUI implements CardGameUI{
    /**
     * a Big Two card game associates with this GUI.
     */
    BigTwo game;
    /**
     * a Big Two card game client
     */
    BigTwoClient client;
    /**
     *  the list of players
     */
    private ArrayList<CardGamePlayer> playerList;
    /**
     * a boolean array indicating which cards are being selected..
     */
    boolean[] selected = new boolean[13];;
    /**
     *  an integer specifying the index of the active player
     */
    int activePlayer;
    /**
     * the main window of the application.
     */
    JFrame frame= new JFrame();
    /**
     * a panel for showing the cards of each player and the cards played on the table.
     */
    JPanel bigTwoPanel;
    /**
     *  a play button for the active player to play the selected cards.
     */
    JButton playButton;
    /**
     * a pass button for the active player to pass his/her turn to the
     * next player.
     */
    JButton passButton;
    /**
     * a text area for showing the current game status as well as end of game messages.
     */
    JTextArea msgArea= new JTextArea(5,30);
    /**
     * a text area for showing chat messages sent by the players.
     */
    JTextArea chatArea= new JTextArea(5,30);
    /**
     * a text field for players to input chat messages.
     */
    JTextField chatInput;
    /**
     * an array keeping all the card images
     */
    Image [][] cardImages;
    /**
     * the card back of the image
     */
    Image cardBackImage;
    /**
     * an array keeping all the player icon
     */
    Image [] PlayerIcon;
    /**
     * the path to find the image
     */
    String Path;
    /**
     * the array stored which player are joined the game
     */
    boolean[]existance=new boolean[4];
    /**
     * a boolean value to record the game status is started of not
     */
    boolean isStart=false;
    /**
     * Constructor of the BigTwoGUI class.Creating a BigTwoGUI
     *
     * @param game a BigTwo object associated with this UI
     */
    public BigTwoGUI(BigTwo game){
        this.game=game;
        client=new BigTwoClient(game,this);
        selected= new boolean[13];
        //this.client.setPlayerName(JOptionPane.showInputDialog("Please enter your name: "));
        setActivePlayer(game.getCurrentPlayerIdx());
        loadImages();
        GuiSetUp();
        //playerName=JOptionPane.showInputDialog("Please enter your name: ");



    }

    /**
     * A method to load all the image needed in this problem
     */
    private void loadImages() {
        PlayerIcon = new Image[4];
        for (int i=0;i<4;i++){
            Path = "PlayerIcon/player"+i+".png";
            //Image Icon = new ImageIcon(Path).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            Image Icon = new ImageIcon(getClass().getClassLoader().getResource(Path)).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            PlayerIcon[i] = new ImageIcon( Icon ).getImage();
        }
        //Image cardBack = new ImageIcon("cards/b.gif").getImage().getScaledInstance(69, 91, Image.SCALE_SMOOTH);
        Image cardBack = new ImageIcon(getClass().getClassLoader().getResource("cards/b.gif")).getImage().getScaledInstance(69, 91, Image.SCALE_SMOOTH);
        cardBackImage = new ImageIcon(cardBack).getImage();

        char[] suit = {'d','c','h','s'};

        char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};

        cardImages = new Image[4][13];
        for(int j=0;j<13;j++) {
            for(int i=0;i<4;i++) {
                Path="cards/" + rank[j] + suit[i] + ".gif";
                //Image cardFront = new ImageIcon(Path).getImage().getScaledInstance(69, 91, Image.SCALE_SMOOTH);
                Image cardFront = new ImageIcon(getClass().getClassLoader().getResource(Path)).getImage().getScaledInstance(69, 91, Image.SCALE_SMOOTH);
                cardImages[i][j] = new ImageIcon( cardFront ).getImage();
            }
        }
    }

    /**
     * A method to set the whole framework of the GUI
     */
    public void GuiSetUp(){
        playButton = new JButton();
        passButton = new JButton();
        chatInput = new JTextField();
        bigTwoPanel = new BigTwoPanel();
        if (game.getCurrentPlayerIdx() != activePlayer) {
        	enable();
        }else{disable();}

        frame = new JFrame();
        frame.setTitle("Big Two");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //menu
        MenuBar menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);

        //leftPanel
        JPanel leftPanel= new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        BigTwoPanel bigTwoPanel=new BigTwoPanel();
        bigTwoPanel.setPreferredSize(new Dimension(800,925));
        ButtonBar buttonBar=new ButtonBar();
        leftPanel.add(bigTwoPanel);
        leftPanel.add(buttonBar);
        leftPanel.setBackground(new Color(3, 149, 89));

        //rightPanel
        JPanel rightPanel= new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        ChatBox chatBox = new ChatBox();
        chatBox.setLayout(new BoxLayout(chatBox, BoxLayout.Y_AXIS));
        chatBox.setPreferredSize(new Dimension(445,900));
        MessageBar messageBar=new MessageBar();
        rightPanel.add(chatBox);
        rightPanel.add(messageBar);


        frame.add(leftPanel,BorderLayout.CENTER);
        frame.add(rightPanel,BorderLayout.EAST);

        frame.setSize(new Dimension(1920, 1080));
        frame.setMinimumSize(new Dimension(1480, 900));
        //frame.setResizable(false);
        frame.setVisible(true);
    }
    /**
     * Sets the index of the active player.
     *
     * @param activePlayer an int value representing the index of the active player
     */
    @Override
    public void setActivePlayer(int activePlayer) {
            this.activePlayer = activePlayer;
    }

    /**
     * Repainting the GUI.
     */
    @Override
    public void repaint() {
        Arrays.fill(selected, Boolean.FALSE);
        frame.repaint();
    }

    /**
     * method for printing the specified string to the message
     * area of the GUI.
     *
     * @param msg the string to be printed to the message area of the card game user
     */
    @Override
    public void printMsg(String msg) {
        chatArea.append( msg + "\n");
    }

    /**
     * a method for clearing the message area of the GUI.
     */
    @Override
    public void clearMsgArea() {
        msgArea.selectAll();
        msgArea.replaceSelection("");
    }

    /**
     * a method for resetting the GUI.
     */
    //  (i) reset the list of selected cards; (ii) clear the message area; and (iii) enable user interactions.
    @Override
    public void reset() {

        Arrays.fill(selected, Boolean.FALSE);

        clearMsgArea();

        enable();

    }

    /**
     * a method for enabling user interactions with the GUI
     */
    //You should (i) enable the �lay�� button and �ass�� button (i.e., making them clickable); (ii) enable the chat input; and (iii) enable the BigTwoPanel for selection of cards through mouse clicks.
    @Override
    public void enable() {
    	
        playButton.setEnabled(true);

        passButton.setEnabled(true);

        chatInput.setEnabled(true);

        bigTwoPanel.setEnabled(true);
    }

    /**
     * a method for disabling user interactions with the GUI.
     */
    //You should (i) disable the play button and pass button (i.e., making them not clickable); (ii) disable the chat input; and (ii) disable the BigTwoPanel for selection of cards through mouse clicks.
    @Override
    public void disable() {
        playButton.setEnabled(false);

        passButton.setEnabled(false);

        chatInput.setEnabled(false);

        bigTwoPanel.setEnabled(false);
    }
    /**
     * a method setting a player are existing in the game
     */
    public void setExistence(int playerID)
    {
        existance[playerID] = true;
    }
    /**
     * a method cancel the existence of a player
     */
    public void cancelExistence(int playerID){
        existance[playerID]=false;
    }

    /**
     * a method to update the isStart status
     * @param status the current status
     */
    public void setIsStart(boolean status) {
    	isStart=status;
    }

    /**
     * a method to get the index of the last player
     * @return the index of the last player
     */
    public int getLastPlayerIndex() {
    	if (game.getCurrentPlayerIdx()-1==-1) {
    		return 3;
    	}
    	return game.getCurrentPlayerIdx()-1;
    }
    /**
     * a method for prompting the active player to select cards and make his/her move.
     */
//A message should be displayed in the message area showing it is the active player� turn.
    @Override
    public void promptActivePlayer() {
        msgArea.append("Player "+game.getCurrentPlayerIdx()+"'s turn:" + "\n");
    }

    /**
     * BigTwoPanel is an inner class, used to build the bigTwoPanel(a panel for showing the cards of each player and the cards played on the table.)
     */
    private class BigTwoPanel extends JPanel implements MouseListener{
        int nameCoordinateX = 20;
        int nameCoordinateY = 20;
        int playerIconCoordinateX = 20;
        int playerIconCoordinateY= 30;
        int cardCoordinateX= 160;
        int selectedCardCoordinateY = 5;
        int cardCoordinateYBottom =25;
        int cardWidth =69;
        int halfCardWidth=15;
        int cardHeight =91;
        int lineYCoordinate=playerIconCoordinateY+100;
        int gap=140;

        /**
         * the constructor of the bigTwoPanel class
         */
        public BigTwoPanel(){
            addMouseListener(this);
            setBackground(new Color(3, 149, 89));

        }

        /**
         * A method to Draw the cards,playerIcon and the borderLine in the bigTwoPanel
         *
         * @param g Graphics
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            this.setOpaque(true);
            Graphics2D g2d =(Graphics2D) g;

            for (int i=0;i<4;i++){
                if (existance[i]) {
                    g.setColor(Color.BLACK);
                    //draw Player Icon and BorderLine
                    g.drawImage(PlayerIcon[i], playerIconCoordinateX, playerIconCoordinateY + i * gap, this);
                    g2d.drawLine(0, lineYCoordinate + i * gap, 1920, lineYCoordinate + i * gap);
                    if (i == activePlayer) {
                		g.setColor(Color.BLUE);
                	}
                    
                    if (isStart && i == game.getCurrentPlayerIdx()) {
                		g.setColor(Color.GREEN);
                	}
                    if (i == activePlayer) {
                        //draw Name

                        g.drawString("You", nameCoordinateX, nameCoordinateY + i * gap);
                        //draw cards

                        for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
                            int suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
                            int rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
                            if (selected[j]) {
                                g.drawImage(cardImages[suit][rank], cardCoordinateX + halfCardWidth * j, selectedCardCoordinateY + i * gap, this);
                            } else {
                                g.drawImage(cardImages[suit][rank], cardCoordinateX + halfCardWidth * j, cardCoordinateYBottom + i * gap, this);
                            }
                        }
                    } else {
                        g.drawString("Player "+i, nameCoordinateX, nameCoordinateY + i * gap);
                        for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
                            g.drawImage(cardBackImage, cardCoordinateX + halfCardWidth * j, cardCoordinateYBottom + i * gap, this);
                        }
                    }
                }


            }
            //Last Hand
            if (isStart ) {
                
                
	            if (!game.getHandsOnTable().isEmpty()){
	                int sizeOfHand =game.getHandsOnTable().size();
	                Hand handsOnTable=game.getHandsOnTable().get(sizeOfHand-1);
	                if (!handsOnTable.equals(null)) {
	                	if (getLastPlayerIndex() == activePlayer) {
	                		g.setColor(Color.BLUE);
	                		g.drawString("Played by YOU",nameCoordinateX,nameCoordinateY+4*gap);
	                	}else {
		    	            g.setColor(Color.BLACK);
		    	            g.drawString("Played by Player"+getLastPlayerIndex(),nameCoordinateX,nameCoordinateY+4*gap);
	    	            }
	                	g.drawImage(cardBackImage, playerIconCoordinateX, playerIconCoordinateY + 4 * gap, this);
	                }
	                
	                for (int i=0;i<handsOnTable.size();i++){
	                    int suit = handsOnTable.getCard(i).getSuit();
	                    int rank = handsOnTable.getCard(i).getRank();
	                    g.drawImage(cardImages[suit][rank],cardCoordinateX+halfCardWidth*i,cardCoordinateYBottom+4*gap,this);
	                }
	            }
            }


        }

        /**
         * A method to dues with the event after clicking on the bigTwoPanel.
         * Giving response when clicking the card
         *
         * @param e MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            //the card only show half

            int totalCardOnHand=game.getPlayerList().get(activePlayer).getNumOfCards();
            for (int i=0;i<totalCardOnHand-1;i++){
                if (e.getX() >= (cardCoordinateX+i*halfCardWidth) && e.getX() < (cardCoordinateX+(i+1)*halfCardWidth)){
                    if (!selected[i] && e.getY()>= (cardCoordinateYBottom+gap*activePlayer) && e.getY()<(cardCoordinateYBottom+gap*activePlayer+cardHeight)){
                        selected[i] = true;
                    }
                    else if (selected[i] && e.getY()>= (selectedCardCoordinateY +gap*activePlayer) && e.getY()<(selectedCardCoordinateY +gap*activePlayer+cardHeight)){
                        selected[i] = false;
                    }
                }

            }
            //the last card
            if (e.getX() >= (cardCoordinateX+(totalCardOnHand-1)*halfCardWidth) && e.getX() <= (cardCoordinateX+(totalCardOnHand-1)*halfCardWidth+cardWidth)){
                if (!selected[totalCardOnHand-1] && e.getY()>= (cardCoordinateYBottom+gap*activePlayer) && e.getY()<(cardCoordinateYBottom+gap*activePlayer+cardHeight)){
                    selected[totalCardOnHand-1] = true;
                }
                else if (selected[totalCardOnHand-1] && e.getY()>= (selectedCardCoordinateY +gap*activePlayer) && e.getY()<(selectedCardCoordinateY +gap*activePlayer+cardHeight)){
                    selected[totalCardOnHand-1] = false;
                }
            }
            frame.repaint();
        }

        /**
         * Not used
         * @param e
         */
        public void mousePressed(MouseEvent e) {

        }
        /**
         * Not used
         * @param e
         */
        public void mouseReleased(MouseEvent e) {

        }
        /**
         * Not used
         * @param e
         */
        public void mouseEntered(MouseEvent e) {

        }
        /**
         * Not used
         * @param e
         */
        public void mouseExited(MouseEvent e) {

        }
    }
    /**
     * An inner class extends the JPanel and used to create the chatArea and msgArea
     */
    class ChatBox extends JPanel{
        /**
         * the constructor of the ChatBox class
         */
        public ChatBox(){
            msgArea.setEditable(false);
            JScrollPane ScrollMsgArea = new JScrollPane(msgArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            DefaultCaret MsgAreaCaret = (DefaultCaret)msgArea.getCaret();
            MsgAreaCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            add(ScrollMsgArea);

            chatArea.setEditable(false);
            chatArea.setForeground(Color.BLUE);
            JScrollPane ScrollChatArea = new JScrollPane(chatArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            DefaultCaret ChatAreaCaret = (DefaultCaret)msgArea.getCaret();
            ChatAreaCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            add(ScrollChatArea);

        }
    }

    /**
     * An inner class extends the JPanel and used to create the MenuBar
     */
    class MenuBar extends JMenuBar{
        /**
         *@gameButton A button to open the slide menu of Game function
         *
         * @messageButton A button to open the slide menu of the message function
         */
        JMenu gameButton,messageButton;
        /**
         * @restartButton a button to restart the game
         *
         * @quitButton a button to quit the frame
         *
         * @clearButton a button to clear the msgArea text
         */
        JMenuItem connectButton, quitButton, clearButton;

        /**
         * the constructor of the MenuBar class
         */
        public MenuBar(){
            gameButton    =new JMenu("Game");
            messageButton =new JMenu("Message");
            //restart
            connectButton =new JMenuItem("Connect");
            connectButton.addActionListener(new ConnectMenuItemListener());
            //quit
            quitButton    =new JMenuItem("Quit");
            quitButton.addActionListener(new QuitMenuItemListener());
            //clear
            clearButton   =new JMenuItem("Clear");
            clearButton.addActionListener(new ClearMenuItemListener());

            gameButton.add(connectButton);
            gameButton.add(quitButton);
            messageButton.add(clearButton);

            add(gameButton);
            add(messageButton);

        }
    }

    /**
     *An inner class extends the JPanel and used to create the ButtonBar
     */
    class ButtonBar extends JPanel{
        /**
         * the constructor of the ButtonBar class
         */
        public ButtonBar(){
            JButton playButton= new JButton("Play");
            JButton passButton= new JButton("Pass");
            playButton.addActionListener(new PlayButtonListener());
            passButton.addActionListener(new PassButtonListener());
            add(playButton);
            add(Box.createHorizontalStrut(10));
            add(passButton);

        }
    }
    /**
     *An inner class extends the JPanel and used to create the MessageBar
     */
    class MessageBar extends JPanel{
        /**
         * a int indicate the width of the chatInput
         */
        private static final int CHAT_COLUMNS = 30;

        /**
         * the constructor of the MessageBar class
         */
        public MessageBar(){
            //message
            JLabel jlabel = new JLabel("Message: ");
            JTextField chatInput = new JTextField(CHAT_COLUMNS);
            chatInput.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.sendMessage(new CardGameMessage(CardGameMessage.MSG,game.getCurrentPlayerIdx(),chatInput.getText()));
                    chatInput.setText("");
                    chatInput.requestFocus();

                }
            });
            add(jlabel);
            add(chatInput) ;
        }
    }
    /**
     * Returns an array of indices of the cards selected through the UI.
     *
     * @return an array of indices of the cards selected, or null if no valid cards
     *         have been selected
     */
    private int[] getSelected() {
        int[] cardIdx = null;
        int count = 0;
        for (int j = 0; j < selected.length; j++) {
            if (selected[j]) {
                count++;
            }
        }

        if (count != 0) {
            cardIdx = new int[count];
            count = 0;
            for (int j = 0; j < selected.length; j++) {
                if (selected[j]) {
                    cardIdx[count] = j;
                    count++;
                }
            }
        }
        return cardIdx;
    }

    /**
     * A class to listen the event of the playButton
     */
    class PlayButtonListener implements ActionListener{
        /**
         * A method make the move if the action is valid,otherwise print wrong message on msgArea
         *
         * @param e the clicking action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
        	if (!isStart) { msgArea.append("The Game is not Started yet !"+ "\n");return;}
            if (game.getCurrentPlayerIdx() == activePlayer)
            {
                if (getSelected()== null)
                {
//                	msgArea.append("game Idx"+game.getCurrentPlayerIdx() + "\n");
//                	msgArea.append(activePlayer + "\n");
                    msgArea.append("Not a legal move!!!" + "\n");
                }

                else
                {
                    client.sendMessage(new CardGameMessage(CardGameMessage.MOVE,activePlayer,getSelected()));
                }

                repaint();
            }

            else
            {
//            	msgArea.append("game Idx"+game.getCurrentPlayerIdx() + "\n");
//            	msgArea.append(activePlayer + "\n");
                msgArea.append("It is not your turn yet !" + "\n");
                repaint();
            }
        }
    }
    /**
     * A class to listen the event of the passButton
     */
    class PassButtonListener implements ActionListener{
        /**
         * A method make the pass action
         *
         * @param e the clicking action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
        	if (!isStart) { msgArea.append("The Game is not Started yet !"+ "\n");return;}
            if (game.getCurrentPlayerIdx() == activePlayer)
            {
                client.sendMessage(new CardGameMessage(CardGameMessage.MOVE,activePlayer,null));
                repaint();
            }

            else
            {
                msgArea.append("It is not your turn yet !" + "\n");
                repaint();
            }
        }
    }
    /**
     * A class to listen the event of the restartButton
     */
    class ConnectMenuItemListener implements ActionListener{
        /**
         * A method restart the game
         *
         * @param e the clicking action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (client.getPlayerID() == -1) {
                client.connect();
            }else{
                msgArea.append("You are already connected to the Server"+"\n");
            }
        }
    }
    /**
     * A class to listen the event of the quitButton
     */
    class QuitMenuItemListener implements ActionListener{
        /**
         * A method to quit the frame
         *
         * @param e the clicking action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    /**
     * A class to listen the event of the clearButton
     */
    class ClearMenuItemListener implements ActionListener{
        /**
         * A method to clear the message on the msgArea
         *
         * @param e the clicking action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            clearMsgArea();
        }
    }

    //Boardcast Message

    /**
     * method to boardcast the join message
     * @param ip the ip address of the server
     * @param port the port of the connected ip address
     */
    public void BoardcastJoinMessage(String ip,int port){
        msgArea.append("Connected to servert at /"+ip+":"+port+"\n");
    }
    /**
     * method to boardcast the full message
     * @param id not using
     */
    public void BoardcastFullMessage(int id){msgArea.append("The game room are full"+"\n"); }
    /**
     * method to boardcast the lose message
     * @param id the id of losing player
     */
    public void BoardcastLoseMessage(int id){
        msgArea.append("Player"+id+" Quit the Game"+"\n");
    }
    /**
     * method to boardcast the ready message
     * @param name the name of the player gets ready
     */
    public void BoardcastReadyMessage(String name){
        msgArea.append(name+" is Ready"+"\n");
    }
    /**
     * method to boardcast the start message
     */
    public void BoardcastStartMessage(){
    	msgArea.setText("");
        msgArea.append("All player are ready, the Game are Going to Start !!"+"\n");
    }
    /**
     * method to boardcast the error message
     */
    public void BoardcastErrorMessage(){
        msgArea.append("Server Error!!");
    }
    /**
     * method to boardcast the end game message
     */
    public void BoardcastEndOfGame(){
        msgArea.append("" + "\n");
        msgArea.append("Games End" + "\n");
        for(int i = 0; i < playerList.size();i++)
        {
            if(playerList.get(i).getNumOfCards() == 0)
                msgArea.append("Player " + i + " wins the game" + "\n");
            else
                msgArea.append("Player " + i + " has " + playerList.get(i).getNumOfCards() + " cards in hand" + "\n");
        }
    }


}
