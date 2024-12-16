package domain;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class POOBvsZOMBIES implements Serializable {
    private JLayeredPane layeredPane;
    private ArrayList<JButton> positions;
    private boolean[] usagePlants;
    private boolean[] usageZombies;
    private Plant[][] plants;
    private Rectangle[][] plantHitboxs;
    private ArrayList<Zombie>[] zombies;
    private ArrayList<Projectile>[] projectiles;
    private ArrayList<Collectable>[] collectables;
    private LawnMower[] LawnMowers;
    private int suns;
    private int brains;
    private final long autoGenerate = 10000;
    private long lastgenerate;
    private long currentTime;
    private boolean endGame;
    private String resultMatch = null;
    private long lastHorde;
    private long startTime;
    private long pauseTime;
    private ZombieMachine zombieMachine;
    private PlantMachine plantMachine;

    public POOBvsZOMBIES(int suns, int brains, ArrayList<JButton> positions,JLayeredPane principalPane, boolean[] usagePlants, boolean[] usageZombies) {
        this.layeredPane = principalPane;
        this.usagePlants = usagePlants;
        this.usageZombies = usageZombies;
        this.positions = positions;
        this.endGame = false;
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
        lastHorde = System.currentTimeMillis();
        this.startTime = System.currentTimeMillis();
        this.suns = suns;
        this.brains = brains;
        plantHitboxs = new Rectangle[5][8];
        ArrayList<Integer> buttonPosition = new ArrayList<>(Arrays.asList(0, 9, 18, 27, 36));
        for (int i = 0; i < 5; i++) {
            JButton button = positions.get(buttonPosition.get(i));
            LawnMowers[i] = new LawnMower(layeredPane, button, zombies[i]);
        }
        zombieMachine = null;
    }

    public POOBvsZOMBIES(int suns, int brains, ArrayList<JButton> positions,JLayeredPane principalPane, boolean[] usagePlants, boolean[] usageZombies, int modeZombies) {
        this.layeredPane = principalPane;
        this.usagePlants = usagePlants;
        this.usageZombies = usageZombies;
        this.positions = positions;
        this.endGame = false;
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
        lastHorde = System.currentTimeMillis();

        this.suns = suns;
        this.brains = brains;
        plantHitboxs = new Rectangle[5][8];
        ArrayList<Integer> buttonPosition = new ArrayList<>(Arrays.asList(0, 9, 18, 27, 36));
        for (int i = 0; i < 5; i++) {
            JButton button = positions.get(buttonPosition.get(i));
            LawnMowers[i] = new LawnMower(layeredPane, button, zombies[i]);
        }
        zombieMachine = new ZombieMachine(modeZombies,this);
    }

    public POOBvsZOMBIES(int suns, int brains, ArrayList<JButton> positions,JLayeredPane principalPane, boolean[] usagePlants, boolean[] usageZombies, int modeZombies,int modePlants) {
        this.layeredPane = principalPane;
        this.usagePlants = usagePlants;
        this.usageZombies = usageZombies;
        this.positions = positions;
        this.endGame = false;
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
        lastHorde = System.currentTimeMillis();

        this.suns = suns;
        this.brains = brains;
        plantHitboxs = new Rectangle[5][8];
        ArrayList<Integer> buttonPosition = new ArrayList<>(Arrays.asList(0, 9, 18, 27, 36));
        for (int i = 0; i < 5; i++) {
            JButton button = positions.get(buttonPosition.get(i));
            LawnMowers[i] = new LawnMower(layeredPane, button, zombies[i]);
        }
        zombieMachine = new ZombieMachine(modeZombies,this);
        plantMachine = new PlantMachine(modePlants,this);
    }

    public void update(long currentTime){
        this.currentTime = currentTime;
        createHordeZombie(currentTime);
        for (int row = 0; row<5 ; row++){
            handleRow(row,currentTime);
        }
        if (currentTime - lastgenerate > autoGenerate) {
            suns += 25;
            brains += 50;
            lastgenerate = currentTime;
        }
        if (zombieMachine != null){
            zombieMachine.makeDecision(brains,currentTime);
        }
        if (plantMachine != null){
            plantMachine.makeDecision(suns);
        }
    }

    public void handleRow(int rowIndex,long currentTime) {
        //plantas
        ArrayList<int[]> eliminatePlants = new ArrayList<>();
        for (int i = 0; i < plants[rowIndex].length; i++) {
            Plant plant = plants[rowIndex][i];
            if (plant != null) {
                plant.update(currentTime,suns);
                if (plant.getHp() <= 0) {
                    plant.die();
                    eliminatePlants.add(new int[]{rowIndex, i});
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
                if (zombie.endGame()){
                    endGame = true;
                    resultMatch = "THE ZOMBIES ATE YOUR BRAINS";
                }
                if (zombie.getHp() <= 0 && currentTime - zombie.getDeadTime() > 2000) {
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
                    projectile.forward(currentTime);
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
                LawnMowers[rowIndex].update(currentTime);
            }
        }
    }

    public String result(){
        if (resultMatch == null){
            if (scorePlant() > scoreZombie()){
                resultMatch = "Ganaron las plantas";
            }
            else if (scorePlant() < scoreZombie()){
                resultMatch = "Ganaron los zombies";
            }
            else{
                resultMatch = "Empate";
            }
        }


        return resultMatch;
    }

    public void createPlant(String type, int row, int column,JButton button){
        if (plantMachine == null && plants[row][column]==null){
            if (type.equals("sunflower") && suns >= 50 && usagePlants[0]){
                plants[row][column] = new Sunflower(button, layeredPane, collectables[row],false);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 50;
            }
            else if (type.equals("peashooter") && suns >= 100 && usagePlants[1]){
                plants[row][column] = new Peashooter(button,layeredPane, projectiles[row],zombies[row]);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 100;
            }
            else if (type.equals("ECIPlant") && suns >= 75 && usagePlants[2]){
                plants[row][column] = new ECIPlant(button, layeredPane, collectables[row],false);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 75;
            }
            else if (type.equals("wall-nut") && suns >= 50 && usagePlants[3]){
                plants[row][column] = new Wallnut(button, layeredPane);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 50;
            }
            else if (type.equals("potatoMine") && suns >= 25 && usagePlants[4]){
                plants[row][column] = new PotatoMine(button, layeredPane,zombies[row]);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 25;
            }
        }
    }

    public void deletePlant(int row, int column) {
        plants[row][column].setHp(0);
    }

    public int scorePlant(){
        int scorePlants = 0;
        for (int i = 0; i < plants.length; i++ ){
            for (int j = 0; j < plants[i].length; j++ ){
                if (plants[i][j] != null){
                    scorePlants +=plants[i][j].getCost();
                }
            }
        }


        scorePlants = (int) ((scorePlants+ suns)*1.5) ;

        return scorePlants;
    }

    public int scoreZombie(){
        int scoreZombies = 0;
        for (int i = 0; i < zombies.length; i++ ){
            for (int j = 0; j < zombies[i].size(); j++ ){
                if (zombies[i].get(j) != null){
                    scoreZombies +=zombies[i].get(j).getCost();
                }
            }
        }

        scoreZombies = (int) ((scoreZombies+ brains)) ;

        return scoreZombies;
    }

    public void createZombie(String type, int row, JButton button){
        if (zombieMachine == null) {
            if (type.equals("basic") && brains >= 100 && usageZombies[0]) {
                zombies[row].add(new Basic(button, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
                brains -= 100;
            } else if (type.equals("coneHead") && brains >= 150 && usageZombies[1]) {
                zombies[row].add(new ConeHead(button, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
                brains -= 150;
            } else if (type.equals("bucketHead") && brains >= 200 && usageZombies[2]) {
                zombies[row].add(new BucketHead(button, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
                brains -= 200;
            } else if (type.equals("ECIZombie") && brains >= 250 && usageZombies[3]) {
                zombies[row].add(new ECIZombie(button, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row], projectiles[row]));
                brains -= 250;
            } else if (type.equals("brainstein") && brains >= 50 && usageZombies[4]) {
                zombies[row].add(new Brainstein(button, layeredPane, collectables[row],false));
                brains -= 50;
            }
        }
    }

    public void createHordeZombie(long currentTime) {
        if (currentTime-startTime <= 61000){
            if (currentTime-lastHorde >= 30000){
                // Lista de posiciones posibles para colocar zombies
                ArrayList<Integer> zombiePositions = new ArrayList<>(Arrays.asList(8, 17, 26, 35, 44));
                // Determinar cuántos zombies se van a generar (cantidad aleatoria)
                Random random = new Random();
                int hordeSize = random.nextInt(5)+1;

                for (int i = 0; i < hordeSize; i++) {
                    // Elegir una posición aleatoria de la lista de posiciones
                    int randomIndex = random.nextInt(zombiePositions.size());
                    int position = zombiePositions.get(randomIndex);

                    // Determinar el tipo de zombie aleatoriamente (según usageZombies)
                    int zombieType = random.nextInt(4); // Índice aleatorio para el array usageZombies
                    if (zombieType == 0 && usageZombies[0]) {
                        zombies[randomIndex].add(new Basic(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 1  && usageZombies[1]){
                        zombies[randomIndex].add(new ConeHead(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 2 && usageZombies[2]){
                        zombies[randomIndex].add(new BucketHead(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 3  && usageZombies[3]){
                        zombies[randomIndex].add(new ECIZombie(positions.get(position),layeredPane, plantHitboxs[randomIndex],plants[randomIndex],LawnMowers[randomIndex],projectiles[randomIndex]));
                    }
                }
                lastHorde = currentTime;
            }
        }
        else if (currentTime-startTime <= 121000){
            if (currentTime-lastHorde >= 30000){
                // Lista de posiciones posibles para colocar zombies
                ArrayList<Integer> zombiePositions = new ArrayList<>(Arrays.asList(8, 17, 26, 35, 44));
                // Determinar cuántos zombies se van a generar (cantidad aleatoria)
                Random random = new Random();
                int hordeSize = random.nextInt(4)+3;

                for (int i = 0; i < hordeSize; i++) {
                    // Elegir una posición aleatoria de la lista de posiciones
                    int randomIndex = random.nextInt(zombiePositions.size());
                    int position = zombiePositions.get(randomIndex);

                    // Determinar el tipo de zombie aleatoriamente (según usageZombies)
                    int zombieType = random.nextInt(4); // Índice aleatorio para el array usageZombies
                    if (zombieType == 0 && usageZombies[0]) {
                        zombies[randomIndex].add(new Basic(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 1  && usageZombies[1]){
                        zombies[randomIndex].add(new ConeHead(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 2 && usageZombies[2]){
                        zombies[randomIndex].add(new BucketHead(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 3  && usageZombies[3]){
                        zombies[randomIndex].add(new ECIZombie(positions.get(position),layeredPane, plantHitboxs[randomIndex],plants[randomIndex],LawnMowers[randomIndex],projectiles[randomIndex]));
                    }
                }
                lastHorde = currentTime;
            }
        }
        else {
            if (currentTime-lastHorde >= 30000){
                // Lista de posiciones posibles para colocar zombies
                ArrayList<Integer> zombiePositions = new ArrayList<>(Arrays.asList(8, 17, 26, 35, 44));
                // Determinar cuántos zombies se van a generar (cantidad aleatoria)
                Random random = new Random();
                int hordeSize = random.nextInt(4)+5;

                for (int i = 0; i < hordeSize; i++) {
                    // Elegir una posición aleatoria de la lista de posiciones
                    int randomIndex = random.nextInt(zombiePositions.size());
                    int position = zombiePositions.get(randomIndex);

                    // Determinar el tipo de zombie aleatoriamente (según usageZombies)
                    int zombieType = random.nextInt(4); // Índice aleatorio para el array usageZombies
                    if (zombieType == 0 && usageZombies[0]) {
                        zombies[randomIndex].add(new Basic(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 1  && usageZombies[1]){
                        zombies[randomIndex].add(new ConeHead(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 2 && usageZombies[2]){
                        zombies[randomIndex].add(new BucketHead(positions.get(position), layeredPane, plantHitboxs[randomIndex], plants[randomIndex], LawnMowers[randomIndex]));
                    }
                    else if (zombieType == 3  && usageZombies[3]){
                        zombies[randomIndex].add(new ECIZombie(positions.get(position),layeredPane, plantHitboxs[randomIndex],plants[randomIndex],LawnMowers[randomIndex],projectiles[randomIndex]));
                    }
                }
                lastHorde = currentTime;
            }
        }
    }

    public void machineCreateZombie(int row, int zombieType){
        ArrayList<Integer> zombiePositions = new ArrayList<>(Arrays.asList(8, 17, 26, 35, 44));
        JButton buttonRow = positions.get(zombiePositions.get(row));
        if (zombieType == 0 && brains >= 100 && usageZombies[0]) {
            zombies[row].add(new Basic(buttonRow, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
            brains -= 100;
        }
        else if (zombieType == 1 && brains >= 150 && usageZombies[1]){
            zombies[row].add(new ConeHead(buttonRow, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
            brains -= 150;
        }
        else if (zombieType == 2 && brains >= 200 && usageZombies[2]){
            zombies[row].add(new BucketHead(buttonRow, layeredPane, plantHitboxs[row], plants[row], LawnMowers[row]));
            brains -= 200;
        }
        else if (zombieType == 3 && brains >= 250 && usageZombies[3]){
            zombies[row].add(new ECIZombie(buttonRow,layeredPane, plantHitboxs[row],plants[row],LawnMowers[row],projectiles[row]));
            brains -= 250;
        }
        else if (zombieType == 4 && brains >= 50 && usageZombies[4]){
            zombies[row].add(new Brainstein(buttonRow,layeredPane,collectables[row],zombieMachine!=null));
            brains -= 50;
        }
    }

    public void machineCreatePlant(int row,int column, int type){
        if (plants[row][column]==null){
            int index = row * 9 + column;
            JButton button = positions.get(index);
            if (type == 0 && suns >= 50 && usagePlants[0]) {
                plants[row][column] = new Sunflower(button,layeredPane,collectables[row],plantMachine!=null);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 50;
            }
            else if (type == 1 && suns >= 100 && usagePlants[1]) {
                plants[row][column] = new Peashooter(button,layeredPane,projectiles[row],zombies[row]);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 100;
            }
            else if (type == 2 && suns >= 75 && usagePlants[2]) {
                plants[row][column] = new ECIPlant(button,layeredPane,collectables[row],plantMachine!=null);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 75;
            }
            else if (type == 3 && suns >= 50 && usagePlants[3]){
                plants[row][column] = new Wallnut(button,layeredPane);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 25;
            }
            else if (type == 4 && suns >= 25 && usagePlants[4]){
                plants[row][column] = new PotatoMine(button,layeredPane,zombies[row]);
                plantHitboxs[row][column] = plants[row][column].getHitbox();
                suns -= 25;
            }
        }
    }

    public int getSuns() {
        return suns;
    }

    public int getBrains() {
        return brains;
    }

    public boolean getEndGame(){
        return endGame;
    }

    public Plant[][] getPlants() {
        return plants;
    }

    public boolean hasZombie(int row) {
        return !zombies[row].isEmpty();
    }

    public boolean findBrainstein(int row){
        for (Zombie zombie:zombies[row]){
            if (zombie instanceof Brainstein){
                return true;
            }
        }
        return false;
    }

    public void clear(){
        for (LawnMower lawnMower : LawnMowers){
            if(lawnMower != null){
                lawnMower.remove();
            }
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