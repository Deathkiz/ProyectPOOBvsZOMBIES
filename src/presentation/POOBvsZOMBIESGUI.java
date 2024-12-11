package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class POOBvsZOMBIESGUI extends JFrame {
    public static int WIDTH, HEIGHT;
    private JLayeredPane principalPanel;
    //background y sus posibles imagenes
    private JLabel background;
    private ImageIcon menuBackground;
    private ImageIcon gameBackground;

    //frame pvp
    private JFrame framePvP;
    //botones frame pvp
    private JButton PvPButton;
    private JButton aceptarPvP;
    private JButton cancelarPvP;
    //informacion pvp
    private String namePlayer11;
    private String namePlayer2;
    private JTextField nameForPlayer1;
    private JTextField nameForPlayer2;

    //frame pvm
    private JFrame framePvM;
    //botones frame pvm
    private JButton PvMButton;
    private JButton aceptarPvM;
    private JButton cancelarPvM;
    private JButton zombiesOriginal;
    private JButton zombiesStrategic;
    //informacion pvm
    private String namePlayer1;
    private JTextField nameForPlayer11;


    //frame mvm
    private JFrame frameMvM;
    //botones frame mvm
    private JButton MvMButton;
    private JButton aceptarMvM;
    private JButton cancelarMvM;
    private JButton zombiesOriginal2;
    private JButton zombiesStrategic2;
    private JButton plantsIntelligent;
    private JButton plantsStrategic;

    //informacion maquinas
    private int modeMachine = 0;
    private int modePlants = 0;

    //frame choose
    private JFrame choose;
    //botones frame choose
    private JButton aceptarChoose;
    private JButton cancelarChoose;
    private ColorButton peaShooter;
    private ColorButton sunflower;
    private ColorButton wallnut;
    private ColorButton ECIPlant;
    private ColorButton potatoMine;
    private ColorButton zombie;
    private ColorButton zombieConehead;
    private ColorButton zombieBuckethead;
    private ColorButton ECIZombie;
    private ColorButton Brainstain;
    //informacion frame choose
    private boolean usagePeashooter = true;
    private boolean usageSunflower = true;
    private boolean usageECIPlant = true;
    private boolean usageWallnut = true;
    private boolean usagePotatoMine = true;
    private boolean usageZombie = true;
    private boolean usageZombieConehead = true;
    private boolean usageZombieBuckethead = true;
    private boolean usageECIZombie = true;
    private boolean usageBrainstain = true;

    //tipo de letra
    private Font sizedFont;

    //logica del juego
    private POOBvsZOMBIES GAME;
    private volatile boolean isPaused = false;
    private volatile boolean endGame = false;
    private long totalPausedTime = 0; // Tiempo total acumulado en pausa
    private long pauseStartTime = 0;
    //malla de botones
    private JPanel gridPanel;
    private ArrayList<JButton> positions;
    private JLabel timeLabel;
    //imagen y botones de menu de plantas
    private JLabel plantLabel;
    private JPanel plantMenuPanel;
    private ArrayList<JButton> plantOptions;
    private JLabel sunLabel;

    //imagen y boton de menu de zombies
    private JLabel zombieLabel;
    private JPanel zombieMenuPanel;
    private ArrayList<JButton> zombieOptions;
    private JLabel brainLabel;

    private boolean activePlant = false;
    private boolean activeZombie = false;
    private String selectedPlant;
    private String selectedZombie;



    public POOBvsZOMBIESGUI() {
        setTitle("PoobVsZombies");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        WIDTH = dm.getWidth();
        HEIGHT = dm.getHeight();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        prepareElements();
        prepareActions();
        revalidate();
        repaint();
    }

    private void prepareElements() {
        //preparaJlayeredPane y el fondo
        prepareElementsStart();
        //prepara botones primer menu (eleccion de modo) y sus frames respectivos
        prepareMenuPrincipalButtons();
        frameForPvP();
        frameForPvM();
        frameForMvM();
        //prepara frame de seleccion de zombies y plantas
        frameChoose();
        //prepara paneles y botones para el juego
        prepareGame();//dividirlo
    }

    private void prepareElementsStart() {
        principalPanel = new JLayeredPane();
        principalPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        principalPanel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        principalPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        prepareElementsBackground();

        principalPanel.add(background, Integer.valueOf(0));
        add(principalPanel);
    }

    private void prepareElementsBackground() {
        background = new JLabel();
        ImageIcon imageBackground = new ImageIcon("src/resources/FondoInicial.jpg");
        Image scaledImage = setSizeImageBackground(imageBackground);
        ImageIcon imageMenuBackground = new ImageIcon(getClass().getResource("/resources/Menu.jpg"));
        Image menuImage = setSizeImageBackground(imageMenuBackground);
        menuBackground = new ImageIcon(menuImage);
        ImageIcon imageGame = new ImageIcon("src/resources/Frontyard.jpg");
        Image gameImage = setSizeImageBackground(imageGame);
        gameBackground = new ImageIcon(gameImage);

        background.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        background.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        background.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        background.setIcon(new ImageIcon(scaledImage));
        background.setOpaque(true);
    }

    private void prepareMenuPrincipalButtons() {
        PvPButton = new JButton();
        PvMButton = new JButton();
        MvMButton = new JButton();
        preparePvPButton();
        preparePvMButton();
        prepareMvMButton();
    }

    private void preparePvPButton() {
        PvPButton.setBounds( (int) (4.05*(WIDTH / 9)), (int) (1.46*(HEIGHT / 3.5)), (int) (WIDTH / 3.3), (int) (HEIGHT / 8));
        ImageIcon imagePvPButton = new ImageIcon(getClass().getResource("/resources/ButtonPvP.png"));
        Image resizedimagePvPButton = imagePvPButton.getImage().getScaledInstance((int) (WIDTH / 3.3), (int) (HEIGHT / 8), Image.SCALE_SMOOTH);
        PvPButton.setIcon(new ImageIcon((resizedimagePvPButton)));
        ImageIcon imagePvPButtonRollover = new ImageIcon(getClass().getResource("/resources/ButtonPvPRollover.png"));
        Image resizedimagePvPButtonRollover = imagePvPButtonRollover.getImage().getScaledInstance((int) (WIDTH / 3.3), (int) (HEIGHT / 8), Image.SCALE_SMOOTH);
        PvPButton.setIcon(new ImageIcon((resizedimagePvPButton)));
        PvPButton.setPressedIcon(new ImageIcon((resizedimagePvPButton)));
        PvPButton.setRolloverIcon(new ImageIcon(resizedimagePvPButtonRollover));
        PvPButton.setForeground(Color.BLACK);
        PvPButton.setContentAreaFilled(false);
        PvPButton.setFocusPainted(false);
        PvPButton.setBorderPainted(false);
    }

    private void preparePvMButton() {
        PvMButton.setBounds( (int) (3.8*(WIDTH / 9)), (int) (HEIGHT / 3.5), (int) (WIDTH / 2.83), (int) (HEIGHT / 7.2));
        ImageIcon imagePvMButton = new ImageIcon(getClass().getResource("/resources/ButtonPvM.png"));
        Image resizedimagePvMButton = imagePvMButton.getImage().getScaledInstance((int) (WIDTH / 2.83), (int) (HEIGHT / 7.5), Image.SCALE_SMOOTH);
        PvMButton.setIcon(new ImageIcon((resizedimagePvMButton)));
        ImageIcon imagePvMButtonRollover = new ImageIcon(getClass().getResource("/resources/ButtonPvMRollover.png"));
        Image resizedimagePvMButtonRollover = imagePvMButtonRollover.getImage().getScaledInstance((int) (WIDTH / 2.83), (int) (HEIGHT / 7.5), Image.SCALE_SMOOTH);
        PvMButton.setIcon(new ImageIcon((resizedimagePvMButton)));
        PvMButton.setPressedIcon(new ImageIcon((resizedimagePvMButton)));
        PvMButton.setForeground(Color.BLACK);
        PvMButton.setContentAreaFilled(false);
        PvMButton.setFocusPainted(false);
        PvMButton.setBorderPainted(false);
        PvMButton.setRolloverIcon(new ImageIcon(resizedimagePvMButtonRollover));


    }
    private void prepareMvMButton() {
        MvMButton.setBounds((int) (4.2*(WIDTH / 9)), (int) (1.9*(HEIGHT / 3.5)), (int) (WIDTH / 3.7), (int) (HEIGHT / 7));
        ImageIcon imageMvMButton = new ImageIcon(getClass().getResource("/resources/ButtonMvM.png"));
        Image resizedimageMvMButton = imageMvMButton.getImage().getScaledInstance((int) (WIDTH / 3.7), (int) (HEIGHT / 7), Image.SCALE_SMOOTH);
        MvMButton.setIcon(new ImageIcon((resizedimageMvMButton)));
        ImageIcon imageMvMButtonRollover = new ImageIcon(getClass().getResource("/resources/ButtonMvMRollover.png"));
        Image resizedimageMvMButtonRollover = imageMvMButtonRollover.getImage().getScaledInstance((int) (WIDTH / 3.7), (int) (HEIGHT / 7), Image.SCALE_SMOOTH);
        MvMButton.setRolloverIcon(new ImageIcon((resizedimageMvMButtonRollover)));
        MvMButton.setPressedIcon(new ImageIcon((resizedimageMvMButton)));
        MvMButton.setForeground(Color.BLACK);
        MvMButton.setContentAreaFilled(false);
        MvMButton.setFocusPainted(false);
        MvMButton.setBorderPainted(false);
    }



    private void frameForPvP(){
        //crear objetos
        framePvP = new JFrame();
        nameForPlayer11 = new JTextField("Name 1");
        nameForPlayer2 = new JTextField("Name 2");
        aceptarPvP = new JButton("Accept");
        cancelarPvP = new JButton("Cancel");
        JPanel panelFramePvP = new JPanel(new FlowLayout());

        //editarlos
        framePvP.setSize(WIDTH / 2, HEIGHT / 2);
        framePvP.setLocationRelativeTo(null);

        //agregarlos al panel y frame
        panelFramePvP.add(nameForPlayer11);
        panelFramePvP.add(nameForPlayer2);
        panelFramePvP.add(aceptarPvP);
        panelFramePvP.add(cancelarPvP);
        framePvP.add(panelFramePvP);
    }

    private void frameForPvM() {
        //crear objetos
        framePvM = new JFrame("Player vs Machine");
        nameForPlayer1 = new JTextField("Name");
        aceptarPvM = new JButton("Accept");
        cancelarPvM = new JButton("Cancel");
        zombiesOriginal = new JButton("Zombies Original");
        zombiesStrategic = new JButton("Zombies Strategic");

        //editarlos
        framePvM.setSize(WIDTH / 2, HEIGHT / 2);
        framePvM.setLocationRelativeTo(null);

        //agregarlos al panel y frame
        JPanel panelFramePvM = new JPanel(new FlowLayout()); // Cambiado a FlowLayout
        panelFramePvM.add(nameForPlayer1);
        panelFramePvM.add(zombiesOriginal);
        panelFramePvM.add(zombiesStrategic);
        panelFramePvM.add(aceptarPvM);
        panelFramePvM.add(cancelarPvM);
        framePvM.add(panelFramePvM);
    }

    private void frameForMvM(){
        //crear objetos
        frameMvM = new JFrame();
        aceptarMvM = new JButton("Accept");
        cancelarMvM = new JButton("Cancel");
        plantsIntelligent = new JButton("Plants Intelligent");
        plantsStrategic = new JButton("Plants Strategic");
        zombiesOriginal2 = new JButton("Zombies Original");
        zombiesStrategic2 = new JButton("Zombies Strategic");

        //editarlos
        frameMvM.setSize(WIDTH / 2, HEIGHT / 2);
        frameMvM.setLocationRelativeTo(null);

        //agregarlos al panel y frame
        JPanel panelFrameMvM = new JPanel(new FlowLayout());
        panelFrameMvM.add(plantsIntelligent);
        panelFrameMvM.add(plantsStrategic);
        panelFrameMvM.add(zombiesStrategic2);
        panelFrameMvM.add(zombiesOriginal2);
        panelFrameMvM.add(aceptarMvM);
        panelFrameMvM.add(cancelarMvM);
        frameMvM.add(panelFrameMvM);
    }

    private void frameChoose(){
        //crear objetos
        choose = new JFrame();
        aceptarChoose = new JButton("Accept");
        cancelarChoose = new JButton("Cancel");
        JPanel panelChoose = new JPanel(new BorderLayout());
        JPanel panelGrid = new JPanel(new GridLayout(2,5,10,10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        createChooseButtons();

        //editarlos
        //frame
        choose.setSize(WIDTH / 2, HEIGHT / 2);
        choose.setLocationRelativeTo(null);
        //paneles
        panelChoose.setBackground(Color.decode("#582d17"));
        panelGrid.setBackground(Color.decode("#582d17"));
        buttonPanel.setBackground(Color.decode("#924e2a"));
        //botones
        aceptarChoose.setBackground(Color.decode("#6a3821"));
        aceptarChoose.setForeground(Color.decode("#c79242"));
        aceptarChoose.setBorderPainted(false);
        cancelarChoose.setBackground(Color.decode("#6a3821"));
        cancelarChoose.setForeground(Color.decode("#c79242"));
        cancelarChoose.setBorderPainted(false);

        //agregarlos al panel y frame
        //plantas
        panelGrid.add(sunflower);
        panelGrid.add(peaShooter);
        panelGrid.add(ECIPlant);
        panelGrid.add(wallnut);
        panelGrid.add(potatoMine);
        //zombies
        panelGrid.add(zombie);
        panelGrid.add(zombieConehead);
        panelGrid.add(zombieBuckethead);
        panelGrid.add(ECIZombie);
        panelGrid.add(Brainstain);
        //aceptar cancelar
        buttonPanel.add(aceptarChoose);
        buttonPanel.add(cancelarChoose);
        //paneles
        panelChoose.add(panelGrid,BorderLayout.CENTER);
        panelChoose.add(buttonPanel, BorderLayout.SOUTH);
        panelChoose.setBackground(Color.decode("#582d17"));
        //frame
        choose.add(panelChoose);
    }

    private void createChooseButtons(){
        //plantas
        peaShooter = createColorButton("src/resources/peashooterSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        sunflower = createColorButton("src/resources/SunflowerSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        ECIPlant = createColorButton("src/resources/ECIPlantSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        wallnut = createColorButton("src/resources/Wall-nutSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        potatoMine = createColorButton("src/resources/PotatoMineSeedPacket.jpg", WIDTH/12, HEIGHT/6);

        //zombies
        zombie = createColorButton("src/resources/ZombieSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        zombieConehead = createColorButton("src/resources/ConeHeadSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        zombieBuckethead = createColorButton("src/resources/BucketheadSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        ECIZombie = createColorButton("src/resources/ZombieSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        Brainstain = createColorButton("src/resources/ZombieSeedPacket.jpg", WIDTH/12, HEIGHT/6);
    }


    private void prepareGame(){
        //Tipo de Letra
        fontOfSunsBrains();

        //grid del juego para plantas
        //panel y arraylist para tener referencia sencilla a los botones
        gridPanel = new JPanel(new GridLayout(5, 9));
        positions = new ArrayList<>();
        //edicion del panel
        gridPanel.setOpaque(false);
        gridPanel.setBounds((int) (WIDTH*0.05), (int) (HEIGHT*0.11), (int) (WIDTH*0.9), (int) (HEIGHT*0.85));
        //crear y agregar los botones
        for (int i = 0; i < 45; i++) {
            JButton button = new JButton("");
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            gridPanel.add(button);
            positions.add(button);
        }
        gridPanel.setVisible(false);
        principalPanel.add(gridPanel,Integer.valueOf(200));

        //labels de imagen de fondo para menus de planta y zombie
        //menu plantas
        plantLabel = new JLabel();
        ImageIcon imageIcon1 = new ImageIcon("src/resources/plantMenu.jpg");
        Image scaledImage1 = imageIcon1.getImage().getScaledInstance((int) (WIDTH*0.5), (int) (HEIGHT*0.1), Image.SCALE_SMOOTH);
        plantLabel.setIcon(new ImageIcon(scaledImage1));
        plantLabel.setBounds(0, 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        plantLabel.setVisible(false);
        principalPanel.add(plantLabel,Integer.valueOf(10));
        //menu zombie
        zombieLabel = new JLabel();
        ImageIcon imageIcon2 = new ImageIcon("src/resources/zombieMenu.jpg");
        Image scaledImage2 = imageIcon2.getImage().getScaledInstance((int) (WIDTH*0.5), (int) (HEIGHT*0.1), Image.SCALE_SMOOTH);
        zombieLabel.setIcon(new ImageIcon(scaledImage2));
        zombieLabel.setBounds((int) (WIDTH*0.5), 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        zombieLabel.setVisible(false);
        principalPanel.add(zombieLabel, Integer.valueOf(10));

        //Botones de los menus
        //tamaño boton tanto planta como zombie
        int buttonWidth = (int) (WIDTH * 0.4 / 8);
        int buttonHeight = (int) (HEIGHT * 0.08);

        //panel para plantas
        plantMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,(int) (WIDTH*0.009),(int) (HEIGHT*0.01)));
        plantMenuPanel.setOpaque(false);
        plantMenuPanel.setBounds(0, 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        //botones de plantas y su arrayList para facil acceso
        plantOptions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("");
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            if (i==1){
                JPanel spacer = new JPanel();
                spacer.setOpaque(false); // Fondo transparente
                spacer.setPreferredSize(new Dimension((int) (WIDTH*0.005), 1)); // Ancho del espacio extra
                plantMenuPanel.add(spacer);
                ImageIcon image = new ImageIcon("src/resources/SunflowerSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 2){
                ImageIcon image = new ImageIcon("src/resources/PeashooterSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 3){
                ImageIcon image = new ImageIcon("src/resources/ECIPlantSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 4){
                ImageIcon image = new ImageIcon("src/resources/Wall-nutSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 5){
                ImageIcon image = new ImageIcon("src/resources/PotatoMineSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            plantMenuPanel.add(button);
            plantOptions.add(button);
        }
        plantMenuPanel.setVisible(false);
        principalPanel.add(plantMenuPanel,Integer.valueOf(200));
        //label para mostrar cantidad de soles
        sunLabel = new JLabel();
        sunLabel.setBounds((int) (WIDTH*0.4/16),(int)(HEIGHT*0.075),(int) (WIDTH*0.4/8),(int) (HEIGHT*0.08*0.25));
        sunLabel.setOpaque(false);
        sunLabel.setFont(sizedFont);
        sunLabel.setForeground(Color.decode("#1d1401"));
        sunLabel.setVisible(false);
        principalPanel.add(sunLabel,Integer.valueOf(11));

        //panel para zombies
        zombieMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,(int) (WIDTH*0.009),(int) (HEIGHT*0.01)));
        zombieMenuPanel.setOpaque(false); // Fondo transparente
        zombieMenuPanel.setBounds((int) (WIDTH*0.5), 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        //botones de zombies y su arrayList para facil acceso
        zombieOptions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08)));
            if (i==1){
                JPanel spacer = new JPanel();
                spacer.setOpaque(false); // Fondo transparente
                spacer.setPreferredSize(new Dimension((int) (WIDTH*0.005), 1)); // Ancho del espacio extra
                zombieMenuPanel.add(spacer);
                ImageIcon image = new ImageIcon("src/resources/ZombieSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 2){
                ImageIcon image = new ImageIcon("src/resources/ConeHeadSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 3){
                ImageIcon image = new ImageIcon("src/resources/BucketheadSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 4){
                ImageIcon image = new ImageIcon("src/resources/ZombieSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            if (i== 5){
                ImageIcon image = new ImageIcon("src/resources/ZombieSeedPacket.jpg");
                Image buton1 = image.getImage().getScaledInstance((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(buton1));
            }
            zombieMenuPanel.add(button);
            zombieOptions.add(button);
        }
        zombieMenuPanel.setVisible(false);
        principalPanel.add(zombieMenuPanel,Integer.valueOf(200));
        //label para mostrar cantidad de zombies
        brainLabel = new JLabel();
        brainLabel.setForeground(Color.decode("#1d1401"));
        brainLabel.setBounds((int) ((WIDTH*0.4/16)+ WIDTH/2),(int)(HEIGHT*0.075),(int) (WIDTH*0.4/8),(int) (HEIGHT*0.02));
        brainLabel.setOpaque(false);
        brainLabel.setFont(sizedFont);
        brainLabel.setVisible(false);
        principalPanel.add(brainLabel,Integer.valueOf(11));

        timeLabel = new JLabel();
        timeLabel.setForeground(Color.decode("#1d1401"));
        timeLabel.setBackground(Color.WHITE);
        timeLabel.setBounds((int) (WIDTH/2)-100,(int)(HEIGHT*0.96),(int) (WIDTH*0.4/8),(int) (HEIGHT*0.02));
        timeLabel.setOpaque(true);
        timeLabel.setFont(sizedFont);
        timeLabel.setVisible(false);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        principalPanel.add(timeLabel,Integer.valueOf(11));
    }

    private void prepareActions() {
        prepareActionsStart();
        prepareActionsMenuButtons();
        prepareActionsGame();
    }


    private void prepareActionsStart() {
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                background.removeMouseListener(this);
                principalPanel.add(PvMButton, Integer.valueOf(1));
                principalPanel.add(PvPButton, Integer.valueOf(1));
                principalPanel.add(MvMButton, Integer.valueOf(1));
                background.setIcon(menuBackground);
                background.setOpaque(true);
            }
        });
    }

    private void prepareActionsMenuButtons(){
        prepareActionsPVPFrame();
        prepareActionsPVMFrame();
        prepareActionsMVMFrame();
        prepareActionsChooseFrame();
    }

    private void prepareActionsPVPFrame(){
        PvPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (framePvP.isVisible()) {
                    framePvP.dispose();
                }
                else if (framePvM.isVisible()){
                    framePvM.dispose();
                }
                else if (frameMvM.isVisible()){
                    frameMvM.dispose();
                }
                modeMachine = 0;
                modePlants = 0;
                framePvP.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                framePvP.setVisible(true);
            }
        });

        aceptarPvP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                namePlayer11 = nameForPlayer11.getText();
                namePlayer2 = nameForPlayer2.getText();
                framePvP.dispose();
                choose.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                choose.setVisible(true);
            }
        });

        cancelarPvP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePvP.dispose();
            }
        });
    }

    private void prepareActionsPVMFrame(){
        PvMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (framePvP.isVisible()) {
                    framePvP.dispose();
                }
                else if (framePvM.isVisible()){
                    framePvM.dispose();
                }
                else if (frameMvM.isVisible()){
                    frameMvM.dispose();
                }
                modeMachine = 0;
                modePlants = 0;
                framePvM.setVisible(true);
            }
        });
        zombiesStrategic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMachine = 1;
            }
        });
        zombiesOriginal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMachine = 0;
            }
        });
        aceptarPvM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                namePlayer1 = nameForPlayer1.getText();
                framePvM.dispose();
                choose.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                choose.setVisible(true);
            }
        });
        cancelarPvM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePvM.dispose();
            }
        });
    }

    private void prepareActionsMVMFrame(){
        MvMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (framePvP.isVisible()) {
                    framePvP.dispose();
                }
                else if (framePvM.isVisible()){
                    framePvM.dispose();
                }
                else if (frameMvM.isVisible()){
                    frameMvM.dispose();
                }
                modeMachine = 0;
                modePlants = 0;
                frameMvM.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frameMvM.setVisible(true);

            }
        });
        plantsStrategic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modePlants = 1;
            }
        });
        plantsIntelligent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modePlants = 0;
            }
        });
        zombiesOriginal2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMachine = 0;
            }
        });
        zombiesStrategic2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMachine = 1;
            }
        });
        aceptarMvM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameMvM.dispose();
                choose.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                choose.setVisible(true);
            }
        });
        cancelarMvM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameMvM.dispose();
            }
        });
    }

    private void prepareActionsChooseFrame(){
        aceptarChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.dispose();
                visibleMenu(false);
                visibleGame(true);
                endGame = false;
                game();
            }
        });

        cancelarChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.dispose();
            }
        });

        peaShooter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usagePeashooter = !usagePeashooter;
                toggleButtonState(peaShooter, usagePeashooter);
            }
        });

        sunflower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageSunflower = !usageSunflower;
                toggleButtonState(sunflower, usageSunflower);
            }
        });

        wallnut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageWallnut = !usageWallnut;
                toggleButtonState(wallnut, usageWallnut);
            }
        });

        ECIPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageECIPlant = !usageECIPlant;
                toggleButtonState(ECIPlant, usageECIPlant);
            }
        });

        potatoMine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usagePotatoMine = !usagePotatoMine;
                toggleButtonState(potatoMine, usagePotatoMine);
            }
        });

