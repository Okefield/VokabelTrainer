package net.tfobz.vokabeltrainer.gui;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startseite_GUI extends JFrame {

    private JPanel startPanel;
    private JPanel einstellungenPanel;
   

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
    private JButton loeschen;
    //table
    private JTable t1;
    private DefaultTableModel tableModel;
    private java.util.List<Lernkartei> angezeigteKarteien = new java.util.ArrayList<>();
    private JCheckBox faellige;

    public Startseite_GUI() {
        setTitle("Vokabeltrainer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Startseite erstellen
        startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setPreferredSize(new Dimension(520, 350));

        JLabel text = new JLabel("Ihre Lernkarteien");
        text.setBounds(30, 15, 200, 25);
        startPanel.add(text);
        //Checkbox ob nur fällige angezeigt werden:
        faellige = new JCheckBox("Nur fällige anzeigen");
        faellige.setBounds(300,15,190,25);
        startPanel.add(faellige);
        
        //lern Button
        JButton lern = new JButton("Lern");
        lern.setBounds(30, 280, 110, 35);
        lern.setBackground(new Color(120, 220, 120));
        startPanel.add(lern);
        
        //scrollpane
        JButton neu = new JButton("Neu");
        neu.setBounds(300, 50, 50, 50);
        neu.setBounds(340, 280, 150, 35);
        startPanel.add(neu);
        
        //löschen Button
        loeschen = new JButton("Löschen");
        loeschen.setBounds(150, 280, 100, 35);
        startPanel.add(loeschen);
        //Actionlistener für alle
        
        loeschen.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		Lernkartei gewaehlt = getAusgewaehlteKartei();
        		if(gewaehlt != null) {
        			int antwort = JOptionPane.showConfirmDialog(Startseite_GUI.this, "Möchten Sie die Kartei "+ gewaehlt.getBeschreibung() + " wirklich löschen?","Löschen Bestätigen", JOptionPane.YES_NO_OPTION);
        			if(antwort == JOptionPane.YES_OPTION) {
        				VokabeltrainerDB.loeschenLernkartei(gewaehlt.getNummer());
        				tabelleAktualisieren();
        
        			}
        		} else {
        			JOptionPane.showMessageDialog(Startseite_GUI.this, "Bitte markieren Sie zuerst eine Kartei in der Tabelle");
        			
        		}
        		
        	}
        	
        }
        );
        faellige.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		tabelleAktualisieren();
        	}
        }
        );
        lern.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Lernkartei gewaehlt = getAusgewaehlteKartei();
        		
        		if(gewaehlt != null) {
        			LernDialog dialog = new LernDialog(Startseite_GUI.this,gewaehlt);
        			zeigePanel(dialog);
        			tabelleAktualisieren();
        			
        		}else {
        			JOptionPane.showMessageDialog(Startseite_GUI.this, "Bitte zuerst Lernkartei auswählen!");
        		}
        		
        		
        	}
        });
        

        
        
        
        //JTable
        String [] spalten = {"ID", "Name der Kartei", "Sprache1", "Sprache2"};
        tableModel = new DefaultTableModel(spalten,0);
        t1 = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(t1);
        scrollPane.setBounds(30, 50, 460, 210);
        scrollPane.setBounds(30, 45, 420, 180); 
        startPanel.add(scrollPane);
        
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
        			
        			//scyhaut ob alles passt
        			l.validiere();
        			int ergebnis = VokabeltrainerDB.hinzufuegenLernkartei(l);

                    if (ergebnis >= 0) {
                        JOptionPane.showMessageDialog(Startseite_GUI.this, "Erfolgreich gespeichert!");
                        
                        // Textfelder leeren
                        txtname.setText("");
                        txtsprache1.setText("");
                        txtsprache2.setText("");
                        
                        // Tabelle aktualisieren und zurück zur Startseite
                        tabelleAktualisieren();
                        zeigePanel(startPanel);
                    } else {
                        JOptionPane.showMessageDialog(
                            Startseite_GUI.this, 
                            "Fehler: Eine Kartei mit diesem Namen existiert bereits!", 
                            "Fehler", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    } 			
        		}catch(Exception ex) {
        			JOptionPane.showMessageDialog(Startseite_GUI.this, ex.getMessage(), "EingabeFehler", JOptionPane.WARNING_MESSAGE);
        			
        		}

			}
        		
        }
        );
        	
        
        
        // Zwischen Panels wechseln
        neu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zeigePanel(einstellungenPanel);
            }
        });

        zurueck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	txtname.setText("");
            	txtsprache1.setText("");
            	txtsprache2.setText("");
            	wort_1.setSelected(true);
            	schreibung.setSelected(false);
            	
            	
            	tabelleAktualisieren();
                zeigePanel(startPanel);
            }
        });
        tabelleAktualisieren();
        // Startseite anzeigen
        zeigePanel(startPanel);
        
        
    }
    public void tabelleAktualisieren() {
        tableModel.setRowCount(0);
        if (faellige.isSelected()) {
            angezeigteKarteien = VokabeltrainerDB.getLernkarteienErinnerung();
        } else {
            angezeigteKarteien = VokabeltrainerDB.getLernkarteien();
        }
        
        if (angezeigteKarteien != null) {
            for (Lernkartei k : angezeigteKarteien) {
                tableModel.addRow(new Object[]{
                    k.getNummer(),
                    k.getBeschreibung(),
                    k.getWortEinsBeschreibung(),
                    k.getWortZweiBeschreibung()
                });
            }
        }
    }
    public Lernkartei getAusgewaehlteKartei() {
        int zeile = t1.getSelectedRow(); 
        

        if (zeile >= 0 && zeile < angezeigteKarteien.size()) {
            return angezeigteKarteien.get(zeile);
        }
        return null; 
    }
    

    void zeigePanel(JPanel panel) {
        setContentPane(panel);
        pack(); 

        setLocationRelativeTo(null);
        setLocation(getX(), 0);
    }
    
    JPanel getStartPanel() {
    	return startPanel;
    }

    public static void main(String[] args) {
        Startseite_GUI gui = new Startseite_GUI();
        gui.setVisible(true);
 
    }
}
