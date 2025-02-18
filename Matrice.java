package labo2;

/*
 * Classe représentant une matrice. Composée d'une liste
 * de vecteurs. Chaque rangée de la matrice est un vecteur.
 */
public class Matrice {

	private Vecteur[] lignes;

	public Matrice(double[][] dat) {

		lignes = new Vecteur[dat.length];

		for (int i = 0; i < dat.length; i++) {

			lignes[i] = new Vecteur(dat[i]);

		}
	}

	private Matrice(Vecteur[] vecteurs) {
		this.lignes = vecteurs.clone();
	}

	/*
	 * Élimination Gaussienne. Let me google that for you... Implémentation
	 * suivant le pseudo-code classique.
	 */
	public void Gauss() {

		int noLigne = 0;
		for (Vecteur ligne : lignes) {
			double pivot = ligne.getValeur(noLigne);
			if (pivot != 0) {
				double pivotInverse = 1.0 / pivot;
				for (int i = 0; i < ligne.taille(); i++) {
					ligne.setValeur(i, ligne.getValeur(i) * pivotInverse);
				}
			}

			for (Vecteur ligneElim : lignes) {
				if (ligneElim != ligne) {
					double f = ligneElim.getValeur(noLigne);
					for (int i = 0; i < ligneElim.taille(); ++i) {
						ligneElim.setValeur(i, ligneElim.getValeur(i) - f * ligne.getValeur(i));
					}
				}
			}
			noLigne++;
		}
	}
	
	public String toString() {

		String res = "";

		for (Vecteur v : lignes) {
			res += v + "\n";
		}

		return res;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Matrice mat = (Matrice) obj;
		if(mat.lignes.length == lignes.length){
			for (int i = 0; i < lignes.length; i++) {
			if (!lignes[i].equals(mat.lignes[i])) {
				return false;
			}
		}
		}
		else{
			return false;
		}
		return true;
	}
	public Matrice sousMatrice (int nbLignes, int nbColonnes){

		if (nbLignes > lignes.length ||  nbColonnes > lignes[0].taille() || nbLignes < 0 || nbColonnes < 0)
		{
			throw new IllegalArgumentException("blabla erreur bouhouhou");
		}

		if (lignes.length == 0 || lignes[0].taille() == 0)
		{     double sousElements[][] = new double[0][0];
			Vecteur v [] = new Vecteur[0];
			Matrice m = new Matrice(sousElements);

			return m;
		}
		double [][]temp = new double[nbLignes][nbColonnes];

		for (int i =0; i < nbLignes; i++)
		{
			temp[i]=lignes[i].sousVecteur(nbColonnes).getVecteurs();

		}

		Matrice mat = new Matrice(temp);
		return mat;

	}

	public int tailleMatrice() {
		return lignes.length;
	}

	public static Matrice creerMatriceNulle(int lignes, int Colonnes) {
		Vecteur[] temp = new Vecteur[lignes];
		for (int i = 0; i < lignes; i++) {
				temp[i] = Vecteur.creerVecteurNul(Colonnes);
			}
			Matrice mat = new Matrice(temp);
			return mat;
		}

		public static Matrice creerMatriceIdentite(int taille){

			Vecteur[] temp = new Vecteur[taille];
			for (int i = 0; i < taille; i++) {
				double[] identiteLigne = new double[taille];
				for (int j = 0; j < taille; j++) {
					if (i == j) {
						identiteLigne[j] = 1.0;
					} else {
						identiteLigne[j] = 0.0;
					}
				}
				temp[i] = new Vecteur(identiteLigne);
			}
			Matrice mat = new Matrice(temp);
			return mat;
		}




	}
