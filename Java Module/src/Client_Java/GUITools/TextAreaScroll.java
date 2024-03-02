package Client_Java.GUITools;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;


public class TextAreaScroll extends JScrollPane {
    private final Animator animator;
    private boolean animateHinText = true;
    private float animateLocation;
    private boolean show;
    private boolean mouseOver;
    private String labelText = "Label";
    private Color lineColor = new Color(3, 155, 216);
    private boolean error = false;

    public TextAreaScroll() {
        setVerticalScrollBar(new ScrollBarCustom());
        ScrollBarCustom scroll = new ScrollBarCustom();
        scroll.setOrientation(JScrollBar.HORIZONTAL);
        setHorizontalScrollBar(scroll);
        setBorder(new EmptyBorder(20, 0, 0, 0));
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                setAnimateLocation(fraction);
                repaint();
            }
        };
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        this.error=false;
    }

    @Override
    public void paint(Graphics grphcs) {
        try{
            super.paint(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            int width = getWidth();
            int height = getHeight();
            if (mouseOver) {
                g2.setColor(lineColor);
            } else {
                g2.setColor(new Color(150, 150, 150));
            }
            g2.fillRect(2, height - 1, width - 4, 1);
            createHintText(g2);
            createLineStyle(g2);
            g2.dispose();
        }catch (IllegalStateException ignore){}
    }

    private void createHintText(Graphics2D g2) {
        try{
            Insets in = getInsets();
            if(!error){
                g2.setColor(new Color(150, 150, 150));
            }else{
                g2.setColor(Color.RED);
            }
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(labelText, g2);
            double height = in.top;
            double textY = (height - r2.getHeight()) / 2;
            double size;
            if (animateHinText) {
                if (show) {
                    size = 18 * (1 - animateLocation);
                } else {
                    size = 18 * animateLocation;
                }
            } else {
                size = 18;
            }
            g2.drawString(labelText, in.right, (int) (in.top + textY + ft.getAscent() - size));
        }catch (IllegalStateException ignore){}
    }

    private void createLineStyle(Graphics2D g2) {
        try{
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            double size;
            if (show) {
                size = width * (1 - animateLocation);
            } else {
                size = width * animateLocation;
            }
            double x = (width - size) / 2;
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }catch (IllegalStateException ignore){}

    }

    protected Animator getAnimator() {
        return animator;
    }

    public float getAnimateLocation() {
        return animateLocation;
    }

    public void setAnimateLocation(float animateLocation) {
        this.animateLocation = animateLocation;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
        repaint();
    }

    public boolean isAnimateHinText() {
        return animateHinText;
    }

    public void setAnimateHinText(boolean animateHinText) {
        this.animateHinText = animateHinText;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setError(boolean error){
        this.error=error;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
