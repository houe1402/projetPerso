package apce.gestion;

import apce.CollantException;
import apce.tables.*;
import apce.tuples.TupleFournisseur;
import apce.tuples.TuplePointDeVente;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.sql.SQLException;
import java.util.List;


public class GestionPointDeVente extends GestionTransactions {
    private final TablePointDeVente pointDeVente;

    private final TableProduit produits;



    private final TableProducteurPointDeVente producteurPointDeVente;
    private final TableProduitPointDeVente produitPointDeVente;

        public GestionPointDeVente(TablePointDeVente pointDeVente, TableProduit produits, TableProducteur producteur,TableProducteurPointDeVente producteurPointDeVente,TableProduitPointDeVente produitPointDeVente,TableFournisseur fournisseurs) {
            super(pointDeVente.getConnexion());
            this.pointDeVente = pointDeVente;
            this.produits = produits;

            this.producteurPointDeVente = producteurPointDeVente;
            this.produitPointDeVente = produitPointDeVente;


        }

        public void ajouterPointDeVente(String nom, String courriel, String adresse) throws SQLException, CollantException {
            try {
                cx.demarreTransaction();
                if (pointDeVente.existe(nom))
                    throw new CollantException("Le pointdevente "+ nom +" existe deja.");
                pointDeVente.ajouterPointDeVente(nom, courriel, adresse);

                cx.executeTransaction();
            } catch (Exception e) {
                cx.annuleTransaction();
                throw e;
            }

        }


    public void vendreProduit(String nomProduit,String nomPointDeVente) throws SQLException, CollantException{

        try {
            cx.demarreTransaction();

            // Récupérer les identifiants du produit et du fournisseur
            TupleProduit produit = produits.getProduit(nomProduit);
            TuplePointDeVente pointDeVente3 = pointDeVente.getPointDeVente(nomPointDeVente);
            


            System.out.println(pointDeVente3);


            // Vérifier si le produit existe
            if (produit == null) {
                throw new CollantException("Le produit " + nomProduit + " n'existe pas.");
            }

            // Vérifier si le fournisseur existe
            if (pointDeVente3 == null) {
                throw new CollantException("Le point de vente " + nomPointDeVente + " n'existe pas.");
            }


                int tuple = pointDeVente3.idpointdevente;
            // Ajouter la relation dans la table point de vente et producteur
            produitPointDeVente.ajouterProduitPointDeVente(produit.idProduit, tuple);

           producteurPointDeVente.ajouterPointDeVenteProducteur(produit.idProducteur,tuple);

            cx.executeTransaction();
        } catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
    }

        public void afficherPointDeVente (String nompointdevente) throws SQLException {
            try {
                cx.demarreTransaction();
                TuplePointDeVente tuplePointDeVente = pointDeVente.getPointDeVente(nompointdevente);
                System.out.print("\nPointDeVente:");
                System.out.println("\nNom Courriel Adresse");
                System.out.println(tuplePointDeVente.nom + " " + tuplePointDeVente.courriel + " "  + tuplePointDeVente.adresse);


                List<TupleProduit> pointList = produitPointDeVente.getPointsFromProduit(nompointdevente);

                System.out.print("\nProduits:");
                for (TupleProduit produit : pointList)
                {
                    System.out.println(produit.nom);
                }

                List<TupleProducteur> productList = producteurPointDeVente.getPointdeProducteur(nompointdevente);
                System.out.print("\nProducteur:");
                for (TupleProducteur producteur : productList)
                {
                    System.out.println(producteur.nom_producteur);
                }
                cx.executeTransaction();
            } catch (Exception e)
            {
                cx.annuleTransaction();
                throw e;
            }
        }

        public void supprimerPointDeVente(String nom) throws SQLException, CollantException {
            try {
                cx.demarreTransaction();
                TuplePointDeVente pointDeVente1 = this.pointDeVente.getPointDeVente(nom);
                if (pointDeVente1 == null) {
                    throw new CollantException("Le point de vente " + nom + " n'existe pas.");

                }


                // Supprimer les associations avec les produits
                produitPointDeVente.supprimerAssociationsProduits(pointDeVente1.idpointdevente);

                        // Supprimer les associations avec les producteurs
                producteurPointDeVente.supprimerAssociationsProducteurs(pointDeVente1.idpointdevente);


                        // Supprimer le Point de vente
                pointDeVente.supprimePointDeVente(nom);


                cx.executeTransaction();
            } catch (Exception e) {
                cx.annuleTransaction();
                throw e;
            }
        }



    public void retirerProduitPointDeVente(String nomProduit, String nomPointDevente) throws SQLException, CollantException {

        try {        cx.demarreTransaction();
            // Allons chercher le fournisseur
            TuplePointDeVente pointDeVente2= pointDeVente.getPointDeVente(nomPointDevente);
            if (pointDeVente2 == null) {
                throw new CollantException("Point de vente inexistant: " + nomPointDevente);
            }
            // Allons chercher le produit
            TupleProduit produit2 = produits.getProduit(nomProduit);
            if (produit2 == null)
            {
                throw new CollantException("Produit de Vente inexistant: " + nomProduit);

            }
            // Allons voir si la relation existe
            if(!produitPointDeVente.existe(produit2.idProduit,pointDeVente2.idpointdevente ))
            {
                throw new CollantException("Le point de vente " + nomPointDevente + " ne fabrique pas le produit " + nomPointDevente +".");
            }
            //On supprimer la relation
            produitPointDeVente.supprimerProduitPointDeVente(produit2.idProduit, pointDeVente2.idpointdevente);
            cx.executeTransaction();
        } catch (Exception e)
        {        cx.annuleTransaction();
            throw e;
        }
    }




}