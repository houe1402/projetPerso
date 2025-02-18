package apce;

import apce.bdd.Connexion;
import apce.gestion.GestionFournisseur;
import apce.gestion.GestionPointDeVente;
import apce.gestion.GestionProducteur;
import apce.gestion.GestionProduit;
import apce.tables.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionAPCE {
    private final Connexion cx;
    private final TableProducteur producteurs;
    private final TableProduit produits;
    private final TableProduitPointDeVente produitPointDeVente;
   /** private final TableProducteurPointDeVente producteurPointDeVente;*/
    private final TablePointDeVente pointDeVente;
    private final TableFournisseur fournisseurs;
    private final TableProduitFournisseur produitsFournisseurs;
    private final TableProducteurFournisseur producteursFournisseurs;
    private final TableProducteurPointDeVente producteursPointDeVente;
    private GestionProducteur gestionProducteur;
    private GestionProduit gestionProduit;
    private GestionFournisseur gestionFournisseur;
    private GestionPointDeVente gestionPointDeVente;

    public GestionAPCE(String server,  String bd, String user, String password) throws CollantException, SQLException {
        cx = new Connexion(server, bd, user, password);

        producteurs = new TableProducteur(cx);
        produits = new TableProduit(cx);
        pointDeVente = new TablePointDeVente(cx);
        fournisseurs = new TableFournisseur(cx);
        produitsFournisseurs = new TableProduitFournisseur(cx);
        producteursFournisseurs = new TableProducteurFournisseur(cx);
        produitPointDeVente = new TableProduitPointDeVente(cx);
        producteursPointDeVente = new TableProducteurPointDeVente(cx);


        setGestionProducteur(new GestionProducteur(producteurs, produits, fournisseurs,produitsFournisseurs,produitPointDeVente,producteursFournisseurs,producteursPointDeVente));
        setGestionProduit(new GestionProduit(produits,producteurs,fournisseurs,produitsFournisseurs,produitPointDeVente));
        setGestionFournisseur(new GestionFournisseur(fournisseurs, produits, producteursFournisseurs,produitsFournisseurs));
        setGestionPointDeVente(new GestionPointDeVente(pointDeVente,produits, producteurs,producteursPointDeVente,produitPointDeVente, fournisseurs));
    }


    public Connexion getCx() {
        return cx;
    }

    public GestionProducteur getGestionProducteur() {
        return gestionProducteur;
    }

    public void setGestionProducteur(GestionProducteur gestionProducteur) {
        this.gestionProducteur = gestionProducteur;
    }

    public GestionProduit getGestionProduit() {
        return gestionProduit;
    }

    public void setGestionProduit(GestionProduit gestionProduit) {
        this.gestionProduit = gestionProduit;
    }
    public GestionFournisseur getGestionFournisseur() {
        return gestionFournisseur;
    }
    public GestionPointDeVente getGestionPointDeVente() {
        return gestionPointDeVente;
    }

    public void setGestionFournisseur(GestionFournisseur gestionFournisseur) {
        this.gestionFournisseur = gestionFournisseur;
    }
    public void  setGestionPointDeVente(GestionPointDeVente gestionPointDeVente) {
        this.gestionPointDeVente = gestionPointDeVente;
    }

    public void creerBD() throws SQLException, FileNotFoundException {
        executeSQL("creation.sql");
    }

    public void dropBD() throws SQLException, FileNotFoundException {
        executeSQL("destruction.sql");
        //TODO votre code ici
    }

    public void afficherBD() throws SQLException, FileNotFoundException {
        //TODO votre code ici
        executeSQL("afficher.sql");
    }

    private void executeSQL(String scriptName) throws SQLException, FileNotFoundException {
        File f = new File(scriptName);
        Scanner reader = new Scanner(f);
        String sql = reader.nextLine();
        while (reader.hasNextLine()) {
            sql += reader.nextLine();
        }
        System.out.println(sql);
        reader.close();
        try {
            this.cx.demarreTransaction();
            this.cx.getConnection().createStatement().execute(sql);
            this.cx.executeTransaction();
        } catch (Exception e) {
            this.cx.annuleTransaction();
            throw e;
        }
    }
}
