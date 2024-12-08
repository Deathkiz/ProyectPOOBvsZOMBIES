package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class POOBvsZOMBIES {
    private Plant[][] plants;
    private ArrayList<Zombie>[] zombies;
    private int suns;
    private int brains;
    private Rectangle[][] plantHitboxs;
    private ArrayList<pea>[] peas;

    public POOBvsZOMBIES(int suns, int brains) {
        plants = new Plant[5][8];
        zombies = (ArrayList<Zombie>[]) new ArrayList[5];
        peas = (ArrayList<pea>[]) new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            zombies[i] = new ArrayList<>();
            peas[i] = new ArrayList<>();
        }

        this.suns = suns;
        this.brains = brains;
        plantHitboxs = new Rectangle[5][8];

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
                    // Lógica para mover o atacar con los zombies
                    zombie.update();
                }
            }
            ArrayList<pea> eliminate = new ArrayList<>();
            for (pea p: peas[rowIndex]){
                if (p != null) {
                    if (p.outOfBonds()){
                        eliminate.add(p);
                    }
                    else {
                        p.forward();
                    }
                }
            }
            for (pea p: eliminate){
                p.remove();
                peas[rowIndex].remove(p);
            }


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

    public void createPlant(String type, int row, int column,JButton button,JLayeredPane layeredPane){
        if (type.equals("peashooter")){
            plants[row][column] = new Peashooter(button,layeredPane,peas[row]);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
        }
        else if (type.equals("sunflower")){
            plants[row][column] = new Sunflower(button, layeredPane);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
        }


    }

    public void deletePlant(int row, int column){
        plants[row][column] = null;
    }

    public void createZombie(String type, int row, JButton button,JLayeredPane layeredPane){
        if (type.equals("basic")){
            zombies[row].add(new Basic(button,layeredPane, plantHitboxs[row],plants[row]));
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
