package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TupleProducteur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableProducteurFournisseur extends GestionTables {
    private final PreparedStatement  stmtListePret ;
    private final PreparedStatement  stmtDelete ;
    private final PreparedStatement  stmtDeletee ;
    private final PreparedStatement  stmtInsert ;
    private final PreparedStatement  stmtGet ;


    public TableProducteurFournisseur(Connexion cx) throws SQLException{
        super(cx);
        this.stmtListePret = cx.getConnection().prepareStatement(
                "SELECT p.* FROM PRODUCTEUR p " +
                "JOIN ProducteurFournisseur ff ON p.idProducteur = ff.idProducteur " +
                "JOIN FOURNISSEUR f ON ff.idFournisseur = f.idFournisseur " +
                "WHERE f.Nom_Fournisseur = ?");
        this.stmtDelete =cx.getConnection().prepareStatement(
                "DELETE FROM ProducteurFournisseur WHERE idFournisseur = ?");
        this.stmtDeletee =cx.getConnection().prepareStatement(
                "DELETE FROM ProducteurFournisseur WHERE idproducteur = ?");
        this.stmtInsert =cx.getConnection().prepareStatement(
                "INSERT INTO ProducteurFournisseur (idfournisseur, idproducteur) VALUES (?, ?)"
        );
        this.stmtGet= cx.getConnection().prepareStatement(
                "SELECT idProducteur, idFournisseur FROM ProducteurFournisseur WHERE idFournisseur=? AND idProducteur=? "
        );



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

    public void ajouterproducteurFournisseur(int idFournisseur, int idProducteur) throws SQLException{

        stmtInsert.setInt(1, idFournisseur);
        stmtInsert.setInt(2, idProducteur);
        stmtInsert.executeUpdate();

    }

    public List<TupleProducteur> getProducteurFournisseur(String nomFournisseur)throws SQLException {
        //TODO votre code ici
        stmtListePret.setString(1,nomFournisseur);
        ResultSet rest = stmtListePret.executeQuery();
        List<TupleProducteur> productfournisseur= new LinkedList<>();
        while(rest.next()){
            TupleProducteur tupleProducteur = new TupleProducteur();
            tupleProducteur.setNom(rest.getString("Nom_producteur")); // Ajout de l'ID du producteur

            tupleProducteur.setCourriel(rest.getString("Courriel"));
            tupleProducteur.setNbEmploi(rest.getInt("Nombre_Employes"));
            tupleProducteur.setAdresse(rest.getString("Adresse_Postale"));
            tupleProducteur.setIdProducteur(rest.getInt("idProducteur"));


            productfournisseur.add(tupleProducteur);

        }
        rest.close();
        return productfournisseur;

    }


    public void supprimerAssociationsFournisseur(int nomFournisseur) throws SQLException{
        stmtDelete.setInt(1,nomFournisseur);
        stmtDelete.executeUpdate();
    }
    public void supprimerAssociationsProducteur(int nomProducteur) throws SQLException{
        stmtDeletee.setInt(1,nomProducteur);
        stmtDeletee.executeUpdate();
    }


}
