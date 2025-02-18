package labo1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public  class NuagePoints extends Dessin {

    protected ArrayList<Point> tab;
    protected int nombrePoint ;

    public NuagePoints(){
        tab = new ArrayList<Point>();
        nombrePoint=0;
    }

    public void dessiner(Graphics2D graph) {

        for (int i = 0; i < nombrePoint; i++) {

            graph.drawLine(tab.get(i).getX() , tab.get(i).getY() , tab.get(i).getX() , tab.get(i).getY() );

           tab.get(i).dessiner(graph );


        }
    }
        public void lire(Scanner reader) {
            nombrePoint = reader.nextInt();
            for (int i = 0; i < nombrePoint; ++i) {
                Point p1 = new Point();
                p1.lire(reader);
                tab.add(p1);
            }
        }


    public String toString(){

        StringBuilder result = new StringBuilder();
        for (Point point : tab) {
            result.append(point.toString()).append("\n");
        }
        return result.toString();
    }

    public ArrayList<Point> getTab() {
        return tab;
    }
}






