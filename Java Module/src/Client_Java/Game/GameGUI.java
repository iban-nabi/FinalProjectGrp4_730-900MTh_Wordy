package Client_Java.Game;

import CORBA_IDL.*;
import Client_Java.GUITools.*;
import Client_Java.GUITools.TextArea;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.JComponent.WHEN_FOCUSED;

public class GameGUI extends JFrame {
    public ImageIcon background;
    public JLabel display;
    public JLabel score;
    public JLabel round;
    public JLabel timer;
    public TextAreaScroll textAreaScroll;
    public TextArea textArea;
    public JButton exitButton;
    public JButton enterButton;
    ScrollBarCustom scroll;
    JScrollPane scrollableText;

    DefaultListModel <String> listOfPlayers = new DefaultListModel<>();
    TimerTask task;
    private boolean exit=false;

    GameActions gameActions;
    int gameID;

    public GameGUI(GameActions gameActions, String username, int gameID, int roundNumber) {
        this.gameActions=gameActions;
        this.gameID=gameID;

        background = new ImageIcon(getClass().getResource("res/Ingame.gif"));
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

        score = new JLabel("WINS: "+gameActions.getNoWinInGame(gameID,username));
        score.setHorizontalAlignment(SwingConstants.RIGHT);
        score.setFont(new Font("Agency FB", Font.BOLD, 70));
        score.setBounds(display.getWidth() - 1300, display.getHeight() - 630, 340, 60);
        score.setForeground(new Color(0xFFF8ED));

        display.add(score);

        AtomicInteger num = new AtomicInteger();
        num.getAndIncrement();
        round = new JLabel("ROUND " + (roundNumber+1)); //not final (for testing only)
        round.setHorizontalAlignment(SwingConstants.RIGHT);
        round.setFont(new Font("Agency FB", Font.BOLD, 70));
        round.setBounds(display.getWidth() - 800, display.getHeight() - 630, 340, 60);
        round.setForeground(new Color(0xFFF8ED));

        display.add(round);

        timer = new JLabel("1");
        timer.setFont(new Font("Agency FB", Font.BOLD, 50));
        timer.setBounds(display.getWidth() - 155, display.getHeight() - 630, 340, 60);

        new Thread(() -> {
            timer.setForeground(new Color(0xFFF8ED));
                for (int x =1 ; x < 12; x++) {
                    if (x == 11)
                        timer.setVisible(false);
                        timer.setText(String.valueOf(x));
                    for (int y = 0; y < (x % 4); y++) {
                        timer.setText(String.valueOf(x));
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
        display.add(timer);


        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(display.getWidth() - 825, display.getHeight() - 508, 495, 245);

        List<Character>charList = new ArrayList<>();
        for(char c : gameActions.getCharacters(gameID)){
            charList.add(c);
        }
        Collections.sort(charList);
        JList<Character> list = new JList<>(charList.toArray(new Character[0]));
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)list.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        list.setFixedCellWidth(27);
        list.setVisibleRowCount(1);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setBounds(display.getWidth() - 805, display.getHeight() - 200, 500, 50);

        list.setFont(new Font("Century Gothic", Font.BOLD, 25));
        list.setBackground(new Color(0, 0, 0,0));
        list.setForeground(new Color(0xF6F1F1));
        
        display.add(list);

        textArea = new TextArea();
        textAreaScroll = new TextAreaScroll();
        textAreaScroll.setLabelText("ENTER YOUR ANSWERS HERE!");
        textArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        textArea.setColumns(17);
        textArea.setRows(1);
        textAreaScroll.setViewportView(textArea);
        textArea.setFont(new Font("Century Gothic", Font.PLAIN, 30));


        InputMap inputMap = textArea.getInputMap(WHEN_FOCUSED);
        ActionMap actionMap = textArea.getActionMap();

        KeyStroke enterStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        inputMap.put(enterStroke, enterStroke.toString());
        actionMap.put(enterStroke.toString(), new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                java.util.Timer startTimer = new Timer();
                try{
                    task.cancel();
                }catch (Exception ignore){}

                int delay=0;

                try {
                    gameActions.sendWord(gameID,username,textArea.getText());
                    textArea.setText("");
                } catch (NoWordException ex) {
                    textAreaScroll.setLabelText("NO SUCH WORD EXISTING! TRY AGAIN.");
                    textAreaScroll.setLineColor(Color.RED);
                    textAreaScroll.setError(true);
                    textArea.setText("");
                    delay=2000;
                } catch (ShortWordException ex) {
                    textAreaScroll.setLabelText("THE WORD IS TOO SHORT! TRY AGAIN.");
                    textAreaScroll.setLineColor(Color.RED);
                    textAreaScroll.setError(true);
                    delay=2000;
                    textArea.setText("");
                } catch (WordRepeatedException ex) {
                    textAreaScroll.setLabelText("REPEATING A WORD IS NOT ALLOWED! TRY AGAIN.");
                    textAreaScroll.setLineColor(Color.RED);
                    textAreaScroll.setError(true);
                    delay=2000;
                    textArea.setText("");
                } catch (InvalidWordException ex) {
                    textAreaScroll.setLabelText("USE THE PROVIDED LETTERS! TRY AGAIN.");
                    textAreaScroll.setLineColor(Color.RED);
                    textAreaScroll.setError(true);
                    delay=2000;
                    textArea.setText("");
                }


                task = new TimerTask() {
                    @Override
                    public void run() {
                        textAreaScroll.setLabelText("ENTER YOUR ANSWERS HERE!");
                        textAreaScroll.setError(false);
                    }
                };
                startTimer.schedule(task,delay);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(340, 340, 340)
                                .addComponent(textAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(540, 540, 540)
                                .addComponent(textAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(500, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);

        enterButton = new JButton();
        enterButton.setText("ENTER");
        enterButton.setFont(new Font("Calibri", Font.BOLD, 20));
        enterButton.setBackground(new Color(0x5D9C59));
        enterButton.setForeground(new Color(0xF6F1F1));
        enterButton.setUI(new StyledButton());
        enterButton.setBounds(display.getWidth() - 430, display.getHeight() - 110, 90, 60);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Timer startTimer = new Timer();
                try{
                    task.cancel();
                }catch (Exception ignore){}

                int delay=0;

                try {
                    gameActions.sendWord(gameID,username,textArea.getText());
                    textArea.setText("");
                } catch (NoWordException ex) {
                   textAreaScroll.setLabelText("NO SUCH WORD EXISTING! TRY AGAIN.");
                   textAreaScroll.setLineColor(Color.RED);
                   textAreaScroll.setError(true);
                   delay=2000;
                   textArea.setText("");
                } catch (ShortWordException ex) {
                   textAreaScroll.setLabelText("THE WORD IS TOO SHORT! TRY AGAIN.");
                   textAreaScroll.setLineColor(Color.RED);
                   textAreaScroll.setError(true);
                   delay=2000;
                   textArea.setText("");
                } catch (WordRepeatedException ex) {
                   textAreaScroll.setLabelText("REPEATING A WORD IS NOT ALLOWED! TRY AGAIN.");
                   textAreaScroll.setLineColor(Color.RED);
                   textAreaScroll.setError(true);
                   delay=2000;
                   textArea.setText("");
                } catch (InvalidWordException ex) {
                   textAreaScroll.setLabelText("USE THE PROVIDED LETTERS! TRY AGAIN.");
                   textAreaScroll.setLineColor(Color.RED);
                   textAreaScroll.setError(true);
                   delay=2000;
                   textArea.setText("");
                }


               task = new TimerTask() {
                   @Override
                   public void run() {
                       textAreaScroll.setLabelText("ENTER YOUR ANSWERS HERE!");
                       textAreaScroll.setError(false);
                   }
               };
               startTimer.schedule(task,delay);
            }
        });
        display.add(enterButton);


        exitButton = new JButton();
        exitButton.setText("EXIT");
        exitButton.setFont(new Font("Calibri", Font.BOLD, 20));
        exitButton.setBackground(new Color(0xD21312));
        exitButton.setForeground(new Color(0xF6F1F1));
        exitButton.setUI(new StyledButton());
        exitButton.setBounds(display.getWidth() - 120, display.getHeight() - 60, 90, 40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit=true;
                gameActions.quitGame(gameID,username);
                dispose();
                try {
                    new PlayerMenuGUI(gameActions,username);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        display.add(exitButton);

        scrollableText = new JScrollPane();
        scroll = new ScrollBarCustom();
        scroll.setOrientation(JScrollBar.HORIZONTAL);
        Thread thread = readMessage(panel);
        thread.start();
        Thread threadRunGame = new Thread(() -> {
            gameActions.runGame(gameID);
        });
        threadRunGame.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(!exit){
            threadFlag=false;
            this.dispose();
            new GameResultGUI(gameActions,username,gameID,(roundNumber+1));
        }

    }



    private void showEnteredWords(JPanel panel){
        JList<String> list = new JList<>(listOfPlayers);
        list.setFixedCellWidth(470);
        list.setBounds(0, display.getHeight() - 508, 495, 245);

        list.setFont(new Font("Agency FB", Font.BOLD, 23));
        list.setBackground(new Color(0, 0, 0,0));
        list.setForeground(new Color(0xF6F1F1));

        scrollableText.setViewportView(list);
        scrollableText.setVerticalScrollBar(new ScrollBarCustom());
        scrollableText.setHorizontalScrollBar(scroll);
        scrollableText.setBorder(new EmptyBorder(20, 3, 3, 3));

        scrollableText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableText.getViewport().setOpaque(false);
        scrollableText.setOpaque(false);
        scrollableText.setBorder(null);
        panel.add(scrollableText);
        display.add(panel);
    }

    boolean threadFlag = true;
    private Thread readMessage(JPanel panel){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while(threadFlag){
                    try{
                        if(!Objects.equals(gameActions.getEnteredWords(gameID)[0], " ")){
                            String[] temp = gameActions.getEnteredWords(gameID);
                            if(listOfPlayers.size() != temp.length){
                                listOfPlayers.clear();
                                for(String s : gameActions.getEnteredWords(gameID)){
                                    listOfPlayers.addElement(s);
                                    showEnteredWords(panel);
                                }
                            }
                        }
                    }catch (Exception ignore){}
                }
            }
        });
    }
}
