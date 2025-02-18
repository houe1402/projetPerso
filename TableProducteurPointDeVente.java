package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TuplePointDeVente;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableProducteurPointDeVente extends GestionTables {

    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtDeletee;
    private final PreparedStatement stmtListeProd ;
    private final PreparedStatement stmtListePoint ;


    public TableProducteurPointDeVente(Connexion cx) throws SQLException {
        super(cx);
        this.stmtInsert =cx.getConnection().prepareStatement(
                "INSERT INTO ProducteurPointDeVente (idProducteur,idpointdevente) VALUES (?, ?)");
        this.stmtDelete =cx.getConnection().prepareStatement(
                "DELETE FROM ProducteurPointDeVente WHERE idpointdevente = ?"
        );
        this.stmtDeletee =cx.getConnection().prepareStatement(
                "DELETE FROM ProducteurPointDeVente WHERE idproducteur = ?"
        );
        this.stmtListeProd = cx.getConnection().prepareStatement(
                "SELECT p.* FROM producteur p " +
                        "JOIN producteurpointdevente pp ON p.idproducteur = pp.idproducteur  " +
                        "JOIN pointdevente po ON po.idpointdevente = pp.idpointdevente " +
                        "WHERE po.nom_pointdevente = ?");
        this.stmtListePoint = cx.getConnection().prepareStatement(
                "SELECT p.* FROM pointdevente p " +
                        "JOIN producteurpointdevente pp ON p.idpointdevente = pp.idpointdevente  " +
                        "JOIN producteur po ON po.idproducteur = pp.idproducteur " +
                        "WHERE po.nom_producteur = ?");

        //TODO votre code ici
    }



    public void ajouterPointDeVenteProducteur( int idProducteur, int idpointDeVente  ) throws SQLException{

        stmtInsert.setInt(1, idProducteur);
        stmtInsert.setInt(2,  idpointDeVente);
        stmtInsert.executeUpdate();
    }

    public void supprimerAssociationsProducteurs(int ipointdevente) throws SQLException{


        stmtDelete.setInt(1,ipointdevente);
        stmtDelete.executeUpdate();

    }
    public void supprimeProductFrompointdevente(int iproducteur) throws SQLException{
        stmtDeletee.setInt(1,iproducteur);
        stmtDeletee.executeUpdate();
    }

    public List<TupleProducteur> getPointdeProducteur(String pointdevente)throws SQLException {


        stmtListeProd.setString(1, pointdevente);
        ResultSet rest = stmtListeProd.executeQuery();
        List<TupleProducteur> productpoint = new LinkedList<>();
        while (rest.next()) {
            TupleProducteur product = new TupleProducteur(
                    rest.getString("nom_Producteur"),

                    rest.getString("Courriel"),
                    rest.getString("adresse_postale"),
                    rest.getInt("Nombre_Employes"),

                    rest.getInt("idProducteur")
            );
            productpoint.add(product);
        }
        rest.close();
        return productpoint;
    }

    public List<TuplePointDeVente> getProducPointdeVente(String nmproducteur) throws SQLException {


            stmtListePoint.setString(1, nmproducteur);
            ResultSet rst = stmtListePoint.executeQuery();
            List<TuplePointDeVente> prodpoint = new LinkedList<>();
            while (rst.next()) {
                TuplePointDeVente pvente = new TuplePointDeVente(
                        rst.getString("nom_PointDevente"),

                        rst.getString("Courriel"),
                        rst.getString("adresse_postale"),
                        rst.getInt("idpointdevente")

                );


                prodpoint.add(pvente);

            }
            rst.close();
            return prodpoint;
        }

}








