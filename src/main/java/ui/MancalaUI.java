package ui;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.InvalidMoveException;
import mancala.KalahRules;
import mancala.MancalaGame;
import mancala.NoSuchPlayerException;
import mancala.PitNotFoundException;
import mancala.Player;
import mancala.Saver;
import mancala.UserProfile;


public class MancalaUI extends JFrame {
    // imported classes
    private MancalaGame game;
    private Saver saver;

    // instance variables
    private int gameState;
    private Player player1;
    private Player player2;
    private boolean player1Saved;
    private boolean player2Saved;
    private boolean bonus;
    private int pitWidth;
    static private final String DEFAULTPATH = "assets/";
    static private final String COMICFONT = "Comic Sans MS";
    static private final String CONFIRMATION = "Confirmation";
    static private final int ONE = 1;
    static private final int TWO = 2;

    // gui elements that need to be updated
    private JPanel headerPanel;
    private JPanel p2StatPanel;
    private JPanel p1StatPanel;
    private JPanel boardPanel;
    private JButton loadProfileP1;
    private JButton loadProfileP2;
    private JButton newProfileP1;
    private JButton newProfileP2;
    
    /**
     * First method to run.
     *
     * @param args Given arguments.
     */
    public static void main(final String[] args) {
        final MancalaUI display = new MancalaUI("Mancala");
        display.setVisible(true);
    }

    /**
     * Constructor for the user interface.
     */
    public MancalaUI() {
        this("Mancala");
    }

    /**
     * Constructor to create the user interface.
     *
     * @param title The title of the application.
     */
    public MancalaUI(final String title) {
        super();
        game = new MancalaGame();
        saver = new Saver();
        gameState = 0;
        createAssetFolder();

        basicSetUp(title);
    }

    private void createAssetFolder() {
        final File dir = new File(DEFAULTPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private boolean saveGame() {
        boolean exit;
        if (gameState == 0) {
            exit = true;

        } else {
            // if game is running, ask if they want to save it
            final int choice = JOptionPane.showOptionDialog(null, 
                "Do you want to save the game?", 
                CONFIRMATION, 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new String[]{"Yes", "No"},
                null);

            if (choice == JOptionPane.YES_OPTION) {
                final String filename = JOptionPane.showInputDialog(null, "Please enter a filename:");

                saver.saveObject(game, DEFAULTPATH+filename);
                exit = true;
            } else if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                // Handle cancellation here
                // For example, you might want to do nothing or provide feedback
                exit = false;
            } else {
                exit = true;
            }
        }

        return exit;
    }

    private void onExit() {
        boolean exit=saveGame();

        // if players are loaded, ask if they want to save them
        // break in two
        if (exit) {
            for (int i=1; i<=2; i++) {
                if (i==1&&player1Saved || i==2&&player2Saved) {
                    String name;
                    if (i == ONE) {
                        name = player1.getName();
                    } else {
                        name = player2.getName();
                    }
                    final int choice = JOptionPane.showOptionDialog(null, 
                        "Do you want to save "+name+"'s profile?", 
                        CONFIRMATION, 
                        JOptionPane.YES_NO_CANCEL_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        null,
                        null); 

                    if (choice == JOptionPane.YES_OPTION) {
                        if (i == ONE) {
                            saver.saveObject(player1.getUser(), DEFAULTPATH + player1.getName());
                        } else {
                            saver.saveObject(player2.getUser(), DEFAULTPATH + player2.getName());
                        }
                    } else if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                        exit=false;
                        break;
                    }
                }
            }
        }

        if (exit) {
            System.exit(0);
        }
    }

