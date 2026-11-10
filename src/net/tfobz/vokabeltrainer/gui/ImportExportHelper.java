package net.tfobz.vokabeltrainer.gui;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

public class ImportExportHelper {

    /**
     * Öffnet einen Dateiauswahldialog zum Importieren von Vokabeln aus einer .txt-Datei
     * und zeigt das Ergebnis bzw. Fehler an.
     *
     * @param parent   Übergeordnetes Fenster (z. B. Startseite / this)
     * @param karteiId Die ID / Nummer der Ziel-Lernkartei
     */
    public static void importieren(Component parent, int karteiId) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Vokabeln importieren (.txt)");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int auswahl = fileChooser.showOpenDialog(parent);
        if (auswahl != JFileChooser.APPROVE_OPTION) {
            return; 
        }

        File datei = fileChooser.getSelectedFile();
        String pfad = datei.getAbsolutePath();

        
        int ergebnis = VokabeltrainerDB.importierenKarten(karteiId, pfad);

        
        switch (ergebnis) {
            case -1:
                JOptionPane.showMessageDialog(
                    parent,
                    "Fehler beim Lesen der Datei:\nDie Datei existiert nicht oder konnte nicht geöffnet werden.",
                    "Importfehler (-1)",
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            case -2:
                JOptionPane.showMessageDialog(
                    parent,
                    "Fehlerhaftes Dateiformat:\nDie Zeilen entsprechen nicht dem geforderten Format (z. B. Trennzeichen fehlt).",
                    "Formatfehler (-2)",
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            case -3:
                JOptionPane.showMessageDialog(
                    parent,
                    "Datenbankfehler:\nDie Vokabeln konnten nicht in der Datenbank gespeichert werden.",
                    "Datenbankfehler (-3)",
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            default:
                if (ergebnis >= 0) {
                    JOptionPane.showMessageDialog(
                        parent,
                        "Erfolg: Es wurden " + ergebnis + " Vokabel(n) erfolgreich importiert!",
                        "Import erfolgreich",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        parent,
                        "Unbekannter Fehler beim Importieren (Code: " + ergebnis + ").",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                break;
        }
    }

    /**
     * Öffnet einen Dateiauswahldialog zum Exportieren von Vokabeln in eine .txt-Datei
     * und fragt ab, ob die Leitner-Fächer mitexportiert werden sollen.
     *
     * @param parent   Übergeordnetes Fenster (z. B. Startseite / this)
     * @param karteiId Die ID / Nummer der zu exportierenden Lernkartei
     */
    public static void exportieren(Component parent, int karteiId) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Vokabeln exportieren als...");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int auswahl = fileChooser.showSaveDialog(parent);
        if (auswahl != JFileChooser.APPROVE_OPTION) {
            return; 
        }

        File datei = fileChooser.getSelectedFile();
        String pfad = datei.getAbsolutePath();

        
        if (!pfad.toLowerCase().endsWith(".txt")) {
            pfad += ".txt";
        }

        
        int faecherAbfrage = JOptionPane.showConfirmDialog(
            parent,
            "Sollen die aktuellen Leitner-Fächer (Lernstände) mitexportiert werden?",
            "Export-Option",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        boolean mitFaecher = (faecherAbfrage == JOptionPane.YES_OPTION);

       
        int ergebnis = VokabeltrainerDB.exportierenKarten(karteiId, pfad, mitFaecher);

        
        switch (ergebnis) {
            case -1:
                JOptionPane.showMessageDialog(
                    parent,
                    "Schreibfehler:\nDie Datei konnte am angegebenen Ort nicht erstellt oder geschrieben werden.",
                    "Exportfehler (-1)",
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            case -2:
                JOptionPane.showMessageDialog(
                    parent,
                    "Hinweis:\nDiese Lernkartei enthält keine Vokabeln, die exportiert werden könnten.",
                    "Kartei ist leer (-2)",
                    JOptionPane.WARNING_MESSAGE
                );
                break;
            case -3:
                JOptionPane.showMessageDialog(
                    parent,
                    "Datenbankfehler:\nDie Vokabeln konnten nicht aus der Datenbank gelesen werden.",
                    "Datenbankfehler (-3)",
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            default:
                if (ergebnis >= 0) {
                    JOptionPane.showMessageDialog(
                        parent,
                        "Erfolg: Es wurden " + ergebnis + " Vokabel(n) erfolgreich exportiert!",
                        "Export erfolgreich",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        parent,
                        "Unbekannter Fehler beim Exportieren (Code: " + ergebnis + ").",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                break;
        }
    }
}