package labo1;

import java.awt.*;
import java.util.Scanner;

public abstract class Dessin {

    // Méthode abstraite pour dessiner le dessin
    public abstract void dessiner(Graphics2D graph);

    // Méthode abstraite pour lire le dessin
    public abstract void lire(Scanner reader);


}