// Listeners para los zombies
        zombie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageZombie = !usageZombie;
                toggleButtonState(zombie, usageZombie);
            }
        });

        zombieConehead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageZombieConehead = !usageZombieConehead;
                toggleButtonState(zombieConehead, usageZombieConehead);
            }
        });

        zombieBuckethead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageZombieBuckethead = !usageZombieBuckethead;
                toggleButtonState(zombieBuckethead, usageZombieBuckethead);
            }
        });

        ECIZombie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageECIZombie = !usageECIZombie;
                toggleButtonState(ECIZombie, usageECIZombie);
            }
        });

        Brainstain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usageBrainstain = !usageBrainstain;
                toggleButtonState(Brainstain, usageBrainstain);
            }
        });

    }

    private void prepareActionsGame() {
        // Acción para los botones del menu de plantas
        for (int i = 0; i < plantOptions.size(); i++) {
            final int index = i;
            JButton button = plantOptions.get(i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index == 0){
                        pauseGame();
                    }
                    // Establecer planta activa y seleccionada según el índice
                    if (index == 1) {
                        activePlant = true;
                        selectedPlant = "sunflower";
                    } else if (index == 2) {
                        activePlant = true;
                        selectedPlant = "peashooter";
                    } else if (index == 3) {
                        activePlant = true;
                        selectedPlant = "ECIPlant";
                    } else if (index == 4) {
                        activePlant = true;
                        selectedPlant = "wall-nut";
                    } else if (index == 5) {
                        activePlant = true;
                        selectedPlant = "potatoMine";
                    }
                    else if (index == 6){
                        endGame();
                    }
                }
            });
        }


        // Acción para los botones del menu de zombis
        for (int i = 0; i < zombieOptions.size(); i++) {
            final int index = i;
            JButton button = zombieOptions.get(i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index == 0){
                        resumeGame();
                    }
                    // Establecer zombi activo y seleccionado según el índice
                    if (index == 1) {
                        activeZombie = true;
                        selectedZombie = "basic";
                    } else if (index == 2) {
                        activeZombie = true;
                        selectedZombie = "coneHead";
                    } else if (index == 3) {
                        activeZombie = true;
                        selectedZombie = "bucketHead";
                    } else if (index == 4) {
                        activeZombie = true;
                        selectedZombie = "ECIZombie";
                    } else if (index == 5) {
                        activeZombie = true;
                        selectedZombie = "brainstein";
                    }
                }
            });
        }

        // Acción para los botones de la grid
        for (int i = 0; i < positions.size(); i++) {
            final int index = i;
            JButton button = positions.get(i);
            ArrayList<Integer> zombiePositions = new ArrayList<>(Arrays.asList(8, 17, 26, 35, 44));

            // Acción general para el botón
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener fila y columna de la posición
                    int row = index / 9;
                    int column = index % 9;

                    // Acción si es un zombi
                    if (zombiePositions.contains(index)) {
                        if (activeZombie) {
                            handleZombieAction(button, row); // Manejar acción del zombi
                            activeZombie = false;
                        }
                    }
                    // Acción si es una planta
                    else if (activePlant) {
                        handlePlantAction(button, row, column); // Manejar acción de la planta
                        activePlant = false;
                    }
                }
            });
        }
    }


    private void handleZombieAction(JButton button,int row) {
        if ("basic".equals(selectedZombie) || "coneHead".equals(selectedZombie) || "bucketHead".equals(selectedZombie) || "ECIZombie".equals(selectedZombie)) {
            if (!isPaused){
                GAME.createZombie(selectedZombie, row, button);
            }
        } else if ("brainstein".equals(selectedZombie)) {
            if (button.getIcon() == null) {
                if (!isPaused){
                    GAME.createZombie(selectedZombie, row, button);
                }
            }
        }
    }

    private void handlePlantAction(JButton button, int row, int column) {
        if (button.getIcon() == null) {
            if (!isPaused){
                GAME.createPlant(selectedPlant,row,column,button);
            }
        }
    }

    private void visibleGame(boolean flag){
        //manejar plantas
        for (JButton button: positions){
            button.setIcon(null);
        }

        gridPanel.setVisible(flag);

        plantLabel.setVisible(flag);
        plantMenuPanel.setVisible(flag);
        sunLabel.setVisible(flag);

        zombieLabel.setVisible(flag);
        zombieMenuPanel.setVisible(flag);
        brainLabel.setVisible(flag);

        timeLabel.setVisible(flag);
    }

    private void visibleMenu(boolean flag){
        if (flag){
            background.setIcon(menuBackground);
        }
        else {
            background.setIcon(gameBackground);
        }
        PvPButton.setVisible(flag);
        PvMButton.setVisible(flag);
        MvMButton.setVisible(flag);
    }

    private Image setSizeImageBackground(ImageIcon imagePrincipal) {
        Image image = imagePrincipal.getImage();
        return image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    private ColorButton createColorButton(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ColorButton(new ImageIcon(image)); // Return ColorButton instance
    }

    private void toggleButtonState(ColorButton button, boolean usageFlag) {
        if (usageFlag) {
            (button).setBackgroundColor(Color.decode("#144806")); // Verde
        } else {
            (button).setBackgroundColor(Color.decode("#480707")); // Rojo
        }
    }


    private void fontOfSunsBrains(){
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/Fonts/Hotel W00 Black.ttf"));
            sizedFont = customFont.deriveFont(Font.PLAIN, (int) (HEIGHT*0.08*0.25));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void game() {
        boolean[] usagePlants = {usageSunflower,usagePeashooter,usageECIPlant,usageWallnut,usagePotatoMine};
        boolean[] usageZombies = { usageZombie,usageZombieConehead,usageZombieBuckethead,usageECIZombie, usageBrainstain};
        GAME = new POOBvsZOMBIES(10000, 10000, positions, principalPanel,usagePlants,usageZombies);
        int gameDuration = 1000000;
        totalPausedTime = 0;
        pauseStartTime = 0;

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long lastUpdateTime = startTime;
            long currentTime = startTime;
            long frameTime = 1000 / 60; // Tiempo en milisegundos por cuadro (16.67 ms)

            while (currentTime - startTime - totalPausedTime < gameDuration) {
                currentTime = System.currentTimeMillis();
                if (endGame){
                    break;
                }
                // Si el juego está en pausa, solo ajusta el tiempo en pausa
                if (isPaused) {
                    if (pauseStartTime == 0) {
                        pauseStartTime = currentTime; // Registrar inicio de la pausa
                    }
                    continue; // Salta el resto del ciclo si está en pausa
                } else if (pauseStartTime != 0) {
                    // Salió de la pausa, actualizar el tiempo total pausado
                    totalPausedTime += currentTime - pauseStartTime;
                    pauseStartTime = 0;
                }
                if (currentTime - lastUpdateTime >= frameTime) {
                    GAME.update(currentTime - totalPausedTime);
                    revalidate();
                    repaint();
                    sunLabel.setText(String.valueOf(GAME.getSuns()));
                    brainLabel.setText(String.valueOf(GAME.getBrains()));
                    timeLabel.setText(String.valueOf((gameDuration-currentTime+startTime+totalPausedTime)/1000));
                    lastUpdateTime = currentTime;
                }

                try {
                    // Espera el tiempo restante hasta el siguiente cuadro
                    long elapsedTime = currentTime - lastUpdateTime;
                    if (elapsedTime < frameTime) {
                        Thread.sleep(frameTime - elapsedTime); // Pausa para mantener 60 FPS
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Salir del ciclo si ocurre una interrupción
                }
            }

            visibleGame(false);
            visibleMenu(true);
        }).start();
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

    public void endGame(){
        endGame = true;
        GAME.clear();
    }

    public static void main(String[] args) {
        POOBvsZOMBIESGUI game = new POOBvsZOMBIESGUI();
        game.setVisible(true);
    }
}