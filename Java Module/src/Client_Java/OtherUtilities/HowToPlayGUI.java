package Client_Java.OtherUtilities;

import Client_Java.GUITools.MoveListener;
import Client_Java.GUITools.StyledButton;

import javax.swing.*;
import java.awt.*;

public class HowToPlayGUI extends JFrame {

    public HowToPlayGUI(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x282828));
        panel.setPreferredSize(new Dimension(840, 521));

        JTextArea text = new JTextArea();
        text.setText("\nWELCOME TO WORDY!\nUpon starting a new game, you will be automatically added to an existing\n" +
                "lobby with other players. If no lobbies exist, you will be made the host of\n" +
                "one. If 10 seconds pass and you are the sole player in the lobby, you will \n" +
                "be returned to the Main Menu. The game will only start if there is at least \n" +
                "one other player in the lobby. \n" +
                "\n" +
                "At the start of the game, you will be presented with 17 random letters\n" +
                "(with 5-7 vowels), and all players must try to send valid words using these\n" +
                "letters. All words must be at least 5 letters long, and can repeat letters from\n" +
                "the 17 random letters. Submit as many words as you can within 10 seconds,\n" +
                "and the player who submitted the longest word will be the winner as soon as\n" +
                "the round ends. No winners will be declared in the event of a tie, or if no \n" +
                "player submitted a valid word.\n" +
                "\n" +
                "The first player to win 3 rounds will be declared the eventual winner.\n" +
                "Good luck!\n");
        text.setFont(new Font("Calibri", Font.BOLD, 20));
        text.setPreferredSize(new Dimension(800, 450));
        text.setColumns(20);
        text.setForeground(new Color(0xFFFFFF));
        text.setOpaque(false);
        panel.add(text);

        JButton back = new JButton("Return to Main Menu");
        back.setUI(new StyledButton());
        back.setForeground(Color.BLACK);
        back.setBackground(new Color(0xCBC9C9));
        back.addActionListener(e -> this.dispose());
        panel.add(back);

        MoveListener listener = new MoveListener(this);
        panel.addMouseListener(listener);
        panel.addMouseMotionListener(listener);

        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.add(panel);
        this.setTitle("How To Play");
        setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
