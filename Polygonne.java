package labo1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Polygonne extends NuagePoints {

    @Override
    public void dessiner(Graphics2D graph) {


        for(int i=1;i<nombrePoint; i++){
            new Ligne(tab.get(i-1), tab.get(i)).dessiner(graph);
        }
        new Ligne(tab.get(tab.size()-1), tab.get(0)).dessiner(graph);

    }
    public void lire(Scanner reader){

        super.lire(reader);


    }
}
