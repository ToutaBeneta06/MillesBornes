package jeu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import cartes.Carte;

public class Sabot implements Iterable<Carte> {

    private Carte[] cartes;
    private int nbCartes;
    private int modCount = 0; 

    public Sabot(Carte[] cartes) {
        this.cartes = cartes;
        this.nbCartes = cartes.length;
    }

    public boolean estVide() {
        return nbCartes == 0;
    }

    public void ajouterCarte(Carte carte) {
        if (nbCartes >= cartes.length) {
            throw new IllegalStateException("Sabot plein : impossible d'ajouter une carte");
        }
        cartes[nbCartes] = carte;
        nbCartes++;
        modCount++;
    }

   
    public Carte piocher() {
        Iterator<Carte> it = iterator();
        Carte carte = it.next();
        it.remove();
        return carte;
    }

    @Override
    public Iterator<Carte> iterator() {
        return new SabotIterator();
    }

    private class SabotIterator implements Iterator<Carte> {
        private int curseur = 0;
        private int expectedModCount = modCount;
        private boolean peutSupprimer = false;

        @Override
        public boolean hasNext() {
            return curseur < nbCartes;
        }

        @Override
        public Carte next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException("Plus de carte dans le sabot");
            }
            Carte carte = cartes[curseur];
            curseur++;
            peutSupprimer = true;
            return carte;
        }

        @Override
        public void remove() {
            if (!peutSupprimer) {
                throw new IllegalStateException("next() doit être appelé avant remove()");
            }
            checkForComodification();

           
            for (int i = curseur - 1; i < nbCartes - 1; i++) {
                cartes[i] = cartes[i + 1];
            }
            cartes[nbCartes - 1] = null;
            nbCartes--;
            curseur--;

            modCount++;
            expectedModCount = modCount;
            peutSupprimer = false;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
