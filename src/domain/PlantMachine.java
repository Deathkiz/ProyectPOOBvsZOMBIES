package domain;

public class PlantMachine {
    private int type;
    private POOBvsZOMBIES game;
    private Plant[][] plants;
    private long cooldown = 10000;

    public PlantMachine(int type, POOBvsZOMBIES pooBvsZOMBIES){
        this.type = type;
        this.game = pooBvsZOMBIES;
        this.plants = game.getPlants();
    }

    public void makeDecision(int suns){
        if (type==0){
            for (int i = 0 ; i<5 ; i++){
                if (game.hasZombie(i) && suns >= 100 && plants[i][1] == null){
                    game.machineCreatePlant(i,1,1);
                    suns -= 100;
                }
            }
            boolean haveSunflowers = true;
            for (int i = 0; i < 5; i++) {
                if (suns >= 150 && plants[i][0] == null) {
                    game.machineCreatePlant(i, 0, 0);
                    suns -= 50;
                }
                if (plants[i][0] == null) {
                    haveSunflowers = false;
                }
            }
            if (haveSunflowers){
                boolean havePeashooters = true;
                for (int i = 0 ; i<5 ; i++){
                    if (suns >= 200 && plants[i][1] == null){
                        game.machineCreatePlant(i,1,1);
                        suns -= 100;
                    }
                    if (plants[i][0] == null) {
                        havePeashooters = false;
                    }
                }
                if (havePeashooters){
                    for (int i = 0 ; i<5 ; i++){
                        if (suns >= 150 && plants[i][2] == null){
                            game.machineCreatePlant(i,2,3);
                        }
                    }
                }
            }
        }
        else{
            for (int i = 0 ; i<5 ; i++){
                if (game.hasZombie(i) && suns >= 100 && plants[i][1] == null){
                    game.machineCreatePlant(i,1,1);
                    suns -= 100;
                }
            }
            boolean haveSunflowers = true;
            for (int i = 0; i < 5; i++) {
                if (suns >= 150 && plants[i][0] == null) {
                    game.machineCreatePlant(i, 0, 0);
                    suns -= 50;
                }
                if (plants[i][0] == null) {
                    haveSunflowers = false;
                }
            }
            if (haveSunflowers) {
                boolean havePeashooters = true; // Asumimos inicialmente que todas las posiciones tienen Peashooters

                for (int i = 0; i < 5; i++) { // Recorre las filas
                    for (int j = 1; j <= 6; j++) { // Recorre las columnas de 1 a 6
                        if (plants[i][j] == null) { // Si falta una planta en esta posición
                            havePeashooters = false; // Cambiamos a false
                        }
                        if (suns >= 200 && plants[i][j] == null) { // Si hay soles y no hay planta
                            game.machineCreatePlant(i, j, 1); // Creamos una planta de tipo 1
                            suns -= 100; // Reducimos los soles
                        }
                    }
                }
                if (havePeashooters){
                    for (int i = 0; i < 5; i++) {
                        if (suns >= 50 && plants[i][7] == null) {
                            game.machineCreatePlant(i, 7, 3);
                            suns -= 50;
                        }
                    }
                }
            }
        }
    }
}
