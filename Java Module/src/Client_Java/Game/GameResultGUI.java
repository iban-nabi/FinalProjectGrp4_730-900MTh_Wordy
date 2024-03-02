package Client_Java.Game;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;
import Client_Java.GUITools.StyledButton;
import Client_Java.OtherUtilities.MenuLongestWordsLBoards;
import Client_Java.OtherUtilities.MenuWinLBoards;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameResultGUI extends JFrame {
    public JLabel display;
    public ImageIcon backgroundWin;
    public JLabel name, word, score, round;
    public JButton back, joinAnother, viewLongest;
    public JLabel win, draw;
    private static GameActions gameActions;
    private static String username;
    private int gameID;
    boolean flag;

    public GameResultGUI(GameActions gameActions, String username, int gameID, int roundNumber) {
        this.gameActions = gameActions;
        this.username = username;
        this.gameID = gameID;

        String roundWinner = gameActions.getRoundWinner(gameID);
        String gameWinner = gameActions.getGameWinner(gameID);
        String[] s = roundWinner.split(":");
        System.out.println(roundWinner);
        try {
            if (!Objects.equals(roundWinner, "tie")) {
                backgroundWin = new ImageIcon(Objects.requireNonNull(getClass().getResource("res/Roundwinner.gif")));
                display = new JLabel(backgroundWin);

                if (Objects.equals(gameWinner, " ")) { // show round winner
                    name = new JLabel("Player: " + s[0], JLabel.CENTER);
                    win = new JLabel("Game Round " + roundNumber, JLabel.CENTER);
                    word = new JLabel("Word: " + s[1], JLabel.CENTER); // include the word in return;
                } else { // show game winner
                    name = new JLabel("Winner: " + gameWinner, JLabel.CENTER);
                    win = new JLabel("Game Winner", JLabel.CENTER);
                    word = new JLabel("", JLabel.CENTER); // include the word in return;
                    flag = true;
                }

                win.setForeground(new Color(0xd9a614));
                win.setFont(new Font("Agency FB", Font.BOLD, 55));
                win.setBounds(260, 110, 650, 40);
                display.add(win);

                name.setBounds(240, 390, 650, 85);
                Font fontname = new Font("Times New Roman", Font.BOLD, 55);
                name.setFont(fontname);
                name.setForeground(Color.YELLOW);
                name.setOpaque(false);

                word.setBounds(240, 540, 650, 40);
                Font fontword = new Font("Times New Roman", Font.BOLD, 40);
                word.setFont(fontword);
                word.setForeground(Color.YELLOW);
                word.setOpaque(false);

                score = new JLabel("1,000", JLabel.CENTER);
                score.setBounds(240, 480, 650, 20);
                Font fontscore = new Font("Times New Roman", Font.BOLD, 20);
                score.setFont(fontscore);
                score.setForeground(Color.YELLOW);
                score.setOpaque(false);

                display.add(name);
                display.add(word);

            } else {
                backgroundWin = new ImageIcon(Objects.requireNonNull(getClass().getResource("res/RoundDraw.gif")));
                display = new JLabel(backgroundWin);

                draw = new JLabel("ROUND " + roundNumber, JLabel.CENTER);
                draw.setForeground(new Color(0xd9a614));
                draw.setFont(new Font("Agency FB", Font.BOLD, 55));
                draw.setBounds(250, 110, 650, 40);
                display.add(draw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        MoveListener listener = new MoveListener(this);
        display.addMouseListener(listener);
        display.addMouseMotionListener(listener);

        this.setTitle("RoundResults");
        this.add(display);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        if (flag) {
            Font fontJ = new Font("Verdana", Font.BOLD, 25);
            Font fontB = new Font("Verdana", Font.BOLD, 12);
            Font fontV = new Font("Verdana", Font.BOLD, 12);

            joinAnother = new JButton("Main Menu");
            joinAnother.setBounds(300, 530, 150, 30);
            joinAnother.setFont(fontB);
            joinAnother.setForeground(Color.DARK_GRAY);
            joinAnother.setUI(new StyledButton());
            joinAnother.setBackground(new Color(0xff0000));
            joinAnother.setForeground(new Color(0xffffff));
            joinAnother.addActionListener(e -> {
                this.dispose();
                try {
                    new PlayerMenuGUI(gameActions, username);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            back = new JButton("Win LBoards");
            back.setBounds(680, 530, 200, 30);
            back.setFont(fontB);
            back.setForeground(Color.DARK_GRAY);
            back.setUI(new StyledButton());
            back.setBackground(new Color(0xfff060));
            back.setForeground(new Color(0x000000));
            back.addActionListener(e -> {
                this.dispose();
                new MenuWinLBoards(gameActions, username);
            });

            viewLongest = new JButton("Longest Word LBoards");
            viewLongest.setBounds(680, 580, 200, 30);
            viewLongest.setFont(fontB);
            viewLongest.setForeground(Color.DARK_GRAY);
            viewLongest.setUI(new StyledButton());
            viewLongest.setBackground(new Color(0xff0f));
            viewLongest.setForeground(new Color(0x0));
            viewLongest.addActionListener(e -> {
                this.dispose();
                new MenuLongestWordsLBoards(gameActions, username);
            });

            display.add(joinAnother);
            display.add(back);
            display.add(viewLongest);
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.dispose();
            gameActions.initializeGame(gameID);
            new GameGUI(gameActions, username, gameID, roundNumber);
        }
    }
}

