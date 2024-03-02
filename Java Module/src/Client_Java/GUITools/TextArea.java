package Client_Java.GUITools;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;

public class TextArea extends JTextArea {
    private TextAreaScroll scroll;

    public TextArea() {
        setWrapStyleWord(true);
        setLineWrap(true);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setSelectionColor(new Color(76, 204, 255));
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                showing(false);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                showing(true);
            }
        });

    }

    private void showing(boolean action) {
        TextAreaScroll s = getScroll();
        Animator animator = s.getAnimator();
        float location = s.getAnimateLocation();
        if (animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        animator.setStartFraction(1f - location);
         s.setShow(action);
        location = 1f - location;
         s.setAnimateLocation(location);
        animator.start();
    }

    @Override
    public void setText(String string) {
        if (!getText().equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }

    private TextAreaScroll getScroll() {
        if (scroll == null) {
            Component com = getParent();
            JViewport view = (JViewport) com;
            scroll = (TextAreaScroll) view.getParent();
        }
        return scroll;
    }
}

