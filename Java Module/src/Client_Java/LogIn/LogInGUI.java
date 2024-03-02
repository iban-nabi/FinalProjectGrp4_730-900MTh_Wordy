package Client_Java.LogIn;

import CORBA_IDL.GameActions;
import Client_Java.GUITools.MoveListener;

import javax.swing.*;
import java.util.Objects;

public class LogInGUI extends JFrame {
    public BackgroundLogin backgroundLogin1;
    
    public LogInGUI(GameActions gameActions) {
        backgroundLogin1 = new BackgroundLogin(this,gameActions);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        MoveListener listener = new MoveListener(this);
        backgroundLogin1.addMouseListener(listener);
        backgroundLogin1.addMouseMotionListener(listener);
        backgroundLogin1.setImage(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("res/backgroundLogin.png"))));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundLogin1, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundLogin1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(null);
        
    }
}
