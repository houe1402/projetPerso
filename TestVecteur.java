package labo2.tests;

import org.junit.Before;
import org.junit.Test;

import labo2.Vecteur;

import static org.junit.Assert.*;

public class TestVecteur {

	double[] s1 = { 1, 2, 3, 14 };
	Vecteur l1 = new Vecteur(s1);	
	
	
	@Before
	public void setup(){		
				
		l1 = new Vecteur(s1);	
		
	}
	//Exercice 1
	//Déjà fait en guise d'exemple.
	//Quelque-chose de semblable doit être fait du côté de TestMatrice.
	@Test
	public void testToString() {

		double[] s1l1 = { 1, 2, 3, 14 };
		Vecteur l1 = new Vecteur(s1l1);	
		
		String resAttendu = "[1.0 2.0 3 14.0]";

		assertTrue(l1.toString().equals(resAttendu));
		//assertFalse(l1.toString().equals(resAttendu));

	}

	@Test
	public void testEquals() {

		double[] s2 = { 1, 2, 3, 14 };
		double [] s3={1,2,3,14,15};
		double [] s5={1,2,3,14,15,16};
		double [] s4= {5};

		Vecteur l1 = new Vecteur(s1);

		
		Vecteur resAttendu = new Vecteur(s2);
		Vecteur resAttendu1= new Vecteur(s3);
		Vecteur resAttendu2 = new Vecteur(s4);
		Vecteur resAttendu3 = new Vecteur(s5);
		
		assertEquals(resAttendu,l1);
		assertNotEquals(resAttendu1,l1);
		assertNotEquals(resAttendu2,l1);
		assertNotEquals(resAttendu3,l1);



		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSousVecteurExceptionel() {


		double[] s2 = { 1, 2, 3, 5};
		Vecteur l2 = new Vecteur(s2);

		// Appel de la méthode sousVecteur avec un argument de 10
		Vecteur vec = l2.sousVecteur(10);
		Vecteur vec1 = l2.sousVecteur(-4);

		// Si la méthode ne lève pas l'exception, le test échouera

	}


	@Test
	public void testSousVecteurLimite() {

		double[] s1 = {};
		Vecteur l1 = new Vecteur(s1);

		// Appel de la méthode sousVecteur avec une taille limite de 0
		Vecteur v = l1.sousVecteur(0);

		// Vérifiez que le vecteur résultant est vide
		assertTrue(v.estVide());

		// Vérifiez que la taille du vecteur résultant est 0
		assertEquals(0, v.taille());


	}

	@Test
	public void testSousVecteurNormal() {

		double[] s1 = {1, 2, 3, 4, 5};
		double[] s2 = {1, 2};

		Vecteur l1 = new Vecteur(s1);

		// Appel de la méthode sousVecteur avec une taille inférieure à la taille du vecteur d'origine
		Vecteur v = l1.sousVecteur(2);

		// Vérifiez que le vecteur résultant est égal au vecteur attendu
		Vecteur resAttendu = new Vecteur(s2);
		assertEquals(resAttendu, v);

		// Vérifiez que la taille du vecteur résultant est égale à la taille spécifiée
		assertEquals(2, v.taille());
	}

	@Test
	public void testCreerVecteurNul() {
		Vecteur vect = Vecteur.creerVecteurNul(5);
		double[] Elements = {0.0, 0.0, 0.0, 0.0, 0.0};
		Vecteur ElementsVecteur = new Vecteur(Elements);
		assertEquals(vect, ElementsVecteur);
	}



	}