    private void basicSetUp(final String title) {
        // set up main frame
        this.setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent event) {
                onExit();
            }
        });

        setLayout(new BorderLayout());

        // create display top down
        add(makeTopPanel(), BorderLayout.NORTH);
        add(createMancalaBoardPanel(null));
        add(makeBottomPanel(), BorderLayout.SOUTH);

        setJMenuBar(makeMenuBar());

        pack();
        repaintDisplay();
    }

    private JMenuBar makeMenuBar() {
        // Create a menu bar
        final JMenuBar menuBar = new JMenuBar();

        // Create a menu
        final JMenu settingsMenu = new JMenu("Game Manager");
        settingsMenu.setFont(new Font(COMICFONT, Font.BOLD, 25));
        final Dimension setMenuPrefSize = settingsMenu.getPreferredSize();
        final int width = setMenuPrefSize.width;

        // create panel for all sub buttons
        final JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new GridLayout(0,1));
        final Dimension panelDimension = new Dimension(width-11, width);
        dropdownPanel.setPreferredSize(panelDimension);

        // Create menu items
        final JButton newGameButton = newGameButton();
        newGameButton.setFont(new Font(COMICFONT, Font.BOLD, 15));
        final JButton restartGameButton = loadGameButton();
        restartGameButton.setFont(new Font(COMICFONT, Font.BOLD, 15));
        final JButton quitGameButton = quitGameButton();
        quitGameButton.setFont(new Font(COMICFONT, Font.BOLD, 15));
        
        // Add menu items to the menu
        dropdownPanel.add(newGameButton);
        dropdownPanel.add(restartGameButton);
        dropdownPanel.add(quitGameButton);

        settingsMenu.add(dropdownPanel);

        // Add the menu to the menu bar
        menuBar.add(Box.createHorizontalGlue()); // Aligns the menu to the left
        menuBar.add(settingsMenu);
        menuBar.add(Box.createHorizontalGlue()); // Aligns the menu to the right

        
        return menuBar;
    }
    
    private JButton quitGameButton() {
        final JButton button = new JButton("Main Menu");

        button.addActionListener(e -> quitGame());
        return button;
    }

    private void quitGame() {
        if (gameState != 0) {
            final int choice = JOptionPane.showOptionDialog(null, 
                "Do you want to save the game?", 
                CONFIRMATION, 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new String[]{"Yes", "No"},
                "Yes");

            if (choice == JOptionPane.YES_OPTION) {
                final String filename = JOptionPane.showInputDialog(null, "Please enter a filename:");

                saver.saveObject(game, DEFAULTPATH+filename);
            }

            game.resetBoard();
            gameState = 0;
            repaintDisplay();
        }
    }

    private JButton loadGameButton() {
        final JButton button = new JButton("Load Saved Game");
        
        button.addActionListener(e -> openSavedGame());
        return button;
    }

    private Player getPlayerObj(final int playerNum) {
        final Player player;
        final ArrayList<Player> players = game.getPlayers();
        if (playerNum == ONE) {
            player = (Player) players.get(0);
        } else {
            player = (Player) players.get(1);
        }

        return player;
    }

    private String getFilePath(final File file) {
        return file.getAbsolutePath();
    }

    protected void openSavedGame() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        final int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            final String path = getFilePath(file);
            final MancalaGame loadedGame = (MancalaGame) saver.loadObject(path);
            game = loadedGame;
                
            if (game.getBoard().getClass() == KalahRules.class) {
                gameState = 1;
            } else if (game.getBoard().getClass() == AyoRules.class) {
                gameState = 2;
            }

            player1 = getPlayerObj(1);
            player2 = getPlayerObj(2);
            player1Saved = true;
            player2Saved = true;
        }

        repaintDisplay();
        pack();
    }

    /* New Game Button */
    private JButton newGameButton() {
        final JButton button = new JButton("Start New Game"); // Customize the button label
        button.addActionListener(e -> newGame());
        return button;
    }

    private void newGame() {
        // check if players are registered
        if (!player1Saved || !player2Saved) {
            final int choice = JOptionPane.showConfirmDialog(null, "There are not yet two registered players. Create default accounts?", "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                // yes
                if (!player1Saved) {
                    final UserProfile user = new UserProfile();
                    user.setName("Player 1");
                    player1 = new Player(user);
                    player1Saved = true;
                }
                if (!player2Saved) {
                    final UserProfile user = new UserProfile();
                    user.setName("Player 2");
                    player2 = new Player(user);
                    player2Saved = true;
                }

                repaintDisplay();
            } else {
                // no
                return;
            }
        }
        pack();

        // Options to be displayed in the dialog
        final Object[] options = {"Kalah Rules", "Ayo Rules"};

        // Show the option dialog
        final int choice = JOptionPane.showOptionDialog(
                null,
                "Choose a ruleset:",
                "Option Dialog",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Process the user's choice
        if (choice == JOptionPane.YES_OPTION) {
            game.setBoard(new KalahRules());
            game.setRuleName("Kalah");
            gameState = 1;
        } else if (choice == JOptionPane.NO_OPTION) {
            game.setBoard(new AyoRules());
            game.setRuleName("Ayo");
            gameState = 2;
        }

        game.setPlayers(player1, player2);
        if (gameState != 0) {
            // set the players
            game.setPlayers(player1, player2);

            // start the game (set up board)
            game.startNewGame();
            repaintDisplay();
        }
        pack();
    }


    private JPanel makeTopPanel() {
        // overall JPanel
        final JPanel upperSpot = new JPanel();
        upperSpot.setLayout(new BoxLayout(upperSpot, BoxLayout.Y_AXIS));

        // make header text
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        changeHeader("Please register players to begin playing");

        // player two menu
        final JPanel playerTwoMenu = new JPanel();
        
        final JPanel centerPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2StatPanel = displayStats(-1);
        final Dimension p2StatDim = p2StatPanel.getPreferredSize();
        final int statPanelHeight = p2StatDim.height;
        centerPanel2.add(p2StatPanel, BorderLayout.SOUTH);

        final JPanel leftPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadProfileP2 = loadButton(2);
        leftPanel2.add(loadProfileP2);

        newProfileP2 = newProfileBtn(2);
        final JPanel rightPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel2.add(newProfileP2);

        final Dimension buttonDimension = new Dimension(statPanelHeight, statPanelHeight);
        loadProfileP2.setPreferredSize(buttonDimension);
        newProfileP2.setPreferredSize(buttonDimension);

        playerTwoMenu.add(leftPanel2, BorderLayout.WEST);
        playerTwoMenu.add(centerPanel2, BorderLayout.CENTER);
        playerTwoMenu.add(rightPanel2, BorderLayout.EAST);

        upperSpot.add(headerPanel);
        upperSpot.add(playerTwoMenu);
        
        return upperSpot;
    }

    // method to change the header text
    private void changeHeader(final String text) {
        if (headerPanel != null) {
            headerPanel.removeAll();
        }
        final JLabel header = new JLabel(text);
        header.setFont(new Font(COMICFONT, Font.BOLD, 30));

        headerPanel.add(header);
        headerPanel.repaint();
        headerPanel.revalidate();
    }

    private JPanel makeBottomPanel() {
        // player one menu
        final JPanel playerOneMenu = new JPanel();

        p1StatPanel = displayStats(-1);
        final JPanel centerPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel1.add(p1StatPanel);

        loadProfileP1 = loadButton(1);
        final JPanel leftPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel1.add(loadProfileP1);

        newProfileP1 = newProfileBtn(1);
        final JPanel rightPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel1.add(newProfileP1);

        final Dimension dimen = p1StatPanel.getPreferredSize();
        final int statPanelHeight = dimen.height;
        final Dimension buttonDimension = new Dimension(statPanelHeight, statPanelHeight);
        loadProfileP1.setPreferredSize(buttonDimension);
        newProfileP1.setPreferredSize(buttonDimension);

        playerOneMenu.add(leftPanel1, BorderLayout.WEST);
        playerOneMenu.add(centerPanel1, BorderLayout.CENTER);
        playerOneMenu.add(rightPanel1, BorderLayout.EAST);  

        return playerOneMenu;
    }
    
    // method to create JPanel of the stats display
    private JPanel displayStats(final int playerNum) {
        final JPanel panel = new JPanel();
                
        final Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        // EmptyBorder for padding
        final Border paddingBorder = BorderFactory.createEmptyBorder(5, 25, 5, 25);
    
        // CompoundBorder to combine the existing border with padding
        final Border compoundBorder = BorderFactory.createCompoundBorder(border, paddingBorder);
        panel.setBorder(compoundBorder);

        // get all stats
        if (playerNum == ONE) {
            final JLabel p1StatDisplay = new JLabel(getStats(playerNum));
            p1StatDisplay.setFocusable(false);
		    p1StatDisplay.setFont(new Font(COMICFONT, Font.BOLD, 20));
            panel.add(p1StatDisplay);
        } else {
            final JLabel p2StatDisplay = new JLabel(getStats(playerNum));
    		p2StatDisplay.setFocusable(false);
            p2StatDisplay.setFont(new Font(COMICFONT, Font.BOLD, 20));
            panel.add(p2StatDisplay);
        }


        return panel;
    }

    private String getUsername(final UserProfile user) {
        return user.getName();
    }
    private int getKalahGP(final UserProfile user) {
        return user.kalahGamesPlayed();
    }
    private int getKalahGW(final UserProfile user) {
        return user.kalahGamesWon();
    }
    private int getAyoGP(final UserProfile user) {
        return user.ayoGamesPlayed();
    }
    private int getAyoGW(final UserProfile user) {
        return user.ayoGamesWon();
    }
    
    // helper method to get all the player's stats
    private String getStats(final int playerNum) {
        String stats = "<html>";
        if (playerNum == -1) {
            stats += "\tNo Player loaded.<br/><br/>";
            stats += "<html>" +
            "<table>" +
            "<tr><td>Kalah Games Played:</td><td align=\"right\">" + "0" + "</td></tr>" +
            "<tr><td>Kalah Games Won:</td><td align=\"right\">" + "0" + "</td></tr>" +
            "<tr><td>Ayo Games Played:</td><td align=\"right\">" + "0" + "</td></tr>" +
            "<tr><td>Ayo Games Won:</td><td align=\"right\">" + "0" + "</td></tr>" +
            "</table>" +
            "</html>";
        } else {
            UserProfile user;
            if (playerNum == ONE) {
                user = player1.getUser();
            } else {
                user = player2.getUser();
            }
            stats += "Player "+playerNum+": "+ getUsername(user)+"<br/><br/>";
            stats += "<html>" +
            "<table>" +
            "<tr><td>Kalah Games Played:</td><td align=\"right\">" + getKalahGP(user) + "</td></tr>" +
            "<tr><td>Kalah Games Won:</td><td align=\"right\">" + getKalahGW(user) + "</td></tr>" +
            "<tr><td>Ayo Games Played:</td><td align=\"right\">" + getAyoGP(user) + "</td></tr>" +
            "<tr><td>Ayo Games Won:</td><td align=\"right\">" + getAyoGW(user) + "</td></tr>" +
            "</table>" +
            "</html>";
        }
        stats += "</html>";

        return stats;
    }

    private JButton loadButton(final int playerNum) {
        final JButton button = new JButton("Load Profile"); // Customize the button label
        button.setFont(new Font(COMICFONT, Font.BOLD, 20));

        if (gameState != 0) {
            button.setEnabled(false);
        }
        button.addActionListener(e -> openSave(playerNum));
        return button;
    }

    protected void openSave(final int playerNum) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        final int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            final String path = getFilePath(file);
            if (playerNum == ONE) {
                final UserProfile user = (UserProfile) saver.loadObject(path);
                if (user != null) {
                    player1 = new Player(user);
                    player1Saved = true;
                }
            } else {
                final UserProfile user = (UserProfile) saver.loadObject(path);
                if (user != null) {
                    player2 = new Player(user);
                    player2Saved = true;
                }
            }
        }

        repaintDisplay();
        pack();
    }

    private JButton newProfileBtn(final int playerNum) {
        final JButton button = new JButton("New Profile"); // Customize the button label
        button.setFont(new Font(COMICFONT, Font.BOLD, 20));

        if (gameState != 0) {
            button.setEnabled(false);
        }
        button.addActionListener(e -> newProfile(playerNum));
        return button;
    }

    private void newProfile(final int playerNum) {
        final String name = JOptionPane.showInputDialog(null, "Enter your name:");

        if (name != null && !name.equals("")) {
            final UserProfile user = new UserProfile();
            user.setName(name);
            if (playerNum == ONE) {
                player1 = new Player(user);
                player1Saved = true;
            } else {
                player2 = new Player(user);
                player2Saved = true;
            }
            repaintDisplay();

            final int saveOption = askToSaveProfile(playerNum);
            if (saveOption == JOptionPane.YES_OPTION) {
                saver.saveObject(user, DEFAULTPATH+user.getName());          
            }
        }

        pack();
    }

    private int askToSaveProfile(final int playerNum) {
        String name;
        if (playerNum == ONE) {
            name = player1.getName();
        } else {
            name = player2.getName();
        }
        
        return JOptionPane.showConfirmDialog(
                null,
                "Would you like to save the profile for " + name + "?",
                "Save Profile",
                JOptionPane.YES_NO_OPTION);
    }

    private void setProfileBtns() {
        if (gameState == 0) {
            loadProfileP1.setEnabled(true);
            loadProfileP2.setEnabled(true);
            newProfileP1.setEnabled(true);
            newProfileP2.setEnabled(true);
        } else {
            loadProfileP1.setEnabled(false);
            loadProfileP2.setEnabled(false);
            newProfileP1.setEnabled(false);
            newProfileP2.setEnabled(false);
        }
        if (player1Saved) {
            p1StatPanel.removeAll();
            p1StatPanel.setBorder(null);
            p1StatPanel.add(displayStats(1));
        } 


        final boolean playable = player1Saved && player2Saved;
        if (playable && gameState == 0) {
            changeHeader("Start a new game or load a previous one");
            boardPanel.removeAll();
            boardPanel.add(createMancalaBoardPanel(null));
        } else if (playable) {
            final Player player = game.getCurrentPlayer();
            final String name = player.getName();
            if (gameState == ONE) {
                if (game.isBonus()) {
                    changeHeader("Bonus! It is "+name+"'s turn");
                } else {
                    changeHeader("It is "+name+"'s turn");
                }
            } else {
                changeHeader("It is "+name+"'s turn");
            }
        }
    }

    private void repaintDisplay() {
        setProfileBtns();
        if (player2Saved) {
            p2StatPanel.removeAll();
            p2StatPanel.setBorder(null);
            p2StatPanel.add(displayStats(2));
        }
  
        if (gameState != 0 && game.isGameOver()) {
            try {
                final Player winner = game.getWinner();
                String text;
                if (winner != null) {
                    text = "Game Over! "+winner.getName()+" won the game!";
                } else {
                    text = "Game over! It's a tie!";
                }
                boardPanel.removeAll();
                boardPanel.add(createMancalaBoardPanel(null));
                changeHeader(text);
                JOptionPane.showMessageDialog(null, text, "Information", JOptionPane.INFORMATION_MESSAGE);

                // ask if they want new game or main menu
                gameState = 0;

                endOfGame();
            } catch (GameNotOverException e) {
                // this wont happen
            }
        } else {
            boardPanel.removeAll();
            boardPanel.add(createMancalaBoardPanel(game.getCurrentPlayer()));
        }


        p1StatPanel.repaint();
        p1StatPanel.revalidate();
        p2StatPanel.repaint();
        p2StatPanel.revalidate();
    }

    private void endOfGame() {

        final int choice = JOptionPane.showOptionDialog(null, 
            "What would you like to do?", 
            CONFIRMATION, 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            new String[]{"Start New Game", "Return to Main Screen"},  // Options
            "Yes");  // Default selection

        if (choice == JOptionPane.YES_OPTION) {
            newGame();
        } else if (choice == JOptionPane.NO_OPTION) {
            game.startNewGame();
            repaintDisplay();
        }
    }

    private JPanel createMancalaBoardPanel(final Player curPlayer) {
        int curPlayerNum;
        if (curPlayer == null) {
            curPlayerNum = -1;
        } else {
            curPlayerNum = getPlayerNum(curPlayer);
        }
        boardPanel = new JPanel(new BorderLayout());
        final JPanel centralPitsPanel = new JPanel(new GridLayout(2, 1));

        final JPanel bottomRow = new JPanel(new GridLayout(1, 6));
        for (int i = 1; i <= 6; i++) {
            final JButton button = pitButton(i);
            if (curPlayerNum != ONE) {
                button.setEnabled(false);
            }
            bottomRow.add(button);
        }

        final JPanel topRow = new JPanel(new GridLayout(1, 6));
        for (int i = 12; i > 6; i--) {
            final JButton button = pitButton(i);
            if (curPlayerNum != TWO) {
                button.setEnabled(false);
            }
            topRow.add(button);
        }

        centralPitsPanel.add(topRow);
        centralPitsPanel.add(bottomRow);

        // Create store for player 1 (height = 2)
        final JPanel store1 = new JPanel(new GridLayout(1, 1));
        store1.add(storeButton(2));

        // Create store for player 2 (height = 2)
        final JPanel store2 = new JPanel(new GridLayout(1, 1));
        store2.add(storeButton(1));

        // Add components to the board panel
        boardPanel.add(store1, BorderLayout.WEST);
        boardPanel.add(centralPitsPanel, BorderLayout.CENTER);
        boardPanel.add(store2, BorderLayout.EAST);

        return boardPanel;
    }

    private JButton pitButton(final int pitNum) {
        final JButton button = new JButton();
        int numStones;
        if (gameState == 0) {
            button.setEnabled(false);
            numStones = 0;
        } else {
            try {
                numStones = game.getNumStones(pitNum);
            } catch (PitNotFoundException e) {
                // should never happen
                numStones = -1;
            }
        }

        button.setText(numStones+"");
        button.setFont(new Font(COMICFONT, Font.BOLD, 20));
        pitWidth = button.getPreferredSize().width;
        final Dimension buttonDimension = new Dimension(pitWidth, pitWidth*2);
        button.setPreferredSize(buttonDimension);

        button.addActionListener(e -> makeMove(pitNum));
        
        return button;
    }

    private Player getOtherPlayer(final Player curPlayer) {
        Player otherPlayer;
        
        if (curPlayer.equals(player1)) {
            otherPlayer = player2;
        } else {
            otherPlayer = player1;
        }

        return otherPlayer;
    }

    private void makeMove(final int pitNum) {
        try {
            if (gameState == ONE) {
                game.setBonus(false);
            }
            game.move(pitNum);
            game.setCurrentPlayer(getOtherPlayer(game.getCurrentPlayer()));
            if (gameState == ONE) {
                bonus = game.isBonus();
                if (bonus) {
                    game.setCurrentPlayer(getOtherPlayer(game.getCurrentPlayer()));
                }
            }
        } catch (InvalidMoveException e) {
            // this can't happen
        }

        if (game.isGameOver()) {
            // get winner
            try {
                final Player winner = game.getWinner();
                final Player loser = getOtherPlayer(winner);
                final UserProfile userWinner = winner.getUser();
                final UserProfile userLoser = loser.getUser();

                if (gameState == ONE) {
                    // kalah game
                    userWinner.finishedKalahGame();
                    userWinner.wonKalahGame();
                    userLoser.finishedKalahGame();
                } else {
                    // ayo game
                    userWinner.finishedAyoGame();
                    userWinner.wonAyoGame();
                    userLoser.finishedAyoGame();
                }
                repaintDisplay();
            } catch (GameNotOverException e) {
                // never happen
            }
        }

        repaintDisplay();
    }

    private JButton storeButton(final int storeNum) {
        final JButton button = new JButton();
        button.setEnabled(false);
        int numStones;
        String text;
        if (gameState == 0) {
            numStones = 0;
            text=""+numStones;
        } else {
            try {
                if (storeNum == ONE) {
                    numStones = game.getStoreCount(player1);
                } else {
                    numStones = game.getStoreCount(player2);
                }
                text=""+numStones;
            } catch (NoSuchPlayerException e) {
                // this shouldn't happen
                text="yikes";
            }
        }
        
        final Dimension buttonDimension = new Dimension(pitWidth*2, 100);
        button.setPreferredSize(buttonDimension);

        button.setText(text);
        button.setFont(new Font(COMICFONT, Font.BOLD, 20));

        return button;
    }

    private int getPlayerNum(final Player curPlayer) {
        int toReturn;
        if (curPlayer.equals(player1)) {
            toReturn = 1;
        } else {
            toReturn = 2;
        }

        return toReturn;
    }

}
