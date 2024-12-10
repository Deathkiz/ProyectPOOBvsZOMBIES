package domain;

import javax.swing.*;
import java.awt.*;

public class ConeHead extends Zombie{
    private JLabel label;
    private JLabel head;
    private boolean attack;
    private int distance;
    private ImageIcon walkingIcon;
    private ImageIcon attackIcon;
    private ImageIcon walkingConeLessIcon;
    private ImageIcon attackConeLessIcon;
    private ImageIcon bodyDieIcon;
    private ImageIcon headDieIcon;
    private long lastMovement;
    private long lastAttack;

    public ConeHead(JButton button, JLayeredPane layeredPane, Rectangle[] hitboxs, Plant[] plants, LawnMower lawnMower) {
        super.hp = 380;
        super.layeredPane = layeredPane;
        super.hitboxs = hitboxs;
        super.plants = plants;
        this.distance = layeredPane.getWidth()/600;
        this.attack = false;
        super.icon = "caminando";
        super.lawnMower = lawnMower;
        this.lastMovement = System.currentTimeMillis();
        this.lastAttack = System.currentTimeMillis();
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/ConeheadZombie.gif"));
        this.walkingIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon attackIcon = new ImageIcon(getClass().getResource("/resources/ConeheadZombieAttack.gif"));
        this.attackIcon = new ImageIcon(attackIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon withoutBucket = new ImageIcon(getClass().getResource("/resources/zombie.gif"));
        this.walkingConeLessIcon = new ImageIcon(withoutBucket.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon attackWithoutBucket = new ImageIcon(getClass().getResource("/resources/zombieAttack.gif"));
        this.attackConeLessIcon = new ImageIcon(attackWithoutBucket.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon dieIcon = new ImageIcon(getClass().getResource("/resources/zombieDie.gif"));
        this.bodyDieIcon = new ImageIcon(dieIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon headDieIcon = new ImageIcon(getClass().getResource("/resources/zombieHead.gif"));
        this.headDieIcon = new ImageIcon(headDieIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));

        this.label = new JLabel(walkingIcon);
        Point buttonLocationOnScreen = button.getLocationOnScreen();
        Point layeredPaneLocationOnScreen = layeredPane.getLocationOnScreen();
        int relativeY = buttonLocationOnScreen.y - layeredPaneLocationOnScreen.y;
        int width = walkingIcon.getIconWidth();
        int height = walkingIcon.getIconHeight();
        int y = (int) (relativeY - height / 3);
        label.setBounds(layeredPane.getWidth(), y, width, height);
        super.hitbox = new Rectangle((int) (layeredPane.getWidth() + width * 0.7), relativeY, (int) width / 5, button.getHeight());
        layeredPane.add(label, JLayeredPane.DRAG_LAYER);
    }


    public void update(long currentTime) {
        if (hp>0){
            int position = -1;
            for (int i = 0; i<hitboxs.length && position<0;i++){
                if (hitboxs[i] != null){
                    if (hitboxs[i].intersects(hitbox)){
                        attack = true;
                        position = i;
                    }
                }
            }

            if (hp>100){
                if (attack && !icon.equals("atacando")) {
                    label.setIcon(attackIcon);
                    icon = "atacando";
                } else if ((!attack) && !icon.equals("caminando")) {
                    label.setIcon(walkingIcon);
                    icon = "caminando";
                }
            }
            else {
                if (attack && !icon.equals("atacandoSinCubeta")) {
                    label.setIcon(attackConeLessIcon);
                    icon = "atacandoSinCubeta";
                } else if ((!attack) && !icon.equals("caminandoSinCubeta")) {
                    label.setIcon(walkingConeLessIcon);
                    icon = "caminandoSinCubeta";
                }
            }
            if (position >= 0) {
                if (currentTime-lastAttack >= 500){
                    attackZombie(position);
                    lastAttack = currentTime;
                }
            } else {
                if (currentTime-lastMovement >= 100){
                    movement(distance);
                    lastMovement = currentTime;
                }
            }

            if (lawnMower!= null){
                if (hitbox.intersects(lawnMower.getHitbox())){
                    lawnMower.activate();
                }
            }
            attack = false;
        }
    }

    public void movement(long LONG){
        hitbox.setLocation((int) (hitbox.x-LONG),hitbox.y);
        label.setBounds((int) (label.getX()-LONG),label.getY(), (int) label.getSize().getWidth(), (int) label.getSize().getHeight());
        layeredPane.repaint();
    }
    public int getX(){
        return label.getX();
    }

    public void attackZombie(int position){
        plants[position].damage(100);
    }

    public void die(long currentTime){
        deadTime = currentTime;
        hitbox = new Rectangle(0,0,0,0);
        label.setIcon(bodyDieIcon);
        icon = "dead";
        head =new JLabel();
        head.setBounds(label.getBounds());
        head.setIcon(headDieIcon);
        layeredPane.add(head,JLayeredPane.DRAG_LAYER);
    }

    public void remove() {
        if (label != null) {
            layeredPane.remove(label);
        }
        if (head != null) {
            layeredPane.remove(head);
        }
        layeredPane.repaint();
    }
}