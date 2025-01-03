package presentation;

import javax.swing.*;
import java.awt.*;

class ColorButton extends JButton {
    private Color bgColor = Color.decode("#144806");

    public ColorButton(Icon icon) {
        super(icon);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public void setBackgroundColor(Color color) {
        this.bgColor = color;
        repaint();
    }
}
