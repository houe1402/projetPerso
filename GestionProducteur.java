package apce.gestion;

import apce.CollantException;
import apce.bdd.Connexion;
import apce.tables.*;
import apce.tuples.TupleFournisseur;
import apce.tuples.TuplePointDeVente;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.sql.SQLException;
import java.util.List;

public class GestionProducteur extends GestionTransactions{
    private final TableProducteur producteurs;

    private final TableProduit produits;
    private final TableFournisseur fournisseurs;

    private final TableProduitFournisseur produitFournisseur;
    private final TableProduitPointDeVente produitPointDeVente;
    private final TableProducteurFournisseur producteurFournisseur;
    private final TableProducteurPointDeVente producteurPointDeVente;

    public GestionProducteur(TableProducteur producteurs, TableProduit produits, TableFournisseur fournisseurs,TableProduitFournisseur produitFournisseur,TableProduitPointDeVente produitPointDeVente,TableProducteurFournisseur producteurFournisseur,TableProducteurPointDeVente producteurPointDeVente) {
        super(producteurs.getConnexion());
        this.producteurs = producteurs;
        this.produits = produits;
        this.fournisseurs = fournisseurs;

        this.produitFournisseur=produitFournisseur;
        this.producteurFournisseur=producteurFournisseur;
        this.producteurPointDeVente=producteurPointDeVente;
        this.produitPointDeVente=produitPointDeVente;
    }

    /**
     * Ajout d'un nouveau producteur au système.
     */
    public void ajouterProducteur(String nom, String courriel, int nombreEmployes, String adresse) throws SQLException, CollantException {
        try {
            cx.demarreTransaction();
            // verifier si le client existe deja
            if (producteurs.existe(nom))
                throw new CollantException("Le producteur "+ nom +" existe deja.");
            producteurs.ajouterProducteur(nom, courriel, nombreEmployes, adresse);

            cx.executeTransaction();
        }catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
    }

    public void afficherProducteur(String nomProducteur) throws SQLException {
        try
        {
            cx.demarreTransaction();
            TupleProducteur tupleProducteur = producteurs.getProducteur(nomProducteur);
            System.out.print("\nProducteur:");
            System.out.println("\nNom Courriel NombreEmployes Adresse");
            System.out.println(tupleProducteur.nom_producteur + " " + tupleProducteur.courriel + " " + tupleProducteur.nbEmploi + " " + tupleProducteur.adresse);


            List<TupleProduit> produitList = produits.getProduitsFromProducteur(nomProducteur);
            System.out.print("\nProduits:");

            for (TupleProduit produit : produitList)
            {
                System.out.println(produit.nom);

            }



            List<TupleFournisseur> fournisseurList = fournisseurs.getFournisseursProducteur(nomProducteur);
            System.out.print("\nFournisseurs:");
            for (TupleFournisseur fournisseur : fournisseurList)
            {
                System.out.println(fournisseur.nom);
            }
            List<TuplePointDeVente> PointdeVenteList = producteurPointDeVente.getProducPointdeVente(nomProducteur);
            System.out.print("\nPointDeVente:");
            for (TuplePointDeVente pointVente : PointdeVenteList)
            {
                System.out.println( pointVente .nom);
            }
            cx.executeTransaction();
        } catch (Exception e)
        {
            cx.annuleTransaction();
            throw e;
        }
    }

    public void supprimerProducteur(String nomProducteur) throws SQLException,  CollantException  {


        try {
            cx.demarreTransaction();
            // Vérifier si le producteur existe
            TupleProducteur tupleProducteur = producteurs.getProducteur(nomProducteur);
            if (tupleProducteur == null) {
                throw new CollantException("Le producteur " + nomProducteur + " n'existe pas.");
            }
            int idtuple = tupleProducteur.idProducteur;

            List<TupleProduit> produitList = produits.getProduitsFromProducteur(nomProducteur);
            for (TupleProduit produit : produitList)
            {
                int tuple= produit.idProduit;
                produitFournisseur.supprimerAssociationsFournisseur(tuple);
                produitPointDeVente.supprimerAssociationsPproduits(tuple);
            }
            // Supprimer les associations avec les fournisseurs et point de vente
            producteurFournisseur.supprimerAssociationsProducteur(idtuple);
            producteurPointDeVente.supprimeProductFrompointdevente(idtuple);


             produits.supprimerProduits(idtuple);

            // Supprimer le producteur
            producteurs.supprimeProducteur(idtuple);

            cx.executeTransaction();
        } catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
    }
        //TODO votre code ici
        //Vous devriez vérifier qu'un producteur existe avant de le supprimer

}
