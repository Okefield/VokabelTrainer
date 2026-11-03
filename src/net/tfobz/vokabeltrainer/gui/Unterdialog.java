import javax.swing.JDialog;
import javax.swing.JFrame;

public class Unterdialog extends JDialog {
	public Unterdialog(JFrame owner) {
		super(owner);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Einstellungen");
		this.setLayout(null);
	}
	
	

}
