package net.tfobz.vokabeltrainer.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import net.tfobz.vokabeltrainer.logik.LernSession; 

public class LernErgebnisDialog extends JDialog {

    
    private static final Color BG_WINDOW        = new Color(243, 244, 246);
    private static final Color BG_CARD          = Color.WHITE;
    private static final Color BORDER_CARD      = new Color(209, 213, 219);
    private static final Color COLOR_PRIMARY    = new Color(37, 99, 235);
    private static final Color COLOR_SUCCESS    = new Color(22, 163, 74);
    private static final Color COLOR_TEXT_MAIN  = new Color(17, 24, 39);
    private static final Color COLOR_TEXT_MUTED = new Color(107, 114, 128);

    private final JFrame parent;
    private final LernSession session;

    public LernErgebnisDialog(JFrame parent, LernSession session) {
        super(parent, "Lerneinheit abgeschlossen", true);
        this.parent = parent;
        this.session = session;

        initUI();

        setSize(480, 310);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        getContentPane().setLayout(null);
        getContentPane().setBackground(BG_WINDOW);

       
        JPanel pnlCard = new JPanel();
        pnlCard.setLayout(null);
        pnlCard.setBackground(BG_CARD);
        pnlCard.setBorder(new LineBorder(BORDER_CARD, 1, true));
        pnlCard.setBounds(25, 20, 415, 170);

       
        JLabel lblTitel = new JLabel("Lerneinheit beendet!", SwingConstants.CENTER);
        lblTitel.setForeground(COLOR_TEXT_MAIN);
        lblTitel.setBounds(10, 15, 395, 25);
        pnlCard.add(lblTitel);

        
        JLabel lblFachInfo = new JLabel("Fach " + session.getAktuellesFach() + " vollständig durchgearbeitet", SwingConstants.CENTER);
        lblFachInfo.setForeground(COLOR_TEXT_MUTED);
        lblFachInfo.setBounds(10, 42, 395, 20);
        pnlCard.add(lblFachInfo);

        
        int richtig = session.getRichtigCount();
        int falsch = session.getFalschCount();
        int gesamt = richtig + falsch;
        int prozent = gesamt > 0 ? (richtig * 100 / gesamt) : 0;

        JLabel lblErgebnis = new JLabel(richtig + " von " + gesamt + " richtig (" + prozent + "%)", SwingConstants.CENTER);
        lblErgebnis.setForeground(prozent >= 50 ? COLOR_SUCCESS : new Color(220, 38, 38));
        lblErgebnis.setBounds(10, 75, 395, 35);
        pnlCard.add(lblErgebnis);

        String feedbackSpruch = prozent == 100 ? "Perfekt! Alle Vokabeln gewusst." :
                                prozent >= 70  ? "Sehr gut gemacht!" :
                                prozent >= 50  ? "Gute Leistung, weiter so!" : "Nicht aufgeben, Übung macht den Meister!";
        JLabel lblSpruch = new JLabel(feedbackSpruch, SwingConstants.CENTER);
        lblSpruch.setForeground(COLOR_TEXT_MUTED);
        lblSpruch.setBounds(10, 115, 395, 25);
        pnlCard.add(lblSpruch);

        getContentPane().add(pnlCard);

      
        JButton btnStartseite = new JButton("Zurück zur Startseite");
        btnStartseite.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnStartseite.setBackground(Color.WHITE);
        btnStartseite.setForeground(COLOR_TEXT_MAIN);
        btnStartseite.setFocusPainted(false);
        btnStartseite.setBounds(25, 205, 195, 38);
        btnStartseite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
        getContentPane().add(btnStartseite);

        JButton btnNaechstesFach = new JButton("Nächstes Fach lernen →");
        btnNaechstesFach.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNaechstesFach.setBackground(COLOR_PRIMARY);
        btnNaechstesFach.setForeground(Color.WHITE);
        btnNaechstesFach.setFocusPainted(false);
        btnNaechstesFach.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNaechstesFach.setBounds(230, 205, 210, 38);

        
        if (session.getAktuellesFach() >= 5) {
            btnNaechstesFach.setEnabled(false);
            btnNaechstesFach.setText("Höchstes Fach erreicht");
        } else {
            btnNaechstesFach.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
	                
	                session.setAktuellesFach(session.getAktuellesFach() + 1);
	                LernDialog naechsterDialog = new LernDialog(parent, session.getLernkartei());
	                naechsterDialog.setVisible(true);
					
				}
			});
        }
        getContentPane().add(btnNaechstesFach);
    }
}