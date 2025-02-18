package labo1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public  class DessinCompose extends Dessin {

    private ArrayList<Dessin> dessins = new ArrayList<>();
    @Override
    public void dessiner(Graphics2D graph) {
        for (Dessin dessin : dessins) {
            dessin.dessiner(graph);
        }
    }

    @Override
    public void lire(Scanner reader) {



        while (reader.hasNextInt()) {
            int code = reader.nextInt();
            Dessin dessin;

            switch (code) {
                case -1:
                    dessin = new Point(0, 0);
                    break;
                case -2:
                    dessin = new Ligne();
                    break;
                case -3:
                    dessin = new NuagePoints();
                    break;
                case -4:
                    dessin = new Polygonne();
                    break;
                case -5:
                    return;
                default:
                    continue;
            }

            dessin.lire(reader);
            dessins.add(dessin);
        }

    }
}


