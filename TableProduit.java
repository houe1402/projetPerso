package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableProduit extends GestionTables {
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtDelet;
    private final PreparedStatement stmtDeleteP;

    private final PreparedStatement stmtListGet;
    private final PreparedStatement stmtDeletePP;
    private final PreparedStatement stmtGete;
    private final PreparedStatement stmtInsert;


    private final PreparedStatement stmtPr;
    public TableProduit(Connexion cx)  throws SQLException{
        super(cx);

        this.stmtDelete = cx.getConnection().prepareStatement(
                "DELETE FROM PRODUIT WHERE idProducteur = ?");

        this.stmtDeleteP = cx.getConnection().prepareStatement(
                "DELETE  FROM produit WHERE idproducteur = ?");
        this.stmtDeletePP = cx.getConnection().prepareStatement(
                "DELETE  FROM produit WHERE idproduit = ?");



        this.stmtPr = cx.getConnection().prepareStatement(
                "SELECT  Nom_Produit, Prix, coût, categorie, idProducteur,idProduit FROM PRODUIT WHERE Nom_Produit = ?");

        this.stmtDelet = cx.getConnection().prepareStatement(
                "DELETE FROM produitpointdevente WHERE idProduit = ?");
        this.stmtListGet = cx.getConnection().prepareStatement("SELECT p.* FROM PRODUIT p " +
                "JOIN PRODUCTEUR pr ON p.idProducteur = pr.idProducteur " +
                "WHERE pr.Nom_Producteur = ?");

        this.stmtInsert = cx.getConnection().prepareStatement(
                "INSERT INTO Produit(nom_produit, prix, coût, categorie,idproducteur)" +
                        "VALUES (?, ?, ?, ?,?)"
        );


        this.stmtGete = cx.getConnection().prepareStatement(
                "SELECT pr.* FROM PRODUCTEUR pr " +
                        "JOIN PRODUIT p ON pr.idProducteur = p.idProducteur " +
                        "WHERE p.idProduit = ?"
        );




        //TODO votre code ici
    }
    public boolean existe(String nom) throws SQLException {
        stmtPr.setString(1, nom);
        ResultSet resultSet = stmtPr.executeQuery();
        boolean existe = resultSet.next();
        resultSet.close();
        return existe;



    }


    public void ajouterProduits(String nom, BigDecimal prix, BigDecimal coût, String categorie,int idProducteur) throws SQLException{
        stmtInsert.setString(1, nom);
        stmtInsert.setBigDecimal(2, prix);
        stmtInsert.setBigDecimal(3, coût);
        stmtInsert.setString(4, categorie);
        stmtInsert.setInt(5, idProducteur);
        stmtInsert.executeUpdate();

     }



    public TupleProduit getProduit(String nomProduit) throws SQLException {
        // TODO : votre implémentation ici
            stmtPr.setString(1, nomProduit);
            try (ResultSet rest = stmtPr.executeQuery()) {
                if (rest.next()) {

                    TupleProduit tupleProduit = new TupleProduit();
                    tupleProduit.setNom(rest.getString("Nom_Produit")); // Ajout de l'ID du produit
                    tupleProduit.setPrix(rest.getBigDecimal("Prix"));
                    tupleProduit.setCoût(rest.getBigDecimal("coût"));
                    tupleProduit.setCategorie(rest.getString("categorie"));

                    tupleProduit.setIdProducteur(rest.getInt("idProducteur"));
                    tupleProduit.setIdProduit(rest.getInt("idProduit"));


                    return tupleProduit;

                } else {
                    return null;
                }
            }
        }




    public List<TupleProduit> getProduitsFromProducteur(String nomProducteur) throws SQLException {


        stmtListGet.setString(1,nomProducteur);
        ResultSet rest = stmtListGet.executeQuery();
        List<TupleProduit> productfromproducteur= new LinkedList<>();
        while(rest.next()){
            TupleProduit produitFrom = new TupleProduit(
                    rest.getString("nom_Produit"),
                    rest.getBigDecimal("Prix") ,
                    rest.getBigDecimal("coût"),
                    rest.getString("categorie"),
                    rest.getInt("idProduit"),
                    rest.getInt("idProducteur"));


            productfromproducteur.add(produitFrom);
        }
        rest.close();
        return productfromproducteur;

        //TODO : implémenter la fonction

    }

    public TupleProducteur getProducteurFromProduit(int idProduit) throws SQLException {

        stmtGete.setInt(1, idProduit);
        ResultSet rest = stmtGete.executeQuery();

        if (rest.next()) {
            TupleProducteur tupleproducteur = new TupleProducteur();
            tupleproducteur.setNom(rest.getString(1));
            tupleproducteur.setCourriel(rest.getString(2));
            tupleproducteur.setAdresse(rest.getString(3));
            tupleproducteur.setNbEmploi(rest.getInt(4));

            rest.close();
            return tupleproducteur;


        } else {
            return null;
        }
    }



    public void supprimerProduits(int idProducteurs) throws SQLException {


        stmtDeleteP.setInt(1,idProducteurs);
         stmtDeleteP.executeUpdate();

        }

    public void supprimerProduitP(int idProduit) throws SQLException {


        stmtDeletePP.setInt(1,idProduit);
        stmtDeletePP.executeUpdate();

    }

    public void supprimeProductFromProduit(int idProducteur) throws SQLException {
        stmtDelete.setInt(1,idProducteur);
        stmtDelete.executeUpdate();

    }

    public void supprimerPointDeVente(int idProducteur) throws SQLException{
        stmtDelet.setInt(1,idProducteur);
         stmtDelet.executeUpdate();

    }

}
