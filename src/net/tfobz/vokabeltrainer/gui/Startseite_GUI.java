package net.tfobz.vokabeltrainer.gui;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startseite_GUI extends JFrame {

    private JPanel startPanel;
    private JPanel einstellungenPanel;
   

    private JComboBox<String> sprache;
    private JComboBox<String> fach;
    private JCheckBox schreibung;
    private JRadioButton wort_1;
    private JRadioButton wort_2;
    private ButtonGroup g1;
    private JLabel lblname;
    private JTextField txtname;
    private JLabel lblsprache1;
    private JLabel lblsprache2;
    private JTextField txtsprache1;
    private JTextField txtsprache2;
    private JLabel radiobuttons;
    private JButton zurueck;
    private JButton speichern;

    public Startseite_GUI() {
        setTitle("Vokabeltrainer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Startseite erstellen
        startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setPreferredSize(new Dimension(400, 250));

        JLabel text = new JLabel(
            "Wählen Sie folgende Optionen aus um zu lernen!"
        );
        text.setBounds(50, 10, 320, 20);
        startPanel.add(text);

        fach = new JComboBox<String>();
        fach.addItem("1 Fach");
        fach.addItem("2 Fach");
        fach.addItem("3 Fach");
        fach.addItem("4 Fach");
        fach.setBounds(50, 50, 100, 30);
        startPanel.add(fach);
        
        sprache = new JComboBox<String>();
        sprache.setBounds(50, 100, 100, 30);
        startPanel.add(sprache);

        JButton lern = new JButton("Lern");
        lern.setBounds(50, 150, 80, 40);
        lern.setBackground(Color.GREEN);
        startPanel.add(lern);

        JButton einstellungen = new JButton("⚙");
        einstellungen.setBounds(300, 50, 50, 50);
        einstellungen.setBackground(Color.LIGHT_GRAY);
        startPanel.add(einstellungen);

        // Einstellungen-Panel erstellen
        einstellungenPanel = new JPanel();
        einstellungenPanel.setLayout(null);
        einstellungenPanel.setPreferredSize(
            new Dimension(420, 380)
        );
        //titel
        JLabel titel = new JLabel("Lernkartei verwalten");
        titel.setBounds(30, 15, 250, 25);
        einstellungenPanel.add(titel);
        
        //Name der kartei:
        lblname = new JLabel("Name der Kartei");
        lblname.setBounds(30, 50, 150, 20);
        einstellungenPanel.add(lblname);
        
        txtname = new JTextField();
        txtname.setBounds(30, 70, 340, 25);
        einstellungenPanel.add(txtname);
        
        //Sprache 1 und 2
        lblsprache1 = new JLabel("Sprache 1");
        lblsprache2 = new JLabel("Sprache 2");
        txtsprache1 = new JTextField();
        txtsprache2 = new JTextField();
        lblsprache1.setBounds(30,105,160,20);
        lblsprache2.setBounds(210, 105, 160, 20);
        txtsprache1.setBounds(30,125,160,25);
        txtsprache2.setBounds(210, 125, 160, 20);
        einstellungenPanel.add(lblsprache1);
        einstellungenPanel.add(lblsprache2);
        einstellungenPanel.add(txtsprache1);
        einstellungenPanel.add(txtsprache2);

        //Checkbox für Groß/kleinschreibung:
        schreibung = new JCheckBox("Groß-/Kleinschreibung beachten?");
        schreibung.setBounds(30,225,280,25);
        einstellungenPanel.add(schreibung);
        
        //RadioButtons für Wort 1 zu Wort 2 oder Wort 2 zu Wort 1:
        radiobuttons = new JLabel("Standard-Abfragerichtung: ");
        radiobuttons.setBounds(30, 165, 200, 20);
        einstellungenPanel.add(radiobuttons);
        wort_1 = new JRadioButton("Wort 1 → Wort 2", true);
        wort_2 = new JRadioButton("Wort 2 → Wort 1");
        g1 = new ButtonGroup();
        g1.add(wort_1);
        g1.add(wort_2);
        wort_1.setBounds(30,190,140,25);
        wort_2.setBounds(180,190,140,25);
        
        einstellungenPanel.add(wort_1);
        einstellungenPanel.add(wort_2);
        //Zurück Button und Speichern Button
       
        JButton zurueck = new JButton("Zurück");
        zurueck.setBounds(150,280,100,35);
        einstellungenPanel.add(zurueck);
        JButton speichern = new JButton("Speichern");
        speichern.setBounds(30,280,110,35);
        speichern.setBackground(new Color(120,220,120));
        einstellungenPanel.add(speichern);
        speichern.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
        			Lernkartei l = new Lernkartei();
        			l.setGrossKleinschreibung(schreibung.isSelected());
        			l.setBeschreibung(txtname.getText());
        			l.setWortEinsBeschreibung(txtsprache1.getText());
        			l.setWortZweiBeschreibung(txtsprache2.getText());
        			l.setRichtung(wort_1.isSelected());
        			
        			//schaut ob alles passt
        			l.validiere();
        			VokabeltrainerDB.hinzufuegenLernkartei(l);
        			JOptionPane.showMessageDialog(Startseite_GUI.this, "Erfolgreich gespeichert"); 			
        		}catch(Exception ex) {
        			JOptionPane.showMessageDialog(Startseite_GUI.this, ex.getMessage(), "EingabeFehler", JOptionPane.WARNING_MESSAGE);
        			
        		}

			}
        		
        }
        );
        	
        
        
        // Zwischen Panels wechseln
        einstellungen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zeigePanel(einstellungenPanel);
            }
        });

        zurueck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zeigePanel(startPanel);
            }
        });

        // Startseite anzeigen
        zeigePanel(startPanel);
        
        
    }
    

    private void zeigePanel(JPanel panel) {
        setContentPane(panel);
        pack(); // JFrame passt sich an die Panel-Größe an

        setLocationRelativeTo(null);
        setLocation(getX(), 0);
    }

    public static void main(String[] args) {
        Startseite_GUI gui = new Startseite_GUI();
        gui.setVisible(true);
 
    }
}
