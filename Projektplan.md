code
Bash
cat << 'EOF' > PROJEKTPLAN.md
# 📚 Projektplan: Vokabeltrainer

Dieses Dokument regelt die Architektur, die Aufgabenverteilung für zwei Personen und die genauen Schnittstellen für das Schulprojekt.

---

## 📌 Status Quo (Bereits fertig)

Die Datenbankschicht und die Kern-Datenmodelle im Package `net.tfobz.vokabeltrainer.model` sind bereits vollständig implementiert und durch Unit-Tests abgesichert:

* **`Fach.java`**: Verwaltet Intervall, Lerndatum und berechnet Fälligkeiten (`getErinnerungFaellig()`).
* **`Karte.java`**: Enthält Wortpaare, Richtung, Case-Sensitivität und Prüflogik (`getRichtig()`).
* **`Lernkartei.java`**: Verwaltet Metadaten (Name, Beschreibungen der Sprachen, Default-Optionen).
* **`VokabeltrainerDB.java`**: Führt alle HSQLDB-Routinen aus (Leitner-Stufen, CRUD, Import/Export, Fälligkeiten).

---

## 🏗️ Klassen- und Paketstruktur

```text
src/net/tfobz/vokabeltrainer/
│
├── model/                     # [BEREITS FERTIG]
│   ├── Fach.java
│   ├── Karte.java
│   ├── Lernkartei.java
│   └── VokabeltrainerDB.java
│
├── business/                  # [LOGIK & ABLAUF]
│   └── LernSession.java       # [Person 2] State-Machine für den aktiven Lernvorgang
│
└── gui/                       # [BENUTZEROBERFLÄCHE]
    ├── Startseite.java             # [Person 1] Hauptfenster mit Übersicht aller Karteien
    ├── LernkarteiDialog.java       # [Person 1] Dialog zum Erstellen/Bearbeiten einer Kartei
    ├── KartenVerwaltungDialog.java # [Person 1] Vokabeln & Fächer einsehen und bearbeiten
    ├── ImportExportHelper.java     # [Person 1] JFileChooser & I/O-Fehlerbehandlung
    ├── LernDialog.java             # [Person 2] Aktive Vokabelabfrage (Frage/Antwort)
    └── LernErgebnisDialog.java     # [Person 2] Zusammenfassung nach Durchlauf
👥 Aufgabenverteilung
Komponente / Klasse	Zuständige Person	Hauptaufgabe	Direkter DB-Zugriff?
Balsamiq Mockups	Person 1	Wireframes (.bmpr) für alle Hauptansichten zeichnen	❌ Nein
Startseite.java	Person 1	Hauptmenü, Tabelle aller Karteien, Filter für Fälligkeit	✅ Ja
LernkarteiDialog.java	Person 1	Maske: Name, Sprachen-Labels, Standard-Richtung	✅ Ja
KartenVerwaltungDialog.java	Person 1	Vokabeln der Kartei listen, anlegen, löschen; Fächer ansehen	✅ Ja
ImportExportHelper.java	Person 1	Dateiauswahl (JFileChooser), Import/Export mit Fehlertexten	✅ Ja
LernSession.java	Person 2	Lernablauf-Steuerung: Zufallskarte holen, Antwort werten, Leitner-Update	✅ Ja
LernDialog.java	Person 2	UI für Abfrage: Eingabefeld, Richtungswechsel, Grün/Rot-Feedback	❌ Nein (über LernSession)
LernErgebnisDialog.java	Person 2	Modal: Statistik am Ende eines Fachs ("X von Y gewusst")	❌ Nein
📋 Detaillierte Arbeitsaufträge
👤 Person 1: Verwaltung, Daten-I/O & Mockups
1. Balsamiq Mockups (.bmpr)

Screen 1 (Startseite): Übersicht aller Karteien, Buttons (Neu, Bearbeiten, Löschen, Import, Export, Lernen), Checkbox für fällige Karteien.

Screen 2 (Verwaltung): Detailansicht einer Kartei mit Fächerübersicht (Fach 1–5, Intervall, Datum) und Vokabel-Tabelle.

Screen 3 (Lernansicht): Große Karteikarte mit Frage, Antwort-Eingabefeld, Buttons für Richtung/Groß-Kleinschreibung und Feedback-Bereich.
2. Startseite.java (JFrame)

JTable mit Liste aller Karteien einbinden (VokabeltrainerDB.getLernkarteien()).

Checkbox "Nur fällige anzeigen" implementieren: Ruft bei Aktivierung VokabeltrainerDB.getLernkarteienErinnerung() auf und aktualisiert die Tabelle.

Button "Neue Kartei" öffnet den LernkarteiDialog.

Button "Löschen" ruft VokabeltrainerDB.loeschenLernkartei(id) mit Bestätigungsdialog auf.

Button "Lernen starten" übergibt die markierte Kartei an den LernDialog von Person 2.
3. LernkarteiDialog.java & KartenVerwaltungDialog.java (JDialog)

Maske zum Anlegen/Editieren einer Kartei (Titel, Wort1-Label, Wort2-Label, RadioButtons für Standard-Richtung und Checkbox für Groß-/Kleinschreibung).

Validierung vor dem Speichern über lernkartei.validiere().

Vokabelverwaltung: Tabelle aller Vokabeln anzeigen; Eingabefelder für neues Wortpaar mit Speichern via VokabeltrainerDB.hinzufuegenKarte().
4. ImportExportHelper.java (Utility)

JFileChooser für Dateiauswahl (.txt) implementieren.

Aufruf von VokabeltrainerDB.importierenKarten(karteiId, pfad).

Aufruf von VokabeltrainerDB.exportierenKarten(karteiId, pfad, mitFaecher).

Fehlercodes (-1, -2, -3) abfangen und als verständliche JOptionPane-Meldungen anzeigen.
👤 Person 2: Lernmodus & Leitner-Ablauf
1. LernSession.java (Business-Logik)

Hält die Referenz auf die aktive Lernkartei, das gewählte Fach und Trefferzähler (richtigCount, falschCount).

Methode naechsteKarte(): Zieht die nächste Karte über VokabeltrainerDB.getZufaelligeKarte().

Methode antwortPruefen(String eingabe):
Aktualisiert Richtung und Groß-/Kleinschreibung auf dem aktuellen Karte-Objekt.
Prüft das Wort über karte.getRichtig(eingabe).
Bei true: Erhöht richtigCount, ruft VokabeltrainerDB.setKarteRichtig(karte).
Bei false: Erhöht falschCount, ruft VokabeltrainerDB.setKarteFalsch(karte).
Gibt boolean für die GUI zurück.
2. LernDialog.java (GUI)

Konstruktor nimmt übergeordnete Startseite und gewählte Lernkartei entgegen.

Große Textanzeige (JLabel) für das gesuchte Wort.

Eingabefeld (JTextField) für die Nutzereingabe (Reaktion auf Klick und Enter-Taste).

Umschalter für Lernrichtung (
1
→
2
1→2
 oder 
2
→
1
2→1
) und Checkbox für Groß-/Kleinschreibung.

Visuelle Rückmeldung nach Eingabe:
Grün ("Richtig!") bei Treffer.
Rot ("Falsch! Lösung: ...") bei Fehleingabe.

Button "Weiter" lädt über die LernSession die nächste Vokabel.
3. LernErgebnisDialog.java (JDialog)

Öffnet sich automatisch, wenn naechsteKarte() den Wert null liefert (Fach fertig gelernt).

Zusammenfassung anzeigen: "Lerneinheit beendet! X von Y Vokabeln richtig."

Buttons: "Nächstes Fach lernen" oder "Zurück zur Startseite".
🔌 Schnittstelle zwischen beiden Personen
Damit beide unabhängig voneinander entwickeln können, ist dies der einzige Berührungspunkt im Code:
code
Java
// Aufruf in Startseite.java (Person 1 ruft Person 2 auf):
btnLernen.addActionListener(e -> {
    Lernkartei gewaehlt = getAusgewaehlteKartei();
    if (gewaehlt != null) {
        LernDialog dialog = new LernDialog(this, gewaehlt);
        dialog.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Bitte zuerst eine Lernkartei auswählen!");
    }
});
🌿 Git-Workflow
Feature-Branches anlegen:
code
Bash
# Person 1
git checkout -b feature/verwaltung-und-io

# Person 2
git checkout -b feature/lernansicht
Saubere Trennung einhalten:
Person 1 arbeitet ausschließlich an Startseite, LernkarteiDialog, KartenVerwaltungDialog, ImportExportHelper und den Mockups.
Person 2 arbeitet ausschließlich an LernSession, LernDialog, LernErgebnisDialog.
Merge:
Erst in main mergen, wenn der eigene Teil fehlerfrei kompiliert.
EOF
