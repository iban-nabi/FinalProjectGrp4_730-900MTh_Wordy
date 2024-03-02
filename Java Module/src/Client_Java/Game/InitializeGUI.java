package Client_Java.Game;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;
import Client_Java.GUITools.ScrollBarCustom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class InitializeGUI extends JFrame{
    public ImageIcon background;
    public JLabel display;
    public JLabel title;
    public JLabel players;
    public JPanel panel;
    public JList <String> listOfPlayers;
    public JScrollPane scrollableText;
    public ScrollBarCustom scroll;

    public InitializeGUI(GameActions gameActions, String username, int gameID) {
        gameActions.initializeGame(gameID);
        background = new ImageIcon(getClass().getResource("res/initializing.gif"));
        display = new JLabel(background);
        this.setContentPane(display);

        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        MoveListener listener = new MoveListener(this);
        display.addMouseListener(listener);
        display.addMouseMotionListener(listener);

        Font initializeFont = null;
        try {
            initializeFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client_Java/OtherUtilities/res/Login.ttf")).deriveFont(48f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client_Java/OtherUtilities/res/Login.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        title = new JLabel("Preparing Game...");
        title.setFont(initializeFont);
        title.setForeground(new java.awt.Color(24, 23, 26));
        title.setBounds(display.getWidth() - 825, display.getHeight() - 608, 500,200);
        display.add(title);

        players = new JLabel("PLAYERS");
        players.setFont(initializeFont);
        players.setForeground(new java.awt.Color(0xECF2FF));
        players.setBounds(display.getWidth() - 700, display.getHeight() - 550, 495, 245);
        display.add(players);


        DefaultListModel <String> list = new DefaultListModel<>();

        for(String s : gameActions.getGamePlayers(gameID)){
            list.addElement(s);
        }

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(display.getWidth() - 885, display.getHeight() - 410, 615, 340);

        listOfPlayers = new JList<>(list);
        listOfPlayers.setFixedCellWidth(580);
        listOfPlayers.setBounds(display.getWidth() - 885, display.getHeight() - 150, 615, 500);

        listOfPlayers.setFont(new Font("Agency FB", Font.BOLD, 33));
        listOfPlayers.setBackground(new Color(0, 0, 0,0));
        listOfPlayers.setForeground(new Color(0xF6F1F1));

        scroll = new ScrollBarCustom();
        scroll.setOrientation(JScrollBar.HORIZONTAL);

        scrollableText = new JScrollPane(listOfPlayers);
        scrollableText.setVerticalScrollBar(new ScrollBarCustom());
        scrollableText.setHorizontalScrollBar(scroll);
        scrollableText.setBorder(new EmptyBorder(20, 3, 3, 3));

        scrollableText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableText.getViewport().setOpaque(false);
        scrollableText.setOpaque(false);
        scrollableText.setBorder(null);
        panel.add(scrollableText);
        display.add(panel);

        Timer startTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                dispose();
                new GameGUI(gameActions,username,gameID,0);
            }
        };
        startTimer.schedule(task,3500);
    }
}