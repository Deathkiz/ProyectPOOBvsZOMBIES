package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class POOBvsZOMBIES {
    private JLayeredPane layeredPane;
    private Plant[][] plants;
    private Rectangle[][] plantHitboxs;
    private ArrayList<Zombie>[] zombies;
    private ArrayList<Projectile>[] projectiles;
    private ArrayList<Collectable>[] collectables;
    private LawnMower[] LawnMowers;
    private int[][] rows;
    private int maxWidth;
    private int suns;
    private int brains;
    private final long autoGenerate = 10000;
    private long lastgenerate;
    private volatile boolean paused;
    private volatile boolean gameOver;

    public POOBvsZOMBIES(int suns, int brains, ArrayList<JButton> positions,JLayeredPane principalPane) {
        this.layeredPane = principalPane;
        this.gameOver = false;
        this.paused = false;
        plants = new Plant[5][8];
        LawnMowers = new LawnMower[5];
        collectables = (ArrayList<Collectable>[]) new ArrayList[5];
        zombies = (ArrayList<Zombie>[]) new ArrayList[5];
        projectiles = (ArrayList<Projectile>[]) new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            zombies[i] = new ArrayList<>();
            projectiles[i] = new ArrayList<>();
            collectables[i] = new ArrayList<>();
        }
        lastgenerate = System.currentTimeMillis();

        this.suns = suns;
        this.brains = brains;
        plantHitboxs = new Rectangle[5][8];
        ArrayList<Integer> buttonPosition = new ArrayList<>(Arrays.asList(0, 9, 18, 27, 36));
        for (int i = 0; i < 5; i++) {
            JButton button = positions.get(buttonPosition.get(i));
            LawnMowers[i] = new LawnMower(layeredPane, button, zombies[i]);
        }
    }

    public void update(long currentTime){
        for (int row = 0; row<5 ; row++){
            handleRow(row,currentTime);
        }
        if (currentTime - lastgenerate > autoGenerate) {
            suns += 25;
            brains += 50;
            lastgenerate = currentTime;
        }
    }

    public void handleRow(int rowIndex,long currentTime) {
        //plantas
        ArrayList<int[]> eliminatePlants = new ArrayList<>();
        for (int i = 0; i < plants[rowIndex].length; i++) {
            Plant plant = plants[rowIndex][i];
            if (plant != null) {
                plant.update(currentTime);
                if (plant.getHp() <= 0) {
                    plant.die();
                    eliminatePlants.add(new int[]{rowIndex, i}); // Agregar la posición de la planta a eliminar
                }
            }
        }
        for (int[] position : eliminatePlants) {
            plants[position[0]][position[1]] = null;
            plantHitboxs[position[0]][position[1]]=null;
        }

        //zombies
        ArrayList<Zombie> eliminateZombie = new ArrayList<>();
        for (Zombie zombie : zombies[rowIndex]) {
            if (zombie != null) {
                zombie.update(currentTime);
                if (zombie.getHp() <= 0 && !(zombie.getIcon().equals("dead"))) {
                    zombie.die(currentTime);
                } else if (zombie.getHp() <= 0 && currentTime - zombie.getDeadTime() > 2000) {
                    zombie.remove();
                    eliminateZombie.add(zombie);
                }
            }
        }
        zombies[rowIndex].removeAll(eliminateZombie);

        //proyectiles
        ArrayList<Projectile> projectileToEliminate = new ArrayList<Projectile>();
        for (Projectile projectile : projectiles[rowIndex]) {
            if (projectile != null) {
                if (projectile.isOutOfBonds()) {
                    projectileToEliminate.add(projectile);
                } else {
                    projectile.forward();
                }
            }
        }
        projectiles[rowIndex].removeAll(projectileToEliminate);

        //Colectables
        ArrayList<Collectable> collectablesToRemove = new ArrayList<>();
        for (Collectable collectable : collectables[rowIndex]) {
            if (collectable.isActive()) {
                collectable.moveToPosition();
                if (collectable.isInPosition()) {
                    collectablesToRemove.add(collectable);
                    collectable.remove();
                    int value = collectable.getValue();
                    if (collectable instanceof Sun){
                        suns+=value;
                    }
                    else {
                        brains+=value;
                    }
                }
            }
        }
        collectables[rowIndex].removeAll(collectablesToRemove);

        //lawnmowers
        if (LawnMowers[rowIndex] != null) {
            if (LawnMowers[rowIndex].getOutOfBonds()) {
                LawnMowers[rowIndex].remove();
                LawnMowers[rowIndex] = null;
            } else {
                LawnMowers[rowIndex].update();
            }
        }
    }

    public void createPlant(String type, int row, int column,JButton button){
        if (type.equals("peashooter") && suns >= 100){
            plants[row][column] = new Peashooter(button,layeredPane, projectiles[row],zombies[row]);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
            suns -= 100;
        }
        else if (type.equals("sunflower") && suns >= 50){
            plants[row][column] = new Sunflower(button, layeredPane, collectables[row]);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
            suns -= 50;
        }

        else if (type.equals("wall-nut") && suns >= 50){
            plants[row][column] = new Wallnut(button, layeredPane);
            plantHitboxs[row][column] = plants[row][column].getHitbox();
            suns -= 50;
        }
        else if (type.equals("potatoMine") && suns >= 25){
            //plants[row][column] = new PotatoMine(button, layeredPane);
            //plantHitboxs[row][column] = plants[row][column].getHitbox();
            //suns -= 25;
        }
        else if (type.equals("ECIPlant") && suns >= 75){
            //plants[row][column] = new ECIPlant(button, layeredPane);
            //plantHitboxs[row][column] = plants[row][column].getHitbox();
            //suns -= 75;
        }
    }


    public void createZombie(String type, int row, JButton button){
        if (type.equals("basic") && brains >= 100) {
            zombies[row].add(new Basic(button, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
            brains -= 100;
        }
        else if (type.equals("coneHead") && brains>=150){
            zombies[row].add(new ConeHead(button,layeredPane, plantHitboxs[row],plants[row],LawnMowers[row]));
            brains -= 150;
        }
        else if (type.equals("bucketHead") && brains>=200){
            zombies[row].add(new BucketHead(button,layeredPane, plantHitboxs[row],plants[row],LawnMowers[row]));
            brains -= 200;
        }
        else if (type.equals("brainstein") && brains>=50){
            zombies[row].add(new Brainstein(button,layeredPane,collectables[row]));
            brains -= 200;
        }
        else if (type.equals("ECIZombie") && brains>=250){
            //zombies[row].add(new BucketHead(button,layeredPane, plantHitboxs[row],plants[row],LawnMowers[row]));
            //brains -= 200;
        }
    }

    public void sumSuns(int sunsGot) {
        suns += sunsGot;
    }

    public void sumBrains(int brainsGot) {
        brains += brainsGot;
    }

    public int getSuns() {
        return suns;
    }

    public int getBrains() {
        return brains;
    }

    public void clear(){
        for (LawnMower lawnMower : LawnMowers){
            lawnMower.remove();
        }
        for (ArrayList<Zombie> rowZombies:zombies){
            for (Zombie zombie:rowZombies){
                zombie.remove();
            }
        }
        for (ArrayList<Collectable> rowCollectables:collectables){
            for (Collectable collectable:rowCollectables){
                collectable.remove();
            }
        }

        for (ArrayList<Projectile> rowProjectiles:projectiles){
            for (Projectile projectile:rowProjectiles){
                projectile.remove();
            }
        }
    }
}
