package Client_Java.OtherUtilities;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;
import Client_Java.GUITools.StyledButton;
import Client_Java.Game.PlayerMenuGUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MenuWinLBoards extends JFrame{
    public JLabel display;
    public ImageIcon backgroundLead;
    public JLabel top1,top2,top3,top4,top5;
    public JLabel win1,win2,win3,win4,win5;
    public JButton back;

    public MenuWinLBoards(GameActions gameActions, String username) {
        try {
            backgroundLead = new ImageIcon(Objects.requireNonNull(getClass().getResource("res/PLeaderboard.gif")));
            display = new JLabel(backgroundLead);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String [][] topPlayers = gameActions.getLeaderboardWinList();
        Font fontB = new Font("Verdana", Font.BOLD, 12);

        JButton wordLeaderboard = new JButton("Show Longest Word Leaderboard");
        wordLeaderboard.setBounds(650, 550, 300, 40);
        wordLeaderboard.setFont(fontB);
        wordLeaderboard.setUI(new StyledButton());
        wordLeaderboard.setForeground(Color.WHITE);
        wordLeaderboard.setBackground(new Color(0x317307));
        wordLeaderboard.addActionListener(e -> {
            this.dispose();
            new MenuLongestWordsLBoards(gameActions,username);
        });
        display.add(wordLeaderboard);

        JLabel message = new JLabel("Note: Only players with 1 or more wins qualify for the leaderboard!");
        message.setBounds(284, 177, 600, 40);
        message.setFont(new Font("Verdana", Font.BOLD, 16));
        message.setForeground(new Color(0xd9a614));
        display.add(message);


        back = new JButton("Back");
        back.setBounds(980, 550, 100, 40);
        back.setFont(fontB);
        back.setUI(new StyledButton());
        back.setBackground(new Color(0xff0000));
        back.setForeground(new Color(0xffffff));
        back.addActionListener(e -> {
            try {
                new PlayerMenuGUI(gameActions, username);
                dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        display.add(back);

        Font font = new Font("Verdana", Font.BOLD, 20);


        top1 = new JLabel(topPlayers[0][1],JLabel.LEFT);
        top1.setBounds(320, 230, 650, 20);
        win1 = new JLabel(topPlayers[0][0],JLabel.RIGHT);
        win1.setBounds(300, 230, 550, 20);
        top1.setFont(font);
        win1.setFont(font);
        top1.setForeground(Color.BLACK);
        win1.setForeground(Color.DARK_GRAY);

        display.add(top1);
        display.add(win1);

        top2 = new JLabel(topPlayers[1][1],JLabel.LEFT);
        top2.setBounds(320, 295, 650, 25);
        win2 = new JLabel(topPlayers[1][0],JLabel.RIGHT);
        win2.setBounds(300,295, 550, 25);
        top2.setFont(font);
        win2.setFont(font);
        top2.setForeground(Color.WHITE);
        win2.setForeground(Color.WHITE);

        display.add(top2);
        display.add(win2);

        top3 = new JLabel(topPlayers[2][1],JLabel.LEFT);
        top3.setBounds(320, 360, 650, 25);
        win3 = new JLabel(topPlayers[2][0],JLabel.RIGHT);
        win3.setBounds(300, 360, 550, 25);
        top3.setFont(font);
        win3.setFont(font);
        top3.setForeground(Color.WHITE);
        win3.setForeground(Color.WHITE);

        display.add(top3);
        display.add(win3);

        top4 = new JLabel(topPlayers[3][1],JLabel.LEFT);
        top4.setBounds(320, 430, 650, 25);
        win4 = new JLabel(topPlayers[3][0],JLabel.RIGHT);
        win4.setBounds(300, 430, 550, 25);
        top4.setFont(font);
        win4.setFont(font);
        top4.setForeground(Color.WHITE);
        win4.setForeground(Color.WHITE);

        display.add(top4);
        display.add(win4);

        top5 = new JLabel(topPlayers[4][1],JLabel.LEFT);
        top5.setBounds(320, 495, 650, 25);
        win5 = new JLabel(topPlayers[4][0],JLabel.RIGHT);
        win5.setBounds(300, 495, 550, 25);
        top5.setFont(font);
        win5.setFont(font);
        top5.setForeground(Color.WHITE);
        win5.setForeground(Color.WHITE);

        display.add(top5);
        display.add(win5);

        MoveListener listener = new MoveListener(this);
        display.addMouseListener(listener);
        display.addMouseMotionListener(listener);

        this.setTitle("Highest Wins Leaderboards");
        this.add(display);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

