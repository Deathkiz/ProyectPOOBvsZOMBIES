package domain;

import java.util.Random;

public class ZombieMachine {
    private int type;
    private POOBvsZOMBIES game;
    private long startTime;
    private long lastZombie;
    private long cooldown = 10000;

    public ZombieMachine(int type, POOBvsZOMBIES pooBvsZOMBIES){
        this.type = type;
        this.game = pooBvsZOMBIES;
        this.startTime = System.currentTimeMillis();
        this.lastZombie = 0;
    }

    public void makeDecision(int brains, long currentTime) {
        Random random = new Random();

        if (type == 0) {
            if (currentTime - startTime <= 60000) {
                if (currentTime - lastZombie >= cooldown && brains >= 100) {
                    int row = random.nextInt(5);
                    game.machineCreateZombie(row, 0);
                    lastZombie = currentTime;
                }
            } else if (currentTime - startTime <= 120000) {
                if (currentTime - lastZombie >= cooldown && brains >= 150) {
                    int row = random.nextInt(5);
                    game.machineCreateZombie(row, 1);
                    lastZombie = currentTime;
                }
            } else {
                if (currentTime - lastZombie >= cooldown && brains >= 200) {
                    int row = random.nextInt(5);
                    game.machineCreateZombie(row, 2);
                    lastZombie = currentTime;
                }
            }
        }

        else {
            for (int i = 0; i<5 ; i++){
                if (!game.findBrainstein(i) && brains>=50){
                    game.machineCreateZombie(i,4);
                    brains -= 50;
                }
            }
            if (currentTime - startTime <= 60000) {
                if (currentTime - lastZombie >= cooldown && brains >= 150) {
                    int row = random.nextInt(5);
                    game.machineCreateZombie(row, 1);
                    lastZombie = currentTime;
                }
            } else if (currentTime - startTime <= 120000) {
                if (currentTime - lastZombie >= cooldown && brains >= 200) {
                    int row = random.nextInt(5);
                    game.machineCreateZombie(row, 2);
                    lastZombie = currentTime;
                }
            } else { // Más de 2 minutos
                if (currentTime - lastZombie >= cooldown && brains >= 250) {
                    int row = random.nextInt(5);
                    game.machineCreateZombie(row, 3);
                    lastZombie = currentTime;
                }
            }
        }
    }

}