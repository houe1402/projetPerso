package labo2.tests;

import labo2.Matrice;
import labo2.Vecteur;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMatrice {

    double[][] systeme1 = {

            {3, 5, -3, 15},
            {7, 10, 1, 2},
            {-3, 2, -5, 6}
    };
    Matrice mat = new Matrice(systeme1);


    @Before
    public void setup() {

        mat = new Matrice(systeme1);

    }


    @Test
    public void testToString() {

        double[][] s1l1 = {
                { 3, 5, -3, 15 },
                { 7, 10, 1, 2},
                {-3, 2, -5, 6}
        };

        Matrice l1 = new Matrice(s1l1);

        String resAttendu = "[3.0 5.0 -3.0 15.0]\n"
                + "[7.0 10.0 1.0 2.0]\n"
                + "[-3.0 2.0 -5.0 6.0]\n";

        assertTrue(l1.toString().equals(resAttendu));



    }

    @Test
    public void testEquals() {

        double[][] systeme2 = {
                { 3, 5, -3, 15 },
                { 7, 10, 1, 2 },
                { -3, 2, -5, 6 }
        };

        Matrice mat = new Matrice(systeme1);

        Matrice resAttendu = new Matrice(systeme2);

        assertEquals(resAttendu,mat);


    }

    @Test
    public void testGauss() {

        double[][] s3 = {
                {3, 5, -3, 15},
                {7, 10, 1, 2},
                {-3, 2, -5, 6}
        };

        Matrice l1 = new Matrice(s3);
        l1.Gauss();

        double[][] resAttendu = {
                {1, 0, 0, 4.15625},
                {0, 1, 0, -2.25},
                {0, 0, 1, -4.59375}

        };


        double[][] resAttendu1 = {
                {1, 0, 0, 4.15625,5},
                {0, 1, 0, -2.25,7},
                {0, 0, 1, -4.59375}

        };
        double[][] resAttendu2 = {
                {1, 0, 0, 4.15625,5},


        };



        Matrice l2 = new Matrice(resAttendu);
        Matrice l3 = new Matrice(resAttendu1);
        Matrice l4 = new Matrice(resAttendu2);

        assertEquals(l1, l2);
        assertNotEquals(l1, l3);
        assertNotEquals(l1, l4);
    }


    @Test
    public void testSousMatriceLimite()
    {
        double [][] s3 = {
                { 3, 5, -3, 15 },
                { 7, 10, 1, 2 },
                { -3, 2, -5, 6 }
        };

        Matrice l1 = new Matrice(s3);
        Matrice m;
        m = l1.sousMatrice(0, 0);

        double[][] resAttendu = {};

        Matrice l2 = new Matrice(resAttendu);
        assertEquals(m,l2);
    }

    @Test
    public void testSousMatriceNormal()
    {
        double[][] s3 = {
                { 3, 5, -3, 15 },
                { 7, 10, 1, 2 },
                { -3, 2, -5, 6 }
        };

        Matrice l1 = new Matrice(s3);

        // Appel de la méthode sousMatrice avec des indices valides
        Matrice m = l1.sousMatrice(3, 2);

        // Vérifiez que la matrice résultante est égale à la matrice attendue
        double[][] resAttendu = {
                { 3, 5 },
                { 7, 10 },
                { -3, 2 }
        };

        Matrice l2 = new Matrice(resAttendu);
        assertEquals(l2, m);

        // Vérifiez que la taille de la matrice résultante est égale au nombre de colonnes de la matrice d'origine
        assertEquals(l1.tailleMatrice(), m.tailleMatrice());

    }
    @Test(expected=IllegalArgumentException.class)
    public void testMatriceExceptionnel()
    {
        // Créez une matrice
        double[][] s3 = {
                { 3, 5, -3, 15 },
                { 7, 10, 1, 2 },
                { -3, 2, -5, 6 }
        };
        Matrice l1 = new Matrice(s3);

        // Tentez d'obtenir une sous-matrice avec des indices hors limites (6 colonnes)
        Matrice m = l1.sousMatrice(2, 6);
        Matrice m1 = l1.sousMatrice(-2, 6);

        // Matrice attendue (celle qui aurait dû être obtenue sans exception)
        double[][] resAttendu = {
                { 3, 5 },
                { 7, 10 }
        };
        Matrice l2 = new Matrice(resAttendu);

        // Assert pour vérifier que l'exception IllegalArgumentException est levée
        assertEquals(m, l2);

        }


    @Test
    public void testCreerMatriceNulle() {
        Matrice matrice = Matrice.creerMatriceNulle(3, 3);
        double[][] expectedElements = {{0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}};
        Matrice expectedElementsMatrice = new Matrice(expectedElements);
        assertEquals(expectedElementsMatrice, matrice);
    }

    @Test
    public void testCreerMatriceIdentite() {
        Matrice matrice = Matrice.creerMatriceIdentite(4);
        double[][] expectedElements = {{1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};
        Matrice expectedElementsMatrice = new Matrice(expectedElements);
        assertEquals(expectedElementsMatrice, matrice);
    }

    @Test
    public void testSousMatriceIdentiteApresGauss() {

        double[][] elements = {{1.0, 2.0, 3.0}, {0.0, 1.0, 4.0}, {0.0, 0.0, 1.0}};
        Matrice mat = new Matrice(elements);

        // Appliquer la méthode Gauss pour éliminer la colonne 2 (0-indexed)
        mat.Gauss();

        Matrice sousMatrice = mat.sousMatrice(3, 2);
        System.out.println(sousMatrice);

        Matrice matriceIdentite = Matrice.creerMatriceIdentite(3);
        System.out.println(matriceIdentite);

        // Vérifier si la sous-matrice est égale à la matrice identité
        assertFalse(sousMatrice.equals(matriceIdentite));

    }




}


