package Client_Java.Game;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;
import Client_Java.OtherUtilities.CreditsGUI;
import Client_Java.OtherUtilities.HowToPlayGUI;
import Client_Java.OtherUtilities.MenuWinLBoards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PlayerMenuGUI extends JFrame {
    public JLabel display;
    public ImageIcon background;
    MenuButton startGame;

    public PlayerMenuGUI(GameActions gameActions, String username) throws IOException {
        background = new ImageIcon(getClass().getResource("res/clientMenu.gif"));
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


        JLabel title = new JLabel(" WORDY: The Game", SwingConstants.LEFT);
        title.setOpaque(true);
        title.setBounds(0, 0, 500,50);
        title.setFont(new Font("Agency FB", Font.BOLD,40));
        title.setForeground(new Color(0xFFF8ED));
        title.setBackground(new Color(0x14212a));
        display.add(title);

        JLabel user = new JLabel(" Logged In As: "+username, SwingConstants.LEFT);
        user.setOpaque(true);
        user.setBounds(0, 491, 265,30);
        user.setFont(new Font("Agency FB", Font.BOLD,20));
        user.setForeground(new Color(0xFFF8ED));
        user.setBackground(new Color(0x15222b));
        display.add(user);

        startGame = new MenuButton("NEW GAME", 30);
        startGame.setHorizontalAlignment(SwingConstants.RIGHT);
        startGame.setBounds(display.getWidth()-350, display.getHeight()-300, 340, 60);
        startGame.setForeground(new Color(0xFFF8ED));
        startGame.addActionListener(e -> {
            try {
                new PlayerLobbyGUI(gameActions,username);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            this.dispose();
        });
        display.add(startGame);

        MenuButton leaderboards = new MenuButton("LEADERBOARDS", 30);
        leaderboards.setHorizontalAlignment(SwingConstants.RIGHT);
        leaderboards.setBounds(display.getWidth()-350, startGame.getY()+40, 340, 60);
        leaderboards.setForeground(new Color(0xFFF8ED));
        leaderboards.addActionListener(e -> {
            new MenuWinLBoards(gameActions,username);
            this.dispose();
        });
        display.add(leaderboards);

        MenuButton howToPlay = new MenuButton("HOW TO PLAY", 30);
        howToPlay.setHorizontalAlignment(SwingConstants.RIGHT);
        howToPlay.setBounds(display.getWidth()-350, leaderboards.getY()+40, 340, 60);
        howToPlay.setForeground(new Color(0xFFF8ED));
        howToPlay.addActionListener(e -> {
            new HowToPlayGUI();
        });
        display.add(howToPlay);

        MenuButton credits = new MenuButton("CREDITS", 30);
        credits.setHorizontalAlignment(SwingConstants.RIGHT);
        credits.setBounds(display.getWidth()-350, howToPlay.getY()+40, 340, 60);
        credits.setForeground(new Color(0xFFF8ED));
        credits.addActionListener(e -> {
            new CreditsGUI();
        });
        display.add(credits);

        MenuButton exit = new MenuButton("EXIT", 30);
        exit.setHorizontalAlignment(SwingConstants.RIGHT);
        exit.setBounds(display.getWidth()-350, credits.getY()+40, 340, 60);
        exit.setForeground(new Color(0xFFF8ED));
        exit.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", null, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                this.dispose();
                gameActions.logout(username);
                JOptionPane.showMessageDialog(null, "Thank you for playing!", "", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        display.add(exit);

        startGame.requestFocus();
    }

    /**
     * Custom button designed for the Main Menu. If a button is selected via mouse/Tab key,the button's text will be highlighted.
     */
    public class MenuButton extends JButton {

        float alpha = 0.5f;
        int fontSize;
        String text;

        /**
         * Creates a button that increases in size when hovered on by the cursor.
         * @param text Text contained within the button.
         * @param fontSize Initial font size. Hovering on the button will increase font size by 15 points.
         */
        public MenuButton(String text, int fontSize) {
            super(text);
            this.fontSize = fontSize;

            this.text = text;
            setFont(new Font("Calibri", Font.BOLD, fontSize));


            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            addMouseListener(new ML());
            addFocusListener(new FL());
        }

        public void turnOff() {
            setForeground(new Color(0xFFF8ED));
            if (hasFocus())
                return;
            setFont(new Font("Calibri", Font.BOLD, fontSize));
            new Thread(() -> {
                for (float i = 1f; i >= .5f; i -= .03f) {
                    setAlpha(i);
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
            }).start();
        }

        public void turnOn() {
            this.getRootPane().setDefaultButton(this);
            setForeground(new Color(0xFFF8ED));
            if (!hasFocus())
                return;
            setFont(new Font("Calibri", Font.BOLD, fontSize + 15));
            new Thread(() -> {
                for (float i = .5f; i <= 1f; i += .03f) {
                    setAlpha(i);
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
            }).start();
        }

        public float getAlpha() {
            return alpha;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
            repaint();

        }

        public void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paintComponent(g2);
        }

        public class FL extends FocusAdapter {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                turnOn();
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                turnOff();
            }
        }

        public class ML extends MouseAdapter {
            public void mouseExited(MouseEvent me) {
                turnOff();
            }

            public void mouseEntered(MouseEvent me) {
                requestFocusInWindow();
                turnOn();
            }
        }
    }
}
