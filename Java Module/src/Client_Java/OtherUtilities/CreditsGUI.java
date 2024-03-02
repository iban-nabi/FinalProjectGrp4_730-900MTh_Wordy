package Client_Java.OtherUtilities;

import Client_Java.GUITools.MoveListener;
import Client_Java.GUITools.StyledButton;

import javax.swing.*;
import java.awt.*;

public class CreditsGUI extends JFrame {

    public CreditsGUI(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x282828));
        panel.setPreferredSize(new Dimension(840, 521));

        JTextArea text = new JTextArea();
        text.setText("\nSaint Louis University\nSchool of Accountancy, Management, Computing, and Information Sciences\n" +
                "\nIT222L: Integrative Technologies\nFinal Laboratory Group Project\n\n"+
                "Group 4\nAromin, Eddyson Tristan\n" +
                "Arzadon, Maxwell John, A. \n" +
                "Del Rosario, Carliah Beatriz, M. \n" +
                "Lalwet, Carl Joshua, A. \n" +
                "Masaba, Jessalyn Mae, G.\n" +
                "Paguyo, Jan Ivan Ezra, D. \n" +
                "");
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
