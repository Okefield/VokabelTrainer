package net.tfobz.vokabeltrainer.gui;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        List<Lernkartei> liste = VokabeltrainerDB.getLernkarteien();
        
        System.out.println("Anzahl Karteien in DB: " + liste.size());
        for (Lernkartei k : liste) {
            System.out.println("ID: " + k.getNummer() + " | Name: " + k.getBeschreibung());
        }
    }
}