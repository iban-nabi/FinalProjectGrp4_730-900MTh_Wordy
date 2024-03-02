package Client_Java.LogIn;

import CORBA_IDL.GameActions;
import CORBA_IDL.UserNotFoundException;
import CORBA_IDL.UserOnlineException;
import CORBA_IDL.WrongCredentials;
import Client_Java.Game.PlayerMenuGUI;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.swingx.graphics.ShadowRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BackgroundLogin extends javax.swing.JPanel {
    GameActions gameActions;
    LogInGUI logInGUI;
    public BackgroundLogin(LogInGUI guiLogin,GameActions gameActions) {
        this.gameActions=gameActions;
        this.logInGUI=guiLogin;
        initComponents();
        setOpaque(false);
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    private Icon image;

    private void initComponents() {
        panelLogin1 = new PanelLogin();
        JPanel jPanel1 = new JPanel();
        TextField myTextField1 = new TextField();
        PasswordField myPassword1 = new PasswordField();
        ImageAvatar imageAvatar1 = new ImageAvatar();
        Button button1 = new Button();
        JLabel jLabel1 = new JLabel();
        JLabel logInLabel = new JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(600,500));

        myTextField1.setLabelText("Username");
        myPassword1.setLabelText("Password");
        myPassword1.setShowAndHide(true);

        InputMap inputMap = myPassword1.getInputMap(WHEN_FOCUSED);
        ActionMap actionMap = myPassword1.getActionMap();

        KeyStroke enterStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        inputMap.put(enterStroke, enterStroke.toString());
        actionMap.put(enterStroke.toString(), new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    gameActions.login(myTextField1.getText(), myPassword1.getText());
                    try {
                        logInGUI.dispose();
                        new PlayerMenuGUI(gameActions, myTextField1.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (NullPointerException exc) {
                    JOptionPane.showMessageDialog(null, "The username/password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (UserNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "The username " + myTextField1.getText() + " does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (WrongCredentials ex) {
                    JOptionPane.showMessageDialog(null, "Invalid username/password", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (UserOnlineException ex) {
                    JOptionPane.showMessageDialog(null, "The user " + myTextField1.getText() + " is already online! ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        imageAvatar1.setBorderSize(2);
        imageAvatar1.setBorderSpace(2);
        imageAvatar1.setGradientColor2(new java.awt.Color(42, 98, 199));
        imageAvatar1.setImage(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("res/icon.jpg"))));

        button1.setBackground(new java.awt.Color(0x3E54AC));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Sign In");
        button1.setFont(new java.awt.Font("sansserif", Font.BOLD, 14));
        button1.addActionListener(e -> {
            try {
                gameActions.login(myTextField1.getText(), myPassword1.getText());
                try {
                    logInGUI.dispose();
                    new PlayerMenuGUI(gameActions, myTextField1.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }catch (NullPointerException exc){
                JOptionPane.showMessageDialog(null, "The username/password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }catch (UserNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "The username " + myTextField1.getText() + " does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (WrongCredentials ex) {
                JOptionPane.showMessageDialog(null, "Invalid username/password", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (UserOnlineException ex) {
                JOptionPane.showMessageDialog(null, "The user " + myTextField1.getText() + " is already online! ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        Font logInFont = null;
        try {
            logInFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Client_Java/Login/res/Login.ttf")).deriveFont(48f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Client_Java/Login/res/Login.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        logInLabel.setFont(logInFont);
        logInLabel.setForeground(new java.awt.Color(0,0,0));
        logInLabel.setText("LOG IN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150,150,150)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(logInLabel)
                                        .addComponent(imageAvatar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(myTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                                        .addComponent(myPassword1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap(5, Short.MAX_VALUE))

        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(logInLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addGap(25, 25, 25)
                                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(myTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,50, Short.MAX_VALUE)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE))
        );


        jLabel1.setFont(logInFont);
        jLabel1.setForeground(new java.awt.Color(219, 219, 219));
        jLabel1.setText("W O R D Y");

        javax.swing.GroupLayout panelLogin1Layout = new javax.swing.GroupLayout(panelLogin1);
        panelLogin1.setLayout(panelLogin1Layout);
        panelLogin1Layout.setHorizontalGroup(
                panelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogin1Layout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLogin1Layout.setVerticalGroup(
                panelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogin1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout1 = new javax.swing.GroupLayout(this);
        this.setLayout(layout1);
        layout1.setHorizontalGroup(
                layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout1.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout1.setVerticalGroup(
                layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout1.createSequentialGroup()
                                .addContainerGap(139, Short.MAX_VALUE)
                                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(139, Short.MAX_VALUE))
        );
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        if (image != null) {
            Rectangle size = getAutoSize(image);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(toImage(image), size.getLocation().x, size.getLocation().y, size.getSize().width, size.getSize().height, null);
        }
        int width = getWidth();
        int height = getHeight();
        Area area = new Area(new Rectangle2D.Double(0, 0, width, height));
        Rectangle rec = panelLogin1.getBounds();
        rec.setLocation(rec.x + 10, rec.y + 10);
        rec.setSize(rec.width - 20, rec.height - 20);
        area.subtract(new Area(rec));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(new Color(160, 160, 160));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(graphics);
    }

    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        int x = getWidth() / 2 - (width / 2);
        int y = getHeight() / 2 - (height / 2);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }

    private PanelLogin panelLogin1;
}

class PanelLogin extends javax.swing.JPanel {

    private BufferedImage image;

    public PanelLogin() {
        initComponents();
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        super.paintComponent(graphics);
    }

    private void createImage() {
        int shadowSize = 10;
        int width = getWidth();
        int height = getHeight();
        int space = shadowSize * 2;
        BufferedImage img = new BufferedImage(width - space, height - space, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D img_g2 = image.createGraphics();
        img_g2.drawImage(new ShadowRenderer(shadowSize, 0.7f, new Color(0, 0, 0)).createShadow(img), 0, 0, null);
        img_g2.setComposite(AlphaComposite.Clear);
        img_g2.fillRect(shadowSize, shadowSize, width - space, height - space);
        img_g2.dispose();
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createImage();
    }

    private void initComponents() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }
}

class Button extends JButton {
    private final Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private final Color effectColor = new Color(255, 255, 255);

    public Button() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };
        animator = new Animator(800, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, 5, 5);
        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }
        g2.dispose();
        graphics.drawImage(img, 0, 0, null);
        super.paintComponent(graphics);
    }
}