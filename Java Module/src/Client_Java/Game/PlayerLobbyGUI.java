package Client_Java.Game;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerLobbyGUI extends JFrame {
    JLabel display;
    static GameActions gameActions;
    int gameID;
    int noOfPlayers;

    public PlayerLobbyGUI(GameActions gameActions, String username) throws InterruptedException {
        this.gameActions=gameActions;
        try {
            ImageIcon background = new ImageIcon(getClass().getResource("res/lobby.gif"));
            display = new JLabel(background);
            this.setContentPane(display);
        } catch (Exception e) {
            System.out.println("Image cannot be found.");
        }


        MoveListener listener = new MoveListener(this);
        display.addMouseListener(listener);
        display.addMouseMotionListener(listener);

        JPanel panelJoinedPlayers = new JPanel();
        panelJoinedPlayers.setOpaque(false);
        panelJoinedPlayers.setBounds(40, 150, 780, 300);

        JTextArea listOfJoinedPlayers = new JTextArea("", 1, 20);
        listOfJoinedPlayers.setFont(new Font("Calibri", Font.BOLD, 30));
        listOfJoinedPlayers.setForeground(new Color(0x36190B));
        listOfJoinedPlayers.setOpaque(false);
        listOfJoinedPlayers.setBorder(null);
        listOfJoinedPlayers.setEditable(false);
        listOfJoinedPlayers.setFocusable(false);
        String playerName = "PlayerName";
        JScrollPane scrollableText = new JScrollPane(listOfJoinedPlayers);
        scrollableText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableText.getViewport().setOpaque(false);
        scrollableText.setOpaque(false);
        scrollableText.setBorder(null);
        panelJoinedPlayers.add(scrollableText, BorderLayout.CENTER);
        this.add(panelJoinedPlayers);


        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        waitForPlayers(gameActions,username);
    }


    public boolean waitForPlayers(GameActions gameActions, String username){
        AtomicBoolean flag = new AtomicBoolean();
        AtomicBoolean breakFlag = new AtomicBoolean(true);
        JLabel waiting = new JLabel("Waiting for other players 0.", SwingConstants.LEFT);
        waiting.setFont(new Font("Calibri", Font.BOLD, 30));
        waiting.setBounds(display.getWidth()/4, 100, display.getWidth(), 50);
        new Thread(() -> {
            waiting.setForeground(new Color(0xFFFFFF));
            gameID = gameActions.joinGame(username);

            //x=gameActions.getTimer(gameID); replace x=0
            for (int x=gameActions.getTimer(gameID); x<=10; x++) {
                waiting.setText("Waiting for other players "+x);

                for (int y=0; y<(x%4); y++) {
                    waiting.setText(waiting.getText() + ".");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            noOfPlayers = gameActions.getGamePlayers(gameID).length;
            if (noOfPlayers==1) {
                waiting.setForeground(new Color(0x732020));
                waiting.setText("          No players detected!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag.set(false);
                this.dispose();
                try {
                    new PlayerMenuGUI(gameActions,username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                waiting.setForeground(new Color(0xFFFFFF));
                waiting.setText("          "+gameActions.getGamePlayers(gameID).length+" player(s) found!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.dispose();
                new InitializeGUI(gameActions,username,gameID).setVisible(true);
                flag.set(true);
            }
            breakFlag.set(false);

        }).start();
        display.add(waiting);

        return flag.get();
    }

}
