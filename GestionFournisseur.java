package apce.gestion;

import apce.CollantException;
import apce.tables.*;
import apce.tuples.TupleFournisseur;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.sql.SQLException;
import java.util.List;

public class GestionFournisseur extends GestionTransactions {

    private final TableFournisseur fournisseurs;
    private final TableProduit produits;

    private final TableProducteurFournisseur producteurFournisseur;
    private final TableProduitFournisseur produitFournisseur;
    public GestionFournisseur(TableFournisseur fournisseurs, TableProduit produits, TableProducteurFournisseur producteurFournisseur,TableProduitFournisseur produitFournisseur) {
        super(fournisseurs.getConnexion());
        this.fournisseurs = fournisseurs;
        this.produits = produits;

        this. producteurFournisseur=producteurFournisseur;
        this.produitFournisseur=produitFournisseur;
        //TODO votre code ici
    }

    public void ajouterFournisseur(String nom, String courriel, String adressePostale) throws SQLException, CollantException {

        try {
            cx.demarreTransaction();
            // verifier si le client existe deja
            if (fournisseurs.existe(nom))
                throw new CollantException("Le fournisseur "+ nom +" existe deja.");
            fournisseurs.ajouterFournisseur(nom, courriel, adressePostale);
            cx.executeTransaction();
        }catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
        //TODO votre code ici
    }

    public void afficherFournisseur(String nomFournisseur) throws SQLException {

        try
        {
            cx.demarreTransaction();
            TupleFournisseur tupleFournisseur = fournisseurs.getFournisseur(nomFournisseur);
            System.out.print("\nFournisseur:");
            System.out.println("\nNom Courriel Adresse");
            System.out.println(tupleFournisseur.nom + " " + tupleFournisseur.courriel + " " + " " + tupleFournisseur.adresse);


            List<TupleProduit> produitList = produitFournisseur.getProduitsFromFournisseur(nomFournisseur);
            System.out.print("\nProduits:");
            for (TupleProduit produit : produitList)
            {
                System.out.println(produit.nom);
            }

            List<TupleProducteur> producteurList = producteurFournisseur.getProducteurFournisseur(nomFournisseur);
            System.out.print("\nProducteurs:");
            for (TupleProducteur producteur : producteurList)
            {
                System.out.println(producteur.nom_producteur);
            }
            cx.executeTransaction();
        } catch (Exception e)
        {
            cx.annuleTransaction();
            throw e;
        }

        //TODO votre code ici
    }

    public void supprimerFournisseur(String nomFournisseur) throws SQLException,  CollantException  {


        try {
            cx.demarreTransaction();
            // Vérifier si le producteur existe
            TupleFournisseur tupleFournisseur = fournisseurs.getFournisseur(nomFournisseur);
            if (tupleFournisseur == null) {
                throw new CollantException("Le Fournisseurs " + nomFournisseur + " n'existe pas.");
            }
            int intuple = tupleFournisseur.idFournisseur;
            // Supprimer les associations avec les produits
            produitFournisseur.supprimerAssociationsFournisseur(intuple);

            // Supprimer les associations avec les producteurs
            producteurFournisseur.supprimerAssociationsFournisseur(intuple);


            // Supprimer le fournisseur
            fournisseurs.supprimeFournisseur(nomFournisseur);

            cx.executeTransaction();
        } catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
    }
    //TODO votre code ici
    //Vous devriez vérifier qu'un producteur existe avant de le supprimer

    public void fabriquerProduit(String nomProduit,String nomFournisseur) throws SQLException, CollantException {
        try {
            cx.demarreTransaction();
            // Allons chercher le fournisseur
             TupleFournisseur fournisseur = fournisseurs.getFournisseur(nomFournisseur);
             if (fournisseur == null)
            {            throw new CollantException("Fournisseur inexistant: " + nomFournisseur);
            }
             // Allons chercher le produit
             TupleProduit produit = produits.getProduit(nomProduit);
             if (produit == null)
            {            throw new CollantException("Produit inexistant: " + nomProduit);
            }
             // Allons voir si la relation existe déjà
             if(produitFournisseur.existe( produit.idProduit,fournisseur.idFournisseur)) {
            throw new CollantException("Le fournisseur " + nomFournisseur + " fabrique déjà le produit " + nomProduit +".");
            }
             // Finalement ajouter le produit
            produitFournisseur.ajouterProduitFournisseur(produit.idProduit,fournisseur.idFournisseur);
        //Maintenant, il faut ajouter la relation également entre le producteur et le fournisseur

            int producteur = produit.idProducteur;
        if(!producteurFournisseur.existe(fournisseur.idFournisseur, producteur)) {
         producteurFournisseur.ajouterproducteurFournisseur(fournisseur.idFournisseur, producteur);
        }
             cx.executeTransaction();
        } catch (Exception e) {
            cx.annuleTransaction();
            throw e;
         }
    }

    public void retirerProduitFournisseur(String nomProduit,String nomFournisseur) throws SQLException, CollantException {

        try {        cx.demarreTransaction();
            // Allons chercher le fournisseur
            TupleFournisseur fournisseur = fournisseurs.getFournisseur(nomFournisseur);
            if (fournisseur == null) {
             throw new CollantException("Fournisseur inexistant: " + nomFournisseur);
             }
            // Allons chercher le produit
            TupleProduit produit = produits.getProduit(nomProduit);
             if (produit == null)
             {
            throw new CollantException("Produit inexistant: " + nomProduit);

                 }
            // Allons voir si la relation existe
            if(!produitFournisseur.existe( produit.idProduit,fournisseur.idFournisseur))
             {
                 throw new CollantException("Le fournisseur " + nomFournisseur + " ne fabrique pas le produit " + nomProduit +".");
            }
            //On supprimer la relation
            produitFournisseur.supprimerProduitFournisseur(produit.idProduit,fournisseur.idFournisseur);
             cx.executeTransaction();
            } catch (Exception e)
            {        cx.annuleTransaction();
                throw e;
            }
    }


    }
