import javax.swing.*;
import java.awt.*;
public class Startseite_GUI extends JFrame{
	private JComboBox<String> sprache = null;
	private JComboBox<String> fach = null;
	private JButton lern = null;
	JButton einstellungen = null;
	private JLabel text = null;
	
	public Startseite_GUI() {
		this.setTitle("Vokabeltrainer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		setSize(400, 250);
		setLocationRelativeTo(null);
		setLocation(getX(), 0);		
		lern = new JButton("Lern");
		lern.setBounds(50,150,80,40);
		lern.setBackground(Color.green);
		add(lern);;
		fach = new JComboBox<String>();
		fach.addItem("1 Fach");
		fach.addItem("2 Fach");
		fach.addItem("3 Fach");
		fach.addItem("4 Fach");
		fach.setBounds(50,50,80,40);
		sprache = new JComboBox<String>();
		sprache.setBounds(50,100,80,40);
		text = new JLabel("Wählen Sie folgende Optionen aus um zu lernen!");
		text.setBounds(50,10,320,15);
		JButton einstellungen = new JButton("⚙");
		einstellungen.setBounds(300,50,50,50);
		einstellungen.setBackground(Color.lightGray);
		add(fach);
		add(sprache);
		add(text);
		add(einstellungen);
		einstellungen.addActionListener(
				new ActionListener() {
					public void actionPerformed(actionEvent e) {
						
					}
				}
	}
	public static void main(String[] args) {
		Startseite_GUI gui = new Startseite_GUI();
		gui.setVisible(true);
	}
	
	
}

