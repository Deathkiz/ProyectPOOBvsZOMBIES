package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class POOBvsZOMBIES {
    private JLayeredPane layeredPane;
    private Plant[][] plants;
    private ArrayList<Zombie>[] zombies;
    private int suns;
    private int brains;
    private Rectangle[][] plantHitboxs;
    private ArrayList<Pea>[] peas;
    private LawnMower[] LawnMowers;
    private long StartTime;

    public POOBvsZOMBIES(int suns, int brains, ArrayList<JButton> positions,JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
        plants = new Plant[5][8];
        LawnMowers = new LawnMower[5];
        zombies = (ArrayList<Zombie>[]) new ArrayList[5];
        peas = (ArrayList<Pea>[]) new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            zombies[i] = new ArrayList<>();
            peas[i] = new ArrayList<>();
        }

        this.suns = suns;
        this.brains = brains;
        plantHitboxs = new Rectangle[5][8];
        ArrayList<Integer> buttonPosition = new ArrayList<>(Arrays.asList(0, 9, 18, 27, 36));
        for (int i = 0; i < 5; i++) {
            JButton button = positions.get(buttonPosition.get(i));
            LawnMowers[i] = new LawnMower(layeredPane,button,zombies[i]);
        }

        // Crear un hilo por fila para manejar tanto plantas como zombies
        for (int i = 0; i < 5; i++) {
            int rowIndex = i; // Necesario para usar en lambda
            Thread rowThread = new Thread(() -> handleRow(rowIndex));
            rowThread.start();
        }
    }

    private void handleRow(int rowIndex) {
        while (true) {
            // Manejar las plantas de la fila
            for (Plant plant : plants[rowIndex]) {
                if (plant != null) {
                    // Lógica para actualizar la planta (disparar, regenerar, etc.)
                    plant.update();
                }
            }

            // Manejar los zombies de la fila
            for (Zombie zombie : zombies[rowIndex]) {
                if (zombie != null) {
                    zombie.update();
                    long currentTime = System.currentTimeMillis();
                    if (zombie.getHp() <= 0 && !(zombie.getIcon().equals("dead"))){
                        zombie.die();
                    }
                    else if (zombie.getHp() <= 0 && currentTime-zombie.getDeadTime() > 2400){
                        zombie.remove();
                    }
                }
            }
            ArrayList<Pea> eliminate = new ArrayList<>();
            for (Pea p: peas[rowIndex]){
                if (p != null) {
                    if (p.outOfBonds()){
                        eliminate.add(p);
                    }
                    else {
                        p.forward();
                    }
                }
            }
            for (Pea p: eliminate){
                p.remove();
                peas[rowIndex].remove(p);
            }

            LawnMowers[rowIndex].update();


            try {
                Thread.sleep(10); // Ajusta el intervalo de actualización según sea necesario
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void plantDamage(int row, int column, int damage){plants[row][column].damage(damage);}

    public void zombieDamage(int damage){}

    public void createPlant(String type, int row, int column,JButton button){
        if (type.equals("peashooter")){
            plants[row][column] = new Peashooter(button,layeredPane,peas[row],zombies[row]);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
        }
        else if (type.equals("sunflower")){
            plants[row][column] = new Sunflower(button, layeredPane);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
        }

        else if (type.equals("wall-nut")){
            plants[row][column] = new Wallnut(button, layeredPane);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
        }


    }

    public void deletePlant(int row, int column){
        plants[row][column] = null;
    }

    public void createZombie(String type, int row, JButton button){
        if (type.equals("basic")){
            zombies[row].add(new Basic(button,layeredPane, plantHitboxs[row],plants[row],LawnMowers[row]));
        }
        else if (type.equals("coneHead")){
            //zombies[row].add(new ConeHead(button,layeredPane));
        }
        else if (type.equals("bucketHead")){
            //zombies[row].add(new BucketHead(button,layeredPane));
        }
        else if (type.equals("brainstein")){
            //zombies[row].add(new Brainstein(button,layeredPane));
        }
        else if (type.equals("ECIZombie")){
            //zombies[row].add(new ECIZombie(button,layeredPane));
        }

    }
}
