package apce;

import apce.bdd.Connexion;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Apce {
    public static final String RESET = "\033[0m";  // Remet la couleur du texte à celle par défaut
    public static final String YELLOW = "\033[0;33m";
    private final Connexion cx;
    private final boolean ECHO = true;
    private final GestionAPCE gestionAPCE;

    public static void main(String[] args)
    {
        if (args.length < 4)
        {
            System.out.println("Usage: java APCE.apce <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

        Apce apce = null;

        try
        {

            BufferedReader reader = ouvrirFichier(args);

            apce = new Apce(args[0], args[1], args[2], args[3]);
            apce.traiterTransactions(reader);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        finally
        {
            if(apce != null){
                try{
                    apce.fermer();
                }
                catch (Exception e){
                    System.out.println("Erreur imprévue lors de la déconnexion");
                }
            }

        }

    }

    public Apce(String serveur, String bd, String user, String pass) throws Exception
    {
        gestionAPCE = new GestionAPCE(serveur, bd, user, pass);
        cx = gestionAPCE.getCx();
    }

    public void fermer() throws SQLException {
        cx.fermerConnexion();
    }

    /**
     * Traitement des transactions du programme
     */
    public void traiterTransactions(BufferedReader reader) throws Exception
    {
        afficherAide();
        String transaction = lireTransaction(reader);
        while (!finTransaction(transaction))
        {
            executerTransaction(transaction);
            transaction = lireTransaction(reader);
        }
    }

    /** Affiche le menu des transactions acceptées par le système */
    private static void afficherAide()
    {
        System.out.println();
        System.out.print("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separés par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println();
        System.out.println("Les transactions sont:");
        System.out.println(YELLOW);
        System.out.println("  aide");
        System.out.println("  ajouterProducteur nom courriel nombreEmployes adressePostale");
        System.out.println("  afficherProducteur nom");
        System.out.println("  supprimerProducteur nom");
        System.out.println("  ajouterProduit nom prix cout catégorie nomProducteur");
        System.out.println("  afficherProduit nom");
        System.out.println("  supprimerProduit nom");
        System.out.println("  ajouterFournisseur nom courriel adressePostale");
        System.out.println("  afficherFournisseur nom");
        System.out.println("  supprimerFournisseur nom");
        System.out.println("  fabriquerProduit nomProduit nomFournisseur");
        System.out.println("  retirerProduitFournisseur nomProduit nomFournisseur");
        System.out.println("  ajouterPointDeVente nom courriel adressePostale");
        System.out.println("  afficherPointDeVente nom");
        System.out.println("  supprimerPointDeVente nom");
        System.out.println("  vendreProduit nomProduit nomPointDeVente");
        System.out.println("  retirerProduitPointDeVente nomProduit nomPointDeVente");
        System.out.println("  créer");
        System.out.println("  afficher");
        System.out.println("  détruire");
        System.out.println("  quitter");

        System.out.println(RESET);
    }

    /**
     * Decodage et traitement d'une transaction
     */
    void executerTransaction(String transaction)
    {
        try
        {
            if(ECHO) System.out.println(transaction);
            // Decoupage de la transaction en mots
            String[] tokens = transaction.split("\\s");
            if (transaction.length()>0 && tokens.length > 0)
            {
                String command = tokens[0];

                switch (command) {
                    case "aide":
                        afficherAide();
                        break;
                    case "ajouterProducteur":
                        String nom = tokens[1];
                        String courriel = tokens[2];
                        int nombreEmployes = Integer.parseInt(tokens[3]);
                        String adressePostale = lireReste(tokens, 4);
                        gestionAPCE.getGestionProducteur().ajouterProducteur(nom, courriel, nombreEmployes, adressePostale);
                        break;
                    case "afficherProducteur":
                        String nomProducteur = tokens[1];
                        gestionAPCE.getGestionProducteur().afficherProducteur(nomProducteur);
                        break;
                    case "supprimerProducteur":
                        nomProducteur = tokens[1];
                        gestionAPCE.getGestionProducteur().supprimerProducteur(nomProducteur);
                        break;
                    case "ajouterProduit":
                        String nomProduit = tokens[1];
                        BigDecimal prix = new BigDecimal(Float.parseFloat(tokens[2]));
                        BigDecimal coût = new BigDecimal(Float.parseFloat(tokens[3]));
                        String categorie = tokens[4];
                         nomProducteur = lireReste(tokens, 5);
                        gestionAPCE.getGestionProduit().ajouterProduit(nomProduit, prix, coût, categorie,nomProducteur);
                        break;
                    case "afficherPointDeVente":
                          String nomPointDeVente =tokens[1];
                        gestionAPCE.getGestionPointDeVente().afficherPointDeVente(nomPointDeVente);
                        break;
                    case "supprimerPointDeVente":
                        nomPointDeVente= tokens[1];
                        gestionAPCE.getGestionPointDeVente().supprimerPointDeVente(nomPointDeVente);
                        break;



                    case "vendreProduit":
                        nomProduit =tokens[1];
                        nomPointDeVente     =tokens[2];
                            gestionAPCE.getGestionPointDeVente().vendreProduit(nomProduit,nomPointDeVente);

                    case "ajouterPointDeVente":
                        nomPointDeVente = tokens[1];
                        courriel = tokens[2];
                        adressePostale = lireReste(tokens, 3);
                        gestionAPCE.getGestionPointDeVente().ajouterPointDeVente(nomPointDeVente, courriel, adressePostale);
                        break;

                    case "afficherProduit":
                        nomProduit= tokens[1];
                        gestionAPCE.getGestionProduit().afficherProduit(nomProduit);

                        break;
                    case "supprimerProduit":
                        nomProduit= tokens[1];
                        gestionAPCE.getGestionProduit().supprimerProduit(nomProduit);

                        break;
                    case "ajouterFournisseur":
                        nom = tokens[1];
                        courriel = tokens[2];
                        adressePostale = lireReste(tokens, 3);
                        gestionAPCE.getGestionFournisseur().ajouterFournisseur(nom, courriel, adressePostale);
                        break;
                    case "afficherFournisseur":
                        nom = tokens[1];
                        gestionAPCE.getGestionFournisseur().afficherFournisseur(nom);
                        break;
                    case "supprimerFournisseur":
                        nom = tokens[1];
                        gestionAPCE.getGestionFournisseur().supprimerFournisseur(nom);
                        break;
                        //TODO Votre code ici


                    case "fabriquerProduit":
                          nomProduit  = tokens[1];
                        String nomFournisseur = tokens[2];
                        gestionAPCE.getGestionFournisseur().fabriquerProduit(nomProduit,nomFournisseur);
                        //TODO Votre code ici

                        break;
                    case "retirerProduitFournisseur":
                        nomProduit= tokens[1];
                        nomFournisseur= tokens[2];
                        gestionAPCE.getGestionFournisseur().retirerProduitFournisseur(nomProduit,nomFournisseur);
                        //TODO Votre code ici

                        break;
                    case "retirerProduitPointDeVente":
                        nomProduit = tokens[1];
                        nomPointDeVente= tokens[2];
                        gestionAPCE.getGestionPointDeVente().retirerProduitPointDeVente(nomProduit,nomPointDeVente);
                        //TODO Votre code ici

                        break;
                    //TODO : Tous les cas des points de vente ici

                    case "créer":
                        gestionAPCE.creerBD();
                        break;
                    case "afficher":
                        gestionAPCE.afficherBD();
                        //TODO Votre appel ici
                        break;
                    case "détruire":
                        gestionAPCE.dropBD();
                        //TODO Votre appel ici
                        break;
                    case "quitter":
                        System.exit(0);
                        break;
                    default:
                        System.out.println(" : Transaction non reconnue");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e);
            e.printStackTrace();
        }
    }

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException
    {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }


    /**
     * Lecture du reste de la transaction entree a l'ecran à partir d'une position
     */
    static String lireReste(String[] tokens, int debut){
        StringBuilder buf = new StringBuilder();

        for (int i = debut; i < tokens.length ; ++i){
            buf.append(tokens[i]);
            buf.append(" ");
        }

        return buf.toString().trim();
    }
}
