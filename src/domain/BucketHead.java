package domain;

import javax.swing.*;
import java.awt.*;

public class BucketHead extends Zombie{
    private JLabel label;
    private JLabel head;
    private boolean attack;
    private int distance;
    private ImageIcon walkingIcon;
    private ImageIcon attackIcon;
    private ImageIcon walkingBucketLessIcon;
    private ImageIcon attackBucketLessIcon;
    private ImageIcon bodyDieIcon;
    private ImageIcon headDieIcon;
    private long lastMovement;
    private long lastAttack;

    public BucketHead(JButton button, JLayeredPane layeredPane, Rectangle[] hitboxs, Plant[] plants, LawnMower lawnMower) {
        super.hp = 800;
        super.cost = 200;
        super.layeredPane = layeredPane;
        super.hitboxs = hitboxs;
        super.plants = plants;
        this.distance = layeredPane.getWidth()/600;
        this.attack = false;
        super.icon = "caminando";
        super.lawnMower = lawnMower;
        this.lastMovement = System.currentTimeMillis();
        this.lastAttack = System.currentTimeMillis();
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/BucketheadZombie.gif"));
        this.walkingIcon = new ImageIcon(gifIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon attackIcon = new ImageIcon(getClass().getResource("/resources/BucketheadZombieAttack.gif"));
        this.attackIcon = new ImageIcon(attackIcon.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon withoutBucket = new ImageIcon(getClass().getResource("/resources/zombie.gif"));
        this.walkingBucketLessIcon = new ImageIcon(withoutBucket.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
        ImageIcon attackWithoutBucket = new ImageIcon(getClass().getResource("/resources/zombieAttack.gif"));
        this.attackBucketLessIcon = new ImageIcon(attackWithoutBucket.getImage().getScaledInstance((int) (button.getSize().getWidth() * 1.3), (int) (button.getSize().getHeight() * 1.3), Image.SCALE_DEFAULT));
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
        layeredPane.repaint();
    }


    public void update() {
        if (hp>100){
            int position = -1;
            for (int i = 0; i<hitboxs.length && position<0;i++){
                if (hitboxs[i] != null){
                    if (hitboxs[i].intersects(hitbox)){
                        attack = true;
                        position = i;
                    }
                }
            }

            if (attack && !icon.equals("atacando")) {
                label.setIcon(attackIcon);
                icon = "atacando";
            } else if ((!attack) && !icon.equals("caminando")) {
                label.setIcon(walkingIcon);
                icon = "caminando";
            }

            long currentTime;
            if (position >= 0) {
                currentTime = System.currentTimeMillis();
                if (currentTime-lastAttack >= 500){
                    attackZombie(position);
                    lastAttack = currentTime;
                }
            } else {
                currentTime = System.currentTimeMillis();
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
        else if (hp>0){
            int position = -1;
            for (int i = 0; i<hitboxs.length && position<0;i++){
                if (hitboxs[i] != null){
                    if (hitboxs[i].intersects(hitbox)){
                        attack = true;
                        position = i;
                    }
                }
            }

            if (attack && !icon.equals("atacandoSinCubeta")) {
                label.setIcon(attackBucketLessIcon);
                icon = "atacandoSinCubeta";
            } else if ((!attack) && !icon.equals("caminandoSinCubeta")) {
                label.setIcon(walkingBucketLessIcon);
                icon = "caminandoSinCubeta";
            }

            long currentTime;
            if (position >= 0) {
                currentTime = System.currentTimeMillis();
                if (currentTime-lastAttack >= 500){
                    attackZombie(position);
                    lastAttack = currentTime;
                }
            } else {
                currentTime = System.currentTimeMillis();
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

    public void die(){
        deadTime = System.currentTimeMillis();
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
