package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TuplePointDeVente;
import apce.tuples.TupleProduit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TablePointDeVente extends GestionTables{


    private final PreparedStatement stmtInsert;

    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtGet;


    public TablePointDeVente(Connexion cx) throws SQLException {
        super(cx);

        this.stmtInsert = cx.getConnection().prepareStatement(
                "INSERT INTO pointdevente (Nom_PointdeVente, Courriel, Adresse_Postale) VALUES (?, ?, ?)"
        );


        this.stmtDelete = cx.getConnection().prepareStatement(
                "DELETE FROM pointdevente WHERE nom_pointdevente = ?"
        );
        this.stmtGet = cx.getConnection().prepareStatement(
                "SELECT nom_pointdevente, courriel, adresse_postale,idpointdevente FROM pointdevente WHERE Nom_PointdeVente = ?"
        );


    }
    public boolean existe(String nom) throws SQLException {
        stmtGet.setString(1, nom);
        ResultSet resultSet = stmtGet.executeQuery();
        boolean existe = resultSet.next();
        resultSet.close();
        return existe;
    }

    public void ajouterPointDeVente(String nom, String courriel, String adresse) throws SQLException {
        stmtInsert.setString(1, nom);
        stmtInsert.setString(2, courriel);
        stmtInsert.setString(3, adresse);
        stmtInsert.executeUpdate();
    }



    public void supprimePointDeVente(String nompointdevnte) throws SQLException {
        stmtDelete.setString(1, nompointdevnte);
        stmtDelete.executeUpdate();
    }

    public TuplePointDeVente getPointDeVente(String nom) throws SQLException {
        stmtGet.setString(1, nom);
        try (ResultSet rs = stmtGet.executeQuery()) {
            if (rs.next()) {
                TuplePointDeVente tuplepointdevente = new TuplePointDeVente();
                tuplepointdevente.setNom(rs.getString("Nom_PointDeVente")); // Ajout de l'ID du producteur
                tuplepointdevente.setCourriel(rs.getString("Courriel"));
                tuplepointdevente.setAdresse(rs.getString("Adresse_Postale"));
                tuplepointdevente.setidpointdevente(rs.getInt("idpointdevente"));


                return tuplepointdevente;
            } else {
                return null;
            }
        }
    }




}
