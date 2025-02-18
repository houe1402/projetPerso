package labo1;

import java.awt.Graphics2D;
import java.util.Scanner;

public class Point extends Dessin {
	
	private int x;
	private int y;
	
	public Point(){
		x=0;
		y=0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/*
	 * Fonction qui dessine le point sur la surface 2D.
	 */
	
	public void dessiner(Graphics2D graph){
		int ligne =10;

		graph.drawLine(x-ligne, y-ligne, x+ligne, y+ligne);
		graph.drawLine(x-ligne, y+ligne, x+ligne, y-ligne);
	}
	
	/*
	 * Fonction qui obtient les coordonn�es du point 
	 * � partir d'un flot d'entiers
	 */
	public void lire(Scanner reader) {		
		x=reader.nextInt();
		y=reader.nextInt();		
		
	}


	public String toString(){

		return "(" + x + "," + y + ")";
	}


}
