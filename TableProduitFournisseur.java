package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableProduitFournisseur extends GestionTables{

    private final PreparedStatement stmtListePret ;
    private final PreparedStatement stmtListePrete ;
    private final PreparedStatement stmtDelete ;
    private final PreparedStatement stmtDeletee ;
    private final PreparedStatement stmtDelet ;
    private final PreparedStatement stmtInserto;
    private final PreparedStatement stmtGet;
    private final PreparedStatement  stmtListePr;
    public TableProduitFournisseur(Connexion cx) throws SQLException {
        super(cx);

        this.stmtListePret = cx.getConnection().prepareStatement("SELECT p.* FROM PRODUIT p " +
                "JOIN ProduitFournisseur pa ON p.idProduit = pa.idProduit " +
                "JOIN FOURNISSEUR f ON pa.idFournisseur = f.idFournisseur " +
                "WHERE f.Nom_Fournisseur = ?");

        this.stmtListePrete = cx.getConnection().prepareStatement("SELECT p.* FROM PRODUCTEUR p " +
                "JOIN ProducteurFournisseur ff ON p.idProducteur = ff.idProducteur " +
                "JOIN FOURNISSEUR f ON ff.idFournisseur = f.idFournisseur " +
                "WHERE f.Nom_Fournisseur = ?");

        this.stmtDelete =cx.getConnection().prepareStatement("DELETE FROM produitfournisseur WHERE idFournisseur = ?");
        this.stmtDeletee =cx.getConnection().prepareStatement("DELETE FROM produitfournisseur WHERE idproduit = ?");
        this.stmtDelet =cx.getConnection().prepareStatement("DELETE FROM produitfournisseur WHERE idproduit = ? AND idfournisseur=?");
        this.stmtInserto= cx.getConnection().prepareStatement(
                "INSERT INTO produitfournisseur (idProduit, idFournisseur) VALUES (?, ?)");
        this.stmtGet= cx.getConnection().prepareStatement(
                "SELECT idProduit, idFournisseur FROM produitfournisseur WHERE idProduit=? AND idFournisseur=? "
                        );
        this.stmtListePr = cx.getConnection().prepareStatement("SELECT f.* FROM FOURNISSEUR f " +
                "JOIN produitfournisseur pa ON f.idFournisseur = pa.idFournisseur " +
                "WHERE pa.idProduit = ?");


        //TODO votre code ici
    }
    public boolean existe(int id1, int id2) throws SQLException{
        stmtGet.setInt(1, id1);
        stmtGet.setInt(2, id2);
        ResultSet resultSet = stmtGet.executeQuery();
        boolean existe = resultSet.next();
        resultSet.close();
        return existe;
    }


        //TODO votre code ici

    public void ajouterProduitFournisseur(int id1,int id2) throws SQLException{

            stmtInserto.setInt(1, id1);
            stmtInserto.setInt(2, id2);
            stmtInserto.executeUpdate();

        }




    public List<TupleProduit> getProduitsFromFournisseur(String nomFournisseur)throws SQLException {
        //TODO votre code ici
        stmtListePret.setString(1,nomFournisseur);
        ResultSet rest = stmtListePret.executeQuery();
        List<TupleProduit> produitfournisseur= new LinkedList<>();
        while(rest.next()){
            TupleProduit produit = new TupleProduit(

                    rest.getString("nom_Produit"),
                    rest.getBigDecimal("prix"),
                    rest.getBigDecimal("coût") ,

                    rest.getString("categorie"),
                    rest.getInt("idProduit"),
                    rest.getInt("idProducteur"));


            produitfournisseur.add(produit);

        }
        rest.close();
        return produitfournisseur;

    }

    public List<TupleProducteur> getProduitFournisseur(String nomFournisseur)throws SQLException {


        stmtListePrete.setString(1,nomFournisseur);
        ResultSet rest = stmtListePrete.executeQuery();
        List<TupleProducteur> productfournisseur= new LinkedList<>();
        while(rest.next()){
            TupleProducteur product = new TupleProducteur(
                    rest.getString("nomProducteur"),

                    rest.getString("courriel") ,
                    rest.getString("adresse postal"),
                    rest.getInt("nmEmploye"),
                    rest.getInt("idProducteur"));


            productfournisseur.add(product);

        }
        rest.close();
        return productfournisseur;



    }
    public List<TupleFournisseur> getFournisseursFromProduit(int idProduit)throws SQLException {
        //TODO votre code ici
        stmtListePr.setInt(1,idProduit);
        ResultSet rest = stmtListePr.executeQuery();
        List<TupleFournisseur> fournisseurproduct= new LinkedList<>();
        while(rest.next()){
            TupleFournisseur fournisseur = new TupleFournisseur(
                    rest.getString("nom_Fournisseur"),
                    rest.getString("courriel") ,
                    rest.getString("adresse_postale"),
                    rest.getInt("idFournisseur"));


            fournisseurproduct.add(fournisseur);

        }
        rest.close();
        return fournisseurproduct;

    }

    public void supprimerAssociationsFournisseur(int idFournisseur) throws SQLException{
        stmtDelete.setInt(1,idFournisseur);
        stmtDelete.executeUpdate();



    }
    public void supprimerProduitFournisseur(int id1, int id2) throws SQLException{
        stmtDelet.setInt(1,id1);
        stmtDelet.setInt(2,id2);
        stmtDelet.executeUpdate();

    }

    public void supprimerAssociationsFproduit(int idProduit) throws SQLException{
        stmtDeletee.setInt(1,idProduit);
        stmtDeletee.executeUpdate();



    }


}
