package net.tfobz.vokabeltrainer.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

import net.tfobz.vokabeltrainer.model.Lernkartei;

public class FachAuswahlPanel extends JPanel {

   
    private static final Color BG_WINDOW        = new Color(243, 244, 246);
    private static final Color BG_CARD          = Color.WHITE;
    private static final Color BORDER_CARD      = new Color(209, 213, 219);
    private static final Color COLOR_PRIMARY    = new Color(37, 99, 235);
    private static final Color COLOR_TEXT_MAIN  = new Color(17, 24, 39);
    private static final Color COLOR_TEXT_MUTED = new Color(107, 114, 128);

    private final LernDialog dialog;
    private final Lernkartei lernkartei;
    private Startseite_GUI parent;

    private JRadioButton rbFach1;
    private JRadioButton rbFach2;
    private JRadioButton rbFach3;
    private JRadioButton rbFach4;
    private JRadioButton rbFach5;

    public FachAuswahlPanel(LernDialog dialog, Lernkartei lernkartei, Startseite_GUI parent) {
        this.dialog = dialog;
        this.lernkartei = lernkartei;
        this.parent = parent;

        initUI();
    }

    private void initUI() {
    	setPreferredSize(new Dimension(545, 430));   // ← neu
        setLayout(null);
        setBackground(BG_WINDOW);
    
        JLabel lblTitel = new JLabel("Lernstufe auswählen");
        lblTitel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitel.setForeground(COLOR_TEXT_MAIN);
        lblTitel.setBounds(30, 15, 300, 25);
        add(lblTitel);

        JLabel lblUntertitel = new JLabel("Kartei: " + (lernkartei != null ? lernkartei.getBeschreibung() : ""));
        lblUntertitel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUntertitel.setForeground(COLOR_TEXT_MUTED);
        lblUntertitel.setBounds(30, 42, 450, 20);
        add(lblUntertitel);

        
        JPanel pnlCard = new JPanel();
        pnlCard.setLayout(null);
        pnlCard.setBackground(BG_CARD);
        pnlCard.setBorder(new LineBorder(BORDER_CARD, 1, true));
        pnlCard.setBounds(30, 75, 485, 270);

        JLabel lblHinweis = new JLabel("Wähle das Leitner-Fach, das du jetzt üben möchtest:");
        lblHinweis.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHinweis.setForeground(COLOR_TEXT_MAIN);
        lblHinweis.setBounds(25, 15, 435, 25);
        pnlCard.add(lblHinweis);

       
        rbFach1 = new JRadioButton("Fach 1  (Täglich – Neue oder ungelernte Vokabeln)", true);
        rbFach2 = new JRadioButton("Fach 2  (Alle 3 Tage – Erste Wiederholung)");
        rbFach3 = new JRadioButton("Fach 3  (Jede Woche – Vertiefung)");
        rbFach4 = new JRadioButton("Fach 4  (Alle 2 Wochen – Festigung)");
        rbFach5 = new JRadioButton("Fach 5  (Monatlich – Fast gemeistert)");

        JRadioButton[] fächerButtons = { rbFach1, rbFach2, rbFach3, rbFach4, rbFach5 };
        ButtonGroup gruppe = new ButtonGroup();

        int yPosition = 50;
        for (JRadioButton rb : fächerButtons) {
            rb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            rb.setBackground(BG_CARD);
            rb.setForeground(COLOR_TEXT_MAIN);
            rb.setFocusPainted(false);
            rb.setBounds(25, yPosition, 435, 35);
            gruppe.add(rb);
            pnlCard.add(rb);
            yPosition += 40;
        }

        add(pnlCard);

       
        JButton btnAbbrechen = new JButton("Abbrechen");
        btnAbbrechen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAbbrechen.setBackground(Color.WHITE);
        btnAbbrechen.setForeground(COLOR_TEXT_MAIN);
        btnAbbrechen.setFocusPainted(false);
        btnAbbrechen.setBounds(225, 365, 120, 38);
        btnAbbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.zeigePanel(parent.getStartPanel());
            }
        });
        add(btnAbbrechen);

        JButton btnStarten = new JButton("Lernen starten →");
        btnStarten.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnStarten.setBackground(COLOR_PRIMARY);
        btnStarten.setForeground(Color.WHITE);
        btnStarten.setFocusPainted(false);
        btnStarten.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnStarten.setBounds(355, 365, 160, 38);
        btnStarten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int gewaehltesFach = 1;
                if (rbFach2.isSelected()) gewaehltesFach = 2;
                else if (rbFach3.isSelected()) gewaehltesFach = 3;
                else if (rbFach4.isSelected()) gewaehltesFach = 4;
                else if (rbFach5.isSelected()) gewaehltesFach = 5;

                
                dialog.starteLernenFuerFach(gewaehltesFach);
            }
        });
        add(btnStarten);
    }
}