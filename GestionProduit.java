package apce.gestion;

import apce.CollantException;
import apce.tables.*;
import apce.tuples.TupleFournisseur;
import apce.tuples.TuplePointDeVente;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class GestionProduit extends GestionTransactions {
    private final TableProduit produits;
    private final TableProducteur producteurs;
    private final TableProduitFournisseur produitFournisseur;
    private final TableProduitPointDeVente produitPointDeVente;




    public GestionProduit(TableProduit produits, TableProducteur producteurs,TableFournisseur fournisseur,TableProduitFournisseur produitFournisseur,TableProduitPointDeVente produitPointDeVente) {
        super(produits.getConnexion());
        this.produits = produits;
        this.producteurs = producteurs;

        this.produitFournisseur=produitFournisseur;
        this.produitPointDeVente=produitPointDeVente;

        //TODO votre code ici
    }
/** Question a pose au prof*/
    public void ajouterProduit(String nom, BigDecimal prix, BigDecimal coût,String categorie,String nomProducteur) throws SQLException ,CollantException {
        try {
            cx.demarreTransaction();
            TupleProducteur p = producteurs.getProducteur(nomProducteur);
            // verifier si le client existe deja
            if (produits.existe(nom))
                throw new CollantException("Le produit "+ nom +" existe deja.");
            if(p == null)
                throw new CollantException("Le producteur "+ nomProducteur +" N'existe pas.");
            else
                produits.ajouterProduits(nom, prix,coût,categorie,p.idProducteur);

            cx.executeTransaction();
        }catch (Exception e) {
            cx.annuleTransaction();
            throw e;

        }
    }
    public void afficherProduit(String nomProduit) throws SQLException {
        try {
            cx.demarreTransaction();
            TupleProduit tupleProduit = produits.getProduit(nomProduit);
            System.out.println(tupleProduit);
            if (tupleProduit == null) {
                throw new SQLException("Le produit " + nomProduit + " n'existe pas.");
            }
            System.out.print("\nProduit:");
            System.out.println("\nNom Prix Cout Categorie");
            System.out.println(tupleProduit.nom + " " + tupleProduit.prix + " " + tupleProduit.coût + " " + tupleProduit.categorie);
                int liste = tupleProduit.idProduit;
            List<TupleFournisseur> fournisseurList = produitFournisseur.getFournisseursFromProduit(liste);
            System.out.print("\nFournisseurs:");
            for (TupleFournisseur fournisseur : fournisseurList) {
                System.out.println(fournisseur.nom);
            }

            TupleProducteur producteurList = produits.getProducteurFromProduit(liste);
            if (producteurList != null) {
                System.out.print("\nProducteur:");
                System.out.println(producteurList.nom_producteur);
            } else {
                System.out.println("Aucun producteur trouvé pour ce produit.");
            }

            List<TuplePointDeVente> pventeList = produitPointDeVente.getPointDeVFromProduit(liste);
            System.out.print("\nPointDeVente:");
            for (TuplePointDeVente pointVte : pventeList) {
                System.out.println(pointVte .nom);
            }

            cx.executeTransaction();
        } catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
    }

    public void supprimerProduit(String nomProduit) throws SQLException,  CollantException  {


        try {
            cx.demarreTransaction();
            // Vérifier si le producteur existe
            TupleProduit tupleProduit = produits.getProduit(nomProduit);
            if (tupleProduit == null) {
                throw new CollantException("Le Produit " + nomProduit + " n'existe pas.");
            }
            int inttuple = tupleProduit.idProduit;

            // Supprimer les associations avec les producteurs

            produitFournisseur.supprimerAssociationsFproduit(inttuple);

            // Supprimer le Point de vente a completer
            produitPointDeVente.supprimerAssociationsPproduits(inttuple);


            produits.supprimerProduitP(inttuple);

            cx.executeTransaction();
        } catch (Exception e) {
            cx.annuleTransaction();
            throw e;
        }
    }



}
