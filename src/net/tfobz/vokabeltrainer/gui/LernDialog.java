package net.tfobz.vokabeltrainer.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

import net.tfobz.vokabeltrainer.logik.LernSession; 
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;

public class LernDialog extends JPanel {

    
    private static final Color BG_WINDOW        = new Color(243, 244, 246);
    private static final Color BG_CARD          = Color.WHITE;
    private static final Color BORDER_CARD      = new Color(209, 213, 219);
    private static final Color COLOR_PRIMARY    = new Color(37, 99, 235);   
    private static final Color COLOR_TEXT_MAIN  = new Color(17, 24, 39);
    private static final Color COLOR_TEXT_MUTED = new Color(107, 114, 128);
    private static final Color COLOR_SUCCESS    = new Color(22, 163, 74);   
    private static final Color COLOR_ERROR      = new Color(220, 38, 38);  

    private final Startseite_GUI parent;
    private final LernSession session;

    
    private JLabel lblKarteiTitel;
    private JLabel lblStatistik;
    private JRadioButton rbRichtungEinsZwei;
    private JRadioButton rbRichtungZweiEins;
    private JCheckBox cbGrossKlein;

    private JPanel pnlKarte;
    private JLabel lblKategorieHint;
    private JLabel lblFrage;
    private JTextField txtAntwort;
    private JLabel lblFeedback;

    private JButton btnAbbrechen;
    private JButton btnAktion;

    private boolean antwortGeprueft = false;

    public LernDialog(Startseite_GUI parent, Lernkartei gewaehlt) {
    	
        this.parent = parent;
        this.session = new LernSession(gewaehlt);
        
        ladeNaechsteKarte();
        
        setPreferredSize(new Dimension(560, 480));
        
    }

    private void initUI(Lernkartei gewaehlt) {
        
        setLayout(null);
        setBackground(BG_WINDOW);

       
        lblKarteiTitel = new JLabel(gewaehlt != null ? gewaehlt.getBeschreibung() : "Vokabeltrainer");
        lblKarteiTitel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblKarteiTitel.setForeground(COLOR_TEXT_MAIN);
        lblKarteiTitel.setBounds(30, 15, 300, 25);
        add(lblKarteiTitel);

        lblStatistik = new JLabel("Richtig: 0 | Falsch: 0", SwingConstants.RIGHT);
        lblStatistik.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblStatistik.setForeground(COLOR_TEXT_MUTED);
        lblStatistik.setBounds(330, 17, 185, 20);
        add(lblStatistik);

       
        rbRichtungEinsZwei = new JRadioButton("Wort 1 → Wort 2", true);
        rbRichtungEinsZwei.setBackground(BG_WINDOW);
        rbRichtungEinsZwei.setFocusPainted(false);
        rbRichtungEinsZwei.setBounds(30, 45, 130, 25);

        rbRichtungZweiEins = new JRadioButton("Wort 2 → Wort 1");
        rbRichtungZweiEins.setBackground(BG_WINDOW);
        rbRichtungZweiEins.setFocusPainted(false);
        rbRichtungZweiEins.setBounds(165, 45, 130, 25);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbRichtungEinsZwei);
        bg.add(rbRichtungZweiEins);

