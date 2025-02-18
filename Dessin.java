package labo1;

import java.awt.*;
import java.util.Scanner;

public abstract class Dessin {

    // M�thode abstraite pour dessiner le dessin
    public abstract void dessiner(Graphics2D graph);

    // M�thode abstraite pour lire le dessin
    public abstract void lire(Scanner reader);


}
