package labo2.tests;

import labo2.Equation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;



    public class TestEquation {
        private Equation eq;

        @Before
        public void setUp() {
            eq = new Equation();
        }

        @Test
        public void testLireEquation() {
            eq.lire("3x + 5y -2z = 0");
            eq.lire("3x + 5y= 0");
            eq.lire("5= 0");
            assertEquals("3x + 5y - 2z = 0", eq.toString());
            assertEquals("3x + 5y=0", eq.toString());
            assertEquals("5= 0", eq.toString());
        }

        @Test
        public void testLireEquationWithSpaces() {
            eq.lire("  2x -  4y + 6z  = 8 ");
            assertEquals("2x - 4y + 6z = 8", eq.toString());
        }

        @Test(expected = IllegalArgumentException.class)
        public void testLireInvalidEquation() {
            eq.lire("2x + 5y =");
        }
    }



