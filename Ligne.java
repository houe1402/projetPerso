package labo1;

import java.awt.*;
import java.util.Scanner;

public  class Ligne extends Dessin {
    Point p1;
    Point p2;

    public Ligne(){
        p1= new Point();
        p2= new Point();
    }



    public Ligne(Point pointDebut, Point pointFin) {
        p1 = pointDebut;
        p2 = pointFin;
    }


    public void dessiner(Graphics2D graph){
        int ligne =10;

        graph.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /*
     * Fonction qui obtient les coordonn�es du point
     * � partir d'un flot d'entiers
     */
    public void lire(Scanner reader) {
        p1.lire(reader);
        p2.lire(reader);


    }

    public String toString(){
        return p1.toString() + " -> " + p2.toString();

    }



}
