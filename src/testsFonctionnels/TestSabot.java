package testsFonctionnels;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import cartes.Carte;
import cartes.JeuDeCartes;
import jeu.Sabot;

public class TestSabot {

    public static void main(String[] args) {
        JeuDeCartes jeu = new JeuDeCartes();

    
        Sabot sabot = new Sabot(jeu.donnerCartes());
        while (!sabot.estVide()) {
            Carte carte = sabot.piocher();
            System.out.println("je pioche " + carte);
        }

       
        Sabot sabot2 = new Sabot(jeu.donnerCartes());
        Iterator<Carte> it = sabot2.iterator();
        while (it.hasNext()) {
            Carte carte = it.next();
            System.out.println("je pioche " + carte);
            it.remove();
        }

     
        Sabot sabot3 = new Sabot(jeu.donnerCartes());
        sabot3.piocher();
        try {
        	
            Iterator<Carte> it2 = sabot3.iterator();
            while (it2.hasNext()) {
                it2.next();
                sabot3.piocher();     }
        } catch (ConcurrentModificationException e) {
            System.out.println("Exception attendue en mélangeant piocher() et itérateur : " + e);
        }
    }
}
