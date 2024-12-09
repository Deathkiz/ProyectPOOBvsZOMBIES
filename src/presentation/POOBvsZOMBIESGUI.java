package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class POOBvsZOMBIESGUI extends JFrame {
    public static int WIDTH, HEIGHT;
    private JLabel background;
    private JLayeredPane principalPanel;

    private JFrame framePvM;
    private JFrame framePvP;
    private JFrame frameMvM;

    //private TreeMap<String, JButton> buttons;
    private JButton PvPButton;
    private JButton PvMButton;
    private JButton MvMButton;
    private JButton zombiesOriginal;
    private JButton zombiesStrategic;
    private JButton zombiesOriginal2;
    private JButton zombiesStrategic2;
    private JButton plantsIntelligent;
    private JButton plantsStrategic;
    private JButton aceptarPvP;
    private JButton cancelarPvP;
    private JButton aceptarPvM;
    private JButton cancelarPvM;
    private JButton aceptarMvM;
    private JButton cancelarMvM;
    private JButton aceptarChoose;
    private JButton cancelarChoose;


    private JTextField nameForPlayer1;
    private JTextField nameForPlayer11;
    private JTextField nameForPlayer2;
    private String namePlayer11;
    private String namePlayer1;
    private String namePlayer2;
    private int modeMachine = 0;
    private int modePlants = 0;

    private JFrame choose;
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

    private int offsetX, offsetY;

    private POOBvsZOMBIES GAME;
    private JLayeredPane layeredPane;
    private JPanel hitboxSystem;
    private ArrayList<JButton> positions;
    private ArrayList<JButton> plantOptions;
    private ArrayList<JButton> zombieOptions;

    private boolean activePlant = false;
    private boolean activeZombie = false;
    private String selectedPlant;
    private String selectedZombie;
    private ImageIcon imageIcon;
    private JLabel imageLabel;

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
    }

    private void prepareElements() {
        prepareElementsStart();
        prepareElementsButtons();
        frameForPvM();
        frameForPvP();
        frameForMvM();
        createChooseButtons();
        frameChoose();
        prepareGame();
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
        background.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        background.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        background.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        background.setIcon(new ImageIcon(scaledImage));
        background.setOpaque(true);
    }

    private void prepareElementsButtons() {
        PvPButton = new JButton();
        PvMButton = new JButton();
        MvMButton = new JButton();
        aceptarPvP = new JButton("Accept");
        aceptarPvM = new JButton("Accept");
        aceptarMvM = new JButton("Accept");
        aceptarChoose = new JButton("Accept");
        cancelarPvP = new JButton("Cancel");
        cancelarPvM = new JButton("Cancel");
        cancelarMvM = new JButton("Cancel");
        cancelarChoose = new JButton("Cancel");
        plantsIntelligent = new JButton("Plants Intelligent");
        plantsStrategic = new JButton("Plants Strategic");
        zombiesOriginal = new JButton("Zombies Original");
        zombiesStrategic = new JButton("Zombies Strategic");
        zombiesOriginal2 = new JButton("Zombies Original");
        zombiesStrategic2 = new JButton("Zombies Strategic");

        // Añadir botones al panel
        prepareSizePrincipalButtons();

        principalPanel.add(PvMButton, Integer.valueOf(1));
        principalPanel.add(PvPButton, Integer.valueOf(1));
        principalPanel.add(MvMButton, Integer.valueOf(3));
        PvMButton.setVisible(false);
        PvPButton.setVisible(false);
        MvMButton.setVisible(false);
    }

    private void prepareSizePrincipalButtons() {
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
        framePvP = new JFrame();
        nameForPlayer11 = new JTextField("Name 1");
        nameForPlayer2 = new JTextField("Name 2");
        JPanel panelFramePvP = new JPanel(new FlowLayout());
        panelFramePvP.add(nameForPlayer11);
        panelFramePvP.add(nameForPlayer2);
        panelFramePvP.add(aceptarPvP);
        panelFramePvP.add(cancelarPvP);
        framePvP.add(panelFramePvP);
        framePvP.setSize(WIDTH / 2, HEIGHT / 2);
        framePvP.setLocationRelativeTo(null);
    }

    private void frameForPvM() {
        framePvM = new JFrame("Player vs Machine");
        nameForPlayer1 = new JTextField("Name");

        framePvM.setSize(WIDTH / 2, HEIGHT / 2);
        framePvM.setLocationRelativeTo(null);

        JPanel panelFramePvM = new JPanel(new FlowLayout()); // Cambiado a FlowLayout
        panelFramePvM.add(nameForPlayer1);
        panelFramePvM.add(zombiesOriginal);
        panelFramePvM.add(zombiesStrategic);
        panelFramePvM.add(aceptarPvM);
        panelFramePvM.add(cancelarPvM);

        framePvM.add(panelFramePvM);
    }

    private void frameForMvM(){
        frameMvM = new JFrame();
        frameMvM.setSize(WIDTH / 2, HEIGHT / 2);
        frameMvM.setLocationRelativeTo(null);

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
        choose = new JFrame();
        choose.setSize(WIDTH / 2, HEIGHT / 2);
        choose.setLocationRelativeTo(null);

        JPanel panelChoose = new JPanel(new BorderLayout());


        JPanel panelGrid = new JPanel(new GridLayout(2,5,10,10));
        panelGrid.add(sunflower);
        panelGrid.add(peaShooter);
        panelGrid.add(ECIPlant);
        panelGrid.add(wallnut);
        panelGrid.add(potatoMine);
        panelGrid.add(zombie);
        panelGrid.add(zombieConehead);
        panelGrid.add(zombieBuckethead);
        panelGrid.add(ECIZombie);
        panelGrid.add(Brainstain);
        panelChoose.add(panelGrid,BorderLayout.CENTER);
        panelGrid.setBackground(Color.decode("#582d17"));

        panelChoose.setBackground(Color.decode("#582d17"));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        aceptarChoose.setBackground(Color.decode("#6a3821"));
        aceptarChoose.setForeground(Color.decode("#c79242"));
        cancelarChoose.setBackground(Color.decode("#6a3821"));
        cancelarChoose.setForeground(Color.decode("#c79242"));
        aceptarChoose.setBorderPainted(false);
        cancelarChoose.setBorderPainted(false);

        buttonPanel.add(aceptarChoose);
        buttonPanel.add(cancelarChoose);
        buttonPanel.setBackground(Color.decode("#924e2a"));
        panelChoose.add(buttonPanel, BorderLayout.SOUTH);
        panelChoose.setBackground(Color.decode("#582d17"));
        choose.add(panelChoose);
    }

    private void createChooseButtons(){
        peaShooter = createColorButton("src/resources/peashooterSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        sunflower = createColorButton("src/resources/SunflowerSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        ECIPlant = createColorButton("src/resources/ECIPlantSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        wallnut = createColorButton("src/resources/Wall-nutSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        potatoMine = createColorButton("src/resources/PotatoMineSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        zombie = createColorButton("src/resources/ZombieSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        zombieConehead = createColorButton("src/resources/ConeHeadSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        zombieBuckethead = createColorButton("src/resources/BucketheadSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        ECIZombie = createColorButton("src/resources/ZombieSeedPacket.jpg", WIDTH/12, HEIGHT/6);
        Brainstain = createColorButton("src/resources/ZombieSeedPacket.jpg", WIDTH/12, HEIGHT/6);

    }

    private void prepareGame(){
        positions = new ArrayList<>();
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.setLayout(null);

        hitboxSystem = new JPanel();
        hitboxSystem.setBackground(Color.WHITE);
        hitboxSystem.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(hitboxSystem, JLayeredPane.DEFAULT_LAYER);

        JLabel imageLabel1 = new JLabel();
        ImageIcon imageIcon1 = new ImageIcon("src/resources/plantMenu.jpg");
        Image scaledImage1 = imageIcon1.getImage().getScaledInstance((int) (WIDTH*0.5), (int) (HEIGHT*0.1), Image.SCALE_SMOOTH);
        imageLabel1.setIcon(new ImageIcon(scaledImage1));
        imageLabel1.setBounds(0, 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        layeredPane.add(imageLabel1, JLayeredPane.PALETTE_LAYER);

        JLabel imageLabel2 = new JLabel();
        ImageIcon imageIcon2 = new ImageIcon("src/resources/zombieMenu.jpg");
        Image scaledImage2 = imageIcon2.getImage().getScaledInstance((int) (WIDTH*0.5), (int) (HEIGHT*0.1), Image.SCALE_SMOOTH);
        imageLabel2.setIcon(new ImageIcon(scaledImage2));
        imageLabel2.setBounds((int) (WIDTH*0.5), 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        layeredPane.add(imageLabel2, JLayeredPane.PALETTE_LAYER);

        // Malla de botones
        JPanel gridPanel = new JPanel(new GridLayout(5, 9));
        gridPanel.setOpaque(false); // Fondo transparente
        gridPanel.setBounds((int) (WIDTH*0.05), (int) (HEIGHT*0.11), (int) (WIDTH*0.9), (int) (HEIGHT*0.85));
        for (int i = 0; i < 45; i++) {
            JButton button = new JButton("");
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            gridPanel.add(button);
            positions.add(button);
        }
        layeredPane.add(gridPanel, JLayeredPane.PALETTE_LAYER);

        // Imagen de fondo
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/resources/Frontyard.jpg");
        Image scaledImage = imageIcon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(imageLabel, JLayeredPane.PALETTE_LAYER);


        plantOptions = new ArrayList<>();
        JPanel plantMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,(int) (WIDTH*0.009),(int) (HEIGHT*0.01)));
        plantMenuPanel.setOpaque(false); // Fondo transparente
        plantMenuPanel.setBounds(0, 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        for (int i = 0; i < 9; i++) {
            if (i == 1) {
                // Insertar espacio adicional antes del segundo botón
                JPanel spacer = new JPanel();
                spacer.setOpaque(false); // Fondo transparente
                spacer.setPreferredSize(new Dimension((int) (WIDTH*0.005), 1)); // Ancho del espacio extra
                plantMenuPanel.add(spacer);
            }
            JButton button = new JButton("");
            button.setContentAreaFilled(false);
            button.setBorderPainted(true);
            button.setPreferredSize(new Dimension((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08)));
            plantMenuPanel.add(button);
            plantOptions.add(button);
        }
        layeredPane.add(plantMenuPanel,JLayeredPane.POPUP_LAYER);



        zombieOptions = new ArrayList<>();
        JPanel zombieMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,(int) (WIDTH*0.009),(int) (HEIGHT*0.01)));
        zombieMenuPanel.setOpaque(false); // Fondo transparente
        zombieMenuPanel.setBounds((int) (WIDTH*0.5), 0, (int) (WIDTH*0.5), (int) (HEIGHT*0.1));
        for (int i = 0; i < 9; i++) {
            if (i == 1) {
                // Insertar espacio adicional antes del segundo botón
                JPanel spacer = new JPanel();
                spacer.setOpaque(false); // Fondo transparente
                spacer.setPreferredSize(new Dimension((int) (WIDTH*0.005), 1)); // Ancho del espacio extra
                zombieMenuPanel.add(spacer);
            }
            JButton button = new JButton("B");
            button.setContentAreaFilled(false);
            button.setBorderPainted(true);
            button.setPreferredSize(new Dimension((int) (WIDTH*0.4/8),(int) (HEIGHT*0.08)));
            zombieMenuPanel.add(button);
            zombieOptions.add(button);
        }
        layeredPane.add(zombieMenuPanel,JLayeredPane.POPUP_LAYER);
    }

    private void prepareActions() {
        prepareActionsStart();
        prepareActionsMenuButtons();
        prepareActionsGame();
    }

    private void game(){
        // Configurar el layout principal
        setLayout(null);
        add(layeredPane);
        setVisible(true);
        GAME = new POOBvsZOMBIES(10,10,positions,layeredPane);


    }

    private void prepareActionsStart() {
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                background.setIcon(null);
                background.removeMouseListener(this);
                PvMButton.setVisible(true);
                PvPButton.setVisible(true);
                MvMButton.setVisible(true);
                ImageIcon imageBackground = new ImageIcon(getClass().getResource("/resources/Menu.jpg"));
                Image resizedImageBackground = imageBackground.getImage().getScaledInstance(WIDTH, HEIGHT,Image.SCALE_DEFAULT);
                background.setIcon(new ImageIcon(resizedImageBackground));
                background.setOpaque(true);
                revalidate();
                repaint();
            }
        });
    }

    private void prepareActionsMenuButtons(){
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


        aceptarChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.dispose();
                getContentPane().removeAll();
                game();
                revalidate();
                repaint();
            }
        });

        cancelarChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose.dispose();
            }
        });

        JButton[] buttons = {peaShooter, sunflower, wallnut, ECIPlant, potatoMine,
                zombie, zombieConehead, zombieBuckethead, ECIZombie, Brainstain};

        boolean[] usageFlags = {usagePeashooter, usageSunflower, usageWallnut, usageECIPlant,
                usagePotatoMine, usageZombie, usageZombieConehead,
                usageZombieBuckethead, usageECIZombie, usageBrainstain};

        for (int i = 0; i < buttons.length; i++) {
            final int index = i; // Necesario para usar en el ActionListener
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    usageFlags[index] = !usageFlags[index]; // Cambia el estado de uso
                    JButton button = (JButton) e.getSource(); // Obtiene el botón que disparó el evento

                    // Cambia el color de fondo basado en el estado de uso
                    if (usageFlags[index]) {
                        ((ColorButton) button).setBackgroundColor(Color.decode("#144806")); // Cambia a verde
                    } else {
                        ((ColorButton) button).setBackgroundColor(Color.decode("#480707")); // Cambia a rojo
                    }
                }
            });
        }
    }

    private void prepareActionsGame() {
        // Acción para los botones de plantas
        for (int i = 0; i < plantOptions.size(); i++) {
            final int index = i;
            JButton button = plantOptions.get(i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                }
            });
        }


        // Acción para los botones de zombis
        for (int i = 0; i < zombieOptions.size(); i++) {
            final int index = i;
            JButton button = zombieOptions.get(i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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

        // Acción para los botones de posiciones
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
                        }
                    }
                    // Acción si es una planta
                    else if (activePlant) {
                        handlePlantAction(button, row, column); // Manejar acción de la planta
                    }

                    // Resetear flags después de realizar la acción
                    activeZombie = false;
                    activePlant = false;
                }
            });
        }
    }


    private void handleZombieAction(JButton button,int row) {
        if ("basic".equals(selectedZombie)) {
            GAME.createZombie(selectedZombie, row, button);
        } else if ("coneHead".equals(selectedZombie)) {
            // Lógica para coneHead
        } else if ("bucketHead".equals(selectedZombie)) {
            // Lógica para bucketHead
        } else if ("ECIZombie".equals(selectedZombie)) {
            // Lógica para ECIZombie
        } else if ("brainstein".equals(selectedZombie)) {
            if (button.getIcon() == null) {
                ImageIcon gifIcon = new ImageIcon(getClass().getResource("/resources/PeaShooter.gif"));
                gifIcon = new ImageIcon(gifIcon.getImage().getScaledInstance(
                        (int) (button.getSize().getWidth() * 0.7),
                        (int) (button.getSize().getHeight() * 0.7),
                        Image.SCALE_DEFAULT));
                button.setIcon(gifIcon);
            }
        }
    }


    private void handlePlantAction(JButton button, int row, int column) {
        if (button.getIcon() == null) {
            GAME.createPlant(selectedPlant,row,column,button);
        }
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

    public static void main(String[] args) {
        POOBvsZOMBIESGUI game = new POOBvsZOMBIESGUI();
        game.setVisible(true);
    }
}