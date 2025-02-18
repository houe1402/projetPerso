package labo2;


/*
 * Classe représentant un vecteur. 
 */

public class Vecteur {

	private double valeurs[];


	public Vecteur(double[] valeurs) {

		this.valeurs = new double[valeurs.length];
		for (int i = 0; i < valeurs.length; i++) {

			this.valeurs[i] = valeurs[i];
		}
	}
	public Vecteur () {};

	public String toString() {

		String res = "[";
		for (double v : valeurs) {
			res += v + " ";
		}
		return res.substring(0, res.length() - 1) + "]";
	}
	public double [] getVecteurs() {
		return valeurs;
	}

	public int taille() {
		return valeurs.length;
	}

	public double getValeur(int pos) {
		return valeurs[pos];
	}

	public void setValeur(int pos, double val) {
		valeurs[pos] = val;
	}

	public static boolean egaliteDoublePrecision(double a, double b, double espilon) {
		return (Math.abs(a - b) <= espilon);
	}

	@Override
	public boolean equals(Object other) {
		boolean resultat = true;

		if (other instanceof Vecteur) {


			Vecteur v = (Vecteur) other;

			if (valeurs.length == v.taille()) {


				for (int i = 0; i < valeurs.length; i++) {
					if (!egaliteDoublePrecision(v.getValeur(i), valeurs[i], 0.0001))
						resultat = false;
				}
			}
			else{
				return false;
			}


		}
		else{
			return false;
		}
		return resultat;
	}

	public Vecteur sousVecteur(int taille) {
		if (taille > this.taille() || taille < 0) {
			;
			throw new IllegalArgumentException("Dimensions inadmissibles");
		}
		else {
			double temp[] = new double[taille] ;
			for (int i = 0; i < taille; i++ ) {
				temp[i] = valeurs[i];
			}
			Vecteur t = new Vecteur(temp);
			return t;
		}
	}

	public boolean estVide() {
		return false;
	}

	public static Vecteur creerVecteurNul(int taille){
		double[] nulData = new double[taille];
		Vecteur val = new Vecteur();
		val.valeurs = nulData;
		return val;
	}

}

