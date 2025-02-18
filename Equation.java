package labo2.tests;

import labo2.Vecteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import static labo2.UtilitairesAlgebre.isOperator;
import static labo2.UtilitairesAlgebre.isOperatorEquals;

public class Equation extends Vecteur {

    ArrayList<Character> variables = new ArrayList<Character>();
    ArrayList<Integer> coefficients = new ArrayList<Integer>();
    int constante;
    String equation = "3x + 5y - 3z = 15";
    public Equation() {
        super(); // Appelle le constructeur de la classe mère (Vecteur)
    }

        public String toString(){

            String res = "";
            res += coefficients.get(0) + "" + variables.get(0);

            for (int i = 1; i < variables.size(); i++) {

                int coeff = coefficients.get(i);
                if (coeff < 0) {
                    res += " - ";
                } else {
                    res += " + ";
                }

                res += Math.abs(coeff) + "" + variables.get(i);
            }

            res += " = " + constante;
            return res;

        }
/*----------------------------------------------------------------------------------*/
    public void lire(String source){

                int constante = 0;
                final String delims = "+-=";
                HashMap<Character, Integer> signes = new HashMap<Character, Integer>();
                signes.put('+', 1);
                signes.put('-', -1);

                int lastSign = 1;
                boolean complete = false;

                StringTokenizer tokenizer = new StringTokenizer(source, delims, true);

                while (tokenizer.hasMoreTokens()) {

                    String val = tokenizer.nextToken().trim();
                    if (val.length() == 0)
                        continue;

                    if (isOperator(val)) {

                        lastSign = signes.get(val.charAt(0));

                    } else if (isOperatorEquals(val)) {

                        if (!tokenizer.hasMoreTokens())
                            throw new IllegalArgumentException("Equation: équation mal formée (constante manquante à la fin");

                        constante = Integer.parseInt(tokenizer.nextToken().trim());
                        complete = true;
                        break;

                    } else {

                        int coeff = Integer.parseInt(val.substring(0, val.length() - 1));
                        char var = val.charAt(val.length() - 1);

                        if (variables.contains(var))
                            throw new IllegalArgumentException("Equation: équation mal formée (variable dupliquée");

                        variables.add(var);
                        coefficients.add(coeff * lastSign);
                    }
                }
                if (tokenizer.hasMoreTokens())
                    throw new IllegalArgumentException("Equation: équation mal formée (expression continue après la constante");
                if (!complete)
                    throw new IllegalArgumentException("Equation: équation mal formée (manque = constante à la fin)");

            }

}