        cbGrossKlein = new JCheckBox("Groß-/Kleinschreibung");
        cbGrossKlein.setBackground(BG_WINDOW);
        cbGrossKlein.setFocusPainted(false);
        cbGrossKlein.setBounds(315, 45, 200, 25);

       
        ActionListener wechselListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aktualisiereFrageLabel();
				
			}
		};
        rbRichtungEinsZwei.addActionListener(wechselListener);
        rbRichtungZweiEins.addActionListener(wechselListener);

        add(rbRichtungEinsZwei);
        add(rbRichtungZweiEins);
        add(cbGrossKlein);

       
        pnlKarte = new JPanel();
        pnlKarte.setLayout(null); 
        pnlKarte.setBackground(BG_CARD);
        pnlKarte.setBorder(new LineBorder(BORDER_CARD, 1, true));
        pnlKarte.setBounds(30, 80, 485, 260);

        lblKategorieHint = new JLabel("GESUCHTES WORT", SwingConstants.CENTER);
        lblKategorieHint.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblKategorieHint.setForeground(COLOR_TEXT_MUTED);
        lblKategorieHint.setBounds(0, 20, 485, 15);
        pnlKarte.add(lblKategorieHint);

        lblFrage = new JLabel("...", SwingConstants.CENTER);
        lblFrage.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblFrage.setForeground(COLOR_TEXT_MAIN);
        lblFrage.setBounds(15, 45, 455, 45);
        pnlKarte.add(lblFrage);

        txtAntwort = new JTextField();
        txtAntwort.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtAntwort.setHorizontalAlignment(JTextField.CENTER);
        txtAntwort.setBorder(new CompoundBorder(
                new LineBorder(BORDER_CARD, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        
        txtAntwort.setBounds(63, 115, 360, 42);
        pnlKarte.add(txtAntwort);

        lblFeedback = new JLabel(" ", SwingConstants.CENTER);
        lblFeedback.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFeedback.setBounds(15, 185, 455, 35);
        pnlKarte.add(lblFeedback);

        add(pnlKarte);

        
        btnAbbrechen = new JButton("Abbrechen");
        btnAbbrechen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAbbrechen.setFocusPainted(false);
        btnAbbrechen.setBackground(Color.WHITE);
        btnAbbrechen.setForeground(COLOR_TEXT_MAIN);
        btnAbbrechen.setBounds(245, 360, 120, 38);
        btnAbbrechen.addActionListener(new ActionListener() {
           
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.zeigePanel(parent.getStartPanel());
				repaint();
				
			}
        });
        add(btnAbbrechen);

        btnAktion = new JButton("Prüfen");
        btnAktion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAktion.setFocusPainted(false);
        btnAktion.setBackground(COLOR_PRIMARY);
        btnAktion.setForeground(Color.WHITE);
        btnAktion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAktion.setBounds(380, 360, 135, 38);
        add(btnAktion);

       
        ActionListener aktionsEvent =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				verarbeiteEingabe();
				
			}
		};
        
        btnAktion.addActionListener(aktionsEvent);
        txtAntwort.addActionListener(aktionsEvent); 
    }

    private void verarbeiteEingabe() {
        if (!antwortGeprueft) {
            String eingabe = txtAntwort.getText().trim();
            if (eingabe.isEmpty()) {
                lblFeedback.setText("Bitte eine Antwort eingeben!");
                lblFeedback.setForeground(COLOR_ERROR);
                return;
            }

            boolean richtung = rbRichtungEinsZwei.isSelected();
            boolean grossKlein = cbGrossKlein.isSelected();

            boolean istRichtig = session.antwortPruefen(eingabe, richtung, grossKlein);
            Karte karte = session.getAktuelleKarte();

            if (istRichtig) {
                lblFeedback.setText("✓ Hervorragend, das ist richtig!");
                lblFeedback.setForeground(COLOR_SUCCESS);
                txtAntwort.setBackground(new Color(240, 253, 244));
            } else {
                String loesung = richtung ? karte.getWortZwei() : karte.getWortEins();
                lblFeedback.setText("✗ Falsch! Lösung: " + loesung);
                lblFeedback.setForeground(COLOR_ERROR);
                txtAntwort.setBackground(new Color(254, 242, 242));
            }

            lblStatistik.setText("Richtig: " + session.getRichtigCount() + " | Falsch: " + session.getFalschCount());

            txtAntwort.setEditable(false);
            btnAktion.setText("Weiter");
            btnAktion.setBackground(new Color(15, 118, 110)); 
            antwortGeprueft = true;
            btnAktion.requestFocusInWindow();
        } else {
            ladeNaechsteKarte();
        }
    }
    
    public void starteLernenFuerFach(int fach) {
        
        session.setAktuellesFach(fach);

        
        initUI(session.getLernkartei());
        ladeNaechsteKarte();
        revalidate();
        repaint();
    }

    private void ladeNaechsteKarte() {
        Karte karte = session.naechsteKarte();

        if (karte == null) {
            
            LernErgebnisDialog ergebnisDialog = new LernErgebnisDialog(parent, session);
            parent.zeigePanel(ergebnisDialog);
        }

        aktualisiereFrageLabel();
        txtAntwort.setText("");
        txtAntwort.setEditable(true);
        txtAntwort.setBackground(Color.WHITE);
        lblFeedback.setText(" ");
        btnAktion.setText("Prüfen");
        btnAktion.setBackground(COLOR_PRIMARY);
        antwortGeprueft = false;
        txtAntwort.requestFocusInWindow();
    }

    private void aktualisiereFrageLabel() {
        Karte karte = session.getAktuelleKarte();
        if (karte != null) {
            if (rbRichtungEinsZwei.isSelected()) {
                lblFrage.setText(karte.getWortEins());
            } else {
                lblFrage.setText(karte.getWortZwei());
            }
        }
    }
}