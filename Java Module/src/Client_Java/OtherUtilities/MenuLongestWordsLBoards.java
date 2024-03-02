package Client_Java.OtherUtilities;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;
import Client_Java.GUITools.StyledButton;
import Client_Java.Game.PlayerMenuGUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MenuLongestWordsLBoards extends JFrame{
    public JLabel display;
    public ImageIcon backgroundLead;
    public GameActions gameActions;
    public JLabel top1,top2,top3,top4,top5;
    public JLabel win1,win2,win3,win4,win5;
    public JButton back;

    public MenuLongestWordsLBoards(GameActions gameActions, String username) {
        try {
            backgroundLead = new ImageIcon(Objects.requireNonNull(getClass().getResource("res/PLWLeaderboard.gif")));
            display = new JLabel(backgroundLead);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String [][] longestWords = gameActions.getLeaderBoardLongestWordsList();
        Font fontB = new Font("Verdana", Font.BOLD, 12);


        JButton winsLeaderboard = new JButton("Show Player Wins Leaderboard");
        winsLeaderboard.setBounds(650, 550, 300, 40);
        winsLeaderboard.setFont(fontB);
        winsLeaderboard.setUI(new StyledButton());
        winsLeaderboard.setForeground(Color.WHITE);
        winsLeaderboard.setBackground(new Color(0x317307));
        winsLeaderboard.addActionListener(e -> {
            this.dispose();
            new MenuWinLBoards(gameActions,username);
        });
        display.add(winsLeaderboard);

        back = new JButton("Back");
        back.setBounds(980, 550, 100, 40);
        back.setFont(fontB);
        back.setForeground(Color.DARK_GRAY);
        back.setUI(new StyledButton());
        back.setBackground(new Color(0xff0000));
        back.setForeground(new Color(0xffffff));
        back.addActionListener(e -> {
            try {
                new PlayerMenuGUI(gameActions,username);
                dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        display.add(back);

        Font font = new Font("Verdana", Font.BOLD, 20);


        if (longestWords[0][0] != " "){
            top1 = new JLabel(longestWords[0][0],JLabel.LEFT);
            top1.setBounds(405, 195, 650, 20);
            win1 = new JLabel(longestWords[0][1],JLabel.RIGHT);
            win1.setBounds(275, 195, 550, 20);
            top1.setFont(font);
            win1.setFont(font);
            top1.setForeground(Color.BLACK);
            win1.setForeground(Color.BLACK);

            display.add(top1);
            display.add(win1);

            top2 = new JLabel(longestWords[1][0],JLabel.LEFT);
            top2.setBounds(405, 260, 650, 25);
            win2 = new JLabel(longestWords[1][1],JLabel.RIGHT);
            win2.setBounds(275,260, 550, 25);
            top2.setFont(font);
            win2.setFont(font);
            top2.setForeground(Color.BLACK);
            win2.setForeground(Color.BLACK);

            display.add(top2);
            display.add(win2);

            top3 = new JLabel(longestWords[2][0],JLabel.LEFT);
            top3.setBounds(405, 330, 650, 25);
            win3 = new JLabel(longestWords[2][1],JLabel.RIGHT);
            win3.setBounds(275, 330, 550, 25);
            top3.setFont(font);
            win3.setFont(font);
            top3.setForeground(Color.BLACK);
            win3.setForeground(Color.BLACK);

            display.add(top3);
            display.add(win3);

            top4 = new JLabel(longestWords[3][0],JLabel.LEFT);
            top4.setBounds(405, 395, 650, 25);
            win4 = new JLabel(longestWords[3][1],JLabel.RIGHT);
            win4.setBounds(275, 395, 550, 25);
            top4.setFont(font);
            win4.setFont(font);
            top4.setForeground(Color.BLACK);
            win4.setForeground(Color.BLACK);

            display.add(top4);
            display.add(win4);

            top5 = new JLabel(longestWords[4][0],JLabel.LEFT);
            top5.setBounds(405, 465, 650, 25);
            win5 = new JLabel(longestWords[4][1],JLabel.RIGHT);
            win5.setBounds(275, 465, 550, 25);
            top5.setFont(font);
            win5.setFont(font);
            top5.setForeground(Color.BLACK);
            win5.setForeground(Color.BLACK);

            display.add(top5);
            display.add(win5);
        }

        MoveListener listener = new MoveListener(this);
        display.addMouseListener(listener);
        display.addMouseMotionListener(listener);

        this.add(display);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
