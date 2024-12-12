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
    private JTextField nameForPlayer11;

    private JLabel yourNamePvP1;
    private JLabel yourNamePvP2;

    //frame pvm
    private JFrame framePvM;
    //botones frame pvm
    private JButton PvMButton;
    private JButton aceptarPvM;
    private JButton cancelarPvM;
    private JButton zombiesOriginal;
    private JButton zombiesStrategic;

    //informacion pvm
    private JLabel yourName;
    private JLabel sunsText;
    private JLabel brainsText;
    private JLabel modeZombiesO;
    private JLabel modeZombiesS;
    private String namePlayer1;
    private JLabel timesText;
    private JTextField sunsTextField;
    private JTextField brainsTextField;
    private JTextField timesTextField;
    private JLabel sunsText1;
    private JTextField sunsTextField1;
    private JLabel brainsText1;
    private JTextField brainsTextField1;
    private JLabel timesText1;
    private JTextField timesTextField1;



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
    private Font sizedFontAngulatte;
    private Font sizedFontAugust;

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

    //Margenes de los modos de juego
    private JLabel northBarrier;
    private JLabel northBarrierFromPvP;
    private JLabel northBarrierFromMvM;
    private JLabel modeZombiesO1;
    private JLabel modeZombiesS1;


    private JLabel modePlantsO;
    private JLabel modePlantsS;

    //Boton de Salir
    private JButton xButton;
    private JButton xButtonFromPvP;
    private JButton xButtonFromMvM;

    //Imagenes para los frames de los modos de juego
    private Image resizedImageZombiesButtonOF;
    private Image resizedImageZombiesButtonON;
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
        prepareBarriers();
        prepareBottomX();
        ImagesFromButtonsFrames();
        prepareElementsForFrames();
        prepareFontForModeFrames();
        prepareBottomXFromPvP();
        prepareBottomXFromMvM();
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
    private void prepareBarriers(){
        northBarrier = new JLabel();
        northBarrier.setBounds(0,0,WIDTH/2, (int) ((HEIGHT/2)*0.07));
        northBarrier.setBackground(Color.decode("#60627f"));
        northBarrier.setOpaque(true);
        northBarrierFromPvP = new JLabel();
        northBarrierFromPvP.setBounds(0,0,WIDTH/2, (int) ((HEIGHT/2)*0.07));
        northBarrierFromPvP.setBackground(Color.decode("#60627f"));
        northBarrierFromPvP.setOpaque(true);
        northBarrierFromMvM = new JLabel();
        northBarrierFromMvM.setBounds(0,0,WIDTH/2, (int) ((HEIGHT/2)*0.07));
        northBarrierFromMvM.setBackground(Color.decode("#60627f"));
        northBarrierFromMvM.setOpaque(true);

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




    private void prepareFontForModeFrames(){

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/Fonts/Angulatte W90 Bold.ttf"));
            sizedFontAngulatte = customFont.deriveFont(Font.PLAIN, (int) (HEIGHT*0.08*0.3));
            Font customFontAugust = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/Fonts/The August.otf"));
            sizedFontAugust = customFontAugust.deriveFont(Font.PLAIN, (int) (HEIGHT*0.08*0.3));

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void prepareElementsForFrames(){
        zombiesOriginal = new JButton();
        zombiesOriginal.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.25),(int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1));
        zombiesOriginal.setIcon(new ImageIcon(resizedImageZombiesButtonON));
        zombiesOriginal.setBorderPainted(false);
        zombiesStrategic = new JButton();
        zombiesStrategic.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.45),(int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1));
        zombiesStrategic.setIcon(new ImageIcon(resizedImageZombiesButtonOF));
        zombiesStrategic.setBorderPainted(false);

    }

    private void ImagesFromButtonsFrames(){
        ImageIcon imageZombiesOriginalButton = new ImageIcon(getClass().getResource("/resources/Buttons/ButtonZombiesOOF.jpg"));
        resizedImageZombiesButtonOF = imageZombiesOriginalButton.getImage().getScaledInstance((int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1), Image.SCALE_SMOOTH);
        ImageIcon imageZombiesOriginalButtonON = new ImageIcon(getClass().getResource("/resources/Buttons/ButtonZombiesOON.jpg"));
        resizedImageZombiesButtonON = imageZombiesOriginalButtonON.getImage().getScaledInstance((int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1), Image.SCALE_SMOOTH);

    }
    private void frameForPvP(){
        //crear objetos
        framePvP = new JFrame();
        yourNamePvP1 = new JLabel("Introduce your name 1:");
        yourNamePvP1.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.1),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        yourNamePvP1.setOpaque(false);
        yourNamePvP1.setFont(sizedFontAngulatte);
        yourNamePvP1.setForeground(Color.decode("#c9ad60"));

        nameForPlayer11 = new JTextField();
        nameForPlayer11.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.25),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        nameForPlayer11.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        nameForPlayer11.setBackground(Color.decode("#6a481b"));
        nameForPlayer11.setFont(sizedFontAugust);
        nameForPlayer11.setForeground(Color.decode("#e5d9db"));

        yourNamePvP2 = new JLabel("Introduce your name 2:");
        yourNamePvP2.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.1),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        yourNamePvP2.setOpaque(false);
        yourNamePvP2.setFont(sizedFontAngulatte);
        yourNamePvP2.setForeground(Color.decode("#c9ad60"));

        nameForPlayer2 = new JTextField();
        nameForPlayer2.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.25),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        nameForPlayer2.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        nameForPlayer2.setBackground(Color.decode("#6a481b"));
        nameForPlayer2.setFont(sizedFontAugust);
        nameForPlayer2.setForeground(Color.decode("#e5d9db"));

        aceptarPvP = new JButton("Accept");
        cancelarPvP = new JButton("Cancel");

        aceptarPvP.setFont(sizedFontAngulatte);
        aceptarPvP.setBounds((int) ((WIDTH/4)- (WIDTH/4*0.25)),(int)(HEIGHT/2-(HEIGHT/2*0.12)),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        aceptarPvP.setBorder(BorderFactory.createLineBorder(Color.decode("#474a5e"), 3));
        aceptarPvP.setContentAreaFilled(false);
        aceptarPvP.setOpaque(true);
        aceptarPvP.setForeground(Color.decode("#04970d"));
        aceptarPvP.setBackground(Color.decode("#615d7b"));


        sunsText1 = new JLabel("Introduce the suns:");
        sunsText1.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.3),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        sunsText1.setOpaque(false);
        sunsText1.setFont(sizedFontAngulatte);
        sunsText1.setForeground(Color.decode("#c9ad60"));

        sunsTextField1 = new JTextField();
        sunsTextField1.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.45),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        sunsTextField1.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        sunsTextField1.setBackground(Color.decode("#6a481b"));
        sunsTextField1.setFont(sizedFontAugust);
        sunsTextField1.setForeground(Color.decode("#e5d9db"));


        brainsText1 = new JLabel("Introduce the brains:");
        brainsText1.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.5),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        brainsText1.setOpaque(false);
        brainsText1.setFont(sizedFontAngulatte);
        brainsText1.setForeground(Color.decode("#c9ad60"));

        brainsTextField1 = new JTextField();
        brainsTextField1.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.65),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        brainsTextField1.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        brainsTextField1.setBackground(Color.decode("#6a481b"));
        brainsTextField1.setFont(sizedFontAugust);
        brainsTextField1.setForeground(Color.decode("#e5d9db"));


        timesText1 = new JLabel("Introduce the time:");
        timesText1.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.5),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        timesText1.setOpaque(false);
        timesText1.setFont(sizedFontAngulatte);
        timesText1.setForeground(Color.decode("#c9ad60"));

        timesTextField1 = new JTextField();
        timesTextField1.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.65),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        timesTextField1.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        timesTextField1.setBackground(Color.decode("#6a481b"));
        timesTextField1.setFont(sizedFontAugust);
        timesTextField1.setForeground(Color.decode("#e5d9db"));

        JLayeredPane panelFramePvP = new JLayeredPane();
        //editarlos
        framePvP.setSize(WIDTH / 2, HEIGHT / 2);
        framePvP.setLocationRelativeTo(null);
        framePvP.setUndecorated(true);

        //agregarlos al panel y frame
        panelFramePvP.setBorder(BorderFactory.createLineBorder(Color.decode("#60627f"), 3));
        panelFramePvP.setBackground(Color.decode("#2e2f3f"));
        panelFramePvP.setOpaque(true);
        panelFramePvP.add(northBarrierFromPvP,JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(xButtonFromPvP,JLayeredPane.PALETTE_LAYER);
        panelFramePvP.add(yourNamePvP1,JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(nameForPlayer11, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(yourNamePvP2, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(nameForPlayer2, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(sunsText1, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(sunsTextField1, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(brainsText1, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(brainsTextField1, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(timesText1, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(timesTextField1, JLayeredPane.DEFAULT_LAYER);
        panelFramePvP.add(aceptarPvP, JLayeredPane.DEFAULT_LAYER);


        framePvP.add(panelFramePvP);
    }

    private void frameForPvM() {

        //crear objetos
        framePvM = new JFrame("Player vs Machine");
        nameForPlayer1 = new JTextField();
        nameForPlayer1.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.25),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        nameForPlayer1.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        nameForPlayer1.setBackground(Color.decode("#6a481b"));
        nameForPlayer1.setFont(sizedFontAugust);
        nameForPlayer1.setForeground(Color.decode("#e5d9db"));
        aceptarPvM = new JButton("Accept");
        aceptarPvM.setFont(sizedFontAngulatte);
        aceptarPvM.setBounds((int) ((WIDTH/4)- (WIDTH/4*0.25)),(int)(HEIGHT/2-(HEIGHT/2*0.12)),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        aceptarPvM.setBorder(BorderFactory.createLineBorder(Color.decode("#474a5e"), 3));
        aceptarPvM.setContentAreaFilled(false);
        aceptarPvM.setOpaque(true);
        aceptarPvM.setForeground(Color.decode("#04970d"));
        aceptarPvM.setBackground(Color.decode("#615d7b"));

        //editarlos
        framePvM.setSize(WIDTH / 2, HEIGHT / 2);
        framePvM.setLocationRelativeTo(null);
        framePvM.setUndecorated(true);

        //Labels para Texto:
        yourName = new JLabel("Introduce your name:");
        yourName.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.1),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        yourName.setOpaque(false);
        yourName.setFont(sizedFontAngulatte);
        yourName.setForeground(Color.decode("#c9ad60"));

        sunsText = new JLabel("Introduce the suns:");
        sunsText.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.3),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        sunsText.setOpaque(false);
        sunsText.setFont(sizedFontAngulatte);
        sunsText.setForeground(Color.decode("#c9ad60"));

        sunsTextField = new JTextField();
        sunsTextField.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.45),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        sunsTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        sunsTextField.setBackground(Color.decode("#6a481b"));
        sunsTextField.setFont(sizedFontAugust);
        sunsTextField.setForeground(Color.decode("#e5d9db"));


        brainsText = new JLabel("Introduce the brains:");
        brainsText.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.5),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        brainsText.setOpaque(false);
        brainsText.setFont(sizedFontAngulatte);
        brainsText.setForeground(Color.decode("#c9ad60"));

        brainsTextField = new JTextField();
        brainsTextField.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.65),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        brainsTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        brainsTextField.setBackground(Color.decode("#6a481b"));
        brainsTextField.setFont(sizedFontAugust);
        brainsTextField.setForeground(Color.decode("#e5d9db"));


        timesText = new JLabel("Introduce the time:");
        timesText.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.5),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        timesText.setOpaque(false);
        timesText.setFont(sizedFontAngulatte);
        timesText.setForeground(Color.decode("#c9ad60"));

        timesTextField = new JTextField();
        timesTextField.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.65),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        timesTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#cab469"), 3));
        timesTextField.setBackground(Color.decode("#6a481b"));
        timesTextField.setFont(sizedFontAugust);
        timesTextField.setForeground(Color.decode("#e5d9db"));

        modeZombiesO = new JLabel("Zombies Original");
        modeZombiesO.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.1),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        modeZombiesO.setOpaque(false);
        modeZombiesO.setFont(sizedFontAngulatte);
        modeZombiesO.setForeground(Color.decode("#c9ad60"));

        modeZombiesS = new JLabel("Zombies Strategic");
        modeZombiesS.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.3),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        modeZombiesS.setOpaque(false);
        modeZombiesS.setFont(sizedFontAngulatte);
        modeZombiesS.setForeground(Color.decode("#c9ad60"));

        //agregarlos al panel y frame
        JLayeredPane panelFramePvM = new JLayeredPane();
        panelFramePvM.setBorder(BorderFactory.createLineBorder(Color.decode("#60627f"), 3));
        panelFramePvM.setBackground(Color.decode("#2e2f3f"));
        panelFramePvM.setOpaque(true);
        panelFramePvM.add(northBarrier,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(xButton,JLayeredPane.PALETTE_LAYER);
        panelFramePvM.add(yourName,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(modeZombiesO,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(modeZombiesS,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(sunsText,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(nameForPlayer1,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(sunsTextField,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(brainsText,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(brainsTextField,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(zombiesOriginal,JLayeredPane.PALETTE_LAYER);
        panelFramePvM.add(zombiesStrategic,JLayeredPane.PALETTE_LAYER);
        panelFramePvM.add(timesText,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(timesTextField,JLayeredPane.DEFAULT_LAYER);
        panelFramePvM.add(aceptarPvM, JLayeredPane.DEFAULT_LAYER);
        framePvM.add(panelFramePvM);
    }
    private void prepareBottomX(){
        xButton = new JButton();

        //Imagen predeterminada
        ImageIcon imageButtonX = new ImageIcon("src/resources/Buttons/XButton.png");
        Image resizedImageButtonX = imageButtonX.getImage().getScaledInstance((int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07),Image.SCALE_SMOOTH);

        //Imagen Rollover
        ImageIcon imageButtonXRollover = new ImageIcon("src/resources/Buttons/XButtonRollover.jpg");
        Image resizedImageButtonXRollover = imageButtonXRollover.getImage().getScaledInstance((int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07),Image.SCALE_SMOOTH);

        //Tamaño Boton

        xButton.setBounds((int) ((WIDTH/2)-((HEIGHT/2)*0.07)),0,(int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07));

        //Colocarlas en el boton
        xButton.setIcon(new ImageIcon(resizedImageButtonX));
        xButton.setRolloverIcon(new ImageIcon(resizedImageButtonXRollover));
        xButton.setPressedIcon(new ImageIcon(resizedImageButtonX));
        xButton.setOpaque(true);
        xButton.setBorderPainted(false);
        xButton.setRolloverEnabled(true);
        xButton.setContentAreaFilled(false);
    }
    private void prepareBottomXFromPvP(){
        xButtonFromPvP = new JButton();

        //Imagen predeterminada
        ImageIcon imageButtonX = new ImageIcon("src/resources/Buttons/XButton.png");
        Image resizedImageButtonX = imageButtonX.getImage().getScaledInstance((int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07),Image.SCALE_SMOOTH);

        //Imagen Rollover
        ImageIcon imageButtonXRollover = new ImageIcon("src/resources/Buttons/XButtonRollover.jpg");
        Image resizedImageButtonXRollover = imageButtonXRollover.getImage().getScaledInstance((int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07),Image.SCALE_SMOOTH);

        //Tamaño Boton

        xButtonFromPvP.setBounds((int) ((WIDTH/2)-((HEIGHT/2)*0.07)),0,(int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07));

        //Colocarlas en el boton
        xButtonFromPvP.setIcon(new ImageIcon(resizedImageButtonX));
        xButtonFromPvP.setRolloverIcon(new ImageIcon(resizedImageButtonXRollover));
        xButtonFromPvP.setPressedIcon(new ImageIcon(resizedImageButtonX));
        xButtonFromPvP.setOpaque(true);
        xButtonFromPvP.setBorderPainted(false);
        xButtonFromPvP.setRolloverEnabled(true);
        xButtonFromPvP.setContentAreaFilled(false);
    }
    private void prepareBottomXFromMvM(){
        xButtonFromMvM = new JButton();

        //Imagen predeterminada
        ImageIcon imageButtonX = new ImageIcon("src/resources/Buttons/XButton.png");
        Image resizedImageButtonX = imageButtonX.getImage().getScaledInstance((int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07),Image.SCALE_SMOOTH);

        //Imagen Rollover
        ImageIcon imageButtonXRollover = new ImageIcon("src/resources/Buttons/XButtonRollover.jpg");
        Image resizedImageButtonXRollover = imageButtonXRollover.getImage().getScaledInstance((int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07),Image.SCALE_SMOOTH);

        //Tamaño Boton

        xButtonFromMvM.setBounds((int) ((WIDTH/2)-((HEIGHT/2)*0.07)),0,(int) ((HEIGHT/2)*0.07),(int) ((HEIGHT/2)*0.07));

        //Colocarlas en el boton
        xButtonFromMvM.setIcon(new ImageIcon(resizedImageButtonX));
        xButtonFromMvM.setRolloverIcon(new ImageIcon(resizedImageButtonXRollover));
        xButtonFromMvM.setPressedIcon(new ImageIcon(resizedImageButtonX));
        xButtonFromMvM.setOpaque(true);
        xButtonFromMvM.setBorderPainted(false);
        xButtonFromMvM.setRolloverEnabled(true);
        xButtonFromMvM.setContentAreaFilled(false);
    }
    private void frameForMvM(){
        //crear objetos
        frameMvM = new JFrame();
        aceptarMvM = new JButton("Accept");
        aceptarMvM.setFont(sizedFontAngulatte);
        aceptarMvM.setBounds((int) ((WIDTH/4)- (WIDTH/4*0.25)),(int)(HEIGHT/2-(HEIGHT/2*0.22)),(int) (WIDTH/2*0.25),(int) (HEIGHT/2*0.1));
        aceptarMvM.setBorder(BorderFactory.createLineBorder(Color.decode("#474a5e"), 3));
        aceptarMvM.setContentAreaFilled(false);
        aceptarMvM.setOpaque(true);
        aceptarMvM.setForeground(Color.decode("#04970d"));
        aceptarMvM.setBackground(Color.decode("#615d7b"));


        cancelarMvM = new JButton("Cancel");
        plantsIntelligent = new JButton("Plants Intelligent");
        plantsStrategic = new JButton("Plants Strategic");

        //editarlos
        frameMvM.setSize(WIDTH / 2, HEIGHT / 2);
        frameMvM.setLocationRelativeTo(null);


        modePlantsO = new JLabel("Plants Intelligent");
        modePlantsO.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.1),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        modePlantsO.setOpaque(false);
        modePlantsO.setFont(sizedFontAngulatte);
        modePlantsO.setForeground(Color.decode("#c9ad60"));

        modePlantsS = new JLabel("Plants Strategic");
        modePlantsS.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.3),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        modePlantsS.setOpaque(false);
        modePlantsS.setFont(sizedFontAngulatte);
        modePlantsS.setForeground(Color.decode("#c9ad60"));

        plantsIntelligent = new JButton();
        plantsIntelligent.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.25),(int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1));
        plantsIntelligent.setIcon(new ImageIcon(resizedImageZombiesButtonON));
        plantsIntelligent.setBorderPainted(false);
        plantsStrategic = new JButton();
        plantsStrategic.setBounds((int) (WIDTH/2*0.1),(int) (HEIGHT/2*0.45),(int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1));
        plantsStrategic.setIcon(new ImageIcon(resizedImageZombiesButtonOF));
        plantsStrategic.setBorderPainted(false);



        zombiesOriginal2 = new JButton();
        zombiesOriginal2.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.25),(int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1));
        zombiesOriginal2.setIcon(new ImageIcon(resizedImageZombiesButtonON));
        zombiesOriginal2.setBorderPainted(false);
        zombiesStrategic2 = new JButton();
        zombiesStrategic2.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.45),(int) (WIDTH/2*0.2),(int) (HEIGHT/2*0.1));
        zombiesStrategic2.setIcon(new ImageIcon(resizedImageZombiesButtonOF));
        zombiesStrategic2.setBorderPainted(false);


        modeZombiesO1 = new JLabel("Zombies Original");
        modeZombiesO1.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.1),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        modeZombiesO1.setOpaque(false);
        modeZombiesO1.setFont(sizedFontAngulatte);
        modeZombiesO1.setForeground(Color.decode("#c9ad60"));

        modeZombiesS1 = new JLabel("Zombies Strategic");
        modeZombiesS1.setBounds((int) (WIDTH/2*0.6),(int) (HEIGHT/2*0.3),(int) (WIDTH/2*0.3),(int) (HEIGHT/2*0.2));
        modeZombiesS1.setOpaque(false);
        modeZombiesS1.setFont(sizedFontAngulatte);
        modeZombiesS1.setForeground(Color.decode("#c9ad60"));



        //agregarlos al panel y frame
        JLayeredPane panelFrameMvM = new JLayeredPane();
        panelFrameMvM.setBorder(BorderFactory.createLineBorder(Color.decode("#60627f"), 3));
        panelFrameMvM.setBackground(Color.decode("#2e2f3f"));
        panelFrameMvM.setOpaque(true);
        panelFrameMvM.add(northBarrierFromMvM,JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(xButtonFromMvM,JLayeredPane.PALETTE_LAYER);
        panelFrameMvM.add(modeZombiesO1, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(zombiesOriginal2,JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(modeZombiesS1, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(zombiesStrategic2, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(modePlantsO, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(plantsIntelligent, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(modePlantsS, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(plantsStrategic, JLayeredPane.DEFAULT_LAYER);
        panelFrameMvM.add(aceptarMvM,JLayeredPane.DEFAULT_LAYER);
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
                zombiesOriginal.setIcon(new ImageIcon(resizedImageZombiesButtonOF));
                zombiesStrategic.setIcon(new ImageIcon(resizedImageZombiesButtonON));
            }
        });
        zombiesOriginal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeMachine = 0;
                zombiesOriginal.setIcon(new ImageIcon(resizedImageZombiesButtonON));
                zombiesStrategic.setIcon(new ImageIcon(resizedImageZombiesButtonOF));
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

        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePvM.dispose();
            }
        });
        xButtonFromPvP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePvP.dispose();
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