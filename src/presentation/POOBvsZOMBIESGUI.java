package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private int modeMachine = 2;
    private int modePlants = 2;

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

    public POOBvsZOMBIESGUI() {
        setTitle("PoobVsZombies");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        WIDTH = dm.getWidth();
        HEIGHT = dm.getHeight();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
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
        PvPButton = new JButton("PvP");
        PvMButton = new JButton("PvM");
        MvMButton = new JButton("MvM");
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
        PvPButton.setBounds(WIDTH / 7, HEIGHT / 2, WIDTH / 7, HEIGHT / 6);
        PvPButton.setHorizontalTextPosition(SwingConstants.CENTER);
        PvPButton.setVerticalTextPosition(SwingConstants.CENTER);
        PvPButton.setFont(new Font("Arial", Font.BOLD, 16));
        PvPButton.setForeground(Color.BLACK);
        PvPButton.setContentAreaFilled(false);
        PvPButton.setFocusPainted(false);
    }

    private void preparePvMButton() {
        PvMButton.setBounds( 3*(WIDTH / 7), HEIGHT / 2, WIDTH / 7, HEIGHT / 6);
        PvMButton.setHorizontalTextPosition(SwingConstants.CENTER);
        PvMButton.setVerticalTextPosition(SwingConstants.CENTER);
        PvMButton.setFont(new Font("Arial", Font.BOLD, 16));
        PvMButton.setForeground(Color.BLACK);
        PvMButton.setContentAreaFilled(false);
        PvMButton.setFocusPainted(false);
    }

    private void prepareMvMButton() {
        MvMButton.setBounds( 5*(WIDTH / 7), HEIGHT / 2, WIDTH / 7, HEIGHT / 6);
        MvMButton.setHorizontalTextPosition(SwingConstants.CENTER);
        MvMButton.setVerticalTextPosition(SwingConstants.CENTER);
        MvMButton.setFont(new Font("Arial", Font.BOLD, 16));
        MvMButton.setForeground(Color.BLACK);
        MvMButton.setContentAreaFilled(false);
        MvMButton.setFocusPainted(false);
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(aceptarChoose);
        buttonPanel.add(cancelarChoose);
        panelChoose.add(buttonPanel, BorderLayout.SOUTH);

        choose.add(panelChoose);
    }

    private void createChooseButtons(){
        peaShooter = createColorButton("src/resources/peashooter.jpg", WIDTH/11, HEIGHT/5);
        sunflower = createColorButton("src/resources/sunflower.jpg", WIDTH/11, HEIGHT/5);
        ECIPlant = createColorButton("src/resources/sunflower.jpg", WIDTH/11, HEIGHT/5);
        wallnut = createColorButton("src/resources/wallnut.jpg", WIDTH/11, HEIGHT/5);
        potatoMine = createColorButton("src/resources/potatoMine.jpg", WIDTH/11, HEIGHT/5);
        zombie = createColorButton("src/resources/zombie.jpg", WIDTH/11, HEIGHT/5);
        zombieConehead = createColorButton("src/resources/conehead.jpg", WIDTH/11, HEIGHT/5);
        zombieBuckethead = createColorButton("src/resources/buckethead.jpg", WIDTH/11, HEIGHT/5);
        ECIZombie = createColorButton("src/resources/sunflower.jpg", WIDTH/11, HEIGHT/5);
        Brainstain = createColorButton("src/resources/sunflower.jpg", WIDTH/11, HEIGHT/5);

    }

    private void prepareActions() {
        prepareActionsStart();
        prepareActionsMenuButtons();

    }

    private void game(){

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
                background.setIcon(null);
                background.setBackground(Color.green);
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
                modeMachine = 2;
                modePlants = 2;
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
                modeMachine = 2;
                modePlants = 2;
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
                modeMachine = 2;
                modePlants = 2;
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

                // Configurar el layout principal
                setLayout(new BorderLayout());

                // Crear el JLayeredPane
                JLayeredPane layeredPane = new JLayeredPane();
                layeredPane.setPreferredSize(new Dimension(WIDTH, 8*HEIGHT/10)); // Tamaño del panel central
                layeredPane.setLayout(null); // Layout absoluto para movimiento libre

                // Imagen de fondo
                JLabel imageLabel = new JLabel();
                ImageIcon imageIcon = new ImageIcon("src/resources/Prueba.jpg");
                Image scaledImage = imageIcon.getImage().getScaledInstance(WIDTH, 8*HEIGHT/10, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                imageLabel.setBounds(0, HEIGHT/10, WIDTH, 8*HEIGHT/10);
                layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);

                // Malla de botones
                JPanel gridPanel = new JPanel(new GridLayout(5, 10, 5, 5));
                gridPanel.setOpaque(false); // Fondo transparente
                gridPanel.setBounds(0, HEIGHT/10, WIDTH, 8*HEIGHT/10); // Ajustar posición dentro del JLayeredPane

                for (int i = 0; i < 50; i++) {
                    JButton button = new JButton("B" + (i + 1));
                    button.setContentAreaFilled(false); // Fondo transparente
                    button.setBorderPainted(true); // Borde visible
                    gridPanel.add(button);
                }
                layeredPane.add(gridPanel, JLayeredPane.PALETTE_LAYER);

                // Botón móvil
                JButton movableButton = new JButton("Drag Me!");
                movableButton.setBounds(150, 100, 100, 30); // Posición inicial del botón
                layeredPane.add(movableButton, JLayeredPane.DRAG_LAYER);

                // Listener para mover el botón
                movableButton.addMouseMotionListener(new MouseMotionAdapter() {
                    private int offsetX, offsetY; // Desplazamiento del clic

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        int newX = e.getXOnScreen() - offsetX;
                        int newY = e.getYOnScreen() - offsetY;
                        movableButton.setLocation(newX, newY);
                    }
                });

                // Agregar el JLayeredPane al centro del BorderLayout
                add(layeredPane, BorderLayout.CENTER);

                // Agregar un panel inferior con botones adicionales
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                JButton exampleButton1 = new JButton("Example 1");
                JButton exampleButton2 = new JButton("Example 2");
                JButton exampleButton3 = new JButton("Example 3");
                bottomPanel.add(exampleButton1);
                bottomPanel.add(exampleButton2);
                bottomPanel.add(exampleButton3);
                add(bottomPanel, BorderLayout.SOUTH);

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
                        ((ColorButton) button).setBackgroundColor(Color.GREEN); // Cambia a verde
                    } else {
                        ((ColorButton) button).setBackgroundColor(Color.RED); // Cambia a rojo
                    }
                }
            });
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