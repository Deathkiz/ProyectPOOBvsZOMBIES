package domain;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LawnMower {
    private JLabel label;
    private ImageIcon image;
    private JLayeredPane layeredPane;
    private int distance;
    private boolean outOfBonds;
    private boolean active;
    private Rectangle hitbox;
    private ArrayList<Zombie> zombies;


    public LawnMower(JLayeredPane layeredPane,JButton button, ArrayList<Zombie> zombies) {
        this.layeredPane = layeredPane;
        this.outOfBonds = false;
        this.zombies = zombies;
        this.hitbox = new Rectangle();

        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();

        // Calcular la posición relativa del botón dentro del JLayeredPane
        int relativeX = buttonLocationOnScreen.x - layeredPaneLocationOnScreen.x;
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        distance = layeredPane.getWidth()/100;

        Dimension buttonSize = button.getSize();

        // Configurar la imagen y el JLabel
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/LawnMower.png"));
        int labelWidth = (int) (buttonSize.getWidth()/2);
        int labelHeight = (int) (buttonSize.getHeight()/2);
        image = new ImageIcon(gifIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_DEFAULT));
        label = new JLabel(image);

        int labelX = relativeX - labelWidth;
        int labelY = relativeY + (buttonSize.height / 2);

        // Configurar las dimensiones y posición del JLabel
        label.setBounds(labelX, labelY, labelWidth, labelHeight);
        hitbox.setBounds(labelX, labelY, labelWidth, labelHeight);
        layeredPane.add(label, JLayeredPane.POPUP_LAYER);
    }

    public void update(long currentTime){
        if (active){
            int currentX = label.getX();
            int currentY = label.getY();
            // Calcular la nueva posición (mover hacia la derecha)
            int newX = currentX + distance;
            // Actualizar la posición del JLabel
            label.setBounds(newX, currentY, label.getWidth(), label.getHeight());
            hitbox.setBounds(newX,hitbox.y,hitbox.width,hitbox.height);
            layeredPane.repaint();

            for (Zombie zombie: zombies){
                Rectangle zombieHitbox = zombie.getHitbox();
                if (hitbox.intersects(zombieHitbox)){
                    zombie.setHp(0);
                    zombie.die(currentTime);
                }
            }

            if (label.getX() > layeredPane.getWidth()){
                outOfBonds = true;
            }
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void activate(){
        active=true;
    }

    public void remove(){
        SwingUtilities.invokeLater(() -> {
            layeredPane.remove(label);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public boolean getOutOfBonds(){return outOfBonds;}
}
