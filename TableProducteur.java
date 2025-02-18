package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TupleProducteur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableProducteur extends GestionTables {
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtGet;

    private final PreparedStatement  stmtDelete;




    public TableProducteur(Connexion cx) throws SQLException {
        super(cx);
        this.stmtInsert = cx.getConnection().prepareStatement(
                "INSERT INTO Producteur(nom_Producteur, courriel, nombre_Employes, adresse_postale)" +
                        "VALUES (?, ?, ?, ?)"
        );
        this.stmtGet = cx.getConnection().prepareStatement(
                "SELECT Nom_producteur,idproducteur, courriel, nombre_Employes, adresse_postale FROM Producteur WHERE nom_Producteur = ?"
        );

        this.stmtDelete = cx.getConnection().prepareStatement(
                "delete from Producteur where idProducteur=?");


    }

    public boolean existe(String nom) throws SQLException {
        stmtGet.setString(1, nom);
        ResultSet resultSet = stmtGet.executeQuery();
        boolean existe = resultSet.next();
        resultSet.close();
        return existe;
    }

    public void ajouterProducteur(String nom, String courriel, int nombreEmployes, String adresse) throws SQLException {
        stmtInsert.setString(1, nom);
        stmtInsert.setString(2, courriel);
        stmtInsert.setInt(3, nombreEmployes);
        stmtInsert.setString(4, adresse);
        stmtInsert.executeUpdate();
    }
       /** Lecture d'un Producteur*/
    public TupleProducteur getProducteur(String nomProducteur) throws SQLException {
        // TODO : votre implémentation ici
        stmtGet.setString(1, nomProducteur);
        try (ResultSet rs = stmtGet.executeQuery()) {
            if (rs.next()) {
                TupleProducteur tupleProducteur = new TupleProducteur();
                tupleProducteur.setNom(rs.getString("Nom_producteur")); // Ajout de l'ID du producteur

                tupleProducteur.setCourriel(rs.getString("Courriel"));
                tupleProducteur.setNbEmploi(rs.getInt("Nombre_Employes"));
                tupleProducteur.setAdresse(rs.getString("Adresse_Postale"));
                tupleProducteur.setIdProducteur(rs.getInt("idProducteur"));
                return tupleProducteur;
            } else {
                return null;
            }
        }

    }



    public void supprimeProducteur(int idProducteur) throws SQLException{
        stmtDelete.setInt(1,idProducteur);
        stmtDelete.executeUpdate();


    }



}
