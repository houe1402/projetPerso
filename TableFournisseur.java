package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TupleProducteur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableFournisseur extends GestionTables{
    private final PreparedStatement stmtInsert;

    private final PreparedStatement stmtGet;

    private final PreparedStatement stmtGetList;
    private final PreparedStatement  stmtDelete;

    private final PreparedStatement  stmtListePret ;

    public TableFournisseur(Connexion cx)  throws SQLException {
        super(cx);

        this.stmtInsert = cx.getConnection().prepareStatement(
                "INSERT INTO Fournisseur(nom_Fournisseur, courriel, adresse_postale)" +
                        "VALUES (?, ?, ?)"
        );

        this.stmtGet = cx.getConnection().prepareStatement(
                "SELECT Nom_Fournisseur, courriel, adresse_postale,idfournisseur FROM Fournisseur WHERE nom_Fournisseur = ?"
        );
        this.stmtGetList = cx.getConnection().prepareStatement(
                "SELECT Nom_Fournisseur, courriel, adresse_postale ,idfournisseur FROM Fournisseur WHERE idfournisseur = ?"
        );


        this.stmtDelete = cx.getConnection().prepareStatement(
                "delete from Fournisseur where nom_Fournisseur=?");



        this.stmtListePret = cx.getConnection().prepareStatement(
                "SELECT f.* FROM FOURNISSEUR f " +
                        "JOIN producteurfournisseur ff ON f.idFournisseur = ff.idFournisseur " +
                        "JOIN PRODUCTEUR p ON ff.idProducteur = p.idProducteur " +
                        "WHERE p.Nom_Producteur = ?");



        //TODO votre code ici
    }



    public boolean existe(String nom) throws SQLException {
        stmtGet.setString(1, nom);
        ResultSet resultSet = stmtGet.executeQuery();
        boolean existe = resultSet.next();
        resultSet.close();
        return existe;
    }

    public void ajouterFournisseur(String nom, String courriel,  String adresse) throws SQLException {
        stmtInsert.setString(1, nom);
        stmtInsert.setString(2, courriel);
        stmtInsert.setString(3, adresse);
        stmtInsert.executeUpdate();
    }
    public TupleFournisseur getFournisseur(String nomFournisseur) throws SQLException {

        //TODO votre code ici// Ane plus toucher
        stmtGet.setString(1, nomFournisseur);
        try (ResultSet rs = stmtGet.executeQuery()) {
            if (rs.next()) {
                TupleFournisseur tuplefournisseur = new TupleFournisseur();
                // Ajout de l'ID du producteur

                tuplefournisseur.setNom(rs.getString("Nom_Fournisseur"));
                tuplefournisseur.setCourriel(rs.getString("Courriel"));
                tuplefournisseur.setAdresse(rs.getString("Adresse_Postale"));
                tuplefournisseur.setIdFournisseur(rs.getInt("idFournisseur"));
                return tuplefournisseur;


            } else {
                return null;
            }
        }

    }
    public List<TupleFournisseur> getFournisseursProducteurList(String nomFournisseur)throws SQLException {
        //TODO votre code ici
        stmtGetList.setString(1,nomFournisseur);
        ResultSet rest = stmtGetList.executeQuery();
        List<TupleFournisseur> fournisseurproduct= new LinkedList<>();
        while(rest.next()){
            TupleFournisseur fournisseur = new TupleFournisseur(rest.getString("nom_Fournisseur"),
                    rest.getString("courriel") ,
                    rest.getString("adresse"),
                    rest.getInt("idFournisseur"));



            fournisseurproduct.add(fournisseur);

        }
        rest.close();
        return fournisseurproduct;

    }





    public List<TupleFournisseur> getFournisseursProducteur(String nomProducteur)throws SQLException {
        //TODO votre code ici
        stmtListePret.setString(1,nomProducteur);
        ResultSet rest = stmtListePret.executeQuery();
        List<TupleFournisseur> fournisseurproduct= new LinkedList<>();
        while(rest.next()){
            TupleFournisseur fournisseur = new TupleFournisseur(rest.getString("nom_Fournisseur"),
                                                                rest.getString("courriel") ,
                                                                rest.getString("adresse_postale"),
                                                                rest.getInt("idFournisseur"));


            fournisseurproduct.add(fournisseur);

        }
        rest.close();
        return fournisseurproduct;

    }



    public int supprimeFournisseur(String nom) throws SQLException{
        stmtDelete.setString(1,nom);
        return stmtDelete.executeUpdate();

    }










}


