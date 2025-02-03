package org.example;
import org.openqa.selenium.WebDriver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

public class RadUi {

    public static void main(String[] args) {
        String[] options = {"c-RED","MABU", "http://popupplayer.radio.net/popupplayer/index.html?station=schizoiddubtechno&tenant=www.radio.de&authId=7929a0dd-5120-4bc7-9785-11f9929e35d6"};
        Radios radios = new Radios();
        
        // Hintergrundbild laden
        ImageIcon backgroundImage = new ImageIcon("src/test/resources/giphy.gif");

        // Haupt-JFrame erstellen
        JFrame frame = new JFrame("DubRad");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Custom JPanel mit Hintergrundbild
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Hintergrundbild zeichnen
                if (backgroundImage.getImage() != null) {
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Hintergrundbild konnte nicht geladen werden.");
                }
            }
        };

        //Window Listener
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Window is closing...");
                System.exit(0);
            }
        });

        // Hauptlayout festlegen
        backgroundPanel.setLayout(new BorderLayout());

        // Panel für die unteren Buttons
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Abstand zwischen Buttons
        botPanel.setOpaque(false); // Hintergrund transparent, damit das Hintergrundbild sichtbar bleibt

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        //DropDown erstellen
        JComboBox<String> dropDown = new JComboBox<String>(options);
        dropDown.setPreferredSize(new Dimension(200, 30));
        dropDown.addActionListener(Event -> {
            String selectedOption = (String) dropDown.getSelectedItem();
            System.out.println("Selected option: " + selectedOption);
        });



        // Buttons erstellen
        ImageIcon playIcon = new ImageIcon("app/icons/play.png");
        JButton btnPlay = new JButton(playIcon);
        JButton exitBtn = new JButton("Exit");
        btnPlay.setPreferredSize(new Dimension(40, 40));

        JButton nextRad = new JButton("Next");
        nextRad.setPreferredSize(new Dimension(80, 40));
        if(radios.isPlaying) {
            nextRad.addActionListener(e -> {
                // Aktion beim Klicken des Next-Buttons
                System.out.println("Next-Button geklickt!");
                radios.setUrl(dropDown.getSelectedItem().toString());
            });
        }


        // Buttons zum unteren Panel hinzufügen
        botPanel.add(btnPlay);
        botPanel.add(nextRad);

        botPanel.add(dropDown);

        //Button zum top Panel hinzufügen
        topPanel.add(exitBtn);

        exitBtn.addActionListener(e -> {
            try {
                if (radios.driver != null) {
                    radios.driver.quit(); // Beendet den WebDriver
                    radios.driver = null; // Dereferenzierung zur Sicherheit
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // Schließt alle geöffneten Frames
                JFrame.getFrames()[0].dispose();
                // JVM sicher beenden
                System.exit(0);
            }
        });


        btnPlay.addActionListener(e -> {
            //Hintergrundaufgabe durch swingWorker
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws InterruptedException {
                    radios.setUrl(dropDown.getSelectedItem().toString());
                    radios.changeIsPlaying();
                    radios.play();
                    System.out.println("Hintergrundaufgabe gestartet."+radios.isPlaying);
                    return null;
                }
                @Override
                protected void  done() {
                    System.out.println("Hintergrundaufgabe abgeschlossen.");
                }
            };
            worker.execute();
        });


        // Komponenten hinzufügen
        backgroundPanel.add(botPanel, BorderLayout.SOUTH); // Buttons unten positionieren
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Panel dem Frame hinzufügen
        frame.setContentPane(backgroundPanel);

        // Frame sichtbar machen
        frame.setVisible(true);
    }
}
