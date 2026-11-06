package net.tfobz.vokabeltrainer.gui;

import net.tfobz.vokabeltrainer.logik.*;
import javax.swing.*;

public class LernDialog extends JDialog{
	
	private LernSession session;

	
	private JLabel lblFrage;
	private JTextField txtAntwort;
	private JLabel lblFeedback;
	private JButton btnAktion;
	private JRadioButton rbRichtungEinsZwei;
	private JRadioButton rbRichtungZweiEins;
	private JCheckBox cbGrossKlein;
	
	public LernDialog(JFrame owner) {
		super(owner);
		setModal(true);
		setResizable(false);
		setBounds(owner.getBounds());
		setLocationRelativeTo(null);
		setTitle(session.getLernkartei().getBeschreibung());
		
		
		
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		
		
	}
	
}
