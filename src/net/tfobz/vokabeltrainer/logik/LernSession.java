package net.tfobz.vokabeltrainer.logik;
import net.tfobz.vokabeltrainer.model.*;
import net.tfobz.vokabeltrainer.gui.*;


public class LernSession {
	
	private Lernkartei lernkartei;
	private int aktuellesFach = 1; 
	private Karte aktuelleKarte;
	private int richtigCount = 0;
	private int falschCount = 0;
	
	public LernSession(Lernkartei kartei) {
		lernkartei = kartei;
	}
	
	public Karte naechsteKarte() {
		
		aktuelleKarte = VokabeltrainerDB.getZufaelligeKarte(lernkartei.getNummer(), aktuellesFach);
		return aktuelleKarte;
		
	}
	
	public boolean antwortPruefen(String eingabe, boolean richtung, boolean grossKlein) {
	    if (aktuelleKarte == null) {
	        return false;
	    }

	    
	    this.aktuelleKarte = new Karte(
	        aktuelleKarte.getNummer(),
	        aktuelleKarte.getWortEins(),
	        aktuelleKarte.getWortZwei(),
	        richtung,
	        grossKlein
	    );

	    
	    boolean istRichtig = aktuelleKarte.getRichtig(eingabe);

	    if (istRichtig) {
	        richtigCount++;
	        VokabeltrainerDB.setKarteRichtig(aktuelleKarte);
	    } else {
	        falschCount++;
	        VokabeltrainerDB.setKarteFalsch(aktuelleKarte);
	    }

	    return istRichtig;
	}
	
	
	 public Karte getAktuelleKarte() {
	        return aktuelleKarte;
	    }

	    public int getRichtigCount() {
	        return richtigCount;
	    }

	    public int getFalschCount() {
	        return falschCount;
	    }

	    public int getAktuellesFach() {
	        return aktuellesFach;
	    }

	    public void setAktuellesFach(int fach) {
	        this.aktuellesFach = fach;
	        
	        this.richtigCount = 0;
	        this.falschCount = 0;
	    }
	
	


}
